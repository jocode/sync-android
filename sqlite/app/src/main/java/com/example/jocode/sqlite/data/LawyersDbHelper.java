package com.example.jocode.sqlite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.jocode.sqlite.data.LawyersContract.LawyerEntry;

public class LawyersDbHelper extends SQLiteOpenHelper {

    // Definimos la Versión y el Nombre de la base de datos
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Lawyers.db";

    public LawyersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table
        db.execSQL(LawyerEntry.CREATE_TABLE_SENTENCE);

        // Insertar Datos Ficticias (Prueba Inicial)
        //mockData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LawyerEntry.DELETE_TABLE_SENTENCE);
        onCreate(db);
    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockLawyer(sqLiteDatabase, new Lawyer("Carlos Perez", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "carlos_perez.jpg", ""));
        mockLawyer(sqLiteDatabase, new Lawyer("Daniel Samper", "Abogado accidentes de tráfico",
                "300 200 2222", "Gran profesional con experiencia de 5 años en accidentes de tráfico.",
                "daniel_samper.jpg", ""));
        mockLawyer(sqLiteDatabase, new Lawyer("Lucia Aristizabal", "Abogado de derechos laborales",
                "300 200 3333", "Gran profesional con más de 3 años de experiencia en defensa de los trabajadores.",
                "lucia_aristizabal.jpg", ""));
        mockLawyer(sqLiteDatabase, new Lawyer("Marina Acosta", "Abogado de familia",
                "300 200 4444", "Gran profesional con experiencia de 5 años en casos de familia.",
                "marina_acosta.jpg", ""));
        mockLawyer(sqLiteDatabase, new Lawyer("Olga Ortiz", "Abogado de administración pública",
                "300 200 5555", "Gran profesional con experiencia de 5 años en casos en expedientes de urbanismo.",
                "olga_ortiz.jpg", ""));
        mockLawyer(sqLiteDatabase, new Lawyer("Pamela Briger", "Abogado fiscalista",
                "300 200 6666", "Gran profesional con experiencia de 5 años en casos de derecho financiero",
                "pamela_briger.jpg", ""));
        mockLawyer(sqLiteDatabase, new Lawyer("Rodrigo Benavidez", "Abogado Mercantilista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en redacción de contratos mercantiles",
                "rodrigo_benavidez.jpg", ""));
        mockLawyer(sqLiteDatabase, new Lawyer("Tom Bonz", "Abogado penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "tom_bonz.jpg", ""));
    }

    public long mockLawyer(SQLiteDatabase db, Lawyer lawyer) {
        return db.insert(
                LawyerEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());
    }

    public Cursor getAllLawyers() {
        return getReadableDatabase()
                .query(
                        LawyerEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getLawyerById(String lawyerId) {
        Cursor c = getReadableDatabase().query(
                LawyerEntry.TABLE_NAME,
                null,
                LawyerEntry.ID + " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        return c;
    }

    public long saveLawyer(Lawyer lawyer) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                LawyerEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());

    }

    public int deleteLawyer(String lawyerId) {
        return getWritableDatabase().delete(
                LawyerEntry.TABLE_NAME,
                LawyerEntry.ID + " LIKE ?",
                new String[]{lawyerId});
    }

    public int updateLawyer(Lawyer lawyer, String lawyerId) {
        return getWritableDatabase().update(
                LawyerEntry.TABLE_NAME,
                lawyer.toContentValues(),
                LawyerEntry.ID + " LIKE ?",
                new String[]{lawyerId}
        );
    }

}
