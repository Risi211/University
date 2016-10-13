using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    public interface Image
    {
        void Draw();
    }
    public class RealImage : Image
    {
        string path;
        public RealImage(string path)
        {
            this.path = path;
            LoadImage();
        }
        public void Draw()
        {
            Console.WriteLine("draw " + path);
        }
        public void LoadImage()
        {
            Console.WriteLine("load image from disk: potential heavy operation");
        }
    }
    public class ProxyImage : Image
    {
        RealImage img;
        string path;
        public ProxyImage(string path)
        {
            this.path = path;
        }
        public void Draw()
        {
            if (img == null)
            {
                img = new RealImage(path);
            }
            img.Draw();
        }
    }
    class Program
    {
        static void Main(string[] args)
        {
            Image img = new ProxyImage("img1");
            img.Draw();
            img.Draw();

            Console.Read();
        }
    }
}
