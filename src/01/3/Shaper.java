// 3. Написать пример кода, который реализует принцип полиморфизма,
// на примере фигур — круг, квадрат, треугольник.

class Shaper {
    public static void main(String[] args) {
        // создать треугольник, круг и квадрат...
        Triangle t = new Triangle(15d, 25d, 35d);
        Circle c = new Circle(100d);
        Square s = new Square(50d);
        // ...поместить их в массив фигур и вывести информацию о свойствах каждой
        Shape[] z = new Shape[] { t, c, s };
        for (Shape shape : z) {
            shape.introduce();
            shape.showMeasures();
        }
    }
}