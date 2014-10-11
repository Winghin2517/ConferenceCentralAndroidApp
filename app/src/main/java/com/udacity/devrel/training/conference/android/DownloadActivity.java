package com.udacity.devrel.training.conference.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.udacity.devrel.training.conference.android.utils.Blobs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Simon on 2014/10/09.
 */
public class DownloadActivity extends Activity {

    private Blobs mBlobs;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_picture);
        ImageView UpLoadImage = (ImageView) findViewById(R.id.downloadImgView);
        Intent intent = getIntent();
        mBlobs = (Blobs) intent.getSerializableExtra("blobObject");

        URL url;
        Bitmap bmp = null;
        try {
            url = new URL(mBlobs.getServingUrl());
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpLoadImage.setImageBitmap(bmp);


    }
}
