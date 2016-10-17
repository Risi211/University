#include <iostream>
#include <string>
#include <vector>

using namespace std;

template <typename T>
class IMediator;

template <typename T>
class IColleague{ //interface
	public:
		virtual void send(class IMediator<T>* m, T s) = 0;
		virtual void receive(T s) = 0;
};

template <typename T>
class IMediator{ //interface
	public:
		virtual void distributeMessage(T s) = 0;
		virtual void registerColleague(class IColleague<T>* c) = 0;
};

template <typename T>
class Colleague : public IColleague<T>{
	private:
		string name;
	public:
		Colleague(string n) : name(n) {}
		void send(class IMediator<T>* m, T s){
			m->distributeMessage(s);
		}
		void receive(T s){
			cout << name << " received " << s << "\r\n";
		}
};

template <typename T>
class Mediator : public IMediator<T>{
	private:
		vector<IColleague<T>*> colleagues;
	public:
		void distributeMessage(T s){
			for(typename vector<IColleague<T>*>::iterator it = colleagues.begin(); it != colleagues.end(); ++it) {
			    (*it)->receive(s);
			}			
		}
		void registerColleague(class IColleague<T>* c){
			colleagues.push_back(c);
		}	
};

int main(){
        //list of participants
        IColleague<string>* colleagueA = new Colleague<string>("ColleagueA");
        IColleague<string>* colleagueB = new Colleague<string>("ColleagueB");
        IColleague<string>* colleagueC = new Colleague<string>("ColleagueC");
        IColleague<string>* colleagueD = new Colleague<string>("ColleagueD");

        //first mediator
        IMediator<string>* mediator1 = new Mediator<string>;
        //participants registers to the mediator
        mediator1->registerColleague(colleagueA);
        mediator1->registerColleague(colleagueB);
        mediator1->registerColleague(colleagueC);
        //participantA sends out a message
        colleagueA->send(mediator1, "MessageX from ColleagueA");

        //second mediator
        IMediator<string>* mediator2 = new Mediator<string>;
        //participants registers to the mediator
        mediator2->registerColleague(colleagueB);
        mediator2->registerColleague(colleagueD);
        //participantB sends out a message
        colleagueB->send(mediator2, "MessageY from ColleagueB");	
}
