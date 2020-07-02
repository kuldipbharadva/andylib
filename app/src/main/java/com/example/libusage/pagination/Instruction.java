package com.example.libusage.pagination;

public class Instruction {

   /*
    *  Pagination/Lode More Usage:
    *  ---------------------------
    *
    *  PaginationOneActivity there is an apiCallResponseBlock() function to manage response and handle pagination.
    *  There is only logical code written not tested.
    *  This is basic logic for pagination/load more.
    *  You can just check offSet param from api response block and manage load more based on offSet.
    *  Also have isLoading boolean to manage first time calling api.
    *  First time when api call that time we need to clear arrayList and add all data in arrayList
    *  which comes from api and then we need to notify adapter.
    *  Here Pagination manage based on OFFSET param.
    *
    *  PaginationTwoActivity: The another way is to manage pagination using like this in this activity.
    *  You can use SuperRecyclerView for listing in XML.
    *
    *
    *  PG package :
    *  You just have to create your adapter like 'RecyclerAdapter' and just check MyPaginationActivity code and pagination is on your hand.
    */
}
