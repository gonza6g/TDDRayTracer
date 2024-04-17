package examples;

import core.*;
import draw.Canvas;
import draw.Color;
import shape.*;
import utils.FileUtils;

public class VioletSphereExample {
    public static void main(String[] args) {
        // Define constants and variables
        int canvasWidth = 300;
        int canvasHeight = 300;
        double wallSize = 7.0;
        double half = wallSize / 2.0;
        double pixelSize = wallSize / canvasWidth;
        double wallZ = 10; // Adjust wallZ if needed

        // Create a canvas
        Canvas canvas = new Canvas(canvasWidth, canvasHeight);

        Tuple rayOrigin = Tuple.point(0, 0, -5);

        // Create a sphere and add a material to it
        Material m = new Material();
        m.setColor(new Color(1.0, 0.2, 1.0));
        Sphere s = new Sphere();
        s.setMaterial(m);

        // Set the lights
        Tuple lightPosition = Tuple.vector(0.0, 0.0, -10.0);
        Color lightColor = Color.WHITE;
        PointLight light = new PointLight(lightPosition, lightColor);

        // For each row of pixels in the canvas
        for (int y = 0; y < canvasHeight; y++) {
            // Compute the world y coordinate
            double worldY = half - pixelSize * y;
            // For each pixel in the row
            for (int x = 0; x < canvasWidth; x++) {
                // Describe the point on the wall that the ray will target
                double worldX = -half + pixelSize * x;
                Tuple position = Tuple.point(worldX, worldY, wallZ);

                // Create the ray direction
                Tuple direction = Tuple.subtract(position, rayOrigin);
                direction = Tuple.normalize(direction);

                // Create the ray
                Ray r = new Ray(rayOrigin, direction);

                // Intersect the ray with the sphere
                Intersections xs = r.intersect(s, s.getTransform());

                // Check if the ray hits the sphere
                if (xs.size() > 0) {
                    // Find the CLOSEST intersection (assuming xs.hit() returns it)
                    Intersection hit = xs.hit();
                    Shape hitObject = xs.hit().getObject();
                    Tuple hitPoint = Ray.position(r, hit.getT());

                    // Find the normal vector at the hit point of the Sphere
                    Tuple normal = NormalCalculator.normalAt((Sphere) hitObject, hitPoint);

                    // Calculate the eye vector
                    Tuple eye = Tuple.negate(direction);

                    // Calculate the color with the lighting function
                    Color color = m.lighting(light, hitPoint, eye, normal);

                    // Write the color to the canvas
                    canvas.setPixelAt(x, y, color);
                }
            }
        }

        // Convert the canvas to PPM format
        String ppmData = Canvas.canvasToPPM(canvas);

        // Save the PPM data to a file
        try {
            FileUtils.writeBytesToFile("violetSphere.ppm", ppmData.getBytes());
            System.out.println("Canvas saved to: violetSphere.ppm");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
