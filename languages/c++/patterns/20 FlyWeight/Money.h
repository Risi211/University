#ifndef Money_Included
#define Money_Included

#include "MoneyType.h"

class Money
{
  public:
  virtual MoneyType GetMoneyType() = 0; //IntrinsicState()
  void GetDisplayOfMoneyFalling(int moneyValue); //GetExtrinsicSate()
};

#endif
