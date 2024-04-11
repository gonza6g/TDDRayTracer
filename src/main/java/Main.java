public class Main {
    public static void main(String[] args) {
        // Set up the initial projectile
        Tuple initialPosition = Tuple.point(0, 1, 0);
        Tuple initialVelocity = Tuple.vector(1, 1, 0);
        Projectile projectile = new Projectile(initialPosition, initialVelocity);

        // Set up the environment
        Tuple gravity = Tuple.vector(0, -0.1, 0);
        Tuple wind = Tuple.vector(0.1, 0, 0);
        Environment environment = new Environment(gravity, wind);

        // Simulate the projectile over time
        for (int i = 0; i < 10; i++) {
            projectile = PhysicsEngine.tick(environment, projectile);
            System.out.println("Projectile position: " + projectile.getPosition());
            System.out.println("Projectile velocity: " + projectile.getVelocity());
        }
    }
}