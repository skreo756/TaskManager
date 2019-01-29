package com.example.baptiste.taskmanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.baptiste.taskmanager.Task;

import java.util.ArrayList;

public class TaskDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "task";
    public static final String DB_NAME = "Task.db";
    public static final String TASK_ID = "id";
    public static final String TASK_TITLE = "title";
    public static final String TASK_DATE = "start";
    public static final String TASK_TYPE = "type";
    public static final String TASK_DESC = "description";
    public static final int DB_VERSION = 1;
    public static final String TAG = "DBBBBBBB";
    public Context context = null;

    public TaskDbHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" ( " +
                TASK_ID + " integer primary key autoincrement, " +
                TASK_TITLE + " text not null, " +
                TASK_TYPE + " text not null, " +
                TASK_DESC + " text not null, " +
                TASK_DATE + " text not null); "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS task");
        onCreate(db);
    }

    public long InsertTask(String title, String start, String type, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title", title);
        contentValues.put("type", type);
        contentValues.put("description", description);
        contentValues.put("start", start);
        long idddd = db.insert("task", null, contentValues);
        return idddd;
    }


    public ArrayList<String> getAllTasks() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from task", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString((res.getColumnIndex(TASK_TITLE))) + "\n"+(res.getString(res.getColumnIndex(TASK_DATE))));

            res.moveToNext();
        }
        return array_list;
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from task where id ="+id+"", null);
        return res;
    }


    public Task getTaskById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from task where id ="+id+"", null);
        res.moveToFirst();
        String title = res.getString(res.getColumnIndex(TASK_TITLE));
        String type = res.getString(res.getColumnIndex(TASK_TYPE));
        String dateDebut = res.getString(res.getColumnIndex(TASK_DATE));
        String description = res.getString(res.getColumnIndex(TASK_DESC));

        return new Task(title, type, description, dateDebut, id, context);

    }
}
