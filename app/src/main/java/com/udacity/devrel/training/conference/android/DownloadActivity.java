package com.udacity.devrel.training.conference.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.udacity.devrel.training.conference.android.utils.Blobs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Simon on 2014/10/09.
 */
public class DownloadActivity extends Activity implements View.OnTouchListener {

    ImageView mUploadImage;
    Bitmap bmp = null;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_picture);

        mUploadImage = (ImageView) findViewById(R.id.downloadImgView);

        new displayPictureAsyncTask().execute((Blobs) getIntent().getSerializableExtra("blobObject"));
    }

//still to be implemented
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    protected class displayPictureAsyncTask extends AsyncTask<Object, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Object... param) {

            Blobs blobs = (Blobs) param[0];
            try {
                URL url = new URL(blobs.getServingUrl());
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute (Boolean result) {
            if (result == true) {
                mUploadImage.setImageBitmap(bmp);
            }
        }
    }

}
