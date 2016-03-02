package solvers.problem.global.trackingcollector;

import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Johannes on 02.03.2016.
 */


public class Tracker {

    private Context parentContext;
    private ArrayList<Location> locationsLog;
    public String log = "";

    public Tracker(Context parentContext) {
        this.parentContext = parentContext;
        locationsLog = new ArrayList<>();
    }

    public ArrayList<Location> getLocationsLog() {
        return locationsLog;
    }
    public void clearLocationsLog() {locationsLog.clear();}

    public void startLocationTracking() {
        LocationManager locationManager = (LocationManager) parentContext.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                locationsLog.add(location);
                System.out.println("Position: " + location.toString());
                System.out.println("latitude: " + location.getLatitude());
                System.out.println("longitude: " + location.getLongitude());
                log += location.getLongitude() + " " + location.getLatitude() + "\n";
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                System.out.println("1");
            }

            @Override
            public void onProviderEnabled(String provider) {
                System.out.println("2");

            }

            @Override
            public void onProviderDisabled(String provider) {
                System.out.println(provider);
                new AlertDialog.Builder(parentContext)
                        .setTitle("Location disabled")
                        .setMessage("The " + provider + " provider of your device is disabled." +
                                " Go to your device's settings to configure.")
                        .setNeutralButton("OK", null)
                        .show();

                System.out.println("3");
            }
        };

        System.out.println("asking for updates");
        try {
            locationManager.requestLocationUpdates(getProvider(locationManager),
                    R.integer.MIN_TIME, R.integer.MIN_DISTANCE, locationListener);
        } catch (SecurityException e) {}

    }

    private String getProvider(LocationManager manager) {

        boolean isGpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGpsEnabled && !isNetworkEnabled) {
            return LocationManager.GPS_PROVIDER;
        } else {
            return LocationManager.NETWORK_PROVIDER;
        }
    }





}