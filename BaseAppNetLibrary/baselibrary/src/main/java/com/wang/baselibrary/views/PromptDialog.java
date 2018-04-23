package com.wang.baselibrary.views;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wang.baselibrary.R;
import com.wang.baselibrary.listeners.NoDoubleClickListener;
import com.wang.baselibrary.utils.DensityUtils;
import com.wang.baselibrary.utils.UtilStringEmpty;

/**
 * Created by w on 2018/4/18.
 */

public class PromptDialog extends AlertDialog {

    public PromptDialog(Context context) {
        super(context);

    }

    public PromptDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public PromptDialog(Context context, @StyleRes int themeResId, final String content, final String cancel, final String submit, final NoDoubleClickListener tvCancelOnClickListener, final NoDoubleClickListener tvSubmitlOnClickListener) {
        super(context, themeResId);
        initViews(context,content,cancel,submit,tvCancelOnClickListener,tvSubmitlOnClickListener);
    }

    private void initViews(Context context, final String content, final String cancel, final String submit, final NoDoubleClickListener tvCancelOnClickListener, final NoDoubleClickListener tvSubmitlOnClickListener) {
        try {
            View localView = LayoutInflater.from(context).inflate(R.layout.dialog_prompt, null);
            setContentView(localView);
            getWindow().setGravity(Gravity.CENTER);
            TextView tvSubmit = (TextView) localView.findViewById(R.id.tv_submit);
            TextView tvCancel = (TextView) localView.findViewById(R.id.tv_cancel);
            TextView tvContent = (TextView) localView.findViewById(R.id.tv_content);
            tvContent.setText(content);
            if (UtilStringEmpty.isEmpty(cancel)) {
                tvCancel.setVisibility(View.GONE);
            }else {
                tvCancel.setVisibility(View.VISIBLE);
                tvCancel.setOnClickListener(tvCancelOnClickListener);
                tvCancel.setText(cancel);
            }
            if (UtilStringEmpty.isEmpty(submit)) {
                tvSubmit.setVisibility(View.GONE);
            }else {
                tvSubmit.setVisibility(View.VISIBLE);
                tvSubmit.setOnClickListener(tvSubmitlOnClickListener);
                tvSubmit.setText(submit);
            }
            // 设置全屏
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
}
