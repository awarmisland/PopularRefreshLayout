package com.awarmisland.android.popularrefreshlayout.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.awarmisland.android.popularrefreshlayout.Config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;


/**
 * SharedPreferences 工具
 * Created by Administrator on 2016/9/6.
 */
public class SharedPreferUtil {
    private static Context context;
    private final static String TAG = "SharedPreferUtil";
    /**
     * 初始化context
     */
    static{
        getContext();
    }

    /**
     * 获取全局context
     * @return
     */
    public static Context getContext(){
        if(context==null){
            context= Config.mContext;
        }
        return context;
    }
    /**
     * 获取sp中存值
     * @param fileName 保存文件名
     * @param key 保存keyName
     * @return
     */
    public static String getString(String fileName,String key){
        try{
            SharedPreferences sp=context.getSharedPreferences(fileName, context.MODE_PRIVATE);
            return sp.getString(key, "");
        }catch(Exception e){
            return null;
        }
    }
    /**
     * 写入sp缓存
     * @param fileName xml名字
     * @param key 键值
     * @param KeyValue
     */
    public static void setString(String fileName,String key,String KeyValue){
        try {
            SharedPreferences sp=context.getSharedPreferences(fileName, context.MODE_PRIVATE);
            SharedPreferences.Editor ed=sp.edit();
            ed.putString(key, KeyValue);
            ed.commit();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    /**
     * 保存对象
     * @param fileName 文件名
     * @param key 键值
     * @param cls 转换类型
     * @return
     */
    public static  <T> T getObject(String fileName,String key,Class<T> cls) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            String objectVal = sp.getString(key, null);
            byte[] buffer = Base64.decode(objectVal, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取对象
     * @param fileName 文件名
     * @param key 键值
     * @param object 对象
     */
    public static void setObject(String fileName,String key,Object object){
        SharedPreferences sp = context. getSharedPreferences(fileName, Context.MODE_PRIVATE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(key, objectVal);
            editor.commit();
        } catch (IOException e) {
           
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
               
            }
        }
    }

    /**
     * 保存list
     * @param fileName
     * @param key
     * @param list
     */
    public static void setList(String fileName, String key, List list){
        try {
            String obj_str = GsonUtil.getJson(list);
            setString(fileName, key, obj_str);
        }catch (Exception e){}
    }

    /**
     * 返回list
     * @param fileName
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> getList(String fileName,String key,Class<T> cls){
        try {
            String obj_str = getString(fileName, key);
            return GsonUtil.getGsonList(obj_str, cls);
        }catch (Exception e){return null;}
    }
    /**
     * 移除某个key
     * @param fileName 文件名
     * @param key 键值
     */
    public static void removeKey(String fileName,String key){
        try{
            SharedPreferences sp=context.getSharedPreferences(fileName, context.MODE_PRIVATE);
            SharedPreferences.Editor ed=sp.edit();
            ed.remove(key);
            ed.commit();
        }catch(Exception e){}
    }
    /**
     * 移除sp中存储内容
     * @param fileName 文件名
     */
    public static void removeFile(String fileName) {
        try{
            SharedPreferences sp=context.getSharedPreferences(fileName, context.MODE_PRIVATE);
            SharedPreferences.Editor ed=sp.edit();
            ed.clear();
            ed.commit();
        }catch(Exception e){}
    }
}
