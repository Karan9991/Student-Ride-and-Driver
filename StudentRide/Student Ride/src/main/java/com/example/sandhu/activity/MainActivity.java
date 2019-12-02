package com.example.sandhu.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
///import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;

import com.example.sandhu.Student.FetchURL;
import com.example.sandhu.Student.GpsTracker;
import com.example.sandhu.Student.MapsActivity;
import com.example.sandhu.Student.MyProvider;
import com.example.sandhu.Student.R;
import com.example.sandhu.Student.TaskLoadedCallback;
import com.example.sandhu.interfaces2.LatLngInterpolatorNew;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.geometry.*;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.example.sandhu.R;
//import com.example.sandhu.R.id;
import com.example.sandhu.collection2.MarkerCollection;
import com.example.sandhu.helpers2.FirebaseEventListenerHelper;
import com.example.sandhu.helpers2.GoogleMapHelper;
import com.example.sandhu.helpers2.MarkerAnimationHelper;
import com.example.sandhu.helpers2.UiHelper;
import com.example.sandhu.interfaces2.FirebaseDriverListener;
import com.example.sandhu.interfaces2.IPositiveNegativeListener;
import com.example.sandhu.interfaces2.IPositiveNegativeListener.DefaultImpls;
import com.example.sandhu.interfaces2.LatLngInterpolator;
import com.example.sandhu.interfaces2.LatLngInterpolator.Spherical;
import com.example.sandhu.interfaces2.LatLngInterpolatorNew.LinearFixed;
import com.example.sandhu.model2.Driver;
import com.example.sandhu.Student.MyProvider;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kotlin.jvm.internal.Intrinsics;
//import kotlin.TypeCastException;
//import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MainActivity extends AppCompatActivity implements FirebaseDriverListener, TaskLoadedCallback, ValueEventListener {
    private GoogleMap googleMap;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean locationFlag = true;
    private FirebaseEventListenerHelper valueEventListener;
    private final UiHelper uiHelper = new UiHelper();
    private final GoogleMapHelper googleMapHelper = new GoogleMapHelper();
    private final DatabaseReference databaseReference;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 6161;
    private static final String ONLINE_DRIVERS = "online_drivers";
  //  public static final com.spartons.passengerapp.MainActivity.Companion Companion = new com.spartons.passengerapp.MainActivity.Companion((DefaultConstructorMarker)null);
    private HashMap _$_findViewCache;
    GpsTracker gpsTracker;
    private MarkerOptions place1, place2, place3, place4;
    private Polyline currentPolyline;
    Driver driver;
    String contentProvider=null;
    CursorLoader cursorLoader;
    StringBuilder res;
    String data,destinationn;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mdatabaseReference = firebaseDatabase.getReference();
    DatabaseReference mdata = mdatabaseReference.child("passengerpicked");
    double dlat,dlng;
    private GoogleMap mMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);
      //  Toast.makeText(getApplicationContext(),"Tcp"+contentProvider,Toast.LENGTH_SHORT).show();
        res = new StringBuilder();
        mdata.addValueEventListener(this);

//inserting content provider
        gpsTracker = new GpsTracker(getApplication());
       // driver = new Driver();
        ContentValues values = new ContentValues();
        values.put(MyProvider.name, "Karan");
        values.put(MyProvider.sourcce, 43.6532);
        values.put(MyProvider.destinationn, 79.3832);
        Uri uri = getContentResolver().insert(MyProvider.CONTENT_URI, values);
//        Toast.makeText(getBaseContext(), "New record inserted"+driver.getLat()+driver.getLng(), Toast.LENGTH_SHORT)
     //           .show();

