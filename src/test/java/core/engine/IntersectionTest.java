package core.engine;

import core.geometry.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shape.Sphere;

import java.util.ArrayList;
import java.util.List;

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

        List<Intersection> xs = new ArrayList<>();
        xs.add(i1);
        xs.add(i2);

        assertEquals(2, xs.size());
        assertEquals(i1, xs.get(0));
        assertEquals(i2, xs.get(1));
    }

    @Test
    void intersectSetsObject() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        List<Intersection> i = r.intersect(s, s.getTransform());

        assertEquals(2, i.size());
        assertEquals(s, i.get(0).getObject());
        assertEquals(s, i.get(1).getObject());
    }

    @Test
    void testHitAllPositiveT() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(1, s);
        Intersection i2 = new Intersection(2, s);
        List<Intersection> xs = new ArrayList<>();
        xs.add(i2);
        xs.add(i1);

        Intersection closest = Intersection.hit(xs);
        Assertions.assertEquals(i1, closest);
    }

    @Test
    void testHitSomeNegativeT() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(-1, s);
        Intersection i2 = new Intersection(1, s);
        List<Intersection> xs = new ArrayList<>();
        xs.add(i2);
        xs.add(i1);

        Intersection closest = Intersection.hit(xs);
        Assertions.assertEquals(i2, closest);
    }

    @Test
    void testHitAllNegativeT() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(-2, s);
        Intersection i2 = new Intersection(-1, s);
        List<Intersection> xs = new ArrayList<>();
        xs.add(i2);
        xs.add(i1);

        Intersection closest = Intersection.hit(xs);
        Assertions.assertNull(closest);
    }

    @Test
    void testHitLowestNonnegativeIntersection() {
        Sphere s = new Sphere();
        Intersection i1 = new Intersection(5, s);
        Intersection i2 = new Intersection(7, s);
        Intersection i3 = new Intersection(-3, s);
        Intersection i4 = new Intersection(2, s);
        List<Intersection> xs = new ArrayList<>();
        xs.add(i1);
        xs.add(i2);
        xs.add(i3);
        xs.add(i4);

        Intersection closest = Intersection.hit(xs);
        assertEquals(i4, closest);
    }

    @Test
    public void testIntersectScaledSphere() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        s.setTransform(MatrixTransform.scaling(2, 2, 2));
        List<Intersection> xs = r.intersect(s, s.getTransform());
        assertEquals(2, xs.size());
        assertEquals(3, xs.get(0).getT(), 0.00001);
        assertEquals(7, xs.get(1).getT(), 0.00001);
    }

    @Test
    public void testIntersectTranslatedSphere() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        s.setTransform(MatrixTransform.translation(5, 0, 0));
        List<Intersection> xs = r.intersect(s, s.getTransform());
        assertEquals(0, xs.size());
    }
}
