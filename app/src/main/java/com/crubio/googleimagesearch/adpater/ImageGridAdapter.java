package com.crubio.googleimagesearch.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.crubio.googleimagesearch.R;
import com.crubio.googleimagesearch.activity.ImageViewActivity;
import com.crubio.googleimagesearch.model.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageHolder>{
    private List<Image> images;
    private Context context;

    public ImageGridAdapter(List<Image> images, Context context){
        this.images = images;
        this.context = context;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = ((RecyclerView.LayoutParams) v.getLayoutParams()).getViewAdapterPosition();
                Intent i = new Intent(context, ImageViewActivity.class);
                i.putExtra("image",images.get(itemPosition));

                context.startActivity(i);

            }
        });
        return new ImageHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        final Image image = images.get(position);

        // Setting a placeholder drawable with the same size than the picture that will be load allows to the
        // allows the staggeredGridLayout to reorder faster.
        // Read your drawable from somewhere
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bitmap = Bitmap.createBitmap(image.getTbWidth(), image.getTbHeight(), conf); // this creates a MUTABLE bitmap
        Drawable d = new BitmapDrawable(context.getResources(), bitmap);

        Picasso.with(context).load(image.getTbUrl()).placeholder(d).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void clear() {
        images.clear();
        notifyDataSetChanged();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        private ImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.iv_image);
        }
    }
}
