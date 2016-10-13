using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace main
{
    class Program
    {
        public interface State
        {
            void doAction(Semaphore s);
        }
        public class Semaphore
        {
            private State ls;
            public State LightState
            {
                get
                {
                    return ls;
                }
                set
                {
                    this.ls = value;
                }
            }
        }
        public class Red : State
        {
            public void doAction(Semaphore s)
            {
                Console.WriteLine("Light: Red");
                s.LightState = this;
            }
        }
        public class Green : State
        {
            public void doAction(Semaphore s)
            {
                Console.WriteLine("Light: Green");
                s.LightState = this;
            }
        }
        public class Yellow : State
        {
            public void doAction(Semaphore s)
            {
                Console.WriteLine("Light: Yellow");
                s.LightState = this;
            }
        }
        static void Main(string[] args)
        {
            Semaphore sem = new Semaphore();            

            //alternate lights
            for (int i = 0; i < 21; i++)
            {
                if ((i % 3) == 0)
                {
                    new Red().doAction(sem);
                }
                if ((i % 3) == 1)
                {
                    new Green().doAction(sem);
                }
                if ((i % 3) == 2)
                {
                    new Yellow().doAction(sem);
                }
            }
            Console.Read();
        }
    }
}
