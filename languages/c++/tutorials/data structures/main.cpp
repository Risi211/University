// array of structures
#include <iostream>
#include <string>
#include <sstream>
using namespace std;

struct movies_t {
  string title;
  int year;
} films [3];

struct friends_t {
  string name;
  string email;
  movies_t favorite_movie;
} charlie, maria;

union mix_t {
  int l;
  struct {
    short hi;
    short lo;
    } s;
  char c[4];
} mix;

//anonymous union
struct book2_t {
  char title[50];
  char author[50];
  union {
    float dollars;
    int yen;
  };
} book2;

enum colors_t {
	black = 1, 
	blue, 
	green, 
	cyan, 
	red, 
};

enum class colors_Class {
	purple = 1, 
	yellow, 
	white
};

void printmovie (movies_t movie);

typedef unsigned int WORD;

int main ()
{
  string mystr;
  int n;

  for (n=0; n<3; n++)
  {
    cout << "Enter title: ";
    getline (cin,films[n].title);
    cout << "Enter year: ";
    getline (cin,mystr);
    stringstream(mystr) >> films[n].year;
  }

  cout << "\nYou have entered these movies:\n";
  for (n=0; n<3; n++)
    printmovie (films[n]);

  //pointers to structures
  movies_t * pmovie;
  pmovie = &films[0];
  cout << "Enter title: ";
  getline (cin, pmovie->title);
  cout << "Enter year: ";
  getline (cin, mystr);
  (stringstream) mystr >> pmovie->year;

  cout << "\nYou have entered:\n";
  cout << pmovie->title;
  cout << " (" << (*pmovie).year << ")\n";

  //nested structures
  friends_t * pfriends = &charlie;
  charlie.name = "ccc";
  maria.favorite_movie.title = "ttt";
  charlie.favorite_movie.year = 32;
  pfriends->favorite_movie.year = 34;

  //union
  mix.l = 80; //automatically change also hi, lw and char[4] fields, depending with the bits of 80.

  //anonymous union
  book2.dollars = 30.21f;

  //enum
  colors_t mycolor; 
  mycolor = blue;
  if (mycolor == green) 
	mycolor = red; 

  //enum class
  colors_Class myc = colors_Class::white; //need the scope reference ::
  if (myc == colors_Class::white) 
	myc = colors_Class::purple; 

  return 0;
}

void printmovie (movies_t movie)
{
  cout << movie.title;
  cout << " (" << movie.year << ")\n";
}
