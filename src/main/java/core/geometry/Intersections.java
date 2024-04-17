package core.geometry;

import java.util.ArrayList;
import java.util.List;

public class Intersections {

    private List<Intersection> intersections;

    public Intersections(Intersection... intersections) {
        this.intersections = new ArrayList<>();
        for (Intersection intersection : intersections) {
            this.intersections.add(intersection);
        }
    }

    public Intersection get(int index) {
        return intersections.get(index);
    }

    public int size() {
        return intersections.size();
    }

    public void add(Intersection intersection) {
        intersections.add(intersection);
    }

    public Intersection hit() {
        Intersection hit = null;
        double minT = Double.MAX_VALUE;

        for (Intersection intersection : intersections) {
            if (intersection.getT() >= 0 && intersection.getT() < minT) {
                minT = intersection.getT();
                hit = intersection;
            }
        }

        return hit;
    }
}
