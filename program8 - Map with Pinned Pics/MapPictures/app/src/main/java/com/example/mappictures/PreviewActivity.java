package com.example.mappictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.Toast;

public class PreviewActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        final Intent intent = getIntent();
        String picPath = intent.getStringExtra("Path");
        Log.e(picPath,"path");
        imageView = (ImageView)findViewById(R.id.image);
        Bitmap mypic = BitmapFactory.decodeFile(picPath);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(mypic , 0, 0, mypic.getWidth(), mypic.getHeight(), matrix, true);
        imageView.setImageBitmap(rotatedBitmap);
    }
}
