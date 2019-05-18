package com.example.libusage.mapping.mapCluster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.libusage.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class MyClusterRender extends DefaultClusterRenderer<MyItem> {

    private final float mDensity;
    private Context context;

    private final IconGenerator mClusterIconGenerator;

    public MyClusterRender(Context context, GoogleMap map,
                           ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        mClusterIconGenerator = new IconGenerator(context);
        mDensity = context.getResources().getDisplayMetrics().density;
    }

    private void setupIconGen(IconGenerator generator, Drawable drawable, Context context, int size) {
        TextView textView = new TextView(context);
//        textView.setTextAppearance(context, R.style.BubbleText);
//        textView.setTypeface(App.FONTS[2]);
//        Typeface type = Typeface.createFromAsset(context.getAssets(), "Poppins-Bold_0.ttf");
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setText("" + size);
        if (size < 10) {
            mClusterIconGenerator.setContentPadding(5, 5, 5, 5);
        } else {
            mClusterIconGenerator.setContentPadding(40, 40, 40, 40);
        }
        textView.setTextSize(16);
        textView.setGravity(android.view.Gravity.CENTER);
        textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        textView.setLayoutParams(new FrameLayout.LayoutParams(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()));
        generator.setContentView(textView);
        generator.setBackground(drawable);
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
        final Drawable clusterIcon = context.getResources().getDrawable(R.drawable.bg_map_cluster);
        mClusterIconGenerator.setBackground(clusterIcon);
        mClusterIconGenerator.makeIcon(getClusterText(context.getResources().getColor(R.color.colorAccent)));

        Bitmap icon;
        setupIconGen(mClusterIconGenerator, clusterIcon, context, cluster.getSize());
        icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        /* you can set custom marker pin icon */
//        BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin);
//        markerOptions.icon(markerDescriptor);
    }

    @Override
    protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);
    }
}