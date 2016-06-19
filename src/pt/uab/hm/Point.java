package pt.uab.hm;

public class Point implements Comparable<Point> {
    private double x;
    private double y;

    public Point(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calculates the distance between two points (always a positive value)
     *
     * @param point The point to calculate the distance to
     * @return The distance between the current point and the given point
     */
    public double distanceTo(Point point) {
        return Math.abs(Math.sqrt(Math.pow(this.x - point.getX(), 2) + Math.pow(this.y - point.getY(), 2)));
    }

    @Override
    public int compareTo(Point o) {
        if (this.x > o.x || this.y > o.y)
            return 1;
        else if (this.x < o.x || this.y < o.y)
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
