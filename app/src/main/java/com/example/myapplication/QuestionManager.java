package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.Calendar;
import java.util.Date;
import java.time.ZonedDateTime;

public class QuestionManager extends AppCompatActivity implements View.OnClickListener, Marcador.OnFragmentInteractionListener, Question.OnFragmentInteractionListener, BotonQuestion.OnFragmentInteractionListener{
    ArrayList<String[]> Preguntas= new ArrayList<String[]>();
    SeekBar Skb_BarraRespuesta;
    int valorMinimo=0;
    int Estado_validar=0;
    int suma_Acierto=100;
    int suma_Fallo=-50;
    int Puntuacion=0;
    int Aciertos=0;
    int Fallos=0;
    int n_preguntas_totales;
    int n_preguntas_test;
    String TipoPreguntaActual, usuario_seleccionado, tema_seleccionado, Respuesta;
    int n_pregunta=1;
    Animation scaleUp, scaleDown;
    Button btn_validar, btn_PlaySound, btn_PauseSound;
    Date date;
    Random rnd;
    Intent intent;
    MediaPlayer mediaPlayer = new MediaPlayer();
    VideoView videoView;

    private DbManager dbManager;
    Cursor c_preguntas;
    Cursor c_respuestas;
    Cursor c_imagenes;

    ArrayList<Pregunta> todasPreguntas = new ArrayList<>();
    ArrayList<Pregunta> preguntas = new ArrayList<>();

