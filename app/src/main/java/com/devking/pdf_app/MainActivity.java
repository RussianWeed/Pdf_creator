package com.devking.pdf_app;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    ArrayList<Uri> mArrayUri;
    Button button;
    int PICK_IMAGE_MULTIPLE = 1;
    GridView grid_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        grid_view = findViewById(R.id.grid_view);
        mArrayUri = new ArrayList<Uri>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

            // setting type to select to be image
                intent.setType("image/*");

            // allowing multiple image to be selected
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            if (data.getClipData() != null) {
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                    mArrayUri.add(imageurl);
                }
                Grid_view_adapter adapter = new Grid_view_adapter(this,mArrayUri);
                grid_view.setAdapter(adapter);
            }
        } else {
            // show this if no image is selected
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

}



//    Button select;
//    ImageView imageView;
//    int PICK_IMAGE_MULTIPLE = 1;
//
//    ArrayList<Uri> mArrayUri;
//    int position = 0;

// imageView = findViewById(R.id.image);
//         mArrayUri = new ArrayList<Uri>();
//        select = findViewById(R.id.select);
//
//
//        select.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        Intent intent = new Intent();
//
//        // setting type to select to be image
//        intent.setType("image/*");
//
//        // allowing multiple image to be selected
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
//        }
//        });
//        }
//
//
//@Override
//protected void onActivityResult ( int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        // When an Image is picked
//        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
//        // Get the Image from data
//        if (data.getClipData() != null) {
//        ClipData mClipData = data.getClipData();
//        int cout = data.getClipData().getItemCount();
//        for (int i = 0; i < cout; i++) {
//        // adding imageuri in array
//        Uri imageurl = data.getClipData().getItemAt(i).getUri();
//        mArrayUri.add(imageurl);
//        }
//        // setting 1st selected image into image switcher
//        imageView.setImageURI(mArrayUri.get(0));
//        position = 0;
//        } else {
//        Uri imageurl = data.getData();
//        mArrayUri.add(imageurl);
//        imageView.setImageURI(mArrayUri.get(0));
//        position = 0;
//        }
//        } else {
//        // show this if no image is selected
//        Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
//        }
