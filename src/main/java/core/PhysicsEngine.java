package core;

public class PhysicsEngine {
    public static Projectile tick(Environment environment, Projectile projectile) {
        Tuple newPosition = Tuple.add(projectile.getPosition(), projectile.getVelocity());
        Tuple newVelocity = Tuple.add(Tuple.add(projectile.getVelocity(), environment.getGravity()), environment.getWind());
        return new Projectile(newPosition, newVelocity);
    }
}