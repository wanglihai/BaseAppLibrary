package com.wang.baselibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * 跳转到手机的各个系统页面
 * Created by w on 2017/11/22.
 */

public class SettingsActivity {

    /**
     * 去软件的权限及其他的系统设置页面
     * @param context
     */
    public static void goToAPPSetting(Context context){
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }

    /**
     * 去GPS系统设置页面
     * @param context
     */
    public static void goToGPS(Context context){
        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    /**
     * 去往移动网络设置页面
     * @param context
     */
    public static void goTo4G(Context context){
        context.startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
    }
    /**
     * 去往无线网络设置页面
     * @param context
     */
    public static void goToWIFI(Context context){
        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    /**
     * 去往蓝牙设置页面
     * @param context
     */
    public static void goToBLUETOOTH(Context context){
        context.startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
    }

    /**
     * 去往声音设置页面
     * @param context
     */
    public static void goToSOUND(Context context){
        context.startActivity(new Intent(Settings.ACTION_SOUND_SETTINGS));
    }

    /**
     * 去往打电话
     * @param context
     */
    public static void goToCall(Context context, String telePhone){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + telePhone));
//        "010-52088895"
        context.startActivity(intent);
    }
}
