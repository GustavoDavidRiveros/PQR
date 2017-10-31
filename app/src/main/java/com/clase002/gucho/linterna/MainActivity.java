package com.clase002.gucho.linterna;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
     ImageButton boton;
    Camera camara;
    boolean pardo = false;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rl_linterna);

        boton = (ImageButton)findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pardo==false){
                    camara=Camera.open();
                    Camera.Parameters parameters = camara.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camara.setParameters(parameters);
                    camara.startPreview();

                    pardo=true;
                    Toast.makeText(MainActivity.this,"Linterna Prendida", Toast.LENGTH_SHORT).show();
                }else {
                    camara.stopPreview();
                    camara.release();
                    Toast.makeText(MainActivity.this,"Linterna Apagada", Toast.LENGTH_LONG).show();
                    pardo=false;
                }



            }
        });


    }


}
