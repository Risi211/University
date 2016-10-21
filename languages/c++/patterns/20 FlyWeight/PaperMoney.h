#ifndef PaperMoney_Included
#define PaperMoney_Included

#include "Money.h"
#include "MoneyType.h"

class PaperMoney : public Money
{
	public:
	   MoneyType GetMoneyType();
};

#endif
