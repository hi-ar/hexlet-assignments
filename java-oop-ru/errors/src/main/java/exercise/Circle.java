package exercise;

// BEGIN
class Circle {
    Point middle;
    int radius;

    public Circle(Point middle, int radius) {
        this.middle = middle;
        this.radius = radius;
    }

    public double getSquare() throws NegativeRadiusException {
        if (radius < 0) throw new NegativeRadiusException(String.valueOf(radius));
        return radius * radius * Math.PI;
    }

    public int getRadius() {
        return radius;
    }
}
// END
