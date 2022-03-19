package com.example.aula_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    // Variáveis
    private ImageView imgVwPicture;
    private Button btnGeoLoc;

    // ?
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGeoLoc = (Button) findViewById(R.id.Button_GeoLoc);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        btnGeoLoc.setOnClickListener(new View.OnClickListener(){
            // ?
            @Override
            public void onClick(View vw)
            {
                GPStracker gpsTracker = new GPStracker(getApplication());
                Location loc = gpsTracker.getLocation();

                // ?
                if(loc != null)
                {
                    double latitude = loc.getLatitude();
                    double longitude = loc.getLongitude();
                    Toast.makeText(getApplicationContext(), "Latitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();
                }
            }
        });

        // ?
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        // ?
        imgVwPicture =  (ImageView) findViewById(R.id.ImageView_Picture);
        findViewById(R.id.Button_TakePicture).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View vw)
            {
                TakePicture();
            }
        });
    }

    // ?
    private void TakePicture()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    // ?
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap picture = (Bitmap) extras.get("data");
            imgVwPicture.setImageBitmap(picture);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}