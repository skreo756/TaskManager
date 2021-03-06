package com.example.baptiste.taskmanager;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.baptiste.taskmanager.db.TaskDbHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;


public class CreateActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final int ALARM_REQUEST_CODE = 133;


    private static final String REPLY_ACTION = "REPLY_ACTION";
    private static final String KEY_NOTIFICATION_ID = "KEY_NOTIFICATION_ID";
    private static final String KEY_REPLY = "KEY_REPLY";
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private int notificationId = 1500;
    private Intent in;


    Button btnDatePicker, btnTimePicker, btnAdd;
    EditText txtDate, txtTime, Title, Description;
    Spinner timeBefore, Type;
    private int mYear, mMonth, mDay;
    private TaskDbHelper mHelper;

    private String TAG = "CreateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        in = new Intent(this, MainActivity.class);


        btnTimePicker = (Button) findViewById(R.id.btn_Time);
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnAdd = (Button) findViewById(R.id.eventCreationValidate);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);
        Title = (EditText) findViewById(R.id.eventCreationName);
        Description = (EditText) findViewById(R.id.eventCreationDescription);
        timeBefore = (Spinner) findViewById(R.id.timeBefore);
        Type = (Spinner) findViewById(R.id.type);



        btnAdd.setOnClickListener(this);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        mHelper = new TaskDbHelper(this);


    }


    public void onClick(View v) {
        Log.d(TAG, "salut");

        if (v == btnTimePicker) {
            Log.d(TAG, "TimePicker");

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, hour, minute, false);
            timePickerDialog.show();
        }


        if (v == btnDatePicker) {
            Log.d(TAG, "DatePicker");
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }


        if (v == btnAdd) {
            String title = String.valueOf(Title.getText());
            String description = String.valueOf(Description.getText());
            String type = Type.getSelectedItem().toString();
            String before = timeBefore.getSelectedItem().toString();
            String start = String.valueOf(txtDate.getText());
            String time_ = String.valueOf(txtTime.getText());
            String datetime_ = start + "  "+ time_;

          //  datetime_ = datetime_.replace("-", "/");

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
            Calendar c3 = new GregorianCalendar();

            c2.setTime(dd);
            c.setTime(d);
            c3.setTime(d);


            c.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
            c.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));

            c3.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
            c3.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));

            int mn = 0;

            try {
                mn = Integer.parseInt(before);
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            mn = - mn;

            c.add(Calendar.MINUTE, mn);


            long insertId = mHelper.InsertTask(title, datetime_, type, description);

            Task t = mHelper.getTaskById(insertId);

            t.createNotification(c);


        }
    }









}
