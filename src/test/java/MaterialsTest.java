import core.Material;
import org.junit.Test;

import draw.Color;

import static org.junit.Assert.assertEquals;

public class MaterialsTest {

    @Test
    public void testDefaultMaterial() {
        Material m = new Material();
        assertEquals(Color.WHITE, m.getColor());
        assertEquals(0.1, m.getAmbient(), 0.00001);
        assertEquals(0.9, m.getDiffuse(), 0.00001);
        assertEquals(0.9, m.getSpecular(), 0.00001);
        assertEquals(200.0, m.getShininess(), 0.00001);
    }
}
