package com.example.libusage.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.libusage.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebasePushService";
    private int uniqueId = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String messageJson = remoteMessage.getData().get("message");
        parseJson(messageJson);
    }

    private void parseJson(String messageJson) {
//        try {
//            JSONObject mainObject = new JSONObject(messageJson);
//            int type = Integer.valueOf(mainObject.get("push_type").toString());
//            switch (type) {
//                case AppConstant.PUSH_TYPE_VERIFY:
//                    sendNotification(mainObject.get("text_message").toString(), type, "", null);
//                    break;
//                case AppConstant.PUSH_TYPE_EVENT_EXPIRE_TOMORROW:
//                    WalletData data = new WalletData();
//                    data.setTransactionId(mainObject.get("transaction_id").toString());
//                    data.setEventId(mainObject.get("event_id").toString());
//                    data.setLocation(mainObject.get("location").toString());
//                    data.setEventDate(mainObject.get("selected_date").toString());
//                    data.setEventTime(mainObject.get("ticket_time").toString());
//                    data.setEventName(mainObject.get("event_name").toString());
//                    data.setEventLat(mainObject.get("event_lat").toString());
//                    data.setEventLong(mainObject.get("event_long").toString());
//                    data.setMaxMembers(mainObject.get("max_members").toString());
//                    sendNotification(mainObject.get("text_message").toString(), type, "", data);
//                    break;
//                case AppConstant.PUSH_TYPE_FAV_EXPIRE_UNPURCHASED:
//                case AppConstant.PUSH_TYPE_EVENT_LIMITED_SEAT_LEFT:
//                    sendNotification(mainObject.get("text_message").toString(), type, mainObject.get("event_id").toString(), null);
//                    break;
//                case AppConstant.PUSH_TYPE_FROM_ADMIN:
//                    sendNotification(mainObject.get("text_message").toString(), type, mainObject.getString("description"), null);
//                    break;
//            }
//            Intent in = new Intent(
//                    AppConstant.NOTIFICATION_REFRESH);
//            in.putExtra("action", AppConstant.NOTIFICATION_ACTION);
//            LocalBroadcastManager.getInstance(
//                    getApplicationContext())
//                    .sendBroadcast(in);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    //This method is only generating push notification
    private void sendNotification(String message, int type, String eventId) {
        try {
            Intent intent = null;
            //On click of notification it redirect to this Activity
            generateUniqueId();
//            intent = new Intent(this, SplashActivity.class);
//            if (type == AppConstant.PUSH_TYPE_EVENT_LIMITED_SEAT_LEFT || type == AppConstant.PUSH_TYPE_FAV_EXPIRE_UNPURCHASED) {
//                intent.putExtra("eid", eventId);
//            } else if (type == AppConstant.PUSH_TYPE_EVENT_EXPIRE_TOMORROW) {
//                intent.putExtra("WALLET_DATA", data);
//            } else if (type == AppConstant.PUSH_TYPE_FROM_ADMIN) {
//                intent.putExtra("TYPE", type);
//            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String id = "channel" + uniqueId;
            NotificationManager notificationManager = getNotificationManager();
            if (notificationManager != null) {
                String channelId = "channel" + uniqueId;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    NotificationChannel mChannel = new NotificationChannel(channelId, getString(R.string.app_name), importance);
                    // Configure the notification channel.
                    mChannel.setDescription(getString(R.string.app_name));
                    mChannel.setShowBadge(true);
                    mChannel.enableLights(true);
                    mChannel.setLightColor(getColor());
                    mChannel.enableVibration(true);
                    mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                    notificationManager.createNotificationChannel(mChannel);

                }
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder =
                        new NotificationCompat.Builder(this, id)
                                .setSmallIcon(getNotificationIcon())
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                .setColor(getColor())
                                .setSound(defaultSoundUri)
                                .setContentText(message)
                                .setContentTitle(getString(R.string.app_name))
                                .setAutoCancel(true).setChannelId(channelId)
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                .setContentIntent(pendingIntent);
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
                    notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
                }
                notificationManager.notify(uniqueId, notificationBuilder.build());

                Notification notification = notificationBuilder.build();

                notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                notification.flags |=
                        Notification.FLAG_AUTO_CANCEL; //Do not clear the notification
                notification.defaults |= Notification.DEFAULT_LIGHTS; // LED
                notification.defaults |= Notification.DEFAULT_VIBRATE;//Vibration

                notificationManager.notify(uniqueId, notification);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateUniqueId() {
        uniqueId = (int) System.currentTimeMillis();
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
    }

    //method to get notificationManager
    public NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private int getColor() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            return ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        }
        return 0;
    }

}