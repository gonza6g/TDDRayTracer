package draw;

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
                pixels[x][y] = Color.BLACK;
            }
        }
    }

    public Color getPixelAt(int x, int y) {
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

        int maxLineLength = 70;
        int currentLineLength = 0;

        for (int y = 0; y < canvas.getHeight(); y++) {
            for (int x = 0; x < canvas.getWidth(); x++) {
                Color color = canvas.getPixelAt(x, y);
                int red = ceilInt(clampColorValue(color.getRed() * 255));
                int green = ceilInt(clampColorValue(color.getGreen() * 255));
                int blue = ceilInt(clampColorValue(color.getBlue() * 255));

                if (currentLineLength + 1 + String.valueOf(red).length() <= maxLineLength) {
                    sb.append(red);
                    currentLineLength += String.valueOf(red).length();
                } else {
                    sb.append("\n").append(red);
                    currentLineLength = String.valueOf(red).length();
                }

                if (currentLineLength + 1 + String.valueOf(green).length() <= maxLineLength) {
                    sb.append(" ").append(green);
                    currentLineLength += 1 + String.valueOf(green).length();
                } else {
                    sb.append("\n").append(green);
                    currentLineLength = String.valueOf(green).length();
                }

                if (currentLineLength + 1 + String.valueOf(blue).length() <= maxLineLength) {
                    sb.append(" ").append(blue);
                    currentLineLength += 1 + String.valueOf(blue).length();
                } else {
                    sb.append("\n").append(blue);
                    currentLineLength = String.valueOf(blue).length();
                }

                if (x < canvas.getWidth() - 1) {
                    sb.append(" ");
                    currentLineLength++;
                }
            }
            sb.append("\n");
            currentLineLength = 0;
        }

        return sb.toString();
    }

    public static int ceilInt(double value) {
        return (int) Math.ceil(value);
    }
}
