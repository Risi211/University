// condition_variable example
#include <iostream>           // std::cout
#include <thread>             // std::thread
#include <mutex>              // std::mutex, std::unique_lock
#include <condition_variable> // std::condition_variable

std::mutex mtx;
std::condition_variable cv;
bool ready = false;

void print_id (int id) {
//At the moment of blocking the thread, the function automatically calls lck.unlock(), allowing other locked threads to continue.
  std::unique_lock<std::mutex> lck(mtx);
  while (!ready) cv.wait(lck);
  // ...
  std::cout << "thread " << id << '\n';
}

void go() {
//At the moment of blocking the thread, the function automatically calls lck.unlock(), allowing other locked threads to continue.
  std::unique_lock<std::mutex> lck(mtx);
  ready = true;
  cv.notify_all();
}

int main ()
{
  std::thread threads[10];
  // spawn 10 threads:
  for (int i=0; i<10; ++i)
    threads[i] = std::thread(print_id,i);

  //all threads are blocked
  std::cout << "10 threads ready to race...\n";
  
  //main thread makes ready=true and notifies all the blocked threads
  go();                       // go!

  for (auto& th : threads) th.join();

  return 0;
}

/*
why condition variables are needed over mutexes?

People may think they can implement a feature like conditional variable without the support of kernel. A common pattern one might come up with is the "flag + mutex" like:

lock(mutex)

while (!flag) {
    sleep(100);
}

unlock(mutex)

do_something_on_flag_set();

but it will never work, because you never release the mutex during the waiting, no one else can set the flag in a thread-safe way. This is why we need conditional variable, when you're waiting on a condition variable, the associated mutex is not hold by your thread until it's signaled.

*/
