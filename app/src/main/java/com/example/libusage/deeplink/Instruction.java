package com.example.libusage.deeplink;

public class Instruction {

   /*
    * Deeplink is concept of open app from shared link if app installed in device.
    * Deeplink support API 23+
    * When you tapped on link then its give option to link open in app or browser if we deeplinking in our app.
    * For deeplink we need to add intent-filter in manifest.
    * For Ex.
    * TODO      <intent-filter>
                    <action android:name="android.intent.action.VIEW" />
                    <category android:name="android.intent.category.DEFAULT" />
                    <category android:name="android.intent.category.BROWSABLE" />
                   <data
                       android:host="google.com"
                       android:scheme="https" />
                   <!-- for reference : you can pass pathPrefix too -->
                   <!--<data-->
                       <!--android:host="www.google.com"-->
                       <!--android:pathPrefix="/api/"-->
                       <!--android:scheme="https" />-->
                </intent-filter>

    * This intent-filter block basically put in splash or starting activity so you can handle deeplinking.
    * DeeplinkActivity shows how to handle deeplink url.
    * Then you need to create link and shared to user.
    * You need to create link based on given 'host' in manifest or you need to set 'host' based on your link.
    * You can set multiple 'host' i mean <data> tag in intent-filter.
    * You can give query parameter in link like get url and fetch when deeplink open app.
    * For Ex. you can use below function to get param value. you can used your query param.
    */

//   TODO
//    private void getDeepLinkData() {
//        Intent appLinkIntent = getIntent();
//        try {
//            Uri appLinkData = appLinkIntent.getData();
//            if (appLinkData != null) {
//                if (appLinkIntent.getData().getQuery().contains("eid"))
//                    eventId = appLinkIntent.getData().getQueryParameter("eid");
//                else if (appLinkIntent.getData().getQuery().contains("linkid"))
//                    linkId = appLinkIntent.getData().getQueryParameter("linkid");
//            } else if (appLinkIntent.getExtras() != null && appLinkIntent.getExtras().containsKey("eid") && appLinkIntent.getExtras().get("eid").toString().trim().length() > 0) {
//                eventId = appLinkIntent.getExtras().get("eid").toString();
//            } else if (appLinkIntent.getExtras() != null && appLinkIntent.getExtras().containsKey("WALLET_DATA") && appLinkIntent.getExtras().get("WALLET_DATA") != null) {
//                walletData = appLinkIntent.getExtras().get("WALLET_DATA");
//            } else if (appLinkIntent.getExtras() != null && appLinkIntent.getExtras().containsKey("TYPE") && appLinkIntent.getExtras().get("TYPE") != null) {
//                type = appLinkIntent.getExtras().getInt("TYPE");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
