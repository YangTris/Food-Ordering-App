package com.example.food_ordering_app.models.map;

import java.util.List;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MapInfo {

    @SerializedName("geocoded_waypoints")
    @Expose
    private List<GeocodedWaypoint> geocodedWaypoints;
    @SerializedName("routes")
    @Expose
    private List<Route> routes;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * No args constructor for use in serialization
     *
     */
    public MapInfo() {
    }

    /**
     *
     * @param routes
     * @param geocodedWaypoints
     * @param status
     */
    public MapInfo(List<GeocodedWaypoint> geocodedWaypoints, List<Route> routes, String status) {
        super();
        this.geocodedWaypoints = geocodedWaypoints;
        this.routes = routes;
        this.status = status;
    }

    public List<GeocodedWaypoint> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodedWaypoint> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MapInfo{" +
                "geocodedWaypoints=" + geocodedWaypoints +
                ", routes=" + routes +
                ", status='" + status + '\'' +
                '}';
    }
}