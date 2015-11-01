package com.crubio.googleimagesearch.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.crubio.googleimagesearch.R;
import com.crubio.googleimagesearch.model.Image;
import com.squareup.picasso.Picasso;

public class ImageViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        image = getIntent().getParcelableExtra("image");
        Picasso.with(this).load(image.getUrl()).into((ImageView) findViewById(R.id.img));
        toolbarCreation();
    }

    private void toolbarCreation() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        setToolbarTitle(image.getTitle());
    }

    private void setToolbarTitle(String title) {
        if (toolbar != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbarTitle.setText(title);
        }
    }
}
