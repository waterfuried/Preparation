<<<<<<< HEAD
// ÐšÑ€ÑƒÐ³ Ð½Ð°ÑÐ»ÐµÐ´ÑƒÐµÑ‚ ÐºÐ»Ð°ÑÑ Ð¤Ð¸Ð³ÑƒÑ€Ð°
class Circle extends Shape {
    private double radius; // Ñ€Ð°Ð´Ð¸ÑƒÑ
=======
// Êðóã íàñëåäóåò êëàññ Ôèãóðà
class Circle extends Shape {
    private double radius; // ðàäèóñ
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