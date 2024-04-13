package examples;

import core.Intersections;
import core.Ray;
import core.Tuple;
import draw.Canvas;
import draw.Color;
import shape.Sphere;
import utils.FileUtils;

public class RedCircleExample {
    public static void main(String[] args) {
        // Define constants and variables
        int canvasWidth = 100;
        int canvasHeight = 100;
        double wallSize = 7.0;
        double half = wallSize / 2.0;
        double pixelSize = wallSize / canvasWidth;
        Tuple rayOrigin = Tuple.point(0, 0, -5);
        double wallZ = 10;

        // Create a canvas
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);

        // Create a sphere
        Sphere shape = new Sphere();

        // For each row of pixels in the canvas
        for (int y = 0; y < canvasHeight; y++) {
            // Compute the world y coordinate
            double worldY = half - pixelSize * y;

            // For each pixel in the row
            for (int x = 0; x < canvasWidth; x++) {
                // Compute the world x coordinate
                double worldX = -half + pixelSize * x;

                // Describe the point on the wall that the ray will target
                Tuple position = Tuple.point(worldX, worldY, wallZ);

                // Create the ray direction
                Tuple direction = Tuple.normalize(Tuple.subtract(position, rayOrigin));

                // Create the ray
                Ray r = new Ray(rayOrigin, direction);

                // Intersect the ray with the sphere
                Intersections xs = r.intersect(shape, shape.getTransform());

                // Check if the ray hits the sphere
                if (xs.hit() != null) {
                    // Write a red pixel to the canvas
                    canvas.setPixelAt(x, y, Color.RED);
                }
            }
        }

        // Convert the canvas to PPM format
        String ppmData = Canvas.canvasToPPM(canvas);

        // Save the PPM data to a file
        try {
            FileUtils.writeBytesToFile("redCircle.ppm", ppmData.getBytes());
            System.out.println("Canvas saved to: redCircle.ppm");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
