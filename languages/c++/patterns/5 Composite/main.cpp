#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Component{
  public:
    virtual void traverse() = 0;
};

class Leaf : public Component{
  private:
    string name;
  public:
    Leaf(string n) : name(n) {}
    void traverse(){
	cout << name << "\r\n";
    }
};

class Composite : public Component{
  private:
    string name;
    vector< Component* > children;
  public:
    Composite(string n) : name(n) {}
    void insert(Component* c){
	children.push_back(c);
    }
    void traverse(){
	cout << name << "\r\n";
	int length = children.size();
	for(int i = 0; i < length; i++){
		children[i]->traverse();
	}
    }
};

int main()
{
  Composite root ("root");
  Composite sub ("sub");
  sub.insert(new Leaf("s1"));
  sub.insert(new Leaf("s2"));
  sub.insert(new Leaf("s3"));
  root.insert(&sub);
  root.insert(new Leaf("c2"));
  root.insert(new Leaf("c3"));
  root.insert(new Leaf("c4"));

  root.traverse();

  return 0;
}
