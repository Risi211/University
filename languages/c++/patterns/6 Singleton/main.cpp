#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Singleton{
  private:
    int value;
    static Singleton* instance;
    Singleton(int v = 0){ //private constructor, initialize value = 0
	value = v;
    }
  public:
    static Singleton* getInstance(){
	if(instance == 0){
		instance = new Singleton;
	}
	return instance;
    }
    int getValue() {return value;}
    void setValue(int v) {value = v;}
};

//init static field, not the object
Singleton* Singleton::instance = 0;

int main()
{
  
  Singleton::getInstance()->setValue(10);
  cout << "value: " <<   Singleton::getInstance()->getValue() << "\r\n";
  Singleton::getInstance()->setValue(20);
  cout << "value: " <<   Singleton::getInstance()->getValue() << "\r\n";

  return 0;
}
