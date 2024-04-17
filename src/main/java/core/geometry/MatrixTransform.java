package core.geometry;

public class MatrixTransform {

    public static Matrix translation(double x, double y, double z) {
        double[][] matrixData = {
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}
        };
        return new Matrix(matrixData);
    }

    public static Matrix scaling(double x, double y, double z) {
        double[][] matrixData = {
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}
        };
        return new Matrix(matrixData);
    }

    public static Matrix reflect() {
        return MatrixTransform.scaling(-1, 1, 1);
    }

    public static Matrix rotationX(double angle) {
        double[][] matrixData = {
                {1, 0, 0, 0},
                {0, Math.cos(angle), -Math.sin(angle), 0},
                {0, Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 0, 1}
        };
        return new Matrix(matrixData);
    }

    public static Matrix rotationY(double angle) {
        double[][] matrixData = {
                {Math.cos(angle), 0, Math.sin(angle), 0},
                {0, 1, 0, 0},
                {-Math.sin(angle), 0, Math.cos(angle), 0},
                {0, 0, 0, 1}
        };
        return new Matrix(matrixData);
    }

    public static Matrix rotationZ(double angle) {
        double[][] matrixData = {
                {Math.cos(angle), -Math.sin(angle), 0, 0},
                {Math.sin(angle), Math.cos(angle), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix(matrixData);
    }

    public static Matrix shearing(double xy, double xz, double yx, double yz, double zx, double zy) {
        double[][] matrixData = {
                {1, xy, xz, 0},
                {yx, 1, yz, 0},
                {zx, zy, 1, 0},
                {0, 0, 0, 1}
        };
        return new Matrix(matrixData);
    }
}
