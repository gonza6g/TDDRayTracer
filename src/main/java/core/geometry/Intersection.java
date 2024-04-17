package core.geometry;

import shape.Shape;

import java.util.List;

public class Intersection {
    private double t;
    private Shape object;

    public Intersection(double t, Shape object) {
        this.t = t;
        this.object = object;
    }

    public static Intersection hit(List<Intersection> intersections) {
        Intersection hit = null;
        double minT = Double.MAX_VALUE;

        for (Intersection ints : intersections) {
            if (ints.getT() >= 0 && ints.getT() < minT) {
                minT = ints.getT();
                hit = ints;
            }
        }

        return hit;
    }

    public double getT() {
        return t;
    }

    public Shape getObject() {
        return object;
    }
}
