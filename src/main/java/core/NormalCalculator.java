package core;

import shape.Sphere;

public class NormalCalculator {

    public static Tuple normalAt(Sphere sphere, Tuple worldSpacePoint) {
        Tuple objectSpacePoint = sphere.getTransform().inverse().multiply(worldSpacePoint);
        Tuple worldNormal = sphere.getTransform().inverse().transpose().multiply(objectSpacePoint);
        worldNormal = Tuple.vector(worldNormal.getX(), worldNormal.getY(), worldNormal.getZ());
        return Tuple.normalize(worldNormal);
    }
}
