package org.wirvsvirushackathon.stayathome.data;

public class PointsRepository {

    private PointsDataSource pointsDataSource;

    public PointsRepository(PointsDataSource pointsDataSource) {
        this.pointsDataSource = pointsDataSource;
    }

    public int getCurrentPoints() {
        return pointsDataSource.getCurrentPoints();
    }

    public void addPoints(int points) {
        int currentPoints = pointsDataSource.getCurrentPoints();
        pointsDataSource.setCurrentPoints(currentPoints + points);
    }

}
