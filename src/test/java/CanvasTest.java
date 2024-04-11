import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CanvasTest {

    @Test
    public void testCanvasCreation() {
        Canvas canvas = new Canvas(10, 20);

        Assertions.assertEquals(10, canvas.width);
        Assertions.assertEquals(20, canvas.height);

        Color color = new Color(0, 0, 0);
        for (int y = 0; y < canvas.height; y++) {
            for (int x = 0; x < canvas.width; x++) {
                Assertions.assertEquals(color, canvas.getPixel(x, y));
            }
        }
    }

    @Test
    public void testWritingPixels() {
        Canvas canvas = new Canvas(10, 20);
        Color red = new Color(1, 0, 0);

        canvas.setPixelAt(2, 3, red);

        Color pixelColor = canvas.getPixel(2, 3);
        Assertions.assertEquals(1.0, pixelColor.getRed(), 0.01);
        Assertions.assertEquals(0.0, pixelColor.getGreen(), 0.01);
        Assertions.assertEquals(0.0, pixelColor.getBlue(), 0.01);
    }

    @Test
    public void constructingThePPMHeader() {
        Canvas canvas = new Canvas(5, 3);
        String ppm = Canvas.canvasToPPM(canvas);
        String[] lines = ppm.split("\n");
        Assertions.assertEquals("P3", lines[0]);
        Assertions.assertEquals("5 3", lines[1]);
        Assertions.assertEquals("255", lines[2]);
    }

    @Test
    public void constructingThePPMPixelData() {
        Canvas canvas = new Canvas(5, 3);
        Color c1 = new Color(1.5, 0, 0);
        Color c2 = new Color(0, 0.5, 0);
        Color c3 = new Color(-0.5, 0, 1);

        canvas.setPixelAt(0, 0, c1);
        canvas.setPixelAt(2, 1, c2);
        canvas.setPixelAt(4, 2, c3);

        String ppm = Canvas.canvasToPPM(canvas);
        String[] lines = ppm.split("\n");

        Assertions.assertEquals("255 0 0 0 0 0 0 0 0 0 0 0 0 0 0", lines[3]);
        Assertions.assertEquals("0 0 0 0 0 0 0 128 0 0 0 0 0 0 0", lines[4]);
        Assertions.assertEquals("0 0 0 0 0 0 0 0 0 0 0 0 0 0 255", lines[5]);
    }
}
