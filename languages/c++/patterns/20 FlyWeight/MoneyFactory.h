#ifndef MoneyFactory_Included
#define MoneyFactory_Included

#include <iostream>
#include <array>
#include "Money.h"

using namespace std;

class MoneyFactory
{
	private:
		Money* objects[2];
	public:
		static int objectsCount;
		MoneyFactory();
		Money* GetMoneyToDisplay(MoneyType form); // Same as GetFlyWeight()
};

#endif
