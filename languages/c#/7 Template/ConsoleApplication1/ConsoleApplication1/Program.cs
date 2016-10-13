using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public abstract class Game
    {
        public abstract void Init();
        public abstract void Body();
        public abstract void End();
        public void Play()
        {
            Init();
            Body();
            End();
        }
    }
    public class Football : Game
    {
        public override void Body()
        {
            Console.WriteLine("Football Body");
        }

        public override void End()
        {
            Console.WriteLine("Football End");
        }

        public override void Init()
        {
            Console.WriteLine("Football Init");
        }
    }
    public class Basketball: Game
    {
        public override void Body()
        {
            Console.WriteLine("Basketball Body");
        }

        public override void End()
        {
            Console.WriteLine("Basketball End");
        }

        public override void Init()
        {
            Console.WriteLine("Basketball Init");
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            Game g1 = new Football();
            Game g2 = new Basketball();
            Console.WriteLine("game 1:");
            g1.Play();
            Console.WriteLine("game 2:");
            g2.Play();
            Console.Read();
        }
    }
}
