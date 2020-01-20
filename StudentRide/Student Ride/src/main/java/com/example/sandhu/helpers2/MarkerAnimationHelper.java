package com.example.sandhu.helpers2;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.sandhu.Student.PointsParser;
import com.example.sandhu.interfaces2.LatLngInterpolatorNew;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.example.sandhu.interfaces2.LatLngInterpolator;


public final class MarkerAnimationHelper {
    public static final MarkerAnimationHelper INSTANCE;
    private LatLng startPositionn, endPositionn;
    LatLng newPos;
    double lat,lng;
    private int indexx, nextt;
    private int indexca, nextca;
    private LatLng startPositionca, endPositionca;
    private float v;
    final Handler handlerca = new Handler();


    public final void animateMarkerToGB(final Marker marker, final LatLng finalPosition, final LatLngInterpolator latLngInterpolator) {
        indexx = -1;
        nextt = 1;
        final long duration = 1000;
        final LatLng startPosition = marker.getPosition();
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        final float durationInMs = 2000.0F;
        handler.post((Runnable)(new Runnable() {
            private long elapsed;
            private float t;
            private float v;

            public final long getElapsed() {
                return this.elapsed;
            }

            public final void setElapsed(long var1) {
                this.elapsed = var1;
            }

            public final float getT() {
                return this.t;
            }

            public final void setT(float var1) {
                this.t = var1;
            }

            public final float getV() {
                return this.v;
            }

            public final void setV(float var1) {
                this.v = var1;
            }

            public void run() {
                final LatLngInterpolatorNew latLngInterpolato = new LatLngInterpolatorNew.LinearFixed();
        final LatLng endPosition = new LatLng(43.658038, -79.760535);

                this.elapsed = SystemClock.uptimeMillis() - start;
                this.t = (float)this.elapsed / durationInMs;
                this.v = interpolator.getInterpolation(this.t);
                Marker var10000 = marker;
                LatLngInterpolator var10001 = latLngInterpolator;
                float var10002 = this.v;
                LatLng var10003 = startPosition;
                var10000.setPosition(var10001.interpolate(var10002, var10003, finalPosition));
                var10000.setRotation(latLngInterpolato.getBearing(startPosition, endPosition));
                if (this.t < (float)1) {
                    handler.postDelayed((Runnable)this, 16L);
                }

            }
        }));
        if (indexx < PointsParser.polyLineList.size() - 1) {
            indexx++;
            nextt = indexx + 1;
        }
        if (indexx < PointsParser.polyLineList.size() - 1){
            startPositionn = PointsParser.polyLineList.get(indexx);
            endPositionn = PointsParser.polyLineList.get(nextt);
            Log.i("startend","startenddddddddddddd"+startPositionn+" "+endPositionn);
        }

        long elapsedv = SystemClock.uptimeMillis() - start;
        float tv = interpolator.getInterpolation((float) elapsedv/duration
        );
        lng = tv * endPositionn.longitude + (1 - tv)
                * startPositionn.longitude;
        lat = tv * endPositionn.latitude + (1 - tv)
                * startPositionn.latitude;
        newPos = new LatLng(lat, lng);
        marker.setPosition(newPos);
        marker.setAnchor(0.5f, 0.5f);
        // if (null != newPos) {
        //  marker.setRotation(getBearing(startPositionn, newPos));


        marker.setRotation(getBearing(startPositionn, newPos));
    }
    public void createAnimation(final Marker marker, GoogleMap mMap){
        // Collections.reverse(PointsParser.polyLineList);
        indexca = -1;
        nextca = 1;
        handlerca.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (indexca < PointsParser.polyLineList.size() - 1) {
                    indexca++;
                    nextca = indexca + 1;
                }
                if (indexca < PointsParser.polyLineList.size() - 1) {
                    startPositionca = PointsParser.polyLineList.get(indexca);
                    endPositionca = PointsParser.polyLineList.get(nextca);
                    // Log.i("startend","startenddddddddddddd"+startPosition+" "+endPosition);
                }
                if (indexca == 0) {
//                    BeginJourneyEvent beginJourneyEvent = new BeginJourneyEvent();
//                    beginJourneyEvent.setBeginLatLng(startPositionca);
//                    JourneyEventBus.getInstance().setOnJourneyBegin(beginJourneyEvent);
                }
                if (indexca == PointsParser.polyLineList.size() - 1) {
//                    EndJourneyEvent endJourneyEvent = new EndJourneyEvent();
//                    endJourneyEvent.setEndJourneyLatLng(new LatLng(PointsParser.polyLineList.get(indexca).latitude,
//                            PointsParser.polyLineList.get(indexca).longitude));
//                    JourneyEventBus.getInstance().setOnJourneyEnd(endJourneyEvent);
                }
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(1000);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        v = valueAnimator.getAnimatedFraction();
                        //  Log.i("vv","vvvvvvvvvvvvv"+v);

                        lng = v * endPositionca.longitude + (1 - v)
                                * startPositionca.longitude;
                        lat = v * endPositionca.latitude + (1 - v)
                                * startPositionca.latitude;
                        LatLng newPos = new LatLng(lat, lng);
//                        CurrentJourneyEvent currentJourneyEvent = new CurrentJourneyEvent();
//                        currentJourneyEvent.setCurrentLatLng(newPos);
//                        JourneyEventBus.getInstance().setOnJourneyUpdate(currentJourneyEvent);
                        marker.setPosition(newPos);
                        marker.setAnchor(0.5f, 0.5f);
                        marker.setRotation((float) getBearing(startPositionca, newPos));
                        //  Log.i("bearing","bbbbbbbbbbbbbbb"+ getBearing(startPosition, newPos));
                        // Log.i("sd",startPosition +"v"+ newPos);

//                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition
//                                (new CameraPosition.Builder().target(newPos)
//                                        .zoom(15.5f).build()));
                    }
                });
                valueAnimator.start();
                if (indexca != PointsParser.polyLineList.size() - 1) {
                    handlerca.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    private MarkerAnimationHelper() {
    }

    static {
        MarkerAnimationHelper var0 = new MarkerAnimationHelper();
        INSTANCE = var0;
    }
    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }
}
