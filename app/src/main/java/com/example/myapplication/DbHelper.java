package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.myapplication.DbContract.SQL_CREATE_PUNTUACIONES;
import static com.example.myapplication.DbContract.SQL_CREATE_IMAGEN;
import static com.example.myapplication.DbContract.SQL_CREATE_JUGADORES;
import static com.example.myapplication.DbContract.SQL_CREATE_PREGUNTA;
import static com.example.myapplication.DbContract.SQL_CREATE_PUNTUACIONES;
import static com.example.myapplication.DbContract.SQL_CREATE_RESPUESTA;
import static com.example.myapplication.DbContract.SQL_DELETE_ENTRIES;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SqlPreguntas.db";

    protected DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)  {
        db.execSQL(SQL_CREATE_PREGUNTA);
        db.execSQL(SQL_CREATE_RESPUESTA);
        db.execSQL(SQL_CREATE_IMAGEN);
        db.execSQL(SQL_CREATE_PUNTUACIONES);
        db.execSQL(SQL_CREATE_JUGADORES);
        //db.execSQL("INSERT INTO Preguntas VALUES ('ID', 'COLUMN_TIPO', 'COLUMN_ASK', 'COLUMN_ID_RESPUESTA','COLUMN_EXPLICACION','COLUMN_SOLUCION','COLUMN_MIN','COLUMN_MAX','COLUMN_ID_IMAGEN')");
        db.execSQL("INSERT INTO Preguntas VALUES (1, 'Button', '¿Quién fue el jugador que marcó en la final del mundial de 2010?', 1,'Iniesta marcó en la prórroga el gol decisivo que dio el título a España en una disputadísima final contra Holanda (0-1)','Andrés Iniesta',NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Preguntas VALUES (2, 'Seekbar', '¿Cuál es el máximo número de goles que se han marcado en un partido?', NULL,'El 31 de Octubre de 2002  ek AS Adema ganó al SO I Emyrne 149-0','149',10,200,NULL)");
        db.execSQL("INSERT INTO Preguntas VALUES (3, 'Imagen', '¿Donde se jugó la final de la Champion de la foto?', 2,'Sergio Ramos marcó este gol en la final de Lisboa en el minuto 93 provocando la prórroga, lo que finalizaría con la Décima Champion para el conjunto blanco','Lisboa',NULL,NULL,1)");
        db.execSQL("INSERT INTO Preguntas VALUES (4, 'Multiple', '¿Cuáles de estos equipos han ganado alguna Liga?', 3,'El Real Sociedad ganó dos trofeos ligueros en 1981 y 1982, el Sevilla ganó este trofeo únicamente en 1946 y el Betis en 1935','Real Sociedad|Sevilla|Betis',NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Preguntas VALUES (5, 'Switch', '¿Lionel Messi es el futbolista que más veces ha ganado el Trofeo Pichichi de la Liga?', null,'Verdadero, Lionel Messi ha ganado este trofeo 7 veces, ademas tiene el record con mas goles en una temporada con 50 goles','true',NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Preguntas VALUES (6, 'ButtonImagen', '¿Cuál de todas estas imagenes corresponde con un penalti?', 4,'Un penalti consiste en lanzar un tiro desde el punto ubicado a 11 metros de la portería.','imagen_tiro_penalti',NULL,NULL,2)");
        db.execSQL("INSERT INTO Preguntas VALUES (7, 'Button', '¿Cuál es el equipo español que ha conseguido mayor diferencia de goles en una eliminatoria de competición europea?', 4,'El Rayo Vallecanó ganó 10-0 en la ida y 0-6 en la vuelta contra el Constelació Esportiva de Andorra en la UEFA de la temporada 2000/2001','Rayo Vallecano',NULL,NULL,NULL)");

        //db.execSQL("INSERT INTO Respuestas VALUES ('ID_RESPUESTA', 'RESPUESTA')");
        db.execSQL("INSERT INTO Respuestas VALUES (1, 'Andrés Iniesta')");
        db.execSQL("INSERT INTO Respuestas VALUES (1, 'David Villa')");
        db.execSQL("INSERT INTO Respuestas VALUES (1, 'Sergio Ramos')");
        db.execSQL("INSERT INTO Respuestas VALUES (1, 'Fernando Torres')");
        db.execSQL("INSERT INTO Respuestas VALUES (2, 'Lisboa')");
        db.execSQL("INSERT INTO Respuestas VALUES (2, 'Milán')");
        db.execSQL("INSERT INTO Respuestas VALUES (2, 'Cardiff')");
        db.execSQL("INSERT INTO Respuestas VALUES (2, 'Kiev')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Real Sociedad')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Celta del Vigo')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Sevilla')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Betis')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Getafe')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Osasuna')");
        db.execSQL("INSERT INTO Respuestas VALUES (4, 'Barcelona')");
        db.execSQL("INSERT INTO Respuestas VALUES (4, 'Real Madrid')");
        db.execSQL("INSERT INTO Respuestas VALUES (4, 'Atlético de Madrid')");
        db.execSQL("INSERT INTO Respuestas VALUES (4, 'Rayo Vallecano')");

        //db.execSQL("INSERT INTO Respuestas VALUES ('ID_IMAGEN', 'IMAGEN')");
        db.execSQL("INSERT INTO Imagenes VALUES (1, 'imagen_gol_ramos')");
        db.execSQL("INSERT INTO Imagenes VALUES (2, 'imagen_saque_banda')");
        db.execSQL("INSERT INTO Imagenes VALUES (2, 'imagen_saque_esquina')");
        db.execSQL("INSERT INTO Imagenes VALUES (2, 'imagen_tiro_penalti')");
        db.execSQL("INSERT INTO Imagenes VALUES (2, 'imagen_falta')");

        //db.execSQL("INSERT INTO Jugadores VALUES ('ID_IMAGEN', 'IMAGEN')");
        db.execSQL("INSERT INTO Jugadores VALUES ('Anonimo',NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
