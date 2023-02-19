// Квадрат наследует класс Фигура
class Square extends Shape {
    private double side; // длина стороны

    Square(double side) { this.side = side; }

    public double getSide() { return side; }
    public void setSide(double side) { this.side = side; }

    @Override
    void introduce() {
        System.out.println("Square with side of " + side + ":");
    }

    // собственный метод квадрата - вычисление длины диагонали
    double getDiagonal() { return Math.sqrt(2)*side; }

    @Override double getSquare() { return side*side; }

    @Override double getBoundLength() { return 4*side; }

    // переопределить вывод свойств добавлением информации о длине диагонали
    @Override
    void showMeasures() {
        super.showMeasures();
        System.out.printf("\tdiagonal length: %.2f", getDiagonal());
    }
}