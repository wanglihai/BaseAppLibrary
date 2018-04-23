package com.wang.baselibrary.utils;

import android.widget.EditText;

/**
 * Created by Administrator on 2016/3/30.
 */
public class UtilEdt {

    public static  boolean isEdtEmpty(EditText editText){
        if (editText==null){
            return  true;
        }
        String text=editText.getText().toString().trim();
        return  UtilStringEmpty.isEmpty(text);
    }


    public static String getEdtText(EditText editText){
        if (editText==null){
            return  null;
        }
        String text=editText.getText().toString().trim();
        return  text;
    }
}
