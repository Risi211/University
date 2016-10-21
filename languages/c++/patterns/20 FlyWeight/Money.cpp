#include <iostream>
using namespace std;

#include "Money.h"
#include "MoneyType.h"

void Money::GetDisplayOfMoneyFalling(int moneyValue) //GetExtrinsicSate()
{
	//This method would display graphical representation of a metallic currency like a    
	//gold coin.
	cout << "Displaying a graphical object of " << static_cast<typename std::underlying_type<MoneyType>::type>(GetMoneyType()) << " currency of " << moneyValue << "falling from the sky\r\n";
}
