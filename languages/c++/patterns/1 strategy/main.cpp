#include <iostream>
#include <string>

using namespace std;

class Strategy{
  public:
    virtual int doOp(int n1, int n2) = 0;
};

class Addition : public Strategy{
  public:
    int doOp(int n1, int n2){
	return n1 + n2;
    }
};

class Substraction : public Strategy{
  public:
    int doOp(int n1, int n2){
	return n1 - n2;
    }
};

class Context{
  private:
    Strategy* op;
  public:
    Context(Strategy* str) : op(str) {}
    int execOp(int n1, int n2){
	return op->doOp(n1,n2);
    }
};

int main()
{

  Context c1 (new Addition);
  Context c2 (new Substraction);  

  cout << "c1: " << c1.execOp(5,3) << "\r\n";
  cout << "c2: " << c2.execOp(5,3) << "\r\n";

  return 0;
}
