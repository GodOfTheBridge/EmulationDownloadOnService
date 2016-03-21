package com.gotb.emulationdownloadonservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import static android.R.drawable.stat_sys_download;

public class NotificationService extends Service {

    public int NOTIFICATION_ID = 111;
    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showNotification();

        Intent receiveDataIntent = new Intent("receiveDataIntent");
        receiveDataIntent.putExtra(MainActivity.COMPLETE_DOWNLOAD, "Download complete");
        sendBroadcast(receiveDataIntent);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder
                .setContentIntent(pendingIntent)
                .setSmallIcon(stat_sys_download)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setTicker("Start download")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Download");

        for (int progress = 0; progress < 100; progress++) {
            SystemClock.sleep(50);
            builder
                    .setContentText(progress + "%")
                    .setProgress(100, progress, false);
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
        builder
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentText("Download complete")
                        .setProgress(0, 0, false);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}



