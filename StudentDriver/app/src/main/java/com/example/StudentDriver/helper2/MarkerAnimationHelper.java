package com.example.StudentDriver.helper2;

import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.example.StudentDriver.interfaces2.LatLngInterpolator;


public final class MarkerAnimationHelper {
    public final void animateMarkerToGB(final Marker marker, final LatLng finalPosition, final LatLngInterpolator latLngInterpolator) {

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
                this.elapsed = SystemClock.uptimeMillis() - start;
                this.t = (float)this.elapsed / durationInMs;
                this.v = interpolator.getInterpolation(this.t);
                Marker var10000 = marker;
                LatLngInterpolator var10001 = latLngInterpolator;
                float var10002 = this.v;
                LatLng var10003 = startPosition;
                var10000.setPosition(var10001.interpolate(var10002, var10003, finalPosition));
                if (this.t < (float)1) {
                    handler.postDelayed((Runnable)this, 16L);
                }

            }
        }));
    }
}

