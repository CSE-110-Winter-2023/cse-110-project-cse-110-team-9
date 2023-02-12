package edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map;public class MapUIState
{
    private Double latitude;
    private Double longitiude;

    public MapUIState(Double latitude, Double longitiude)
    {
        this.latitude = latitude;
        this.longitiude = longitiude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitiude() {
        return longitiude;
    }
}

