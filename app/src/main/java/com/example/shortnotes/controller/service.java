package com.example.shortnotes.controller;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.example.shortnotes.R;
import com.example.shortnotes.ui.MainActivity;
import com.example.shortnotes.ui.app;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


/*
this service is just for testing some thing not related to this app or
there is no something special with this service for this app
 */

public class service extends IntentService {
    public static final String Extra = "massage";
    String massage;


    public service() {
        super("delayed_service");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        massage = intent.getStringExtra(Extra);
        synchronized (this) {
            try {
                wait(10000);
            } catch (InterruptedException e) {
                Log.e("handler", Objects.requireNonNull(e.getMessage()));
            }

        }

        creat_notification();


    }

    private void creat_notification() {

        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), app.CHANNEL_ID);
        builder.setSmallIcon(R.drawable.favorites_24dp).setContentTitle("from service after waiting 10 second").setContentText(massage);
        builder.setVibrate(new long[]{0, 1000});
        builder.setContentIntent(actionPendingIntent).setAutoCancel(true);
        NotificationManagerCompat nmc = NotificationManagerCompat.from(getApplicationContext());
        nmc.notify(2, builder.build());


    }
}
