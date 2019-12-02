package com.example.sandhu.Student;

import android.content.ContentValues;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ConfirmRide extends AppCompatActivity {
    GpsTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_ride);
//        gpsTracker = new GpsTracker(getApplication());
//        ContentValues values = new ContentValues();
//        values.put(MyProvider.name, "Karan");
//        values.put(MyProvider.sourcce, 43.6532);
//        values.put(MyProvider.destinationn, 79.3832);
//        Uri uri = getContentResolver().insert(MyProvider.CONTENT_URI, values);
//        Toast.makeText(getBaseContext(), "New record inserted", Toast.LENGTH_LONG)
//                .show();

    }
//    public void onClickAddName(View view) {
//        ContentValues values = new ContentValues();
//        values.put(MyProvider.name, "Karan");
//        values.put(MyProvider.sourcce, 43.6532);
//        values.put(MyProvider.destinationn, 79.3832);
//        Uri uri = getContentResolver().insert(MyProvider.CONTENT_URI, values);
//      //  int uril = getContentResolver().update(MyProvider.CONTENT_URI, values, "id=1",null);
//        Toast.makeText(getBaseContext(), "New record inserted"+gpsTracker.testLoc(), Toast.LENGTH_LONG)
//                .show();
//    }
}
