package com.awarmisland.android.popularrefreshlayout.utils;

/**
 * Created by Administrator on 2016/9/6.
 */
public class StringUtil {

    public static boolean isEmpty(Object obj){
        if(obj != null && !obj.toString().trim().isEmpty()&&!"null".equals(obj.toString())) {
            return false;
        }
        return true;
    }
}
