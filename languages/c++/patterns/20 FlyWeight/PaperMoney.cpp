#include "Money.h"
#include "MoneyType.h"
#include "PaperMoney.h"

using namespace std;

MoneyType PaperMoney::GetMoneyType()
   {
     return MoneyType::Paper;
   }
