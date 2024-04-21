package scene;

import core.geometry.Intersection;
import core.geometry.Ray;
import shape.Shape;
import core.lighting.PointLight;
import shape.Sphere;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class World {
    private List<Shape> objects;
    private List<PointLight> lights;

    public World() {
        this.lights = new ArrayList<>();
        this.objects = new ArrayList<>();
    }

    public List<Shape> getObjects() {
        return this.objects;
    }

    public void addObject(Shape object) {
        this.objects.add(object);
    }

    public void setLight(PointLight light) { this.lights.add(light); }

    public List<PointLight> getLights() {
        return this.lights;
    }

    public boolean containsObject(Shape object) {
        return this.objects.contains(object);
    }

    public static List<Intersection> intersectWorld(World world, Ray ray) {
        List<Intersection> intersections = new ArrayList<>();

        for (Shape object : world.objects) {
            Sphere s = (Sphere) object;
            intersections.addAll(ray.intersect(object, s.getTransform()));
        }

        intersections.sort(Comparator.comparing(Intersection::getT));
        return intersections;
    }
}
