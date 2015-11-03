package com.crubio.googleimagesearch.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.crubio.googleimagesearch.R;
import com.crubio.googleimagesearch.model.Image;
import com.squareup.picasso.Picasso;

public class ImageViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Image image;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        image = getIntent().getParcelableExtra("image");
        imageView = (ImageView) findViewById(R.id.img);
        Picasso.with(this).load(image.getUrl()).into(imageView);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean b = super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view, menu);
        return b;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                ImageView siv = (ImageView) findViewById(R.id.img);
                Drawable mDrawable = siv.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();

                String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                        mBitmap, "Image Description", null);

                Uri uri = Uri.parse(path);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/*");
                // Launch sharing dialog for image
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
