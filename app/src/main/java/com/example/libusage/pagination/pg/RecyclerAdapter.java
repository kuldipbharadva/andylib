package com.example.libusage.pagination.pg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libusage.R;

import java.util.ArrayList;

/**
 * Created by KD on 9/28/2019.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<CityDatum> arrayList;

    public RecyclerAdapter(/*ArrayList<CityDatum> arrayList*/) {
//        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pagination_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.index.setText(arrayList.get(position).getCityId() + "");
        holder.tvName.setText(arrayList.get(position).getCityName());
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName, index;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            index = itemView.findViewById(R.id.index);
        }
    }

    public void setList(ArrayList<CityDatum> list) {
        this.arrayList = list;
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<CityDatum> newList) {
        int lastIndex = arrayList.size();
        arrayList.addAll(newList);
        notifyItemRangeInserted(lastIndex, newList.size());
    }
}
