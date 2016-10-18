#include <iostream>
using namespace std;

#include "StateAppend1.h"
#include "StateAppend2.h"
#include "Context.h"

void StateAppend2::write(Context* c, string s){
	cout << s << "-2\r\n";
	//change state
	c->setState(new StateAppend1);
}

