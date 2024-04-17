package core.engine;

import core.geometry.Tuple;

public class Environment {
    private Tuple gravity;
    private Tuple wind;

    public Environment(Tuple gravity, Tuple wind) {
        this.gravity = gravity;
        this.wind = wind;
    }

    public Tuple getGravity() {
        return gravity;
    }

    public Tuple getWind() {
        return wind;
    }

    public Tuple getAcceleration() {
        return Tuple.add(this.gravity, this.wind);
    }
}