package shape;

import core.material.Material;
import core.geometry.Matrix;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Sphere other = (Sphere) obj;
        return this.getMaterial().equals(other.getMaterial()) &&
                this.getTransform().equals(other.getTransform());
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, transform);
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "id=" + id +
                ", transform=" + transform +
                ", material=" + material +
                '}';
    }
}
