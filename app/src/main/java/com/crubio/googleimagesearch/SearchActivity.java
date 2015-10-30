package com.crubio.googleimagesearch;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.crubio.googleimagesearch.fragment.ImageGrid;

public class SearchActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbarCreation();
        switchToImageGrid();
    }

    private void switchToImageGrid() {
        ImageGrid imageGrid = new ImageGrid();
        switchFragment(imageGrid);
    }

    private void switchFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void toolbarCreation() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
//        toolbar.getMenu().clear();
        setSupportActionBar(toolbar);
        setToolbarTitle(getResources().getString(R.string.app_name));
    }

    private void setToolbarTitle(String title) {
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//            Assets.setInstagramTitleFont(toolbarTitle, this);
            toolbarTitle.setText(title);
        }
    }
}
