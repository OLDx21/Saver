package com.example.saver;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class dbhelp extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "saversec";
   public static final String TABLE_NAME = "save";
   public static final int VERSION = 1;

   public static final  String NAME_COLUMN = "name";
   public static final  String MAIL_COLUMN = "mail";
   public static final  String SEC_COLUMN = "pas";

    public dbhelp(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + NAME_COLUMN + " text," + MAIL_COLUMN + " text,"+SEC_COLUMN+" text"+")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("drop table if exists " + TABLE_NAME);
onCreate(db);
    }
}