////////////////////////////  1   43.658038, -79.760535
//        place1 = new MarkerOptions().position(new LatLng(43.658038, -79.760535)).title("Location 1");
//        place2 = new MarkerOptions().position(new LatLng(gpsTracker.latitude, gpsTracker.longitude)).title("Driver");
        //////////////////////////////////////////// driver.getLat(), driver.getLng()
        Fragment var10000 = this.getSupportFragmentManager().findFragmentById(R.id.supportMap);

            SupportMapFragment mapFragment = (SupportMapFragment)var10000;
            mapFragment.getMapAsync((OnMapReadyCallback)(new OnMapReadyCallback() {
                public final void onMapReady(GoogleMap it) {
                    MainActivity var10000 = MainActivity.this;
                    var10000.googleMap = it;

                    mMap = it;
                    ////////////////////////////////////////////3
//                    googleMap.addMarker(place1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                    googleMap.addMarker(place2).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//                    new FetchURL(MainActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
////////////////////////////////////////
                }
            }));
            this.createLocationCallback();
            FusedLocationProviderClient var10001 = LocationServices.getFusedLocationProviderClient((Activity)this);
            this.locationProviderClient = var10001;
            this.locationRequest = this.uiHelper.getLocationRequest();
            if (!this.uiHelper.isPlayServicesAvailable((Context)this)) {
                Toast.makeText((Context)this, (CharSequence)"Play Services did not installed!", Toast.LENGTH_SHORT).show();
                this.finish();
            } else {
                this.requestLocationUpdate();
            }

            this.valueEventListener = new FirebaseEventListenerHelper((FirebaseDriverListener)this);
            DatabaseReference var3 = this.databaseReference;
            FirebaseEventListenerHelper var4 = this.valueEventListener;
            if (var4 == null) {
            }

            var3.addChildEventListener((ChildEventListener)var4);
      //  getSupportLoaderManager().initLoader(1, null, this);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.getValue(String.class)!=null){
            String key = dataSnapshot.getKey();
            if (key.equals("passengerpicked")){
                data = dataSnapshot.getValue(String.class);
               // Toast.makeText(getApplicationContext(),"firebase "+data,Toast.LENGTH_SHORT).show();
            }else {
                data = null;
            }
        }
        else {
            data = null;
        }
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    //    //Getting content provider values from diver app
//    @Override
//    public androidx.loader.content.Loader
//            <Cursor> onCreateLoader(int arg0, Bundle arg1){
//        cursorLoader = new CursorLoader(this, Uri.parse("content://com.example.StudentDriver.Activity.MyProvider/cte"), null, null, null, null);
//        return cursorLoader;
//    }
//
//    @Override
//    public void onLoadFinished
//            (@NonNull androidx.loader.content.Loader < Cursor > loader, Cursor cursor){
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            res.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")) + "-" + cursor.getString(cursor.getColumnIndex("source")) + "-" + cursor.getString(cursor.getColumnIndex("destination")));
//            cursor.moveToNext();
//        }
//
//        Toast.makeText(getApplicationContext(), "cp" + res, Toast.LENGTH_SHORT).show();
//        contentProvider = res.toString();
//
//
//        new AlertDialog.Builder(MainActivity.this)
//                .setTitle("One Passenger Found")
//                .setMessage("m"+contentProvider)
//                // .setIcon(R.drawable.ninja)
//                .setPositiveButton("Request Accept",
//                        new DialogInterface.OnClickListener() {
//                            @TargetApi(11)
//                            public void onClick(DialogInterface dialog, int id) {
//                                Toast.makeText(getApplicationContext(),"Tcp"+contentProvider,Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                .setNegativeButton("Request Reject", new DialogInterface.OnClickListener() {
//                    @TargetApi(11)
//                    public void onClick(DialogInterface dialog, int id) {
//
//                    }
//                }).show();
//
//    }
//
//    @Override
//    public void onLoaderReset (@NonNull androidx.loader.content.Loader < Cursor > loader) {
//
//    }
    @SuppressLint({"MissingPermission"})
    private final void requestLocationUpdate() {
        if (!this.uiHelper.isHaveLocationPermission((Context)this)) {
            ActivityCompat.requestPermissions((Activity)this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 6161);
        } else {
            if (this.uiHelper.isLocationProviderEnabled((Context)this)) {
                UiHelper var10000 = this.uiHelper;
                Context var10001 = (Context)this;
                String var10002 = this.getResources().getString(R.string.need_location);
                String var10003 = this.getResources().getString(R.string.location_content);
                var10000.showPositiveDialogWithListener(var10001, var10002, var10003, (IPositiveNegativeListener)(new IPositiveNegativeListener() {
                    public void onPositive() {
                        MainActivity.this.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                    }

                    public void onNegative() {
                        DefaultImpls.onNegative(this);
                    }
                }), "Turn On", false);
            }

            FusedLocationProviderClient var1 = this.locationProviderClient;
            if (var1 == null) {
            }

            LocationRequest var2 = this.locationRequest;
            if (var2 == null) {
            }

            LocationCallback var3 = this.locationCallback;
            if (var3 == null) {
            }

            var1.requestLocationUpdates(var2, var3, Looper.myLooper());
        }
    }

    private final void createLocationCallback() {
        this.locationCallback = (LocationCallback)(new LocationCallback() {
            public void onLocationResult( LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                }

                if (locationResult.getLastLocation() != null) {
                    Location var10002 = locationResult.getLastLocation();
                    double var3 = var10002.getLatitude();
                    Location var10003 = locationResult.getLastLocation();
                    LatLng latLng = new LatLng(var3, var10003.getLongitude());
                    Log.e("Locationn", latLng.latitude + " , " + latLng.longitude);
                    if (MainActivity.this.locationFlag) {
                        MainActivity.this.locationFlag = false;
                        MainActivity.this.animateCamera(latLng);
                    }

                }
            }
        });
    }

    private final void animateCamera(LatLng latLng) {
        CameraUpdate cameraUpdate = this.googleMapHelper.buildCameraUpdate(latLng);
        GoogleMap var10000 = this.googleMap;
        if (var10000 == null) {
        }

        var10000.animateCamera(cameraUpdate, 10, (CancelableCallback)null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 6161) {
            int value = grantResults[0];
            if (value == -1) {
                Toast.makeText((Context)this, (CharSequence)"Location Permission denied", Toast.LENGTH_SHORT).show();
                this.finish();
            } else if (value == 0) {
                this.requestLocationUpdate();
            }
        }
    }
    ///////////////////////////////////2
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
        currentPolyline = googleMap.addPolyline((PolylineOptions) values[0]);
        currentPolyline.setColor(getApplicationContext().getResources().getColor(R.color.colorBlue));

    }
    //////////////////////////////////////////////
    @Override
    public void onDriverAdded(Driver var1) {
        MarkerOptions markerOptions = this.googleMapHelper.getDriverMarkerOptions(new LatLng(var1.getLat(), var1.getLng())).flat(true);
        GoogleMap var10000 = this.googleMap;

        Marker marker = var10000.addMarker(markerOptions);
        marker.setTag(var1.getDriverId());
        MarkerCollection.INSTANCE.insertMarker(marker);
        TextView var4 = (TextView)this._$_findCachedViewById(R.id.totalOnlineDrivers);
        var4.setText((CharSequence)(this.getResources().getString(R.string.total_online_drivers) + " " + MarkerCollection.INSTANCE.allMarkers().size()));

       // Toast.makeText(getBaseContext(), "driver added"+var1.getLat()+var1.getLng(), Toast.LENGTH_SHORT).show();
        place1 = new MarkerOptions().position(new LatLng(43.658038, -79.760535)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(var1.getLat(), var1.getLng())).title("Driver");
       // googleMap.addMarker(place1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
       // googleMap.addMarker(place2).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        new FetchURL(MainActivity.this).execute(getUrl(place2.getPosition(), place1.getPosition(), "driving"), "driving");
  //      MarkerAnimationHelper var100000 = MarkerAnimationHelper.INSTANCE;
//var100000.createAnimation(marker, mMap);

 // Animating marker
//        Intent bb = getIntent();
//        if (bb!=null)
//        {
//            destinationn = bb.getStringExtra("destinationn");
//        }
//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(getApplication(), Locale.getDefault());
//        try {
//            addresses = geocoder.getFromLocationName(destinationn,1);
//            dlat = addresses.get(0).getLatitude();
//            dlng = addresses.get(0).getLongitude();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Marker markerr = MarkerCollection.INSTANCE.getMarker(var1.getDriverId());
//        MarkerAnimationHelper var10000r = MarkerAnimationHelper.INSTANCE;
//        if (data==null) {
//            place1 = new MarkerOptions().position(new LatLng(43.658038, -79.760535)).title("Location 1");
//            place2 = new MarkerOptions().position(new LatLng(var1.getLat(), var1.getLng())).title("Driver");
//            //googleMap.addMarker(place1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//            // googleMap.addMarker(place2).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//            new FetchURL(MainActivity.this).execute(getUrl(place2.getPosition(), place1.getPosition(), "driving"), "driving");
////            Toast.makeText(getBaseContext(), "database" + data, Toast.LENGTH_SHORT)
////                    .show();
//            var10000r.createAnimation(marker,mMap);
//        }else {
////43.645458, -79.750877  61 michigan ave
//            place3 = new MarkerOptions().position(new LatLng(gpsTracker.latitude,gpsTracker.longitude)).title("source");
//            place4 = new MarkerOptions().position(new LatLng(dlat, dlng)).title("destination");
//            new FetchURL(MainActivity.this).execute(getUrl(place3.getPosition(), place4.getPosition(), "driving"), "driving");
//            //  Toast.makeText(getBaseContext(), "database61" + data, Toast.LENGTH_SHORT).show();
//            var10000r.createAnimation(marker,mMap);
//        }
    }

    @Override
    public void onDriverRemoved(Driver var1) {
        MarkerCollection.INSTANCE.removeMarker(var1.getDriverId());
        TextView var10000 = (TextView)this._$_findCachedViewById(R.id.totalOnlineDrivers);
        var10000.setText((CharSequence)(this.getResources().getString(R.string.total_online_drivers) + " " + MarkerCollection.INSTANCE.allMarkers().size()));
    }
