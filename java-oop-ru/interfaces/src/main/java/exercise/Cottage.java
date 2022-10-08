package exercise;

// BEGIN
class Cottage implements Home {
    private double area;
    private int floorCount;

    public Cottage(double area, int floorCount) {
        this.area = area;
        this.floorCount = floorCount;
    }
    public int getFloor() {
        return this.floorCount;
    }

    public String toString() {
        return this.getFloor() + " этажный коттедж площадью " + this.getArea() + " метров";
    }
    @Override
    public double getArea() {
        return this.area;
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
