using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public enum ACTION_LIST
    {
        ADD,
		SUBTRACT,
		MULTIPLY
    }

    public interface IReciever
    {
	    void SetAction(ACTION_LIST action);
	    int GetResult();    
	}


    public abstract class ACommand
    {        
	    protected IReciever pReciever_;
	    public ACommand(IReciever reciever)
        {
            this.pReciever_ = reciever;
        }
        public abstract int Execute(); //change for each command
	}


    public class AddCommand : ACommand
	{	
        public AddCommand(IReciever reciever) : base(reciever) { }
        public override int Execute()
		{
			pReciever_.SetAction(ACTION_LIST.ADD);
			return pReciever_.GetResult();
		}
	}

	public class SubtractCommand : ACommand
	{
		public SubtractCommand(IReciever reciever): base(reciever) { }
		public override int Execute()
		{
			pReciever_.SetAction(ACTION_LIST.SUBTRACT);
			return pReciever_.GetResult();
		}
	}

	public class MultiplyCommand : ACommand
	{
		public MultiplyCommand(IReciever reciever) : base(reciever) { }
		public override int Execute()
		{
			pReciever_.SetAction(ACTION_LIST.MULTIPLY);
			return pReciever_.GetResult();
		}
	}


	public class Calculator : IReciever
	{
		int x_;
		int y_;
		ACTION_LIST currentAction;

		public Calculator(int x, int y)
		{
			x_ = x;
			y_ = y;
		}
		public void SetAction(ACTION_LIST action)
		{
			currentAction = action;
		}
		public int GetResult()
		{
			int result;
			if (currentAction == ACTION_LIST.ADD)
			{
				result = x_ + y_;
			}
			else if (currentAction == ACTION_LIST.MULTIPLY)
			{
				result = x_ * y_;
			}
			else
			{
				result = x_ - y_;
			}
			return result;
		}
	}

    class Program
    {
        static void Main(string[] args)
        {
        //init
        Calculator calculator = new Calculator(30, 20);
        AddCommand addCmd = new AddCommand(calculator);
        SubtractCommand subCmd = new SubtractCommand(calculator);
        MultiplyCommand mulCmd = new MultiplyCommand(calculator);

        //command
        ACommand command; //This will be used to invoke commands

        //simulate user behavior
        //press +
        command = addCmd;
            //execute command
            int result = command.Execute();
            Console.WriteLine("result = " + result + "\r\n");

            //press -
            command = subCmd;
            //execute command
            result = command.Execute();
            Console.WriteLine("result = " + result + "\r\n");
        //press *
            command = mulCmd;
            //execute command
            result = command.Execute();
            Console.WriteLine("result = " + result + "\r\n");

            Console.Read();

        }
}
}
