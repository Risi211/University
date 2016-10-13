using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace main
{
    public interface Observer
    {
        void Update(float price);
    }
    public class ExObserver : Observer
    {
        int id = 0;
        public ExObserver(int id)
        {
            this.id = id;
        }
        public void Update(float price)
        {
            Console.WriteLine(this.id.ToString() + " new price = " + price.ToString());
        }
    }
    public interface Subject
    {
        void registerObserver(Observer obs);
        void notify();
    }
    public class Fuel : Subject
    {
        private List<Observer> observers = new List<Observer>();
        private float price = 0;
        public void notify()
        {
            foreach (Observer obs in observers)
            {
                obs.Update(this.price);
            }
        }

        public void registerObserver(Observer obs)
        {
            observers.Add(obs);
        }
        public float Price
        {
            get
            {
                return price;
            }
            set
            {
                this.price = value;
                //notify the observers
                this.notify();
            }
        }
    }
    public class Program
    {
        static void Main(string[] args)
        {
            Observer o1 = new ExObserver(1);
            Observer o2 = new ExObserver(2);
            Fuel f = new Fuel();
            f.registerObserver(o1);
            f.registerObserver(o2);
            f.Price = 1.2f;
            f.Price = 1.5f;
            Console.Read();
        }
    }
}