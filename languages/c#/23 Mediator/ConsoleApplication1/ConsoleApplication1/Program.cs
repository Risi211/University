using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    
	public interface IColleague<T>
    { //interface
		void send(IMediator<T> m, T s);
        void receive(T s);
	}

	public interface IMediator<T>
    { //interface
		void distributeMessage(T s);
		void registerColleague(IColleague<T> c);
	}

	public class Colleague<T> : IColleague<T>{
		string name;
        public Colleague(string n) { name = n; }
        public void send(IMediator<T> m, T s){
			m.distributeMessage(s);
        }
        public void receive(T s)
        {
            Console.WriteLine(name + " received " + s + "\r\n");
        }
	}

    public class Mediator<T> : IMediator<T>{
		List<IColleague<T>> colleagues = new List<IColleague<T>>();
		public void distributeMessage(T s)
        {
            foreach (IColleague<T> it in colleagues) {
            it.receive(s);
            }
        }
        public void registerColleague(IColleague<T> c){
			colleagues.Add(c);
		}	
	}

    class Program
    {
        static void Main(string[] args)
        {
			//list of participants
			IColleague<string> colleagueA = new Colleague<string>("ColleagueA");
			IColleague<string> colleagueB = new Colleague<string>("ColleagueB");
			IColleague<string> colleagueC = new Colleague<string>("ColleagueC");
			IColleague<string> colleagueD = new Colleague<string>("ColleagueD");

			//first mediator
			IMediator<string> mediator1 = new Mediator<string>();
			//participants registers to the mediator
			mediator1.registerColleague(colleagueA);
			mediator1.registerColleague(colleagueB);
			mediator1.registerColleague(colleagueC);
			//participantA sends out a message
			colleagueA.send(mediator1, "MessageX from ColleagueA");

			//second mediator
			IMediator<string> mediator2 = new Mediator<string>();
			//participants registers to the mediator
			mediator2.registerColleague(colleagueB);
			mediator2.registerColleague(colleagueD);
			//participantB sends out a message
			colleagueB.send(mediator2, "MessageY from ColleagueB");

				Console.Read();
		}
    }
}
