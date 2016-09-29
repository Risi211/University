// multiple producers - single consumer
#include <iostream>           // std::cout
#include <thread>             // std::thread
#include <mutex>              // std::mutex, std::unique_lock
#include <condition_variable> // std::condition_variable
#include <vector>

using namespace std;

#define MAX 10
#define NUM_PRODUCERS 10

mutex out; //for cout
mutex mtx;
condition_variable full;
condition_variable empty;
int count = 0;

void producer(vector<int>* buffer){

  //critical section: access to cout
  out.lock();
  cout << "producer id: " << this_thread::get_id() << "\r\n";
  out.unlock();
  for(int i = 1; i <= 100; i++){
	unique_lock<mutex> lck(mtx);	//unique access
	//if buffer is full
	if(buffer->size() >= MAX){
		//wait
		full.wait(lck);
	}
	//produce
	buffer->push_back(i);	
	empty.notify_all();
  }
  out.lock();
  count++;
  out.unlock();  
}

void consumer(vector<int>* buffer){

  //critical section: access to cout
  out.lock();
  cout << "consumer id: " << this_thread::get_id() << "\r\n";
  out.unlock();
//  for(int i = 0; i < 100; i++){
  while(count < NUM_PRODUCERS){
	unique_lock<mutex> lck(mtx);	//unique access
	//if buffer is empty
	if(buffer->empty()){
		//wait
		empty.wait(lck);
	}
	//consume
	cout << "i: " << buffer->front() << "\r\n";
	buffer->erase(buffer->begin());
	full.notify_all();
  }
  //consume what is left into the buffer
  while(!buffer->empty()){
	//consume
	cout << "i: " << buffer->front() << "\r\n";
	buffer->erase(buffer->begin());
	full.notify_all();
  }   
}

int main ()
{
  vector<int> buffer;
  thread cons(consumer, &buffer);
  thread producers[NUM_PRODUCERS];
  for(int i = 0; i < NUM_PRODUCERS; i++){
    producers[i] = thread(producer, &buffer);
  }

  for (auto& th : producers) 
	th.join();

  cons.join();

  return 0;
}
