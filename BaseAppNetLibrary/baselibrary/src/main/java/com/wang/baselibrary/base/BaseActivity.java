package com.wang.baselibrary.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wang.baselibrary.R;
import com.wang.baselibrary.listeners.NoDoubleClickListener;
import com.wang.baselibrary.utils.ToolUtils;
import com.wang.baselibrary.views.PromptDialog;


/**
 * Created by w on 2017/11/16.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = false;

    /**
     * 该Activity实例，命名为mActivitySelf是因为大部分方法都只需要mActivitySelf，写成mActivitySelf使用更方便
     *
     * @warn 不能在子类中创建
     */
    protected BaseActivity mActivitySelf = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        UtilTypeFace.setTyprFace(this);/*设置字体*/
        super.onCreate(savedInstanceState);
        //判断是否允许全屏
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        ActivityControl.add(this);
        mActivitySelf = this;
        isAlive = true;
        setContentView(setRootView());
        initSystemBarTint();
        initViews();
        initDatas();
        initListener();
    }

    /** 子类可以重写改变状态栏颜色 */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /** 子类可以重写决定是否使用透明状态栏 */
    protected boolean translucentStatusBar() {
        return false;
    }

    /** 设置状态栏颜色 */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            /**
             * 设置状态栏透明并使带有虚拟键的手机虚拟键保持黑色(不支持API21版本以下---也就是Android4.4版本及以下版本)
             */
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /** 获取主题色 */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (ToolUtils.isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(mActivitySelf.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 是否允许全屏开放一个方法供子类调用
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * 在UI线程中运行，建议用这个方法代替runOnUiThread
     *
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (isAlive() == false) {
            return;
        }
        runOnUiThread(action);
    }

    private boolean isAlive = false;


    public final boolean isAlive() {
        return isAlive && mActivitySelf != null;// & ! isFinishing();导致finish，onDestroy内runUiThread不可用
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private PromptDialog promptDialog;//提示弹框

    /**
     * 封装提示框
     *
     * @param content
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
     * @param content
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
     * @param content
     * @param cancel
     * @param submit
     * @param tvCancelOnClickListener
     * @param tvSubmitlOnClickListener
     */
    public void showPromptDialog(final String content, final String cancel, final String submit, final NoDoubleClickListener tvCancelOnClickListener, final NoDoubleClickListener tvSubmitlOnClickListener) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
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
        });
    }

    /**
     * 销毁提示弹框
     */
    public void dismissPromptDialog() {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (promptDialog != null && promptDialog.isShowing()) {
                    promptDialog.dismiss();
                    promptDialog = null;
                }
            }
        });
    }


    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity1(intent, -1);
    }

    /**
     * 打开新的Activity，向左滑入效果
     *
     * @param intent
     * @param requestCode
     */
    public void toActivityforResult(Intent intent, int requestCode) {
        toActivity1(intent, requestCode);
    }

    /**
     * 打开新的Activity
     *
     * @param intent
     * @param requestCode
     */
    public void toActivity1(final Intent intent, final int requestCode) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
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
        });
    }

    @Override
    public void finish() {
        super.finish();//必须写在最前才能显示自定义动画
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityControl.remove(this);
        dismissPromptDialog();
//        dismissWaitDialog();
//        waitDialog=null;
        isAlive = false;
//        promptDialog=null;
    }

    //标题栏操作
    public abstract int setRootView();

    //初始化View控件
    public abstract void initViews();

    //初始化数据
    public abstract void initDatas();

    public abstract void initListener();

    //    /** View点击 **/
//    public abstract void widgetClick(View v);
    //    //布局文件ID
//    protected abstract int getContentViewId();
    //布局中Fragment的ID
    protected abstract int getFragmentContentId();
}
