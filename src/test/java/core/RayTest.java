package core;

import core.Tuple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RayTest {

    @Test
    public void testCreatingAndQueryingRay() {
        // Given
        Tuple origin = Tuple.point(1, 2, 3);
        Tuple direction = Tuple.vector(4, 5, 6);

        // When
        Ray r = new Ray(origin, direction);

        // Then
        assertEquals(origin, r.getOrigin());
        assertEquals(direction, r.getDirection());
    }

    private static void assertPositionEquals(Tuple expected, Tuple actual) {
        Assertions.assertEquals(expected.getX(), actual.getX(), "X coordinate does not match");
        Assertions.assertEquals(expected.getY(), actual.getY(), "Y coordinate does not match");
        Assertions.assertEquals(expected.getZ(), actual.getZ(), "Z coordinate does not match");
        Assertions.assertEquals(expected.getW(), actual.getW(), "W coordinate does not match");
    }

    @Test
    public void testComputingAPointFromADistance() {
        Ray r = new Ray(Tuple.point(2, 3, 4), Tuple.vector(1, 0, 0));

        assertPositionEquals(Tuple.point(2, 3, 4), Ray.position(r, 0));
        assertPositionEquals(Tuple.point(3, 3, 4), Ray.position(r, 1));
        assertPositionEquals(Tuple.point(1, 3, 4), Ray.position(r, -1));
        assertPositionEquals(Tuple.point(4.5, 3, 4), Ray.position(r, 2.5));
    }

    @Test
    public void testTransformTranslation() {
        Ray r = new Ray(Tuple.point(1, 2, 3), Tuple.vector(0, 1, 0));
        Matrix m = MatrixTransform.translation(3, 4, 5);
        Ray r2 = r.transform(m);
        assertEquals(4, r2.getOrigin().getX(), 0.00001);
        assertEquals(6, r2.getOrigin().getY(), 0.00001);
        assertEquals(8, r2.getOrigin().getZ(), 0.00001);
        assertEquals(0, r2.getDirection().getX(), 0.00001);
        assertEquals(1, r2.getDirection().getY(), 0.00001);
        assertEquals(0, r2.getDirection().getZ(), 0.00001);
    }

    @Test
    public void testTransformScaling() {
        Ray r = new Ray(Tuple.point(1, 2, 3), Tuple.vector(0, 1, 0));
        Matrix m = MatrixTransform.scaling(2, 3, 4);
        Ray r2 = r.transform(m);
        assertEquals(2, r2.getOrigin().getX(), 0.00001);
        assertEquals(6, r2.getOrigin().getY(), 0.00001);
        assertEquals(12, r2.getOrigin().getZ(), 0.00001);
        assertEquals(0, r2.getDirection().getX(), 0.00001);
        assertEquals(3, r2.getDirection().getY(), 0.00001);
        assertEquals(0, r2.getDirection().getZ(), 0.00001);
    }
}
