package core.engine;

import core.geometry.*;
import core.lighting.PointLight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import scene.World;
import scene.WorldFactory;
import shape.Shape;
import shape.Sphere;
import draw.Color;

import java.util.ArrayList;
import java.util.List;

import static core.geometry.Computations.prepareComputations;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

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

        Assertions.assertEquals(2, xs.size());
        Assertions.assertEquals(i1, xs.get(0));
        Assertions.assertEquals(i2, xs.get(1));
    }

    @Test
    void intersectSetsObject() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        List<Intersection> i = r.intersect(s, s.getTransform());

        Assertions.assertEquals(2, i.size());
        Assertions.assertEquals(s, i.get(0).getObject());
        Assertions.assertEquals(s, i.get(1).getObject());
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
        Assertions.assertEquals(i4, closest);
    }

    @Test
    public void testIntersectScaledSphere() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        s.setTransform(MatrixTransform.scaling(2, 2, 2));
        List<Intersection> xs = r.intersect(s, s.getTransform());
        Assertions.assertEquals(2, xs.size());
        Assertions.assertEquals(3, xs.get(0).getT(), 0.00001);
        Assertions.assertEquals(7, xs.get(1).getT(), 0.00001);
    }

    @Test
    public void testIntersectTranslatedSphere() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();
        s.setTransform(MatrixTransform.translation(5, 0, 0));
        List<Intersection> xs = r.intersect(s, s.getTransform());
        Assertions.assertEquals(0, xs.size());
    }

    @Test
    void testPrepareComputations() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere shape = new Sphere();
        Intersection i = new Intersection(4, shape);

        Computations comps = prepareComputations(i, r);

        Assertions.assertEquals(i.getT(), comps.getT(), 0.0001);
        Assertions.assertEquals(i.getObject(), comps.getObject());
        Assertions.assertEquals(0, comps.getPoint().getX(), 0.0001);
        Assertions.assertEquals(0, comps.getPoint().getY(), 0.0001);
        Assertions.assertEquals(-1, comps.getPoint().getZ(), 0.0001);

        Assertions.assertEquals(0, comps.getEyeVector().getX(), 0.0001);
        Assertions.assertEquals(0, comps.getEyeVector().getY(), 0.0001);
        Assertions.assertEquals(-1, comps.getEyeVector().getZ(), 0.0001);

        Assertions.assertEquals(0, comps.getNormalVector().getX(), 0.0001);
        Assertions.assertEquals(0, comps.getNormalVector().getY(), 0.0001);
        Assertions.assertEquals(-1, comps.getNormalVector().getZ(), 0.0001);

    }

    @Test
    public void prepareComputations_InsideIntersection() {
        Ray r = new Ray(Tuple.point(0, 0, 0), Tuple.vector(0, 0, 1));
        Sphere shape = new Sphere();
        Intersection i = new Intersection(1, shape);

        Computations comps = prepareComputations(i, r);

        assertTrue(comps.isInside());
        Assertions.assertEquals(Tuple.point(0, 0, 1), comps.getPoint());
        Assertions.assertEquals(Tuple.vector(0, 0, -1), comps.getEyeVector());
        Assertions.assertEquals(Tuple.vector(0, 0, -1), comps.getNormalVector());
    }

    @Test
    public void prepareComputations_OutsideIntersection() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere shape = new Sphere();
        Intersection i = new Intersection(4, shape);

        Computations comps = prepareComputations(i, r);

        assertFalse(comps.isInside());
        Assertions.assertEquals(Tuple.point(0, 0, -1), comps.getPoint());
        Assertions.assertEquals(Tuple.vector(0, 0, -1), comps.getEyeVector());
        Assertions.assertEquals(Tuple.vector(0, 0, -1), comps.getNormalVector());
    }

    @Test
    public void shadeHit_Intersection() {
        World w = WorldFactory.defaultWorld();
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Shape shape = w.getObjects().get(0);
        Intersection i = new Intersection(4, shape);
        Computations comps = prepareComputations(i, r);

        Color c = Color.BLACK;
        c = c.shadeHit(w, comps);

        Assertions.assertEquals(0.38066, c.getRed(), 0.0001);
        Assertions.assertEquals(0.47583, c.getGreen(), 0.0001);
        Assertions.assertEquals(0.2855, c.getBlue(), 0.0001);
    }

    @Test
    public void shadeHit_IntersectionInside() {
        World w = WorldFactory.defaultWorld();
        w.setLight(new PointLight(Tuple.point(0, 0.25, 0), new Color(1, 1, 1)));
        Ray r = new Ray(Tuple.point(0, 0, 0), Tuple.vector(0, 0, 1));
        Shape shape = w.getObjects().get(1);
        Intersection i = new Intersection(0.5, shape);
        Computations comps = prepareComputations(i, r);

        Color c = Color.BLACK;
        c = c.shadeHit(w, comps);

        Assertions.assertEquals(0.90498, c.getRed(), 0.0001);
        Assertions.assertEquals(0.90498, c.getGreen(), 0.0001);
        Assertions.assertEquals(0.90498, c.getBlue(), 0.0001);
    }

    @Test
    public void colorAt_RayMisses() {
        World w = WorldFactory.defaultWorld();
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 1, 0));

        Color c = Color.BLACK;
        c = c.colorAt(w, r);

        Assertions.assertEquals(new Color(0, 0, 0), c);
    }

    @Test
    public void colorAt_RayHits() {
        World w = WorldFactory.defaultWorld();
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));

        Color c = Color.BLACK;
        c = c.colorAt(w, r);

        Assertions.assertEquals(0.38066, c.getRed(), 0.0001);
        Assertions.assertEquals(0.47583, c.getGreen(), 0.0001);
        Assertions.assertEquals(0.2855, c.getBlue(), 0.0001);
    }

    @Test
    public void colorAt_IntersectionBehindRay() {
        World w = WorldFactory.defaultWorld();
        Sphere outer = (Sphere) w.getObjects().get(0);
        outer.getMaterial().setAmbient(1);
        Sphere inner = (Sphere) w.getObjects().get(1);
        inner.getMaterial().setAmbient(1);
        Ray r = new Ray(Tuple.point(0, 0, 0.75), Tuple.vector(0, 0, -1));

        Color c = Color.BLACK;
        c = c.colorAt(w, r);

        assertEquals(inner.getMaterial().getColor(), c);
    }
}
