package exercise;

// BEGIN
class Flat implements Home {
    private double area;
    private double balconyArea;
    private int floor;

    public Flat(double area, double balconyArea, int floor) {
        this.area = area;
        this.balconyArea = balconyArea;
        this.floor = floor;
    }

    @Override
    public double getArea() {
        return area + balconyArea;
    }

    public String toString() {
        return "Квартира площадью " + this.getArea() + " метров на " + this.getFloor() + " этаже";
    }

    private int getFloor() {
        return this.floor;
    }

    @Override
    public int compareTo(Home another) {
        if (this.area == another.getArea()) {
            return 0;
        } else if (this.area > another.getArea()) {
            return 1;
        }
        return -1;
    }
}
// END
