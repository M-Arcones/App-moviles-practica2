package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

    private Button buttonPlayAgain, ButtonVolver;
    private TextView textScore;
    private Animation scaleUp,scaleDown;

    private DbManager dbManager;
    private int num_preguntas;
    private String usuario_seleccionado, tema_seleccionado;
    //Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        int puntuacion = (int) getIntent().getSerializableExtra("puntuacion");
        num_preguntas= (int) getIntent().getSerializableExtra("num_preguntas");
        usuario_seleccionado=(String) getIntent().getSerializableExtra("nom_jugador");
        tema_seleccionado=(String) getIntent().getSerializableExtra("tema_seleccionado");

        textScore = findViewById(R.id.Text_Puntuacion);
        textScore.setText("Puntuacion Final \n \n" + puntuacion +" pts");

        buttonPlayAgain=findViewById(R.id.Button_VolverAJugar);
        ButtonVolver=findViewById(R.id.Btn_Volver_);

        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);

        buttonPlayAgain.setOnClickListener(this);
        ButtonVolver.setOnClickListener(this);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        this.dbManager = new DbManager(this);

        Cursor c_top5;
        c_top5=dbManager.get5Mejores_Clasificacion();
        c_top5.moveToLast();
        Intent intent;
        intent = new Intent(this, Clasificacion.class);


        if(puntuacion>0){
            int a=dbManager.getN_Clasificacion();
            if(dbManager.getN_Clasificacion()<=5){
                c_top5.close();
                dbManager.insertPuntuacion(usuario_seleccionado,puntuacion,dateFormat.format(date));
                intent.putExtra("n_preguntas", num_preguntas);
                intent.putExtra("nom_jugador", usuario_seleccionado);
                intent.putExtra("tema_seleccionado", tema_seleccionado);
                startActivity(intent);
            }else{
                if(c_top5.getInt(c_top5.getColumnIndex(DbContract.DbEntry.COLUMN_PUNTUACION))<puntuacion){
                    dbManager.insertPuntuacion(usuario_seleccionado,puntuacion,dateFormat.format(date));
                    c_top5.close();
                    intent.putExtra("n_preguntas", num_preguntas);
                    intent.putExtra("nom_jugador", usuario_seleccionado);
                    intent.putExtra("tema_seleccionado", tema_seleccionado);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case (R.id.Button_VolverAJugar):
                buttonPlayAgain.startAnimation(scaleDown);
                buttonPlayAgain.startAnimation(scaleUp);
                intent = new Intent(this, QuestionManager.class);
                intent.putExtra("n_preguntas", num_preguntas);
                intent.putExtra("nom_jugador", usuario_seleccionado);
                intent.putExtra("tema_seleccionado", tema_seleccionado);
                startActivity(intent);
            break;
            case(R.id.Btn_Volver_):
                ButtonVolver.startAnimation(scaleDown);
                ButtonVolver.startAnimation(scaleUp);
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("n_preguntas", num_preguntas);
                intent.putExtra("nom_jugador", usuario_seleccionado);
                intent.putExtra("tema_seleccionado", tema_seleccionado);
                startActivity(intent);
            break;
        }
    }
}