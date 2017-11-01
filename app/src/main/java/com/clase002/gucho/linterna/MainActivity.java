package com.clase002.gucho.linterna;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    ImageButton boton;
    Camera camara;
    boolean pardo = false;
    Button btn;
    int repeticiones;
    private ImageButton btnLinterna;
    private Context context;
    private Camera Camara;
    Camera.Parameters parametrosCamara;
    int frecuencia;
    //Runner Run;
    //Thread hilo;
    SeekBar skBarIntensidad;


 /*
        private class Runner implements Runnable {
            int freq;
            boolean stopRunning = false;

            @Override
            public void run() {
                Camera.Parameters paramsOn = Camara.getParameters();
                Camera.Parameters paramsOff = parametrosCamara;
                paramsOn.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                paramsOff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

                try {
                    while(!stopRunning) {
                        Camara.setParameters(paramsOn);
                        Camara.startPreview();
                        Thread.sleep(1000 - freq);
                        Camara.setParameters(paramsOff);
                        Camara.startPreview();
                        Thread.sleep(freq);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
       private void Intermitencia(boolean estado) {
            if(estado) {
                if(frecuencia != 0) {
                    Run = new Runner();
                    Run.freq = frecuencia;
                    hilo = new Thread(Run);
                    hilo.start();
                    return;
                } else {
                    parametrosCamara.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }
            }
            else {//if(!on)
                if(hilo != null) {
                    Run.stopRunning = true;
                    hilo = null;
                    parametrosCamara.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    return;
                } else {
                    parametrosCamara.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                }
            }
            Camara.setParameters(parametrosCamara);
            Camara.startPreview();
        }
*/

public void leer()
{
    SharedPreferences preferences;
    preferences=getSharedPreferences("Linterna", Context.MODE_PRIVATE);
    repeticiones=preferences.getInt("repeticiones",3);
    frecuencia =preferences.getInt("inte",50);

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rl_linterna);
        VerificarPermiso();


        leer();
        boton = (ImageButton)findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (!pardo)//Si esta Apagada
                {
                    camara = Camera.open();
                    Camera.Parameters parameters = camara.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camara.setParameters(parameters);
                    camara.startPreview();
                    pardo=true;
                    Toast.makeText(MainActivity.this,"Linterna Prendida", Toast.LENGTH_SHORT).show();

                }else // si esta prendida
                    {
                        camara.stopPreview();
                        camara.release();
                        Toast.makeText(MainActivity.this, "Linterna Apagada", Toast.LENGTH_LONG).show();
                        pardo = false;
                    }
            }
        });

        Button intermitente = (Button)findViewById(R.id.btnIntermitencia);
        intermitente.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        leer();
                        intermitencia();
                    }
                }
        );


        Button men = (Button)findViewById(R.id.btnMenu);
        men.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, clsMenu.class );
                        startActivity(i);
                    }
                }
        );

    }

    public void intermitencia(){
        Thread i = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int a=1; a<=repeticiones;a++){
                        camara = Camera.open();
                        Camera.Parameters parameters = camara.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camara.setParameters(parameters);
                        camara.startPreview();
                        Thread.sleep(1000-(frecuencia*10));
                        camara.stopPreview();
                        camara.release();
                        Thread.sleep(1000-(frecuencia*10));
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        i.start();
        try {
            i.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }

//Permisos
    private final int SolicitudPermisos = 0;
    private void VerificarPermiso() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "Tu version de Android no es 6 o posterior" + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CAMERA);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {Manifest.permission.CAMERA}, SolicitudPermisos);
                Toast.makeText(this, "Solicitud de permisos", Toast.LENGTH_LONG).show();

            }else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_LONG).show();
            }
        }
        return;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(SolicitudPermisos == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permisos otorgados", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Permisos no otorgados", Toast.LENGTH_LONG).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
