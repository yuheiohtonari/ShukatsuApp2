package com.example.yuhei.shukatsuapp.memoDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemoDatabaseAccess {


    private SQLiteDatabase database;
    private MemoDatabaseOpenHelper openHelper;
    private static volatile MemoDatabaseAccess instance;

    private MemoDatabaseAccess(Context context) {
        this.openHelper = new MemoDatabaseOpenHelper(context);
    }

    public static synchronized MemoDatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new MemoDatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void save(HelpMemo memo) {
        ContentValues values = new ContentValues();
        values.put("date", memo.getTime());
        values.put("memo", memo.getText());
        database.insert(MemoDatabaseOpenHelper.TABLE, null, values);
    }

    public void update(HelpMemo memo) {
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        values.put("memo", memo.getText());
        String date = Long.toString(memo.getTime());
        database.update(MemoDatabaseOpenHelper.TABLE, values, "date = ?", new String[]{date});
    }

    public void delete(HelpMemo memo) {
        String date = Long.toString(memo.getTime());
        database.delete(MemoDatabaseOpenHelper.TABLE, "date = ?", new String[]{date});
    }

    public List getAllMemos() {
        List memos = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From memo ORDER BY date DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long time = cursor.getLong(0);
            String text = cursor.getString(1);
            memos.add(new HelpMemo(time, text));
            cursor.moveToNext();
        }
        cursor.close();
        return memos;
    }
}

