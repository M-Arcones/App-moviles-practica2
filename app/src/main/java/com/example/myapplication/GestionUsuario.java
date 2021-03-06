package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class GestionUsuario extends AppCompatActivity  implements View.OnClickListener{

    private int num_preguntas;
    private Animation scaleUp, scaleDown;
    private String usuario_seleccionado, tema_seleccionado;
    private Button ButtonVolver, ButtonNuevoUsuario, ButtonEliminarUsuario, ButtonModificarUsuario;
    private Button ButtonEliminar, ButtonNoEliminar;
    private ImageView imageView;
    private DbManager dbManager;
    private Cursor c_usuarios;

    private ArrayList<String> arrayList = new ArrayList<>();
    //private ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
    private int Index=0;
    private Intent intent;
    private Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuario);
        Bundle b=this.getIntent().getExtras();
        num_preguntas=b.getInt("n_preguntas");
        usuario_seleccionado=b.getString("nom_jugador");
        tema_seleccionado=b.getString("tema_seleccionado");
        ButtonVolver=findViewById(R.id.Btn_Volver);
        ButtonNuevoUsuario=findViewById(R.id.Btn_AltaJugador);
        ButtonEliminarUsuario=findViewById(R.id.Btn_Eliminar_Usuario);
        ButtonModificarUsuario=findViewById(R.id.btn_ModificarUsuario);
        ButtonEliminar=findViewById(R.id.Btn_Eliminar);
        ButtonNoEliminar=findViewById(R.id.btn_No_Eliminar);
        ButtonVolver.setOnClickListener(this);
        ButtonNuevoUsuario.setOnClickListener(this);
        ButtonEliminarUsuario.setOnClickListener(this);
        ButtonModificarUsuario.setOnClickListener(this);
        ButtonEliminar.setOnClickListener(this);
        ButtonNoEliminar.setOnClickListener(this);
        imageView=findViewById(R.id.imageViewGestion);
        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);

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

        /*int n_partidas=dbManager.getNPartidas(usuario_seleccionado);
        ((TextView)findViewById(R.id.TxtNpartidas)).setText("Nº de partidas: " + n_partidas);
        if(n_partidas>0){
            Cursor c_MaxPt=dbManager.getMaxPuntuacion(usuario_seleccionado);
            c_MaxPt.moveToFirst();
            ((TextView)findViewById(R.id.TxtMaxPuntuacion)).setText("Maxima Puntuación: "+ c_MaxPt.getInt(c_MaxPt.getColumnIndex("Maxima")));
            c_MaxPt.close();
            Cursor c_MaxFech=dbManager.getUlltimaPartida(usuario_seleccionado);
            c_MaxFech.moveToFirst();
            ((TextView)findViewById(R.id.Txt_UltPartida)).setText("Última Partida: "+ c_MaxFech.getString(c_MaxPt.getColumnIndex("Maxima")));
            c_MaxFech.close();

        }else{
            ((TextView)findViewById(R.id.TxtMaxPuntuacion)).setText("Maxima Puntuación: 0");
        }*/

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spn_Jugadores.setAdapter(arrayAdapter);
        Spn_Jugadores.setSelection(Index);

        if (usuario_seleccionado.equals("Anonimo")){
            findViewById(R.id.LayoutDatosJugador).setVisibility(View.GONE);
        }else{
            findViewById(R.id.LayoutDatosJugador).setVisibility(View.VISIBLE);
        }

        Spn_Jugadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (arrayList.get(position).equals("Anonimo")){
                    findViewById(R.id.LayoutDatosJugador).setVisibility(View.GONE);
                }else{
                    findViewById(R.id.LayoutDatosJugador).setVisibility(View.VISIBLE);
                    Cursor c_Foto = dbManager.getFotoJugador(arrayList.get(position));
                    c_Foto.moveToFirst();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(c_Foto.getBlob(c_Foto.getColumnIndex(
                            DbContract.DbEntry.COLUMN_FOTO)),0,c_Foto.getBlob(c_Foto.getColumnIndex(
                                    DbContract.DbEntry.COLUMN_FOTO)).length);
                    imageView.setImageBitmap(bitmap);
                    int n_partidas=dbManager.getNPartidas(arrayList.get(position));
                    ((TextView)findViewById(R.id.TxtNpartidas)).setText("Nº de partidas: " + n_partidas);
                    if(n_partidas>0){
                        Cursor c_MaxPt=dbManager.getMaxPuntuacion(arrayList.get(position));
                        c_MaxPt.moveToFirst();
                        ((TextView)findViewById(R.id.TxtMaxPuntuacion)).setText("Maxima Puntuación: "+ c_MaxPt.getInt(c_MaxPt.getColumnIndex("Maxima")));
                        c_MaxPt.close();
                        Cursor c_MaxFech=dbManager.getUlltimaPartida(arrayList.get(position));
                        c_MaxFech.moveToFirst();
                        ((TextView)findViewById(R.id.Txt_UltPartida)).setText("Última Partida: "+ c_MaxFech.getString(c_MaxPt.getColumnIndex("Maxima")));
                        c_MaxFech.close();
                    }else{
                        ((TextView)findViewById(R.id.TxtMaxPuntuacion)).setText("Maxima Puntuación: 0");
                        ((TextView)findViewById(R.id.Txt_UltPartida)).setText("Última Partida: -");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    public void onClick(View v) {
        Spinner Spn_Jugadores = findViewById(R.id.Spn_Jugadores);
        usuario_seleccionado = Spn_Jugadores.getSelectedItem().toString();
        switch (v.getId()) {
            case (R.id.Btn_Volver):
                ButtonVolver.startAnimation(scaleDown);
                ButtonVolver.startAnimation(scaleUp);
                b.putString("nom_jugador", usuario_seleccionado);
                b.putString("tema_seleccionado", tema_seleccionado);
                b.putInt("n_preguntas", num_preguntas);
                intent = new Intent(this, Ajustes.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
            case (R.id.Btn_AltaJugador):
                ButtonNuevoUsuario.startAnimation(scaleDown);
                ButtonNuevoUsuario.startAnimation(scaleUp);
                //usuario_seleccionado = Spn_Jugadores.getSelectedItem().toString();
                b.putString("nom_jugador", usuario_seleccionado);
                b.putString("tema_seleccionado", tema_seleccionado);
                b.putString("from", "Alta");
                b.putInt("n_preguntas", num_preguntas);
                intent = new Intent(this, Alta_jugador.class);
                intent.putExtras(b);
                startActivity(intent);
            break;
            case (R.id.Btn_Eliminar_Usuario):
                ButtonEliminarUsuario.startAnimation(scaleDown);
                ButtonEliminarUsuario.startAnimation(scaleUp);
                findViewById(R.id.layout_eliminar).setVisibility(View.VISIBLE);
                ButtonModificarUsuario.setClickable(false);
                ButtonEliminarUsuario.setClickable(false);
                ButtonVolver.setClickable(false);
                ButtonNuevoUsuario.setClickable(false);
                Spn_Jugadores.setEnabled(false);

            break;
            case (R.id.btn_ModificarUsuario):
                ButtonModificarUsuario.startAnimation(scaleDown);
                ButtonModificarUsuario.startAnimation(scaleUp);
                b.putString("nom_jugador", usuario_seleccionado);
                b.putString("tema_seleccionado", tema_seleccionado);
                b.putString("from", "Modificar");
                b.putInt("n_preguntas", num_preguntas);
                intent = new Intent(this, Alta_jugador.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case (R.id.Btn_Eliminar):
                ButtonEliminar.startAnimation(scaleDown);
                ButtonEliminar.startAnimation(scaleUp);
                dbManager.delete_Puntuacion(Spn_Jugadores.getSelectedItem().toString());
                dbManager.delete_Jugadores(Spn_Jugadores.getSelectedItem().toString());
                arrayList.remove(Spn_Jugadores.getSelectedItem().toString());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spn_Jugadores.setAdapter(arrayAdapter);
                Spn_Jugadores.setSelection(arrayList.indexOf("Anonimo"));
                findViewById(R.id.LayoutDatosJugador).setVisibility(View.GONE);
                findViewById(R.id.layout_eliminar).setVisibility(View.GONE);
                ButtonModificarUsuario.setClickable(true);
                ButtonEliminarUsuario.setClickable(true);
                ButtonVolver.setClickable(true);
                ButtonNuevoUsuario.setClickable(true);
                Spn_Jugadores.setEnabled(true);
                break;
            case(R.id.btn_No_Eliminar):
                ButtonNoEliminar.startAnimation(scaleDown);
                ButtonNoEliminar.startAnimation(scaleUp);
                ButtonModificarUsuario.setClickable(true);
                ButtonModificarUsuario.setClickable(true);
                ButtonEliminarUsuario.setClickable(true);
                ButtonVolver.setClickable(true);
                ButtonNuevoUsuario.setClickable(true);
                Spn_Jugadores.setEnabled(true);
                findViewById(R.id.layout_eliminar).setVisibility(View.GONE);
                break;
        }
    }
}

