#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Window{
  public:
    virtual void draw() = 0;
};

class SimpleWindow : public Window{
  public:
    void draw(){
	cout << "draw window\r\n";
    }
};

class WindowDecorator : public Window{
  protected:
    Window* w;
  public:
    WindowDecorator(Window* wi) : w(wi) {}
    void draw(){
	w->draw();
    }
};

class VerticalScroll : public WindowDecorator{
  public:
    VerticalScroll(Window* w) : WindowDecorator(w) {}
    void drawVerticalScroll(){
	cout << "draw vertical scroll\r\n";
    }
    void draw() {
	WindowDecorator::draw(); //super.draw() in java
	drawVerticalScroll();
    }
};

class HorizontalScroll : public WindowDecorator{
  public:
    HorizontalScroll(Window* w) : WindowDecorator(w) {}
    void drawHorizontalScroll(){
	cout << "draw horizontal scroll\r\n";
    }
    void draw() {
	WindowDecorator::draw(); //super.draw() in java
	drawHorizontalScroll();
    }
};

int main()
{
  Window* w = new HorizontalScroll(new VerticalScroll(new SimpleWindow));
  w->draw();

  return 0;
}
