// arrays example
#include <iostream>
#include <array>
using namespace std;

int foo [] = {16, 2, 77, 40, 12071};
int n, result=0;

//matrix 2d
int mat [5][3]; //it is like a 1d array 5*3

void printarray (int arg[], int length);
void increment_all (int* start, int* stop);
void print_all (const int* start, const int* stop);
void increase (void* data, int psize);
int addition (int a, int b);
int subtraction (int a, int b);
int operation (int x, int y, int (*functocall)(int,int));

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

  //pointer examples
  int firstvalue, secondvalue;
  int * p1, * p2;

  p1 = &firstvalue;  // p1 = address of firstvalue
  p2 = &secondvalue; // p2 = address of secondvalue
  *p1 = 10;          // value pointed to by p1 = 10
  *p2 = *p1;         // value pointed to by p2 = value pointed to by p1
  p1 = p2;           // p1 = p2 (value of pointer is copied)
  *p1 = 20;          // value pointed to by p1 = 20
  cout << "firstvalue is " << firstvalue << '\n';
  cout << "secondvalue is " << secondvalue << '\n';

  //arrays and pointers
  int numbers[5];
  int * p;
  p = numbers;  *p = 10;
  p++;  *p = 20;
  p = &numbers[2];  *p = 30;
  p = numbers + 3;  *p = 40;
  p = numbers;  *(p+4) = 50;
  for (int n=0; n<5; n++)
    cout << numbers[n] << ", ";
  cout << "\r\n";


  //const pointers
  //const int * p22 = &y;
  //x = *p22;          // ok: reading p
  //*p = x;          // error: modifying p, which is const-qualified 
  int numbers2[] = {10,20,30};
  increment_all (numbers2,numbers2+3);
  print_all (numbers2,numbers2+3);

  //pointers to pointers
  char a;
  char * b;
  char ** c;
  a = 'z';
  b = &a;
  c = &b;	

  //void pointers
  char a2 = 'x';
  int b2 = 1602;
  increase (&a2,sizeof(a2));
  increase (&b2,sizeof(b2));
  cout << a2 << ", " << b2 << '\n';

  //pointers to functions
  int m,n;
  int (*minus)(int,int) = subtraction;
  m = operation (7, 5, addition);
  n = operation (20, m, minus);
  cout <<n;

  //new and delete
  int * arr;
  arr = new int [5];
  arr = new (nothrow) int [5]; //no throw exception if some errors are encountered when assigning memory
  if (arr == nullptr) {
    // error assigning memory. Take measures.
  }
  delete[] arr; //created using new [size]

/*
C++ integrates the operators new and delete for allocating dynamic memory. But these were not available in the C language; instead, it used a library solution, with the functions malloc, calloc, realloc and free, defined in the header <cstdlib> (known as <stdlib.h> in C). The functions are also available in C++ and can also be used to allocate and deallocate dynamic memory.

Note, though, that the memory blocks allocated by these functions are not necessarily compatible with those returned by new, so they should not be mixed; each one should be handled with its own set of functions or operators.
*/
	

  return 0;
}

void printarray (int arg[], int length) {
  for (int n=0; n<length; ++n)
    cout << arg[n] << "\r\n";
  cout << "\r\n";

  arg[0] = 5;
}

void increment_all (int* start, int* stop)
{
  int * current = start;
  while (current != stop) {
    ++(*current);  // increment value pointed
    ++current;     // increment pointer
  }
}

//you can increment pointer even if it is const, but you cannot change the value pointed by it
void print_all (const int* start, const int* stop)
{
  const int * current = start;
  while (current != stop) {
    cout << *current << '\n';
    ++current;     // increment pointer
  }
}

//void pointers
void increase (void* data, int psize)
{
  if ( psize == sizeof(char) )
  { 
	char* pchar; 
	pchar=(char*)data; 
	++(*pchar); 
  }
  else if (psize == sizeof(int) )
  { 
	int* pint; 
	pint=(int*)data; 
	++(*pint); 
  }
}

int addition (int a, int b)
{ 
  return (a+b); 
}

int subtraction (int a, int b)
{
  return (a-b); 
}

int operation (int x, int y, int (*functocall)(int,int))
{
  int g;
  g = (*functocall)(x,y);
  return (g);
}
