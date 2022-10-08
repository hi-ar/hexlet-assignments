package exercise;

// BEGIN
class Segment {
    public Segment(Point a, Point b) {
        this.beginPoint = a;
        this.endPoint = b;
    }

    private Point beginPoint;
    private Point endPoint;

    public Point getBeginPoint() {
        return beginPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public Point getMidPoint() {
        return new Point((int) (beginPoint.getX() + endPoint.getX()) / 2,
                (int) (beginPoint.getY() + endPoint.getY()) / 2);
    }
}
// END
