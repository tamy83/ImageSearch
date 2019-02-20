package com.yensontam.imagesearch;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<Image> images;
    private LayoutInflater mInflater;

    public ImageAdapter(List<Image> images) {
        setImages(images);
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
        for (Image image : images) {
            new ImageDownloadTask().execute(image, images.indexOf(image));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image image = images.get(position);
        Bitmap bitmap = image.getBitmap();
        holder.imageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    private class ImageDownloadTask extends AsyncTask<Object, Void, Integer> {

        @Override
        protected Integer doInBackground(Object... objects) {
            Log.i("SpinCar", "downloading " + ((Image) objects[0]).getThumbnailUrl());
            Image image = (Image) objects[0];
            Integer position = (Integer) objects[1];
            Bitmap bitmap = null;
            try {
                URL url = new URL(image.getThumbnailUrl());
                URLConnection connection = url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e("SpinCar", "Error getting bitmap: " + e.getMessage());
            }
            image.setBitmap(bitmap);
            return position;
        }

        @Override
        protected void onPostExecute(Integer position) {
            notifyItemChanged(position);
        }

    }
}
