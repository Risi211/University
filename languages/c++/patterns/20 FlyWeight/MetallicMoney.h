#ifndef MetallicMoney_Included
#define MetallicMoney_Included

#include "Money.h"
#include "MoneyType.h"

class MetallicMoney : public Money
{
	public:
	   MoneyType GetMoneyType();
};

#endif
