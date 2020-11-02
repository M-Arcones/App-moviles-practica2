package com.example.myapplication;

import android.provider.BaseColumns;

class DbContract {
    static final String SQL_CREATE_PREGUNTA =
            "CREATE TABLE " + DbContract.DbEntry.TABLE_PREGUNTAS + " (" +
                    DbContract.DbEntry._ID + " INTEGER PRIMARY KEY," +
                    DbContract.DbEntry.COLUMN_TIPO + " TEXT," +
                    DbContract.DbEntry.COLUMN_ASK + " TEXT," +
                    DbContract.DbEntry.COLUMN_ID_RESPUESTA + " INTEGER," +
                    DbContract.DbEntry.COLUMN_EXPLICACION + " TEXT," +
                    DbContract.DbEntry.COLUMN_SOLUCION + " TEXT," +
                    DbContract.DbEntry.COLUMN_MIN + " INTEGER," +
                    DbContract.DbEntry.COLUMN_MAX + " INTEGER," +
                    DbContract.DbEntry.COLUMN_ID_IMAGEN + " INTEGER);";
    static final String SQL_CREATE_RESPUESTA =
            "CREATE TABLE "+ DbContract.DbEntry.TABLE_RESPUESTAS + " (" +
                    DbContract.DbEntry.COLUMN_ID_RESPUESTA + " INTEGER," +
                    DbContract.DbEntry.COLUMN_RESPUESTA + " TEXT);";
    static final String SQL_CREATE_IMAGEN =
            "CREATE TABLE "+ DbContract.DbEntry.TABLE_IMAGENES + " (" +
                    DbContract.DbEntry.COLUMN_ID_IMAGEN + " INTEGER," +
                    DbContract.DbEntry.COLUMN_IMAGEN + " TEXT);";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DbContract.DbEntry.TABLE_PREGUNTAS + ";" +
            "DROP TABLE IF EXISTS " + DbContract.DbEntry.TABLE_RESPUESTAS + ";" +
            "DROP TABLE IF EXISTS " + DbContract.DbEntry.TABLE_IMAGENES + ";";

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DbContract() {}

    /* Inner class that defines the table contents */
    static class DbEntry implements BaseColumns {
        static final String TABLE_PREGUNTAS = "Preguntas";
        static final String COLUMN_TIPO = "Tipo";
        static final String COLUMN_ASK = "Ask";
        static final String COLUMN_ID_RESPUESTA = "Id_Respuesta";
        static final String COLUMN_EXPLICACION = "Explicacion";
        static final String COLUMN_SOLUCION = "Solucion";
        static final String COLUMN_MIN = "Min";
        static final String COLUMN_MAX = "Max";
        static final String COLUMN_ID_IMAGEN = "Id_Imagen";
        static final String TABLE_RESPUESTAS = "Respuestas";
        static final String COLUMN_RESPUESTA = "Respuesta";
        static final String TABLE_IMAGENES = "Imagenes";
        static final String COLUMN_IMAGEN = "Imagen";
    }
}
