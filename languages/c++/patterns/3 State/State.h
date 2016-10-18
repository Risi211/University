#ifndef __State_INCLUDED__   // if x.h hasn't been included yet...
#define __State_INCLUDED__   //   #define this so the compiler knows it has been included

#include <string>

using namespace std;

class Context;

class State{
	public:
		virtual void write(Context* c, string s) = 0;
};

#endif
