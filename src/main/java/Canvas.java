public class Canvas {
    public final int width;
    public final int height;
    private final Color[][] pixels;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new Color[width][height];

        // Initialize all pixels to black (0, 0, 0)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x][y] = new Color(0, 0, 0);
            }
        }
    }

    public Color getPixel(int x, int y) {
        if (isValidPixel(x, y)) {
            return pixels[x][y];
        } else {
            throw new IllegalArgumentException("Invalid pixel coordinates");
        }
    }

    public void setPixelAt(int x, int y, Color color) {
        if (isValidPixel(x, y)) {
            double red = clampColorValue(color.getRed());
            double green = clampColorValue(color.getGreen());
            double blue = clampColorValue(color.getBlue());

            pixels[x][y] = new Color(red, green, blue);
        } else {
            throw new IllegalArgumentException("Invalid pixel coordinates");
        }
    }

    public static double clampColorValue(double value) {
        if (value < 0) {
            return 0;
        } else if (value > 255) {
            return 255;
        } else {
            return value;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private boolean isValidPixel(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public static String canvasToPPM(Canvas canvas) {
        StringBuilder sb = new StringBuilder();
        sb.append("P3\n");
        sb.append(canvas.getWidth()).append(" ").append(canvas.getHeight()).append("\n");
        sb.append("255\n");

        for (int y = 0; y < canvas.getHeight(); y++) {
            for (int x = 0; x < canvas.getWidth(); x++) {
                Color color = canvas.getPixel(x, y);
                sb.append((int) (clampColorValue(color.getRed() * 255))).append(" ")
                        .append((int) (clampColorValue(color.getBlue() * 255))).append(" ")
                        .append((int) (clampColorValue(color.getGreen() * 255))).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
