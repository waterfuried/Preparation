<<<<<<< HEAD
// Круг наследует класс Фигура
class Circle extends Shape {
    private double radius; // радиус
=======
// ���� ��������� ����� ������
class Circle extends Shape {
    private double radius; // ������
>>>>>>> 3df2b0bf9be82d8e9a64ea6af5b028971eb97aa3

    Circle(double radius) { this.radius = radius; }

    public double getRadius() { return radius; }

    public void setRadius(double radius) { this.radius = radius; }

    @Override void introduce() {
        System.out.println("Circle with radius of "+radius+":");
    }

    @Override double getSquare() { return Math.PI*radius*radius; }

    @Override double getBoundLength() { return 2*Math.PI*radius; }
}