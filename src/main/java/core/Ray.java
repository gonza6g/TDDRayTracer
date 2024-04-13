package core;

import shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Ray {
    private Tuple origin;
    private Tuple direction;

    public Ray(Tuple origin, Tuple direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Tuple getOrigin() { return origin; }

    public Tuple getDirection() {
        return direction;
    }

    public static Tuple position(Ray ray, double t) {
        Tuple scaledDirection = Tuple.multiply(ray.getDirection(), t);
        return Tuple.add(ray.getOrigin(), scaledDirection);
    }

    public Intersections intersect(Shape s) {
        Tuple sphereToRay = Tuple.subtract(origin, Tuple.point(0, 0, 0));
        double a = Tuple.dot(direction, direction);
        double b = 2 * Tuple.dot(direction, sphereToRay);
        double c = Tuple.dot(sphereToRay, sphereToRay) - 1;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return new Intersections();
        }

        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);

        Intersections intersections = new Intersections();
        intersections.add(new Intersection(t1, s));
        intersections.add(new Intersection(t2, s));

        return intersections;
    }

    public Ray transform(Matrix matrix) {
        Tuple newOrigin = matrix.multiply(origin);
        Tuple newDirection = matrix.multiply(direction);
        return new Ray(newOrigin, newDirection);
    }

}
