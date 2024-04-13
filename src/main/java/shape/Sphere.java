package shape;

public class Sphere implements Shape {
    private static int lastAssignedId = 0;
    private final int id;

    public Sphere() {
        this.id = ++lastAssignedId;
    }

    public int getId() {
        return id;
    }
}
