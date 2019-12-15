package com.example.libusage.recyclerView.swipeToDeleteOption;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.libusage.R;
import com.example.libusage.recyclerView.swipeToDelete.SwipeRevealLayout;
import com.example.libusage.recyclerView.swipeToDelete.ViewBinderHelper;

import java.util.ArrayList;

public class MyAdapterOption extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<String> tempList;
    private ViewBinderHelper viewBinderHelper;

    public MyAdapterOption(Context context, ArrayList<String> tempList, ViewBinderHelper viewBinderHelper) {
        this.context = context;
        this.tempList = tempList;
        this.viewBinderHelper = viewBinderHelper;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_item_option, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        viewBinderHelper.bind(holder.swipeLayout, "" + i);
        holder.tvItem.setText(tempList.get(i));
    }

    @Override
    public int getItemCount() {
        return tempList!=null ? tempList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout frameLayoutSwipe;
        SwipeRevealLayout swipeLayout;
        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            frameLayoutSwipe = itemView.findViewById(R.id.frameLayoutSwipe);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
            tvItem = itemView.findViewById(R.id.tvItem);
        }
    }
}
