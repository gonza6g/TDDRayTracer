package shape;

import core.Matrix;
import core.MatrixTransform;

public class Sphere implements Shape {
    private static int lastAssignedId = 0;
    private final int id;
    private Matrix transform;

    public Sphere() {
        transform = Matrix.identity(4);
        this.id = ++lastAssignedId;
    }

    public int getId() {
        return id;
    }

    public Matrix getTransform() {
        return transform;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
    }
}
