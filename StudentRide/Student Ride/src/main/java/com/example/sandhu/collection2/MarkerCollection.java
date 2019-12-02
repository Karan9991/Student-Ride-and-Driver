package com.example.sandhu.collection2;

import com.google.android.gms.maps.model.Marker;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;


public final class MarkerCollection {
    private static final List markers;
    public static final MarkerCollection INSTANCE;

    public final void insertMarker( Marker marker) {

        markers.add(marker);
    }

    public final Marker getMarker(String driverId) {
        //Intrinsics.checkParameterIsNotNull(driverId, "driverId");
        Iterator var3 = markers.iterator();

        Marker marker;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            marker = (Marker)var3.next();
        } while(marker.getTag()!= driverId);

        return marker;
    }

    public final void clearMarkers() {

        markers.clear();
    }

    public final void removeMarker( String driverId) {
     //   Intrinsics.checkParameterIsNotNull(driverId, "driverId");
        Marker marker = this.getMarker(driverId);
        if (marker != null) {
            marker.remove();
        }

        if (marker != null) {
            markers.remove(marker);
        }

    }

    public final List allMarkers() {
        return markers;
    }

    private MarkerCollection() {
    }

    static {
        MarkerCollection var0 = new MarkerCollection();
        INSTANCE = var0;
        markers = (List)(new LinkedList());
    }
}

