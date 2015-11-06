package com.example.blancomm.popularmoviesstage2.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by manuel on 18/10/15.
 */
public class FavoritesDB {

    public static final String TABLE_FAVORITES = "favorites";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IDMOVIE = "id_movie";
    public static final  String COLUMN_ADULT = "adult";
    public static final  String COLUMN_IMAGE = "image_detail";
    public static final  String COLUMN_GENRES = "genre_ids";
    public static final  String COLUMN_ORLANG = "original_language";
    public static final  String COLUMN_ORTITLE = "original_title";
    public static final  String COLUMN_DESC = "description";
    public static final  String COLUMN_DATE = "release_date";
    public static final  String COLUMN_THUMB = "thumnail";
    public static final  String COLUMN_POPULAR = "popularity";
    public static final  String COLUMN_TITLE = "title";
    public static final  String COLUMN_VIDEO = "video";
    public static final  String COLUMN_AVERAGE = "vote_average";
    public static final  String COLUMN_COUNT = "vote_count";


    private static final String DATABASE_CREATE = "create table "
            + TABLE_FAVORITES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_IDMOVIE + " text, "
            + COLUMN_TITLE + " text, "
            + COLUMN_ADULT+ " text, "
            + COLUMN_IMAGE+ " text, "
            + COLUMN_GENRES + " text, "
            + COLUMN_ORLANG + " text, "
            + COLUMN_ORTITLE + " text, "
            + COLUMN_DESC + " text, "
            + COLUMN_DATE + " text, "
            + COLUMN_THUMB + " text, "
            + COLUMN_POPULAR + " text, "
            + COLUMN_VIDEO + " text, "
            + COLUMN_AVERAGE + " text, "
            + COLUMN_COUNT + " text " +
            ");";

    public static final String[] FIELDS = {COLUMN_ID, COLUMN_IDMOVIE, COLUMN_TITLE, COLUMN_ADULT, COLUMN_IMAGE, COLUMN_GENRES,
    COLUMN_ORLANG, COLUMN_ORTITLE, COLUMN_DESC, COLUMN_DATE, COLUMN_THUMB, COLUMN_POPULAR, COLUMN_VIDEO, COLUMN_AVERAGE, COLUMN_COUNT};

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(database);

    }
}
