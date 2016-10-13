using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public interface Currency
    {
        string getSymbol();
    }
    public class Dollar : Currency
    {
        public string getSymbol()
        {
            return "$";
        }
    }
    public class Euro : Currency
    {
        public string getSymbol()
        {
            return "€";
        }
    }
    public class CurrencyFactory
    {
        private CurrencyFactory() { }
        public static Currency getCurrencyInstance(string country)
        {
            switch (country)
            {
                case "usa":
                    {
                        return new Dollar();
                    }
                case "europe":
                    {
                        return new Euro();
                    }
                default:
                    {
                        return null;
                    }
            }
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            Currency c1 = CurrencyFactory.getCurrencyInstance("usa");
            Currency c2 = CurrencyFactory.getCurrencyInstance("europe");
            Console.WriteLine(c1.getSymbol());
            Console.WriteLine(c2.getSymbol());
            Console.Read();
        }
    }
}
