package com.example.food_ordering_app.services;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food_ordering_app.BuildConfig;
import com.example.food_ordering_app.controllers.MapController;
import com.example.food_ordering_app.models.map.MapInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;


import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapService {
    private final MapController mapController = MapServiceBuilder.getClient().create(MapController.class);
    private final Context context;
    private List<LatLng> polylines;
    public MapService(Context context) {this.context = context;}

    public void responseFailure(Throwable throwable){
        if (throwable instanceof IOException) {
            Toast.makeText(context, "A connection error occured", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
        }
    }

    public void getDirection(LatLng origin, LatLng destination, GoogleMap map, TextView txtTime, TextView txtDistance, String userName, String userPhone){
        String originString = convertLatLongToString(origin);
        String desString = convertLatLongToString(destination);
        Call<MapInfo> request = mapController.getDirection(originString,desString, BuildConfig.API_KEY);
        request.enqueue(new Callback<MapInfo>() {
            @Override
            public void onResponse(Call<MapInfo> call, Response<MapInfo> response) {
                MapInfo info = response.body();
                Log.d("MapStatus",info.getStatus());
                //Clear old Polylines and add new Markers
                if(polylines != null){
                    map.clear();
                    Marker currentMarker = map.addMarker(getMarkerOption(origin,"Vị trí hiện tại",null));
                    currentMarker.showInfoWindow();
                    Marker marker = map.addMarker(getMarkerOption(destination,userName,userPhone));
                    marker.showInfoWindow();
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
                }
                //Get information
                txtTime.setText("Estimated Time : "+info.getRoutes().get(0).getLegs().get(0).getDuration().getText());
                txtDistance.setText("Estimated Distance : "+info.getRoutes().get(0).getLegs().get(0).getDistance().getText());
                polylines = PolyUtil.decode(info.getRoutes().get(0).getOverviewPolyline().getPoints().replace("\\\\","\\"));
                //Add polyline
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.addAll(polylines);
                polylineOptions.width(5).color(Color.BLUE).geodesic(true);
                map.addPolyline(polylineOptions);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
            }

            @Override
            public void onFailure(Call<MapInfo> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public String convertLatLongToString(LatLng latLng) {
        String latitude = ((Double)latLng.latitude).toString();
        String longitude = ((Double)latLng.longitude).toString();
        return latitude + "," + longitude;
    }
    @NonNull
    private MarkerOptions getMarkerOption(LatLng location,String title,String snippet) {
        MarkerOptions option = new MarkerOptions();
        option.position(location);
        option.title(title).snippet(snippet);
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        return option;
    }
}
