package com.example.StudentDriver.helper2;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.StudentDriver.model2.Driver;


public final class FirebaseHelper {
    private final DatabaseReference onlineDriverDatabaseReference;
    private static final String ONLINE_DRIVERS = "online_drivers";
    //public static final Companion Companion = new com.spartons.driverapp.helper.FirebaseHelper.Companion((DefaultConstructorMarker)null);

    public final void updateDriver( Driver driver) {
        this.onlineDriverDatabaseReference.setValue(driver);
        Log.e("Driver Info", " Updated");
    }

    public final void deleteDriver() {
        this.onlineDriverDatabaseReference.removeValue();
    }

    public FirebaseHelper(String driverId) {
        super();
        FirebaseDatabase var10001 = FirebaseDatabase.getInstance();
        DatabaseReference var2 = var10001.getReference().child("online_drivers").child(driverId);
        this.onlineDriverDatabaseReference = var2;
        this.onlineDriverDatabaseReference.onDisconnect().removeValue();
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
