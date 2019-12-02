package com.example.StudentDriver.model2;



public final class Driver {
    private final double lat;
    private final double lng;
    private final String driverId;

    public final double getLat() {
        return this.lat;
    }

    public final double getLng() {
        return this.lng;
    }

    public final String getDriverId() {
        return this.driverId;
    }

    public Driver(double lat, double lng, String driverId) {
        super();
        this.lat = lat;
        this.lng = lng;
        this.driverId = driverId;
    }

//    // $FF: synthetic method
//    public Driver(double var1, double var3, String var5, int var6, DefaultConstructorMarker var7) {
//        if ((var6 & 4) != 0) {
//            var5 = "0000";
//        }
//
//        this(var1, var3, var5);
//    }

    public final double component1() {
        return this.lat;
    }

    public final double component2() {
        return this.lng;
    }

    public final String component3() {
        return this.driverId;
    }

    public final Driver copy(double lat, double lng,  String driverId) {
        return new Driver(lat, lng, driverId);
    }
//
//    // $FF: synthetic method
//    public static com.spartons.driverapp.model.Driver copy$default(com.spartons.driverapp.model.Driver var0, double var1, double var3, String var5, int var6, Object var7) {
//        if ((var6 & 1) != 0) {
//            var1 = var0.lat;
//        }
//
//        if ((var6 & 2) != 0) {
//            var3 = var0.lng;
//        }
//
//        if ((var6 & 4) != 0) {
//            var5 = var0.driverId;
//        }
//
//        return var0.copy(var1, var3, var5);
//    }

    public String toString() {
        return "Driver(lat=" + this.lat + ", lng=" + this.lng + ", driverId=" + this.driverId + ")";
    }

    public int hashCode() {
        long var10000 = Double.doubleToLongBits(this.lat);
        int var1 = (int)(var10000 ^ var10000 >>> 32) * 31;
        long var10001 = Double.doubleToLongBits(this.lng);
        var1 = (var1 + (int)(var10001 ^ var10001 >>> 32)) * 31;
        String var2 = this.driverId;
        return var1 + (var2 != null ? var2.hashCode() : 0);
    }

    public boolean equals( Object var1) {
        if (this != var1) {
            if (var1 instanceof Driver) {
                Driver var2 = (Driver)var1;
                if (Double.compare(this.lat, var2.lat) == 0 && Double.compare(this.lng, var2.lng) == 0 ) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
}
