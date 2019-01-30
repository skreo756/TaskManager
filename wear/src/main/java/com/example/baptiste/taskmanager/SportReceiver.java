package com.example.baptiste.taskmanager;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class SportReceiver extends Service  implements DataClient.OnDataChangedListener,
        MessageClient.OnMessageReceivedListener,
        CapabilityClient.OnCapabilityChangedListener

{
    public static final String TAG = "SportReceiver";

    @Override
    public IBinder onBind(Intent intent) {
        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(this);
        Wearable.getCapabilityClient(this).addListener(this, Uri.parse("wear://"),
                CapabilityClient.FILTER_REACHABLE);

        return null;
    }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {

    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        Log.w(TAG, "MESSAGE RECU");
        Intent sportIntent = new Intent(this,SportActivity.class);
        sportIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sportIntent);

    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        Intent sportIntent = new Intent(getApplicationContext(),SportActivity.class);
        startActivity(sportIntent);
    }
}
