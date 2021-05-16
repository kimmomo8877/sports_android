package com.hiball.gssc.ui.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUserData {
    public static boolean isLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);
        return token != null;
    }

    public static void logout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("userNo");
        editor.remove("token");
        editor.remove("name");
        editor.remove("email");
        editor.apply();
    }

    public static void saveUserInfo(Context context, String userNo, String token) {
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userNo", userNo);
        editor.putString("token", token);
        editor.apply();
    }

    public static String getUserNo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return preferences.getString("userNo", null);
    }

    public static String getEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return preferences.getString("email", null);
    }

    public static String getName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return preferences.getString("name", null);
    }


}
