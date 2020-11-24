package com.example.myapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

public class DbManager {

    private DbHelper helper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.helper = new DbHelper(context);
        this.db = this.helper.getWritableDatabase();
    }

    /*public void insertEntry(String playerName, String numResult){
        this.db.insert(DbContract.DbEntry.TABLE_NAME, null,
                this.generateContentValues(playerName, numResult));
    }*/

    public Cursor getPreguntas () {
        String[] columns = new String[]{
                DbContract.DbEntry.COLUMN_TIPO,
                DbContract.DbEntry.COLUMN_TEMA,
                DbContract.DbEntry.COLUMN_ASK,
                DbContract.DbEntry.COLUMN_ID_RESPUESTA,
                DbContract.DbEntry.COLUMN_EXPLICACION,
                DbContract.DbEntry.COLUMN_SOLUCION,
                DbContract.DbEntry.COLUMN_MIN,
                DbContract.DbEntry.COLUMN_MAX,
                DbContract.DbEntry.COLUMN_ID_IMAGEN,
                DbContract.DbEntry.COLUMN_VIDEO,
                DbContract.DbEntry.COLUMN_SONIDO};
        return db.query(DbContract.DbEntry.TABLE_PREGUNTAS, columns, null, null,
                null, null, null);
    }

    public Cursor getRespuestas (int id_R) {
        String where ="ID_RESPUESTA="+id_R;
        String[] columns = new String[]{
                DbContract.DbEntry.COLUMN_RESPUESTA};
        return db.query(DbContract.DbEntry.TABLE_RESPUESTAS, columns, where, null,
                null, null, null);
    }

    public Cursor getImagenes (int id_I) {
        String where ="ID_IMAGEN="+id_I;
        String[] columns = new String[]{
                DbContract.DbEntry.COLUMN_IMAGEN};
        return db.query(DbContract.DbEntry.TABLE_IMAGENES, columns, where, null,
                null, null, null);
    }

    public Cursor getJugadores () {
        String[] columns = new String[]{
                DbContract.DbEntry.COLUMN_NOMJUGADOR};
        return db.query(DbContract.DbEntry.TABLE_JUGADORES, columns, null, null,
                null, null, null);
    }

    public Cursor getFotoJugador (String nomUsuario) {
        String QueryMaxPt = "SELECT " + DbContract.DbEntry.COLUMN_FOTO + " FROM " + DbContract.DbEntry.TABLE_JUGADORES + " WHERE "+ DbContract.DbEntry.COLUMN_NOMJUGADOR + "= '"+ nomUsuario+ "'";
        Cursor cursor = db.rawQuery(QueryMaxPt, null);
        return cursor;
    }

    public void delete_Jugadores(String nomUsuario)
    {
        db.delete(DbContract.DbEntry.TABLE_JUGADORES, DbContract.DbEntry.COLUMN_NOMJUGADOR + "= '" + nomUsuario + "'", null);
    }

    public void delete_Puntuacion(String nomUsuario)
    {
        db.delete(DbContract.DbEntry.TABLE_PUNTUACION, DbContract.DbEntry.COLUMN_NOMJUGADOR + "= '" + nomUsuario + "'", null);
    }

    public int getNPartidas(String nomUsuario) {
        String QueryMaxPt = "SELECT * FROM " + DbContract.DbEntry.TABLE_PUNTUACION + " WHERE "+ DbContract.DbEntry.COLUMN_NOMJUGADOR + "= '"+ nomUsuario+ "'";
        Cursor cursor = db.rawQuery(QueryMaxPt, null);
        int maxPuntuacion = cursor.getCount();
        return maxPuntuacion;
    }

    public Cursor getMaxPuntuacion(String nomUsuario) {
        String Columnas=  "MAX("+ DbContract.DbEntry.COLUMN_PUNTUACION+") as Maxima " ;
        String QueryNPartidas = "SELECT " + Columnas + " FROM " + DbContract.DbEntry.TABLE_PUNTUACION + " WHERE "+ DbContract.DbEntry.COLUMN_NOMJUGADOR + "= '"+ nomUsuario+ "'";
        Cursor cursor = db.rawQuery(QueryNPartidas, null);
        return cursor;
    }

    public Cursor getUlltimaPartida(String nomUsuario) {
        String Columnas=  "MAX("+ DbContract.DbEntry.COLUMN_FECHAPARTIDA+") as Maxima " ;
        String QueryNPartidas = "SELECT " + Columnas + " FROM " + DbContract.DbEntry.TABLE_PUNTUACION + " WHERE "+ DbContract.DbEntry.COLUMN_NOMJUGADOR + "= '"+ nomUsuario+ "'";
        Cursor cursor = db.rawQuery(QueryNPartidas, null);
        return cursor;
    }


    public void insertJugador(String playerName, byte[] image){
        this.db.insert(DbContract.DbEntry.TABLE_JUGADORES, null,
                this.generateContentValues(playerName, image));
    }

    private ContentValues generateContentValues(String playerName, byte[] image){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DbEntry.COLUMN_NOMJUGADOR, playerName);
        contentValues.put(DbContract.DbEntry.COLUMN_FOTO, image);
        return contentValues;
    }

    public void insertPuntuacion(String playerName, int Puntuacion, String Fecha){
        this.db.execSQL("INSERT INTO Clasificacion (Puntuacion, Nombre_Jugador, FechaPartida) VALUES ("+Puntuacion +",'"+playerName+"','"+ Fecha+"')");
    }

    public int getN_Clasificacion () {
        String countQuery = "SELECT  * FROM " + DbContract.DbEntry.TABLE_PUNTUACION + " WHERE "+ DbContract.DbEntry.COLUMN_PUNTUACION + "> 0";
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Cursor get5Mejores_Clasificacion() {
        String Columnas=  DbContract.DbEntry.COLUMN_PUNTUACION + " ,"+ DbContract.DbEntry.COLUMN_NOMJUGADOR;
        String Top5Query = "SELECT " + Columnas + " FROM " + DbContract.DbEntry.TABLE_PUNTUACION + " WHERE "+ DbContract.DbEntry.COLUMN_PUNTUACION + "> 0  ORDER BY "+ DbContract.DbEntry.COLUMN_PUNTUACION + " DESC LIMIT 5";
        Cursor cursor = db.rawQuery(Top5Query, null);
        return cursor;
    }


    public void deleteAll() {
        this.db.delete(DbContract.DbEntry.TABLE_PREGUNTAS, null,null);
    }



/*    private ContentValues generateContentValues(String playerName, String numResult){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_PLAYER, playerName);
        contentValues.put(DbContract.DbEntry.COLUMN_NAME_RESULT, numResult);
        return contentValues;
    }*/

}
