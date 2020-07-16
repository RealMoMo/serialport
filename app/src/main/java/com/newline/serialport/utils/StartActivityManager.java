package com.newline.serialport.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.hht.middleware.model.SourceValue;

import com.newline.serialport.SerialPortService;
import com.hht.tools.invalid.NullObjects;
import com.hht.tools.log.Logger;
import com.hht.tools.log.SystemPropertiesValue;


import java.lang.reflect.Method;

public class StartActivityManager {
    public static final String PACKAGE_NAME_FILE_COMMANDER = "com.mobisystems.fileman";
    public static final String ACTION_LAUNCHER = "com.hht.action.Launcher";
    public static final String CATEGORY_LAUNCHER = "com.hht.category.Launcher";

    public static final String LAUNCHER_NAME = "launcher_name";
    public static final String LAUNCHER_SOURCE = "launcher_source";
    public static final String LAUNCHER_APPS = "launcher_apps";

    public static final String ACTION_FULLSCREEN_SOURCE = "com.hht.action.Source.FullScreen";
    public static final String CATEGORY_FULLSCREEN_SOURCE = "com.hht.category.Source";
    public static final String KEY_SOURCE_FLAG = "source_flag";

    public static final String TW_WHITEBOARD_PACKAGE = "tw.com.hitevision.whiteboard";
    public static final String TW_WHITEBOARD_CLASS = "tw.com.hitevision.whiteboard.android.MainActivity";
    public static final String KEY_TW_WHITEBOARD_FOR_ANNOTATION = "extraAnnotationMode";
    public static final String SZ_WHITEBOARD_PACKAGE = "com.paike.zjc";
    public static final String SZ_WHITEBOARD_CLASS = "com.paike.zjc.ZWhiteBoardActivity";

    public static final String URI_PATH = "https://www.google.com/webhp?client=android-google&source=android-home";

    public static final String ACTION_SEND_SERIAL_PORT = "com.hht.action.SERIAL";
    public static final String KEY_CONTENT = "content";
    public static final String PACKAGE_NEWLINE_CAST = "com.displaynote.newlinecast";
    public static final String PACKAGE_NEWLINE_BROADCAST = "com.newline.broadcastapp";

    @Deprecated
    public static final String ACTION_UPDATE_MIC_GPIO = "com.hht.action.GPIO.MIC";

    public static void openApp(Context context, String packageName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public static void closeApp(Context context, String packageName) {
        Logger.i("packageName = "+packageName);
        try {
            ActivityManager activityMgr = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
            method.invoke(activityMgr, packageName);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public static void openApp(Context context, String action, String category) {
        try {
            Intent intent = new Intent(action);
            if (!NullObjects.stringNull(category)) {
                intent.addCategory(category);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public static void openBrowser(Context context, String uriPath) {
        try {
            Uri uri = Uri.parse(uriPath);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public static void startTWWhiteboard(Context context) {
//        Intent i = new Intent();
//        i.setClassName(TW_WHITEBOARD_PACKAGE, TW_WHITEBOARD_CLASS);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);


        Intent i = new Intent();
        String packageName = (String) SystemPropertiesValue.getProperty("persist.hht.whiteboard","tw.com.hitevision.whiteboard");

        i.setClassName(packageName, packageName+".android.MainActivity");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void startTWAnnotation(Context context) {
//        Intent i = new Intent();
//        i.setClassName(TW_WHITEBOARD_PACKAGE, TW_WHITEBOARD_CLASS);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.putExtra(KEY_TW_WHITEBOARD_FOR_ANNOTATION, true);
//        context.startActivity(i);


        Intent i = new Intent();
        String packageName = (String) SystemPropertiesValue.getProperty("persist.hht.whiteboard","tw.com.hitevision.whiteboard");

        i.setClassName(packageName, packageName+".android.MainActivity");
        i.putExtra(KEY_TW_WHITEBOARD_FOR_ANNOTATION, true);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void startLauncher(Context context, String extra) {
        Intent intent =new Intent(ACTION_LAUNCHER);
        intent.addCategory(CATEGORY_LAUNCHER);
        intent.putExtra(LAUNCHER_NAME, extra);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 跳转 Ops信号源
     */
    public static void startFullSource(Context context, String name) {
        Logger.i("ordinal = "+ name);
        Intent intent = new Intent(ACTION_FULLSCREEN_SOURCE);
        intent.addCategory(CATEGORY_FULLSCREEN_SOURCE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_SOURCE_FLAG, name);
        context.startActivity(intent);
    }


    /**
     * 跳转 Ops信号源
     */
    public static void startFullSource(Context context, int ordinal) {
        Logger.i("ordinal = "+ ordinal);
        Intent intent = new Intent(ACTION_FULLSCREEN_SOURCE);
        intent.addCategory(CATEGORY_FULLSCREEN_SOURCE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(KEY_SOURCE_FLAG, SourceValue.getSourceByOrdinal(ordinal).name);
        context.startActivity(intent);
    }

    public static void startSerialPort(Context context) {
        Intent intent = new Intent(context, SerialPortService.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(intent);
    }

    public static void openPause(Context context) {
        Logger.i("start pause");
        Intent intent = new Intent("com.hht.intent.action.freezescreen");
        intent.setPackage("com.hht.freezescreen");
        context.startActivity(intent);
    }



    public static void startSettingsAbout(Context context) {
        Logger.i("启动 android.settings.DEVICE_INFO_SETTINGS");
        Intent intent = new Intent("android.settings.DEVICE_INFO_SETTINGS");
        intent.setPackage("com.android.settings");
        context.startActivity(intent);
    }

    public void startFactoryMenu(Context context) {
        try {
            Intent intent = new Intent();
            if (SourceValue.isMS848()) {
                intent.setComponent(new ComponentName("com.hht.factory","com.hht.factory.main.ui.MainActivity"));
            } else {
                intent.setAction("mstar.factorymenu.ui/mstar.tvsetting.factory.ui.designmenu.DesignMenuActivity");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public void startLangoSourceSetting(Context context) {
        Intent intent = new Intent("com.hisilicon.android.intent.action.tvsetting");
        context.startActivity(intent);
    }
}
