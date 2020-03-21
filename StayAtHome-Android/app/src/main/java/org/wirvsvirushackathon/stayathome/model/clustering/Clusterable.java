package org.wirvsvirushackathon.stayathome.model.clustering;

public interface Clusterable<T> {
    /** Computes the distance between two points - this is generally the Euclidean distance but it need not be */
    double distance(T other);
}
