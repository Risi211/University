#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Semaphore; //incomplete type: http://stackoverflow.com/questions/553682/when-can-i-use-a-forward-declaration/553869#553869

class State{
  public:
    virtual void action(Semaphore s) = 0;
};

class Red : public State{
  public:
    void action(Semaphore s);
};

class Yellow : public State{
  public:
    void action(Semaphore s);
};

class Green : public State{
  public:
    void action(Semaphore s);
};

class Semaphore{
  private:
    State* s;
  public:
    void setState(State* st){
	s = st; 
    }
};

void Red::action(Semaphore s)
{
	cout << "Red\r\n";
	s.setState(this);
}

void Yellow::action(Semaphore s)
{
	cout << "Yellow\r\n";
	s.setState(this);
}

void Green::action(Semaphore s)
{
	cout << "Green\r\n";
	s.setState(this);
}

int main()
{

  Semaphore sm;
  //init to red
  State* st = new Red;
  st->action(sm);

  return 0;
}
