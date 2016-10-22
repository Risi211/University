using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public interface Stooge
    {
        Stooge clone(); //return the specific object 
        void slap_stick();
    }

    public static class Factory
    { //contains the prototypes
        private static Stooge[] s_prototypes = new Stooge[] { null, new Larry(), new Moe(), new Curly()};
        public static Stooge make_stooge(int choice)
        {
            return s_prototypes[choice].clone();
        }
    }

    public class Larry : Stooge {
        public Stooge clone() { return new Larry(); }
        public void slap_stick()
        {
            Console.WriteLine("Larry: poke eyes\r\n");
        }
    }
	
	public class Moe : Stooge {
		public Stooge clone() { return new Moe(); }
		public void slap_stick()
		{
			Console.WriteLine("Moe: slap head\r\n");
		}
	}

	public class Curly : Stooge {
	   public Stooge clone() { return new Curly(); }
		public void slap_stick()
		{
			Console.WriteLine("Curly: suffer abuse\r\n");
		}
	}

class Program
    {
        static void Main(string[] args)
        {
            List<Stooge> roles = new List<Stooge>();
            int choice;

            while (true)
            {
                Console.WriteLine("Larry(1) Moe(2) Curly(3) Go(0): ");
                choice = int.Parse(Console.ReadLine());
                if (choice == 0)
                    break;
                roles.Add(Factory.make_stooge(choice)); //get the selected prototype
            }

			for (int i = 0; i < roles.Count; ++i)
				roles[i].slap_stick();

            Console.Read();        
		}	
    }
}
