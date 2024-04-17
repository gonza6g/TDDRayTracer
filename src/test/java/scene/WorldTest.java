package scene;

import core.geometry.Intersection;
import core.geometry.MatrixTransform;
import core.geometry.Ray;
import core.geometry.Tuple;
import core.lighting.PointLight;
import draw.Color;
import org.junit.jupiter.api.Test;
import shape.Sphere;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldTest {
    @Test
    public void testCreatingAWorld() {
        World w = new World();
        assertTrue(w.getObjects().isEmpty());
        assertTrue(w.getLights().isEmpty());
    }

    @Test
    public void testDefaultWorld() {
        // Create the default world
        World w = WorldFactory.defaultWorld();

        // Define the expected light
        PointLight expectedLight = new PointLight(Tuple.point(-10, 10, -10), Color.WHITE);

        // Define the expected spheres
        Sphere s1 = new Sphere();
        s1.getMaterial().setColor(new Color(0.8, 1.0, 0.6));
        s1.getMaterial().setDiffuse(0.7);
        s1.getMaterial().setSpecular(0.2);

        Sphere s2 = new Sphere();
        s2.setTransform(MatrixTransform.scaling(0.5, 0.5, 0.5));

        // Assert that the world contains the expected objects
        assertEquals(expectedLight, w.getLights().get(0));
        assertTrue(w.getObjects().contains(s1));
        assertTrue(w.getObjects().contains(s2));
    }

    @Test
    public void testIntersectWorldWithRay() {
        // Given
        World w = WorldFactory.defaultWorld();
        Ray r = new Ray(Tuple.point(0, 0, -5), Tuple.vector(0, 0, 1));

        // When
        List<Intersection> xs = World.intersectWorld(w, r);

        // Then
        assertEquals(4, xs.size());
        assertEquals(4.0, xs.get(0).getT(), 0.00001);
        assertEquals(4.5, xs.get(1).getT(), 0.00001);
        assertEquals(5.5, xs.get(2).getT(), 0.00001);
        assertEquals(6.0, xs.get(3).getT(), 0.00001);
    }
}
