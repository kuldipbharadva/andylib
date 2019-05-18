package com.example.libusage.pagination;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.libusage.R;

import java.util.ArrayList;

public class PaginationTwoActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private boolean isLoading = false;
    private String offSet;
    private PaginationAdapter adapter;
    private ArrayList<String> mList;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagination);
        initBasic();
    }

    private void initBasic() {
//        setupLoadMore();
//        featuredTabAdapter = new FeaturedTabAdapter(getActivity(), postArrayList);
//        rvBaitsList.setAdapter(featuredTabAdapter);
//        rvBaitsList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        featuredTabAdapter.setOnClickListener(this);
    }

//    private void apiCallThisWeek(String serviceType) {
//        GetAllPostReq post = new GetAllPostReq();
//        post.setThisWeek(true);
//        if (FeaturedFilterFragment.catId == 0) {
//            post.setIsFeatured(true);
//        }
//        post.setDeviceId(Functions.getDeviceId(context));
//        post.setCategoryId(FeaturedFilterFragment.catId);
//        post.setDeviceId("MI123");
//        post.setPageIndex(pageIndex);
//
//        new ApiCall(context, post, AppConfig.getAllHeader(), serviceType, new ApiCallback() {
//            @Override
//            public void success(String responseData) {
//                if (responseData != null) {
//                    GetAllPostRes getAllPostRes = new Gson().fromJson(responseData, GetAllPostRes.class);
//                    if (pageIndex == 1) {
//                        postArrayList.clear();
//                    }
//                    if (pageIndex == getAllPostRes.getResponseData().getPagination().getTotalPages()) {
//                        rvBaitsList.removeMoreListener();
//                    }
//                    if (getAllPostRes.getResponseData().getPost() != null && getAllPostRes.getResponseData().getPost().size() > 0)
//                        postArrayList.addAll(getAllPostRes.getResponseData().getPost());
//                    featuredTabAdapter.notifyDataSetChanged();
//                    pageIndex++;
//                }
//                rvList.showRecycler();
//                rvList.hideProgress();
//            }
//
//            @Override
//            public void failure(String responseData) {
//                rvList.showRecycler();
//                rvList.hideProgress();
//            }
//        });
//
//    }

//    private void setupLoadMore() {
//        rvBaitsList.setupMoreListener(new OnMoreListener() {
//            @Override
//            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
//                if (pageIndex > 1) {
//                    apiCallThisWeek(CommonConfig.serviceCallFrom.LIST_LOAD_MORE_RC_WS_CALL);
//                } else {
//                    rvBaitsList.hideProgress();
//                    rvBaitsList.hideMoreProgress();
//                }
//            }
//        }, AppConfig.loadMoreBeforeNumberOfItems);
//    }
}
