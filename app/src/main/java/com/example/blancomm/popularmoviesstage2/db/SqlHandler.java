package com.example.blancomm.popularmoviesstage2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.blancomm.popularmoviesstage2.model.MoviesInfo;
import com.example.blancomm.popularmoviesstage2.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class SqlHandler {

    public SQLiteDatabase sqLiteDatabase;
    static DataBaseHelper dbHelper;
    private String imsiId = null;
    private Context context;
    private static String TAG = SqlHandler.class.getSimpleName();

    public SqlHandler(Context context) {
        dbHelper = DataBaseHelper.getInstance(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        this.sqLiteDatabase = dbHelper.getWritableDatabase();
        this.context = context;
    }

    /**
     * Method for execute a specific query in a DB. Insert a query string, and this is executed.
     *
     * @param query
     */
    public void executeQuery(String query) {
        try {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }

            sqLiteDatabase = dbHelper.getWritableDatabase();
            sqLiteDatabase.execSQL(query);
        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
    }

    /**
     * General method for all queries in the DB.This select a particular query, but isnt used in the
     * app because exist a specific query for each table.
     *
     * @param query
     * @return
     */
    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
            sqLiteDatabase = dbHelper.getWritableDatabase();
            c1 = sqLiteDatabase.rawQuery(query, null);

        } catch (Exception e) {
            System.out.println("DATABASE ERROR " + e);
        }
        return c1;
    }

    /**
     * Put a new POISInfo object in the table pois.
     *
     * @param moviesInfo
     */
    public void putFavorites(MoviesInfo moviesInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.e(TAG, moviesInfo.getTitle().toString());
        values.put(FavoritesDB.COLUMN_IDMOVIE, moviesInfo.getId());
        db.insert(FavoritesDB.TABLE_FAVORITES, null, values);
        db.close();
    }

    /**
     * Get all registers for the table POIsDB.
     *
     * @return
     */
    public static List<MoviesInfo> getAllFavorites() {

        List<MoviesInfo> movies = null;
        try {

            String selectQuery = "SELECT  * FROM " + FavoritesDB.TABLE_FAVORITES;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    movies = new ArrayList<>();
                    MoviesInfo moviesInfo = new MoviesInfo();
                    moviesInfo.setId(cursor.getString(0));

                    movies.add(moviesInfo);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return movies;
    }

    /*
     * Get the POisDetail info from his id.
     */
    public static MoviesInfo getMovie(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        MoviesInfo moviesInfo = null;

        Cursor cursor = db.query(FavoritesDB.TABLE_FAVORITES, new String[]{FavoritesDB.COLUMN_IDMOVIE,
                }, FavoritesDB.COLUMN_IDMOVIE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();

            moviesInfo = new MoviesInfo();

        }

        cursor.close();
        db.close();

        return moviesInfo;
    }

    /*
     * Verify if exist a register. This method is duplicate more late, but with differents params.
     * We can remove this method more later or remain here like a auxiliar method.
     */
    public static boolean verification(String _username, String TABLE_NAME, String KEY_USERNAME) throws SQLException {
        int count = -1;
        SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
        Cursor c = null;
        try {
            String query = "SELECT COUNT(*) FROM "
                    + TABLE_NAME + " WHERE " + KEY_USERNAME + " = ?";
            c = sqldb.rawQuery(query, new String[]{_username});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            return count > 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    /*
     * Verify if exist a item. With this method check all rows ofDB for find the item or not.
     */
    public static boolean checkidExitsorNot(String tablename, String rowname, String id) {
        String queryf = "select * from " + tablename + " where " + rowname + "='" + id + "'";
        SQLiteDatabase sqldb = dbHelper.getReadableDatabase();
        Cursor c = sqldb.rawQuery(queryf, null);
        if (c.getCount() == 0) {
            c.close();
            sqldb.close();
            return false;
        } else {
            c.close();
            sqldb.close();
            return true;
        }
    }

    /*
     * For verify if exist a table in the DB. Pass the name of the table and open this DB.
     */
    public boolean isTableExists(String tableName, boolean openDb) {
        if (openDb) {
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                sqLiteDatabase = dbHelper.getReadableDatabase();
            }

            if (!sqLiteDatabase.isReadOnly()) {
                sqLiteDatabase.close();
                sqLiteDatabase = dbHelper.getReadableDatabase();
            }
        }

        Cursor cursor = sqLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
}
