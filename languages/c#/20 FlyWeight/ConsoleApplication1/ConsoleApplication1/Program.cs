using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public enum MoneyType
    {
        Metallic = 0,
        Paper = 1
    }

    public abstract class Money
    {
        public abstract MoneyType GetMoneyType(); //IntrinsicState()
        public void GetDisplayOfMoneyFalling(int moneyValue) //GetExtrinsicSate()
        {
            Console.WriteLine("Displaying a graphical object of " + GetMoneyType().ToString() + " currency of " + moneyValue + "falling from the sky\r\n");
        }
    }

    public class MetallicMoney : Money
    {
        public override MoneyType GetMoneyType()
        {
            return MoneyType.Metallic;
        }
    }

    public class PaperMoney : Money
    {
        public override MoneyType GetMoneyType()
        {
            return MoneyType.Paper;
        }
    }

    class MoneyFactory
    {        
		private Money[] objects = new Money[2];
		public static int objectsCount = 2;
        public MoneyFactory()
        {
            objects[0] = new MetallicMoney();
            objects[1] = new PaperMoney();
        }
        public Money GetMoneyToDisplay(MoneyType form) // Same as GetFlyWeight()
        {
            switch (form)
            {
                case MoneyType.Metallic:
                    {
                        return objects[0];
                    }
                case MoneyType.Paper:
                    {
                        return objects[1];
                    }
                default: return null;
            }
        }
    }
class Program
    {
        static void Main(string[] args)
        {
            const int SUP = 100; // <--- Suppose this is one million :)
            MoneyFactory factory = new MoneyFactory();
            for (int i = 0; i < SUP; ++i)
            {
                Money graphicalMoney;
                if (i < 10)
                    graphicalMoney = factory.GetMoneyToDisplay(MoneyType.Metallic);
                else
                    graphicalMoney = factory.GetMoneyToDisplay(MoneyType.Paper);

                graphicalMoney.GetDisplayOfMoneyFalling(i);
            }
            Console.WriteLine("Total Objects created = " + MoneyFactory.objectsCount + "\r\n");

            Console.Read();
        }
    }
}
