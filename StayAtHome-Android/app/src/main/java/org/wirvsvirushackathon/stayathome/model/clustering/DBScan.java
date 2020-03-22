package org.wirvsvirushackathon.stayathome.model.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBScan<T extends Clusterable<T>> {

    /**
     * Radius of the neighborhood for expanding clusters
     */
    private final double eps;

    /**
     * Minimum number of points in a cluster.
     */
    private final int minPts;

    /**
     * @param eps    radius of the neighborhood for expanding clusters
     * @param minPts minimum number of points in a cluster
     */
    public DBScan(final double eps, final int minPts) {
        this.eps = eps;
        this.minPts = minPts;
    }

    /**
     * Returns the radius of the neighborhood for expanding clusters
     *
     * @return epsilon
     */
    public double getEps() {
        return eps;
    }

    /**
     * Returns the minimum number of points in a cluster
     *
     * @return minPts
     */
    public int getMinPts() {
        return minPts;
    }

    /**
     * DBScan clustering algorithm. See the slides for pseudocode and the class documentation for a detailed description.
     * <p>
     * This method will return a list of clusters. Each {@link Cluster} contains a list of points that
     * belong to that cluster. DBScan does not return cluster centers. To find the center, simply
     * average over the points.
     * </p>
     * <p>
     * Points must be valid for clustering - that is a subclass of {@link Clusterable}. This is so that
     * we can compute the distance as defined by {@link Clusterable#distance(Object)}.
     * </p>
     *
     * @param points the list of points we want to cluster
     * @return a list of clusters
     * @see Cluster
     * @see Clusterable
     * @see #eps
     * @see #minPts
     */
    public List<Cluster<T>> cluster(final Collection<T> points) {
        // List of clusters that are populated by the DBScan algorithm.
        List<Cluster<T>> clusters = new ArrayList<>();

        // The cluster that is currently being populated.
        Cluster<T> cluster = new Cluster<>();

        /* maps a point T to true or false, indicating whether that point has been visited or not */
        Map<T, State> states = new HashMap<>();

        // Initialize all points' state to UNVISITED.
        for (final T point : points) {
            states.put(point, State.UNVISITED);
        }

        // Iterate over each unvisited point in the dataset.
        for (T point : points) {
            // Find the points in the neighborhood of the current point that are within
            // eps distance of the current point.
            List<T> neighborPoints = regionQuery(point, points);

            // If the current point has less than minPuts points in its neighborhood (within
            // eps distance) then mark the current point as a noise point.
            if (neighborPoints.size() < minPts) {
                states.put(point, State.NOISE);
            } else {
                // Add the new cluster to the set of clusters.
                clusters.add(cluster);
                cluster = new Cluster<T>();
                expandCluster(cluster, point, states, neighborPoints, points);
            }
        }

        return clusters;
    }

    /**
     * Expands the cluster to include reachable points within {@link #eps}
     * satisfying a local density defined by {@link #minPts}.
     *
     * @param cluster     the cluster to expand
     * @param p           Point to add to cluster
     * @param states      a map from points to true or false,
     *                    indicating whether the point has been visited
     * @param neighborPts the list of neighboring points
     * @param points      the set of all points
     */
    private void expandCluster(final Cluster<T> cluster,
                               final T p,
                               final Map<T, State> states,
                               final List<T> neighborPts,
                               final Collection<T> points) {

        cluster.addPoint(p);
        states.put(p, State.CLUSTERED);

        for (T pointPrime : neighborPts) {
            if (states.get(pointPrime) == State.UNVISITED) {
                final List<T> neighborPointsPrime = regionQuery(pointPrime, neighborPts);
                if (neighborPointsPrime.size() >= minPts) {
                    addAsSet(neighborPts, neighborPointsPrime);
                }
            }
            if (states.get(pointPrime) != State.CLUSTERED) {
                cluster.addPoint(pointPrime);
            }
        }
    }

    /**
     * Returns a list of neighbors within {@link #eps} of the given point.
     *
     * @param p      the point of interest
     * @param points all candidate neighboring points
     * @return a list of neighbors
     * @see Clusterable#distance(Object)
     */
    private List<T> regionQuery(final T p, final Collection<T> points) {
        final List<T> neighbors = new ArrayList<T>();
        for (T point : points) {
            if (p.distance(point) <= eps) {
                neighbors.add(point);
            }
        }
        // Include the current point in its neighborhood.
        neighbors.add(p);
        return neighbors;
    }

    /**
     * Adds all the items in list2 to list1 that are not already contained in list1. It's
     * important that we don't have repeats in our clusters
     *
     * @param list1 the first list of points
     * @param list2 the second list of points
     */
    private void addAsSet(List<T> list1, final List<T> list2) {
        for (T p : list2) {
            if (!list1.contains(p)) {
                list1.add(p);
            }
        }
    }

    /**
     * The state of a point - is it in a cluster or has it been marked as noise?
     */
    private enum State {
        /**
         * The point has not yet been visited
         */
        UNVISITED,
        /**
         * The point has been visited and has determined to be noise (no cluster)
         */
        NOISE,
        /**
         * The point has been assigned to a cluster.
         */
        CLUSTERED
    }
}