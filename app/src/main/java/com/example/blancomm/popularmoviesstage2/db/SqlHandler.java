package com.example.blancomm.popularmoviesstage2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.blancomm.touristictest.Utils.Constant;
import com.example.blancomm.touristictest.model.POIsDetailInfo;
import com.example.blancomm.touristictest.model.POIsInfo;

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
     * @param pois
     */
    public void putPOIs(POIsInfo pois) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        Log.e(TAG,pois.getmTitle().toString());
        values.put(POIsDB.COLUMN_IDPOINT, pois.getmId());
        values.put(POIsDB.COLUMN_TITLE, pois.getmTitle());
        values.put(POIsDB.COLUMN_GEOPOINT, pois.getmGeopoint());
        db.insert(POIsDB.TABLE_POIS, null, values);
        db.close();
    }

    /**
     * Put a POIs Detail object in the table POISDetail.
     *
     * @param poIsDetail
     */
    public void putPOIsDetail(POIsDetailInfo poIsDetail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POIsDetailDB.COLUMN_IDPOINT, poIsDetail.getmId());
        values.put(POIsDetailDB.COLUMN_TITLE, poIsDetail.getmTitle());
        values.put(POIsDetailDB.COLUMN_ADDRESS, poIsDetail.getmAddress());
        values.put(POIsDetailDB.COLUMN_TRANSPORT, poIsDetail.getmTransport());
        values.put(POIsDetailDB.COLUMN_DESCRIPTION, poIsDetail.getmDescription());
        values.put(POIsDetailDB.COLUMN_EMAIL, poIsDetail.getmEmail());
        values.put(POIsDetailDB.COLUMN_PHONE, poIsDetail.getmPhone());
        values.put(POIsDetailDB.COLUMN_GEOPOINT, poIsDetail.getmGeopoint());
        db.insert(POIsDetailDB.TABLE_POIS_DETAIL, null, values);
        db.close();
    }

    /**
     * Get all registers for the table POIsDB.
     *
     * @return
     */
    public static List<POIsInfo> getAllPOIs() {

        List<POIsInfo> pois = null;
        try {

            String selectQuery = "SELECT  * FROM " + POIsDB.TABLE_POIS;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    pois = new ArrayList<>();
                    POIsInfo poisObject = new POIsInfo();
                    poisObject.setmId(cursor.getString(0));
                    poisObject.setmTitle(cursor.getString(1));
                    poisObject.setmGeopoint(cursor.getString(2));
                    pois.add(poisObject);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return pois;
    }

    /**
     * Get all registers for the table POIsDB.
     *
     * @return
     */
    public static ArrayList<POIsDetailInfo> getAllPOIsDetails() {

        ArrayList<POIsDetailInfo> pois = null;
        try {

            String selectQuery = "SELECT  * FROM " + POIsDetailDB.TABLE_POIS_DETAIL;

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    pois = new ArrayList<>();
                    POIsDetailInfo poisObject = new POIsDetailInfo();

                    poisObject.setmId(cursor.getString(0));
                    poisObject.setmTitle(cursor.getString(1));
                    poisObject.setmAddress(cursor.getString(2));
                    poisObject.setmTransport(cursor.getString(3));
                    poisObject.setmDescription(cursor.getString(4));
                    poisObject.setmEmail(cursor.getString(5));
                    poisObject.setmPhone(cursor.getString(6));
                    poisObject.setmGeopoint(cursor.getString(7));

                    pois.add(poisObject);

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

            Log.e(TAG, "Tamaño: " + pois.size());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return pois;
    }

    /*
     * Get the POisDetail info from his id.
     */
    public static POIsDetailInfo getPOIDetail(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        POIsDetailInfo poiDetail = null;

        Cursor cursor = db.query(POIsDetailDB.TABLE_POIS_DETAIL, new String[]{POIsDetailDB.COLUMN_IDPOINT,
                        POIsDetailDB.COLUMN_TITLE, POIsDetailDB.COLUMN_ADDRESS, POIsDetailDB.COLUMN_TRANSPORT, POIsDetailDB.COLUMN_DESCRIPTION, POIsDetailDB.COLUMN_EMAIL, POIsDetailDB.COLUMN_PHONE, POIsDetailDB.COLUMN_GEOPOINT}, POIsDetailDB.COLUMN_IDPOINT + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();

            poiDetail = new POIsDetailInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));

        }

        cursor.close();
        db.close();

        return poiDetail;
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
