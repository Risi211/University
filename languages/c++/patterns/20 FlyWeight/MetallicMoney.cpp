#include "Money.h"
#include "MoneyType.h"
#include "MetallicMoney.h"

using namespace std;

MoneyType MetallicMoney::GetMoneyType()
   {
     return MoneyType::Metallic;
   }
