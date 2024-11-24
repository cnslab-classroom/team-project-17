package com.example.loms;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class CustomNotificationManager {
    private Context context;
    private static final String CHANNEL_ID = "LomsChannel";
    private static final String CHANNEL_NAME = "Loms Notifications";
    private static final int NOTIFICATION_ID_BASE = 1000; // Base ID for notifications

    public CustomNotificationManager(Context context) {
        this.context = context;
        createNotificationChannel();
    }

    /**
     * Creates a notification channel for Android O and above.
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                NotificationChannel channel = new NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("Notifications for Loms application.");
                manager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Displays a notification with the given title, message, and intent.
     *
     * @param title            Title of the notification.
     * @param message          Message content of the notification.
     * @param activityToLaunch Activity to launch on notification click.
     */
    public void showNotification(String title, String message, Class<?> activityToLaunch) {
        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent = new Intent(context, activityToLaunch);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification) // Ensure this resource exists
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // Generate a unique notification ID
            int notificationId = NOTIFICATION_ID_BASE + (int) System.currentTimeMillis() % 1000;

            manager.notify(notificationId, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
