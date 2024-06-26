package core.lighting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.geometry.Tuple;
import core.lighting.PointLight;
import org.junit.jupiter.api.Test;
import draw.Color;
public class LightTest {

    @Test
    public void testPointLightAttributes() {
        Color intensity = Color.WHITE;
        Tuple position = Tuple.point(0, 0, 0);
        PointLight light = new PointLight(position, intensity);
        assertEquals(position, light.getPosition());
        assertEquals(intensity, light.getIntensity());
    }
}
