package com.comsince.github.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liaojinlong on 19-3-7.
 */

public class PreferenceUtil {

    private static SharedPreferences getSharePerferenceByName(Context context, String name){
        //http://zmywly8866.github.io/2015/09/09/sharedpreferences-in-multiprocess.html
        //多进程数据不共享,建议升级pushSDK3.5.0以上版本
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * 保存String类型的数据
     * @param context
     * @param preferenceName
     * @param key
     * @param value
     *
     * */
    public static void putStringByKey(Context context,String preferenceName,String key,String value){
        SharedPreferences sharedPreferences = getSharePerferenceByName(context, preferenceName);
        sharedPreferences.edit().putString(key,value).apply();
    }

    public static void putToken(Context context,String token){
       putStringByKey(context,"push","push-token",token);
    }

    public static String getStringBykey(Context context,String preferenceName,String key){
        return getSharePerferenceByName(context,preferenceName).getString(key,"");
    }

    public static String getToken(Context context){
        return getStringBykey(context,"push","push-token");
    }
}
