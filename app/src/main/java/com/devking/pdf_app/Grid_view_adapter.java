package com.devking.pdf_app;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class Grid_view_adapter extends BaseAdapter {

    String TAG = "MainActivity";
    Context context;
    ArrayList<Uri> mArrayUri;


    

    public Grid_view_adapter(Context context , ArrayList<Uri> mArrayUri) {
        this.context = context;
        this.mArrayUri = mArrayUri;
    }

    @Override
    public int getCount() {
        if (mArrayUri != null && !mArrayUri.isEmpty()){
            Log.d(TAG, "mArray is not empty");
            return mArrayUri.size();
        }
        else {
            Log.d(TAG, "mArray is empty");
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootview = convertView;
        if(rootview == null){
            rootview = LayoutInflater.from(context).inflate(R.layout.grid_image_layout,parent,false);
        }
        ImageView image = rootview.findViewById(R.id.imageview);
        image.setImageURI(mArrayUri.get(position));
        return rootview;


//        ImageView imageView;
//
//        if (convertView == null) {
//            imageView = new ImageView(context);
//            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        }
//        else
//        {
//            imageView = (ImageView) convertView;
//        }
//        imageView.setImageURI(mArrayUri.get(position));
//
//        return imageView;

    }
}
