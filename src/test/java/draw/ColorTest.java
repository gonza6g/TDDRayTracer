package draw;

import draw.Color;
import org.junit.Test;

import static org.junit.Assert.*;
public class ColorTest {
    @Test
    public void colorsAreTuples() {
        Color c = new Color(-0.5, 0.4, 1.7);
        assertEquals(-0.5, c.getRed(), 0.0001);
        assertEquals(0.4, c.getGreen(), 0.0001);
        assertEquals(1.7, c.getBlue(), 0.0001);
    }

    @Test
    public void addingColors() {
        Color c1 = new Color(0.9, 0.6, 0.75);
        Color c2 = new Color(0.7, 0.1, 0.25);
        Color result = c1.add(c2);
        assertEquals(1.6, result.getRed(), 0.0001);
        assertEquals(0.7, result.getGreen(), 0.0001);
        assertEquals(1.0, result.getBlue(), 0.0001);
    }

    @Test
    public void subtractingColors() {
        Color c1 = new Color(0.9, 0.6, 0.75);
        Color c2 = new Color(0.7, 0.1, 0.25);
        Color result = c1.subtract(c2);
        assertEquals(0.2, result.getRed(), 0.0001);
        assertEquals(0.5, result.getGreen(), 0.0001);
        assertEquals(0.5, result.getBlue(), 0.0001);
    }

    @Test
    public void scalingColor() {
        Color c = new Color(0.2, 0.3, 0.4);
        Color result = c.scale(2);
        assertEquals(0.4, result.getRed(), 0.0001);
        assertEquals(0.6, result.getGreen(), 0.0001);
        assertEquals(0.8, result.getBlue(), 0.0001);
    }

    @Test
    public void testMultiplyColors() {
        Color c1 = new Color(1, 0.2, 0.4);
        Color c2 = new Color(0.9, 1, 0.1);
        Color expected = new Color(0.9, 0.2, 0.04);

        Color result = c1.multiply(c2);

        assertEquals(expected.getRed(), result.getRed(), 0.0001);
        assertEquals(expected.getGreen(), result.getGreen(), 0.0001);
        assertEquals(expected.getBlue(), result.getBlue(), 0.0001);
    }
}
