package com.awarmisland.android.popularrefreshlayout.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by awarmisland on 2016/9/20.
 */
public class DateUtil {
    /**
     * 获取当前时间
     * @return yyyy-MM-dd
     */
    public static String getNowDate(){
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return  format.format(date);
    }

    /**
     * 上一个月时间
     * @param date 时间
     * @return
     */
    public static String getPreDate(String date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String value = "";
        try {
            Date now_date = df.parse(date);
            Calendar g = Calendar.getInstance();
            g.setTime(now_date);
            g.add(Calendar.MONTH,-1);
            Date pre_date = g.getTime();
            value = df.format(pre_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 时间字符串转换格式
     * @param date 时间
     * @param oldPattern 当前模式
     * @param newPattern 转换模式
     * @return
     */
    public static String formatDate(String date,String oldPattern, String newPattern){
        DateFormat sdf_old = new SimpleDateFormat(oldPattern);
        DateFormat sdf_new = new SimpleDateFormat(newPattern);
        String value = "";
        try {
            if(!StringUtil.isEmpty(date)){
                Date date1 =  sdf_old.parse(date);
                value = sdf_new.format(date1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 时间字符串转换格式
     * @param date 时间
     * @param pattern 转换模式
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 判断选择的日期是否是本周
     * @param time
     * @return
     */
    public static boolean isThisWeek(String time){
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(time));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if(paramWeek==currentWeek){
            return true;
        }
        return false;
    }

    /**
     * 字符串转时间
     * @param date
     * @return
     */
   public static Date str2Date(String date){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       try {
           return sdf.parse(date);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       return null;
   }
    /**
     * 字符串转时间
     * @param date
     * @return
     */
    public static Date str2Date(String date, String pattern){
        if (StringUtil.isEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 判断选择的日期是否是今天
     * @param date
     * @return
     */
    public static boolean isToday(String date) {
    	 date = date.replaceAll(" ","");
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         String now = sdf.format(new Date());//当前时间
         if(date.equals(now)){
             return true;
         }
         return false;
    }
    /**
     * 判断选择的日期是否是本月
     * @param time
     * @return
     */
    public static boolean isThisMonth(String time) {
        Date date = str2Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if(param.equals(now)){
            return true;
        }
        return false;
    }
    /**
     * 增加分钟
     * @param date
     * @param addMin
     * @return
     */
    public static String addMin(Date date,int addMin){
    	 String returnValue = "";
    	 if(date!=null){
    		 try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.MINUTE,addMin);
				Date ret_date = calendar.getTime();
				returnValue = formatDate(ret_date, "yyyy-MM-dd HH:mm");
			} catch (Exception e) {
				// TODO: handle exception
			}
    	 }
    	 return returnValue;
    }
    /**
     * 比较两个时间
     * @param firstDate
     * @param secDate
     * @return
     */
    public static boolean isBefore(Date firstDate,Date secDate){
    	return firstDate.before(secDate);
    }
    /**
     * 比较两个时间
     * @param firstDate
     * @param secDate
     * @return
     */
    public static boolean isBefore(String firstDate,String secDate,String pattern){
    	try {
    	   SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date tmp_first = sdf.parse(firstDate);
			Date tmp_sec = sdf.parse(secDate);
			return tmp_first.before(tmp_sec);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return false;
    }
    /**
     * 比较两个时间
     * @param firstDate
     * @param secDate
     * @return
     */
    public static boolean isBeforeAequals(String firstDate,String secDate,String pattern){
    	try {
    		if(firstDate.equals(secDate)){return true;}
    	   SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date tmp_first = sdf.parse(firstDate);
			Date tmp_sec = sdf.parse(secDate);
			return tmp_first.before(tmp_sec);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return false;
    }
}
