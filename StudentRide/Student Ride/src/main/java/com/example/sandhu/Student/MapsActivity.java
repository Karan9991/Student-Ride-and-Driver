package com.example.sandhu.Student;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback  {

    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;
    GpsTracker gp = new GpsTracker(this);
    double lat, lon;
    String a, sourceaddress, destinationaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//creating back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MAP");



        GpsTracker gpsTracker = new GpsTracker(getApplication());

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplication(), Locale.getDefault());
//getting latitude and longitude of source and destination
        Intent bb = getIntent();
        if (bb != null) {
            a = bb.getStringExtra("destinationn");
            try {
                addresses = geocoder.getFromLocationName(a
                        , 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                lat = addresses.get(0).getLatitude();
                lon = addresses.get(0).getLongitude();
              destinationaddress = addresses.get(0).getAddressLine(0);

            }catch (Exception e){
                e.printStackTrace();

            }

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        place1 = new MarkerOptions().position(new LatLng(gpsTracker.latitude, gpsTracker.longitude)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(lat, lon)).title(destinationaddress);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(place2);

        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(lat,lon))
                .zoom(7)
                .bearing(0)
                .tilt(45)
                .build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);
        new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
       currentPolyline.setColor(getApplicationContext().getResources().getColor(R.color.progressbarcolor));

    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), RideDetails.class));
        finish();
        return super.onSupportNavigateUp();

    }
}
