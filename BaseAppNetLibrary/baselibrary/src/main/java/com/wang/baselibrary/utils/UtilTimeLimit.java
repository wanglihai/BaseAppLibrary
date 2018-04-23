package com.wang.baselibrary.utils;

/**
 * Created by Administrator on 2017/a_1/a_1.
 */

public class UtilTimeLimit {
//    public static final int MIAO=1000;
    public static final int FEN=60*1000;

    public static final int HOUR=60*60*1000;
    public static String format(long s){
        long hour = s/HOUR;;
        long minute=(s-HOUR*hour)/FEN;
        if (s>=(60*60*1000+60*1000)){
            return hour+"小时"+minute+"分钟";
        }
        if (s>=(60*60*1000)&&s<=(60*60*1000+60*1000)){
            return hour+"小时";
        }
        if (s<=(60*60*1000)&&s>=60*1000){
            return minute+"分钟";
        }
        if (s<=60*1000){
            return 1+"分钟";
        }
        return 0+"";
    }
}