    CountDownTimer CuentaAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_manager);

        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        btn_validar=findViewById(R.id.BtnValidar_SigPregunta);
        btn_PlaySound=findViewById(R.id.Btn_PlaySound);
        btn_PauseSound=findViewById(R.id.Btn_PauseSound);
        videoView=findViewById(R.id.VideoPregunta);
        videoView.setMediaController(new MediaController(this));

        date = new Date();
        rnd = new Random();
        rnd.setSeed(date.getTime());//cambiar a la hora


        intent = new Intent(this, Resultados.class);
        Bundle b=this.getIntent().getExtras();
        n_preguntas_test=b.getInt("n_preguntas");
        usuario_seleccionado=b.getString("nom_jugador");
        tema_seleccionado=b.getString("tema_seleccionado");

        if(tema_seleccionado.equals("Futbol")){
            findViewById(R.id.questionaryView).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_football));
        }else{
            findViewById(R.id.questionaryView).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.background_videogames));
        }

        Cargar_preguntas();
        n_preguntas_totales = todasPreguntas.size();

        ((TextView)findViewById(R.id.TxtPreguntasContestadas)).setText(n_pregunta+"/"+n_preguntas_test);

        /*Crea una lista de las posiciones disponibles para ordenar de manera aletoria las preguntas*/
        List<Integer> PosicionesDisponibles = new ArrayList<Integer>();
        for(int i=0;i<n_preguntas_totales;i++){
            PosicionesDisponibles.add(i);
        }

        /*Añade en la lista de preguntas las preguntas leidas de string.xml cogidas de manera aleatoria por su "name" */
        for(int i=0;i<n_preguntas_test;i++){
            int randomNum = rnd.nextInt((PosicionesDisponibles.size()));
            preguntas.add(todasPreguntas.get(PosicionesDisponibles.get(randomNum)));
            PosicionesDisponibles.remove(randomNum);
        }

        /**/
        mostarPregunta();
        //TipoPreguntaActual=preguntas.get(0).tipo;
        findViewById(R.id.BtnValidar_SigPregunta).setOnClickListener((View.OnClickListener) this);

        /*Definir seekbar*/
        Skb_BarraRespuesta=(SeekBar)  findViewById(R.id.Skb_BarraRespuestas);
        Skb_BarraRespuesta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(Estado_validar==0){
                    int calculo=Skb_BarraRespuesta.getProgress()+valorMinimo;
                    ((TextView) findViewById(R.id.TxtskbValorSeleccionado)).setText(""+calculo);
                }else
                {
                    seekBar.setProgress(Integer.parseInt(((TextView)findViewById(R.id.TxtskbValorSeleccionado)).getText().toString())-Integer.parseInt(((TextView)findViewById(R.id.TxtSeekbarMinValue)).getText().toString()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        RadioButton RbtnRespImagen1 = (RadioButton) findViewById(R.id.RbtnRespImagen1);
        RadioButton RbtnRespImagen2 = (RadioButton) findViewById(R.id.RbtnRespImagen2);
        RadioButton RbtnRespImagen3 = (RadioButton) findViewById(R.id.RbtnRespImagen3);
        RadioButton RbtnRespImagen4 = (RadioButton) findViewById(R.id.RbtnRespImagen4);

        RbtnRespImagen1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(true);
                ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(false);
            }
        });

        RbtnRespImagen2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(true);
                ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(false);
            }
        });

        RbtnRespImagen3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(true);
                ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(false);
            }
        });

        RbtnRespImagen4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(false);
                ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(true);
            }
        });
        /*Cuenta atras*/
        CuentaAtras= new CountDownTimer(10000*n_preguntas_test, 1000) {

            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.Txt_CuentaAtras)).setText(" " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                intent.putExtra("puntuacion", 0);
                intent.putExtra("num_preguntas", n_preguntas_test);
                intent.putExtra("nom_jugador", usuario_seleccionado);
                intent.putExtra("tema_seleccionado", tema_seleccionado);
                startActivity(intent);
            }
        }.start();

        btn_PlaySound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_PlaySound.startAnimation(scaleDown);
                btn_PlaySound.startAnimation(scaleUp);
                mediaPlayer.start();}
        });
        btn_PauseSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_PauseSound.startAnimation(scaleDown);
                btn_PauseSound.startAnimation(scaleUp);
                mediaPlayer.pause();}
        });

    }

    public void onClick(View v) {
        btn_validar.startAnimation(scaleDown);
        btn_validar.startAnimation(scaleUp);
        int valorResuesta=suma_Fallo;

        deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutMultipleRespuesta),false);
        deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutRespuestaButtonImagen),true);
        deshabilitarCambiosRadioGroup((RadioGroup) findViewById(R.id.Rgbtn_button),false);
        deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutSwitch),false);

        switch (preguntas.get(0).tipo){
            case "Sonido":
            case "Button":
            case "Imagen":
            case "Video":
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                RadioButton ArrayRespRadioButton[]= { ((RadioButton)findViewById(R.id.RbtnResp1)),
                        ((RadioButton)findViewById(R.id.RbtnResp2)),
                        ((RadioButton)findViewById(R.id.RbtnResp3)),
                        ((RadioButton)findViewById(R.id.RbtnResp4))};
                for (int i=0;i<4;i++){
                    if(ArrayRespRadioButton[i].isChecked()){
                        if(ArrayRespRadioButton[i].getText().equals(preguntas.get(0).solucion)){
                            valorResuesta=suma_Acierto;
                        }
                        i=4;
                    }
                }
            break;
            case "Seekbar":
                //calcular puntos
                if(Integer.parseInt(((TextView) findViewById(R.id.TxtskbValorSeleccionado)).getText().toString())==Integer.parseInt((preguntas.get(0).solucion))){
                    valorResuesta=suma_Acierto;
                }
                //Corregir
                /*if(valorResuesta==suma_Fallo){
                    ((SeekBar)  findViewById(R.id.Skb_BarraRespuestas)).setProgress(Integer.parseInt((Preguntas.get(0)[Preguntas.get(0).length-1]))-Integer.parseInt((Preguntas.get(0)[2])));
                    int resultadoa=Integer.parseInt((Preguntas.get(0)[Preguntas.get(0).length-1]))-Integer.parseInt((Preguntas.get(0)[2]));
                    ((TextView)findViewById(R.id.TxtskbValorSeleccionado)).setText("" +Preguntas.get(0)[Preguntas.get(0).length-1]);
                }else{

                }*/
            break;
            case "Multiple":
                CheckBox ArrayRespCheckBox[]={((CheckBox)findViewById(R.id.ChkB_Resp1)),
                                                ((CheckBox)findViewById(R.id.ChkB_Resp2)),
                                                ((CheckBox)findViewById(R.id.ChkB_Resp3)),
                                                ((CheckBox)findViewById(R.id.ChkB_Resp4)),
                                                ((CheckBox)findViewById(R.id.ChkB_Resp5)),
                                                ((CheckBox)findViewById(R.id.ChkB_Resp6))};
                int N_respuestas=countChar(preguntas.get(0).solucion,'|');
                int cont_respCorrrectas=0;
                for (int i=0;i<preguntas.get(0).respuestas.size();i++){
                    if(ArrayRespCheckBox[i].isChecked()){
                        if(preguntas.get(0).solucion.contains(ArrayRespCheckBox[i].getText())){
                            cont_respCorrrectas+=1;
                        }else {
                            cont_respCorrrectas=0;
                            i=preguntas.get(0).respuestas.size();
                        }
                    }
                }
                if(cont_respCorrrectas==N_respuestas){
                    valorResuesta=suma_Acierto;
                }
            break;
            case "Switch":
                if (((Switch)findViewById(R.id.Switch)).isChecked()==Boolean.parseBoolean(preguntas.get(0).solucion)){
                    valorResuesta=suma_Acierto;
                }
            break;
            case "ButtonImagen":
                RadioButton ArrayRespRadioButtonImagen[]= { ((RadioButton)findViewById(R.id.RbtnRespImagen1)),
                                                            ((RadioButton)findViewById(R.id.RbtnRespImagen2)),
                                                            ((RadioButton)findViewById(R.id.RbtnRespImagen3)),
                                                            ((RadioButton)findViewById(R.id.RbtnRespImagen4))};
                for (int i=0;i<4;i++){
                    if(ArrayRespRadioButtonImagen[i].isChecked()){
                        if(ArrayRespRadioButtonImagen[i].getText().equals(preguntas.get(0).solucion)){
                            valorResuesta=suma_Acierto;
                        }
                        i=4;
                    }
                }
            break;
        }
        //findViewById(R.id.Layout_AciertoFallo).setVisibility(View.VISIBLE);
        if(valorResuesta==suma_Acierto){
            Aciertos+=1;
            ((TextView) findViewById(R.id.Txt_RespuestaAcierto)).setText("ACIERTO");
            //((TextView) findViewById(R.id.Txt_RespuestaAcierto)).setBackgroundResource(R.drawable.panel_acierto);
        }else{
            Fallos+=1;
            ((TextView) findViewById(R.id.Txt_RespuestaAcierto)).setText("FALLO");
            //((TextView) findViewById(R.id.Txt_RespuestaAcierto)).setBackgroundResource(R.drawable.panel_fallo);
        }

        //((TextView) findViewById(R.id.Txt_Explicacion)).setText(preguntas.get(0).explicacion);
        ((TextView) findViewById(R.id.TXT_AcieFall)).setText("A:"+ Aciertos +"/F:"+ Fallos);

        Puntuacion=Math.max(Puntuacion+valorResuesta,0);
        ((TextView) findViewById(R.id.TxtPuntuacion)).setText("Puntuación: "+Puntuacion);

        if(preguntas.size()>1){
            preguntas.remove(0);
            mostarPregunta();
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutMultipleRespuesta),true);
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutRespuestaButtonImagen),true);
            deshabilitarCambiosLayout((LinearLayout) findViewById(R.id.LayoutSwitch),true);
            deshabilitarCambiosRadioGroup((RadioGroup) findViewById(R.id.Rgbtn_button),true);
            TipoPreguntaActual=preguntas.get(0).tipo;
            n_pregunta++;
            ((TextView)findViewById(R.id.TxtPreguntasContestadas)).setText(n_pregunta+"/"+n_preguntas_test);
            if(preguntas.size()==1){
                ((TextView) findViewById(R.id.BtnValidar_SigPregunta)).setText("Finalizar");
            }
        }else{
            preguntas.remove(0);
            intent.putExtra("puntuacion", Puntuacion+  Integer.parseInt(((TextView) findViewById(R.id.Txt_CuentaAtras)).getText().toString().replaceAll("\\s+","")));
            intent.putExtra("num_preguntas", n_preguntas_test);
            intent.putExtra("nom_jugador", usuario_seleccionado);
            intent.putExtra("tema_seleccionado", tema_seleccionado);
            CuentaAtras.cancel();
            startActivity(intent);
        }
    }

    @SuppressLint("WrongViewCast")
    public void mostarPregunta(){

        int n_respuestas = preguntas.get(0).respuestas.size();
        int imagenID, SonidoID, VideoId;

        ((TextView) findViewById(R.id.TxtPregunta)).setText(preguntas.get(0).pregunta);

        List<Integer> PosicionesDisponiblesRespuesta = new ArrayList<Integer>();

        findViewById(R.id.LayoutRespuestaButton).setVisibility(View.GONE);
        findViewById(R.id.LayoutRespuestaSkb).setVisibility(View.GONE);
        findViewById(R.id.LayoutMultipleRespuesta).setVisibility(View.GONE);
        findViewById(R.id.LayoutSwitch).setVisibility(View.GONE);
        findViewById(R.id.Layout_ImagenPregunta).setVisibility(View.GONE);
        findViewById(R.id.LayoutRespuestaButtonImagen).setVisibility(View.GONE);
        findViewById(R.id.Layout_AciertoFallo).setVisibility(View.GONE);
        findViewById(R.id.Layout_Sonido).setVisibility(View.GONE);
        findViewById(R.id.LayoutVideo).setVisibility(View.GONE);
        switch (preguntas.get(0).tipo){
            case "Button":
                ((RadioGroup) findViewById(R.id.Rgbtn_button)).clearCheck();
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
                break;
            case "Seekbar":
                findViewById(R.id.LayoutRespuestaSkb).setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.TxtSeekbarMinValue)).setText(""+preguntas.get(0).min);
                ((TextView) findViewById(R.id.TxtSeekbarMaxValue)).setText(""+preguntas.get(0).max);
                ((SeekBar)findViewById(R.id.Skb_BarraRespuestas)).setProgress((preguntas.get(0).max - preguntas.get(0).min)/2);
                ((SeekBar)findViewById(R.id.Skb_BarraRespuestas)).setMax(preguntas.get(0).max-preguntas.get(0).min);
                ((TextView) findViewById(R.id.TxtskbValorSeleccionado)).setText(""+((preguntas.get(0).max-preguntas.get(0).min)/2+preguntas.get(0).min));
                valorMinimo=preguntas.get(0).min;
            break;
            case "Multiple":
                findViewById(R.id.LayoutMultipleRespuesta).setVisibility(View.VISIBLE);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<n_respuestas;i++) {
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i) {
                        case 0:
                            ((CheckBox) findViewById(R.id.ChkB_Resp1)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp1)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            ((CheckBox) findViewById(R.id.ChkB_Resp2)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp2)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            ((CheckBox) findViewById(R.id.ChkB_Resp3)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp3)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            ((CheckBox) findViewById(R.id.ChkB_Resp4)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp4)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 4:
                            ((CheckBox) findViewById(R.id.ChkB_Resp5)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp5)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 5:
                            ((CheckBox) findViewById(R.id.ChkB_Resp6)).setChecked(false);
                            ((TextView) findViewById(R.id.ChkB_Resp6)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
            break;
            case "Imagen":
                ((RadioGroup) findViewById(R.id.Rgbtn_button)).clearCheck();
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                findViewById(R.id.Layout_ImagenPregunta).setVisibility(View.VISIBLE);

                imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(0), "raw", this.getPackageName());
                ((ImageView)findViewById(R.id.Img_pregunta)).setImageResource(imagenID);
                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
            break;
            case "Sonido":
                ((RadioGroup) findViewById(R.id.Rgbtn_button)).clearCheck();
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                findViewById(R.id.Layout_Sonido).setVisibility(View.VISIBLE);

                try {
                    SonidoID = this.getResources().getIdentifier(preguntas.get(0).sonido, "raw", this.getPackageName());
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://" +getPackageName()+ "/" +SonidoID));
                    mediaPlayer.setVolume(20,20);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
            break;
            case "Video":
                ((RadioGroup) findViewById(R.id.Rgbtn_button)).clearCheck();
                findViewById(R.id.LayoutRespuestaButton).setVisibility(View.VISIBLE);
                findViewById(R.id.LayoutVideo).setVisibility(View.VISIBLE);

                VideoId = this.getResources().getIdentifier(preguntas.get(0).video, "raw", this.getPackageName());
                videoView.setVideoURI(Uri.parse("android.resource://" +getPackageName()+ "/" + VideoId));
                videoView.requestFocus();

                /*try {
                    SonidoID = this.getResources().getIdentifier(preguntas.get(0).sonido, "raw", this.getPackageName());
                    mediaPlayer.setDataSource(this, Uri.parse("android.resource://" +getPackageName()+ "/" +SonidoID));
                    mediaPlayer.setVolume(20,20);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                for(int i=0;i<n_respuestas;i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<n_respuestas;i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            ((TextView) findViewById(R.id.RbtnResp1)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            ((TextView) findViewById(R.id.RbtnResp2)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            ((TextView) findViewById(R.id.RbtnResp3)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            ((TextView) findViewById(R.id.RbtnResp4)).setText(preguntas.get(0).respuestas.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
                break;
            case "Switch":

                findViewById(R.id.LayoutSwitch).setVisibility(View.VISIBLE);
                ((Switch)findViewById(R.id.Switch)).setChecked(true);
            break;
            case "ButtonImagen":

                findViewById(R.id.LayoutRespuestaButtonImagen).setVisibility(View.VISIBLE);
                for(int i=0;i<preguntas.get(0).imagenes.size();i++){
                    PosicionesDisponiblesRespuesta.add(i);
                }
                for(int i=0;i<preguntas.get(0).imagenes.size();i++){
                    int randomNum = rnd.nextInt((PosicionesDisponiblesRespuesta.size()));
                    switch (i){
                        case 0:
                            imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)), "raw", this.getPackageName());
                            ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setBackgroundResource(imagenID);
                            ((RadioButton) findViewById(R.id.RbtnRespImagen1)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnRespImagen1)).setText(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 1:
                            imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)), "raw", this.getPackageName());
                            ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setBackgroundResource(imagenID);
                            ((RadioButton) findViewById(R.id.RbtnRespImagen2)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnRespImagen2)).setText(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 2:
                            imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)), "raw", this.getPackageName());
                            ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setBackgroundResource(imagenID);
                            ((RadioButton) findViewById(R.id.RbtnRespImagen3)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnRespImagen3)).setText(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                        case 3:
                            imagenID = this.getResources().getIdentifier(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)), "raw", this.getPackageName());
                            ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setBackgroundResource(imagenID);
                            ((RadioButton) findViewById(R.id.RbtnRespImagen4)).setChecked(false);
                            ((TextView) findViewById(R.id.RbtnRespImagen4)).setText(preguntas.get(0).imagenes.get(PosicionesDisponiblesRespuesta.get(randomNum)));
                            break;
                    }
                    PosicionesDisponiblesRespuesta.remove(randomNum);
                }
                break;
        }
    }

    public int countChar(String str, char c)
    {
        int count = 0;
        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }
        return count+1;
    }

    public void deshabilitarCambiosLayout(LinearLayout layout,boolean habilitar){
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            child.setClickable(habilitar);
        }
    }
    public void deshabilitarCambiosRadioGroup(RadioGroup RadGroup,boolean habilitar){
        for (int i = 0; i < RadGroup.getChildCount(); i++) {
            View child = RadGroup.getChildAt(i);
            child.setClickable(habilitar);
        }
    }


    public void Cargar_preguntas(){
        this.dbManager = new DbManager(this);
        c_preguntas=dbManager.getPreguntas();

        c_preguntas.moveToFirst();
        Pregunta preguntaActual = null;
        while(!c_preguntas.isAfterLast()) {
            switch (c_preguntas.getString(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_TIPO))) {
                case "Button":
                    preguntaActual = new Pregunta("Button");
                    break;
                case "Seekbar":
                    preguntaActual = new Pregunta("Seekbar");
                    break;
                case "Imagen":
                    preguntaActual = new Pregunta("Imagen");
                    break;
                case "Multiple":
                    preguntaActual = new Pregunta("Multiple");
                    break;
                case "Switch":
                    preguntaActual = new Pregunta("Switch");
                    break;
                case "ButtonImagen":
                    preguntaActual = new Pregunta("ButtonImagen");
                    break;
                case "Sonido":
                    preguntaActual = new Pregunta("Sonido");
                    break;
                case "Video":
                    preguntaActual = new Pregunta("Video");
                    break;
            }
            preguntaActual.pregunta = c_preguntas.getString(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_ASK));
            preguntaActual.tema = c_preguntas.getString(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_TEMA));
            preguntaActual.sonido = c_preguntas.getString(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_SONIDO));
            preguntaActual.video = c_preguntas.getString(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_VIDEO));
            preguntaActual.explicacion = c_preguntas.getString(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_EXPLICACION));
            preguntaActual.solucion = c_preguntas.getString(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_SOLUCION));
            preguntaActual.min = c_preguntas.getInt(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_MIN));
            preguntaActual.max = c_preguntas.getInt(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_MAX));
            if(c_preguntas.getInt(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_ID_RESPUESTA))!=0){
                c_respuestas = dbManager.getRespuestas(c_preguntas.getInt(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_ID_RESPUESTA)));
                c_respuestas.moveToFirst();
                while(!c_respuestas.isAfterLast()) {
                    preguntaActual.respuestas.add(c_respuestas.getString(c_respuestas.getColumnIndex(DbContract.DbEntry.COLUMN_RESPUESTA)));
                    c_respuestas.moveToNext();
                }
            }
            if(c_preguntas.getInt(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_ID_IMAGEN))!=0){
                c_imagenes = dbManager.getImagenes(c_preguntas.getInt(c_preguntas.getColumnIndex(DbContract.DbEntry.COLUMN_ID_IMAGEN)));
                c_imagenes.moveToFirst();
                while(!c_imagenes.isAfterLast()) {
                    preguntaActual.imagenes.add(c_imagenes.getString(c_imagenes.getColumnIndex(DbContract.DbEntry.COLUMN_IMAGEN)));
                    c_imagenes.moveToNext();
                }
            }
            c_preguntas.moveToNext();
            if(preguntaActual.tema.equals(tema_seleccionado)) {
                todasPreguntas.add(preguntaActual);
            }
            preguntaActual=null;
        }
    }

}
