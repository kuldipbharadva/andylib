package com.example.libusage.fcm.liveTracking;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.example.libusage.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrackerService extends Service {

    private static final String TAG = TrackerService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        requestLocationUpdates();
    }

    /* this function used for update location every ten seconds and provide updated location */
    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        final String path = "locations/" + 10;
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.d(TAG, "location update " + location);
                        ref.setValue(location);
                    }
                }
            }, null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = getNotificationManager();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = getString(R.string.app_name);
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription(channelId);
        notificationChannel.setSound(null, null);

        notificationManager.createNotificationChannel(notificationChannel);
        Notification notification = new Notification.Builder(this, channelId)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Connected through SDL")
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(broadcastIntent)
                .build();
        startForeground(1, notification);
    }

    //method to get notificationManager
    public NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "received stop broadcast");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };
}