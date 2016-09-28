#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Image{ //abstract class
  public:
    virtual void draw() = 0;
};

class RealImage : public Image{
  private:
    string path;
  public:
    RealImage(string p) : path(p) {
	loadImage();
    }
    void draw(){
	cout << "image draw\r\n";
    }
    void loadImage(){
	cout << "load image from disk: potential heavy operation\r\n";
    }
};

class ProxyImage : public Image{
  private:
    RealImage* img;
    string path;
  public:
    ProxyImage(string p) : path(p) {}
    void draw(){
	if(img == NULL){
		img = new RealImage(path);
	}
	img->draw();
    }
};

int main()
{
  Image* img = new ProxyImage("path");
  img->draw(); //load from disk
  img->draw(); //already loaded

  return 0;
}
