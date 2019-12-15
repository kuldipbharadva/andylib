package com.example.libusage.recyclerViewItemClickCommon;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libusage.R;


public class ItemClickUseAdapter extends RecyclerView.Adapter<ItemClickUseAdapter.ViewHolder> {

    private Context context;
    private OnItemClickInterface onItemClickInterface;

    public ItemClickUseAdapter(Context context, OnItemClickInterface onItemClickInterface) {
        this.context=context;
        this.onItemClickInterface = onItemClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.withText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickInterface != null) {
                    onItemClickInterface.onItemClick(context, position, R.id.withText);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView withText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
