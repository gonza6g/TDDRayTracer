package core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shape.Sphere;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntersectionTest {

    @Test
    public void intersectionEncapsulatesTAndObject() {
        Sphere sphere = new Sphere();
        Intersection intersection = new Intersection(3.5, sphere);

        Assertions.assertEquals(3.5, intersection.getT());
        Assertions.assertEquals(sphere, intersection.getObject());
    }

    @Test
    public void aggregatingIntersections() {
        Sphere sphere = new Sphere();
        Intersection i1 = new Intersection(1, sphere);
        Intersection i2 = new Intersection(2, sphere);

        Intersections intersections = new Intersections(i1, i2);

        assertEquals(2, intersections.size());
        assertEquals(i1, intersections.get(0));
        assertEquals(i2, intersections.get(1));
    }

    @Test
    void intersectSetsObject() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        Intersections i = r.intersect(s, s.getTransform());

        assertEquals(2, i.size());
        assertEquals(s, i.get(0).getObject());
        assertEquals(s, i.get(1).getObject());
    }

    @Test
    void testHitAllPositiveT() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(1, s);
        Intersection i2 = new Intersection(2, s);
        Intersections xs = new Intersections();
        xs.add(i2);
        xs.add(i1);

        Assertions.assertEquals(i1, xs.hit());
    }

    @Test
    void testHitSomeNegativeT() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(-1, s);
        Intersection i2 = new Intersection(1, s);
        Intersections xs = new Intersections();
        xs.add(i2);
        xs.add(i1);

        Assertions.assertEquals(i2, xs.hit());
    }

    @Test
    void testHitAllNegativeT() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(-2, s);
        Intersection i2 = new Intersection(-1, s);
        Intersections xs = new Intersections();
        xs.add(i2);
        xs.add(i1);

        Assertions.assertNull(xs.hit());
    }

    @Test
    void testHitLowestNonnegativeIntersection() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(5, s);
        Intersection i2 = new Intersection(7, s);
        Intersection i3 = new Intersection(-3, s);
        Intersection i4 = new Intersection(2, s);
        Intersections xs = new Intersections();
        xs.add(i1);
        xs.add(i2);
        xs.add(i3);
        xs.add(i4);

        assertEquals(i4, xs.hit());
    }

    @Test
    public void testIntersectScaledSphere() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        s.setTransform(MatrixTransform.scaling(2, 2, 2));
        Intersections xs = r.intersect(s, s.getTransform());
        assertEquals(2, xs.size());
        assertEquals(3, xs.get(0).getT(), 0.00001);
        assertEquals(7, xs.get(1).getT(), 0.00001);
    }

    @Test
    public void testIntersectTranslatedSphere() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        s.setTransform(MatrixTransform.translation(5, 0, 0));
        Intersections xs = r.intersect(s, s.getTransform());
        assertEquals(0, xs.size());
    }
}
