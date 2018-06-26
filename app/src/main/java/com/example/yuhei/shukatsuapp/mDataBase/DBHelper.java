package com.example.yuhei.shukatsuapp.mDataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, ToDo.DB_NAME,null, ToDo.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(ToDo.CREATE_TB);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ToDo.DROP_TB);
        onCreate(db);

    }
}
