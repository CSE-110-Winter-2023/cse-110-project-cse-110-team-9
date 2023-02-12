package edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map;

public class MapUIState {
    private Double latitude;
    private Double longitude;
    private String pointLabel;

    public MapUIState(Double latitude, Double longitude, String label) {
        this.latitude = latitude;

        this.longitude = longitude;
        pointLabel = label;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPointLabel()
    {
        return pointLabel;
    }
}

