package com.zv.geochat.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.zv.geochat.ChatActivity;
import com.zv.geochat.R;

public class NotificationDecorator {

    private static final String TAG = "NotificationDecorator";
    private final Context context;
    private final NotificationManager notificationMgr;
    private final MessageNotifierConfig messageNotifierConfig;

    public NotificationDecorator(Context context, NotificationManager notificationManager) {
        this.context = context;
        this.notificationMgr = notificationManager;
        this.messageNotifierConfig = new MessageNotifierConfig(context);

        createNotificationChannel();

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e(TAG, "adding a channel");
            CharSequence name = "com.zv.geochat.notification";
            String description = "com.zv.geochat.notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void displaySimpleNotification(Integer id, String title, String contentText) {
        //if (messageNotifierConfig.isPlaySound()) {
            Log.e(TAG, "displaySimpleNotification");
            Intent intent = new Intent(context, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // notification message
            try {
                Log.e(TAG, "notification");
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                remoteViews.setImageViewResource(R.id.image_left,R.drawable.ic_baseline_email_24);
                remoteViews.setImageViewResource(R.id.image_right,R.drawable.ic_baseline_email_24);
                remoteViews.setTextViewText(R.id.title,title);
                remoteViews.setTextViewText(R.id.text,contentText);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                        .setSmallIcon(R.drawable.ic_message)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true);
                        /*
                Notification noti = new Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_message)
                        .setTicker("Ticker")
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .setContent(remoteViews)
                        .build();
                        */

                        /*.setSmallIcon(R.drawable.ic_message)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setSound(messageNotifierConfig.getSoundUri())
                        .setVibrate(messageNotifierConfig.getVibratePattern())
                        .setLights(Color.BLUE, 1000, 1000)
                        .build();*/

                //noti.flags |= Notification.FLAG_AUTO_CANCEL;
                //notificationMgr.notify(0, noti);
                notificationMgr.notify(id, builder.build());
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.getMessage());
            }
        //}
    }

    public void displayExpandableNotification(Integer id, String title, String contentText) {
        //if (messageNotifierConfig.isPlaySound()) {
            Log.e(TAG, "displayExpandableNotification");
            Intent intent = new Intent(context, ChatActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // notification message
            try {
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_view);
                remoteViews.setImageViewResource(R.id.image_left,R.drawable.ic_message);
                remoteViews.setImageViewResource(R.id.image_right,R.drawable.ic_baseline_email_24);
                remoteViews.setTextViewText(R.id.title,"GeoChat App");
                remoteViews.setTextViewText(R.id.subtitle, title);
                remoteViews.setTextViewText(R.id.text,contentText);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                        .setSmallIcon(R.drawable.ic_message)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true);
                /*
                Notification noti = new Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_message)
                        .setTicker("Ticker")
                        .setAutoCancel(true)
                        .setContentIntent(contentIntent)
                        .setContent(remoteViews)
                        .build();*/

                /*Notification noti = new Notification.Builder(context)

                        .setSmallIcon(R.drawable.ic_message)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setContentIntent(contentIntent)
                        .setAutoCancel(true)
                        .setSound(messageNotifierConfig.getSoundUri())
                        .setVibrate(messageNotifierConfig.getVibratePattern())
                        .setLights(Color.BLUE, 1000, 1000)
                        .setStyle(new Notification.BigTextStyle().bigText(contentText))
                        .build();*/

                //noti.flags |= Notification.FLAG_AUTO_CANCEL;
                //notificationMgr.notify(0, noti);
                notificationMgr.notify(id,builder.build());
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.getMessage());
            }
        //}
    }

}
