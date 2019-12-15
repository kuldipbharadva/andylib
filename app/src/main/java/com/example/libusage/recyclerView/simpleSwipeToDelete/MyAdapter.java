package com.example.libusage.recyclerView.simpleSwipeToDelete;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.libusage.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<String> tempList;

    public MyAdapter(Context context, ArrayList<String> tempList) {
        this.context = context;
        this.tempList = tempList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvItem.setText(tempList.get(i));
    }

    @Override
    public int getItemCount() {
        return tempList!=null ? tempList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }

    public void removeItem(int position) {
        tempList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(String item, int position) {
        tempList.add(position, item);
        notifyItemInserted(position);
    }
}
