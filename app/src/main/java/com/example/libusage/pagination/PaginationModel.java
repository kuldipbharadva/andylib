package com.example.libusage.pagination;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaginationModel {

    @SerializedName("data")
    private ArrayList<String> mEventslist;
    @SerializedName("offset")
    private String offSet;

    public String getOffSet() {
        return offSet;
    }

    public void setOffSet(String offSet) {
        this.offSet = offSet;
    }

    public ArrayList<String> getmEventslist() {
        return mEventslist;
    }

    public void setmEventslist(ArrayList<String> mEventslist) {
        this.mEventslist = mEventslist;
    }
}
