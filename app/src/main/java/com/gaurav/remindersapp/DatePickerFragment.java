package com.gaurav.remindersapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Observable;

/**
 * A fragment that extends DialogFragment is needed to display the Date Picker
 */
public class DatePickerFragment extends android.support.v4.app.DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private int year, month, day;
    private DatePickerDialogListener listener;


    /**
     * Makes sure the activity is a DatePickerDialogListener
     * @param context the context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            listener = (DatePickerDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement DatePickerDialogListener");
        }
    }

    /**
     * Returns a DatePickerDialog after setting it up
     * @param savedInstanceState the saved instance state
     * @return a set up datePickerDialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog =  new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());

        return dialog;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        listener.onFinishedDatePicker(year, month, dayOfMonth);
    }


    /**
     * Activities that display this fragment must be DatePickerDialogListener
     */
    public interface DatePickerDialogListener {
        void onFinishedDatePicker(int year, int month, int dayOfMonth);
    }
}
