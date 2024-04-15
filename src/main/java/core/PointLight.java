package core;

import draw.Color;
public class PointLight {
    private Tuple position;
    private Color intensity;

    public PointLight(Tuple position, Color intensity) {
        this.position = position;
        this.intensity = intensity;
    }

    public Tuple getPosition() {
        return position;
    }

    public Color getIntensity() {
        return intensity;
    }
}
