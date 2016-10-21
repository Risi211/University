#include <iostream>
#include <array>
#include "Money.h"
#include "MoneyFactory.h"
#include "MetallicMoney.h"
#include "PaperMoney.h"

int MoneyFactory::objectsCount = 2;

MoneyFactory::MoneyFactory(){
	objects[0] = new MetallicMoney;
	objects[1] = new PaperMoney;
}


Money* MoneyFactory::GetMoneyToDisplay(MoneyType form) // Same as GetFlyWeight()
   {
	switch(form){
		case MoneyType::Metallic:{
			return objects[0];
		}
		case MoneyType::Paper:{
			return objects[1];
		}
		default: return nullptr;
	}	
   }
