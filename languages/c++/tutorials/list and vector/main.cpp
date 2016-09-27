#include <iostream>
#include <string>
#include <list>
#include <vector>

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
//strategy pattern
  cout << "c1: " << c1.execOp(5,3) << "\r\n";
  cout << "c2: " << c2.execOp(5,3) << "\r\n";

//list example
  list<Context> cs;
  //insert elements
  cs.push_back(c1);
  cs.push_back(c2);  
  cout << "size: " << cs.size() << "\r\n";
  cout << "res1: " << cs.front().execOp(6,2) << "\r\n"; //read first element without removing it
  auto it = cs.begin();  //iterator of list,it points to the first element
  ++it; //it cannot be added like in vector iterator
  cs.erase(it);   //remove element pointed by the iterator
  cout << "size: " << cs.size() << "\r\n";  
  cout << "res2: " << cs.front().execOp(6,2) << "\r\n";

//vector example
  vector<Context> cv;
  //insert elements
  cv.push_back(c1);
  cv.push_back(c2);  
  cout << "size: " << cv.size() << "\r\n";
  //read
  cout << "res3: " << cv[0].execOp(6,2) << "\r\n"; //only vector allow random access
  cout << "res4: " << cv[1].execOp(6,2) << "\r\n";
  //remove
  auto it2 = cv.begin() + 1; //it is not allowed in list, only in vector
  cv.erase(it2);
  cout << "res5: " << cv[0].execOp(6,2) << "\r\n"; 
  cout << "size: " << cv.size() << "\r\n";

  return 0;
}
