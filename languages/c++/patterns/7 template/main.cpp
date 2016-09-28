#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Game{ //abstract class
  public:
    virtual void init() = 0; //pure virtual methods
    virtual void start() = 0;
    virtual void end() = 0;
    virtual void play(){
	init();
	start();
	end();
    }
};

class Football : public Game{
  public:
    void init(){
	cout << "football init \r\n";
    }
    void start(){
	cout << "football start \r\n";
    }
    void end(){
	cout << "football end \r\n";
    }
};

class Basket : public Game{
  public:
    void init(){
	cout << "basket init \r\n";
    }
    void start(){
	cout << "basket start \r\n";
    }
    void end(){
	cout << "basket end \r\n";
    }
};

int main()
{
  
  Football f;
  Basket b;
  f.play();
  b.play();

  return 0;
}
