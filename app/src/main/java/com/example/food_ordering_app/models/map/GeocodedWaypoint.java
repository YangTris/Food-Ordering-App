package com.example.food_ordering_app.models.map;

import java.util.List;
import javax.annotation.processing.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GeocodedWaypoint {

    @SerializedName("geocoder_status")
    @Expose
    private String geocoderStatus;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("types")
    @Expose
    private List<String> types;

    /**
     * No args constructor for use in serialization
     *
     */
    public GeocodedWaypoint() {
    }

    /**
     *
     * @param types
     * @param placeId
     * @param geocoderStatus
     */
    public GeocodedWaypoint(String geocoderStatus, String placeId, List<String> types) {
        super();
        this.geocoderStatus = geocoderStatus;
        this.placeId = placeId;
        this.types = types;
    }

    public String getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

}