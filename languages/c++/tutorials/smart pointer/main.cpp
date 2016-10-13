// my first program in C++
#include <iostream>
#include <string>
#include <memory>
using namespace std;

class A{
	private:
		int v;
    public:
	A(){}
	A(int a) : v(a) { cout << "arg: " << a << "\r\n";}
	void ppp(){ cout << v << "\r\n";}
};

class B : public A {
	public:
		B(){}
		B(int a){}
};

void doIt(weak_ptr<A> wp){
	shared_ptr<A> sp = wp.lock(); // get shared_ptr from weak_ptr
	if(sp) //check if weak pointer was valid
		sp->ppp(); // tell the Thing to do something
	else
		cout << "The Thing is gone!" << endl;
}

void doIt2(weak_ptr<A> wp){
	shared_ptr<A> sp (wp); // get shared_ptr from weak_ptr
	//if it is not valid, it throws an exception bad_weak_ptr
	sp->ppp(); // tell the Thing to do something
}

bool isThere(weak_ptr<A> wp) {
	if(wp.expired()) {
		cout << "Not There!" << endl;
		return false;
	}
	return true;
}

shared_ptr<A> better_idea()
{
	shared_ptr<A> sp(new B(43));
	return sp;
}

A * bad_idea()
{
	shared_ptr<A> sp; // an empty smart pointer
	A * raw_ptr = new A;
	//sp = raw_ptr; // disallowed - compiler error !!!
	return raw_ptr;  // danger!!! - caller could make a mess with this!
}

A * another_bad_idea()
{
	shared_ptr<A> sp(new A);
	//A * raw_ptr  = sp; // No implicit conversion! 	//Compiler error!
	A * raw_ptr = sp.get();   // you must want it, but why?

	return raw_ptr;  // danger!!! - caller could make a mess with this!
}



int main()
{

	//allocate shared pointer
	shared_ptr<B> dp1(new B);
	shared_ptr<A> bp1 = dp1;
	shared_ptr<A> bp2(dp1);
	shared_ptr<A> bp3(new B);


	shared_ptr<A> sp(new A(12));	//2 allocations of memory! memory manager and object managed
	sp->ppp();
	//memory
	shared_ptr<A> sp3(make_shared<A>(5)); // only one allocation!
	//parameters of A are passed into the constructor of make:shared<T>(...args...)

	A* raw_ptr = sp.get(); // you must want it, but why?
	//Thing * raw_ptr  = sp; // No implicit conversion! 
	//return raw_ptr; // danger!!! - caller could make a mess with this!

	shared_ptr<A> sp2(new B);		
	// if static_cast<Derived *>(base_ptr.get()) is valid, then the following is valid:
	shared_ptr<B> sb = static_pointer_cast<B>(sp2);
	if(sb == sp2)
	{
		cout << "equals\r\n";
	}


	//weak pointers
	weak_ptr<A> wp1(sp);   // construct wp1 from a shared_ptr
	weak_ptr<A> wp2;       // an empty weak_ptr - points to nothing
	wp2 = sp;                  // wp2 now points to the new Thing
	weak_ptr<A> wp3 (wp2); // construct wp3 from a weak_ptr
	weak_ptr<A> wp4; 
	wp4 = wp2;                 // wp4 now points to the new Thing.

	doIt(wp4); //example function
	
	cout << isThere(wp4) << " asd\r\n";
	sp = nullptr;
	cout << isThere(wp4) << " asd\r\n";
	
	try{
		doIt2(wp4);
	}
	catch(bad_weak_ptr& asd)
	{
		cout << "A Thing (or something else) has disappeared!" << endl;
	}

	//unique pointer
	unique_ptr<A> pa(new A(23)); // pa owns the Thing
	pa->ppp();
	
	unique_ptr<A> p1 (new A); // p1 owns the Thing
	//unique_ptr<A> p2(p1);  // error - copy construction is not allowed.
	unique_ptr<A> p3;      // an empty unique_ptr;
	//p3 = p1;                   // error, copy assignment is not allowed.

	shared_ptr<A> p5 = better_idea();
	
	unique_ptr<A> k1(new A(89)); // k1 owns the Thing
	unique_ptr<A> k2; // k2 owns nothing
	// invoke move assignment explicitly
	k2 = std::move(k1); // now k2 owns it, k1 owns nothing
	// invoke move construction explicitly
	unique_ptr<A> k3(std::move(k2)); // now k3 owns it, k2 and k1 own nothing
}
