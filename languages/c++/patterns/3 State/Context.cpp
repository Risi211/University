#include "State.h"
#include "StateAppend1.h"
#include "Context.h"

Context::Context(){
	currentState = new StateAppend1;
}

void Context::setState(State* s){
	currentState = s;
}

void Context::write(string s){
	currentState->write(this, s);
}

