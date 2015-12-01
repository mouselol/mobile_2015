package com.example.admin.todosoft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;


import com.example.admin.todosoft.sqliteasset.SQLiteAssetHelper;


public class TodoDbHelper extends SQLiteAssetHelper {

    enum where_clause {
        ACTIVE,DONE,ALL
    }

    public static final String TABLE_NAME = "task";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STATUS = "status";

    private SQLiteDatabase myDataBase;

    private static final String DATABASE_NAME = "todo_list.sqlite";
    private static final int DATABASE_VERSION = 1;


    public TodoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long addTask(TodoTask task){

        if(isExist(task.description))return 0;

        SQLiteDatabase db = getWritableDatabase();
        long newRowId;

        ContentValues values = new ContentValues();
            values.put(COLUMN_DATE, task.date);
            values.put(COLUMN_TASK,task.description);
            values.put(COLUMN_STATUS,"0");

        newRowId = db.insert(TABLE_NAME, null, values);

        return newRowId;
    }

    public Cursor fetchAll(where_clause where) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String whereClause = " WHERE " + COLUMN_STATUS + "=";
        switch (where){
            case ACTIVE:whereClause += "0";break;
            case DONE:whereClause += "1";break;
            case ALL:whereClause = null;break;

        }

        //Cursor c = qb.query(db, sqlSelect, whereClause, null, null, null, null);
        Cursor c = db.rawQuery("SELECT date, task, id FROM " + TABLE_NAME + whereClause, null);
        //c.moveToFirst();
        return c;

    }



    public boolean isExist(String description){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement s = db.compileStatement( "select count(*) from "+TABLE_NAME+" where task='" + description + "'; " );
        long count = s.simpleQueryForLong();
        if(count > 0)return true;
        return false;
    }

    public int moveFromActiveToDone(long id){
        SQLiteDatabase db = getReadableDatabase();
        //db.rawQuery("UPDATE "+TABLE_NAME+" SET status=1 WHERE id="+id, null);
        ContentValues cv = new ContentValues();
        cv.put("status", "1");
        return db.update(TABLE_NAME, cv, "id "+"="+id, null);
    }

    public int removeFromDone(long id){
        SQLiteDatabase db = getReadableDatabase();
        db.rawQuery("DELETE FROM "+TABLE_NAME+" WHERE id="+id, null);
        int r = db.delete(TABLE_NAME,"id = " + id,null);
        return r;
    }


}
