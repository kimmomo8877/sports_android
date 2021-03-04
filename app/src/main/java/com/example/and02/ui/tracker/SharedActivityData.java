package com.example.and02.ui.tracker;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SharedActivityData {
    private static final String TAG_ACTIVITY_COUNT = "activityCount";
    private static final String TAG_LAST_DATE = "lastActivityDate";

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static int getActivityCount(Context context) {
        String currentDate = SharedActivityData.getCurrentDate();

        SharedPreferences preferences = context.getSharedPreferences("Activity", Context.MODE_PRIVATE);
        String lastDate = preferences.getString(TAG_LAST_DATE, null);
        int lastActivityCount = preferences.getInt(TAG_ACTIVITY_COUNT, 0);
        if (lastDate == null) {
            return 0;
        } else if (lastDate.equals(currentDate)) {
            return lastActivityCount;
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
        String lastActivityDate = preferences.getString("lastActivityDate", null);
        if (lastActivityDate == null) {
            editor.putString("lastActivityDate", currentDate);
            editor.putInt("activityCount", activityCount);
        } else if (lastActivityDate.equals(currentDate)) {
            int savedActivityCount = preferences.getInt("activityCount", 0);
            editor.putInt("activityCount", activityCount + savedActivityCount);
        } else {
            editor.putString("lastActivityDate", currentDate);
            editor.putInt("activityCount", activityCount);
        }

        editor.apply();
    }
}
