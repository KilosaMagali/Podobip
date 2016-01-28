package students.molecular.podobip.services;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import students.molecular.podobip.listener.StepListener;

/**
 * Created by meradi on 28/01/16.
 */
public class GPSTracker implements StepListener, LocationListener {

    private static String TAG = GPSTracker.class.getSimpleName();
    private ArrayList<LatLng> positions;
    private final Context mContext;
    boolean isGPSEnabled = true;
    boolean isNetworkEnabled = true;
    boolean canGetLocation = true;
    Location location;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000;
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
        positions = new ArrayList<>();
        getLocation();
    }

    @Override
    public void onStepEvent() {
        Log.d(TAG, "step event");
        Log.d(TAG, "current location is " + location);
        if (location == null)
            getLocation();
        if (location != null) {
            new LatLng(location.getLatitude(), location.getLongitude());
            System.out.println(positions);
            positions.add(new LatLng(location.getLatitude(), location.getLongitude()));
        }

    }

    public void getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(mContext.LOCATION_SERVICE);

            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                showSettingsAlert();
            } else {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null && locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                0,
                                0, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null && locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    /**
     * Function to check GPS/Wi-Fi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    /**
     * Function to show settings alert dialog.
     * On pressing the Settings button it will launch Settings Options.
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }


    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public ArrayList<LatLng> getPositions() {
        return positions;
    }

}
