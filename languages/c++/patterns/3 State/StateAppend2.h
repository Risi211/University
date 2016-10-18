#ifndef __StateAppend2_INCLUDED__   // if x.h hasn't been included yet...
#define __StateAppend2_INCLUDED__   //   #define this so the compiler knows it has been included

#include "State.h"

class StateAppend2 : public State{
	public:
		void write(Context* c, string s);
};

#endif
