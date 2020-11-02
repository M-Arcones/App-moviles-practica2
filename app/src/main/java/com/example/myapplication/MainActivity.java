package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button ButtonPlay, ButtonAjustes;
    private Animation scaleUp, scaleDown;
    private int num_preguntas;
    //private ArrayList<Pregunta> preguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=this.getIntent().getExtras();
        if(b==null){
            num_preguntas=5;
        }else{
            num_preguntas=b.getInt("n_preguntas");
        }
        setContentView(R.layout.activity_main);
        ButtonPlay=findViewById(R.id.Btn_jugar);
        ButtonAjustes=findViewById(R.id.Btn_ajustes);
        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        ButtonPlay.setOnClickListener(this);
        ButtonAjustes.setOnClickListener(this);
    }

    public void onClick(View v) {
        Bundle b=new Bundle();
        b.putInt("n_preguntas",num_preguntas);
        Intent intent;
        switch (v.getId()) {
            case (R.id.Btn_ajustes):
                ButtonAjustes.startAnimation(scaleDown);
                ButtonAjustes.startAnimation(scaleUp);
                intent = new Intent(this, Ajustes.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
            case (R.id.Btn_jugar):
                ButtonPlay.startAnimation(scaleDown);
                ButtonPlay.startAnimation(scaleUp);
                intent = new Intent(this, QuestionManager.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
        }
    }
}
