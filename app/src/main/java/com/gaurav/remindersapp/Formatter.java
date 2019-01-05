package com.gaurav.remindersapp;

import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Formatter {
    private static final String TAG = "Formatter";
    private static String recentDatePattern; //Date that happened this year
    private static String datePattern; //Older dates
    public static boolean militaryTime = false;


    public static void setupTimeSettings(){

        Locale current = Locale.getDefault();

        if (current.equals(Locale.US) ){
            recentDatePattern = "MMM d";
            datePattern = "M/d/yy";

        }
        else if (current.equals(Locale.CANADA)){
            recentDatePattern = "MMM d";
            datePattern = "d/M/yy";
        }
        else{
            recentDatePattern = "M/d";
            datePattern = "yyyy/M/d";
        }

    }


    public static String getDate(Long time){

        if (time == null)
            return "";

        Date today = new Date(System.currentTimeMillis());
        Date reminderDate = new Date(time);


        if(today.toString().substring(4).equals(reminderDate.toString().substring(4))){   //if it's the same year
            SimpleDateFormat df = new SimpleDateFormat(recentDatePattern);
            return df.format(reminderDate);
        }
        else{
            SimpleDateFormat df = new SimpleDateFormat(datePattern);
            return df.format(reminderDate);
        }

    }

    public static String getTime(Long time) {
        if (time == null)
            return "";

        String pattern;

        if (militaryTime)
            pattern = "HH:mm";
        else
            pattern = "hh:mm a";

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date(time));

    }
}
