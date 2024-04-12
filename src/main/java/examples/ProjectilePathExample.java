package examples;

import core.Environment;
import core.Projectile;
import core.Tuple;
import draw.Canvas;
import draw.Color;
import utils.FileUtils;

public class ProjectilePathExample {
    public static void main(String[] args) {
        // Set up the initial projectile
        Tuple start = Tuple.point(0, 1, 0);
        Tuple velocity = Tuple.multiply(Tuple.normalize(Tuple.vector(1, 1.8, 0)), 11.25);
        Projectile p = new Projectile(start, velocity);

        // Set up the environment
        Tuple gravity = Tuple.vector(0, -0.1, 0);
        Tuple wind = Tuple.vector(0.01, 0, 0);
        Environment e = new Environment(gravity, wind);

        Canvas c = new Canvas(900, 550);

        while (p.getPosition().getY() >= 0) {
            int x = (int) p.getPosition().getX();
            int y = c.getHeight() - (int) p.getPosition().getY();

            // Check if the pixel is within the canvas bounds
            if (x >= 0 && x < c.getWidth() && y >= 0 && y < c.getHeight()) {
                c.setPixelAt(x, y, Color.WHITE);
            }

            // Update the projectile's position using the provided formulas
            double dt = 0.1; // Time step
            Tuple acceleration = e.getAcceleration();
            Tuple newVelocity = Tuple.add(p.getVelocity(), Tuple.multiply(acceleration, dt));
            Tuple newPosition = Tuple.add(Tuple.add(p.getPosition(), Tuple.multiply(p.getVelocity(), dt)), Tuple.multiply(acceleration, 0.5 * dt * dt));

            p.setPosition(newPosition);
            p.setVelocity(newVelocity);
        }
        String ppmData = Canvas.canvasToPPM(c);
        FileUtils.writeBytesToFile("projectile_path.ppm", ppmData.getBytes());
        System.out.println("draw.Canvas saved to: projectile_path.ppm");
    }
}