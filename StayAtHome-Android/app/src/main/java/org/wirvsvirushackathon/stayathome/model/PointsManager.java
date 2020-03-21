package org.wirvsvirushackathon.stayathome.model;

import android.util.Log;

import org.wirvsvirushackathon.stayathome.data.PointsRepository;

public class PointsManager {

    private final PointsRepository pointsRepository;

    public PointsManager(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    public void userStayedHomeOneInterval() {
        pointsRepository.addPoints(10);
        Log.d(this.getClass().getSimpleName(), "Added 10 Points. Total: " + pointsRepository.getCurrentPoints());
    }
}
