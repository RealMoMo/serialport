package com.newline.serialport.dao;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Administrator on 2018/4/4.
 *
 */

public abstract class SettingDAO extends HHTContentProvider {
    public static final int CLEAR_TYPE_EVERY_TIME = 0;


    public static final int DEFAULT_HOME_SCREEN = 0;
    public static final int DEFAULT_WINDOWS_TYPE = 1;
    public static final int DEFAULT_LAST_KNOW_SOURCE = 2;
    public static final int DEFAULT_FOVARITE_SOURCE = 3;
    public static final int OEM_HOME_SCREEN = 0;
    public static final int OEM_LAST_KNOW_SOURCE = 1;
    public static final int OEM_FOVARITE_SOURCE = 2;
    /**
     * 获取收藏Source
     * @param context
     * @return
     */
    public static String getFavoriteSourceFlag(Context context) {
        return doQueryString(context, SETTING_CONTENT_PROVIDER_URI, FAVORITE_SOURCE_NAME);
    }

    public static int getClearDateFromProvide(Context context) {
        return doQuery(context, SETTING_CONTENT_PROVIDER_URI, FILED_CLEAR_WHITEBROAD_SCREENSHOT);
    }

    public static int getLastKnowSystemFlag(Context context) {
        return Settings.System.getInt(context.getContentResolver(), LAST_KNOW_SYSTEM_FLAG, -1);
    }

    public static boolean isPasswordEnabled(Context context) {
        return doQuery(context, SETTING_CONTENT_PROVIDER_URI, ENABLE_SCREEN_LOCK) == 1;
    }

    public static int getBackLightMode(Context context) {
        return doQuery(context, SETTING_CONTENT_PROVIDER_URI, CUR_ECO_MODE);
    }

    public static void setBackLightModeOnCustom(Context context) {
        doUpdate(context, SETTING_CONTENT_PROVIDER_URI, CUR_ECO_MODE, 2);
    }

    public static void setAutoSource(Context context, boolean isAuto) {
        doUpdate(context, SETTING_CONTENT_PROVIDER_URI, B_NEW_INPUT_SOURCE, isAuto ? 1 : 0);
    }

    public static int getAutoSource(Context context) {
       return doQuery(context, SETTING_CONTENT_PROVIDER_URI, B_NEW_INPUT_SOURCE);
    }

    //0是hide 1是show
    public static void setZ5ToolBarAppear(Context context, boolean z5ToolBarAppear) {
        doUpdate(context, SETTING_CONTENT_PROVIDER_URI, FIELD_LEFT_FLOATBAR_SHOW_HIDE, z5ToolBarAppear? 1 : 0);
        doUpdate(context, SETTING_CONTENT_PROVIDER_URI, FIELD_RIGHT_FLOATBAR_SHOW_HIDE, z5ToolBarAppear? 1 : 0);

    }

    public static void setPictureMode(Context context, int value) {
        doUpdate(context, SOURCE_DISPLAY_RATIO_SETTING_CONTENT_PROVIDER_URI, FIELD_E_PICTURE, value);
    }

    public static int getPictureMode(Context context) {
        return doQuery(context, SOURCE_DISPLAY_RATIO_SETTING_CONTENT_PROVIDER_URI, FIELD_E_PICTURE);
    }
}
