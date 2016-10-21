#include <iostream>
#include <string>

#include "Money.h"
#include "MoneyFactory.h"

using namespace std;

int main(){
	const int SUP=100; // <--- Suppose this is one million :)
	MoneyFactory factory;
	for (int i = 0; i < SUP; ++i)
	{
		Money* graphicalMoney;
		if (i < 10)
		  graphicalMoney = factory.GetMoneyToDisplay(MoneyType::Metallic);
		else
		  graphicalMoney = factory.GetMoneyToDisplay(MoneyType::Paper);
		
		graphicalMoney->GetDisplayOfMoneyFalling(i);
	}
	cout << "Total Objects created = " << MoneyFactory::objectsCount << "\r\n";
	return 0;
}
