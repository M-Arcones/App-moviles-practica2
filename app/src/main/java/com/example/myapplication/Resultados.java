package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Resultados extends AppCompatActivity implements View.OnClickListener{

    private Button buttonPlayAgain;
    private TextView textScore;
    private Animation scaleUp,scaleDown;

    private DbManager dbManager;
    private int num_preguntas;
    private String usuario_seleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        int puntuacion = (int) getIntent().getSerializableExtra("puntuacion");
        num_preguntas= (int) getIntent().getSerializableExtra("num_preguntas");
        usuario_seleccionado=(String) getIntent().getSerializableExtra("nom_jugador");

        textScore = findViewById(R.id.Text_Puntuacion);
        textScore.setText("Puntuacion Final \n \n" + puntuacion +" pts");

        buttonPlayAgain=findViewById(R.id.Button_VolverAJugar);

        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);

        buttonPlayAgain.setOnClickListener(this);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        this.dbManager = new DbManager(this);
        dbManager.insertPuntuacion(usuario_seleccionado,puntuacion,dateFormat.format(date));

        if(puntuacion>0){
            //Numero de preguntas Maximas puntuaciones, si es mayor que 5 ir a clasificacion, si no ver la 5 posicion,si es mayor ir a Clasificacion

        }
    }

    @Override
    public void onClick(View v) {
        buttonPlayAgain.startAnimation(scaleDown);
        buttonPlayAgain.startAnimation(scaleUp);

        Intent intent;
        intent = new Intent(this, QuestionManager.class);
        intent.putExtra("n_preguntas", num_preguntas);
        startActivity(intent);
    }
}