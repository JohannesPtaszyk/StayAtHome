package org.wirvsvirushackathon.stayathome.model;

import android.location.Location;

import org.wirvsvirushackathon.stayathome.model.clustering.Clusterable;

public class GPSLocation implements Clusterable<GPSLocation> {

    /** timestamp of the event */
    public long timestamp;

    /** latitude */
    public double latitude;

    /** longitude */
    public double longitude;

    /** accuracy */
    public float accuracy;

    public GPSLocation(final long timestamp,
                       final double lat,
                       final double lng,
                       final float accuracy) {
        this.timestamp = timestamp;
        this.latitude = lat;
        this.longitude = lng;
        this.accuracy = accuracy;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Computes the distance (in meters) between two GPSLocations using the android Location API.
    @Override
    public double distance(GPSLocation other) {
        float[] results = new float[3];
        Location.distanceBetween(latitude, longitude, other.getLatitude(), other.getLongitude(), results);
        return results[0];
    }
}