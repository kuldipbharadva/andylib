package com.example.libusage.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.andylib.log.PrintLog;
import com.andylib.preferences.MySharedPreference;
import com.example.libusage.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;
import java.util.Random;


public class FcmListenerService extends FirebaseMessagingService {

    public static String intentIsNotification = "isFromNotification";
    public static String intentUserId = "userid";
    private Context context;

    @Override
    public void onMessageReceived(RemoteMessage message) {
        context = this;
        String from = message.getFrom();
        Map data = message.getData();
        PrintLog.Log(this,"", "From:" + message.getFrom(),"", Log.DEBUG);
          calculateBadgeCounter();
                showNotification(data);


    }

    private void calculateBadgeCounter() {
        int counter = (int) MySharedPreference.getPreference(getApplicationContext(), getString(R.string.Notification_Prefs_Name), getString(R.string.Key_Notification), 0);
        MySharedPreference.setPreference(getApplicationContext(), getString(R.string.Notification_Prefs_Name), getString(R.string.Key_Notification), counter + 1);
    }



    public void showNotification(Map data) {

        int color = 0;
        final int not_nu = generateRandom();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            color = getColor(R.color.colorPrimaryDark);
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = null;
        JSONObject objectMessage = null;
        try {
            objectMessage = new JSONObject(data.get("msg").toString());

        /*    if (data.get("actionType").equals("3")) {
                intent = new Intent(this, SplashActivity.class);
//                Log.e("Email", "showNotification: "+CommonSharedPreference.getPreferenceValue(context, context.getString(R.string.PREF_LOGIN), context.getString(R.string.PREF_EMAIL), "").toString().equalsIgnoreCase("") );
//                if (CommonSharedPreference.getPreferenceValue(context, context.getString(R.string.PREF_LOGIN), context.getString(R.string.PREF_EMAIL), "").toString().equalsIgnoreCase("")) {
//                    intent = new Intent(this, SplashActivity.class);
//                }else {
//                    intent = new Intent(this, PostDetailActivity.class);
//                    intent.putExtra(intentIsNotification, true);
//                    intent.putExtra(intentUserId, data.get("postid").toString());
//                }

//                JSONObject object = new JSONObject(data);
//                intent.putExtra("data", object.toString());
//                intent.putExtra("desc", objectMessage.getString("body"));
            } else if (data.get("actionType").equals("4")){
                intent = new Intent(this, SplashActivity.class);
//                intent.putExtra("isFromNotification", true);
            } else if (data.get("actionType").equals("5")){
                intent = new Intent(this, SplashActivity.class);
//                intent = new Intent(this, ViewBottomActivity.class);
//                intent.putExtra(intentIsNotification, true);
//                intent.putExtra(intentUserId, data.get("UserId").toString());
            } else {
                intent = new Intent(this, SplashActivity.class);
            }*/

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            String channelId = "channel-01";
            String channelName = "Baits";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle(objectMessage.getString("title"))
                    .setContentText(objectMessage.getString("body"))
//                    .setAutoCancel(true)
                    .setAutoCancel(true).setChannelId(channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(color)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(objectMessage.getString("body")))
                    .setSound(defaultSoundUri);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify(not_nu, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int generateRandom(){
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}