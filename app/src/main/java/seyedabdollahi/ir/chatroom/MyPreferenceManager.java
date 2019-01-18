package seyedabdollahi.ir.chatroom;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import seyedabdollahi.ir.chatroom.Keys.Keys;

public class MyPreferenceManager {
    private static MyPreferenceManager instance = null;
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public static MyPreferenceManager getInstance(Context context){
        if (instance == null){
            instance = new MyPreferenceManager(context);
        }
        return instance;
    }

    private MyPreferenceManager(Context context){
        sharedPreferences = context.getSharedPreferences("my_preference" , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getUserName(){
        return sharedPreferences.getString(Keys.USERNAME , null);
    }

    public void putUserName(String username){
        editor.putString(Keys.USERNAME , username);
        editor.apply();
    }

    public String getAccessToken(){
        return sharedPreferences.getString(Keys.ACCESS_TOKEN , null);
    }

    public void putAccessToken(String tokenAccess){
        editor.putString(Keys.ACCESS_TOKEN, tokenAccess);
        editor.apply();
    }

    public void putRealSize(Point point){
        editor.putInt(Keys.WIDTH, point.x);
        editor.putInt(Keys.HEIGHT , point.y);
        editor.apply();
    }

    public Point getRealSize(){
        Point point = new Point();
        point.x = sharedPreferences.getInt(Keys.WIDTH , 1);
        point.y = sharedPreferences.getInt(Keys.HEIGHT , 1);
        return point;
    }

    public void putScaledDensity(float scaledDensity){
        editor.putFloat(Keys.SCALED_DENSITY , scaledDensity);
        editor.apply();
    }

    public float getScaledDensity(){
        return sharedPreferences.getFloat(Keys.SCALED_DENSITY , 1);
    }

    public void clearEverything(){
        editor.clear();
        editor.apply();
    }
}
