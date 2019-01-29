package com.example.baptiste.taskmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Task {

    String title;
    String type;
    String description;
    String DateDebut;
    Long id = null;
    PendingIntent i;

    Context context = null;

    String TAG = "TASSKKKKK";



    public Task(String t, String ty, String desc, String db,Long idd, Context c) {
        title = t;
        type = ty;
        description = desc;
        DateDebut = db;
        id = idd;
        context = c;
    }

    public void createNotification(String before, String start, String time_) {

        Date d = new Date();
        Date dd = new Date();



        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dff = new SimpleDateFormat("HH:mm");
        try {
            d = df.parse(start);
            dd = dff.parse(time_);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = new GregorianCalendar();
        Calendar c2 = new GregorianCalendar();

        c2.setTime(dd);
        c.setTime(d);

        c.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));

        int mn = 0;

        try {
            mn = Integer.parseInt(before);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        mn = - mn;

        c.add(Calendar.MINUTE, mn);

        Intent snoozeIntent = new Intent(context, SnoozeService.class);
        //  snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(SnoozeService.ID_TASK, id);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(description);
        Intent i = new Intent(context, DisplayTaskActivity.class);
        i.putExtra(MainActivity.ID_DISPLAY_TASK, id);
      //  Log.w(TAG, id.toString());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.addAction(R.drawable.ic_snooze, context.getString(R.string.snooze),
                snoozePendingIntent);


        triggerAlarmManager(builder.build(), c.getTimeInMillis());

        Intent notificationIntent = new Intent(context, MainActivity.class);
        context.startActivity(notificationIntent);
    }

    public void triggerAlarmManager(Notification notification, long alarmTriggerTime) {


        Intent notificationIntent = new Intent(context, AlarmReceiver.class);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 0);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, alarmTriggerTime, pendingIntent);

        //  Toast.makeText(this, "Alarm Set for " + alarmTriggerTime + " seconds.", Toast.LENGTH_SHORT).show();


    }


}


