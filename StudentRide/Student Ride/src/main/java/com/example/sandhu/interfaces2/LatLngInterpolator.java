package com.example.sandhu.interfaces2;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

//1.0E-6D
public interface LatLngInterpolator {
    LatLng interpolate(float var1, LatLng var2, LatLng var3);


    public static final class Spherical implements LatLngInterpolator {
        @Override
        public LatLng interpolate(float fraction, LatLng a, LatLng b) {
            double fromLat = Math.toRadians(a.latitude);
            double fromLng = Math.toRadians(a.longitude);
            double toLat = Math.toRadians(b.latitude);
            double toLng = Math.toRadians(b.longitude);
            double cosFromLat = Math.cos(fromLat);
            double cosToLat = Math.cos(toLat);
            double angle = this.computeAngleBetween(fromLat, fromLng, toLat, toLng);
            double sinAngle = Math.sin(angle);
            if (sinAngle < 1.0E-6D) {

                return a;
            } else {
                double temp1 = Math.sin((double)((float)1 - fraction) * angle) / sinAngle;
                double temp2 = Math.sin((double)fraction * angle) / sinAngle;
                double x = temp1 * cosFromLat * Math.cos(fromLng) + temp2 * cosToLat * Math.cos(toLng);
                double y = temp1 * cosFromLat * Math.sin(fromLng) + temp2 * cosToLat * Math.sin(toLng);
                double z = temp1 * Math.sin(fromLat) + temp2 * Math.sin(toLat);
                double lat = Math.atan2(z, Math.sqrt(x * x + y * y));
                double lng = Math.atan2(y, x);
                return new LatLng(Math.toDegrees(lat), Math.toDegrees(lng));
            }
        }

        private final double computeAngleBetween(double fromLat, double fromLng, double toLat, double toLng) {
            double dLat = fromLat - toLat;
            double dLng = fromLng - toLng;
         //   Log.d("vvvvvvvvvvvvvv","vvvvvvvvvvvvvvvvvvv");
            return (double)2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLat / (double)2), 2.0D) + Math.cos(fromLat) * Math.cos(toLat) * Math.pow(Math.sin(dLng / (double)2), 2.0D)));
        }
    }
}
