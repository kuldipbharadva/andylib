package com.example.libusage.imagePickFromGalleryMultiple;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.libusage.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedImagesAdapter extends RecyclerView.Adapter<SelectedImagesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Uri> selectedImagesList;

    public SelectedImagesAdapter(Context context, ArrayList<Uri> selectedImagesList) {
        this.context=context;
        this.selectedImagesList=selectedImagesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selected_image_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(selectedImagesList.get(position))
                .centerCrop()
                .placeholder(R.color.colorGreyPlaceholder)
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return selectedImagesList != null ? selectedImagesList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        RoundedImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
