#ifndef __Context_INCLUDED__   // if x.h hasn't been included yet...
#define __Context_INCLUDED__   //   #define this so the compiler knows it has been included

#include <string>

using namespace std;

class State;

class Context{
	private:
		State* currentState;
	public:
		Context();
		void setState(State* s);
		void write(string s);
};


#endif 
