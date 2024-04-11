import org.junit.Test;

import static org.junit.Assert.*;

public class TupleTest {

    @Test
    public void tupleIsPointWhenWIs1() {
        Tuple t = new Tuple(4.3, -4.2, 3.1, 1.0);
        assertEquals(4.3, t.getX(), 0.00001);
        assertEquals(-4.2, t.getY(), 0.00001);
        assertEquals(3.1, t.getZ(), 0.00001);
        assertEquals(1.0, t.getW(), 0.00001);
        assertTrue(t.isPoint());
        assertFalse(t.isVector());
    }

    @Test
    public void tupleIsVectorWhenWIs0() {
        Tuple t = new Tuple(4.3, -4.2, 3.1, 0.0);
        assertEquals(4.3, t.getX(), 0.00001);
        assertEquals(-4.2, t.getY(), 0.00001);
        assertEquals(3.1, t.getZ(), 0.00001);
        assertEquals(0.0, t.getW(), 0.00001);
        assertFalse(t.isPoint());
        assertTrue(t.isVector());
    }

    @Test
    public void pointFactoryCreatesPointTuple() {
        Tuple t = Tuple.point(4.3, -4.2, 3.1);
        assertEquals(4.3, t.getX(), 0.00001);
        assertEquals(-4.2, t.getY(), 0.00001);
        assertEquals(3.1, t.getZ(), 0.00001);
        assertEquals(1.0, t.getW(), 0.00001);
        assertTrue(t.isPoint());
        assertFalse(t.isVector());
    }

    @Test
    public void vectorFactoryCreatesVectorTuple() {
        Tuple t = Tuple.vector(4.3, -4.2, 3.1);
        assertEquals(4.3, t.getX(), 0.00001);
        assertEquals(-4.2, t.getY(), 0.00001);
        assertEquals(3.1, t.getZ(), 0.00001);
        assertEquals(0.0, t.getW(), 0.00001);
        assertFalse(t.isPoint());
        assertTrue(t.isVector());
    }

    @Test
    public void tuplesAreEqualWithinEpsilon() {
        Tuple t1 = new Tuple(1.0, 2.0, 3.0, 0.0);
        Tuple t2 = new Tuple(1.000001, 2.000002, 3.000003, 0.0);
        assertTrue(Tuple.tupleEquals(t1, t2));
    }

    @Test
    public void tuplesAreNotEqualOutsideEpsilon() {
        Tuple t1 = new Tuple(1.0, 2.0, 3.0, 0.0);
        Tuple t2 = new Tuple(1.0001, 2.0001, 3.0001, 0.0);
        assertFalse(Tuple.tupleEquals(t1, t2));
    }

    @Test
    public void tuplesWithDifferentWValuesAreNotEqual() {
        Tuple t1 = new Tuple(1.0, 2.0, 3.0, 0.0);
        Tuple t2 = new Tuple(1.0, 2.0, 3.0, 1.0);
        assertFalse(Tuple.tupleEquals(t1, t2));
    }

    @Test
    public void addingPointAndVectorProducesNewPoint() {
        Tuple point = Tuple.point(3, -2, 5);
        Tuple vector = Tuple.vector(-2, 3, 1);
        Tuple result = Tuple.add(point, vector);
        Tuple expected = new Tuple(1, 1, 6, 1);
        assertTrue(Tuple.tupleEquals(expected, result));
    }

    @Test
    public void addingTwoPointsThrowsException() {
        Tuple point1 = Tuple.point(1, 2, 3);
        Tuple point2 = Tuple.point(4, 5, 6);
        try {
            Tuple result = Tuple.add(point1, point2);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passed
        }
    }

    @Test
    public void addingTwoVectorsProducesNewVector() {
        Tuple vector1 = Tuple.vector(1, 2, 3);
        Tuple vector2 = Tuple.vector(4, 5, 6);
        Tuple result = Tuple.add(vector1, vector2);
        Tuple expected = new Tuple(5, 7, 9, 0);
        assertTrue(Tuple.tupleEquals(expected, result));
    }

    @Test
    public void subtractingTwoPointsProducesVector() {
        Tuple point1 = Tuple.point(3, 2, 1);
        Tuple point2 = Tuple.point(5, 6, 7);
        Tuple result = Tuple.subtract(point1, point2);
        Tuple expected = Tuple.vector(-2, -4, -6);
        assertTrue(Tuple.tupleEquals(expected, result));
    }

    @Test
    public void subtractingVectorFromPointProducesPoint() {
        Tuple point = Tuple.point(3, 2, 1);
        Tuple vector = Tuple.vector(5, 6, 7);
        Tuple result = Tuple.subtract(point, vector);
        Tuple expected = Tuple.point(-2, -4, -6);
        assertTrue(Tuple.tupleEquals(expected, result));
    }

    @Test
    public void subtractingPointFromVectorThrowsException() {
        Tuple vector = Tuple.vector(3, 2, 1);
        Tuple point = Tuple.point(5, 6, 7);
        try {
            Tuple result = Tuple.subtract(vector, point);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passed
        }
    }

    @Test
    public void subtractingTwoVectorsProducesVector() {
        Tuple vector1 = Tuple.vector(3, 2, 1);
        Tuple vector2 = Tuple.vector(5, 6, 7);
        Tuple result = Tuple.subtract(vector1, vector2);
        Tuple expected = Tuple.vector(-2, -4, -6);
        assertTrue(Tuple.tupleEquals(expected, result));
    }

