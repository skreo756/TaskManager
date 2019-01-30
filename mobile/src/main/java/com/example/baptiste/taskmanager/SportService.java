package com.example.baptiste.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class SportService extends Service {

    @Override
    public IBinder onBind(Intent intent) {

        Log.w("SportService", "SportService lancé");
        DataClient dataClient = Wearable.getDataClient(this);

        PutDataMapRequest dataMapRequest = PutDataMapRequest.create("/notification");
        dataMapRequest.getDataMap().putDouble("timestamp", System.currentTimeMillis());
        dataMapRequest.getDataMap().putString("title", "title : testtttttttt");
        dataMapRequest.getDataMap().putString("content", "description");
        PutDataRequest putDataRequest = dataMapRequest.asPutDataRequest();

        dataClient.putDataItem(putDataRequest);
        return null;
    }
}
