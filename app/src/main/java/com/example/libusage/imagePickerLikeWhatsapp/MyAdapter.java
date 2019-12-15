package com.example.libusage.imagePickerLikeWhatsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.libusage.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> list=new ArrayList<>();
    private Context context;

    public MyAdapter(Context context) {
        this.context=context;
    }

    public void addImage(ArrayList<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_selected_image_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder1, int position) {
        Holder holder=(Holder) holder1;
        //Uri imageUri = Uri.fromFile(new File(list.get(position)));// For files on device
        //Log.e("hello", "- " + imageUri.toString());
        File f=new File(list.get(position));
        Bitmap d=new BitmapDrawable(context.getResources(), f.getAbsolutePath()).getBitmap();
        /*Bitmap scaled = com.fxn.utility.Utility.getScaledBitmap(
            500f, com.fxn.utility.Utility.rotate(d,list.get(position).getOrientation()));*/

        Glide.with(context)
                .load(d)
                .centerCrop()
                .placeholder(R.color.colorGreyPlaceholder)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView ivImage;

        Holder(View itemView) {
            super(itemView);
            ivImage=itemView.findViewById(R.id.ivImage);
        }
    }
}
