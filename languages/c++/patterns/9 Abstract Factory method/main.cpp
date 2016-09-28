#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Shape{ //abstract class
  public:
    virtual string draw() = 0;
};

class Rectangle : public Shape{
  public:
    string draw(){return "Rectangle draw";}
};

class Square : public Shape{
  public:
    string draw(){return "Square draw";}
};

class Circle : public Shape{
  public:
    string draw(){return "Circle draw";}
};

class Colour{ //abstract class
  public:
    virtual string fill() = 0;
};

class Red : public Colour{
  public:
    string fill(){return "Red fill";}
};

class Green : public Colour{
  public:
    string fill(){return "Green fill";}
};

class Blue : public Colour{
  public:
    string fill(){return "Blue fill";}
};

class AbstractFactory{ //abstract factory class
  public:
    virtual Shape* getShape(string s) = 0;
    virtual Colour* getColour(string c) = 0;
};

class ShapeFactory : public AbstractFactory{ //factory class
  public:
    Shape* getShape(string s){
	if(s == "rect"){
		return new Rectangle;		
	}
	if(s == "square"){
		return new Square;		
	}
	if(s == "circle"){
		return new Circle;
	}
    }
    //null method, not implemented in this specific factory
    Colour* getColour(string c){
	return NULL;
    }
};

class ColourFactory : public AbstractFactory{ //factory class
  public:
    Colour* getColour(string c){
	if(c == "r"){
		return new Red;		
	}
	if(c == "g"){
		return new Green;		
	}
	if(c == "b"){
		return new Blue;		
	}
    }
    //null method, not implemented in this specific factory
    Shape* getShape(string s){
	return NULL;
    }
};

class FactoryProducer{ //produce an instance of one factory
  public:
    static AbstractFactory* getFactory(string choice){
	if(choice == "shape"){
		return new ShapeFactory;
	}
	if(choice == "colour"){
		return new ColourFactory;
	}
    }
};

int main()
{
 
  AbstractFactory* a1 = FactoryProducer::getFactory("shape");
  AbstractFactory* a2 = FactoryProducer::getFactory("colour");
 
  Shape* s1 = a1->getShape("rect");
  Shape* s2 = a1->getShape("square");
  Shape* s3 = a1->getShape("circle");

  Colour* c1 = a2->getColour("r");
  Colour* c2 = a2->getColour("g");
  Colour* c3 = a2->getColour("b");

  cout << s1->draw() << ", " << s2->draw() << ", " << s3->draw() << "\r\n";
  cout << c1->fill() << ", " << c2->fill() << ", " << c3->fill() << "\r\n";

  return 0;
}
