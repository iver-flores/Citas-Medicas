package com.ip.citasmedicas.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ip.citasmedicas.R;
import com.ip.citasmedicas.activities.LoginActivity;

public class FcmMessagingService extends FirebaseMessagingService {

    public FcmMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null &&
                remoteMessage.getData().size() > 0 && remoteMessage.getNotification() != null){
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            Notification.Builder notificationBuilder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(notification.getTitle())
                    .setContentText(notification.getBody())
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)

                    //.addAction(R.drawable.ic_location, "RECHAZAR", null)
                    //.addAction(R.drawable.ic_location, "VER", pendingIntent)

                    .setContentIntent(pendingIntent);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                /*notificationBuilder.setColor(desc > .4 ?
                        ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary) :
                        ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));*/
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                /*String channelId = desc < .10 ? getString(R.string.low_channel_id) :
                        getString(R.string.normal_channel_id);*/
                String channelId = "ChanelCompras";
                String channelName = "Compras";
                NotificationChannel channel = new NotificationChannel(channelId, channelName,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 200, 50});
                if (notificationManager != null) {
                    notificationManager.createNotificationChannel(channel);
                }

                notificationBuilder.setChannelId(channelId);
            }

            if (notificationManager != null) {
                notificationManager.notify("", 0, notificationBuilder.build());
            }
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        Log.d("newToken", token);
    }
}
