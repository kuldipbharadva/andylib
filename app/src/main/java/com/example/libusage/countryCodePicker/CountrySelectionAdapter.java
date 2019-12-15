package com.example.libusage.countryCodePicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.libusage.R;

import java.util.ArrayList;
import java.util.List;

public class CountrySelectionAdapter extends RecyclerView.Adapter<CountrySelectionAdapter.ViewHolder> {
    private Activity mActivity;
    private List<SelectionListBean> list;
    private List<SelectionListBean> filterableList;
    private CountrySelectionAdapter.ItemFilter mFilter = new CountrySelectionAdapter.ItemFilter();

    public CountrySelectionAdapter(List<SelectionListBean> list, Activity mActivity) {
        this.mActivity = mActivity;
        this.list = list;
        this.filterableList = list;
    }

    @NonNull
    @Override
    public CountrySelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_selection_country, parent, false);
        return new CountrySelectionAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountrySelectionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvCountry.setText("" + filterableList.get(position).getName());
        holder.tvCode.setText("(" + filterableList.get(position).getId() + ")");

        holder.llContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CustomCountryCodePickerActivity) mActivity).sendDataBack(filterableList.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return filterableList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCountry;
        TextView tvCode;
        LinearLayout llContainer;

        ViewHolder(View view) {
            super(view);
            llContainer = view.findViewById(R.id.llContainer);
            tvCode = view.findViewById(R.id.tvCode);
            tvCountry = view.findViewById(R.id.tvCountry);
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<SelectionListBean> listData = list;

            int count = listData.size();
            final ArrayList<SelectionListBean> nlist = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                if (listData.get(i).getName().toLowerCase().contains(filterString)) {
                    nlist.add(listData.get(i));
                }
            }
            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            filterableList = (List<SelectionListBean>) results.values;
            notifyDataSetChanged();
        }
    }


    public Filter getFilter() {
        return mFilter;
    }
}