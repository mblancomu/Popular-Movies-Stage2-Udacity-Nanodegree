package com.example.blancomm.popularmoviesstage2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageButton;

import com.example.blancomm.popularmoviesstage2.R;
import com.example.blancomm.popularmoviesstage2.model.FavoriteInfo;
import com.example.blancomm.popularmoviesstage2.model.MovieDetailInfo;
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
     * Put a new MovieInfo object in the table favorites.
     *
     * @param moviesInfo
     */
    public void putFavorites(MoviesInfo moviesInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoritesDB.COLUMN_IDMOVIE, moviesInfo.getId());
        values.put(FavoritesDB.COLUMN_TITLE, moviesInfo.getTitle());
        values.put(FavoritesDB.COLUMN_ADULT, moviesInfo.getAdult());
        values.put(FavoritesDB.COLUMN_IMAGE, moviesInfo.getImageDetail());
        values.put(FavoritesDB.COLUMN_GENRES, moviesInfo.getGenreIds());
        values.put(FavoritesDB.COLUMN_ORLANG, moviesInfo.getOriginalLanguage());
        values.put(FavoritesDB.COLUMN_ORTITLE, moviesInfo.getOriginalTitle());
        values.put(FavoritesDB.COLUMN_DESC, moviesInfo.getDescription());
        values.put(FavoritesDB.COLUMN_DATE, moviesInfo.getReleaseDate());
        values.put(FavoritesDB.COLUMN_THUMB, moviesInfo.getThumnail());
        values.put(FavoritesDB.COLUMN_POPULAR, moviesInfo.getPopularity());
        values.put(FavoritesDB.COLUMN_VIDEO, moviesInfo.getVideo());
        values.put(FavoritesDB.COLUMN_AVERAGE, moviesInfo.getVoteAverage());
        values.put(FavoritesDB.COLUMN_COUNT, moviesInfo.getVoteCount());
        db.insert(FavoritesDB.TABLE_FAVORITES, null, values);
        db.close();
    }

    public void deleteFavorite(MoviesInfo moviesInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoritesDB.COLUMN_IDMOVIE, moviesInfo.getId());
        values.put(FavoritesDB.COLUMN_TITLE, moviesInfo.getTitle());
        values.put(FavoritesDB.COLUMN_ADULT, moviesInfo.getAdult());
        values.put(FavoritesDB.COLUMN_IMAGE, moviesInfo.getImageDetail());
        values.put(FavoritesDB.COLUMN_GENRES, moviesInfo.getGenreIds());
        values.put(FavoritesDB.COLUMN_ORLANG, moviesInfo.getOriginalLanguage());
        values.put(FavoritesDB.COLUMN_ORTITLE, moviesInfo.getOriginalTitle());
        values.put(FavoritesDB.COLUMN_DESC, moviesInfo.getDescription());
        values.put(FavoritesDB.COLUMN_DATE, moviesInfo.getReleaseDate());
        values.put(FavoritesDB.COLUMN_THUMB, moviesInfo.getThumnail());
        values.put(FavoritesDB.COLUMN_POPULAR, moviesInfo.getPopularity());
        values.put(FavoritesDB.COLUMN_VIDEO, moviesInfo.getVideo());
        values.put(FavoritesDB.COLUMN_AVERAGE, moviesInfo.getVoteAverage());
        values.put(FavoritesDB.COLUMN_COUNT, moviesInfo.getVoteCount());
        db.delete(FavoritesDB.TABLE_FAVORITES, FavoritesDB.COLUMN_IDMOVIE + " = ?",
                new String[]{String.valueOf(moviesInfo.getId())});
        db.close();

    }

    /**
     * Get all registers for the table Favorites.
     *
     * @return
     */
    public static List<MoviesInfo> getAllFavorites() {

        List<MoviesInfo> favorites = null;
        try {

            String selectQuery = "SELECT  * FROM " + FavoritesDB.TABLE_FAVORITES;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    favorites = new ArrayList<>();
                    MoviesInfo favoritesMovies = new MoviesInfo();
                    favoritesMovies.setId(cursor.getString(1));
                    favoritesMovies.setTitle(cursor.getString(2));
                    favoritesMovies.setAdult(cursor.getString(3));
                    favoritesMovies.setImageDetail(cursor.getString(4));
                    favoritesMovies.setGenreIds(cursor.getString(5));
                    favoritesMovies.setOriginalLanguage(cursor.getString(6));
                    favoritesMovies.setOriginalTitle(cursor.getString(7));
                    favoritesMovies.setDescription(cursor.getString(8));
                    favoritesMovies.setReleaseDate(cursor.getString(9));
                    favoritesMovies.setThumnail(cursor.getString(10));
                    favoritesMovies.setPopularity(cursor.getString(11));
                    favoritesMovies.setVideo(cursor.getString(12));
                    favoritesMovies.setVoteAverage(cursor.getString(13));
                    favoritesMovies.setVoteCount(cursor.getString(14));
                    favorites.add(favoritesMovies);

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
    public static MoviesInfo getFavorite(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        MoviesInfo favoritesMovies = null;

        Cursor cursor = db.query(FavoritesDB.TABLE_FAVORITES, new String[]{FavoritesDB.COLUMN_IDMOVIE,
                }, FavoritesDB.COLUMN_IDMOVIE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();

            favoritesMovies = new MoviesInfo();
            favoritesMovies.setId(cursor.getString(1));
            favoritesMovies.setTitle(cursor.getString(2));
            favoritesMovies.setAdult(cursor.getString(3));
            favoritesMovies.setImageDetail(cursor.getString(4));
            favoritesMovies.setGenreIds(cursor.getString(5));
            favoritesMovies.setOriginalLanguage(cursor.getString(6));
            favoritesMovies.setOriginalTitle(cursor.getString(7));
            favoritesMovies.setDescription(cursor.getString(8));
            favoritesMovies.setReleaseDate(cursor.getString(9));
            favoritesMovies.setThumnail(cursor.getString(10));
            favoritesMovies.setPopularity(cursor.getString(11));
            favoritesMovies.setVideo(cursor.getString(12));
            favoritesMovies.setVoteAverage(cursor.getString(13));
            favoritesMovies.setVoteCount(cursor.getString(14));

        }

        cursor.close();
        db.close();

        return favoritesMovies;
    }

    public static MoviesInfo saveFavorite(MovieDetailInfo movieDetail){

        //Save object in DB when click on favorite button.
        MoviesInfo favoritesMovies = new MoviesInfo();
        favoritesMovies.setId(movieDetail.getId());
        favoritesMovies.setTitle(movieDetail.getTitle());
        favoritesMovies.setAdult(movieDetail.getAdult());
        favoritesMovies.setImageDetail(movieDetail.getImageDetail());
        favoritesMovies.setGenreIds(movieDetail.getGenreIds());
        favoritesMovies.setOriginalLanguage(movieDetail.getOriginalLanguage());
        favoritesMovies.setOriginalTitle(movieDetail.getOriginalTitle());
        favoritesMovies.setDescription(movieDetail.getDescription());
        favoritesMovies.setReleaseDate(movieDetail.getReleaseDate());
        favoritesMovies.setThumnail(movieDetail.getThumnail());
        favoritesMovies.setPopularity(movieDetail.getPopularity());
        favoritesMovies.setVideo(movieDetail.getVideo());
        favoritesMovies.setVoteAverage(movieDetail.getVoteAverage());
        favoritesMovies.setVoteCount(movieDetail.getVoteCount());

        return favoritesMovies;
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
    public static void saveFavoriteObject(MoviesInfo item, SqlHandler sqlHandler, FloatingActionButton fab) {

        MoviesInfo favorite;
        favorite = item;

        boolean existe = SqlHandler.checkidExitsorNot(FavoritesDB.TABLE_FAVORITES, FavoritesDB.COLUMN_IDMOVIE, item.getId());

        if (!existe) {

            sqlHandler.putFavorites(favorite);
            fab.setImageResource(R.drawable.ic_favorite_select);

        } else {

            sqlHandler.deleteFavorite(favorite);
            fab.setImageResource(R.drawable.ic_favorite_normal);
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
