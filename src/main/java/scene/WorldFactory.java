package scene;

import core.geometry.MatrixTransform;
import core.geometry.Tuple;
import core.lighting.PointLight;
import shape.Sphere;
import draw.Color;

public class WorldFactory {
    public static World defaultWorld() {
        World world = new World();

        PointLight light = new PointLight(Tuple.point(-10, 10, -10), new Color(1, 1, 1));
        world.setLight(light);

        Sphere s1 = sphere();
        s1.getMaterial().setColor(new Color(0.8, 1.0, 0.6));
        s1.getMaterial().setDiffuse(0.7);
        s1.getMaterial().setSpecular(0.2);
        world.addObject(s1);

        Sphere s2 = sphere();
        s2.setTransform(MatrixTransform.scaling(0.5, 0.5, 0.5));
        world.addObject(s2);

        return world;
    }

    public static Sphere sphere() {
        return new Sphere();
    }
}
