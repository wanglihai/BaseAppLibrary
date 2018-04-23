package com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.wzgiceman.rxretrofitlibrary.R;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.DensityUtils;


/**
 * Created by w on 2017/10/25.
 */

public class ProgressAlertDialog extends Dialog {

    public ProgressAlertDialog(@NonNull Context context) {
        super(context);
    }

    public ProgressAlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        try {
            View localView = LayoutInflater.from(context).inflate(R.layout.dialog_do_net, null);
            setContentView(localView);
            getWindow().setGravity(Gravity.CENTER);
            // 设置全屏
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.width = DensityUtils.dip2px(context, DensityUtils.px2dip(context, displayMetrics.widthPixels - 100)); // 设置宽度
            getWindow().setAttributes(lp);
            setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ProgressAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
