package com.example.sandhu.Student;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;

import com.dd.processbutton.iml.ActionProcessButton;
import com.example.sandhu.Main3Activity;
import com.example.sandhu.signin.Signin;
import com.example.sandhu.signin.forgot_pas.Forgot_Password;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
EditText editText;
TextView tvname,tvemail;
//Button button;
double distance,fdistance;
ImageView img;
    GpsTracker gpsT = new GpsTracker(this);
DatabaseHelper databaseHelper;

    AlertDialog alertDialog1;
    CharSequence[] values = {" 1 "," 2 "," 3 "," 4 "};
    Boolean seatselected;
    double seats=0.0;
    SharedPreferences sharedPreferences;
    public static final String EXTRAS_ENDLESS_MODE = "EXTRAS_ENDLESS_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        databaseHelper = new DatabaseHelper(this);
        editText = (EditText)findViewById(R.id.editText6);
      //  button = (Button)findViewById(R.id.button5);
     //   button.setEnabled(false);
///////////////////////

        final ProgressGenerator progressGenerator = new ProgressGenerator(this::CreateAlertDialogWithRadioButtonGroup);
        final ActionProcessButton btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
       // btnSignIn.setEnabled(false);
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getBoolean(EXTRAS_ENDLESS_MODE)) {
            btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        } else {
            btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (editText.length()==6) {
                   // btnSignIn.setEnabled(true);
                   progressGenerator.start(btnSignIn);
                   //btnSignIn.setEnabled(false);
                    GpsTracker gpsTracker = new GpsTracker(getApplication());

                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(getApplication(), Locale.getDefault());
                    try {
           //             progressGenerator.start(btnSignIn);

                        addresses = geocoder.getFromLocationName(editText.getText().toString()
                                , 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
//getting current location
                        Location location = new Location("Point A");
                        location.setLatitude(gpsTracker.getLatitude());
                        location.setLongitude(gpsTracker.getLongitude());

//getting destination location
                        Location dlocation = new Location("Point B");
                        dlocation.setLatitude(addresses.get(0).getLatitude());
                        dlocation.setLongitude(addresses.get(0).getLongitude());
//kilometere figure round up
                        float a = location.distanceTo(dlocation) / 1000;
                        distance = a;
//getting two digits after decimal
                        DecimalFormat df = new DecimalFormat();
                        df.setMaximumFractionDigits(2);
                        fdistance = Double.valueOf(df.format(distance));
                        System.out.println(df.format(distance));
//    distance =  (int) Math.ceil(a);
//sending postal code, distance and price to RideDetails activity
                    //    CreateAlertDialogWithRadioButtonGroup();
//if (seatselected) {
//    Intent intent = new Intent(getApplicationContext(), RideDetails.class);
//    intent.putExtra("distance", df.format(distance) + "").putExtra("destination", editText.getText().toString()).putExtra("seats", seats);
//    startActivity(intent);
//}
                    } catch (Exception e) {
                        e.printStackTrace();
                       // gpsT.showSettingsAlert();

                    }



               }

            }
        });
//////////////////////
//button.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        GpsTracker gpsTracker = new GpsTracker(getApplication());
//
//        Geocoder geocoder;
//        List<Address> addresses;
//        geocoder = new Geocoder(getApplication(), Locale.getDefault());
//try {
//    addresses = geocoder.getFromLocationName(editText.getText().toString()
//            , 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//    String city = addresses.get(0).getLocality();
////getting current location
//    Location location = new Location("Point A");
//    location.setLatitude(gpsTracker.getLatitude());
//    location.setLongitude(gpsTracker.getLongitude());
//
////getting destination location
//    Location dlocation = new Location("Point B");
//    dlocation.setLatitude(addresses.get(0).getLatitude());
//    dlocation.setLongitude(addresses.get(0).getLongitude());
////kilometere figure round up
//    float a = location.distanceTo(dlocation)/1000;
//    distance =  a;
////getting two digits after decimal
//    DecimalFormat df = new DecimalFormat();
//    df.setMaximumFractionDigits(2);
//    fdistance = Double.valueOf(df.format(distance));
//    System.out.println(df.format(distance));
////    distance =  (int) Math.ceil(a);
////sending postal code, distance and price to RideDetails activity
//    CreateAlertDialogWithRadioButtonGroup();
////if (seatselected) {
////    Intent intent = new Intent(getApplicationContext(), RideDetails.class);
////    intent.putExtra("distance", df.format(distance) + "").putExtra("destination", editText.getText().toString()).putExtra("seats", seats);
////    startActivity(intent);
////}
//}catch (Exception e){
//    e.printStackTrace();
//    gpsT.showSettingsAlert();
//
//}
//
//        }
//});

