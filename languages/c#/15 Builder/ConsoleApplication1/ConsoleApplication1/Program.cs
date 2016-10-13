using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication1
{
    /* Car parts */
    public class Wheel
    {
        int size;
        public int Size
        {
            get { return size; }
            set { size = value; }
        }
    };

    public class Engine
    {
        int horsepower;
        public int HP
        {
            get { return horsepower; }
            set { horsepower = value; }
        }
    };

    public class Body
    {
        string shape;
        public string Shape
        {
            get { return shape; }
            set { shape = value; }
        }
    };

    /* Final product -- a car */
    public class Car
    {
        public Wheel[] wheels = new Wheel[4];
        public Engine engine;
        public Body body;

        public void specifications()
        {
            Console.WriteLine("body: " + body.Shape);
            Console.WriteLine("HP: " + engine.HP);
            Console.WriteLine("tire size: " + wheels[0].Size);
        }
    };

    /* Builder is responsible for constructing the smaller parts */
    public interface Builder
    {
        Wheel getWheel();
        Engine getEngine();
        Body getBody();
    };

    /* Director is responsible for the whole process */
    public class Director
    {
        Builder builder;

        public void setBuilder(Builder newBuilder)
        {
            builder = newBuilder;
        }

        public Car getCar()
        {
            Car car = new Car();
            car.body = builder.getBody();
            car.engine = builder.getEngine();

            car.wheels[0] = builder.getWheel();
            car.wheels[1] = builder.getWheel();
            car.wheels[2] = builder.getWheel();
            car.wheels[3] = builder.getWheel();

            return car;
        }
    };

    /* Concrete Builder for Jeep SUV cars */
    public class JeepBuilder : Builder
    {
        public Wheel getWheel()
        {
            Wheel wheel = new Wheel();
            wheel.Size = 22;
            return wheel;
        }

        public Engine getEngine()
        {
            Engine engine = new Engine();
            engine.HP = 400;
            return engine;
        }

        public Body getBody()
        {
            Body body = new Body();
            body.Shape = "SUV";
            return body;
        }
    };

/* Concrete builder for Nissan family cars */
    public class NissanBuilder : Builder
    {
        public Wheel getWheel()
        {
            Wheel wheel = new Wheel();
            wheel.Size = 16;
            return wheel;
        }

        public Engine getEngine()
        {
            Engine engine = new Engine();
            engine.HP = 85;
            return engine;
        }

        public Body getBody()
        {
            Body body = new Body();
            body.Shape = "hatchback";
            return body;
        }
    };

    class Program
    {
        static void Main(string[] args)
        {
            /* A director who controls the process */
            Director director = new Director();

            /* Build a Jeep */
            Console.WriteLine("build a jeep");
            director.setBuilder(new JeepBuilder()); // using JeepBuilder instance
            director.getCar().specifications();

            /* Build a Nissan */
            Console.WriteLine("build a nissan");
            director.setBuilder(new NissanBuilder()); // using NissanBuilder instance
            director.getCar().specifications();

            Console.Read();
        }
    }
}
