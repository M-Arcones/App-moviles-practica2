package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonPlay;
    private Animation scaleUp, scaleDown;
    //private ArrayList<Pregunta> preguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPlay=findViewById(R.id.Btn_jugar);










        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        buttonPlay.setOnClickListener(this);
    }

    public void onClick(View v) {
        buttonPlay.startAnimation(scaleDown);
        buttonPlay.startAnimation(scaleUp);

        Intent intent;
        intent = new Intent(this, QuestionManager.class);
        startActivity(intent);
    }
}
