// classes example
#include <iostream>
using namespace std;

class Output {
  public:
    Output() { cout << "Output base constructor called\r\n";}
    static void print (int i){
        cout << "integer: " << i << "\r\n";
    };
};

class Polygon {
  protected:
    int width, height;
  public:
    Polygon() { cout << "Polygon base constructor called\r\n";}
    Polygon(int a, int b) : width(a), height(b) {cout << "Polygon base constructor called with parameters\r\n";}
    void set_values (int a, int b) { width=a; height=b;}
    int getWidth(void) const {return width;}
    int getHeight(void) const {return height;}
    virtual int area () { return 0; } //to be overrided in the derived classes
 };

class Square; //to be defined here, because it is used into the Rectangle class

class Rectangle : public Polygon, public Output {
  private:
    int* ptr; //put only for example
  public:
    static int init;
    Rectangle (){ init++; }; //default constructor
    //Rectangle (int x, int y) : width(x), height(y) { init++; } //init constructor
    Rectangle (int x, int y) : Polygon(x,y) { } //init constructor
    // destructor:
    ~Rectangle () {
	//to use only if you use the pointer, otherwise it is not NULL
	//and it has a value that cannot be used by the destructor,it gives error when the program ends
	//	delete ptr;
    }
    Rectangle operator +(const Rectangle&);
    int area(void) {return width*height;}
    friend Rectangle duplicate (const Rectangle&); //friend function, it can access the private fields of any rectangle object
    void convert (Square a);
};

//init static member outside the class, like the other members
int Rectangle::init = 0;

//overload operator + class Rectangle
Rectangle Rectangle::operator+(const Rectangle& p1){
  Rectangle rect;
  rect.set_values(this->getWidth() + p1.getWidth(), this->getHeight() + p1.getHeight());
  return rect;
}

//it can access private fields of Rectangle object because it is its friend
Rectangle duplicate (const Rectangle& param)
{
  Rectangle res;
  res.set_values(param.width * 2, param.height * 2);
  return res;
}

class Square {
  friend class Rectangle;
  private:
    int side;
  public:
    Square (int a) : side(a) {}
};

void Rectangle::convert (Square a) {
  width = a.side;
  height = a.side;
}

//template class
template <class T>
class mypair {
    T a, b;
  public:
    mypair (T first, T second)
      {a=first; b=second;}
    T getmax ();
};

class Triangle: public Polygon, public Output {
  public:
    int area ()
      { return width * height / 2; }
  };

template <class T>
T mypair<T>::getmax ()
{
  T retval;
  retval = a>b? a : b;
  return retval;
}

int main () {
  Rectangle rect;  //default constructor
  Rectangle rectb (5,6); //init constructor
  //Rectangle rectc(); // function declaration (default constructor NOT called)
  //Rectangle rectd{}; // default constructor called

  rect.set_values (3,4);
  cout << "area: " << rect.area() << "\r\n";

  //pointers to classes
  Rectangle * foo, * bar, * baz;
  foo = &rect;
  bar = new Rectangle (5, 6); //pointer to a single object
  baz = new Rectangle[2] { {2,5}, {3,6} }; //pointer to array of objects
  cout << "obj's area: " << rectb.area() << '\n';
  cout << "*foo's area: " << foo->area() << '\n';  //use of -> single object pointer
  cout << "*bar's area: " << bar->area() << '\n';  //use of -> single object pointer
  cout << "baz[0]'s area:" << baz[0].area() << '\n';  //use of [i]. array object pointer
  cout << "baz[1]'s area:" << baz[1].area() << '\n';       
  delete bar;
  delete[] baz;

  //operator +
  Rectangle sum = (*foo) + rectb;
  cout << "sum area: " << sum.area() << "\r\n";

  //print static count
  cout << "count static: " << Rectangle::init << "\r\n";

  //friend function
  Rectangle r2 = duplicate (rect);
  cout << "duplicate area: " << r2.area() << '\n';

  //friend class
  Square sqr (4);
  rect.convert(sqr);
  cout << "rect square friend area: " << rect.area() << "\r\n";

  //inheritance example, base constructors called
  Triangle trgl;
  rect.set_values (4,5);
  trgl.set_values (4,5);
  cout << "rect area: " << rect.area() << "\r\n";
  cout << "triangle area: " << trgl.area() << "\r\n";
  rect.print(rect.area());
  Triangle::print(trgl.area());

  //polymorphism, pointers to base class (like java interfaces)
  Polygon * ppoly1 = &rect;
  Polygon * ppoly2 = &trgl;
  ppoly1->set_values (4,5);
  ppoly2->set_values (4,5);
  cout << "polygon1 area: " << ppoly1->area() << '\n';
  cout << "polygon2 area: " << ppoly2->area() << '\n';

  Polygon * ppoly3 = new Rectangle (6,6);
  Polygon * ppoly4 = new Triangle;
  ppoly4->set_values (6,6);
  cout << "polygon3 area: " << ppoly3->area() << '\n';
  cout << "polygon4 area: " << ppoly4->area() << '\n';

  //class template
  mypair <int> myobject (100, 75);
  cout << "pair template: " << myobject.getmax() << "\r\n";   

  return 0;
}

/*
Classes defined with struct and union

Classes can be defined not only with keyword class, but also with keywords struct and union.

The keyword struct, generally used to declare plain data structures, can also be used to declare classes that have member functions, with the same syntax as with keyword class. The only difference between both is that members of classes declared with the keyword struct have public access by default, while members of classes declared with the keyword class have private access by default. For all other purposes both keywords are equivalent in this context.

Conversely, the concept of unions is different from that of classes declared with struct and class, since unions only store one data member at a time, but nevertheless they are also classes and can thus also hold member functions. The default access in union classes is public.
*/
