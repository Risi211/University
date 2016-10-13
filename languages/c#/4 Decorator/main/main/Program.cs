using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace main
{
    public interface Window
    {
        void draw();
    }
    public class SimpleWindow : Window
    {
        public void draw()
        {
            Console.WriteLine("Draw Simple Window");
        }
    }
    public class WindowDecorator : Window
    {
        protected Window win;
        public WindowDecorator(Window w)
        {
            win = w;
        }
        public virtual void draw()
        {
            win.draw();
        }        
    }
    public class VerticalScrollWindow : WindowDecorator
    {
        public VerticalScrollWindow(Window w) : base(w)
        {
            
        }
        private void DrawVerticalScroll()
        {
            Console.WriteLine("Draw Vertical Scroll");
        }
        public override void draw()
        {
            base.draw();
            DrawVerticalScroll();
        }
    }
    public class HorizontalScrollWindow : WindowDecorator
    {
        public HorizontalScrollWindow(Window w) : base(w)
        {

        }
        private void DrawHorizontalScroll()
        {
            Console.WriteLine("Draw Horizontal Scroll");
        }
        public override void draw()
        {
            base.draw();
            DrawHorizontalScroll();
        }
    }
    public class Program
    {
        static void Main(string[] args)
        {
            Window w = new HorizontalScrollWindow(new VerticalScrollWindow(new SimpleWindow()));
            w.draw();
            Console.Read();
        }
    }
}
