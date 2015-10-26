package com.example.blancomm.popularmoviesstage2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ImageButton;

import com.example.blancomm.popularmoviesstage2.model.FavoriteInfo;
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
     * @param favoriteInfo
     */
    public void putFavorites(FavoriteInfo favoriteInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FavoritesDB.COLUMN_IDMOVIE, favoriteInfo.getIdMovie());
        values.put(FavoritesDB.COLUMN_TITLE, favoriteInfo.getTitle());
        values.put(FavoritesDB.COLUMN_ISCHECKED, favoriteInfo.getIsChecked());
        db.insert(FavoritesDB.TABLE_FAVORITES, null, values);
        db.close();
    }

    public void deleteFavorite(FavoriteInfo favoriteInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoritesDB.COLUMN_IDMOVIE, favoriteInfo.getIdMovie());
        values.put(FavoritesDB.COLUMN_TITLE, favoriteInfo.getTitle());
        values.put(FavoritesDB.COLUMN_ISCHECKED, favoriteInfo.getIsChecked());
        db.delete(FavoritesDB.TABLE_FAVORITES, FavoritesDB.COLUMN_IDMOVIE + " = ?",
                new String[]{String.valueOf(favoriteInfo.getIdMovie())});
        db.close();

    }

    /**
     * Get all registers for the table POIsDB.
     *
     * @return
     */
    public static List<FavoriteInfo> getAllFavorites() {

        List<FavoriteInfo> favorites = null;
        try {

            String selectQuery = "SELECT  * FROM " + FavoritesDB.TABLE_FAVORITES;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    favorites = new ArrayList<>();
                    FavoriteInfo favoriteInfo = new FavoriteInfo(cursor.getString(1), cursor.getString(2), cursor.getInt(3));
                    favorites.add(favoriteInfo);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return favorites;
    }

    /*
     * Get the Favorite info from his id.
     */
    public static FavoriteInfo getFavorite(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        FavoriteInfo favoriteInfo = null;

        Cursor cursor = db.query(FavoritesDB.TABLE_FAVORITES, new String[]{FavoritesDB.COLUMN_IDMOVIE,
                }, FavoritesDB.COLUMN_IDMOVIE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();

            favoriteInfo = new FavoriteInfo(cursor.getString(1), cursor.getString(2), cursor.getInt(3));

        }

        cursor.close();
        db.close();

        return favoriteInfo;
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
  * Save the favorites items, with a object FavoriteInfo.
  * If exist is save, in other, is update.
 */
    public static void saveFavoriteObject(FavoriteInfo item, SqlHandler sqlHandler) {

        FavoriteInfo favorite;
        favorite = item;

        boolean existe = SqlHandler.checkidExitsorNot(FavoritesDB.TABLE_FAVORITES, FavoritesDB.COLUMN_IDMOVIE, item.getIdMovie());

        if (!existe) {

            sqlHandler.putFavorites(favorite);

        } else {

            sqlHandler.deleteFavorite(favorite);
        }

        sqlHandler.closeDDBB();
    }

    public void closeDDBB() {
        sqLiteDatabase.close();
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
