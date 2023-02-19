// Круг наследует класс Фигура
class Circle extends Shape {
    private double radius; // радиус

    Circle(double radius) { this.radius = radius; }

    public double getRadius() { return radius; }

    public void setRadius(double radius) { this.radius = radius; }

    @Override void introduce() {
        System.out.println("Circle with radius of "+radius+":");
    }

    @Override double getSquare() { return Math.PI*radius*radius; }

    @Override double getBoundLength() { return 2*Math.PI*radius; }
}