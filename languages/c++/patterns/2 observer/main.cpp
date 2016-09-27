#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Observer{
  public:
    virtual void update(float value) = 0;
};

class Subject{
  public:
    virtual void registerObserver(Observer* ob) = 0;
    virtual void notify() = 0;
};

class Fuel : public Subject{
  private:
    vector<Observer*> obs; //observers
    float price;
  public:
    void setPrice(float value){
	price = value;
	notify();
    }
    void registerObserver(Observer* ob){
	obs.push_back(ob);
    }
    void notify(){
	int length = obs.size();
	for(int i = 0; i < length; i++){
		obs[i]->update(price);
	}
    }
};

class ExObserver : public Observer{
  public:
    void update(float price){
	cout << "new price: " << price << "\r\n";
    }
};

int main()
{

  Observer* ob1 = new ExObserver;
  Fuel f;
  f.registerObserver(ob1);
  f.setPrice(2.2);

  return 0;
}
