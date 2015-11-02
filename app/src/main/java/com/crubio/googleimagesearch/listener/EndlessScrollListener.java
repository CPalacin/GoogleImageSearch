package com.crubio.googleimagesearch.listener;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndlessScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 5; // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private int MAX_PAGES = 8;

    private int current_page = 1;

    private StaggeredGridLayoutManager mLayoutManager;

    public EndlessScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        Log.i("Scroll", "onScrolled page = "+current_page);
        super.onScrolled(recyclerView, dx, dy);
        Log.i("Scroll", "loading = " + loading);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();
        firstVisibleItem  = mLayoutManager.findFirstVisibleItemPositions(null)[0];
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            if(current_page <= MAX_PAGES) {
                onLoadMore(current_page);
            }

            loading = true;
        }
    }

    public void resetPage(){
        current_page = 1;
        previousTotal = 0;
    }

    public abstract void onLoadMore(int current_page);
}