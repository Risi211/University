using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace main
{
    public interface Strategy
    {
        int doOperation(int n1, int n2);
    }

    public class Addition : Strategy
    {
        public int doOperation(int n1, int n2)
        {
            return n1 + n2;
        }
    }

    public class Substraction : Strategy
    {
        public int doOperation(int n1, int n2)
        {
            return n1 - n2;
        }
    }

    public class Context
    {
        private Strategy str;
        public Context(Strategy s)
        {
            this.str = s;
        }
        public int executeOperation(int n1, int n2)
        {
            return str.doOperation(n1, n2);
        }
    }

    public class Program
    {
        static void Main(string[] args)
        {
            Context c1 = new Context(new Addition());
            Console.WriteLine("c1: " + c1.executeOperation(5,3).ToString());
            Context c2 = new Context(new Substraction());
            Console.WriteLine("c2: " + c2.executeOperation(5, 3).ToString());
            Console.Read();
        }
    }
}
