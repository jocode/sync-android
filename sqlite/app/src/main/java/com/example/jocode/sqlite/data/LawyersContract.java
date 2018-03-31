package com.example.jocode.sqlite.data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para la Tabla o Entidad Abogados
 */
public class LawyersContract {

    /*Esta clase interna almacena los nombres de los campos y de la Entidad que usaremos en
     * la base de datos */
    public static abstract class LawyerEntry implements BaseColumns {

        public static final String TABLE_NAME ="lawyer";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String SPECIALTY = "specialty";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String AVATAR_URI = "avatarUri";
        public static final String BIO = "bio";

        public static final String CREATE_TABLE_SENTENCE =
                "CREATE TABLE " + LawyerEntry.TABLE_NAME + " ("
                + LawyerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + LawyerEntry.ID + " TEXT NOT NULL,"
                + LawyerEntry.NAME + " TEXT NOT NULL,"
                + LawyerEntry.SPECIALTY + " TEXT NOT NULL,"
                + LawyerEntry.PHONE_NUMBER + " TEXT NOT NULL,"
                + LawyerEntry.BIO + " TEXT NOT NULL,"
                + LawyerEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + LawyerEntry.ID + "))";

        public static final String DELETE_TABLE_SENTENCE =
                "DROP TABLE IF EXISTS " + LawyerEntry.TABLE_NAME;

    }

}
