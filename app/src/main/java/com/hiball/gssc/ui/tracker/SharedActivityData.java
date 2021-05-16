package com.hiball.gssc.ui.tracker;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SharedActivityData {
    private static final String TAG_ACTIVITY_COUNT = "activityCount";
    private static final String TAG_LAST_ACTIVITY_DATE = "lastActivityDate";
    private static final String TAG_HR_COUNT = "activityHrCount";
    private static final String TAG_LAST_HR_DATE = "lastHrDate";

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static int getActivityCount(Context context) {
        String currentDate = SharedActivityData.getCurrentDate();

        SharedPreferences preferences = context.getSharedPreferences("Activity", Context.MODE_PRIVATE);
        String lastDate = preferences.getString(TAG_LAST_ACTIVITY_DATE, null);
        int lastActivityCount = preferences.getInt(TAG_ACTIVITY_COUNT, 0);
        if (lastDate == null) {
            return 0;
        } else if (lastDate.equals(currentDate)) {
            return lastActivityCount;
        } else {
            return 0;
        }
    }

    public static int getHrCount(Context context) {
        String currentDate = SharedActivityData.getCurrentDate();

        SharedPreferences preferences = context.getSharedPreferences("Activity", Context.MODE_PRIVATE);
        String lastDate = preferences.getString(TAG_LAST_HR_DATE, null);
        int lastHrCount = preferences.getInt(TAG_HR_COUNT, 0);
        if (lastDate == null) {
            return 0;
        } else if (lastDate.equals(currentDate)) {
            return lastHrCount;
        } else {
            return 0;
        }
    }

    public static int getGoal(Context context) {
        String currentDate = SharedActivityData.getCurrentDate();
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return preferences.getInt("goal", 0);
    }

    public static void saveActivityCount(Context context, int activityCount) {
        String currentDate = SharedActivityData.getCurrentDate();

        SharedPreferences preferences = context.getSharedPreferences("Activity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String lastActivityDate = preferences.getString(TAG_LAST_ACTIVITY_DATE, null);
        if (lastActivityDate == null) {
            editor.putString(TAG_LAST_ACTIVITY_DATE, currentDate);
            editor.putInt(TAG_ACTIVITY_COUNT, activityCount);
        } else if (lastActivityDate.equals(currentDate)) {
            int savedActivityCount = preferences.getInt(TAG_ACTIVITY_COUNT, 0);
            editor.putInt(TAG_ACTIVITY_COUNT, activityCount + savedActivityCount);
        } else {
            editor.putString(TAG_LAST_ACTIVITY_DATE, currentDate);
            editor.putInt(TAG_ACTIVITY_COUNT, activityCount);
        }

        editor.apply();
    }

    public static void saveHrCount(Context context, int hrCount) {
        String currentDate = SharedActivityData.getCurrentDate();

        SharedPreferences preferences = context.getSharedPreferences("Activity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String lastHrDate = preferences.getString(TAG_LAST_HR_DATE, null);
        if (lastHrDate == null) {
            editor.putString(TAG_LAST_HR_DATE, currentDate);
            editor.putInt(TAG_HR_COUNT, hrCount);
        } else if (lastHrDate.equals(currentDate)) {
            int savedActivityCount = preferences.getInt(TAG_HR_COUNT, 0);
            editor.putInt(TAG_HR_COUNT, hrCount);
        } else {
            editor.putString(TAG_LAST_HR_DATE, currentDate);
            editor.putInt(TAG_HR_COUNT, hrCount);
        }

        editor.apply();
    }
}
