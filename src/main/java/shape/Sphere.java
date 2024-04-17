package shape;

import core.material.Material;
import core.geometry.Matrix;

public class Sphere implements Shape {
    private static int lastAssignedId = 0;
    private final int id;
    private Matrix transform;
    private Material material;


    public Sphere() {
        transform = Matrix.identity(4);
        this.material = new Material();
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
