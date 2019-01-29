package com.example.baptiste.taskmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.baptiste.taskmanager.db.TaskDbHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SnoozeReceiver extends BroadcastReceiver {
    public static final String TAG = "SnoozeService";
    public static final String ID_TASK = "ID_TASK";
    @Override
    public void onReceive(Context context, Intent intent) {
        long idTask =  intent.getExtras().getLong(ID_TASK);
        Task t = new TaskDbHelper(context).getTaskById(idTask);

        Calendar c = new GregorianCalendar();
        Calendar c2 = new GregorianCalendar();
      //  c2.setTime(t.DateDebut)
        c.add(Calendar.MINUTE, 10);

        t.createNotification(c);
    }
}
