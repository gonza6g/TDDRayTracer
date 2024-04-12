import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatrixTest {
    @Test
    public void a4x4MatrixOughtToBeRepresentable() {
        double[][] matrixData = {
                {1, 2, 3, 4},
                {5.5, 6.5, 7.5, 8.5},
                {9, 10, 11, 12},
                {13.5, 14.5, 15.5, 16.5}
        };

        Matrix M = new Matrix(matrixData);

        Assertions.assertEquals(1, M.getElement(0, 0));
        Assertions.assertEquals(4, M.getElement(0, 3));
        Assertions.assertEquals(5.5, M.getElement(1, 0));
        Assertions.assertEquals(7.5, M.getElement(1, 2));
        Assertions.assertEquals(11, M.getElement(2, 2));
        Assertions.assertEquals(13.5, M.getElement(3, 0));
        Assertions.assertEquals(15.5, M.getElement(3, 2));
    }

    @Test
    public void a2x2MatrixOughtToBeRepresentable() {
        double[][] matrixData = {
                {-3, 5},
                {1, -2}
        };

        Matrix M = new Matrix(matrixData);

        Assertions.assertEquals(-3, M.getElement(0, 0));
        Assertions.assertEquals(5, M.getElement(0, 1));
        Assertions.assertEquals(1, M.getElement(1, 0));
        Assertions.assertEquals(-2, M.getElement(1, 1));
    }

    @Test
    public void a3x3MatrixOughtToBeRepresentable() {
        double[][] matrixData = {
                {-3, 5, 0},
                {1, -2, -7},
                {0, 1, 1}
        };

        Matrix M = new Matrix(matrixData);

        Assertions.assertEquals(-3, M.getElement(0, 0));
        Assertions.assertEquals(-2, M.getElement(1, 1));
        Assertions.assertEquals(1, M.getElement(2, 2));
    }

    @Test
    public void matrixEqualityWithIdenticalMatrices() {
        double[][] matrixAData = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        };

        double[][] matrixBData = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        };

        Matrix A = new Matrix(matrixAData);
        Matrix B = new Matrix(matrixBData);

        Assertions.assertTrue(A.equals(B));
    }

    @Test
    public void matrixEqualityWithDifferentMatrices() {
        double[][] matrixAData = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        };

        double[][] matrixBData = {
                {2, 3, 4, 5},
                {6, 7, 8, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };

        Matrix A = new Matrix(matrixAData);
        Matrix B = new Matrix(matrixBData);

        Assertions.assertFalse(A.equals(B));
    }

    @Test
    public void matrixMultiplication() {
        double[][] matrixAData = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        };

        double[][] matrixBData = {
                {-2, 1, 2, 3},
                {3, 2, 1, -1},
                {4, 3, 6, 5},
                {1, 2, 7, 8}
        };

        double[][] expectedResultData = {
                {20, 22, 50, 48},
                {44, 54, 114, 108},
                {40, 58, 110, 102},
                {16, 26, 46, 42}
        };

        Matrix A = new Matrix(matrixAData);
        Matrix B = new Matrix(matrixBData);
        Matrix expectedResult = new Matrix(expectedResultData);

        Matrix actualResult = A.multiply(B);

        Assertions.assertTrue(expectedResult.equals(actualResult));
    }

    @Test
    public void matrixTupleMultiplicationScenario() {
        double[][] matrixData = {
                {1, 2, 3, 4},
                {2, 4, 4, 2},
                {8, 6, 4, 1},
                {0, 0, 0, 1}
        };

        Matrix matrixA = new Matrix(matrixData);
        Tuple tupleB = new Tuple(1, 2, 3, 1);

        Tuple expected = new Tuple(18, 24, 33, 1);
        Tuple result = matrixA.multiply(tupleB);

        Assertions.assertEquals(expected.getX(), result.getX(), 0.001);
        Assertions.assertEquals(expected.getY(), result.getY(), 0.001);
        Assertions.assertEquals(expected.getZ(), result.getZ(), 0.001);
        Assertions.assertEquals(expected.getW(), result.getW(), 0.001);
    }

    @Test
    public void matrixIdentityMultiplicationScenario() {
        double[][] matrixData = {
                {0, 1, 2, 4},
                {1, 2, 4, 8},
                {2, 4, 8, 16},
                {4, 8, 16, 32}
        };

        Matrix matrixA = new Matrix(matrixData);
        Matrix identityMatrix = Matrix.identity(4);

        Matrix result = matrixA.multiply(identityMatrix);

        // Compare each element of result with matrixA
        for (int i = 0; i < matrixData.length; i++) {
            for (int j = 0; j < matrixData[0].length; j++) {
                Assertions.assertEquals(matrixData[i][j], result.getElement(i, j), 0.001);
            }
        }
    }

    @Test
    public void identityMatrixTupleMultiplicationScenario() {
        Tuple tupleA = new Tuple(1, 2, 3, 4);
        Matrix identityMatrix = Matrix.identity(4);

        Tuple result = identityMatrix.multiply(tupleA);

        Assertions.assertEquals(tupleA.getX(), result.getX(), 0.001);
        Assertions.assertEquals(tupleA.getY(), result.getY(), 0.001);
        Assertions.assertEquals(tupleA.getZ(), result.getZ(), 0.001);
        Assertions.assertEquals(tupleA.getW(), result.getW(), 0.001);
    }

    @Test
    public void matrixTranspositionScenario() {
        double[][] matrixData = {
                {0, 9, 3, 0},
                {9, 8, 0, 8},
                {1, 8, 5, 3},
                {0, 0, 5, 8}
        };

        Matrix matrixA = new Matrix(matrixData);

        double[][] expectedData = {
                {0, 9, 1, 0},
                {9, 8, 8, 0},
                {3, 0, 5, 5},
                {0, 8, 3, 8}
        };

        Matrix expectedMatrix = new Matrix(expectedData);

        Matrix transposedMatrix = matrixA.transpose();

        // Compare each element of transposedMatrix with expectedMatrix
        for (int i = 0; i < matrixData.length; i++) {
            for (int j = 0; j < matrixData[0].length; j++) {
                Assertions.assertEquals(expectedData[i][j], transposedMatrix.getElement(i, j), 0.001);
            }
        }
    }

    @Test
    public void matrixIdentityTranspositionScenario() {
        Matrix identityMatrix = Matrix.identity(4);

        Matrix transposedMatrix = identityMatrix.transpose();

        // Compare each element of transposedMatrix with identityMatrix
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Assertions.assertEquals(identityMatrix.getElement(i, j), transposedMatrix.getElement(i, j), 0.001);
            }
        }
    }
}