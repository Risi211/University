#include <iostream>
#include <vector>
#include <ctime>
using namespace std;

enum class ACTION_LIST
{
	ADD,
	SUBTRACT,
	MULTIPLY
};

class IReciever
{
	public:
	    virtual void SetAction(ACTION_LIST action) = 0;
	    virtual int GetResult() = 0;    
};


class ACommand
{
	protected:
	    IReciever *pReciever_;

	public:
	    ACommand(IReciever *reciever) : pReciever_(reciever) {}
	    virtual int Execute() = 0; //change for each command
};


class AddCommand : public ACommand
{
	public:
	    AddCommand(IReciever *reciever) : ACommand(reciever) {}
	    int Execute(){
	        pReciever_->SetAction(ACTION_LIST::ADD);
	        return pReciever_->GetResult();
	    }
};

class SubtractCommand : public ACommand
{
	public:
	    SubtractCommand(IReciever *reciever): ACommand(reciever) {}
	    int Execute(){
	        pReciever_->SetAction(ACTION_LIST::SUBTRACT);
	        return pReciever_->GetResult();
	    }
};

class MultiplyCommand : public ACommand
{
	public:
	    MultiplyCommand(IReciever *reciever) : ACommand(reciever) {}
	    int Execute(){
	        pReciever_->SetAction(ACTION_LIST::MULTIPLY);
        	return pReciever_->GetResult();
	    }
};


class Calculator : public IReciever
{
	private:
	    int x_;
	    int y_;
	    ACTION_LIST currentAction;

	public:
	    Calculator(int x, int y) :x_(x), y_(y) {}
	    void SetAction(ACTION_LIST action){
	        currentAction = action;
	    }
	    int GetResult(){
	        int result;
        	if (currentAction == ACTION_LIST::ADD){
	            result = x_ + y_;
		}
	        else if (currentAction == ACTION_LIST::MULTIPLY){
	            result = x_ * y_;
		}
	        else{
	            result = x_ - y_;
		}
	        return result;
	    }
};

int main(){

	//init
	Calculator calculator(30,20);
	AddCommand addCmd(&calculator);
	SubtractCommand subCmd(&calculator);
	MultiplyCommand mulCmd(&calculator);	

	//command
	ACommand *command; //This will be used to invoke commands

	//simulate user behavior
	//press +
	command = &addCmd;
	//press -
	command = &subCmd;
	//press *
	command = &mulCmd;

	//execute command
	int result = command->Execute();

	cout << "result = " << result << "\r\n";


}
