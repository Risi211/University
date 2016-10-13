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
    public class Viewer
    {
        public void DrawShape(Shape s)
        {
            s.Draw();
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            Viewer v = new Viewer();
            v.DrawShape(new Rectangle());
            v.DrawShape(new Circle());

            Console.Read();
        }
    }
}
