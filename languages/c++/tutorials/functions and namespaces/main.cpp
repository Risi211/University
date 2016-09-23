// my first program in C++
#include <iostream>
#include <string>
#include <sstream>
using namespace std;

#define NEWLINE "\r\n"

//function prototypes, to declare before main
template <class T>
T addition(T, T); //template function

//namespaces: separate names of global variables / functions
namespace print{

	void printmessage ()
	{
	  cout << "I'm a function!" << NEWLINE;
	}

	//overloading
	void printmessage (string msg)
	{
	  cout << msg << NEWLINE;
	}

}

namespace op{

	void duplicate(int& a){
	  a*=2;
	}

	//inline: the function is treated by the compiler as a macro. Code execution flow is sequential, there is not a call() that changes the IP register
	inline string concatenate(const string& a, const string& b){
	  return a+b;
	}

	//same function name of namespace print
	void printmessage (string msg)
	{
	  cout << msg << " asd asd " << NEWLINE;
	}

}

int main ()
{
  
  //import namespaces
  using namespace print;
  using namespace op;

  printmessage();
  print::printmessage("voila"); //function of print namespace
  op::printmessage("voila"); ////function of op namespace
  cout << addition<int>(5,3) << NEWLINE;
  cout << addition<float>(1.2f,2.2f) << NEWLINE;
  cout << addition<string>("a"," b") << NEWLINE;

  //passed by reference
  int a = 5;
  duplicate(a);
  cout << a << NEWLINE;

  //by value: the strings are copied
  //by reference: the function works directly on the passed strings
  //const string&: it tells the user that the strings will not be modified by the functions. The function can access string values directly, without copying them.
  string s1 = "asd";
  string s2 = "zxc";
  cout << concatenate(s1, s2) << NEWLINE;

}

//template function
template <class T>
T addition (T a, T b)
{
  return a+b;
}