//    public void onDriverUpdated(@NotNull Driver driver) {
//        Intrinsics.checkParameterIsNotNull(driver, "driver");
//        Marker marker = MarkerCollection.INSTANCE.getMarker(driver.getDriverId());
//        MarkerAnimationHelper var10000 = MarkerAnimationHelper.INSTANCE;
//        if (marker == null) {
//            Intrinsics.throwNpe();
//        }
//    final LatLng startPosition = marker.getPosition();
//
//        var10000.animateMarkerToGB(marker, new LatLng(driver.getLat(), driver.getLng()), (LatLngInterpolator)(new Spherical()));
//        place1 = new MarkerOptions().position(new LatLng(43.658038, -79.760535)).title("Location 1");
//        place2 = new MarkerOptions().position(new LatLng(driver.getLat(), driver.getLng())).title("Driver");
//        new FetchURL(MainActivity.this).execute(getUrl(place2.getPosition(), place1.getPosition(), "driving"), "driving");
//
//        //   marker.setRotation((float) getBearingBetweenTwoPoints1(startPosition, new LatLng(driver.getLat(), driver.getLng())));
////        var heading = google.maps.geometry.spherical.computeHeading(lastPosn,p);
//
//     //   marker.setPosition(new LatLng(gpsTracker.latitude, gpsTracker.longitude));
////marker.setRotation(getBearing(new LatLng(gpsTracker.latitude, gpsTracker.longitude), new LatLng(driver.getLat(), driver.getLng())));
//   //marker.setRotation((float) SphericalUtil.computeHeading( new LatLng(driver.getLat(), driver.getLng()),new LatLng(driver.getLat(), driver.getLng())));
//   //marker.setRotation(getBearing(new LatLng(gpsTracker.latitude, gpsTracker.longitude),SphericalUtil.interpolate(new LatLng(driver.getLat(), driver.getLng()),new LatLng(driver.getLat(), driver.getLng()),1.566)));
//    }
    @Override
    public void onDriverUpdated(Driver var1) {

            Intent bb = getIntent();
            if (bb!=null)
            {
                destinationn = bb.getStringExtra("destinationn");
            }
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getApplication(), Locale.getDefault());
        try {
            addresses = geocoder.getFromLocationName(destinationn,1);
            dlat = addresses.get(0).getLatitude();
            dlng = addresses.get(0).getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        final LatLngInterpolatorNew latLngInterpolator = new LatLngInterpolatorNew.LinearFixed();

        Marker marker = MarkerCollection.INSTANCE.getMarker(var1.getDriverId());
        MarkerAnimationHelper var10000 = MarkerAnimationHelper.INSTANCE;
        final LatLng startPosition = marker.getPosition();
        final LatLng newPosition = new LatLng(var1.getLat(), var1.getLng());

        final LatLng endPosition = new LatLng(43.658038, -79.760535);
//var10000.animateMarkerToGB(marker, new LatLng(var1.getLat(), var1.getLng()), (LatLngInterpolatorNew)(new LinearFixed()));
        var10000.animateMarkerToGB(marker, new LatLng(var1.getLat(), var1.getLng()), (LatLngInterpolator)(new Spherical()));

        // var10000.animateMarkerToGB(marker, new LatLng(var1.getLat(), var1.getLng()), (LatLngInterpolator)(new LatLngInterpolator.Spherical()));
     //   var10000.animateMarkerToGB(marker, new LatLng(var1.getLat(), var1.getLng()), (LatLngInterpolator)(new Spherical()));
//marker.setRotation(latLngInterpolator.getBearing(startPosition,  new LatLng(var1.getLat(), var1.getLng())));
   //     marker.setRotation(latLngInterpolator.getBearing(startPosition,  newPosition));
//     marker.setRotation((float) getBearingBetweenTwoPoints1(startPosition, new LatLng(var1.getLat(), var1.getLng())));
      //  Toast.makeText(getBaseContext(), "driver updated"+var1.getLat()+var1.getLng(), Toast.LENGTH_SHORT)
        //        .show();
        if (data==null) {
            place1 = new MarkerOptions().position(new LatLng(43.658038, -79.760535)).title("Location 1");
            place2 = new MarkerOptions().position(new LatLng(var1.getLat(), var1.getLng())).title("Driver");
           //googleMap.addMarker(place1).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            // googleMap.addMarker(place2).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            new FetchURL(MainActivity.this).execute(getUrl(place2.getPosition(), place1.getPosition(), "driving"), "driving");
//            Toast.makeText(getBaseContext(), "database" + data, Toast.LENGTH_SHORT)
//                    .show();
        }else {
//43.645458, -79.750877  61 michigan ave
            place3 = new MarkerOptions().position(new LatLng(gpsTracker.latitude,gpsTracker.longitude)).title("source");
            place4 = new MarkerOptions().position(new LatLng(dlat, dlng)).title("destination");
            new FetchURL(MainActivity.this).execute(getUrl(place3.getPosition(), place4.getPosition(), "driving"), "driving");
          //  Toast.makeText(getBaseContext(), "database61" + data, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        DatabaseReference var10000 = this.databaseReference;
        FirebaseEventListenerHelper var10001 = this.valueEventListener;
        if (var10001 == null) {
        }

        var10000.removeEventListener((ChildEventListener)var10001);
        FusedLocationProviderClient var1 = this.locationProviderClient;
        if (var1 == null) {
        }

        LocationCallback var2 = this.locationCallback;
        if (var2 == null) {
        }

        var1.removeLocationUpdates(var2);
        MarkerCollection.INSTANCE.clearMarkers();
    }

    public MainActivity() {
        FirebaseDatabase var10001 = FirebaseDatabase.getInstance();
        this.databaseReference = var10001.getReference().child("online_drivers");
    }

    // $FF: synthetic method
    public static final GoogleMap access$getGoogleMap$p(MainActivity $this) {
        GoogleMap var10000 = $this.googleMap;
        if (var10000 == null) {
        }

        return var10000;
    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }

    public static final class Companion {
        private Companion() {
        }

        // $FF: synthetic method
//        public Companion(DefaultConstructorMarker $constructor_marker) {
//            this();
//        }
    }

}