    @Test
    public void negatingTuple() {
        Tuple a = new Tuple(1, -2, 3, -4);
        Tuple negatedA = Tuple.negate(a);
        assertEquals(-1.0, negatedA.getX(), 0.00001);
        assertEquals(2.0, negatedA.getY(), 0.00001);
        assertEquals(-3.0, negatedA.getZ(), 0.00001);
        assertEquals(4.0, negatedA.getW(), 0.00001);
    }

    @Test
    public void multiplyingTupleByScalar() {
        Tuple a = new Tuple(1, -2, 3, -4);
        Tuple result = Tuple.multiply(a, 3.5);
        assertEquals(3.5, result.getX(), 0.00001);
        assertEquals(-7, result.getY(), 0.00001);
        assertEquals(10.5, result.getZ(), 0.00001);
        assertEquals(-14, result.getW(), 0.00001);
    }

    @Test
    public void multiplyingTupleByFraction() {
        Tuple a = new Tuple(1, -2, 3, -4);
        Tuple result = Tuple.multiply(a, 0.5);
        assertEquals(0.5, result.getX(), 0.00001);
        assertEquals(-1, result.getY(), 0.00001);
        assertEquals(1.5, result.getZ(), 0.00001);
        assertEquals(-2, result.getW(), 0.00001);
    }

    @Test
    public void dividingTupleByScalar() {
        Tuple a = new Tuple(1, -2, 3, -4);
        Tuple result = Tuple.divide(a, 2);
        assertEquals(0.5, result.getX(), 0.00001);
        assertEquals(-1, result.getY(), 0.00001);
        assertEquals(1.5, result.getZ(), 0.00001);
        assertEquals(-2, result.getW(), 0.00001);
    }

    @Test
    public void computingMagnitudeOfVectorX() {
        Tuple v = Tuple.vector(1, 0, 0);
        double magnitude = Tuple.magnitude(v);
        assertEquals(1.0, magnitude, 0.00001);
    }

    @Test
    public void computingMagnitudeOfVectorY() {
        Tuple v = Tuple.vector(0, 1, 0);
        double magnitude = Tuple.magnitude(v);
        assertEquals(1.0, magnitude, 0.00001);
    }

    @Test
    public void computingMagnitudeOfVectorZ() {
        Tuple v = Tuple.vector(0, 0, 1);
        double magnitude = Tuple.magnitude(v);
        assertEquals(1.0, magnitude, 0.00001);
    }

    @Test
    public void computingMagnitudeOfVectorXYZ() {
        Tuple v = Tuple.vector(1, 2, 3);
        double magnitude = Tuple.magnitude(v);
        assertEquals(Math.sqrt(14), magnitude, 0.00001);
    }

    @Test
    public void computingMagnitudeOfNegativeVectorXYZ() {
        Tuple v = Tuple.vector(-1, -2, -3);
        double magnitude = Tuple.magnitude(v);
        assertEquals(Math.sqrt(14), magnitude, 0.00001);
    }

    @Test
    public void normalizingVectorX() {
        Tuple v = Tuple.vector(4, 0, 0);
        Tuple normalized = Tuple.normalize(v);
        assertEquals(1, normalized.getX(), 0.00001);
        assertEquals(0, normalized.getY(), 0.00001);
        assertEquals(0, normalized.getZ(), 0.00001);
    }

    @Test
    public void normalizingVectorXYZ() {
        Tuple v = Tuple.vector(1, 2, 3);
        Tuple normalized = Tuple.normalize(v);
        assertEquals(0.26726, normalized.getX(), 0.00001);
        assertEquals(0.53452, normalized.getY(), 0.00001);
        assertEquals(0.80178, normalized.getZ(), 0.00001);
    }

    @Test
    public void magnitudeOfNormalizedVector() {
        Tuple v = Tuple.vector(1, 2, 3);
        Tuple normalized = Tuple.normalize(v);
        assertEquals(1.0, Tuple.magnitude(normalized), 0.00001);
    }

    @Test
    public void dotProductOfTwoTuples() {
        Tuple a = Tuple.vector(1, 2, 3);
        Tuple b = Tuple.vector(2, 3, 4);
        double dotProduct = Tuple.dot(a, b);
        assertEquals(20.0, dotProduct, 0.00001);
    }

    @Test
    public void dotProductThrowsExceptionForPoints() {
        Tuple a = Tuple.vector(1, 2, 3);
        Tuple b = Tuple.point(2, 3, 4);
        assertThrows(IllegalArgumentException.class, () -> Tuple.dot(a, b));
    }

    @Test
    public void crossProductOfTwoVectors() {
        Tuple a = Tuple.vector(1, 2, 3);
        Tuple b = Tuple.vector(2, 3, 4);

        Tuple crossAB = Tuple.cross(a, b);
        assertEquals(-1, crossAB.getX(), 0.00001);
        assertEquals(2, crossAB.getY(), 0.00001);
        assertEquals(-1, crossAB.getZ(), 0.00001);
        assertEquals(0, crossAB.getW(), 0.00001);

        Tuple crossBA = Tuple.cross(b, a);
        assertEquals(1, crossBA.getX(), 0.00001);
        assertEquals(-2, crossBA.getY(), 0.00001);
        assertEquals(1, crossBA.getZ(), 0.00001);
        assertEquals(0, crossBA.getW(), 0.00001);
    }
}
