using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public interface Component
    {
        void Traverse();
    }

    public class Leaf : Component
    {
        string name;
        public Leaf(string name)
        {
            this.name = name;
        }
        public void Traverse()
        {
            Console.WriteLine(name);
        }
    }

    public class Composite : Component
    {
        List<Component> children;
        string name;
        public Composite(string name)
        {
            this.name = name;
            children = new List<Component>();
        }
        public void InsertComponent(Component c)
        {
            children.Add(c);
        }
        public void Traverse()
        {
            Console.WriteLine(name);
            for (int i = 0; i < children.Count; i++)
            {
                children.ElementAt<Component>(i).Traverse();
            }
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            Composite root = new Composite("root");
            Composite sub = new Composite("sub");
            sub.InsertComponent(new Leaf("s1"));
            sub.InsertComponent(new Leaf("s2"));
            sub.InsertComponent(new Leaf("s3"));
            root.InsertComponent(sub);
            root.InsertComponent(new Leaf("c2"));
            root.InsertComponent(new Leaf("c3"));
            root.InsertComponent(new Leaf("c4"));

            root.Traverse();

            Console.Read();
        }
    }
}
