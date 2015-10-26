package com.example.blancomm.popularmoviesstage2.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by manuel on 18/10/15.
 */
public class FavoritesDB {

    public static final String TABLE_FAVORITES = "favorites";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IDMOVIE = "id_movie";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ISCHECKED = "ischecked";


    private static final String DATABASE_CREATE = "create table "
            + TABLE_FAVORITES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_IDMOVIE + " text, "
            + COLUMN_TITLE + " text, "
            + COLUMN_ISCHECKED+ " integer " +
            ");";

    public static final String[] FIELDS = {COLUMN_ID, COLUMN_IDMOVIE, COLUMN_TITLE, COLUMN_ISCHECKED};

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(database);

    }
}
