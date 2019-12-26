package com.example.StudentDriver.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;

import com.example.StudentDriver.R;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
//import com.spartons.driverapp.R;
import com.example.StudentDriver.helper2.FirebaseHelper;
import com.example.StudentDriver.helper2.GoogleMapHelper;
import com.example.StudentDriver.helper2.MarkerAnimationHelper;
import com.example.StudentDriver.helper2.UiHelper;
import com.example.StudentDriver.interfaces2.IPositiveNegativeListener;
import com.example.StudentDriver.interfaces2.LatLngInterpolator;
import com.example.StudentDriver.interfaces2.LatLngInterpolator.Spherical;
import com.example.StudentDriver.model2.Driver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public final class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ValueEventListener {
    private GoogleMap googleMap;
    SwitchCompat sc;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean locationFlag = true;
    private boolean driverOnlineFlag;
    private Marker currentPositionMarker;
    private final GoogleMapHelper googleMapHelper = new GoogleMapHelper();
    private final FirebaseHelper firebaseHelper = new FirebaseHelper("0000");
    private final MarkerAnimationHelper markerAnimationHelper = new MarkerAnimationHelper();
    private final UiHelper uiHelper = new UiHelper();
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2200;
   // public static final com.spartons.driverapp.MainActivity.Companion Companion = new com.spartons.driverapp.MainActivity.Companion((DefaultConstructorMarker)null);
    private HashMap _$_findViewCache;
    String contentProvider=null;
    CursorLoader cursorLoader;
    StringBuilder res;
Button btn,ridecom;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    DatabaseReference mdata = databaseReference.child("passengerpicked");
    FirebaseDatabase firebaseCancelR = FirebaseDatabase.getInstance();
    DatabaseReference databaseRefCancelR = firebaseCancelR.getReference();
    DatabaseReference cancelRdata = databaseRefCancelR.child("Cancelride");
    String data,datac;
    ImageView image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main2);
        res = new StringBuilder();
btn = (Button)findViewById(R.id.pickedpassengerbtn);
ridecom = (Button)findViewById(R.id.ridecompleted);
sc = (SwitchCompat)findViewById(R.id.driverStatusSwitch);
        image = new ImageView(this);
//        int widthPX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//        int heightPX = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//        LinearLayout.LayoutParams layoutParams  = new LinearLayout.LayoutParams(widthPX, heightPX);
//        image.setLayoutParams(layoutParams);

mdata.addValueEventListener(this);
        cancelRdata.addValueEventListener(this);

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mdata.setValue("passengertook");
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("message");
//        databaseReference.setValue("Karandeep Singh");

