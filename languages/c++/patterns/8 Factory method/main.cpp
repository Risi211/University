#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Currency{ //abstract class
  public:
    virtual string getSymbol() = 0;
};

class Dollar : public Currency{
  public:
    string getSymbol(){return "$";}
};

class Euro : public Currency{
  public:
    string getSymbol(){return "€";}
};

class CurrencyFactory{ //factory class
  public:
    static Currency* getCurrencyInstance(string country){
	if(country == "USA"){
		return new Dollar;
	}
	if(country == "Europe"){
		return new Euro;
	}
    }
};

int main()
{
  
  Currency* c1 = CurrencyFactory::getCurrencyInstance("USA");
  Currency* c2 = CurrencyFactory::getCurrencyInstance("Europe");
  cout << "c1: " << c1->getSymbol() << ", c2: " << c2->getSymbol() << "\r\n";

  return 0;
}
