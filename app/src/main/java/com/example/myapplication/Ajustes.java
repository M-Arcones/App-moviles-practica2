package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;

public class Ajustes extends AppCompatActivity implements View.OnClickListener{
    private Button ButtonVolver, ButtonGesUsuario;
    private Animation scaleUp, scaleDown;
    private int num_preguntas;
    private String usuario_seleccionado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Bundle b=this.getIntent().getExtras();
        num_preguntas=b.getInt("n_preguntas");
        usuario_seleccionado=b.getString("nom_jugador");
        switch (num_preguntas){
            case 5:
                ((RadioButton) findViewById(R.id.Rbtn_5preg)).setChecked(true);
            break;
            case 10:
                ((RadioButton) findViewById(R.id.Rbtn_10preg)).setChecked(true);
            break;
            case 15:
                ((RadioButton) findViewById(R.id.Rbtn_15preg)).setChecked(true);
             break;
        }
        ButtonVolver=findViewById(R.id.Btn_volver);
        ButtonGesUsuario=findViewById(R.id.Btn_GestionUsuario);
        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        ButtonVolver.setOnClickListener(this);
        ButtonGesUsuario.setOnClickListener(this);
    }


    public void onClick(View v) {
        Intent intent;
        Bundle b=new Bundle();
        b.putString("nom_jugador",usuario_seleccionado);
        if(((RadioButton) findViewById(R.id.Rbtn_5preg)).isChecked()){
            b.putInt("n_preguntas",5);
        }else{
            if (((RadioButton) findViewById(R.id.Rbtn_10preg)).isChecked()){
                b.putInt("n_preguntas",10);
            }else{
                b.putInt("n_preguntas",15);
            }
        }
        switch (v.getId()) {
            case (R.id.Btn_volver):
                ButtonVolver.startAnimation(scaleDown);
                ButtonVolver.startAnimation(scaleUp);
                intent = new Intent(this, MainActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
            case (R.id.Btn_GestionUsuario):
                ButtonGesUsuario.startAnimation(scaleDown);
                ButtonGesUsuario.startAnimation(scaleUp);
                intent = new Intent(this, GestionUsuario.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
        }
    }
}