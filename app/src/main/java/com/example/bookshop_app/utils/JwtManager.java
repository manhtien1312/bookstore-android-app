package com.example.bookshop_app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class JwtManager {

    public static final String PREFS_NAME = "jwt_prefs";
    public static final String KEY_JWT_TOKEN = "jwt_token";
    public static String CURRENT_TOKEN;

    public static void saveToken(Context context, String token){
        CURRENT_TOKEN = token;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_JWT_TOKEN, token);
        editor.apply();
    }

    public static String getToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(KEY_JWT_TOKEN, null);
        CURRENT_TOKEN = token;
        return token;
    }

    public static void removeToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_JWT_TOKEN);
        editor.apply();
    }

}
