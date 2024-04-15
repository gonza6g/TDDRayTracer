package shape;

import core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SphereTest {
    @Test
    public void testARayIntersectsASphereAtTwoPoints() {
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));
        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        assertEquals(2, xs.size());
        assertEquals(4.0, xs.get(0).getT(), 0.0001);
        assertEquals(6.0, xs.get(1).getT(), 0.0001);
    }

    @Test
    public void testRayIntersectsSphereAtTangent() {
        Tuple rayOrigin = Tuple.point(0, 1, -5);
        Tuple rayDirection = Tuple.vector(0, 0, 1);
        Ray r = new Ray(rayOrigin, rayDirection);

        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        assertEquals(2, xs.size());
        assertTrue(Double.compare(5.0, xs.get(0).getT()) == 0);
        assertTrue(Double.compare(5.0, xs.get(1).getT()) == 0);
    }

    @Test
    public void testRayMissesSphere() {
        Tuple rayOrigin = Tuple.point(0, 2, -5);
        Tuple rayDirection = Tuple.vector(0, 0, 1);
        Ray r = new Ray(rayOrigin, rayDirection);

        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        assertEquals(0, xs.size());
    }

    @Test
    public void testRayOriginatesInsideSphere() {
        Tuple rayOrigin = Tuple.point(0, 0, 0);
        Tuple rayDirection = Tuple.vector(0, 0, 1);
        Ray r = new Ray(rayOrigin, rayDirection);

        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        assertEquals(2, xs.size());
        assertEquals(-1.0, xs.get(0).getT(), 0.00001);
        assertEquals(1.0, xs.get(1).getT(), 0.00001);
    }

    @Test
    public void testRayBehindSphere() {
        Tuple rayOrigin = Tuple.point(0, 0, 5);
        Tuple rayDirection = Tuple.vector(0, 0, 1);
        Ray r = new Ray(rayOrigin, rayDirection);

        Sphere s = new Sphere();

        Intersections xs = r.intersect(s, s.getTransform());

        assertEquals(2, xs.size());
        assertEquals(-6.0, xs.get(0).getT(), 0.00001);
        assertEquals(-4.0, xs.get(1).getT(), 0.00001);
    }

    @Test
    public void testDefaultTransformation() {
        Sphere s = new Sphere();
        assertEquals(Matrix.identity(4), s.getTransform());
    }

    @Test
    public void testChangingTransformation() {
        Sphere s = new Sphere();
        Matrix t = MatrixTransform.translation(2, 3, 4);
        s.setTransform(t);

        assertEquals(t, s.getTransform());
    }

    @Test
    public void testNormalAtPointOnXAxis() {
        Sphere s = new Sphere();
        Tuple n = NormalCalculator.normalAt(s, Tuple.point(1, 0, 0));
        assertEquals(1.0, n.getX(), 0.00001);
        assertEquals(0.0, n.getY(), 0.00001);
        assertEquals(0.0, n.getZ(), 0.00001);
        assertEquals(0.0, n.getW(), 0.00001);
    }

    @Test
    public void testNormalAtPointOnYAxis() {
        Sphere s = new Sphere();
        Tuple n = NormalCalculator.normalAt(s, Tuple.point(0, 1, 0));
        assertEquals(0.0, n.getX(), 0.00001);
        assertEquals(1.0, n.getY(), 0.00001);
        assertEquals(0.0, n.getZ(), 0.00001);
        assertEquals(0.0, n.getW(), 0.00001);
    }

    @Test
    public void testNormalAtPointOnZAxis() {
        Sphere s = new Sphere();
        Tuple n = NormalCalculator.normalAt(s, Tuple.point(0, 0, 1));
        assertEquals(0.0, n.getX(), 0.00001);
        assertEquals(0.0, n.getY(), 0.00001);
        assertEquals(1.0, n.getZ(), 0.00001);
        assertEquals(0.0, n.getW(), 0.00001);
    }

    @Test
    public void testNormalAtNonAxialPoint() {
        Sphere s = new Sphere();
        double sqrt3over3 = Math.sqrt(3) / 3;
        Tuple n = NormalCalculator.normalAt(s, Tuple.point(sqrt3over3, sqrt3over3, sqrt3over3));
        assertEquals(sqrt3over3, n.getX(), 0.00001);
        assertEquals(sqrt3over3, n.getY(), 0.00001);
        assertEquals(sqrt3over3, n.getZ(), 0.00001);
        assertEquals(0.0, n.getW(), 0.00001);
    }

    @Test
    public void testNormalIsNormalized() {
        Sphere s = new Sphere();
        double sqrt3over3 = Math.sqrt(3) / 3;
        Tuple n = NormalCalculator.normalAt(s, Tuple.point(sqrt3over3, sqrt3over3, sqrt3over3));

        // Check if the magnitude of the normal vector is approximately 1.0
        double magnitude = Math.sqrt(n.getX() * n.getX() + n.getY() * n.getY() + n.getZ() * n.getZ());
        assertTrue(Math.abs(magnitude - 1.0) < 0.00001);
    }

    @Test
    public void testNormalAtTranslatedSphere() {
        Sphere s = new Sphere();
        s.setTransform(MatrixTransform.translation(0, 1, 0));
        Tuple n = NormalCalculator.normalAt(s, Tuple.point(0, 1.70711, -0.70711));
        assertEquals(0.0, n.getX(), 0.00001);
        assertEquals(0.70711, n.getY(), 0.00001);
        assertEquals(-0.70711, n.getZ(), 0.00001);
        assertEquals(0.0, n.getW(), 0.00001);
    }

    @Test
    public void testNormalAtTransformedSphere() {
        Sphere s = new Sphere();
        Matrix m = MatrixTransform.scaling(1, 0.5, 1).multiply(MatrixTransform.rotationZ(Math.PI / 5));
        s.setTransform(m);
        Tuple n = NormalCalculator.normalAt(s, Tuple.point(0, Math.sqrt(2) / 2, -Math.sqrt(2) / 2));
        assertEquals(0.0, n.getX(), 0.00001);
        assertEquals(0.97014, n.getY(), 0.00001);
        assertEquals(-0.24254, n.getZ(), 0.00001);
        assertEquals(0.0, n.getW(), 0.00001);
    }

    @Test
    public void testSphereDefaultMaterial() {
        Sphere s = new Sphere();
        Material m = s.getMaterial();
        assertEquals(new Material(), m);
    }

    @Test
    public void testAssignMaterialToSphere() {
        Sphere s = new Sphere();
        Material m = new Material();
        m.setAmbient(1);
        s.setMaterial(m);
        assertEquals(m, s.getMaterial());
    }
}