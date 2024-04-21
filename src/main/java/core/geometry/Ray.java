package core.geometry;

import core.lighting.PointLight;
import core.material.Material;
import scene.World;
import shape.Shape;
import draw.Color;

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

    public Tuple getPosition(double t) {
        Tuple scaledDirection = Tuple.multiply(getDirection(), t);
        return Tuple.add(getOrigin(), scaledDirection);
    }

    public List<Intersection> intersect(Shape s, Matrix transform) {
        // Transform the ray using the inverse of the shape's transformation matrix
        List<Intersection> xs = new ArrayList<>();
        Matrix inverseTransform = transform.inverse();
        Ray transformedRay = this.transform(inverseTransform);
        Tuple sphereToRay = Tuple.subtract(transformedRay.getOrigin(), Tuple.point(0, 0, 0));

        double a = transformedRay.getDirection().dot(transformedRay.getDirection());
        double b = 2 * transformedRay.getDirection().dot(sphereToRay);
        double c = sphereToRay.dot(sphereToRay) - 1;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return xs;
        }

        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);

        xs.add(new Intersection(t1, s));
        xs.add(new Intersection(t2, s));

        return xs;
    }

    public Ray transform(Matrix matrix) {
        Tuple newOrigin = matrix.multiply(origin);
        Tuple newDirection = matrix.multiply(direction);
        return new Ray(newOrigin, newDirection);
    }
}
