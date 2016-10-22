using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    //object that stores the historical state
	public class Memento<T>
    {
		T state;
		public T GetState()
        {
            return state;
        }
        public void SetState(T state)
        {
            this.state = state;
        }
    }

    //the object that we want to save and restore, such as a check point in an application
	public class Originator<T>
    {
		T state;
		//for saving the state
		public Memento<T> CreateMemento()
        {
            Memento<T> m = new Memento<T>();
            m.SetState(state);
            return m;
        }
        //for restoring the state
        public void SetMemento(Memento<T> m)
        {
            state = m.GetState();
        }
        //change the state of the Originator
        public void SetState(T state)
        {
            this.state = state;
        }
        //show the state of the Originator
        public void ShowState()
        {
            Console.WriteLine(state.ToString() + "\r\n");
        }
    }

    //object for the client to access
	public class Caretaker<T>
    {
		//list of states saved
		List<Memento<T>> mementoList = new List<Memento<T>>();
		//save state of the originator
		public void SaveState(Originator<T> orig)
        {
            mementoList.Add(orig.CreateMemento());
        }

        //restore state of the originator
        public void RestoreState(Originator<T> orig, int stateNumber)
        {
            orig.SetMemento(mementoList[stateNumber]);
        }
    }

    class Program
    {
        static void Main(string[] args)
        {

            Originator<string> orig = new Originator<string>();
            Caretaker<string> care = new Caretaker<string>();

            orig.SetState("state 0");
            care.SaveState(orig); //save state of the originator
            orig.ShowState();

            orig.SetState("state 1");
            care.SaveState(orig); //save state of the originator
            orig.ShowState();

            orig.SetState("state 2");
            care.SaveState(orig); //save state of the originator
            orig.ShowState();

            //restore state of the originator
            care.RestoreState(orig, 0);
            orig.ShowState();  //shows state0

            Console.Read();
        }
    }
}
