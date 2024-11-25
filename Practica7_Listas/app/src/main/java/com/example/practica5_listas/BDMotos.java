package com.example.practica5_listas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BDMotos extends SQLiteOpenHelper {

    private EditText cajaModelo, cajaUrl;
    private Spinner cajaTipo;
    private Button insertar;

    private static final String DATABASE_NAME = "BDMotos.db";

    private static final int DATABASE_VERSION = 1;

    private Context contexto;

    public BDMotos(Context contexto)
    {
        super(contexto, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String SQLCREATE = "CREATE TABLE Motos (modelo TEXT, tipo TEXT, imagen TEXT)";

    private SQLiteDatabase bd = null;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLCREATE);

    }

    private String SQLDROP = "DROP TABLE IF EXISTS Motos";

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQLDROP);
        sqLiteDatabase.execSQL(SQLCREATE);

    }

    public void carrarBD() {
        if (bd != null) {
            bd.close();
        }
    }
    public ArrayList<Motos> obtenerTodosLasMotos()
    {
        bd = getReadableDatabase();

        ArrayList<Motos> motos = new ArrayList<>();

        Cursor c = bd.query
                (

                        "Motos",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                );
        c.moveToFirst();
        if(c.getCount() > 0 )
        {
            do
            {
                motos.add(new Motos(c.getString(0), c.getString(1), c.getString(2)));

            }
            while(c.moveToNext());
        }
        close();
        return motos;
    }

    public void insertMoto(String modelo, String tipo, String imagen)
    {
        bd = getWritableDatabase();

        if(bd != null)
        {
            ContentValues values = new ContentValues();
            values.put("modelo", modelo);
            values.put("tipo", tipo);
            values.put("imagen", imagen);
            bd.insert("Motos", "", values);
        }
    }
}
