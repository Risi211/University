#include <iostream>
#include <string>
#include <vector>

using namespace std;

class MsgService{ //abstract
  public:
    virtual void send(string s) = 0;
};

class EmailServiceImpl : public MsgService{
  public:
    void send(string s){
	cout << "send email: " << s << "\r\n";
    }
};

class SMSServiceImpl : public MsgService{
  public:
    void send(string s){
	cout << "send sms: " << s << "\r\n";
    }
};

class App{ //abstract
  public:
    virtual void processMsg(string s) = 0;
};

class MyApp : public App{
  private:
    MsgService* service;
  public:
    MyApp(MsgService* ms) : service(ms) {}
    void processMsg(string s){
	service->send(s);
    }
};

class Injector{ //abstract
  public:
    virtual App* getApp() = 0;
};

class EmailInjector : public Injector{ 
  public:
    App* getApp(){
	return new MyApp(new EmailServiceImpl);
    }
};

class SMSInjector : public Injector{ 
  public:
    App* getApp(){
	return new MyApp(new SMSServiceImpl);
    }
};

int main()
{
  Injector* inj = new EmailInjector;
  App* a1 = inj->getApp();
  a1->processMsg("a1");
  delete a1;
  delete inj;
  Injector* inj2 = new SMSInjector;
  App* a2 = inj2->getApp();
  a2->processMsg("a2");
  delete a2;
  delete inj2;

  return 0;
}
