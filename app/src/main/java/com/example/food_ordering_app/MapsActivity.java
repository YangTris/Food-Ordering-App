package com.example.food_ordering_app;


import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_ordering_app.services.MapService;
import com.example.food_ordering_app.services.OrderService;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Granularity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.food_ordering_app.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;
    private LocationRequest locationRequest;
    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static String REQUESTING_LOCATION_UPDATES_KEY = "requesting_location_updates_key";
    private boolean requestingLocationUpdates = false;
    private LocationCallback locationCallback;
    private Marker currentMarker;
    private MapService mapService = new MapService(MapsActivity.this);
    private Button btnGetDirection;
    private Button btnFinishOrder;
    private TextView txtTime;
    private TextView txtDistance;
    private TextView txtAddress;
    private LatLng userAddress;
    private String userName;
    private String userPhone;
    private String orderId;
    private OrderService orderService = new OrderService(this);
    private SharedPreferences sharedPreferences;
    private String shipperId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set View
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Set ID
        btnGetDirection = findViewById(R.id.btnGetDirection);
        btnFinishOrder = findViewById(R.id.btnFinishOrder);
        txtDistance = findViewById(R.id.txtDistance);
        txtTime = findViewById(R.id.txtTime);
        txtAddress = findViewById(R.id.txtAddress);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //Get Permission
        updateValuesFromBundle(savedInstanceState, mapFragment);
        permissionCheck(mapFragment);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //Get information
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        userAddress = getLocationFromAddress(this,bundle.getString("userAddress"));
        userName =bundle.getString("userName");
        userPhone = bundle.getString("userPhone");
        orderId = bundle.getString("orderId");
        txtAddress.setText(bundle.getString("userAddress"));
        sharedPreferences = getSharedPreferences("sharedPrefKey",Context.MODE_PRIVATE);
        shipperId = sharedPreferences.getString("userIdKey",null);
        //Get location
        if (!requestingLocationUpdates) {
            createLocationRequest();
        } else {
            startLocationUpdates();
        }
        //Update current location
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.d("location", location.toString());
                    if (location != null) {
                        if (currentLocation.getLongitude() != location.getLongitude()
                                || currentLocation.getLatitude() != location.getLatitude()) {
                            currentLocation = location;
                            mapFragment.getMapAsync(MapsActivity.this);
                        }
                    }
                }
            }
        };
    }

    private void permissionCheck(SupportMapFragment mapFragment) {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                getCurrentLocation(mapFragment);
                            } else {
                                // No location access granted.
                                onDestroy();
                            }
                        }
                );

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (currentMarker != null) {
            currentMarker.remove();
        }
        //Draw Marker
        LatLng origin = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        currentMarker = mMap.addMarker(getMarkerOption(origin,"Vị trí hiện tại",null));
        currentMarker.showInfoWindow();
        Marker marker = mMap.addMarker(getMarkerOption(userAddress,userName,userPhone));
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapService.getDirection(origin,userAddress,mMap,txtTime,txtDistance,userName,userPhone);
                orderService.updateOrderStatus(orderId,shipperId,"Delivering");
                //Clickable after pressing btnGetDirection
                btnFinishOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        orderService.updateOrderStatus(orderId,shipperId,"Delivered");
                        finish();
                    }
                });
            }
        });
    }

    protected void getCurrentLocation(SupportMapFragment mapFragment) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    currentLocation = location;
                    mapFragment.getMapAsync(MapsActivity.this);
                }
            });
        }
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest.Builder(5000)
                .setGranularity(Granularity.GRANULARITY_FINE)
                .setPriority(PRIORITY_HIGH_ACCURACY)
                .build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(MapsActivity.this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(MapsActivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                requestingLocationUpdates = true;
                startLocationUpdates();
            }
        });
        task.addOnFailureListener(MapsActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapsActivity.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                        sendEx.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates);
        super.onSaveInstanceState(outState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState, SupportMapFragment mapFragment) {
        if (savedInstanceState == null) {
            return;
        }
        // Update the value of requestingLocationUpdates from the Bundle.
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            requestingLocationUpdates = savedInstanceState.getBoolean(
                    REQUESTING_LOCATION_UPDATES_KEY);
            if (requestingLocationUpdates) startLocationUpdates();
        }
    }

    @NonNull
    private MarkerOptions getMarkerOption(LatLng location,String title,String snippet) {
        MarkerOptions option = new MarkerOptions();
        option.position(location);
        option.title(title).snippet(snippet);
        option.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        return option;
    }

    public LatLng getLocationFromAddress(Context context, String inputtedAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng resLatLng = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(inputtedAddress, 5);
            if (address == null) {
                return null;
            }

            if (address.size() == 0) {
                return null;
            }

            Address location = address.get(0);
            resLatLng = new LatLng(location.getLatitude(),location.getLongitude());

        } catch (IOException ex) {
            ex.printStackTrace();
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return resLatLng;
    }
}