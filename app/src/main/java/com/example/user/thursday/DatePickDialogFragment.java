package com.example.user.thursday;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by user on 2016-07-21.
 */
public class DatePickDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public DatePickDialogFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView selectDay = (TextView) getActivity().findViewById(R.id.select_day);
        int monthOfyear = month+1;

        String stingOfDate = year+"-"+monthOfyear+"-"+day;
        selectDay.setText(stingOfDate);
    }


}
