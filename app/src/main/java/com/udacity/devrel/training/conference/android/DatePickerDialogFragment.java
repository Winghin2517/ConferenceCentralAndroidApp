package com.udacity.devrel.training.conference.android;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

//The DatePickerDialogFragment class uses v4 support class for DialogFragment
//which means that any class that uses this class will have to extend FragmentActivity instead of Activity
//as the FragmentActivity are used for support classes - behaviors exactly like the Activity class though.

public class DatePickerDialogFragment extends DialogFragment {

    private OnDateSetListener mDateSetListener;

    public DatePickerDialogFragment() {
        // nothing to see here, move along
    }

    @SuppressLint("ValidFragment")
    public DatePickerDialogFragment(OnDateSetListener callback) {
        mDateSetListener = (OnDateSetListener) callback;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        return new DatePickerDialog(getActivity(),
                mDateSetListener, cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

}