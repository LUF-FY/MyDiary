package com.mugivara.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBRecords {
    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tableRecords";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_TEXT = "Text";
    private static final String COLUMN_DATE = "Date";


    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_TITLE = 1;
    private static final int NUM_COLUMN_TEXT = 2;
    private static final int NUM_COLUMN_DATE = 3;

    private SQLiteDatabase mDataBase;

    public DBRecords(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String title,String text, long date) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_TEXT, text);
        cv.put(COLUMN_DATE, date);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Record record) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TITLE, record.getTitle());
        cv.put(COLUMN_TEXT, record.getText());
        cv.put(COLUMN_DATE, record.getCalendarInMillis());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(record.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Record select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String title = mCursor.getString(NUM_COLUMN_TITLE);
        String text = mCursor.getString(NUM_COLUMN_TEXT);
        long date = mCursor.getLong(NUM_COLUMN_DATE);
        //mCursor.close();
        return new Record(id, title, text, date);
    }

    public ArrayList<Record> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Record> arr = new ArrayList<Record>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String title = mCursor.getString(NUM_COLUMN_TITLE);
                String text = mCursor.getString(NUM_COLUMN_TEXT);
                long date = mCursor.getLong(NUM_COLUMN_DATE);
                arr.add(new Record(id, title, text, date));
            } while (mCursor.moveToNext());
        }
        //mCursor.close();
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_TEXT + " TEXT, " +
                    COLUMN_DATE + " LONG); ";
            db.execSQL(query);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}

