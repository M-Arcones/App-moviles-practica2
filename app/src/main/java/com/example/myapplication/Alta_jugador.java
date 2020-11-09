package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Alta_jugador extends AppCompatActivity implements View.OnClickListener{

    private Button ButtonVolver, ButtonGuardar;
    private String usuario_seleccionado;
    private int num_preguntas;
    private DbManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altamodificacion_jugador);
        Bundle b=this.getIntent().getExtras();
        num_preguntas=b.getInt("n_preguntas");
        usuario_seleccionado=b.getString("nom_jugador");
        ButtonVolver=findViewById(R.id.BtnVolver);
        ButtonGuardar=findViewById(R.id.BtnGuardar);
        ButtonVolver.setOnClickListener(this);
        ButtonGuardar.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent;
        Bundle b = new Bundle();
        b.putInt("n_preguntas", num_preguntas);
        switch (v.getId()) {
            case (R.id.BtnVolver):
                b.putString("nom_jugador", usuario_seleccionado);
                break;
            case (R.id.BtnGuardar):
                this.dbManager = new DbManager(this);
                dbManager.insertJugador(((EditText) findViewById(R.id.InputTextNombre)).getText().toString());
                CharSequence text = "Uusrio Guardado";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
                b.putString("nom_jugador", ((EditText) findViewById(R.id.InputTextNombre)).getText().toString());
            break;
        }
        intent = new Intent(this, GestionUsuario.class);
        intent.putExtras(b);
        startActivity(intent);
    }
}
