//import com.sun.xml.internal.ws.api.pipe.Engine;

//2. Описать ошибки в коде (см. задание в методичке) и предложить варианты оптимизации.
interface Moveable {
    void move();
}

interface Stopable {
    void stop();
}

abstract class Car {
    // используемый класс Engine отсутствует;
    // решение: для определения его нужно
    //  - либо создать,
    //  - либо (если используется какой-то предопределенный класс) импортировать (см. import выше)
    public Engine engine;
    private String color;
    private String name;

    protected void start() {
        System.out.println("Car starting");
    }

    abstract void open();

    public Engine getEngine() {
        return engine;
    }
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class LightWeightCar extends Car implements Moveable {
    @Override
    void open() { System.out.println("Car is open"); }

    @Override
    public void move() { System.out.println("Car is moving"); }
}

// 1. класс может реализовывать интерфейсы, но не наследовать их;
// наследование допустимо для классов ИЛИ интерфейсов;
// решение: class Lorry extends Car implements Moveable, Stopable
// 2. поскольку данный класс наследуется от абстрактного, он должен
//    - либо содержать (пере)определения абстрактных методов родительского класса
//      (если такие есть, как в данном случае - метод open());
//    - либо также быть объявлен абстрактным, если методы в нем не переопределены
// решения:
//    - @Override void open() { }
//    - abstract class Lorry extends Car implements Moveable, Stopable
class Lorry extends Car, Moveable, Stopable {
    // это не ошибка, скорее - "плохой тон", поскольку
    // использование аннотации @Override не является обязательным,
    // но оно дает понять, что далее следует переопределение метода
    // интерфейса или родительского класса, а не собственный метод данного класса
    // решение: перед сигнатурами методов добавить аннотацию @Override
    public void move() { System.out.println("Car is moving"); }
    public void stop() { System.out.println("Car is stop"); }
}