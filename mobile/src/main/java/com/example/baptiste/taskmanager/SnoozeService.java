package com.example.baptiste.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.baptiste.taskmanager.db.TaskDbHelper;

public class SnoozeService extends Service {

    public static final String ID_TASK = "ID_TASK";
   // @androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
       long idTask =  intent.getExtras().getLong(ID_TASK);
        new TaskDbHelper(getApplicationContext()).getTaskById(idTask);



        return null;


    }
}
