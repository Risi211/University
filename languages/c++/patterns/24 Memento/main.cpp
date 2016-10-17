#include <iostream>
#include <string>
#include <vector>

using namespace std;

//object that stores the historical state
template<typename T>
class Memento
{
	private:
		T state;
	public:
		T GetState()
		{
			return state;
		}
		void SetState(T state)
		{
			this->state = state;
		}
};

//the object that we want to save and restore, such as a check point in an application
template<typename T>
class Originator
{
	private:
		T state;
	public:
		//for saving the state
		Memento<T>* CreateMemento()
		{
			Memento<T>* m = new Memento<T>;
			m->SetState(state);
			return m;
		}
		//for restoring the state
		void SetMemento(Memento<T>* m)
		{
			state = m->GetState();
		}
		//change the state of the Originator
		void SetState(T state)
		{
			this->state = state;
		}
		//show the state of the Originator
		void ShowState()
		{
			cout << state << "\r\n";
		}
};

//object for the client to access
template<typename T>
class Caretaker
{
	private:
		//list of states saved
		vector<Memento<T>*> mementoList;
	public:
		//save state of the originator
		void SaveState(Originator<T>& orig)
		{
			mementoList.push_back(orig.CreateMemento());
		}

		//restore state of the originator
		void RestoreState(Originator<T>& orig, int stateNumber)
		{
			orig.SetMemento(mementoList.at(stateNumber));
		}
};

int main(){

        Originator<string> orig;
        Caretaker<string> care;

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

	return 0;
}
