using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public class Singleton
    {
        static Singleton instance;
        int val = 0;
        private Singleton()
        {
            //private constructor
        }
        static public Singleton GetInstance()
        {
            if (instance == null)
            {
                instance = new Singleton();
            }
            return instance;
        }
        public int Value
        {
            get { return val; }
            set { val = value; }
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            Singleton instance = Singleton.GetInstance();
            Console.WriteLine(instance.Value.ToString());
            instance.Value = 20;
            Console.WriteLine(instance.Value.ToString());
            Console.Read();
        }
    }
}
