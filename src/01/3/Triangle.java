// Треугольник наследует класс Фигура
class Triangle extends Shape {
    private double a, b, c; // длины сторон

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }

    public void setA(double a) { this.a = a; }
    public void setB(double b) { this.b = b; }
    public void setC(double c) { this.c = c; }

    @Override void introduce() {
        System.out.println("Triangle, sides: " +
                "a = " + a + ", "+
                "b = " + b + ", "+
                "c = " + c);
    }

    @Override double getSquare() {
        double p = getBoundLength()/2;
        return Math.sqrt(p * (p-a) * (p-b) * (p-c));
    }

    @Override double getBoundLength() { return a+b+c; }
}