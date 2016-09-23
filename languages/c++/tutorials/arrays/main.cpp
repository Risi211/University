// arrays example
#include <iostream>
#include <array>
using namespace std;

int foo [] = {16, 2, 77, 40, 12071};
int n, result=0;

//matrix 2d
int mat [5][3]; //it is like a 1d array 5*3

void printarray (int arg[], int length);

int main ()
{
  for ( n=0 ; n<5 ; ++n )
  {
    result += foo[n];
  }
  cout << result << "\r\n";

  mat[0][0] = 1;

  printarray(foo,5);
  cout << foo[0] << "\r\n";

  //example array of <array>
  array<int,3> myarray {10,20,30};

  for (int i=0; i < myarray.size(); ++i)
    ++myarray[i];

  for (int elem : myarray)
    cout << elem << '\n';

  return 0;
}

void printarray (int arg[], int length) {
  for (int n=0; n<length; ++n)
    cout << arg[n] << "\r\n";
  cout << "\r\n";

  arg[0] = 5;
}
