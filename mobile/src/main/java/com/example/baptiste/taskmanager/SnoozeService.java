package com.example.baptiste.taskmanager;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.baptiste.taskmanager.db.TaskDbHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SnoozeService extends Service {

    public static final String TAG = "SnoozeService";
    public static final String ID_TASK = "ID_TASK";
    @Override
    public IBinder onBind(Intent intent) {
        Log.w(TAG,"SALFEZFZEJFEIZFHZUIFZFHEZFHIZE");
       long idTask =  intent.getExtras().getLong(ID_TASK);
       Log.w(TAG, idTask+"");
        Task t = new TaskDbHelper(getApplicationContext()).getTaskById(idTask);

        Calendar c = new GregorianCalendar();
        c.add(Calendar.MINUTE, 10);
        t.createNotification(c);
        return null;


    }


}
