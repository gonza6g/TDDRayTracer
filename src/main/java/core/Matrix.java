package core;

import java.util.Arrays;

public class Matrix {
    private double[][] data;

    public Matrix(double[][] data) {
        this.data = data;
    }

    public double getElement(int row, int col) {
        return this.data[row][col];
    }

    public double[][] getData() {
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Matrix other = (Matrix) obj;

        if (this.data.length != other.data.length || this.data[0].length != other.data[0].length) {
            return false;
        }

        for (int i = 0; i < this.data.length; i++) {
            for (int j = 0; j < this.data[0].length; j++) {
                if (Math.abs(this.data[i][j] - other.data[i][j]) > Double.MIN_VALUE) {
                    return false;
                }
            }
        }

        return true;
    }

    public Matrix multiply(Matrix other) {
        int rowsA = this.data.length;
        int colsA = this.data[0].length;
        int colsB = other.data[0].length;

        double[][] resultData = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    resultData[i][j] += this.data[i][k] * other.data[k][j];
                }
            }
        }

        return new Matrix(resultData);
    }

    public Tuple multiply(Tuple tupleB) {
        if (data[0].length != 4) {
            throw new IllegalArgumentException("matrix.Matrix must have 4 columns for Tuple multiplication");
        }

        double xResult = data[0][0] * tupleB.getX() + data[0][1] * tupleB.getY() + data[0][2] * tupleB.getZ() + data[0][3] * tupleB.getW();
        double yResult = data[1][0] * tupleB.getX() + data[1][1] * tupleB.getY() + data[1][2] * tupleB.getZ() + data[1][3] * tupleB.getW();
        double zResult = data[2][0] * tupleB.getX() + data[2][1] * tupleB.getY() + data[2][2] * tupleB.getZ() + data[2][3] * tupleB.getW();
        double wResult = data[3][0] * tupleB.getX() + data[3][1] * tupleB.getY() + data[3][2] * tupleB.getZ() + data[3][3] * tupleB.getW();

        return new Tuple(xResult, yResult, zResult, wResult);
    }

    public static Matrix identity(int size) {
        double[][] identityMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                identityMatrix[i][j] = (i == j) ? 1 : 0;
            }
        }
        return new Matrix(identityMatrix);
    }

    public Matrix transpose() {
        int rows = data.length;
        int cols = data[0].length;

        double[][] transposedData = new double[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposedData[i][j] = data[j][i];
            }
        }

        return new Matrix(transposedData);
    }

    public double determinant() {
        if (data.length != data[0].length) {
            throw new IllegalArgumentException("matrix.Matrix should be a square matrix to calculate the determinant.");
        }

        if (data.length == 2) {
            return data[0][0] * data[1][1] - data[0][1] * data[1][0];
        }

        double determinant = 0;
        for (int j = 0; j < data.length; j++) {
            determinant += data[0][j] * cofactor(0, j);
        }
        return determinant;
    }

    public Matrix submatrix(int rowToRemove, int colToRemove) {
        int size = data.length - 1;
        double[][] newData = new double[size][size];

        for (int i = 0, k = 0; i < data.length; i++) {
            if (i == rowToRemove) continue;
            for (int j = 0, l = 0; j < data[0].length; j++) {
                if (j == colToRemove) continue;
                newData[k][l] = data[i][j];
                l++;
            }
            k++;
        }

        return new Matrix(newData);
    }

    public double minor(int row, int col) {
        Matrix submatrix = submatrix(row, col);
        return submatrix.determinant();
    }

    public double cofactor(int row, int col) {
        double minor = minor(row, col);
        return (row + col) % 2 == 0 ? minor : -minor;
    }

    public boolean isInvertible() {
        return determinant() != 0;
    }

    public Matrix inverse() {
        if (!isInvertible()) {
            throw new IllegalStateException("matrix.Matrix is not invertible.");
        }

        int size = data.length;
        double[][] inverseData = new double[size][size];
        double det = determinant();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                double c = cofactor(row, col);
                // Note the transposition of row and col
                inverseData[col][row] = c / det;
            }
        }

        return new Matrix(inverseData);
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}