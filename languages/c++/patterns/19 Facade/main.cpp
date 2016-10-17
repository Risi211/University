#include <iostream>
#include <string>

using namespace std;

class SubsystemA{
	public:
		string OperationA1(){
			return "Subsystem A, Method A1\n";
		}
	        string OperationA2(){
	            return "Subsystem A, Method A2\n";
		}
};

class SubsystemB{
	public:
		string OperationB1(){
			return "Subsystem B, Method B1\n";
		}
	        string OperationB2(){
	            return "Subsystem B, Method B2\n";
		}
};    

class SubsystemC{
	public:
		string OperationC1(){
			return "Subsystem C, Method C1\n";
		}
	        string OperationC2(){
	            return "Subsystem C, Method C2\n";
		}
};

class Facade{
	private:
		SubsystemA* a;
		SubsystemB* b;
		SubsystemC* c;
	public:
		Facade() {
			a = new SubsystemA;
			b = new SubsystemB;
			c = new SubsystemC;
		}
		void Operation1(){
			cout << "operation 1\r\n" << a->OperationA1() <<  b->OperationB1() << c->OperationC1();
		}
		void Operation2(){
			cout << "operation 2\r\n" << a->OperationA2() <<  b->OperationB2() << c->OperationC2();
		}
};

int main(){
	Facade f;
	f.Operation1();
	f.Operation2();
}
