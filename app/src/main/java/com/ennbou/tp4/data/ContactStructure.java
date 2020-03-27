package com.ennbou.tp4.data;

import android.provider.BaseColumns;

public final class ContactStructure implements BaseColumns {

    public static final String TABLE_NAME = "contacts";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_JOB = "job";
    public static final String COLUMN_EMAIL = "email";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " " +
            "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            COLUMN_FIRST_NAME + " VARCHAR2(40) NOT NULL ," +
            COLUMN_LAST_NAME + " VARCHAR2(40) ," +
            COLUMN_JOB + " VARCHAR2(60)," +
            COLUMN_PHONE_NUMBER + " VARCHAR2(40) NOT NULL," +
            COLUMN_EMAIL + " VARCHAR2(60)" +
            ")";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
