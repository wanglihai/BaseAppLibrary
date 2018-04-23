package com.wang.baselibrary.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.baselibrary.R;
import com.wang.baselibrary.listeners.NoDoubleClickListener;
import com.wang.baselibrary.views.PromptDialog;

/**
 * Created by w on 2017/11/16.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivitySelf;
    public LayoutInflater mLayoutInflater;
    public View mContentView = null;
    public FragmentManager mFragmentManager;
    public BaseFragment mFragmentSelf;

    @Override
    public void onAttach(Context context) {//Modified 2016-06-01</span>
        super.onAttach(context);
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            mActivitySelf.addFragment(fragment);
        }
    }

    //移除fragment
    protected void removeFragment() {
        mActivitySelf.removeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        initView(view, savedInstanceState);
        mFragmentManager = this.getFragmentManager();
        mFragmentSelf = this;
        mActivitySelf = (BaseActivity) this.getActivity();
        mLayoutInflater = inflater;
        mContentView = mLayoutInflater.inflate(setRootView(), container, false);
        initViews();
        initDatas();
        initListeners();
        return mContentView;
    }

    public View findViewById(int resID) {
        return mContentView.findViewById(resID);
    }


    //------------标题栏操作
    public abstract int setRootView();

    public abstract void initViews();

    public abstract void initDatas();

    public abstract void initListeners();

    //获取布局文件ID
    protected abstract int getLayoutId();

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void toActivityResult(Intent intent, int requestCode) {
        toActivity(intent, requestCode);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     */
    public void toActivity(final Intent intent, final int requestCode) {
        if (intent == null) {
            return;
        }
        //fragment中使用mActivitySelf.startActivity会导致在fragment中不能正常接收onActivityResult
        if (requestCode < 0) {
            startActivity(intent);
        } else {
            startActivityForResult(intent, requestCode);
        }
    }

    private PromptDialog promptDialog;//提示弹框

    /**
     * 封装提示框
     *
     * @param content   上下文对象
     */
    public void doShowSurePromptDialog(String content) {
        showPromptDialog(content, null, "确定", null, new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                dismissPromptDialog();
            }
        });
    }

    /**
     * 封装提示框
     *
     * @param content 上下文对象
     */
    public void doShowPromptDialog(String content) {
        showPromptDialog(content, "取消", "确定", new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                dismissPromptDialog();
            }
        }, new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                dismissPromptDialog();
            }
        });
    }

    /**
     * 提示弹框
     *
     * @param content  上下文对象
     * @param cancel   取消
     * @param submit    确定
     * @param tvCancelOnClickListener   取消的监听
     * @param tvSubmitlOnClickListener  点击确定的监听
     */
    public void showPromptDialog(final String content, final String cancel, final String submit, final NoDoubleClickListener tvCancelOnClickListener, final NoDoubleClickListener tvSubmitlOnClickListener) {
        promptDialog = new PromptDialog(mActivitySelf, R.style.custom_dialog, content, cancel, submit, tvCancelOnClickListener, tvSubmitlOnClickListener);
        promptDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (promptDialog != null && promptDialog.isShowing()) {
                        promptDialog.dismiss();
                        promptDialog = null;
                    }
                }
                return false;
            }
        });
        promptDialog.show();
    }

    /**
     * 销毁提示弹框
     */
    public void dismissPromptDialog() {
        if (promptDialog != null && promptDialog.isShowing()) {
            promptDialog.dismiss();
            promptDialog = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissPromptDialog();
    }

}
