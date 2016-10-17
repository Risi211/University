#include <iostream>
class A{
public:
virtual void toto(){ std::cout<<"A::toto()"<<std::endl;}
virtual void tata(){ std::cout<<"A::tata()"<<std::endl;}
};
class B: public A{
public:
    	void toto(){ std::cout<<"B::toto()"<<std::endl;}
virtual void tata(){ std::cout<<"B::tata()"<<std::endl;}
};
int main(){
B b;
b.toto();
b.tata();

A *a = static_cast<A*> (&b);
a->toto();
a->tata();
}
