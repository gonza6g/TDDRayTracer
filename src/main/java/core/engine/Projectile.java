package core.engine;

import core.geometry.Tuple;

public class Projectile {
    private Tuple position;
    private Tuple velocity;

    public Projectile(Tuple position, Tuple velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Tuple getPosition() { return position; }

    public Tuple getVelocity() {
        return velocity;
    }

    public void setPosition(Tuple position) { this.position = position; }

    public void setVelocity(Tuple velocity) { this.velocity = velocity; }
}