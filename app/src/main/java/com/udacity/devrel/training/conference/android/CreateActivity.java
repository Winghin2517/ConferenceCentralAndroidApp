package com.udacity.devrel.training.conference.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conference_create);

        mCreateButton = (TextView) findViewById(R.id.header);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new createConfAsyncTask().execute();
            }
        });

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
                Toast.makeText(getApplicationContext(),R.string.toast_no_google_accounts_registered,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void createConference() {
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
    };

    public void onDestroy() {
        mName = null;
        mDesc = null;
        mCity = null;
        mMax = 0;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putString(KEY_CITY, mCity);
        outstate.putString(KEY_NAME, mName);
        outstate.putString(KEY_DESC, mName);
        outstate.putInt(KEY_MAX, mMax);
    }


}
