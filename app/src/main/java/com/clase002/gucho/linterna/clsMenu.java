package com.clase002.gucho.linterna;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by Gucho on 30/10/2017.
 */

public class clsMenu extends Activity {
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    SeekBar barIntencidad;
    SeekBar barRepeticiones;

    int repeticiones;
    int intencidad;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        preferences = getSharedPreferences("Linterna", Context.MODE_PRIVATE);
        editor = preferences.edit();
        barIntencidad= (SeekBar)findViewById(R.id.sbIntencidad);
        barRepeticiones= (SeekBar)findViewById(R.id.sbRepeticiones);

        LeerPreferencias();

        Button volver= (Button)findViewById(R.id.btnvolver) ;
        volver.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)  {

                        onBackPressed();
                    }
                }
        );

        Button guardar= (Button)findViewById(R.id.btnGuardar) ;
        guardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)  {

                        GuardarPreferencias();
                    }
                }
        );
    }

    private void GuardarPreferencias() {
        intencidad=barIntencidad.getProgress();
        repeticiones=barRepeticiones.getProgress();
        editor.putInt("repeticiones", repeticiones);
        editor.putInt("inte", intencidad);
        editor.commit();
        Toast.makeText(this,"Guardado",Toast.LENGTH_LONG).show();
    }

    private void LeerPreferencias() {
        repeticiones = preferences.getInt("repeticiones", 3);

        intencidad = preferences.getInt("inte", 50);


        barRepeticiones.setProgress(repeticiones);
        barIntencidad.setProgress(intencidad);
    }


}
