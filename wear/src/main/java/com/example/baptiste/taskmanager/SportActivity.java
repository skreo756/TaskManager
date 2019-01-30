package com.example.baptiste.taskmanager;

import
        android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class SportActivity extends WearableActivity implements SensorEventListener, StepListener {


    private TextView textView, TvSteps;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Nombre de pas : ";
    private int numSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

         TvSteps = (TextView) findViewById(R.id.tv_steps);
        Button BtnStart = (Button) findViewById(R.id.btn_start);
        Button BtnStop = (Button) findViewById(R.id.btn_stop);



        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(SportActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });

        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(SportActivity.this);

            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }
}

