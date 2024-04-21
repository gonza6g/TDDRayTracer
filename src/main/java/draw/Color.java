package draw;

import core.geometry.Computations;
import core.geometry.Intersection;
import core.lighting.PointLight;
import core.material.Material;
import scene.World;
import core.geometry.Ray;

import java.util.List;

import static core.geometry.Computations.prepareComputations;
import static scene.World.intersectWorld;

public class Color {
    double red;
    double green;
    double blue;

    public Color(double red, double green, double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color shadeHit(World world, Computations comps) {
        Material m = comps.getObject().getMaterial();

        Color finalColor = new Color(0, 0, 0);

        for (PointLight light : world.getLights()) {
            finalColor = m.lighting(light, comps.getPoint(), comps.getEyeVector(), comps.getNormalVector());
        }
        return finalColor;
    }

    public Color colorAt(World world, Ray ray) {
        List<Intersection> intersections = intersectWorld(world, ray);

        Intersection hit = Intersection.hit(intersections);

        if (hit == null) {
            return Color.BLACK;
        }

        Computations comps = prepareComputations(hit, ray);

        return shadeHit(world, comps);
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    public Color add(Color other) {
        return new Color(getRed() + other.getRed(), getGreen() + other.getGreen(), getBlue() + other.getBlue());
    }

    public Color subtract(Color other) {
        return new Color(getRed() - other.getRed(), getGreen() - other.getGreen(), getBlue() - other.getBlue());
    }

    public Color scale(double scalar) {
        return new Color(getRed() * scalar, getGreen() * scalar, getBlue() * scalar);
    }

    public Color multiply(Color other) {
        return new Color(getRed() * other.getRed(), getGreen() * other.getGreen(), getBlue() * other.getBlue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Double.compare(color.red, red) == 0 &&
                Double.compare(color.green, green) == 0 &&
                Double.compare(color.blue, blue) == 0;
    }

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color RED = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE = new Color(0, 0, 1);

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
