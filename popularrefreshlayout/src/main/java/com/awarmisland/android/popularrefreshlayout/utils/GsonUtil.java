package com.awarmisland.android.popularrefreshlayout.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * json解析类
 * Created by d on 2016/9/3.
 */
public class GsonUtil {

    private static Gson mGson;
    static {
        mGson = new Gson();
    }
    public static  <T> T getGsonObject(String data, Class<T> mClass) {
        if(data == null){
            return null;
        }
        T result = mGson.fromJson(data, mClass);
        return result;
    }
    /**
     * 返回json解析后list
     * @param data json字符串
     * @param mClass 目标类型class
     * @param <T> 目标类型
     * @return
     */
    public static  <T>  List<T> getGsonList(String data, Class<T> mClass) {
        if(data==null) {
            return null;
        }
        return mGson.fromJson(data, new ListOfSomething<T>(mClass));
    }

    /**
     * 转成json
     * @param obj
     * @return
     */
    public static String  getJson(Object obj){
        if(obj==null) {
            return null;
        }
        return mGson.toJson(obj);
    }
    /**
     * Gson type类型转换
     * @param <X>
     */
    private static class ListOfSomething<X> implements ParameterizedType {
        private Class<?> wrapped;

        public ListOfSomething(Class<X> wrapped) {
            this.wrapped = wrapped;
        }
        public Type[] getActualTypeArguments() {
            return new Type[] {wrapped};
        }
        public Type getRawType() {
            return List.class;
        }
        public Type getOwnerType() {
            return null;
        }
    }
}
