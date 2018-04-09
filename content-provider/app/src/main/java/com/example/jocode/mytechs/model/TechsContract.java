package com.example.jocode.mytechs.model;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class entre el provider y las aplicaciones
 */
public class TechsContract {

    /**
     * Autoridad del Content Provider
     */
    public final static String AUTHORITY = "com.herprogramacion.mytechs.modelo.TechsProvider";
    public static final String ACTIVIDAD = "actividad";
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ACTIVIDAD);

    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;

    /**
     * Código para URIs de multiples registros
     */
    public static final int ALLROWS = 1;
    /**
     * Código para URIS de un solo registro
     */
    public static final int SINGLE_ROW = 2;

    // Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ACTIVIDAD, ALLROWS);
        uriMatcher.addURI(AUTHORITY, ACTIVIDAD + "/#", SINGLE_ROW);
    }

    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + ACTIVIDAD;
    /**
     * Tipo MIME que retorna la consulta de {@link CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + ACTIVIDAD;


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        /**
         * Categoría de la actividad
         */
        public final static String CATEGORIA = "categoria";
        /**
         * Descripción de la actividad
         */
        public final static String DESCRIPCION = "descripcion";
        /**
         * Técnico asignado a la actividad
         */
        public final static String TECNICO = "tecnico";
        /**
         * Estado en que se encuentra la actividad
         */
        public final static String ESTADO = "estado";
        /**
         * Prioridad de realización de la actividad
         */
        public final static String PRIORIDAD = "prioridad";

    }

}
