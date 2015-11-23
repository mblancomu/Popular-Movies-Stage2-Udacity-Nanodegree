package com.example.blancomm.popularmoviesstage2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private String name;
    private static DataBaseHelper sInstance;
    private static String TAG = DataBaseHelper.class.getSimpleName();

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        this.name = name;
    }

    public static synchronized DataBaseHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory,
                                                          int version) {

        if (sInstance == null) {
            sInstance = new DataBaseHelper(context, name, factory, version);

        }
        return sInstance;
    }

   /* private DataBaseHelper(Context context,String database, int dataversion) {
        super(context, database, null, dataversion);
    }*/

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

            FavoritesDB.onCreate(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

            FavoritesDB.onUpgrade(sqLiteDatabase, oldVersion, newVersion);

        }

}
