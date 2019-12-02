package com.example.sandhu.helpers2;

import com.example.sandhu.Student.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import kotlin.jvm.internal.DefaultConstructorMarker;


public final class GoogleMapHelper {
    private static final int ZOOM_LEVEL = 18;
    private static final int TILT_LEVEL = 25;
 //   public static final Companion Companion = new Companion((DefaultConstructorMarker)null);

    public final CameraUpdate buildCameraUpdate(LatLng latLng) {
        CameraPosition cameraPosition = (new Builder()).target(latLng).tilt((float)25).zoom((float)18).build();
        CameraUpdate var10000 = CameraUpdateFactory.newCameraPosition(cameraPosition);
        return var10000;
    }

    public final MarkerOptions getDriverMarkerOptions(LatLng position) {
        MarkerOptions options = this.getMarkerOptions(R.drawable.ic_car, position);
        options.flat(true);
        return options;
    }

    private final MarkerOptions getMarkerOptions(int resource, LatLng position) {
        MarkerOptions var10000 = (new MarkerOptions()).icon(BitmapDescriptorFactory.fromResource(resource)).position(position);
        return var10000;
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

