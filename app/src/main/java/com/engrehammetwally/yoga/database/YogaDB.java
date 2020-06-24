package com.engrehammetwally.yoga.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class YogaDB extends SQLiteAssetHelper {
    private final static String NAME = "yoga.db";
    private final static int VERSION = 1;

    public YogaDB(Context context) {
        super(context, NAME, null, VERSION);
    }

    public int getSettingMode() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlSelect={"Mode"};
        String sqlTable="Settings";

        qb.setTables(sqlTable);

        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);
        c.moveToFirst();
        return c.getInt(c.getColumnIndex("Mode"));

    }

    public void setSettingMode(int value){
        SQLiteDatabase db=getReadableDatabase();
        String query ="UPDATE Settings SET MODE = "+value;
        db.execSQL(query);
    }

    public List<String> getWorkOutDays() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlSelect={"Day"};
        String sqlTable="WorkOutDays";

        qb.setTables(sqlTable);

        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);
        List<String> daysList=new ArrayList<>();
        if (c.moveToFirst()){
            do {
                daysList.add(c.getString(c.getColumnIndex("Day")));

            }while (c.moveToNext());
        }
        return daysList;
    }

    public void setWorkOutDays(String value){
        SQLiteDatabase db=getReadableDatabase();
        String query =String.format("INSERT INTO  WorkOutDays(Day) VALUES('%s');",value);
        db.execSQL(query);
    }

    public void updateWorkOutTime(String value){
        SQLiteDatabase db=getReadableDatabase();
        String query ="UPDATE WorkOutDays SET TIME = "+value;
        db.execSQL(query);
    }

    public String getWorkOutTime() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlSelect={"Time"};
        String sqlTable="WorkOutDays";

        qb.setTables(sqlTable);

        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);
        c.moveToFirst();

        return c.getString(c.getColumnIndex("Time"));
    }

    public void setWorkOutTime(String value){
        SQLiteDatabase db=getReadableDatabase();
        String query =String.format("INSERT INTO  WorkOutDays(Time) VALUES('%s');",value);
        db.execSQL(query);
    }
}
