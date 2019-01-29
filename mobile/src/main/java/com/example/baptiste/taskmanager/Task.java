package com.example.baptiste.taskmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Task {

    String title;
    String type;
    String description;
    String DateDebut;
    Long id = null;
    PendingIntent i;

    Context context = null;

    String TAG = "TASSKKKKK";

    private static final String CHANNEL_ID = "CHANNEL_ID";



    public Task(String t, String ty, String desc, String db,Long idd, Context c) {
        title = t;
        type = ty;
        description = desc;
        DateDebut = db;
        id = idd;
        context = c;
    }

    public void createNotification(Calendar c) {
        createNotificationChannel();


        Intent i = new Intent(context, DisplayTaskActivity.class);
        i.putExtra(MainActivity.ID_DISPLAY_TASK,  Integer.parseInt(id+""));
        Log.w(TAG, id.toString());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent snoozeIntent = new Intent(context, SnoozeReceiver.class);
        snoozeIntent.putExtra(SnoozeService.ID_TASK, id);
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, 1, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_snooze, context.getString(R.string.snooze), snoozePendingIntent).build();

        if (type.equals("Sport")) {
            Intent SportIntent = new Intent(context, SportActivity.class);
            PendingIntent sportPendingIntent = PendingIntent.getActivity(context, 1, SportIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Action SportAction = new NotificationCompat.Action.Builder(R.drawable.ic_rowing, context.getString(R.string.sport), sportPendingIntent).build();

            Log.w(TAG, c.toString());
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(type + description)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.notification_icon)
                    .addAction(action)
                    .addAction(SportAction);
            triggerAlarmManager(builder.build(), c.getTimeInMillis());
            Intent notificationIntent = new Intent(context, MainActivity.class);
            context.startActivity(notificationIntent);
        }

        else {
            Log.w(TAG, c.toString());
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(type + description)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.notification_icon)
                    .addAction(action);
                   // .addAction(SportAction);
            triggerAlarmManager(builder.build(), c.getTimeInMillis());
            Intent notificationIntent = new Intent(context, MainActivity.class);
            context.startActivity(notificationIntent);
        }
    }

    public void triggerAlarmManager(Notification notification, long alarmTriggerTime) {


        Intent notificationIntent = new Intent(context, AlarmReceiver.class);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 0);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, alarmTriggerTime, pendingIntent);
    }

    private void createNotificationChannel() {
        // Créer le NotificationChannel, seulement pour API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Notification channel description");
            // Enregister le canal sur le système : attention de ne plus rien modifier après
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }
    }


}


