using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public class SubsystemA
    {
		public string OperationA1()
        {
            return "Subsystem A, Method A1\n";
        }
        public string OperationA2()
        {
            return "Subsystem A, Method A2\n";
        }
    }

    public class SubsystemB
    {
		public string OperationB1()
        {
            return "Subsystem B, Method B1\n";
        }
        public string OperationB2()
        {
            return "Subsystem B, Method B2\n";
        }
    }

    public class SubsystemC
    {
		public string OperationC1()
        {
            return "Subsystem C, Method C1\n";
        }
        public string OperationC2()
        {
            return "Subsystem C, Method C2\n";
        }
    }

    public class Facade
    {
		private SubsystemA a;
        private SubsystemB b;
        private SubsystemC c;
		public Facade()
        {
            a = new SubsystemA();
            b = new SubsystemB();
            c = new SubsystemC();
        }
        public void Operation1()
        {
            Console.WriteLine("operation 1\r\n" + a.OperationA1() + b.OperationB1() + c.OperationC1());
        }
        public void Operation2()
        {
            Console.WriteLine("operation 2\r\n" + a.OperationA2() + b.OperationB2() + c.OperationC2());
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            Facade f = new Facade();
            f.Operation1();
            f.Operation2();

            Console.Read();
        }
    }
}