//        ContentValues values = new ContentValues();
//        values.put(MyProvider.name, "Karandeep");
//        values.put(MyProvider.sourcce, 43.6532);
//        values.put(MyProvider.destinationn, 79.3832);
//        Uri uri = getContentResolver().insert(MyProvider.CONTENT_URI, values);

    }
});
ridecom.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mdata.removeValue();
    }
});
        getSupportLoaderManager().initLoader(1, null, this);



        Fragment var10000 = this.getSupportFragmentManager().findFragmentById(R.id.supportMap);

            SupportMapFragment mapFragment = (SupportMapFragment)var10000;
            mapFragment.getMapAsync((OnMapReadyCallback)(new OnMapReadyCallback() {
                public void onMapReady( GoogleMap p0) {
                    MainActivity var10000 = MainActivity.this;


                    var10000.googleMap = p0;
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

            final TextView driverStatusTextView = (TextView)this.findViewById(R.id.driverStatusTextView);
            ((SwitchCompat)this.findViewById(R.id.driverStatusSwitch)).setOnCheckedChangeListener((OnCheckedChangeListener)(new OnCheckedChangeListener() {
                public final void onCheckedChanged(CompoundButton $noName_0, boolean b) {
                    MainActivity.this.driverOnlineFlag = b;
                    TextView var10000;
                    if (MainActivity.this.driverOnlineFlag) {
                        var10000 = driverStatusTextView;
                        var10000.setText((CharSequence) MainActivity.this.getResources().getString(R.string.online_driver));
                    } else {
                        var10000 = driverStatusTextView;
                        var10000.setText((CharSequence) MainActivity.this.getResources().getString(R.string.offline));
                        MainActivity.this.firebaseHelper.deleteDriver();
                    }

                }
            }));
//sc.setChecked(true);
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
if(dataSnapshot.getValue(String.class)!=null){
    String key = dataSnapshot.getKey();
    String ckey = dataSnapshot.getKey();

//    if (key.equals("passengerpicked")){
//         data = dataSnapshot.getValue(String.class);
//       Toast.makeText(getApplicationContext(),"firebase "+data,Toast.LENGTH_SHORT).show();
//    }
    if (key.equals("Cancelride")){
        sc.setChecked(false);
        data = dataSnapshot.getValue(String.class);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Ride Canceled")
                // .setMessage(" "+contentProvider)
                // .setIcon(image)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                cancelRdata.removeValue();
                            }
                        }).show();
        //  Toast.makeText(getApplicationContext(),"firebase "+data,Toast.LENGTH_SHORT).show();
    }
}
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    //Getting content provider values from passenger app
        @Override
        public androidx.loader.content.Loader
            <Cursor > onCreateLoader( int arg0, Bundle arg1){
            cursorLoader = new CursorLoader(this, Uri.parse("content://com.example.sandhu.Student.MyProvider/cte"), null, null, null, null);
            return cursorLoader;
        }

        @Override
        public void onLoadFinished
        (@NonNull androidx.loader.content.Loader < Cursor > loader, Cursor cursor){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                //res.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")) + "-" + cursor.getString(cursor.getColumnIndex("source")) + "-" + cursor.getString(cursor.getColumnIndex("destination")));
              //  res.append("\n" +  cursor.getString(cursor.getColumnIndex("name")));
contentProvider = cursor.getString(cursor.getColumnIndex("name"));
                image.setImageBitmap(convertToBitmap(cursor.getBlob(cursor.getColumnIndex("photo"))));
               // image.setMaxHeight(10);
                //image.setMaxWidth(10);
             //  image.setLayoutParams(new ActionBar.LayoutParams(10,2));

//                image.getLayoutParams().height = 10;
  //              image.getLayoutParams().width = 10;
              //  image.setImageDrawable(cursor.getBlob(cursor.getColumnIndex("photo")));
                cursor.moveToNext();
            }

          //  Toast.makeText(getApplicationContext(), "cp" + res, Toast.LENGTH_SHORT).show();
           // contentProvider = res.toString();


            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Passenger : "+contentProvider)
                   // .setMessage(" "+contentProvider)
                    // .setIcon(image)
                    .setPositiveButton("Request Accept",
                            new DialogInterface.OnClickListener() {
                                @TargetApi(11)
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(),"Tcp"+contentProvider,Toast.LENGTH_SHORT).show();

                                }
                            }).setView(image)
                    .setNegativeButton("Request Reject", new DialogInterface.OnClickListener() {
                        @TargetApi(11)
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).show();

        }

        @Override
        public void onLoaderReset (@NonNull androidx.loader.content.Loader < Cursor > loader) {

        }

        @Override
        public void onDestroy () {
            super.onDestroy();
        }

    @SuppressLint({"MissingPermission"})
    private final void requestLocationUpdate() {
        if (!this.uiHelper.isHaveLocationPermission((Context)this)) {
            ActivityCompat.requestPermissions((Activity)this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 2200);
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
                    Log.e("Location", latLng.latitude + " , " + latLng.longitude);
                    if (MainActivity.this.locationFlag) {
                        MainActivity.this.locationFlag = false;
                        MainActivity.this.animateCamera(latLng);
                    }

                    if (MainActivity.this.driverOnlineFlag) {
                        MainActivity.this.firebaseHelper.updateDriver(new Driver(latLng.latitude, latLng.longitude, (String)null));
                    }

                    MainActivity.this.showOrAnimateMarker(latLng);
                }
            }
        });
    }

    private final void showOrAnimateMarker(LatLng latLng) {
        if (this.currentPositionMarker == null) {
            GoogleMap var10001 = this.googleMap;
            if (var10001 == null) {
            }

            this.currentPositionMarker = var10001.addMarker(this.googleMapHelper.getDriverMarkerOptions(latLng));
        } else {
            MarkerAnimationHelper var10000 = this.markerAnimationHelper;
            Marker var2 = this.currentPositionMarker;
            if (var2 == null) {
            }

            var10000.animateMarkerToGB(var2, latLng, (LatLngInterpolator)(new Spherical()));
        }

    }

    private final void animateCamera(LatLng latLng) {
        CameraUpdate cameraUpdate = this.googleMapHelper.buildCameraUpdate(latLng);
        GoogleMap var10000 = this.googleMap;
        if (var10000 == null) {
        }

        var10000.animateCamera(cameraUpdate, 10, (CancelableCallback)null);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2200) {
            int value = grantResults[0];
            if (value == -1) {
                Toast.makeText((Context)this, (CharSequence)"Location Permission denied", Toast.LENGTH_SHORT).show();
                this.finish();
            } else if (value == 0) {
                this.requestLocationUpdate();
            }
        }

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

//        // $FF: synthetic method
//        public Companion(DefaultConstructorMarker $constructor_marker) {
//            this();
//        }
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }

}
