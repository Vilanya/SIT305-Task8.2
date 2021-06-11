package com.example.personalassistantapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
//import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.personalassistantapp.data.DatabaseHelper;
import com.example.personalassistantapp.model.Event;

public class AlarmBroadcast extends BroadcastReceiver {

    DatabaseHelper db;

    private int notifId = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
//        Bundle bundle = intent.getExtras();
        Bundle bundle = intent.getExtras();
        String text = bundle.getString("event");
        String date = bundle.getString("date") + " " + bundle.getString("time");

        db = new DatabaseHelper(context);

        Event event = new Event(text,bundle.getString("date"),bundle.getString("time"),"off");
        db.updateEvent(event);

        //Click on Notification

        Intent intent1 = new Intent(context, NotificationMessage.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("message", text);

        //Notification Builder
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "notify_001";
            mBuilder.setSmallIcon(R.drawable.ic_alarm_white_24dp);
            mBuilder.setContentTitle("Personal Assistant");
            mBuilder.setContentText("Reminder: " + text);
            NotificationChannel channel = new NotificationChannel(channelId, "Reminder", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        } else {
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
            contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
            PendingIntent pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent);
            contentView.setTextViewText(R.id.message, text);
            contentView.setTextViewText(R.id.date, date);

            mBuilder.setContent(contentView);
            mBuilder.setContentTitle("Personal Assistant");
            mBuilder.setSmallIcon(R.drawable.ic_alarm_white_24dp);
            mBuilder.setAutoCancel(true);
//        mBuilder.setOngoing(true);
            mBuilder.setPriority(Notification.PRIORITY_HIGH);
            mBuilder.setOnlyAlertOnce(true);
            mBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
        }

        mBuilder.setContentIntent(pendingIntent);
        Notification notification = mBuilder.build();
        notificationManager.notify(100, notification);

    }
}
