package core.geometry;

public class Tuple {
    private double x, y, z, w;

    public Tuple(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static Tuple reflect(Tuple in, Tuple normal) {
        if (in.isVector() && normal.isVector()) {
            // Round the x, y, and z components to 0 if they are very close to 0
            double x = Math.abs(in.x) < 1e-9 ? 0.0 : in.x;
            double y = Math.abs(in.y) < 1e-9 ? 0.0 : in.y;
            double z = Math.abs(in.z) < 1e-9 ? 0.0 : in.z;
            Tuple inVector = new Tuple(x, y, z, 0.0);
            double dotProduct = Tuple.dot(inVector, normal) * 2;
            Tuple reflection = Tuple.subtract(inVector, Tuple.multiply(normal, dotProduct));
            // Round the components to a certain number of decimal places
            x = Math.round(reflection.getX() * 1000000.0) / 1000000.0;
            y = Math.round(reflection.getY() * 1000000.0) / 1000000.0;
            z = Math.round(reflection.getZ() * 1000000.0) / 1000000.0;
            return new Tuple(x, y, z, 0.0); // Assuming the w component is always 0
        } else if (in.isPoint() && normal.isVector()) {
            Tuple inVector = Tuple.vector(in.x, in.y, in.z);
            double dotProduct = Tuple.dot(inVector, normal) * 2;
            Tuple reflection = Tuple.subtract(inVector, Tuple.multiply(normal, dotProduct));
            // Round the components to a certain number of decimal places
            double x = Math.round(reflection.getX() * 1000000.0) / 1000000.0;
            double y = Math.round(reflection.getY() * 1000000.0) / 1000000.0;
            double z = Math.round(reflection.getZ() * 1000000.0) / 1000000.0;
            return new Tuple(x, y, z, 0.0); // Assuming the w component is always 0
        } else if (in.w == 0.0 || in.w == 1.0) {
            // The in tuple has a valid w component (0 for vector, 1 for point)
            double dotProduct = Tuple.dot(in, normal) * 2;
            Tuple reflection = Tuple.subtract(in, Tuple.multiply(normal, dotProduct));
            // Round the components to a certain number of decimal places
            double x = Math.round(reflection.getX() * 1000000.0) / 1000000.0;
            double y = Math.round(reflection.getY() * 1000000.0) / 1000000.0;
            double z = Math.round(reflection.getZ() * 1000000.0) / 1000000.0;
            return new Tuple(x, y, z, in.w); // Preserve the original w component
        } else {
            System.out.println("in = " + in);
            System.out.println("normal = " + normal);
            throw new IllegalArgumentException("Invalid Tuple types for reflection.");
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    public boolean isPoint() {
        return w == 1.0;
    }

    public boolean isVector() {
        return w == 0.0;
    }

    public static Tuple point(double x, double y, double z) {
        return new Tuple(x, y, z, 1.0);
    }

    public static Tuple vector(double x, double y, double z) {
        return new Tuple(x, y, z, 0.0);
    }

    public static boolean tupleEquals(Tuple t1, Tuple t2) {
        final double EPSILON = 0.00001;
        return Math.abs(t1.getX() - t2.getX()) < EPSILON &&
                Math.abs(t1.getY() - t2.getY()) < EPSILON &&
                Math.abs(t1.getZ() - t2.getZ()) < EPSILON &&
                Math.abs(t1.getW() - t2.getW()) < EPSILON;
    }

    public static Tuple add(Tuple t1, Tuple t2) {
        if (t1.isPoint() && t2.isPoint()) {
            throw new IllegalArgumentException("Cannot add two points.");
        }
        return new Tuple(
                t1.getX() + t2.getX(),
                t1.getY() + t2.getY(),
                t1.getZ() + t2.getZ(),
                t1.getW() + t2.getW()
        );
    }

    public static Tuple subtract(Tuple t1, Tuple t2) {
        if (t1.isPoint() && t2.isVector()) {
            return new Tuple(t1.x - t2.x, t1.y - t2.y, t1.z - t2.z, 1.0);
        } else if (t1.isPoint() && t2.isPoint()) {
            return new Tuple(t1.x - t2.x, t1.y - t2.y, t1.z - t2.z, 0.0);
        } else if (t1.isVector() && t2.isVector()) {
            return new Tuple(t1.x - t2.x, t1.y - t2.y, t1.z - t2.z, 0.0);
        } else if (t1.isVector() && t2.isPoint()) {
            return new Tuple(t1.x - t2.x, t1.y - t2.y, t1.z - t2.z, 0.0);
        } else {
            throw new IllegalArgumentException("Invalid Tuple types for subtraction.");
        }
    }

    public static Tuple negate(Tuple t) {
        if (t.isVector()) {
            double x = t.x != 0.0 ? -t.x : 0.0;
            double y = t.y != 0.0 ? -t.y : 0.0;
            double z = t.z != 0.0 ? -t.z : 0.0;
            return new Tuple(x, y, z, 0.0);
        } else {
            return new Tuple(-t.x, -t.y, -t.z, 1.0);
        }
    }

    public static Tuple multiply(Tuple t, double scalar) {
        return new Tuple(t.getX() * scalar, t.getY() * scalar, t.getZ() * scalar, t.getW() * scalar);
    }

    public static Tuple normalize(Tuple t) {
        double magnitude = magnitude(t);
        if (t.isVector()) {
            return new Tuple(t.x / magnitude, t.y / magnitude, t.z / magnitude, 0.0);
        } else {
            return new Tuple(t.x / magnitude, t.y / magnitude, t.z / magnitude, t.w);
        }
    }

    public static Tuple divide(Tuple t, double scalar) {
        return new Tuple(t.getX() / scalar, t.getY() / scalar, t.getZ() / scalar, t.getW() / scalar);
    }

    public static double magnitude(Tuple t) {
        return Math.sqrt(t.getX() * t.getX() + t.getY() * t.getY() + t.getZ() * t.getZ() + t.getW() * t.getW());
    }

    public static double dot(Tuple t1, Tuple t2) {
        if (t1.isVector() && t2.isVector()) {
            return t1.x * t2.x + t1.y * t2.y + t1.z * t2.z;
        } else if (t1.isPoint() && t2.isVector()) {
            return t1.x * t2.x + t1.y * t2.y + t1.z * t2.z;
        } else if (t1.isVector() && t2.isPoint()) {
            return t1.x * t2.x + t1.y * t2.y + t1.z * t2.z;
        } else {
            throw new IllegalArgumentException("Dot product is only defined for vectors.");
        }
    }

    public static Tuple cross(Tuple a, Tuple b) {
        if (a.isPoint() || b.isPoint()) {
            throw new IllegalArgumentException("Cross product is only defined for vectors");
        }

        double x = a.getY() * b.getZ() - a.getZ() * b.getY();
        double y = a.getZ() * b.getX() - a.getX() * b.getZ();
        double z = a.getX() * b.getY() - a.getY() * b.getX();

        return Tuple.vector(x, y, z);
    }

    @Override
    public String toString() {
        return "core.geometry.Tuple(" + x + ", " + y + ", " + z + ", " + w + ")";
    }
}