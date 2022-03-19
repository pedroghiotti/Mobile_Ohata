package com.example.aula_mobile;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class GPStracker implements LocationListener
{
    Context context;

    // ?
    public GPStracker(Context c)
    {
        context = c;
    }

    //
    public Location getLocation()
    {
        // ?
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // ?
            Toast.makeText(context, "Não tem permissão", Toast.LENGTH_SHORT).show();
            return null;
        }

        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled =  locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // ?
        if(isGPSEnabled)
        {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
            Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return loc;
        }
        else
        {
            Toast.makeText(context, "Habilite o GPS", Toast.LENGTH_LONG).show();
        }

        return null;
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {}

    @Override
    public void onLocationChanged(@NonNull Location location) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
