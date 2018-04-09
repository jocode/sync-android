package com.example.jocode.mytechs.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content Provider personalizado para las actividades
 */
public class TechsProvider extends ContentProvider {

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "techs.db";
    // Versión de la base de datos
    private static final int DATABASE_VERSION = 1;

    // Instancia del administrador de base de datos (DatabaseHelper)
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializamos el gestor de la base de datos
        databaseHelper = new DatabaseHelper(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );

        return true;
    }

    /**
     * Nota:
     * Como consejo es importante evitar crear la base de datos en el método OnCreate,
     * que esto retrasa el inicio
     */


    /**
     * Este método será el encargado de realizar las consultas a la base de datos.
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Obtenemos la base de datos
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        // Comparar el Uri
        int match = TechsContract.uriMatcher.match(uri);

        Cursor cursor;

        switch (match){

            case TechsContract.ALLROWS:
                // Consulta todos los registros
                cursor = database.query(TechsContract.ACTIVIDAD, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                cursor.setNotificationUri(
                        getContext().getContentResolver(),
                        TechsContract.CONTENT_URI);
                break;

            case TechsContract.SINGLE_ROW:
                // Consultando un solo registro basado en el Id del Uri
                long idActividad = ContentUris.parseId(uri);

                cursor = database.query(TechsContract.ACTIVIDAD, projection,
                        TechsContract.Columnas._ID + " = " + idActividad,
                        selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(
                        getContext().getContentResolver(),
                        TechsContract.CONTENT_URI);
                break;

            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (TechsContract.uriMatcher.match(uri)) {
            case TechsContract.ALLROWS:
                return TechsContract.MULTIPLE_MIME;
            case TechsContract.SINGLE_ROW:
                return TechsContract.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida: " + uri);
        }
    }

   @Override
    public Uri insert(Uri uri, ContentValues values) {

        // Validamos la Uri
       if (TechsContract.uriMatcher.match(uri) != TechsContract.ALLROWS) {
           throw new IllegalArgumentException("URI desconocida : " + uri);
       }
       ContentValues contentValues;
       if (values != null) {
           contentValues = new ContentValues(values);
       } else {
           contentValues = new ContentValues();
       }

       // Si es necesario, verifica los valores

       // Inserción de nueva fila
       SQLiteDatabase db = databaseHelper.getWritableDatabase();
       long rowId = db.insert(TechsContract.ACTIVIDAD,
               null, contentValues);
       if (rowId > 0) {
           Uri uri_actividad =
                   ContentUris.withAppendedId(
                           TechsContract.CONTENT_URI, rowId);
           getContext().getContentResolver().
                   notifyChange(uri_actividad, null);
           return uri_actividad;
       }

       throw new SQLException("Falla al insertar fila en : " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = TechsContract.uriMatcher.match(uri);
        int affected;

        switch (match) {
            case TechsContract.ALLROWS:
                affected = db.delete(TechsContract.ACTIVIDAD,
                        selection,
                        selectionArgs);
                break;
            case TechsContract.SINGLE_ROW:
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(TechsContract.ACTIVIDAD,
                        TechsContract.Columnas._ID + "=" + idActividad
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                getContext().getContentResolver().
                        notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (TechsContract.uriMatcher.match(uri)) {
            case TechsContract.ALLROWS:
                affected = db.update(TechsContract.ACTIVIDAD, values,
                        selection, selectionArgs);
                break;
            case TechsContract.SINGLE_ROW:
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(TechsContract.ACTIVIDAD, values,
                        TechsContract.Columnas._ID + "=" + idActividad
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}
