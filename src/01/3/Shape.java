// абстрактный класс Фигура
abstract class Shape {
    // вычисление площади фигуры
    abstract double getSquare();

    // вычисление длины внешней границы фигуры
    abstract double getBoundLength();

    // отображение параметров фигуры
    abstract void introduce();

    // вывод измеренных свойств фигуры - длина внешней границы, площадь
    void showMeasures() {
        System.out.printf("\tlength of outer bound: %.2f\n", getBoundLength());
        System.out.printf("\tsquare: %.2f\n", getSquare());
    }
}