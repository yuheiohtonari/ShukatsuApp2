package com.example.yuhei.shukatsuapp.mDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.SyncStateContract;

public class DBAdapter  {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }

    public void  openDB(){

        try
        {
            db = helper.getWritableDatabase();
        }catch (SQLException e){ }

    }

    public void closeDB(){
        try{
            helper.close();
        }catch (SQLException e){ }
    }

    public boolean add(String name){
        try{
            ContentValues cv = new ContentValues();
            cv.put(ToDo.NAME, name);

            long result = db.insert(ToDo.TB_NAME, ToDo.ROW_ID,cv);
            if (result>0){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public Cursor retrieve(){
        String[] colums = {ToDo.ROW_ID, ToDo.NAME};

        Cursor c = db.query(ToDo.TB_NAME,colums,null,null,null,null,null);
        return c;
    }
    public boolean update(String newName,int id)
    {
        try
        {
            ContentValues cv=new ContentValues();
            cv.put(ToDo.NAME,newName);


            int result=db.update(ToDo.TB_NAME,cv, ToDo.ROW_ID + " =?", new String[]{String.valueOf(id)});
            if(result>0)
            {
                return true;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;

    }
    public boolean delete(int id)
    {
        try
        {
            int result=db.delete(ToDo.TB_NAME,ToDo.ROW_ID+" =?",new String[]{String.valueOf(id)});
            if(result>0)
            {
                return true;
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }




}

