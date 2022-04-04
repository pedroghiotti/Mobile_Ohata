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

    /*
        Constructor da classe.
        Pede o contexto da aplicação.
        (A classe context dá acesso às funções do sistema do dispositivo, se eu entendi corretamente)
    */
    public GPStracker(Context c)
    {
        context = c;
    }

    /*
        Utiliza as funções de localização do dispositivo para retornar sua localização
        desde que a aplicação tenha a permissão do usuário e o GPS do dispositivo esteja ativo.
    */
    public Location getLocation()
    {
        /*
            Verifica se o usuário deu a permissão para que o aplicativo acesse as funções de localização do dispositivo.
        */
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            /*
                Coloca na tela uma notificação avisando o usuário que o aplicativo não tem a permissão pra utilizar a função.
            */
            Toast.makeText(context, "Não tem permissão", Toast.LENGTH_SHORT).show();
            return null;
        }

        LocationManager locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled =  locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        /*
            Verifica se o GPS do dispositivo está habilitado.
            Se sim, irá retornar a localização do dispositivo e
            se não, coloca na tela uma notificação requisitando que o usuário o habilite.
        */
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
