package com.example.libusage.fdb;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.libusage.R;

import java.util.ArrayList;

public class FdbAdapter extends RecyclerView.Adapter<FdbAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<FirebaseModel> firebaseModelArrayList;
    private OnItemClickListener onItemClickListener;

    public FdbAdapter(Context mContext, ArrayList<FirebaseModel> firebaseModelArrayList) {
        this.mContext = mContext;
        this.firebaseModelArrayList = firebaseModelArrayList;
    }

    public void OnItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fdb_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvItem.setText(firebaseModelArrayList.get(position).getVal());
        holder.tvItem.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return firebaseModelArrayList != null ? firebaseModelArrayList.size() : 0;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        ViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }
}
