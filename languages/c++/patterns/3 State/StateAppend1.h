#ifndef __StateAppend1_INCLUDED__   // if x.h hasn't been included yet...
#define __StateAppend1_INCLUDED__   //   #define this so the compiler knows it has been included

#include "State.h"

class StateAppend1 : public State{
	public:
		void write(Context* c, string s);
};

#endif
