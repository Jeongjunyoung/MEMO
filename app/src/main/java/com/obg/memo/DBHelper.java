package com.obg.memo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d1jun on 2017-11-23.
 */

public class DBHelper{
    private static final String DATABASE_NAME = "oneline_memo.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase db;
    private DatabaseHelper mDBHelper;
    private Context mCtx;
    public DBHelper(){}
    private class DatabaseHelper extends SQLiteOpenHelper{


        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table oneline_memo(_id integer primary key AUTOINCREMENT, content text, date text, res_id integer)");
            sqLiteDatabase.execSQL("create table theme_color(_id integer, window text, actionbar text, background text, textsize text)");
            String sql = "insert into theme_color(_id, window, actionbar, background, textsize) values(?,?,?,?,?)";
            Object[] params = {1,"#9ca0a6", "#6d7073", "#d9dcdf", "25dp"};
            sqLiteDatabase.execSQL(sql, params);
        }

        /*@Override
        public void onOpen(SQLiteDatabase db) {
            String sql = "insert into theme_color(window, actionbar, background, textsize) values(?,?,?,?)";
            Object[] params = {"#9ca0a6", "#6d7073", "#d9dcdf", "25dp"};
            db.execSQL(sql, params);
            super.onOpen(db);
        }*/

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public DBHelper(Context context) {
        this.mCtx = context;
    }
    public DBHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        db = mDBHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getDataBaseHelper() {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        return mDBHelper.getWritableDatabase();
    }
    public void insert(String content, String date, int resId) {
        String sql = "insert into oneline_memo(content, date, res_id) values(?,?,?)";
        Object[] params = {content, date, resId};
        db.execSQL(sql, params);
        Log.d("OBG", "INSERT SUCCESS");
    }

    public void delete(int id) {
        String sql = "delete from oneline_memo where _id="+id;
        db.execSQL(sql);
    }

    public List<MemoItem> selectData(int sort) {
        List<MemoItem> list = new ArrayList<MemoItem>();
        MemoItem memoItem = null;
        String sql = "";
        if (sort == 0) {
            sql = "select _id, content, date, res_id  from oneline_memo order by date DESC";
        }else if (sort == 1){
            sql = "select _id, content, date, res_id  from oneline_memo order by res_id ASC";
        }
        Cursor cursor =  db.rawQuery(sql, null);
        for(int i = 0;i<cursor.getCount(); i++) {
            cursor.moveToNext();
            memoItem = new MemoItem();
            int _id = cursor.getInt(0);
            String content = cursor.getString(1);
            String date = cursor.getString(2);
            int resId = cursor.getInt(3);
            memoItem.set_id(cursor.getInt(0));
            memoItem.setContent(cursor.getString(1));
            memoItem.setDate(cursor.getString(2));
            memoItem.setResId(cursor.getInt(3));
            list.add(memoItem);
            //Log.d("OBG","#" + _id + " -> " + content + ", " + date + ", " + resId);
        }
        cursor.close();
        return list;
    }

    public void modifyData(String modifyStr, int _id) {
        String sql = "update oneline_memo set content = ? where _id = ?";
        Object[] params = {modifyStr, _id};
        db.execSQL(sql, params);
    }

    public void changeThemeColor(ThemeItems items) {
        String actionbar = items.getActionbar();
        String window = items.getWindow();
        String background = items.getBackground();
        Log.d("DB", "" +actionbar + " : " + window);
        String sql = "update theme_color set actionbar = ?, window = ?, background =? where _id = "+1;
        Object[] params = {actionbar, window, background};
        db.execSQL(sql, params);
    }
    public ThemeItems getThemeColor() {
        ThemeItems themeItems = null;
        String sql = "select window, actionbar, background, textsize from theme_color";
        Cursor cursor =  db.rawQuery(sql, null);
        for(int i = 0;i<cursor.getCount(); i++) {
            cursor.moveToNext();
            themeItems = new ThemeItems();
            themeItems.setWindow(cursor.getString(0));
            themeItems.setActionbar(cursor.getString(1));
            themeItems.setBackground(cursor.getString(2));
            themeItems.setTextsize(cursor.getString(3));
            //Log.d("OBG","#" + cursor.getString(0) + " -> " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3));
        }
        cursor.close();
        return themeItems;
    }
}
