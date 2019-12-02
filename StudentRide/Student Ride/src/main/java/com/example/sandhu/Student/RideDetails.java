package com.example.sandhu.Student;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RideDetails extends AppCompatActivity {
TextView distance, price, destination;
Button map,confirm;
GpsTracker gpsTracker;
    String destinationn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ride Details");
        distance = (TextView) findViewById(R.id.textViewdistance);
        price = (TextView) findViewById(R.id.textViewprice);
        destination = (TextView) findViewById(R.id.textViewdestination);
        map = (Button)findViewById(R.id.buttonmap);
        confirm = (Button)findViewById(R.id.buttonconfirm);

        gpsTracker = new GpsTracker(RideDetails.this);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gpsTracker.isGPSEnabled()&&gpsTracker.isNetworkAvailable(getApplicationContext())) {

                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("destinationn", destinationn);
                startActivity(intent);
                }
                else {
                    gpsTracker.showSettingsAlert();
                }
            }
        });
        getd();

    confirm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (gpsTracker.isGPSEnabled()&&gpsTracker.isNetworkAvailable(getApplicationContext())) {
                Intent intent = new Intent(getApplicationContext(), com.example.sandhu.activity.MainActivity.class);
                intent.putExtra("destinationn", destinationn);
                startActivity(intent);
            }
            else {
                gpsTracker.showSettingsAlert();
            }
            // startActivity(new Intent(getApplicationContext(), com.example.sandhu.activity.MainActivity.class));

        }
    });
    }

        public void getd(){
            Intent bb = getIntent();
            if (bb!=null)
            {
                String distanc = bb.getStringExtra("distance");
                 destinationn = bb.getStringExtra("destination");
                 String seats = bb.getStringExtra("seats");
distance.setText(distanc + " KM");
double a,b,c;
a = Double.parseDouble(distanc);
            b=Double.parseDouble(seats);
c= a+b;
String z = String.valueOf(c);
price.setText("$ "+z);
destination.setText(destinationn);
            }

        }

    @Override
    protected void onResume() {
        super.onResume();
        Intent bb = getIntent();
        if (bb!=null) {
            String distanc = bb.getStringExtra("distance");
            destinationn = bb.getStringExtra("destination");
            String seats = bb.getStringExtra("seats");
            distance.setText(distanc + " KM");
            double a, b, c;
            a = Double.parseDouble(distanc);
            b = Double.parseDouble(seats);
            c = a + b;
            String z = String.valueOf(c);
            price.setText("$ " + z);
            destination.setText(destinationn);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getd();

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    getd();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
