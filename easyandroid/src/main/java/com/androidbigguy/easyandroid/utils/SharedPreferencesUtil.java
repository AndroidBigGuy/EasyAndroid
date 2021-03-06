package com.androidbigguy.easyandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**

             /~~~~~\        /~~~~~\
            |    (~'        ~~~)   |
             \    \__________/    /
             /~::::::::         ~\
  /~~~~~~~-_| ::::::::             |_-~~~~~~~\
 \ ======= /|  ::A::;      A     :|\ ====== /
  ~-_____-~ |  _----------------_::| ~-____-~
            |/~                  ~\|
            /                      \
           (        ()    ()        )
            `\                   ./'
              ~-_______________-~
                     /~~~~\
                    |      |
                    |      |
                   (________)
                       ()
 *  -----------------------------------------------
 * | 作  者：| AndroidBigGuy（QQ295803379）        
 *  -----------------------------------------------
 * | 时  间：| 2018/5/5                             
 *  -----------------------------------------------
 * | 包  名：| com.ls.libarys.utils                     
 *  -----------------------------------------------
 * | 类  名：| SharedPreferencesUtil.java                             
 *  -----------------------------------------------
 * | 简  述: | SharedPreferences工具类                          |
 *  -----------------------------------------------
 */
 


public class SharedPreferencesUtil {


    /**
     *保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param context 上下文环境
     * @param key 键名
     * @param object 存入数据
     */
    public static void putData(Context context ,String FileName, String key, Object object) {
       //获取存入数据类型
        String type = object.getClass().getSimpleName();

        SharedPreferences sp = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.commit();
    }
    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getData(Context context ,String FileName, String key, Object defaultObject){

        String type = defaultObject.getClass().getSimpleName();

        SharedPreferences sp = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }
    /**
     * 保存List
     */
    public static  void setDataList(Context context, String FileName, String key, List<String> datalist) {
        SharedPreferences sp = context.getSharedPreferences(FileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.putString(key, strJson);
        editor.commit();

    }

    /**
     * 获取List
     * @param key
     * @return
     */
    public static ArrayList<String> getDataList(Context context, String FileName, String key) {
        SharedPreferences sp = context.getSharedPreferences(FileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        ArrayList<String> datalist=new ArrayList<String>();
        String strJson = sp.getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
        return datalist;

    }
    public static void cleanData(Context context ,String FileName){
        SharedPreferences sp = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