//for set postal code format
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        editText.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editText.length() == 0){
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
           // button.setEnabled(false);
        }
        else if (editText.length() == 1){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            //button.setEnabled(false);

        }
        else if (editText.length() == 2){
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            //button.setEnabled(false);

        }
        else if (editText.length() == 3){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            //button.setEnabled(false);

        }
        else if (editText.length() == 4){
            editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            //button.setEnabled(false);

        }
        else if (editText.length() == 5){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
          //  button.setEnabled(false);

        }
        else if (editText.length() == 6){
//button.setEnabled(true);
        }

    }
});

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
navigationView.setItemTextColor(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));
    navigationView.setItemIconTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)));}
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        img = (ImageView)findViewById(R.id.imageViewv);
img.setImageBitmap(convertToBitmap(databaseHelper.getMoviep(1).getImage()));
        tvname = (TextView)findViewById(R.id.namenav);
        tvemail = (TextView)findViewById(R.id.emailnav);
        tvemail.setText(databaseHelper.getMoviep(1).getEmail());
        tvname.setText(databaseHelper.getMoviep(1).getFname()+" "+databaseHelper.getMoviep(1).getLname());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       // tv.setText("Hello World");
        if (id == R.id.nav_camera) {
         //   tv.setText("Hello World");

            startActivity(new Intent(this, Profile.class));
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
             startActivity(new Intent(this, User_Payment.class));

        } else if (id == R.id.logout) {
            //databaseHelper.deletesignAll();
            SharedPreferences.Editor mEditor = sharedPreferences.edit();
//            mEditor.clear();
//            mEditor.commit();
            mEditor.putString("signin", null);
            mEditor.putString("userEmail", null);
            mEditor.putString("userPassword", null);
            mEditor.apply();
            startActivity(new Intent(this, Signin.class));
            finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public void CreateAlertDialogWithRadioButtonGroup() {
        if (gpsT.isNetworkAvailable(getApplicationContext()) && gpsT.isGPSEnabled()){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("How many seats you need?");

        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {

                switch (item) {
                    case 0:

                        seats = 0.0;
                        Intent intent = new Intent(getApplicationContext(), RideDetails.class);
                        intent.putExtra("distance", fdistance + "").putExtra("destination", editText.getText().toString()).putExtra("seats", seats + "");
                        startActivity(intent);
                        break;
                    case 1:

                        seats = 0.30;
                        Intent intentt = new Intent(getApplicationContext(), RideDetails.class);
                        intentt.putExtra("distance", fdistance + "").putExtra("destination", editText.getText().toString()).putExtra("seats", seats + "");
                        startActivity(intentt);
                        break;
                    case 2:

                        seats = 0.40;
                        Intent intenttt = new Intent(getApplicationContext(), RideDetails.class);
                        intenttt.putExtra("distance", fdistance + "").putExtra("destination", editText.getText().toString()).putExtra("seats", seats + "");
                        startActivity(intenttt);
                        break;
                    case 3:

                        seats = 0.60;
                        Intent intentttt = new Intent(getApplicationContext(), RideDetails.class);
                        intentttt.putExtra("distance", fdistance + "").putExtra("destination", editText.getText().toString()).putExtra("seats", seats + "");
                        startActivity(intentttt);
                        break;
                }
                alertDialog1.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();
    }else {
            gpsT.showSettingsAlert();
        }
    }
    //convert image to Bitmp
    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }
}
