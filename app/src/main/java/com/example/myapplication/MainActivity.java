package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button ButtonPlay, ButtonAjustes, ButtonContinuar, ButtonUsuario, ButtonClasificacion;
    private Animation scaleUp, scaleDown;
    private int num_preguntas;
    private String usuario_seleccionado, tema_seleccionado;
    //private ArrayList<Pregunta> preguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=this.getIntent().getExtras();
        if(b==null){
            num_preguntas=5;
            usuario_seleccionado="Anonimo";
            tema_seleccionado="Futbol";
        }else{
            num_preguntas=b.getInt("n_preguntas");
            usuario_seleccionado=b.getString("nom_jugador");
            tema_seleccionado=b.getString("tema_seleccionado");
        }
        setContentView(R.layout.activity_main);
        ButtonPlay=findViewById(R.id.Btn_jugar);
        ButtonAjustes=findViewById(R.id.Btn_ajustes);
        ButtonContinuar=findViewById(R.id.Btn_Continuar);
        ButtonUsuario=findViewById(R.id.Btn_Seleccion);
        ButtonClasificacion=findViewById(R.id.Btn_Clasificacion);
        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        ButtonPlay.setOnClickListener(this);
        ButtonAjustes.setOnClickListener(this);
        ButtonContinuar.setOnClickListener(this);
        ButtonUsuario.setOnClickListener(this);
        ButtonClasificacion.setOnClickListener(this);
    }

    public void onClick(View v) {
        Bundle b=new Bundle();
        b.putInt("n_preguntas",num_preguntas);
        b.putString("nom_jugador",usuario_seleccionado);
        b.putString("tema_seleccionado",tema_seleccionado);
        Intent intent;
        switch (v.getId()) {
            case (R.id.Btn_Clasificacion):
                ButtonClasificacion.startAnimation(scaleDown);
                ButtonClasificacion.startAnimation(scaleUp);
                intent = new Intent(this, Clasificacion.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
            case (R.id.Btn_ajustes):
                ButtonAjustes.startAnimation(scaleDown);
                ButtonAjustes.startAnimation(scaleUp);
                intent = new Intent(this, Ajustes.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
            case (R.id.Btn_jugar):
                if(usuario_seleccionado.equals("Anonimo")){
                    findViewById(R.id.Layout_Advertencia).setVisibility(View.VISIBLE);
                    ButtonPlay.setVisibility(View.GONE);
                    ButtonAjustes.setVisibility(View.GONE);
                    ButtonClasificacion.setVisibility(View.GONE);
                }else{
                    ButtonPlay.startAnimation(scaleDown);
                    ButtonPlay.startAnimation(scaleUp);
                    intent = new Intent(this, QuestionManager.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            break;
            case (R.id.Btn_Continuar):
                ButtonContinuar.startAnimation(scaleDown);
                ButtonContinuar.startAnimation(scaleUp);
                intent = new Intent(this, QuestionManager.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
            case (R.id.Btn_Seleccion):
                ButtonUsuario.startAnimation(scaleDown);
                ButtonUsuario.startAnimation(scaleUp);
                intent = new Intent(this, Alta_jugador.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
        }
    }
}
