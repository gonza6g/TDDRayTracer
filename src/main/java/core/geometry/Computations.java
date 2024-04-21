package core.geometry;

import shape.Shape;
import shape.Sphere;

import static core.geometry.NormalCalculator.normalAt;

public class Computations {
    private double t;
    private Shape object;
    private Tuple point;
    private Tuple eyeVector;
    private Tuple normalVector;
    private boolean inside;

    // Constructor
    public Computations() {
    }

    // Getters and setters
    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public Sphere getObject() {
        return (Sphere) object;
    }

    public void setObject(Shape object) {
        this.object = object;
    }

    public Tuple getPoint() {
        return point;
    }

    public void setPoint(Tuple point) {
        this.point = point;
    }

    public Tuple getEyeVector() {
        return eyeVector;
    }

    public void setEyeVector(Tuple eyeVector) {
        this.eyeVector = eyeVector;
    }

    public Tuple getNormalVector() {
        return normalVector;
    }

    public void setNormalVector(Tuple normalVector) {
        this.normalVector = normalVector;
    }

    public void setInside(boolean inside) {
        this.inside = inside;
    }

    public boolean isInside() {
        return inside;
    }

    public static Computations prepareComputations(Intersection intersection, Ray ray) {
        Computations comps = new Computations();
        comps.setT(intersection.getT());
        comps.setObject(intersection.getObject());
        comps.setPoint(ray.getPosition(comps.getT()));
        comps.setEyeVector(ray.getDirection().negate());
        comps.setNormalVector(normalAt(comps.getObject(), comps.getPoint()));

        if (comps.getNormalVector().dot(comps.getEyeVector()) < 0) {
            comps.setInside(true);
            comps.setNormalVector(comps.getNormalVector().negate());
        } else {
            comps.setInside(false);
        }

        return comps;
    }
}
