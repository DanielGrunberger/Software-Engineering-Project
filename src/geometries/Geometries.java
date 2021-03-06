package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents every Geometry in 3D space
 *
 * @author David Zimberknopf and Daniel Grunberger
 */
public class Geometries implements Intersectables {

    private List<Intersectables> _geometries;

    /**
     * Default constructor
     */
    public Geometries() {
        //  ArrayList is better for storing and accessing data.
        _geometries = new ArrayList<>();
    }

    /**
     * Initialize the geometries based on the geometries received
     * @param geometries
     */
    public Geometries(Intersectables... geometries) {
        _geometries = new ArrayList<>();
        add(geometries);
    }

    /**
     * Add new geometries
     * @param geometries
     */
    public void add(Intersectables... geometries) {
        for (Intersectables geometry : geometries) {
            _geometries.add(geometry);
        }
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray, double maxDistance) {
        if (_geometries.isEmpty()) return null;

        List<GeoPoint> intersections = null;

        for (Intersectables geometry : _geometries) {
            List<GeoPoint> geometryIntersections = geometry.findIntersections(ray, maxDistance);

            if (geometryIntersections != null) {
                if (intersections == null)
                    intersections = new ArrayList<GeoPoint>();
                intersections.addAll(geometryIntersections);
            }
        }

        return intersections;
    }
}
