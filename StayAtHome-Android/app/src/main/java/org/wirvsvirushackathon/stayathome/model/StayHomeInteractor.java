package org.wirvsvirushackathon.stayathome.model;

import android.Manifest;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import java.util.concurrent.TimeUnit;
import java.util.List;
import org.wirvsvirushackathon.stayathome.model.clustering.Cluster;
import org.wirvsvirushackathon.stayathome.model.clustering.DBScan;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class StayHomeInteractor implements LocationListener {

    private static final int MIN_DISTANCE = 2;

    private final HomeWifiManager homeWifiManager;

    /**
     * The home location of the user.
     */
    private final GPSLocation homeLocation = new GPSLocation(0L, 0, 0, 0f); // TODO

    /**
     * Effectively a sliding window over locations
     */
    private Queue<GPSLocation> lastLocations = new LinkedBlockingQueue<>();

    /**
     * The number of GPS location readings within a window for evaluation
     */
    private static final int LOCATION_WINDOW_SIZE = 25;

    /**
     * The distance threshold for evaluating whether the user is currently at home.
     */
    private static final int DISTANCE_THRESHOLD_METERS = 30;

    /**
     * Radius of the neighborhood for expanding clusters in meters
     */
    private static double APPROXIMATE_HOME_RADIUS_METERS = 15;

    /**
     * Minimum number of points in a cluster
     */
    private static int MIN_POINTS_IN_CLUSTER = 5;

    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public StayHomeInteractor(LocationManager locationManager, HomeWifiManager homeWifiManager) {
        this.homeWifiManager = homeWifiManager;

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                TimeUnit.MINUTES.toMillis(5),
                MIN_DISTANCE,
                this
        );
    }

    @RequiresApi(24)
    public boolean isUserAtHome() {
        return isLastLocationHome() || homeWifiManager.isConnectedToHomeWifi();
    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocations.add(new GPSLocation(
                location.getTime(),
                location.getLatitude(),
                location.getLongitude(),
                location.getAccuracy()));

        if (lastLocations.size() > LOCATION_WINDOW_SIZE) {
            lastLocations.remove();
        }
    }

    @RequiresApi(24)
    private boolean isLastLocationHome() {

        final DBScan<GPSLocation> dbScan = new DBScan<>(APPROXIMATE_HOME_RADIUS_METERS, MIN_POINTS_IN_CLUSTER);

        // Run the DBScan algorithm on the locations
        final List<Cluster<GPSLocation>> clusters = dbScan.cluster(lastLocations);

        for (final Cluster<GPSLocation> cluster : clusters) {

            if (clusters.size() > MIN_POINTS_IN_CLUSTER) {
                final GPSLocation clusterCenter = getAverageLocation(cluster.getPoints());

                // if any cluster is not at home, then the user is flagged as having left their home
                // within the last LOCATION_WINDOW_SIZE location sensor readings
                if (clusterCenter.distance(homeLocation) > DISTANCE_THRESHOLD_METERS) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns the average over a collection of timestamped GPS locations. If no GPS locations are
     * provided, then an IllegalStateException is thrown.
     *
     * Note that the average is computed using the mean, i.e. the
     * curvature of the earth is ignored since the locations are generally very close.
     *
     * Note also that the accuracy is not averaged, as the average is not very useful.
     * @param locations The collection of locations to average.
     * @return The average location.
     */
    @RequiresApi(24)
    private GPSLocation getAverageLocation(final Collection<GPSLocation> locations) {
        final long averageTimestamp = (long) locations.stream()
                .mapToLong(GPSLocation::getTimestamp)
                .average()
                .orElseThrow(IllegalStateException::new);

        final double averageLatitude = locations.stream()
                .mapToDouble(GPSLocation::getLatitude)
                .average()
                .orElseThrow(IllegalStateException::new);

        final double averageLongitude = locations.stream()
                .mapToDouble(GPSLocation::getLongitude)
                .average()
                .orElseThrow(IllegalStateException::new);

        return new GPSLocation(
                averageTimestamp,
                averageLatitude,
                averageLongitude,
                0f
        );
    }

    @Override
    public void onStatusChanged(
            String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
