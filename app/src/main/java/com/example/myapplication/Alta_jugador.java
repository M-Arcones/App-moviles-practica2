package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Alta_jugador extends AppCompatActivity implements View.OnClickListener{

    private int PERMISSION_REQUEST_CODE_CAMERA = 100;
    private Button ButtonVolver, ButtonGuardar, ButtonImagen;
    private Animation scaleUp, scaleDown;
    private String usuario_seleccionado, tema_seleccionado, from;
    private int num_preguntas;
    private byte[] imageBlob;
    private ImageView imageView;
    private EditText editText;

    private DbManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altamodificacion_jugador);
        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale_down);
        Bundle b=this.getIntent().getExtras();
        num_preguntas=b.getInt("n_preguntas");
        usuario_seleccionado=b.getString("nom_jugador");
        tema_seleccionado=b.getString("tema_seleccionado");
        from=b.getString("from");
        ButtonVolver=findViewById(R.id.BtnVolver);
        ButtonGuardar=findViewById(R.id.BtnGuardar);
        ButtonImagen=findViewById(R.id.Btn_subirImagen);
        ButtonVolver.setOnClickListener(this);
        ButtonGuardar.setOnClickListener(this);
        ButtonImagen.setOnClickListener(this);
        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.InputTextNombre);

        //Compress default image to blob
        if(from.equals("Alta")){
            editText.setFocusable(true);
            InputStream recursoRaw = getResources().openRawResource(R.raw.default_image);
            Bitmap bitmap = (Bitmap) BitmapFactory.decodeStream(recursoRaw);
            imageView.setImageBitmap(bitmap);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            imageBlob = bos.toByteArray();
        }else{
            editText.setFocusable(false);
            this.dbManager = new DbManager(this);
            Cursor c_Foto = dbManager.getFotoJugador(editText.getText().toString());
            c_Foto.moveToFirst();
            Bitmap bitmap = BitmapFactory.decodeByteArray(c_Foto.getBlob(c_Foto.getColumnIndex(
                    DbContract.DbEntry.COLUMN_FOTO)),0,c_Foto.getBlob(c_Foto.getColumnIndex(
                    DbContract.DbEntry.COLUMN_FOTO)).length);
            imageView.setImageBitmap(bitmap);
        }

        requestPermission();
    }

    public void onClick(View v) {
        Intent intent;
        Bundle b = new Bundle();
        b.putInt("n_preguntas", num_preguntas);
        b.putString("tema_seleccionado", tema_seleccionado);
        switch (v.getId()) {
            case (R.id.BtnVolver):
                ButtonVolver.startAnimation(scaleDown);
                ButtonVolver.startAnimation(scaleUp);
                b.putString("nom_jugador", usuario_seleccionado);

                intent = new Intent(this, GestionUsuario.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case (R.id.BtnGuardar):
                ButtonGuardar.startAnimation(scaleDown);
                ButtonGuardar.startAnimation(scaleUp);
                this.dbManager = new DbManager(this);
                if(from.equals("Modificar")){
                    dbManager.delete_Jugadores(editText.getText().toString());
                }
                dbManager.insertJugador(editText.getText().toString(), imageBlob);
                CharSequence text = "Usuario Guardado";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
                b.putString("nom_jugador", editText.getText().toString());

                intent = new Intent(this, GestionUsuario.class);
                intent.putExtras(b);
                startActivity(intent);
                break;
            case (R.id.Btn_subirImagen):
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PERMISSION_REQUEST_CODE_CAMERA);
                break;
        }

    }

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(Alta_jugador.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Alta_jugador.this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PERMISSION_REQUEST_CODE_CAMERA){
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bm);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
            imageBlob = bos.toByteArray();
            requestPermission();
        }
    }
}
