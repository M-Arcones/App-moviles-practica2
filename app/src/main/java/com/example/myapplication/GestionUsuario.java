package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.util.ArrayList;

public class GestionUsuario extends AppCompatActivity  implements View.OnClickListener{

    private int num_preguntas;
    private String usuario_seleccionado;
    private Button ButtonVolver, ButtonNuevoUsuario;

    private DbManager dbManager;
    private Cursor c_usuarios;

    private ArrayList<String> arrayList = new ArrayList<>();
    //private ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
    private int Index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuario);
        Bundle b=this.getIntent().getExtras();
        num_preguntas=b.getInt("n_preguntas");
        usuario_seleccionado=b.getString("nom_jugador");
        ButtonVolver=findViewById(R.id.Btn_Volver);
        ButtonNuevoUsuario=findViewById(R.id.Btn_AltaJugador);
        ButtonVolver.setOnClickListener(this);
        ButtonNuevoUsuario.setOnClickListener(this);
        Spinner Spn_Jugadores = findViewById(R.id.Spn_Jugadores);

        this.dbManager = new DbManager(this);
        c_usuarios=dbManager.getJugadores();
        c_usuarios.moveToFirst();
        int i=0;
        while(!c_usuarios.isAfterLast()) {
            if(usuario_seleccionado.equals(c_usuarios.getString(c_usuarios.getColumnIndex(DbContract.DbEntry.COLUMN_NOMJUGADOR)))){
                Index=i;
            }
            arrayList.add(c_usuarios.getString(c_usuarios.getColumnIndex(DbContract.DbEntry.COLUMN_NOMJUGADOR)));
            c_usuarios.moveToNext();
            i+=1;
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spn_Jugadores.setAdapter(arrayAdapter);
        Spn_Jugadores.setSelection(Index);

    }

    public void onClick(View v) {
        Intent intent;
        Bundle b = new Bundle();
        Spinner Spn_Jugadores = findViewById(R.id.Spn_Jugadores);
        usuario_seleccionado = Spn_Jugadores.getSelectedItem().toString();
        switch (v.getId()) {
            case (R.id.Btn_Volver):
                b.putString("nom_jugador", usuario_seleccionado);
                b.putInt("n_preguntas", num_preguntas);
                intent = new Intent(this, Ajustes.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case (R.id.Btn_AltaJugador):
                b.putString("nom_jugador", usuario_seleccionado);
                b.putInt("n_preguntas", num_preguntas);
                intent = new Intent(this, Alta_jugador.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
        }
    }
}