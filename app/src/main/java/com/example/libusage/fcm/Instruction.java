package com.example.libusage.fcm;

public class Instruction {

   /*
    *   For firebase messaging put FcmListenerService and register in manifest.
    *   TODO <service android:name=".fcm.FcmListenerService">
                <intent-filter>
                    <action android:name="com.google.firebase.MESSAGING_EVENT" />
                </intent-filter>
             </service>

    *   You need to create Notification Channel to give support in N or above all otherwise fcm not work and
    *   not get notification on that android version only below version support.
    *
    *   Everything done in FcmListenerService so just used that service and send data in notification as per
    *   your requirement and fetch and do task/operation/redirection.
    *
    *   You can get
    *   New way to get Fcm token just put below code in your activity when you need token.
    *   TODO FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListenr(Activity.this,new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String newToken = instanceIdResult.getToken();
                }
             });
    *
    *  You can use FcmListenerService or MyFirebaseMessagingService for reference both working good and well implemented code.
    *
    */
}
