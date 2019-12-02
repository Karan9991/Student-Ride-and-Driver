package com.example.sandhu.model2;



public final class Driver {
    private double lat;
    private double lng;
    private String driverId = "";

    public final double getLat() {
        return this.lat;
    }

    public final void setLat(double var1) {
        this.lat = var1;
    }

    public final double getLng() {
        return this.lng;
    }

    public final void setLng(double var1) {
        this.lng = var1;
    }

    public final String getDriverId() {
        return this.driverId;
    }

    public final void setDriverId( String var1) {
        this.driverId = var1;
    }



    public final void updateDriver(double lat, double lng) {
        this.lat = lat;
        this.lat = lng;
    }
}

