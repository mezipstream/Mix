package me.zipstream.mix.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import me.zipstream.mix.base.MyApplication;

public class LocationUtil {

    public static String getLocationInfo() {

        LocationManager locationManager = (LocationManager)
                MyApplication.getContext().getSystemService(Context.LOCATION_SERVICE);

        String provider = LocationManager.NETWORK_PROVIDER;

        Location location = locationManager.getLastKnownLocation(provider);

        return location.getLongitude() + "," + location.getLatitude();
    }
}
