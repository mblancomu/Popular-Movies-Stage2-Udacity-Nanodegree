package com.example.blancomm.popularmoviesstage2.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by manuel on 18/10/15.
 */
public class FavoritesDB {

    public static final String TABLE_FAVORITES = "favorites";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IDMOVIE = "id_movie";
    public static final String COLUMN_ADULT = "adult";
    public static final String COLUMN_IMAGEDETAIL = "backdrop_path";
    public static final String COLUMN_GENRE_ID = "genre_ids";
    public static final String COLUMN_DESCRIPTION = "overview";
    public static final String COLUMN_ORIGINAL_LANG = "original_language";
    public static final String COLUMN_ORIGINAL_TITLE = "original_title";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_THUMBNAIL = "poster_path";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_VIDEO = "video";
    public static final String COLUMN_VOTE_AVER = "vote_average";
    public static final String COLUMN_VOTE_COUNT = "vote_count";


    private static final String DATABASE_CREATE = "create table "
            + TABLE_FAVORITES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_IDMOVIE + " text not null, "
            + COLUMN_ADULT + " text not null, "
            + COLUMN_IMAGEDETAIL + " text not null, "
            + COLUMN_GENRE_ID + " text not null, "
            + COLUMN_DESCRIPTION + " text not null, "
            + COLUMN_ORIGINAL_LANG + " text not null, "
            + COLUMN_ORIGINAL_TITLE + " text not null, "
            + COLUMN_RELEASE_DATE + " text not null, "
            + COLUMN_THUMBNAIL + " text not null, "
            + COLUMN_POPULARITY + " text not null, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_VIDEO + " text not null, "
            + COLUMN_VOTE_AVER + " text not null, "
            + COLUMN_VOTE_COUNT + " text not null " +
            ");";

    public static final String[] FIELDS = {COLUMN_ID, COLUMN_IDMOVIE, COLUMN_ADULT, COLUMN_IMAGEDETAIL,
            COLUMN_GENRE_ID, COLUMN_DESCRIPTION, COLUMN_ORIGINAL_LANG, COLUMN_ORIGINAL_TITLE, COLUMN_RELEASE_DATE,
            COLUMN_THUMBNAIL, COLUMN_POPULARITY, COLUMN_TITLE, COLUMN_VIDEO, COLUMN_VOTE_AVER, COLUMN_VOTE_COUNT};

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(database);

    }
}
