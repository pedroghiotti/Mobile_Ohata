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

    /*
        Método roda quando a activity é inicializada.
        Coloca na tela o XML correspondente à essa activity,
        pega referências aos elementos da tela e
        define as funções desses elementos (dos botões apenas, no caso).
        Também requisita do usuário permissões para usar as funções de localização e
        a câmera do dispositivo.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGeoLoc = (Button) findViewById(R.id.Button_GeoLoc);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);

        btnGeoLoc.setOnClickListener(
        
            new View.OnClickListener(){
            
                /*
                    Sobrepõe a função do método 'onClick' do OnClickListener atribuído ao botão.
                    Sua função passa a ser de colocar uma mensagem na tela contendo a localização (latitude e longitude) do dispositivo.
                */
                @Override
                public void onClick(View vw)
                {
                    GPStracker gpsTracker = new GPStracker(getApplication());
                    Location loc = gpsTracker.getLocation();

                    /*
                        Só coloca mensagem na tela caso 'loc' tenha algum valor
                        (acredito que para evitar que o método tente acessar um valor nulo)
                    */
                    if(loc != null)
                    {
                        double latitude = loc.getLatitude();
                        double longitude = loc.getLongitude();
                        Toast.makeText(getApplicationContext(), "Latitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();
                    }
                }
            }
        );

        /*
            Verifica se o usuário permitiu que o aplicativo acesse a câmera do dispositivo.
            Se não houver, a aplicação requisita a permissão do usuário.
        */
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        /*
            Pega uma referência à 'ImageView' presente no XML e, então,
            sobrepõe a função 'onClick' do OnclickListener atribuído ao botão.
            Sua função passa a ser de chamar o método 'TakePicture()'.
        */
        imgVwPicture =  (ImageView) findViewById(R.id.ImageView_Picture);
        findViewById(R.id.Button_TakePicture).setOnClickListener(
        
        new View.OnClickListener(){

                @Override
                public void onClick(View vw)
                {
                    TakePicture();
                }
            }
        );
    }

    /*
        Cria um Intent, classe que representa uma operação a ser executada pelo sistema do dispositivo, (no caso de tirar uma foto) e
        inicia essa atividade com 'startActivityForResult' (inicia a atividade e receberá um return em outro método).
    */
    private void TakePicture()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    /*
        Sobrepõe a função 'onActivityResult' da classe Activity (ou AppCompatActivity, no caso).
        Sua função passa a ser de receber o return do método 'startActivityForResult'.
        Começa por verificar se o resultado é válido e corresponde a atividade requisitada no método 'TakePicture'.
        Recebe então a foto que foi tirada e a coloca na tela (no lugar da imagem definida no XML).
    */
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
