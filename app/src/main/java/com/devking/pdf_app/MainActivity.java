package com.devking.pdf_app;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 200;

    String TAG = "create method";

    ArrayList<Uri> mArrayUri;
    Button button,create_pdf;
    int PICK_IMAGE_MULTIPLE = 1;
    GridView grid_view;
    int pageHeight = 1120;
    int pagewidth = 792;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        grid_view = findViewById(R.id.grid_view);
        create_pdf = findViewById(R.id.create_pdf);
        mArrayUri = new ArrayList<Uri>();

        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

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

        create_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatepdf(mArrayUri);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
            // Get the Image from data
            if (data.getClipData() != null) {
                int cout = data.getClipData().getItemCount();
                for (int i = 0; i < cout; i++) {
                    // adding imageuri in array
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

    private void generatepdf(ArrayList<Uri> mArrayUri) {
        Log.d(TAG, "create method is running ");
        int page_count = mArrayUri.size();
        Paint paint = new Paint();
        PdfDocument pdfDocument = new PdfDocument();
        for (int i = 0;i<page_count;i++){
            Bitmap bmp,scaledbmp;
            Uri uri_image = mArrayUri.get(i);
            bmp = uriToBitmap(uri_image);
            scaledbmp = Bitmap.createScaledBitmap(bmp, 700, 700, false);
            PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, i).create();
            PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);
            Canvas canvas = myPage.getCanvas();
            canvas.drawBitmap(scaledbmp, 56, 40,paint);
            pdfDocument.finishPage(myPage);
        }
        File file = new File(Environment.getExternalStorageDirectory(), "GFG.pdf");
        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(MainActivity.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // below line is used
            // to handle error
            e.printStackTrace();
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }

    private Bitmap uriToBitmap(Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}

