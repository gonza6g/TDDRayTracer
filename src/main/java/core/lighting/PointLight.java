package core.lighting;

import core.geometry.Tuple;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PointLight that = (PointLight) obj;
        return position.equals(that.position) && intensity.equals(that.intensity);
    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + intensity.hashCode();
        return result;
    }
}
