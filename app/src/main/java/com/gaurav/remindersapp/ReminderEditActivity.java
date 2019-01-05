package com.gaurav.remindersapp;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * The activity representing the screen when you edit/create a reminder
 */
public class ReminderEditActivity extends FileActivity  implements View.OnClickListener,
        DatePickerFragment.DatePickerDialogListener, TimePickerFragment.TimePickerDialogListener {
    int year ,month, hourOfDay, minute, dayOfMonth;

    TextView date, time;
    EditText description_edit;
    Button date_button, time_button, cancel_button, save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_edit);

        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        description_edit = findViewById(R.id.description_edit);
        date_button = findViewById(R.id.date_button);
        time_button = findViewById(R.id.time_button);
        cancel_button = findViewById(R.id.cancel_button);
        save_button = findViewById(R.id.save_button);

        setupButtons();
    }

    public void setupButtons(){
        date_button.setOnClickListener(this);
        time_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);
        save_button.setOnClickListener(this);

    }

    public void showDatePickerDialog() {
        android.support.v4.app.DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public void showTimePickerDialog() {
        android.support.v4.app.DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.date_button){
            showDatePickerDialog();
        }
        else if (v.getId() == R.id.time_button){
            showTimePickerDialog();
        }
        else if (v.getId() == R.id.cancel_button){
            Intent tmp = new Intent(getApplicationContext(), RemindersActivity.class);
            startActivity(tmp);
            finish();
        }
        else { //save button has been clicked
            if(validate()){
                Reminder rem = new Reminder(getTimestamp(), description_edit.getText().toString());
                loadFromFile();
                if(reminders != null){
                    reminders.add(rem);
                }
                else{
                    reminders = new ArrayList<Reminder>();
                    reminders.add(rem);
                }
                saveToFile();
                Intent tmp = new Intent(getApplicationContext(), RemindersActivity.class);
                startActivity(tmp);
                finish();
            }
        }
    }

    public boolean validate(){
        return !(date.getText().toString().equals("Date") || time.getText().toString().equals("Time")
                || description_edit.getText().toString().equals(""));
    }

    public long getTimestamp(){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth, hourOfDay, minute);
        return cal.getTime().getTime();
    }

    @Override
    public void onFinishedDatePicker(int year, int month, int dayOfMonth) {
        date.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    public void onFinishedTimePicker(int hourOfDay, int minute) {
        int hourConverted = hourOfDay;
        String minuteConverted = minute + "";
        String ampm = "AM";

        if(hourOfDay == 0){
            hourConverted = 12;
        }
        else if(hourOfDay > 12){
            hourConverted = hourOfDay - 12;
            ampm = "PM";
        }

        if(minute < 10){
            minuteConverted = "0" + minuteConverted;
        }

        time.setText(hourConverted + ":" + minuteConverted + " " + ampm);

        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }


}