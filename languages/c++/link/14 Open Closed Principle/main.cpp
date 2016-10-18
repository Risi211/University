// multiple producers - single consumer
#include <iostream>           // std::cout
#include <string>

using namespace std;

class Shape{ //abstract
  public:
  virtual void draw() = 0;
};

class Rectangle : public Shape{
  public:
    void draw(){
	cout << "draw rectangle\r\n";
    }
};

class Circle : public Shape{
  public:
    void draw(){
	cout << "draw circle\r\n";
    }
};

class Viewer{
  public:
    void drawShape(Shape* s){
	s->draw();
    }
};

int main(){
  Viewer v;
  v.drawShape(new Rectangle);
  v.drawShape(new Circle);

  return 0;
}
