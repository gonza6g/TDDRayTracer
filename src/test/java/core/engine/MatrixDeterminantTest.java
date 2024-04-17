package core.engine;

import core.geometry.Matrix;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class MatrixDeterminantTest {

    @Test
    public void determinantOf2x2MatrixScenario() {
        double[][] matrixData = {
                {1, 5},
                {-3, 2}
        };

        Matrix matrixA = new Matrix(matrixData);

        double determinant = matrixA.determinant();

        Assertions.assertEquals(17, determinant, 0.001);
    }

    @Test
    public void submatrixOf3x3MatrixScenario() {
        double[][] matrixData = {
                {1, 5, 0},
                {-3, 2, 7},
                {0, 6, -3}
        };

        Matrix matrixA = new Matrix(matrixData);

        double[][] expectedData = {
                {-3, 2},
                {0, 6}
        };

        Matrix expectedMatrix = new Matrix(expectedData);

        Matrix submatrix = matrixA.submatrix(0, 2);

        // Compare each element of submatrix with expectedMatrix
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Assertions.assertEquals(expectedData[i][j], submatrix.getElement(i, j), 0.001);
            }
        }
    }

    @Test
    public void submatrixOf4x4MatrixScenario() {
        double[][] matrixData = {
                {-6, 1, 1, 6},
                {-8, 5, 8, 6},
                {-1, 0, 8, 2},
                {-7, 1, -1, 1}
        };

        Matrix matrixA = new Matrix(matrixData);

        double[][] expectedData = {
                {-6, 1, 6},
                {-8, 8, 6},
                {-7, -1, 1}
        };

        Matrix expectedMatrix = new Matrix(expectedData);

        Matrix submatrix = matrixA.submatrix(2, 1);

        // Compare each element of submatrix with expectedMatrix
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Assertions.assertEquals(expectedData[i][j], submatrix.getElement(i, j), 0.001);
            }
        }
    }

    @Test
    public void minorOf3x3MatrixScenario() {
        double[][] matrixData = {
                {3, 5, 0},
                {2, -1, -7},
                {6, -1, 5}
        };

        Matrix matrixA = new Matrix(matrixData);
        Matrix submatrixB = matrixA.submatrix(1, 0);

        double determinantB = submatrixB.determinant();

        Assertions.assertEquals(25, determinantB, 0.001);

        double minorA = matrixA.minor(1, 0);

        Assertions.assertEquals(25, minorA, 0.001);
    }

    @Test
    public void cofactorOf3x3MatrixScenario() {
        double[][] matrixData = {
                {3, 5, 0},
                {2, -1, -7},
                {6, -1, 5}
        };

        Matrix matrixA = new Matrix(matrixData);

        double minor00 = matrixA.minor(0, 0);
        Assertions.assertEquals(-12, minor00, 0.001);

        double cofactor00 = matrixA.cofactor(0, 0);
        Assertions.assertEquals(-12, cofactor00, 0.001);

        double minor10 = matrixA.minor(1, 0);
        Assertions.assertEquals(25, minor10, 0.001);

        double cofactor10 = matrixA.cofactor(1, 0);
        Assertions.assertEquals(-25, cofactor10, 0.001);
    }

    @Test
    public void determinantOf3x3MatrixScenario() {
        double[][] matrixData = {
                {1, 2, 6},
                {-5, 8, -4},
                {2, 6, 4}
        };

        Matrix matrixA = new Matrix(matrixData);

        double cofactor00 = matrixA.cofactor(0, 0);
        Assertions.assertEquals(56, cofactor00, 0.001);

        double cofactor01 = matrixA.cofactor(0, 1);
        Assertions.assertEquals(12, cofactor01, 0.001);

        double cofactor02 = matrixA.cofactor(0, 2);
        Assertions.assertEquals(-46, cofactor02, 0.001);

        double determinantA = matrixA.determinant();
        Assertions.assertEquals(-196, determinantA, 0.001);
    }

    @Test
    public void determinantOf4x4MatrixScenario() {
        double[][] matrixData = {
                {-2, -8, 3, 5},
                {-3, 1, 7, 3},
                {1, 2, -9, 6},
                {-6, 7, 7, -9}
        };

        Matrix matrixA = new Matrix(matrixData);

        double cofactor00 = matrixA.cofactor(0, 0);
        Assertions.assertEquals(690, cofactor00, 0.001);

        double cofactor01 = matrixA.cofactor(0, 1);
        Assertions.assertEquals(447, cofactor01, 0.001);

        double cofactor02 = matrixA.cofactor(0, 2);
        Assertions.assertEquals(210, cofactor02, 0.001);

        double cofactor03 = matrixA.cofactor(0, 3);
        Assertions.assertEquals(51, cofactor03, 0.001);

        double determinantA = matrixA.determinant();
        Assertions.assertEquals(-4071, determinantA, 0.001);
    }

    @Test
    public void invertibleMatrixScenario() {
        double[][] matrixData = {
                {6, 4, 4, 4},
                {5, 5, 7, 6},
                {4, -9, 3, -7},
                {9, 1, 7, -6}
        };

        Matrix matrixA = new Matrix(matrixData);

        double determinantA = matrixA.determinant();
        Assertions.assertEquals(-2120, determinantA, 0.001);

        boolean invertible = matrixA.isInvertible();
        Assertions.assertTrue(invertible);
    }

    @Test
    public void nonInvertibleMatrixScenario() {
        double[][] matrixData = {
                {-4, 2, -2, -3},
                {9, 6, 2, 6},
                {0, -5, 1, -5},
                {0, 0, 0, 0}
        };

        Matrix matrixA = new Matrix(matrixData);

        double determinantA = matrixA.determinant();
        Assertions.assertEquals(0, determinantA, 0.001);

        boolean invertible = matrixA.isInvertible();
        Assertions.assertFalse(invertible);
    }

    @Test
    public void inverseOfMatrix1Scenario() {
        double[][] matrixData = {
                {8, -5, 9, 2},
                {7, 5, 6, 1},
                {-6, 0, 9, 6},
                {-3, 0, -9, -4}
        };

        double[][] expectedData = {
                {-0.15385, -0.15385, -0.28205, -0.53846},
                {-0.07692, 0.12308, 0.02564, 0.03077},
                {0.35897, 0.35897, 0.43590, 0.92308},
                {-0.69231, -0.69231, -0.76923, -1.92308}
        };

        Matrix matrixA = new Matrix(matrixData);
        Matrix inverseA = matrixA.inverse();

        assertMatrixEquals(expectedData, inverseA.getData());
    }

    @Test
    public void inverseOfMatrix2Scenario() {
        double[][] matrixData = {
                {9, 3, 0, 9},
                {-5, -2, -6, -3},
                {-4, 9, 6, 4},
                {-7, 6, 6, 2}
        };

        double[][] expectedData = {
                {-0.04074, -0.07778, 0.14444, -0.22222},
                {-0.07778, 0.03333, 0.36667, -0.33333},
                {-0.02901, -0.14630, -0.10926, 0.12963},
                {0.17778, 0.06667, -0.26667, 0.33333}
        };

        Matrix matrixA = new Matrix(matrixData);
        Matrix inverseA = matrixA.inverse();

        assertMatrixEquals(expectedData, inverseA.getData());
    }

    @Test
    public void productByInverseScenario() {
        double[][] matrixAData = {
                {3, -9, 7, 3},
                {3, -8, 2, -9},
                {-4, 4, 4, 1},
                {-6, 5, -1, 1}
        };

        double[][] matrixBData = {
                {8, 2, 2, 2},
                {3, -1, 7, 0},
                {7, 0, 5, 4},
                {6, -2, 0, 5}
        };

        Matrix matrixA = new Matrix(matrixAData);
        Matrix matrixB = new Matrix(matrixBData);

        Matrix matrixC = matrixA.multiply(matrixB);
        Matrix inverseB = matrixB.inverse();

        Matrix product = matrixC.multiply(inverseB);

        assertMatrixEquals(matrixAData, product.getData());
    }

    // Helper method to compare matrices
    private void assertMatrixEquals(double[][] expected, double[][] actual) {
        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected[0].length; j++) {
                Assertions.assertEquals(expected[i][j], actual[i][j], 0.0001);
            }
        }
    }
}
