package shape;

import core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SphereTest {
    @Test
    public void testARayIntersectsASphereAtTwoPoints() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        Assertions.assertEquals(2, xs.size());
        Assertions.assertEquals(4.0, xs.get(0).getT(), 0.0001);
        Assertions.assertEquals(6.0, xs.get(1).getT(), 0.0001);
    }

    @Test
    public void testRayIntersectsSphereAtTangent() {
        Tuple rayOrigin = Tuple.point(0, 1, -5);
        Tuple rayDirection = Tuple.vector(0, 0, 1);
        Ray r = new Ray(rayOrigin, rayDirection);

        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        Assertions.assertEquals(2, xs.size());
        Assertions.assertTrue(Double.compare(5.0, xs.get(0).getT()) == 0);
        Assertions.assertTrue(Double.compare(5.0, xs.get(1).getT()) == 0);
    }

    @Test
    public void testRayMissesSphere() {
        Tuple rayOrigin = Tuple.point(0, 2, -5);
        Tuple rayDirection = Tuple.vector(0, 0, 1);
        Ray r = new Ray(rayOrigin, rayDirection);

        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        Assertions.assertEquals(0, xs.size());
    }

    @Test
    public void testRayOriginatesInsideSphere() {
        Tuple rayOrigin = Tuple.point(0, 0, 0);
        Tuple rayDirection = Tuple.vector(0, 0, 1);
        Ray r = new Ray(rayOrigin, rayDirection);

        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        Assertions.assertEquals(2, xs.size());
        Assertions.assertEquals(-1.0, xs.get(0).getT(), 0.00001);
        Assertions.assertEquals(1.0, xs.get(1).getT(), 0.00001);
    }

    @Test
    public void testRayBehindSphere() {
        Tuple rayOrigin = Tuple.point(0, 0, 5);
        Tuple rayDirection = Tuple.vector(0, 0, 1);
        Ray r = new Ray(rayOrigin, rayDirection);

        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        Assertions.assertEquals(2, xs.size());
        Assertions.assertEquals(-6.0, xs.get(0).getT(), 0.00001);
        Assertions.assertEquals(-4.0, xs.get(1).getT(), 0.00001);
    }

    @Test
    public void testDefaultTransformation() {
        Sphere s = new Sphere();
        Assertions.assertEquals(Matrix.identity(4), s.getTransform());
    }

    @Test
    public void testChangingTransformation() {
        Sphere s = new Sphere();
        Matrix t = MatrixTransform.translation(2, 3, 4);
        s.setTransform(t);

        Assertions.assertEquals(t, s.getTransform());
    }
}