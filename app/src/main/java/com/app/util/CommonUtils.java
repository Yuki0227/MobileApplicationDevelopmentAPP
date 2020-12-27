package com.app.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CommonUtils {

    /**
     * 显示短消息
     * @param context 上下文
     * @param msg 要显示的消息
     */
    public static void showShortMsg(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

    }

    /**
     * 显示长消息
     * @param context 上下文
     * @param msg 要显示的消息
     */
    public static void showLongMsg(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    /**
     * 显示消息对话框
     * @param context    上下文
     * @param msg 要显示的消息
     */
    public static void showDlgMsg(Context context,String msg){
        new AlertDialog.Builder(context)
                .setTitle("显示信息")
                .setMessage(msg)
                .setPositiveButton("确定",null)
                .create().show();
    }

    /**
     * 将日期字符串转换成日期
     * @param dateString 要转化的日期字符串
     * @return  转化后的日期
     */
    public static Date StringToDate(String dateString){
        if(dateString == null) return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        Date date = null;
        try {
            d = format.parse(dateString);
            if(d != null){
                date = new Date(d.getTime());
            }else{
                return null;
            }
        } catch (ParseException e) {
           // e.printStackTrace();
        }
        return date;
    }
}