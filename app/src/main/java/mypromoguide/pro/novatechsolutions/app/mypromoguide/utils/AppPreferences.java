package mypromoguide.pro.novatechsolutions.app.mypromoguide.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {


    private static final AppPreferences instance = new AppPreferences();

    private static final String SETTINGS_NAME = "default_settings";
    private  static SharedPreferences mPref;
    private  static SharedPreferences.Editor mEditor;



    public  static AppPreferences NewInstance(Context context) {
        mPref =  context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        return instance;
    }

    public boolean IsfirstTimeRun() {
        return mPref.getBoolean("IsFirstTime", true);
    }

    public void  UnsetFirstTineRun() {
        mEditor.putBoolean("IsFirstTime", false).commit();
    }

    public void setAuthOTP(int otp) {
        if(mPref.contains("otp"))
            mEditor.remove("otp").commit();

        mEditor.putInt("otp", otp).commit();
    }

    public int  getAuthOTP(){
        return mPref.getInt("otp", 0);
    }

    public void saveUserInfos(String user){
        if(mPref.contains("user"))
            mEditor.remove("user").commit();

        mEditor.putString("user", user).commit();
    }

    public String  getUser(){
        return mPref.contains("user") ? mPref.getString("user", ""):"";
    }


}
