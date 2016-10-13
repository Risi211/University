using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public interface Shape
    {
        void Draw();
    }
    public class Rectangle : Shape
    {
        public void Draw()
        {
            Console.WriteLine("draw rectangle");
        }
    }
    public class Circle : Shape
    {
        public void Draw()
        {
            Console.WriteLine("draw circle");
        }
    }
    public class Square : Shape
    {
        public void Draw()
        {
            Console.WriteLine("draw square");
        }
    }
    public interface Colour
    {
        void Fill();
    }
    public class Red : Colour
    {
        public void Fill()
        {
            Console.WriteLine("Fill red");
        }
    }
    public class Green : Colour
    {
        public void Fill()
        {
            Console.WriteLine("Fill green");
        }
    }
    public class Blue : Colour
    {
        public void Fill()
        {
            Console.WriteLine("Fill blue");
        }
    }
    public interface AbstractFactory
    {
        Shape GetShape(string s);
        Colour GetColour(string c);
    }
    public class ShapeFactory : AbstractFactory
    {
        public Colour GetColour(string c)
        {
            return null;
        }

        public Shape GetShape(string s)
        {
            switch (s)
            {
                case "r":
                    {
                        return new Rectangle();
                    }
                case "s":
                    {
                        return new Square();
                    }
                case "c":
                    {
                        return new Circle();
                    }
                default:
                    {
                        return null;
                    }
            }
        }
    }
    public class ColourFactory : AbstractFactory
    {
        public Colour GetColour(string c)
        {
            switch (c)
            {
                case "r":
                    {
                        return new Red();
                    }
                case "g":
                    {
                        return new Green();
                    }
                case "b":
                    {
                        return new Blue();
                    }
                default:
                    {
                        return null;
                    }
            }        
        }   
    public Shape GetShape(string s)
        {
            return null;
        }
    }
    public class FactoryProducer
    {
        private FactoryProducer() { }
        public static AbstractFactory GetFactoryInstance(string f)
        {
            switch (f)
            {
                case "s":
                    {
                        return new ShapeFactory();
                    }
                case "c":
                    {
                        return new ColourFactory();
                    }
                default:
                    {
                        return null;
                    }
            }
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            AbstractFactory f1 = FactoryProducer.GetFactoryInstance("s");
            AbstractFactory f2 = FactoryProducer.GetFactoryInstance("c");

            Shape s1 = f1.GetShape("r");
            Shape s2 = f1.GetShape("c");
            Shape s3 = f1.GetShape("s");

            Colour c1 = f2.GetColour("r");
            Colour c2 = f2.GetColour("g");
            Colour c3 = f2.GetColour("b");

            s1.Draw();
            s2.Draw();
            s3.Draw();
            c1.Fill();
            c2.Fill();
            c3.Fill();

            Console.Read();
        }
    }
}
