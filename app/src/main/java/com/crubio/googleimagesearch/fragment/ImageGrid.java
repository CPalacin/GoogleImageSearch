package com.crubio.googleimagesearch.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crubio.googleimagesearch.R;
import com.crubio.googleimagesearch.activity.SearchActivity;
import com.crubio.googleimagesearch.adpater.ImageGridAdapter;
import com.crubio.googleimagesearch.handler.GoogleImageHandler;
import com.crubio.googleimagesearch.listener.EndlessScrollListener;
import com.crubio.googleimagesearch.listener.OnSearchListener;
import com.crubio.googleimagesearch.model.Image;
import com.crubio.googleimagesearch.model.SearchConfiguration;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

public class ImageGrid extends Fragment implements OnSearchListener{
    public static final String IMAGES_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&start=[start]&rsz=8&q=[query]";
    public static final String ANY = "Any";

    private List<Image> images = new ArrayList<>();
    private ImageGridAdapter adapter;
    private String lastQuery;
    private int lastPage = 0;
    private EndlessScrollListener scrollListener;
    private OnConfigurationRequestListener onConfigurationRequestListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_image_grid, container, false);

        // Retrieving the RecyclerView from the fragment layout
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_image_grid);

        // Setting a StaggeredGridLayoutManager
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(gridLayoutManager);
        scrollListener = new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.i("FETCH", "onLoadMore");
                fetchPictures(current_page, lastQuery);
            }
        };
        rv.setOnScrollListener(scrollListener);

        adapter = new ImageGridAdapter(images, getActivity());
        rv.setAdapter(adapter);

        return rootView;
    }

    private void fetchPictures(int page, String query){
        Log.i("fetchPictures", "page = " + page + " query: " + query + " lastPage: " + lastPage);
        if(query != null && lastPage < page) {
            AsyncHttpClient client = new AsyncHttpClient();
            String url = IMAGES_URL.replace("[query]", query).replace("[start]", ""+(page*8));
            url = addOptionalParameters(url);
            client.get(url, null, new GoogleImageHandler(images, adapter));
            lastPage = page;
        }
    }

    private String addOptionalParameters(String url) {
        SearchConfiguration configuration = onConfigurationRequestListener.onConfigurationRequest();
        String finalUrl = addSize(url, configuration.getSize());
        finalUrl = addColor(finalUrl, configuration.getColor());
        finalUrl = addType(finalUrl, configuration.getType());
        return addSite(finalUrl, configuration.getSite());
    }

    private String addSite(String finalUrl, String site) {
        String siteUrl = finalUrl;
        if(site != null && !ANY.equalsIgnoreCase(site)) {
            siteUrl += "&as_sitesearch=" + site;
        }
        return siteUrl;
    }

    private String addType(String finalUrl, String type) {
        String typeUrl = finalUrl;
        if(type != null && !ANY.equalsIgnoreCase(type)){
            typeUrl += "&imgtype="+type.toLowerCase();
        }
        return typeUrl;
    }

    private String addColor(String finalUrl, String color) {
        String colorUrl = finalUrl;
        if(color != null && !ANY.equalsIgnoreCase(color)){
            colorUrl += "&imgcolor="+color.toLowerCase();
        }
        return colorUrl;
    }

    private String addSize(String url, String size) {
        String sizeUrl = url;
        if(size != null && !ANY.equalsIgnoreCase(size)){
            sizeUrl += "&imgsz=";
            if("Extra large".equalsIgnoreCase(size)){
                sizeUrl += "xlarge";
            }else{
                sizeUrl += size.toLowerCase();
            }
        }
        return sizeUrl;
    }

    @Override
    public void onSearch(String query) {
        Log.i("FETCH", "onSearch");
        if(query != null) {
            lastPage = -1;
            adapter.clear();
            lastQuery = query;
            fetchPictures(0, query);
            scrollListener.resetPage();
        }else if(lastQuery != null){
            lastPage = -1;
            adapter.clear();
            fetchPictures(0, lastQuery);
            scrollListener.resetPage();
        }
    }
    
    public void setOnConfigurationRequestListener(OnConfigurationRequestListener onConfigurationRequestListener){
        this.onConfigurationRequestListener = onConfigurationRequestListener;
    }

    public interface OnConfigurationRequestListener{
        SearchConfiguration onConfigurationRequest();
    }
}
