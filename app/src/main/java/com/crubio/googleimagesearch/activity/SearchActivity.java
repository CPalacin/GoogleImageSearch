package com.crubio.googleimagesearch.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.crubio.googleimagesearch.R;
import com.crubio.googleimagesearch.fragment.ImageGrid;
import com.crubio.googleimagesearch.fragment.SettingsDialog;
import com.crubio.googleimagesearch.listener.OnSearchListener;

public class SearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private OnSearchListener onSearchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbarCreation();
        switchToImageGrid();
    }

    private void switchToImageGrid() {
        ImageGrid imageGrid = new ImageGrid();
        onSearchListener = imageGrid;
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
        setSupportActionBar(toolbar);
        setToolbarTitle(getResources().getString(R.string.app_name));
    }

    private void setToolbarTitle(String title) {
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbarTitle.setText(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_image_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(onSearchListener != null){
                    onSearchListener.onSearch(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                showSettingsDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSettingsDialog() {
        FragmentManager fm = getFragmentManager();
        SettingsDialog editNameDialog = SettingsDialog.newInstance("Advance Filters");
        editNameDialog.show(fm, "fragment_edit_name");
    }

}
