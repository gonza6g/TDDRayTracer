package core;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MatrixTransformTest {
    @Test
    public void multiplyByTranslationMatrixScenario() {
        Matrix transform = MatrixTransform.translation(5, -3, 2);
        Tuple p = Tuple.point(-3, 4, 5);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(2, result.getX(), 0.0001);
        Assertions.assertEquals(1, result.getY(), 0.0001);
        Assertions.assertEquals(7, result.getZ(), 0.0001);
    }

    @Test
    public void multiplyByInverseTranslationMatrixScenario() {
        Matrix transform = MatrixTransform.translation(5, -3, 2);
        Matrix inv = transform.inverse();
        Tuple p = Tuple.point(-3, 4, 5);

        Tuple result = inv.multiply(p);

        Assertions.assertEquals(-8, result.getX(), 0.0001);
        Assertions.assertEquals(7, result.getY(), 0.0001);
        Assertions.assertEquals(3, result.getZ(), 0.0001);
    }

    @Test
    public void translationDoesNotAffectVectorsScenario() {
        Matrix transform = MatrixTransform.translation(5, -3, 2);
        Tuple v = Tuple.vector(-3, 4, 5);

        Tuple result = transform.multiply(v);

        Assertions.assertEquals(v.getX(), result.getX(), 0.0001);
        Assertions.assertEquals(v.getY(), result.getY(), 0.0001);
        Assertions.assertEquals(v.getZ(), result.getZ(), 0.0001);
    }

    @Test
    public void scalingMatrixAppliedToPointScenario() {
        Matrix transform = MatrixTransform.scaling(2, 3, 4);
        Tuple p = Tuple.point(-4, 6, 8);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(-8, result.getX(), 0.0001);
        Assertions.assertEquals(18, result.getY(), 0.0001);
        Assertions.assertEquals(32, result.getZ(), 0.0001);
    }

    @Test
    public void scalingMatrixAppliedToVectorScenario() {
        Matrix transform = MatrixTransform.scaling(2, 3, 4);
        Tuple v = Tuple.vector(-4, 6, 8);

        Tuple result = transform.multiply(v);

        Assertions.assertEquals(-8, result.getX(), 0.0001);
        Assertions.assertEquals(18, result.getY(), 0.0001);
        Assertions.assertEquals(32, result.getZ(), 0.0001);
    }

    @Test
    public void inverseScalingMatrixAppliedToVectorScenario() {
        Matrix transform = MatrixTransform.scaling(2, 3, 4);
        Matrix inv = transform.inverse();
        Tuple v = Tuple.vector(-4, 6, 8);

        Tuple result = inv.multiply(v);

        Assertions.assertEquals(-2, result.getX(), 0.0001);
        Assertions.assertEquals(2, result.getY(), 0.0001);
        Assertions.assertEquals(2, result.getZ(), 0.0001);
    }

    @Test
    public void reflectionScalingByNegativeValueScenario() {
        Matrix transform = MatrixTransform.reflect();
        Tuple p = Tuple.point(2, 3, 4);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(-2, result.getX(), 0.0001);
        Assertions.assertEquals(3, result.getY(), 0.0001);
        Assertions.assertEquals(4, result.getZ(), 0.0001);
    }

    @Test
    public void rotatePointAroundXAxisScenario() {
        Tuple p = Tuple.point(0, 1, 0);

        Matrix halfQuarter = MatrixTransform.rotationX(Math.PI / 4);
        Matrix fullQuarter = MatrixTransform.rotationX(Math.PI / 2);

        Tuple halfQuarterResult = halfQuarter.multiply(p);
        Tuple fullQuarterResult = fullQuarter.multiply(p);

        Assertions.assertEquals(0, halfQuarterResult.getX(), 0.0001);
        Assertions.assertEquals(Math.sqrt(2) / 2, halfQuarterResult.getY(), 0.0001);
        Assertions.assertEquals(Math.sqrt(2) / 2, halfQuarterResult.getZ(), 0.0001);

        Assertions.assertEquals(0, fullQuarterResult.getX(), 0.0001);
        Assertions.assertEquals(0, fullQuarterResult.getY(), 0.0001);
        Assertions.assertEquals(1, fullQuarterResult.getZ(), 0.0001);
    }

    @Test
    public void inverseRotationXScenario() {
        Tuple p = Tuple.point(0, 1, 0);

        Matrix halfQuarter = MatrixTransform.rotationX(Math.PI / 4);
        Matrix inv = halfQuarter.inverse();

        Tuple result = inv.multiply(p);

        Assertions.assertEquals(0, result.getX(), 0.0001);
        Assertions.assertEquals(Math.sqrt(2) / 2, result.getY(), 0.0001);
        Assertions.assertEquals(-Math.sqrt(2) / 2, result.getZ(), 0.0001);
    }

    @Test
    public void rotatePointAroundYAxisScenario() {
        Tuple p = Tuple.point(0, 0, 1);

        Matrix halfQuarter = MatrixTransform.rotationY(Math.PI / 4);
        Matrix fullQuarter = MatrixTransform.rotationY(Math.PI / 2);

        Tuple halfQuarterResult = halfQuarter.multiply(p);
        Tuple fullQuarterResult = fullQuarter.multiply(p);

        Assertions.assertEquals(Math.sqrt(2) / 2, halfQuarterResult.getX(), 0.0001);
        Assertions.assertEquals(0, halfQuarterResult.getY(), 0.0001);
        Assertions.assertEquals(Math.sqrt(2) / 2, halfQuarterResult.getZ(), 0.0001);

        Assertions.assertEquals(1, fullQuarterResult.getX(), 0.0001);
        Assertions.assertEquals(0, fullQuarterResult.getY(), 0.0001);
        Assertions.assertEquals(0, fullQuarterResult.getZ(), 0.0001);
    }

    @Test
    public void rotatePointAroundZAxisScenario() {
        Tuple p = Tuple.point(0, 1, 0);

        Matrix halfQuarter = MatrixTransform.rotationZ(Math.PI / 4);
        Matrix fullQuarter = MatrixTransform.rotationZ(Math.PI / 2);

        Tuple halfQuarterResult = halfQuarter.multiply(p);
        Tuple fullQuarterResult = fullQuarter.multiply(p);

        Assertions.assertEquals(-Math.sqrt(2) / 2, halfQuarterResult.getX(), 0.0001);
        Assertions.assertEquals(Math.sqrt(2) / 2, halfQuarterResult.getY(), 0.0001);
        Assertions.assertEquals(0, halfQuarterResult.getZ(), 0.0001);

        Assertions.assertEquals(-1, fullQuarterResult.getX(), 0.0001);
        Assertions.assertEquals(0, fullQuarterResult.getY(), 0.0001);
        Assertions.assertEquals(0, fullQuarterResult.getZ(), 0.0001);
    }

    @Test
    public void shearingTransformationScenario() {
        Tuple p = Tuple.point(2, 3, 4);

        Matrix transform = MatrixTransform.shearing(1, 0, 0, 0, 0, 0);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(5, result.getX(), 0.0001);
        Assertions.assertEquals(3, result.getY(), 0.0001);
        Assertions.assertEquals(4, result.getZ(), 0.0001);
    }

    @Test
    public void shearingMovesXInProportionToY() {
        Tuple p = Tuple.point(2, 3, 4);

        Matrix transform = MatrixTransform.shearing(1, 0, 0, 0, 0, 0);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(5, result.getX(), 0.0001);
        Assertions.assertEquals(3, result.getY(), 0.0001);
        Assertions.assertEquals(4, result.getZ(), 0.0001);
    }

    @Test
    public void shearingMovesXInProportionToZ() {
        Tuple p = Tuple.point(2, 3, 4);

        Matrix transform = MatrixTransform.shearing(0, 1, 0, 0, 0, 0);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(6, result.getX(), 0.0001);
        Assertions.assertEquals(3, result.getY(), 0.0001);
        Assertions.assertEquals(4, result.getZ(), 0.0001);
    }

    @Test
    public void shearingMovesYInProportionToX() {
        Tuple p = Tuple.point(2, 3, 4);

        Matrix transform = MatrixTransform.shearing(0, 0, 1, 0, 0, 0);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(2, result.getX(), 0.0001);
        Assertions.assertEquals(5, result.getY(), 0.0001);
        Assertions.assertEquals(4, result.getZ(), 0.0001);
    }

    @Test
    public void shearingMovesYInProportionToZ() {
        Tuple p = Tuple.point(2, 3, 4);

        Matrix transform = MatrixTransform.shearing(0, 0, 0, 1, 0, 0);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(2, result.getX(), 0.0001);
        Assertions.assertEquals(7, result.getY(), 0.0001);
        Assertions.assertEquals(4, result.getZ(), 0.0001);
    }

    @Test
    public void shearingMovesZInProportionToX() {
        Tuple p = Tuple.point(2, 3, 4);

        Matrix transform = MatrixTransform.shearing(0, 0, 0, 0, 1, 0);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(2, result.getX(), 0.0001);
        Assertions.assertEquals(3, result.getY(), 0.0001);
        Assertions.assertEquals(6, result.getZ(), 0.0001);
    }

    @Test
    public void shearingMovesZInProportionToY() {
        Tuple p = Tuple.point(2, 3, 4);

        Matrix transform = MatrixTransform.shearing(0, 0, 0, 0, 0, 1);

        Tuple result = transform.multiply(p);

        Assertions.assertEquals(2, result.getX(), 0.0001);
        Assertions.assertEquals(3, result.getY(), 0.0001);
        Assertions.assertEquals(7, result.getZ(), 0.0001);
    }

    @Test
    public void individualTransformationsInSequence() {
        Tuple p = Tuple.point(1, 0, 1);

        Matrix A = MatrixTransform.rotationX(Math.PI / 2);
        Matrix B = MatrixTransform.scaling(5, 5, 5);
        Matrix C = MatrixTransform.translation(10, 5, 7);

        Tuple p2 = A.multiply(p);
        Assertions.assertEquals(1, p2.getX(), 0.0001);
        Assertions.assertEquals(-1, p2.getY(), 0.0001);
        Assertions.assertEquals(0, p2.getZ(), 0.0001);

        Tuple p3 = B.multiply(p2);
        Assertions.assertEquals(5, p3.getX(), 0.0001);
        Assertions.assertEquals(-5, p3.getY(), 0.0001);
        Assertions.assertEquals(0, p3.getZ(), 0.0001);

        Tuple p4 = C.multiply(p3);
        Assertions.assertEquals(15, p4.getX(), 0.0001);
        Assertions.assertEquals(0, p4.getY(), 0.0001);
        Assertions.assertEquals(7, p4.getZ(), 0.0001);
    }

    @Test
    public void chainedTransformationsInReverseOrder() {
        Tuple p = Tuple.point(1, 0, 1);

        Matrix A = MatrixTransform.rotationX(Math.PI / 2);
        Matrix B = MatrixTransform.scaling(5, 5, 5);
        Matrix C = MatrixTransform.translation(10, 5, 7);

        Matrix T = C.multiply(B.multiply(A));
        Tuple result = T.multiply(p);

        Assertions.assertEquals(15, result.getX(), 0.0001);
        Assertions.assertEquals(0, result.getY(), 0.0001);
        Assertions.assertEquals(7, result.getZ(), 0.0001);
    }
}
