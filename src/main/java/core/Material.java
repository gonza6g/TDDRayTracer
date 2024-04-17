package core;

import draw.Color;

import java.util.Objects;

public class Material {
    private Color color;
    private double ambient;
    private double diffuse;
    private double specular;
    private double shininess;

    public Material() {
        this.color = Color.WHITE;
        this.ambient = 0.1;
        this.diffuse = 0.9;
        this.specular = 0.9;
        this.shininess = 200.0;
    }

    // Getters and setters
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getAmbient() {
        return ambient;
    }

    public void setAmbient(double ambient) {
        this.ambient = ambient;
    }

    public double getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(double diffuse) {
        this.diffuse = diffuse;
    }

    public double getSpecular() {
        return specular;
    }

    public void setSpecular(double specular) {
        this.specular = specular;
    }

    public double getShininess() {
        return shininess;
    }

    public void setShininess(double shininess) {
        this.shininess = shininess;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Material material = (Material) obj;
        return Double.compare(material.ambient, ambient) == 0 &&
                Double.compare(material.diffuse, diffuse) == 0 &&
                Double.compare(material.specular, specular) == 0 &&
                Double.compare(material.shininess, shininess) == 0 &&
                Objects.equals(color, material.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, ambient, diffuse, specular, shininess);
    }

    public Color lighting(PointLight light, Tuple position, Tuple eyeVector, Tuple normalVector) {
        // combine the surface color with the light's color/intensity
        Color effectiveColor = color.multiply(light.getIntensity());

        // find the direction to the light source
        Tuple lightV = Tuple.normalize(Tuple.subtract(light.getPosition(), position));

        // compute the ambient contribution
        Color ambient = effectiveColor.scale(this.ambient);

        // need to initialize both properties
        Color diffuse;
        Color specular;

        // light_dot_normal represents the cosine of the angle
        // between the light vector and the normal vector.
        // A negative number means
        // the light is on the other side of the surface.
        double lightDotNormal = Tuple.dot(lightV, normalVector);
        if (lightDotNormal < 0) {
            diffuse = Color.BLACK;
            specular = Color.BLACK;
        } else {
            // compute the diffuse contribution
            diffuse = effectiveColor.scale(this.diffuse).scale(lightDotNormal);

            // reflect_dot_eye represents the cosine of the angle between the
            // reflection vector and the eye vector. A negative number means the
            // light reflects away from the eye.
            Tuple reflectVector = Tuple.reflect(Tuple.negate(lightV), normalVector);
            double reflectDotEye = Tuple.dot(reflectVector, eyeVector);
            if (reflectDotEye <= 0) {
                specular = Color.BLACK; // Light reflects away from the eye
            } else {
                // Compute the specular contribution with the fall-off
                double factor = Math.pow(reflectDotEye, this.shininess);
                specular = light.getIntensity().scale(this.specular).scale(factor);
            }
        }
//        System.out.println("ambient = " + ambient);
//        System.out.println("diffuse = " + diffuse);
//        System.out.println("specular = " + specular);
        return ambient.add(diffuse).add(clamp(specular));
    }

    private Color clamp(Color color) {
        double min = 0.0;
        double max = 1.0;

        double red = Math.min(Math.max(color.getRed(), min), max);
        double green = Math.min(Math.max(color.getGreen(), min), max);
        double blue = Math.min(Math.max(color.getBlue(), min), max);

        return new Color(red, blue, green);
    }
}
