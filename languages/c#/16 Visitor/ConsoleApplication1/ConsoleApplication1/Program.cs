using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace ConsoleApplication1
{
    // 1. Add an accept(Visitor) method to the "element" hierarchy
    public interface Element
    {
        void accept(Visitor v);
    }

    public class This : Element
    {

        public void accept(Visitor v)
        {
            v.visit(this);
        }
        public string thiss()
        {
            return "This";
        }
    }

    public class That : Element
    {
        public void accept(Visitor v)
        {
            v.visit(this);
        }
        public string that()
        {
            return "That";
        }
    }

    public class TheOther : Element
    {
        public void accept(Visitor v)
        {
            v.visit(this);
        }
        public string theOther()
        {
            return "TheOther";
        }
    }

// 2. Create a "visitor" base class w/ a visit() method for every "element" type
    public interface Visitor
    {
            void visit(This e);
            void visit(That e);
            void visit(TheOther e);
    }

    // 3. Create a "visitor" derived class for each "operation" to do on "elements"
    public class UpVisitor : Visitor
    {
        public void visit(This e)
        {
            Console.WriteLine("do Up on " + e.thiss() + "\n");
        }
        public void visit(That e)
        {
            Console.WriteLine("do Up on " + e.that() + "\n");
        }
        public void visit(TheOther e)
        {
            Console.WriteLine("do Up on " + e.theOther() + "\n");
        }
    }
    public class DownVisitor : Visitor
    {
        public void visit(This e)
        {
            Console.WriteLine("do Down on " + e.thiss() + "\n");
        }
        public void visit(That e)
        {
            Console.WriteLine("do Down on " + e.that() + "\n");
        }
        public void visit(TheOther e)
        {
            Console.WriteLine("do Down on " + e.theOther() + "\n");
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            Element[] list = new Element[]{new This(), new That(), new TheOther()};
            UpVisitor up = new UpVisitor(); // 4. Client creates
            DownVisitor down = new DownVisitor(); //    "visitor" objects
            for (int i = 0; i < 3; i++)
                list[i].accept(up);

            for (int i = 0; i < 3; i++)
                list[i].accept(down);

            Console.Read();
        }
    }
}
