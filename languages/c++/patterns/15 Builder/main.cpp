#include <iostream>
#include <string>

using namespace std;

/* Car parts */
class Wheel
{
    public:
        int size;
};

class Engine
{
    public:
        int horsepower;
};

class Body
{
    public:
        std::string shape;
};

/* Final product -- a car */
class Car
{
    public:
        Wheel*   wheels[4];
        Engine*  engine;
        Body* body;
    
        void specifications()
        {
            cout << "body:" << body->shape << endl;
            cout << "engine horsepower:" << engine->horsepower << endl;
            cout << "tire size:" << wheels[0]->size << "'" << endl;
        }
};

/* Builder is responsible for constructing the smaller parts */
class Builder
{
    public:
        virtual Wheel* getWheel() = 0;
        virtual Engine* getEngine() = 0;
        virtual Body* getBody() = 0;
};

/* Director is responsible for the whole process */
class Director
{
    Builder* builder;

    public:
        void setBuilder(Builder* newBuilder)
        {
            builder = newBuilder;
        }

        Car* getCar()
        {
            Car* car = new Car();

            car->body = builder->getBody();

            car->engine = builder->getEngine();

            car->wheels[0] = builder->getWheel();
            car->wheels[1] = builder->getWheel();
            car->wheels[2] = builder->getWheel();
            car->wheels[3] = builder->getWheel();

            return car;
        }
};

/* Concrete Builder for Jeep SUV cars */
class JeepBuilder : public Builder
{
    public:
        Wheel* getWheel()
        {
            Wheel* wheel = new Wheel();
            wheel->size = 22;
            return wheel;
        }

        Engine* getEngine()
        {
            Engine* engine = new Engine();
            engine->horsepower = 400;
            return engine;
        }

        Body* getBody()
        {
            Body* body = new Body();
            body->shape = "SUV";
	    return body;
        }
};

/* Concrete builder for Nissan family cars */
class NissanBuilder : public Builder
{
    public:
        Wheel* getWheel()
        {
            Wheel* wheel = new Wheel();
            wheel->size = 16;
            return wheel;
        }

        Engine* getEngine()
        {
            Engine* engine = new Engine();
            engine->horsepower = 85;
            return engine;
        }

        Body* getBody()
        {
            Body* body = new Body();
            body->shape = "hatchback";
	    return body;
        }
};


int main()
{
    /* A director who controls the process */
    Director director;

    /* Build a Jeep */
    cout << "Jeep" << endl;
    director.setBuilder(new JeepBuilder); // using JeepBuilder instance
    director.getCar()->specifications();

    cout << endl;

    /* Build a Nissan */
    cout << "Nissan" << endl;
    director.setBuilder(new NissanBuilder); // using NissanBuilder instance
    director.getCar()->specifications();

    return 0;
}

