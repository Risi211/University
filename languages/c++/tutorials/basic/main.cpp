// my first program in C++
#include <iostream>
#include <string>
#include <sstream>
using namespace std;

#define NEWLINE "\r\n"

int main()
{
  //std::cout << "Hello World!"; //without using namespace std
  cout << "Hello World!\r\n";

  //possible initializations
  int a = 1;
  int b (2);
  int c {3};

  //strings
  string s1 = "ciao ";
  string s2 ("sono ");
  string s3 {"Luca\r\n"};

  //integers
  int d1 = 10; //decimal
  int d2 = 012; //octal
  int d3 = 0xff; //hexadecimal
  unsigned int d4 = 10u; //unsigned int
  long int d5 = 10l; //long int
  unsigned long int d6 = 10ul; //unsigned long, = 10lu
  long long int d7 = 10ll; //long long int

  //floats
  float f1 = 3.14;
  float f2 = 6.02e3; //6.02 * 10^3
  float f3 = 6.02e-3; //6.02 * 10^(-3)
  float f4 = 3.14f; //float
  double f5 = 3.14l; //double

  //bool
  bool b1 = true;
  bool b2 = false;

  //comma operator: separate two expressions when only one is expected
  int g = (a++, a); //first a++, then g = a;

  //sizeof
  int size = sizeof(a);

  //cin: input numbers
  cout << "Inserisci valore per b: ";
  cin >> b;
  //necessary to clear cin buffer
  cin.clear();
  cin.ignore(999, '\n');

  //getline(): input strings. With cin it considers a whitespace as the terminating character.
  cout << "Inserisci valore s3: ";
  getline(cin, s3);

  //from string to numbers
  string s4 = "123";
  stringstream(s4) >> c;

  //string comparison
  string s5 = "123";
  if(s4 == s5){
	cout << "equals" << NEWLINE;
  }
  else{
	cout << "different" << NEWLINE;
  }

  //string concatenations
  cout << a << ", " << b << ", " << c << NEWLINE;
  cout << to_string(a) + ", " + to_string(b) + ", " + to_string(c) + NEWLINE; //only c++ 11
  cout << s1 + s2 + s3 + NEWLINE;  
  cout << d1 << ", " << d2 << ", " << d3 << NEWLINE;
  cout << d4 << ", " << d5 << ", " << d6 << ", " << d7 << NEWLINE;
  cout << f1 << ", " << f2 << ", " << f3 << ", " << f4 << ", " << f5 << NEWLINE;
  cout << "bool: " << b1 << ", " << b2 << NEWLINE;
  cout << "comma operator: " << g << NEWLINE;

  //goto
  goto end;

end:
  cout << "end" << NEWLINE;
}
