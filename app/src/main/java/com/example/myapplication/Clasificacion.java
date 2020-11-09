package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class Clasificacion extends AppCompatActivity  implements View.OnClickListener{
    private Button ButtonVolver;
    private String usuario_seleccionado;
    private int num_preguntas;
    private Animation scaleUp, scaleDown;
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=this.getIntent().getExtras();
        num_preguntas=b.getInt("n_preguntas");
        usuario_seleccionado=b.getString("nom_jugador");
        setContentView(R.layout.activity_clasificacion);
        ButtonVolver=findViewById(R.id.Btnvolver);
        ButtonVolver.setOnClickListener(this);

        TextView[] Txt_Jugadores = {findViewById(R.id.TXT_NomJugador1), findViewById(R.id.TXT_NomJugador2), findViewById(R.id.TXT_NomJugador3), findViewById(R.id.TXT_NomJugador4), findViewById(R.id.TXT_NomJugador5)};
        TextView[] Txt_Puntuaciones = {findViewById(R.id.TXT_PuntuacionJ1), findViewById(R.id.TXT_PuntuacionJ2), findViewById(R.id.TXT_PuntuacionJ3), findViewById(R.id.TXT_PuntuacionJ4), findViewById(R.id.TXT_PuntuacionJ5)};


        this.dbManager = new DbManager(this);
        Cursor c_top5;
        c_top5=dbManager.get5Mejores_Clasificacion();
        c_top5.moveToFirst();
        int i=0;
        while(!c_top5.isAfterLast()) {
            String a=c_top5.getString(c_top5.getColumnIndex(DbContract.DbEntry.COLUMN_NOMJUGADOR));
            Txt_Jugadores[i].setText(c_top5.getString(c_top5.getColumnIndex(DbContract.DbEntry.COLUMN_NOMJUGADOR)));
            Txt_Puntuaciones[i].setText(""+ c_top5.getInt(c_top5.getColumnIndex(DbContract.DbEntry.COLUMN_PUNTUACION)));
            i+=1;
            c_top5.moveToNext();
        }
    }


    public void onClick(View v) {
        Intent intent;
        Bundle b=new Bundle();
        b.putString("nom_jugador",usuario_seleccionado);
        b.putInt("n_preguntas",num_preguntas);
        //ButtonVolver.startAnimation(scaleDown);
        //ButtonVolver.startAnimation(scaleUp);
        intent = new Intent(this, MainActivity.class);
        intent.putExtras(b);
        startActivity(intent);

    }
}