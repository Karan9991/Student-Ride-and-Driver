package com.example.sandhu.interfaces2;
import com.google.android.gms.maps.model.LatLng;

public interface LatLngInterpolatorNew {
    LatLng interpolate(float fraction, LatLng a, LatLng b);

   public final class LinearFixed implements LatLngInterpolatorNew {
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
           return (double)2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLat / (double)2), 2.0D) + Math.cos(fromLat) * Math.cos(toLat) * Math.pow(Math.sin(dLng / (double)2), 2.0D)));
       }
    }


    //Method for finding bearing between two points
    public default float getBearing(LatLng latLng1, LatLng latLng2) {
//        double lat = Math.abs(begin.latitude - end.latitude);
//        double lng = Math.abs(begin.longitude - end.longitude);
//
//        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
//            return (float) (Math.toDegrees(Math.atan(lng / lat)));
//        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
//            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
//        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
//            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
//        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
//            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
//        return -1;
//    }
//

        double lat1 = degreesToRadians(latLng1.latitude);
        double long1 = degreesToRadians(latLng1.longitude);
        double lat2 = degreesToRadians(latLng2.latitude);
        double long2 = degreesToRadians(latLng2.longitude);


        double dLon = (long2 - long1);


        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double radiansBearing = Math.atan2(y, x);


        return radiansToDegrees(radiansBearing);
    }
    public default float degreesToRadians(double degrees) {
        return (float) (degrees * Math.PI / 180.0);
    }

    public default float radiansToDegrees(double radians) {
        return (float) (radians * 180.0 / Math.PI);
    }
}