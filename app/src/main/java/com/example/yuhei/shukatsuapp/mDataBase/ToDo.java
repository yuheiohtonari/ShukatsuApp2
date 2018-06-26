package com.example.yuhei.shukatsuapp.mDataBase;

public class ToDo {

    static final String ROW_ID = "id";
    static final String NAME = "name";

    static final String DB_NAME = "hh_DB";
    static final String TB_NAME = "hh_TB";
    static final int DB_VERSION = 1;

    static final String CREATE_TB = "CREATE TABLE hh_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL);";

    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;

}
