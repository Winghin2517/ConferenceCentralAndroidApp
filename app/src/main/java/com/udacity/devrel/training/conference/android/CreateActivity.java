package com.udacity.devrel.training.conference.android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appspot.booming_order_708.conference.model.Conference;
import com.appspot.booming_order_708.conference.model.ConferenceForm;
import com.google.api.client.util.DateTime;
import com.udacity.devrel.training.conference.android.utils.ConferenceException;
import com.udacity.devrel.training.conference.android.utils.ConferenceUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Created by Simon on 2014/10/01.
 */
public class CreateActivity extends Activity {

    //member variables
    TextView mCreateButton;
    String mCity, mDesc, mName = null;
    int mMax = 0;
    Date mStartDate, mEndDate;

    private final static String KEY_CITY = "city";
    private final static String KEY_NAME = "name";
    private final static String KEY_DESC = "desc";
    private final static String KEY_MAX = "max";
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conference_create);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mCreateButton = (TextView) findViewById(R.id.header);
        mCreateButton.setText("Create Conference");
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new createConfAsyncTask().execute();
            }
        });

        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    protected class createConfAsyncTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... voids) {
            mName = ((EditText) findViewById(R.id.conf_name_text)).getText().toString();
            mDesc = ((EditText) findViewById(R.id.conf_desc_text)).getText().toString();
            mCity = ((EditText) findViewById(R.id.conf_city)).getText().toString();
            mMax = Integer.parseInt(((EditText) findViewById(R.id.conf_max)).getText().toString());
            mStartDate = new Date(2014, 9, 12);
            mEndDate = new Date(2014, 9, 20);

            ConferenceForm conferenceForm = new ConferenceForm().
                    setCity(mCity).
                    setDescription(mDesc).
                    setName(mName).
                    setStartDate(new DateTime(mStartDate)).
                    setEndDate(new DateTime(mStartDate)).
                    setMaxAttendees(mMax);
            try {
                ConferenceUtils.createConference(conferenceForm);
            } catch(IOException e) {

            } catch (ConferenceException e) {

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == true) {
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(),"Conference Created",
                        Toast.LENGTH_LONG).show();

            }
        }
    }


    public void onDestroy() {
        mName = null;
        mDesc = null;
        mCity = null;
        mMax = 0;
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) { //called before onStop()
        super.onSaveInstanceState(outstate);
        outstate.putString(KEY_CITY, mCity);
        outstate.putString(KEY_NAME, mName);
        outstate.putString(KEY_DESC, mName);
        outstate.putInt(KEY_MAX, mMax);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) { //called just before onStart in the activity lifecycle
        super.onRestoreInstanceState(savedInstanceState); //apparently this saves the window state
        mName = savedInstanceState.getString(KEY_NAME);
        mDesc = savedInstanceState.getString(KEY_DESC);
        mCity = savedInstanceState.getString(KEY_CITY);
        mMax = savedInstanceState.getInt(KEY_MAX);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
