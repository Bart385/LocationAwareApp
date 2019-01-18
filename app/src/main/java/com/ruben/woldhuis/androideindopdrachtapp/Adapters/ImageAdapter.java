package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruben.woldhuis.androideindopdrachtapp.R;

import java.io.File;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private File imagesFile;

    public ImageAdapter(File folderFile) {
        imagesFile = folderFile;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.camera_image_gallery, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        File imageFile = imagesFile.listFiles()[position];
        Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        holder.getImageView().setImageBitmap(imageBitmap);
    }

    @Override
    public int getItemCount() {
        if (imagesFile.listFiles() == null)
            return 0;
        return imagesFile.listFiles().length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.imageGalleryView);
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}