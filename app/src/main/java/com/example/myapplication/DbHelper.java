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
        String W=SQL_CREATE_PREGUNTA;

        db.execSQL(SQL_CREATE_PREGUNTA);
        db.execSQL(SQL_CREATE_RESPUESTA);
        db.execSQL(SQL_CREATE_IMAGEN);
        db.execSQL(SQL_CREATE_PUNTUACIONES);
        db.execSQL(SQL_CREATE_JUGADORES);
        //db.execSQL("INSERT INTO Preguntas VALUES ('ID', 'COLUMN_TIPO', 'COLUMN_ASK', 'COLUMN_ID_RESPUESTA','COLUMN_EXPLICACION','COLUMN_SOLUCION','COLUMN_MIN','COLUMN_MAX','COLUMN_ID_IMAGEN')");
        //db.execSQL("INSERT INTO Respuestas VALUES ('ID_RESPUESTA', 'RESPUESTA')");
        //db.execSQL("INSERT INTO Respuestas VALUES ('ID_IMAGEN', 'IMAGEN')");
        //Futbol
        db.execSQL("INSERT INTO Preguntas VALUES (1, 'Futbol', 'Button', '¿Quién fue el jugador que marcó en la final del mundial de 2010?', 1,'Iniesta marcó en la prórroga el gol decisivo que dio el título a España en una disputadísima final contra Holanda (0-1)','Andrés Iniesta',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (1, 'Andrés Iniesta')");
        db.execSQL("INSERT INTO Respuestas VALUES (1, 'David Villa')");
        db.execSQL("INSERT INTO Respuestas VALUES (1, 'Sergio Ramos')");
        db.execSQL("INSERT INTO Respuestas VALUES (1, 'Fernando Torres')");

        db.execSQL("INSERT INTO Preguntas VALUES (2, 'Futbol', 'Seekbar', '¿Cuál es el máximo número de goles que se han marcado en un partido?', NULL,'El 31 de Octubre de 2002  ek AS Adema ganó al SO I Emyrne 149-0','149',10,200,NULL,NULL,NULL)");

        db.execSQL("INSERT INTO Preguntas VALUES (3, 'Futbol', 'Imagen', '¿Donde se jugó la final de la Champion de la foto?', 2,'Sergio Ramos marcó este gol en la final de Lisboa en el minuto 93 provocando la prórroga, lo que finalizaría con la Décima Champion para el conjunto blanco','Lisboa',NULL,NULL,1,NULL,NULL)");
        db.execSQL("INSERT INTO Imagenes VALUES (1, 'imagen_gol_ramos')");
        db.execSQL("INSERT INTO Respuestas VALUES (2, 'Lisboa')");
        db.execSQL("INSERT INTO Respuestas VALUES (2, 'Milán')");
        db.execSQL("INSERT INTO Respuestas VALUES (2, 'Cardiff')");
        db.execSQL("INSERT INTO Respuestas VALUES (2, 'Kiev')");

        db.execSQL("INSERT INTO Preguntas VALUES (4, 'Futbol', 'Multiple', '¿Cuáles de estos equipos han ganado alguna Liga?', 3,'El Real Sociedad ganó dos trofeos ligueros en 1981 y 1982, el Sevilla ganó este trofeo únicamente en 1946 y el Betis en 1935','Real Sociedad|Sevilla|Betis',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Real Sociedad')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Celta del Vigo')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Sevilla')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Betis')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Getafe')");
        db.execSQL("INSERT INTO Respuestas VALUES (3, 'Osasuna')");

        db.execSQL("INSERT INTO Preguntas VALUES (5, 'Futbol', 'Switch', '¿Lionel Messi es el futbolista que más veces ha ganado el Trofeo Pichichi de la Liga?', null,'Verdadero, Lionel Messi ha ganado este trofeo 7 veces, ademas tiene el record con mas goles en una temporada con 50 goles','true',NULL,NULL,NULL,NULL,NULL)");

        db.execSQL("INSERT INTO Preguntas VALUES (6, 'Futbol', 'ButtonImagen', '¿Cuál de todas estas imagenes corresponde con un penalti?', 4,'Un penalti consiste en lanzar un tiro desde el punto ubicado a 11 metros de la portería.','imagen_tiro_penalti',NULL,NULL,2,NULL,NULL)");
        db.execSQL("INSERT INTO Imagenes VALUES (2, 'imagen_saque_banda')");
        db.execSQL("INSERT INTO Imagenes VALUES (2, 'imagen_saque_esquina')");
        db.execSQL("INSERT INTO Imagenes VALUES (2, 'imagen_tiro_penalti')");
        db.execSQL("INSERT INTO Imagenes VALUES (2, 'imagen_falta')");

        db.execSQL("INSERT INTO Preguntas VALUES (7, 'Futbol', 'Button', '¿Cuál es el equipo español que ha conseguido mayor diferencia de goles en una eliminatoria de competición europea?', 4,'El Rayo Vallecanó ganó 10-0 en la ida y 0-6 en la vuelta contra el Constelació Esportiva de Andorra en la UEFA de la temporada 2000/2001','Rayo Vallecano',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (4, 'Barcelona')");
        db.execSQL("INSERT INTO Respuestas VALUES (4, 'Real Madrid')");
        db.execSQL("INSERT INTO Respuestas VALUES (4, 'Atlético de Madrid')");
        db.execSQL("INSERT INTO Respuestas VALUES (4, 'Rayo Vallecano')");

        db.execSQL("INSERT INTO Preguntas VALUES (8, 'Futbol', 'Sonido', '¿A que competicion corresponde la siguiente canción?',  5,'','Champions League',NULL,NULL,NULL,'sound_champions',NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (5, 'Champions League')");
        db.execSQL("INSERT INTO Respuestas VALUES (5, 'Premier League')");
        db.execSQL("INSERT INTO Respuestas VALUES (5, 'Copa Mundial de Fútbol')");
        db.execSQL("INSERT INTO Respuestas VALUES (5, 'Copa del Rey española')");

        db.execSQL("INSERT INTO Preguntas VALUES (9, 'Futbol', 'Video', '¿Que trofeo gano el REal Madrid con este gol de Zidane?',  6,'','Champions League',NULL,NULL,NULL,NULL,'video_gol_zidane')");/*https://www.youtube.com/watch?v=YkACI8QHpe4*/
        db.execSQL("INSERT INTO Respuestas VALUES (6, 'Champions League')");
        db.execSQL("INSERT INTO Respuestas VALUES (6, 'Liga Española')");
        db.execSQL("INSERT INTO Respuestas VALUES (6, 'Mundial de clubs')");
        db.execSQL("INSERT INTO Respuestas VALUES (6, 'Copa del Rey española')");

        db.execSQL("INSERT INTO Preguntas VALUES (10, 'Futbol', 'Sonido', '¿A que equipo corresponde el siguiente himno?',  7,'','Betis',NULL,NULL,NULL,'himno_betis',NULL)");/*https://youtu.be/tJcA3eKi_XI*/
        db.execSQL("INSERT INTO Respuestas VALUES (7, 'Betis')");
        db.execSQL("INSERT INTO Respuestas VALUES (7, 'Manchester City')");
        db.execSQL("INSERT INTO Respuestas VALUES (7, 'Getafe')");
        db.execSQL("INSERT INTO Respuestas VALUES (7, 'Cádiz')");

        db.execSQL("INSERT INTO Preguntas VALUES (11, 'Futbol', 'Sonido', '¿A que equipo corresponde el siguiente himno?',  8,'','Sevilla',NULL,NULL,NULL,'himno_sevilla',NULL)");/*https://youtu.be/t59da8ixtlk*/
        db.execSQL("INSERT INTO Respuestas VALUES (8, 'Sevilla')");
        db.execSQL("INSERT INTO Respuestas VALUES (8, 'Real Madrid')");
        db.execSQL("INSERT INTO Respuestas VALUES (8, 'Athlec')");
        db.execSQL("INSERT INTO Respuestas VALUES (8, 'Celta')");

        db.execSQL("INSERT INTO Preguntas VALUES (12, 'Futbol', 'Sonido', '¿A que equipo corresponde el siguiente himno?',  9,'','Liverpool',NULL,NULL,NULL,'himno_liverpool',NULL)");/*https://youtu.be/sH42sfMA7Os*/
        db.execSQL("INSERT INTO Respuestas VALUES (9, 'Liverpool')");
        db.execSQL("INSERT INTO Respuestas VALUES (9, 'Manchester City')");
        db.execSQL("INSERT INTO Respuestas VALUES (9, 'Manchester United')");
        db.execSQL("INSERT INTO Respuestas VALUES (9, 'Levante')");

        db.execSQL("INSERT INTO Preguntas VALUES (13, 'Futbol', 'Sonido', '¿Qué están pitando en el audio?',  10,'','Falta',NULL,NULL,NULL,'pitido_falta',NULL)");/*https://youtu.be/Am5nGiTpYno*/
        db.execSQL("INSERT INTO Respuestas VALUES (10, 'Falta')");
        db.execSQL("INSERT INTO Respuestas VALUES (10, 'Fuera')");
        db.execSQL("INSERT INTO Respuestas VALUES (10, 'Final del partido')");
        db.execSQL("INSERT INTO Respuestas VALUES (10, 'Mano')");

        db.execSQL("INSERT INTO Preguntas VALUES (14, 'Futbol', 'Sonido', '¿Cuál fue la razón para componer ese himno?',  11,'','Ganar la Champion League',NULL,NULL,NULL,'hala_madrid_y_nada_mas',NULL)");/*https://youtu.be/oy-Qoq7nRdc*/
        db.execSQL("INSERT INTO Respuestas VALUES (11, 'Ganar la Champion League')");
        db.execSQL("INSERT INTO Respuestas VALUES (11, 'Ganar la Liga')");
        db.execSQL("INSERT INTO Respuestas VALUES (11, 'Ganar la copa')");
        db.execSQL("INSERT INTO Respuestas VALUES (11, 'Ganar la SuperCopa')");

        db.execSQL("INSERT INTO Preguntas VALUES (15, 'Futbol', 'Video', '¿Quién es el mago del balón que aparece en el vídeo?',  12,'','Ronaldinho',NULL,NULL,NULL,NULL,'ronaldinho_skills')");/*https://youtu.be/Ctw0N7zYoYo*/
        db.execSQL("INSERT INTO Respuestas VALUES (12, 'Ronaldinho')");
        db.execSQL("INSERT INTO Respuestas VALUES (12, 'Neymar Jr')");
        db.execSQL("INSERT INTO Respuestas VALUES (12, 'Luis Figo')");
        db.execSQL("INSERT INTO Respuestas VALUES (12, 'Eric Cantona')");

        db.execSQL("INSERT INTO Preguntas VALUES (16, 'Futbol', 'Video', '¿Cómo se llama el golpeo de balon del vídeo?',  13,'','Escorpión',NULL,NULL,NULL,NULL,'escorpion')");/*https://youtu.be/AyQXrvtodCk*/
        db.execSQL("INSERT INTO Respuestas VALUES (13, 'Escorpión')");
        db.execSQL("INSERT INTO Respuestas VALUES (13, 'Volea')");
        db.execSQL("INSERT INTO Respuestas VALUES (13, 'Chilena')");
        db.execSQL("INSERT INTO Respuestas VALUES (13, 'Tijereta')");

        db.execSQL("INSERT INTO Preguntas VALUES (17, 'Futbol', 'Video', '¿Cómo se llama este tipo de gol?',  14,'','Olímpico',NULL,NULL,NULL,NULL,'gol_olimpico')");/*https://youtu.be/X2Cce5_WrBw*/
        db.execSQL("INSERT INTO Respuestas VALUES (14, 'Olímpico')");
        db.execSQL("INSERT INTO Respuestas VALUES (14, 'Jugada Ensayada')");
        db.execSQL("INSERT INTO Respuestas VALUES (14, 'Falta Directa')");
        db.execSQL("INSERT INTO Respuestas VALUES (14, 'Remate')");

        db.execSQL("INSERT INTO Preguntas VALUES (18, 'Futbol', 'Video', '¿Con qué parte deel pie golpeó Roberto Carlos al balón en esat falta?',  15,'','Exterior del Pie',NULL,NULL,NULL,NULL,'falta_roberto_carlos')");/*https://youtu.be/XdL7EDKr_rk*/
        db.execSQL("INSERT INTO Respuestas VALUES (15, 'Exterior del Pie')");
        db.execSQL("INSERT INTO Respuestas VALUES (15, 'Interior del Pied')");
        db.execSQL("INSERT INTO Respuestas VALUES (15, 'Empeine Frontal')");
        db.execSQL("INSERT INTO Respuestas VALUES (15, 'Tacón')");

        db.execSQL("INSERT INTO Preguntas VALUES (19, 'Futbol', 'Video', '¿Cómo se llama el Regate de Neymar?',  16,'','Lambretta',NULL,NULL,NULL,NULL,'lambretta_neymar')");/*https://youtu.be/Kaj1w-TklVY*/
        db.execSQL("INSERT INTO Respuestas VALUES (16, 'Lambretta')");
        db.execSQL("INSERT INTO Respuestas VALUES (16, 'Bicicleta')");
        db.execSQL("INSERT INTO Respuestas VALUES (16, 'Ruleta')");
        db.execSQL("INSERT INTO Respuestas VALUES (16, 'Croqueta')");

        db.execSQL("INSERT INTO Preguntas VALUES (20, 'Futbol', 'Video', '¿Cómo se llama este emblemático regate',  17,'','Gravesinha',NULL,NULL,NULL,NULL,'gravesinha')");/*https://youtu.be/ExoPmpixznw*/
        db.execSQL("INSERT INTO Respuestas VALUES (17, 'Gravesinha')");
        db.execSQL("INSERT INTO Respuestas VALUES (17, 'Coca-Cola')");
        db.execSQL("INSERT INTO Respuestas VALUES (17, 'Bicicleta')");
        db.execSQL("INSERT INTO Respuestas VALUES (17, 'Elástica')");

        db.execSQL("INSERT INTO Preguntas VALUES (21, 'Futbol', 'Button', '¿Cuál es el equipo español más antiguo?', 18,'','Recreativo de Huelva',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (18, 'Recreativo de Huelva')");
        db.execSQL("INSERT INTO Respuestas VALUES (18, 'Real Madrid')");
        db.execSQL("INSERT INTO Respuestas VALUES (18, 'Athletic de Bilbao')");
        db.execSQL("INSERT INTO Respuestas VALUES (18, 'CF Badalona')");

        db.execSQL("INSERT INTO Preguntas VALUES (22, 'Futbol', 'Button', '¿Cuál es el equipo actual de Robert Lewandowski?', 19,'','Bayern de Munich',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (19, 'Bayern de Munich')");
        db.execSQL("INSERT INTO Respuestas VALUES (19, 'Borussia Dortmund')");
        db.execSQL("INSERT INTO Respuestas VALUES (19, 'Borussia Mönchengladbach')");
        db.execSQL("INSERT INTO Respuestas VALUES (19, 'Bayer 04 Leverkusen')");

        //Videojuegos
        // /*https://youtu.be/krlaWEIx4XY*/
        db.execSQL("INSERT INTO Preguntas VALUES (23, 'Videojuegos', 'Sonido', '¿A que juego pertenece esta intro?',  20,'','Rocket League',NULL,NULL,NULL,'rocket_league_intro',NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (20, 'Rocket League')");
        db.execSQL("INSERT INTO Respuestas VALUES (20, 'Fifa')");
        db.execSQL("INSERT INTO Respuestas VALUES (20, 'League of Legends')");
        db.execSQL("INSERT INTO Respuestas VALUES (20, 'Hello Kitty online')");

        /*https://youtu.be/W2-p8u6JAUo*/
        db.execSQL("INSERT INTO Preguntas VALUES (24, 'Videojuegos', 'Video', '¿Cómo se llama este tipo de disparo?', 21,'','Ceiling Shot',NULL,NULL,NULL,NULL,'ceiling_shot')");
        db.execSQL("INSERT INTO Respuestas VALUES (21, 'Ceiling Shot')");
        db.execSQL("INSERT INTO Respuestas VALUES (21, 'Jump Reset')");
        db.execSQL("INSERT INTO Respuestas VALUES (21, 'Pinch')");
        db.execSQL("INSERT INTO Respuestas VALUES (21, 'Musty Flip')");

        db.execSQL("INSERT INTO Preguntas VALUES (25, 'Videojuegos', 'Button', '¿Cuál es el regate más fuerte de los últmos FIFAs?', 22,'','Bayern de Munich',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (22, 'Elástica')");
        db.execSQL("INSERT INTO Respuestas VALUES (22, 'Croqueta')");
        db.execSQL("INSERT INTO Respuestas VALUES (22, 'Coca-Cola')");
        db.execSQL("INSERT INTO Respuestas VALUES (22, 'Ruleta')");

        /*https://youtu.be/RFxkYOeU2Ag*/
        db.execSQL("INSERT INTO Preguntas VALUES (26, 'Videojuegos', 'Video', '¿Cómo se llama este tipo de movimiento el LoL?', 23,'','Gank',NULL,NULL,NULL,NULL,'gank')");
        db.execSQL("INSERT INTO Respuestas VALUES (23, 'Gank')");
        db.execSQL("INSERT INTO Respuestas VALUES (23, 'Invade')");
        db.execSQL("INSERT INTO Respuestas VALUES (23, 'Push')");
        db.execSQL("INSERT INTO Respuestas VALUES (23, 'Stun')");

        db.execSQL("INSERT INTO Preguntas VALUES (27, 'Videojuegos', 'Button', '¿Cuándo s einició la beta cerrada de LoL?', 24,'','abril de 2009',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (24, 'abril de 2009')");
        db.execSQL("INSERT INTO Respuestas VALUES (24, 'abril 2010')");
        db.execSQL("INSERT INTO Respuestas VALUES (24, 'enero 2009')");
        db.execSQL("INSERT INTO Respuestas VALUES (24, 'junio 2009')");

        db.execSQL("INSERT INTO Preguntas VALUES (28, 'Videojuegos', 'Button', '¿Cuándo se inició la beta cerrada de LoL?', 25,'','abril de 2009',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (25, 'abril de 2009')");
        db.execSQL("INSERT INTO Respuestas VALUES (25, 'abril 2010')");
        db.execSQL("INSERT INTO Respuestas VALUES (25, 'enero 2009')");
        db.execSQL("INSERT INTO Respuestas VALUES (25, 'junio 2009')");

        db.execSQL("INSERT INTO Preguntas VALUES (29, 'Videojuegos', 'Button', '¿Que campeón era capaz de empujar al barón por todo el mapa debido a un bug?', 26,'','abril de 2009',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (26, 'Poppy')");
        db.execSQL("INSERT INTO Respuestas VALUES (26, 'Azir')");
        db.execSQL("INSERT INTO Respuestas VALUES (26, 'Trindamere')");
        db.execSQL("INSERT INTO Respuestas VALUES (26, 'Lulu')");

        db.execSQL("INSERT INTO Preguntas VALUES (30, 'Videojuegos', 'Button', '¿Cuál fue la recompensa en la temporada 1 si llegabas a oro?', 27,'','Jarvan victorioso',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (27, 'Jarvan victorioso')");
        db.execSQL("INSERT INTO Respuestas VALUES (27, 'Janna victoriosa')");
        db.execSQL("INSERT INTO Respuestas VALUES (27, 'Kayle justiciera')");
        db.execSQL("INSERT INTO Respuestas VALUES (27, 'Riven de campeonato')");

        db.execSQL("INSERT INTO Preguntas VALUES (31, 'Videojuegos', 'Button', '¿Qué campeón aparecía en el clip de la beta de Mac?', 28,'','Dr Mundo',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (28, 'Dr Mundo')");
        db.execSQL("INSERT INTO Respuestas VALUES (28, 'Ashe')");
        db.execSQL("INSERT INTO Respuestas VALUES (28, 'Vayne')");
        db.execSQL("INSERT INTO Respuestas VALUES (28, 'Annie')");

        db.execSQL("INSERT INTO Preguntas VALUES (32, 'Videojuegos', 'Button', '¿Qué equipo ha ganod los Worlds este año?', 29,'','DAMWON Gaming',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (29, 'G2')");
        db.execSQL("INSERT INTO Respuestas VALUES (29, 'Fnatic')");
        db.execSQL("INSERT INTO Respuestas VALUES (29, 'DAMWON Gaming')");
        db.execSQL("INSERT INTO Respuestas VALUES (29, 'Unicorns of Love')");

        db.execSQL("INSERT INTO Preguntas VALUES (33, 'Videojuegos', 'Button', '¿Qué campeón de LoL fue el primero en tener skin definitiva?', 30,'','Ezreal',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (30, 'Ezreal')");
        db.execSQL("INSERT INTO Respuestas VALUES (30, 'Vayne')");
        db.execSQL("INSERT INTO Respuestas VALUES (30, 'Jinx')");
        db.execSQL("INSERT INTO Respuestas VALUES (30, 'Teemo')");

        db.execSQL("INSERT INTO Preguntas VALUES (34, 'Videojuegos', 'Button', '¿Segun la historia, que campeon de LoL es el mas poderoso?', 31,'','Xerath',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (31, 'Xerath')");
        db.execSQL("INSERT INTO Respuestas VALUES (31, 'Yasuo')");
        db.execSQL("INSERT INTO Respuestas VALUES (31, 'Brand')");
        db.execSQL("INSERT INTO Respuestas VALUES (31, 'Poppy')");

        db.execSQL("INSERT INTO Preguntas VALUES (35, 'Videojuegos', 'Button', '¿Segun la historia, que campeon de LoL es el mas poderoso?', 32,'','Xerath',NULL,NULL,NULL,NULL,NULL)");
        db.execSQL("INSERT INTO Respuestas VALUES (32, 'Xerath')");
        db.execSQL("INSERT INTO Respuestas VALUES (32, 'Yasuo')");
        db.execSQL("INSERT INTO Respuestas VALUES (32, 'Brand')");
        db.execSQL("INSERT INTO Respuestas VALUES (32, 'Poppy')");

        db.execSQL("INSERT INTO Preguntas VALUES (36, 'Videojuegos', 'Video', '¿Cómo se llama este coche de Rocket League?', 33,'','Esper',NULL,NULL,NULL,NULL,'rocket_league_esper')");
        db.execSQL("INSERT INTO Respuestas VALUES (33, 'Esper')");
        db.execSQL("INSERT INTO Respuestas VALUES (33, 'Octane')");
        db.execSQL("INSERT INTO Respuestas VALUES (33, 'Scarab')");
        db.execSQL("INSERT INTO Respuestas VALUES (33, 'Aftershock')");

        db.execSQL("INSERT INTO Preguntas VALUES (37, 'Videojuegos', 'Video', '¿Cómo se llama este coche de Rocket League?', 34,'','Fennec',NULL,NULL,NULL,NULL,'rocket_league_fennec')");
        db.execSQL("INSERT INTO Respuestas VALUES (34, 'Fennec')");
        db.execSQL("INSERT INTO Respuestas VALUES (34, 'Octane')");
        db.execSQL("INSERT INTO Respuestas VALUES (34, 'Backfire')");
        db.execSQL("INSERT INTO Respuestas VALUES (34, 'Dominus')");

        db.execSQL("INSERT INTO Preguntas VALUES (38, 'Videojuegos', 'Video', '¿Cómo se llama este coche de Rocket League?', 35,'','Merc',NULL,NULL,NULL,NULL,'rocket_league_merch')");
        db.execSQL("INSERT INTO Respuestas VALUES (35, 'Fennec')");
        db.execSQL("INSERT INTO Respuestas VALUES (35, 'Octane')");
        db.execSQL("INSERT INTO Respuestas VALUES (35, 'Merc')");
        db.execSQL("INSERT INTO Respuestas VALUES (35, 'Roadhog')");

        db.execSQL("INSERT INTO Preguntas VALUES (39, 'Videojuegos', 'Video', '¿Cómo se llama este coche de Rocket League?', 36,'','Octane',NULL,NULL,NULL,NULL,'rocket_league_octane')");
        db.execSQL("INSERT INTO Respuestas VALUES (36, 'Triton')");
        db.execSQL("INSERT INTO Respuestas VALUES (36, 'Octane')");
        db.execSQL("INSERT INTO Respuestas VALUES (36, 'Takumi')");
        db.execSQL("INSERT INTO Respuestas VALUES (36, 'Dominus')");

        db.execSQL("INSERT INTO Preguntas VALUES (40, 'Videojuegos', 'Video', '¿Cómo se llama este coche de Rocket League?', 37,'','Breakout',NULL,NULL,NULL,NULL,'rocket_league_breakout')");
        db.execSQL("INSERT INTO Respuestas VALUES (37, 'Breakout')");
        db.execSQL("INSERT INTO Respuestas VALUES (37, 'Venom')");
        db.execSQL("INSERT INTO Respuestas VALUES (37, 'Backfire')");
        db.execSQL("INSERT INTO Respuestas VALUES (37, 'Vulcan')");

        db.execSQL("INSERT INTO Preguntas VALUES (41, 'Videojuegos', 'Video', '¿Cómo se llama este coche de Rocket League?', 38,'','Scarab',NULL,NULL,NULL,NULL,'rocket_league_scarab')");
        db.execSQL("INSERT INTO Respuestas VALUES (38, 'Scarab')");
        db.execSQL("INSERT INTO Respuestas VALUES (38, 'Octane')");
        db.execSQL("INSERT INTO Respuestas VALUES (38, 'Takumi')");
        db.execSQL("INSERT INTO Respuestas VALUES (38, 'Fennec')");

        db.execSQL("INSERT INTO Preguntas VALUES (42, 'Videojuegos', 'Video', '¿Cómo se llama este coche de Rocket League?', 39,'','Ripper',NULL,NULL,NULL,NULL,'rocket_league_ripper')");
        db.execSQL("INSERT INTO Respuestas VALUES (39, 'Ripper')");
        db.execSQL("INSERT INTO Respuestas VALUES (39, 'Merc')");
        db.execSQL("INSERT INTO Respuestas VALUES (39, 'Breakout')");
        db.execSQL("INSERT INTO Respuestas VALUES (39, 'Dominus')");

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
