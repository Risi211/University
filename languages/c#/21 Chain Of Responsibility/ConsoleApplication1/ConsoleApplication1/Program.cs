using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public class Base
    {
        Base next; // 1. "next" pointer in the base class
        public Base()
        {
            next = null;
        }
        public void setNext(Base n)
        {
            next = n;
        }
        public void add(Base n)
        {
            if (next != null)
                next.add(n);
            else
                next = n;
        }
        // 2. The "chain" method in the base class always delegates to the next obj
        public virtual void handle(int i)
        {
            next.handle(i);
        }
    }

    public class Handler1 : Base
	{
		 public override void handle(int i)
		 {
				
			if (i % 4 > 0)
			{
				// 3. Don't handle requests 3 times out of 4
				Console.WriteLine("H1 passed " + i + "  ");
				base.handle(i); // 3. Delegate to the base class
			}
			else
				Console.WriteLine("H1 handled " + i + "  ");
		}
	}

	public class Handler2 : Base
	{
		 public override void handle(int i)
		{
			if (i % 3 > 0)
			{
				Console.WriteLine("H2 passed " + i + "  ");
				base.handle(i);
			}
			else
				Console.WriteLine("H2 handled " + i + "  ");
		}
	}

	public class Handler3 : Base
	{ 
		 public override void handle(int i)
		{
			//handle everything
			Console.WriteLine("H3 handled " + i + "  ");
		
		}
	}

    class Program
    {
        static void Main(string[] args)
        {            
            Handler1 root = new Handler1();
            Handler2 two = new Handler2();
            Handler3 thr = new Handler3();
            root.add(two);
            root.add(thr);
            thr.setNext(root);
            for (int i = 1; i < 10; i++)
            {
                root.handle(i);
            }

            Console.Read();
        }
    }
}
