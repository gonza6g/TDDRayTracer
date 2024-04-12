class Tuple {
    private double x, y, z, w;

    public Tuple(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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
        if (t1.isVector() && t2.isPoint()) {
            throw new IllegalArgumentException("Cannot subtract a point from a vector");
        }
        return Tuple.add(t1, Tuple.negate(t2));
    }

    public static Tuple negate(Tuple t) {
        return new Tuple(-t.getX(), -t.getY(), -t.getZ(), t.getW() == 0 ? 0 : -t.getW());
    }

    public static Tuple multiply(Tuple t, double scalar) {
        return new Tuple(t.getX() * scalar, t.getY() * scalar, t.getZ() * scalar, t.getW() * scalar);
    }

    public static Tuple normalize(Tuple t) {
        double magnitude = magnitude(t);
        return new Tuple(t.getX() / magnitude, t.getY() / magnitude, t.getZ() / magnitude, t.getW() / magnitude);
    }

    public static Tuple divide(Tuple t, double scalar) {
        return new Tuple(t.getX() / scalar, t.getY() / scalar, t.getZ() / scalar, t.getW() / scalar);
    }

    public static double magnitude(Tuple t) {
        return Math.sqrt(t.getX() * t.getX() + t.getY() * t.getY() + t.getZ() * t.getZ() + t.getW() * t.getW());
    }

    public static double dot(Tuple a, Tuple b) {
        if (a.isPoint() || b.isPoint()) {
            throw new IllegalArgumentException("Dot product is only defined for vectors");
        }
        return a.getX() * b.getX() +
                a.getY() * b.getY() +
                a.getZ() * b.getZ() +
                a.getW() * b.getW();
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
        return "Tuple(" + x + ", " + y + ", " + z + ", " + w + ")";
    }
}