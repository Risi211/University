// exceptions
#include <iostream>
#include <exception>
using namespace std;

#define TABLE_SIZE 100

class myexception: public exception
{
  virtual const char* what() const throw()
  {
    return "My exception happened";
  }
} myex;

int main () {
  //default exception
  try
  {
    throw 20;
  }
  catch (int e)
  {
    cout << "An exception occurred. Exception Nr. " << e << '\n';
  }
  catch (char param) { cout << "char exception"; }
  catch (...) { cout << "default exception"; }
  
  //user defined exception
  try
  {
    throw myex;
  }
  catch (exception& e)
  {
    cout << e.what() << '\n';
  }

  //pre-processer
  int table1[TABLE_SIZE];

  #ifdef TABLE_SIZE
  int table2[TABLE_SIZE];
  #endif

  #line 20 "assigning variable"
  int a = 10;

  return 0;
}
