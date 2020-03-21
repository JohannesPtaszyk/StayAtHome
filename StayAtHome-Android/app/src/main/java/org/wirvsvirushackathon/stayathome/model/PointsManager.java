package org.wirvsvirushackathon.stayathome.model;

import android.util.Log;

import org.wirvsvirushackathon.stayathome.data.PointsRepository;

public class PointsManager implements StayHomeInteractor.Callback {

    private final PointsRepository pointsRepository;

    public PointsManager(PointsRepository pointsRepository) {
        this.pointsRepository = pointsRepository;
    }

    @Override
    public void userStayedHomeOneInterval() {
        pointsRepository.addPoints(10);
        Log.d(this.getClass().getSimpleName(), "Added 10 Points");
    }
}
