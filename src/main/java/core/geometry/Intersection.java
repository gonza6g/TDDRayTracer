package core.geometry;

import shape.Shape;

public class Intersection {
    private double t;
    private Shape object;

    public Intersection(double t, Shape object) {
        this.t = t;
        this.object = object;
    }

    public double getT() {
        return t;
    }

    public Shape getObject() {
        return object;
    }
}
