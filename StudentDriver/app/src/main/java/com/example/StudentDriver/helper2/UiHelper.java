package com.example.StudentDriver.helper2;

import android.content.Context;
import android.location.LocationManager;
import android.os.Build.VERSION;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationRequest;
import com.example.StudentDriver.interfaces2.IPositiveNegativeListener;


public final class UiHelper {
    public final boolean isPlayServicesAvailable( Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return status == 0;
    }

    public final boolean isHaveLocationPermission( Context context) {
        return VERSION.SDK_INT < 23 || ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
    }

    public final boolean isLocationProviderEnabled( Context context) {
        Object var10000 = context.getSystemService(Context.LOCATION_SERVICE);

            LocationManager locationManager = (LocationManager)var10000;
            return !locationManager.isProviderEnabled("gps") && !locationManager.isProviderEnabled("network");

    }

    public final void showPositiveDialogWithListener(Context callingClassContext, String title, String content, final IPositiveNegativeListener positiveNegativeListener, String positiveText, boolean cancelable) {

        this.buildDialog(callingClassContext, title, content).getBuilder().positiveText((CharSequence)positiveText).positiveColor(this.getColor(-500007, callingClassContext)).onPositive((SingleButtonCallback)(new SingleButtonCallback() {
            public final void onClick(MaterialDialog $noName_0, DialogAction $noName_1) {

                positiveNegativeListener.onPositive();
            }
        })).cancelable(cancelable).show();
    }

    private final MaterialDialog buildDialog(Context callingClassContext, String title, String content) {
        MaterialDialog var10000 = (new Builder(callingClassContext)).title((CharSequence)title).content((CharSequence)content).build();
        return var10000;
    }

    private final int getColor(int color, Context context) {
        return ContextCompat.getColor(context, color);
    }


    public final LocationRequest getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(100);
        locationRequest.setInterval(3000L);
        return locationRequest;
    }
}
