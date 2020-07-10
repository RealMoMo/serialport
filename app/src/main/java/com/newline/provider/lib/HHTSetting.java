package com.newline.provider.lib;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTProvider
 * @email momo.weiye@gmail.com
 * @time 2020/1/4 14:18
 * @describe
 */
public class HHTSetting {

    private static final String TAG = "provider_lib";

    public static final String TRUE_STR = "1";
    public static final String FALSE_STR = "0";

    public static final String STR_DEFAULT = "default";
    public static final String INT_DEFAULT = "-1";

    private static final int TRUE_VALUE = 1;
    private static final int FALSE_VALUE = 0;



    public static final class System extends NameValueTable {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/system");


        public static String getString(ContentResolver resolver, String name) {
            return getString(CONTENT_URI, resolver, name);
        }


        public static boolean putString(ContentResolver resolver, String name, String value) {

            return putString(resolver, CONTENT_URI, name, value);
        }


        public static int getInt(ContentResolver resolver, String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name));
        }

        public static boolean putInt(ContentResolver resolver, String name, int value) {
            return putString(resolver, CONTENT_URI, name, value + "");
        }

        public static boolean getBoolean(ContentResolver resolver, String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name)) == TRUE_VALUE;
        }

        public static boolean putBoolean(ContentResolver resolver, String name, boolean value) {
            return putString(resolver, CONTENT_URI, name, value ? TRUE_STR : FALSE_STR);
        }

        public static Uri getUriFor(String name) {

            return getUriFor(CONTENT_URI, name);
        }


        /**
         * 主题类型
         */
        public static final String THEME_TYPE = "theme_type";
        /**
         * 主题资源包路径
         */
        public static final String THEME_RES_PATH = "theme_res_path";
        /**
         * 日期格式
         */
        public static final String DATE_FORMAT = "date_format";

        /**
         * 是否显示关机提示
         */
        public static final String SHUT_DOWN_REMINDER_ENABLE = "shut_down_reminder_enable";

        /**
         * 是否显示logo
         */
        public static final String LOGO_ENABLE = "logo_enable";

        /**
         * 关机意图
         */
        public static final String POWER_OFF_OPTION = "power_off_option";

        /**
         * 假待机唤醒方式
         */
        public static final String STANDBY_AWAKE_TYPE = "standby_awake_type";

        /**
         * 是否处于假待机状态
         */
        public static final String IS_STANDBY = "is_standby";
    }


    public static final class Secure extends NameValueTable {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/secure");


        public static String getString( ContentResolver resolver,  String name) {
            return getString(CONTENT_URI, resolver, name);
        }


        public static boolean putString( ContentResolver resolver,  String name,  String value) {

            return putString(resolver, CONTENT_URI, name, value);
        }


        public static int getInt( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name));
        }

        public static boolean putInt( ContentResolver resolver,  String name, int value) {
            return putString(resolver, CONTENT_URI, name, value + "");
        }

        public static boolean getBoolean( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name)) == TRUE_VALUE;
        }

        public static boolean putBoolean( ContentResolver resolver,  String name, boolean value) {
            return putString(resolver, CONTENT_URI, name, value ? TRUE_STR : FALSE_STR);
        }

        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }

        /**
         * 锁屏万能密码
         */
        public static final String LOCK_SCREEN_MASTER_PASSKEY = "lock_screen_master_passkey";
        /**
         * 锁屏密码
         */
        public static final String LOCK_SCREEN_PASSKEY = "lock_screen_passkey";
        /**
         * 是否在锁屏界面中
         */
        public static final String LOCK_SCREEN_STATUS = "lock_screen_status";
        /**
         * QuickSetting是否可用
         */
        public static final String QUICKSETTING_ENABLE = "quicksetting_enable";
        /**
         * Setting是否可用
         */
        public static final String SETTING_ENABLE = "setting_enable";
        /**
         * 清除过时会议资料期限
         */
        public static final String CLEAR_FILE_TIME = "clear_file_time";
        /**
         * OTA部署环境
         */
        public static final String OTA_SERVER_ENVIROMENT = "ota_servce_enviroment";

    }


    public static final class Source extends NameValueTable {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/source");


        public static String getString( ContentResolver resolver,  String name) {
            return getString(CONTENT_URI, resolver, name);
        }


        public static boolean putString( ContentResolver resolver,  String name,  String value) {

            return putString(resolver, CONTENT_URI, name, value);
        }


        public static int getInt( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name));
        }

        public static boolean putInt( ContentResolver resolver,  String name, int value) {
            return putString(resolver, CONTENT_URI, name, value + "");
        }

        public static boolean getBoolean( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name)) == TRUE_VALUE;
        }

        public static boolean putBoolean( ContentResolver resolver,  String name, boolean value) {
            return putString(resolver, CONTENT_URI, name, value ? TRUE_STR : FALSE_STR);
        }

        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }

        /**
         * 当前信号源对应系统信号源Enum.ordianl
         */
        public static final String CURRENT_SOURCE_ORDINAL = "current_source_ordinal";
        /**
         * 当前信号源名称
         * 如：Hdmi1,OPS等
         */
        public static final String CURRENT_SOURCE_TYPE = "current_source_type";
        /**
         * FAVORITE 信号源对应系统信号源Enum.ordianl
         */
        public static final String FAVORITE_SOURCE_ORDINAL = "favorite_source_ordinal";
        /**
         * FAVORITE 信号源名称
         */
        public static final String FAVORITE_SOURCE_TYPE = "favorite_source_type";
        /**
         * 上次信号源对应系统信号源Enum.ordianl
         */
        public static final String LAST_SOURCE_ORDINAL = "last_source_ordinal";
        /**
         * 上次信号源名称
         */
        public static final String LAST_SOURCE_TYPE = "last_source_type";
        /**
         * HomeSource 类型
         */
        public static final String HOME_SOURCE_TYPE = "home_source_type";
        /**
         * PC信号源自定义名称
         */
        public static final String PC_RENAME = "pc_rename";
        /**
         * HDMI_Front信号源自定义名称
         */
        public static final String HDMI_FRONT_RENAME = "hdmi_front_rename";
        /**
         * HDMI1信号源自定义名称
         */
        public static final String HDMI1_RENAME = "hdmi1_rename";
        /**
         * HDMI2信号源自定义名称
         */
        public static final String HDMI2_RENAME = "hdmi2_rename";
        /**
         * HDMI3信号源自定义名称
         */
        public static final String HDMI3_RENAME = "hdmi3_rename";
        /**
         * TYPE C信号源自定义名称
         */
        public static final String TYPE_C_RENAME = "type_c_rename";
        /**
         * VGA信号源自定义名称
         */
        public static final String VGA_RENAME = "vga_rename";
        /**
         * DP信号源自定义名称
         */
        public static final String DP_RENAME = "dp_rename";

        /**
         * 开机信号源类型
         */
        public static final String START_SOURCE_TYPE = "start_source_type";

        /**
         * 是否开启实时预览信号源
         */
        public static final String PREVIDER_SOURCE_ENABLE = "preview_source_enable";
    }


    public static final class WhiteBoard extends NameValueTable {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/whiteboard");


        public static String getString( ContentResolver resolver,  String name) {
            return getString(CONTENT_URI, resolver, name);
        }


        public static boolean putString( ContentResolver resolver,  String name,  String value) {

            return putString(resolver, CONTENT_URI, name, value);
        }


        public static int getInt( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name));
        }

        public static boolean putInt( ContentResolver resolver,  String name, int value) {
            return putString(resolver, CONTENT_URI, name, value + "");
        }

        public static boolean getBoolean( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name)) == TRUE_VALUE;
        }

        public static boolean putBoolean( ContentResolver resolver,  String name, boolean value) {
            return putString(resolver, CONTENT_URI, name, value ? TRUE_STR : FALSE_STR);
        }

        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }

        /**
         * 是否正在批注中
         */
        public static final String IS_ANNOTATIONING = "is_annotationing";
        /**
         * 是否开启护眼书写
         */
        public static final String WRITING_PROTECTION_ENABLE = "writing_protection_enable";

    }


    public static final class Toolbar extends NameValueTable {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/toolbar");


        public static String getString( ContentResolver resolver,  String name) {
            return getString(CONTENT_URI, resolver, name);
        }


        public static boolean putString( ContentResolver resolver,  String name,  String value) {

            return putString(resolver, CONTENT_URI, name, value);
        }


        public static int getInt( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name));
        }

        public static boolean putInt( ContentResolver resolver,  String name, int value) {
            return putString(resolver, CONTENT_URI, name, value + "");
        }

        public static boolean getBoolean( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name)) == TRUE_VALUE;
        }

        public static boolean putBoolean( ContentResolver resolver,  String name, boolean value) {
            return putString(resolver, CONTENT_URI, name, value ? TRUE_STR : FALSE_STR);
        }

        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }

        /**
         * 左侧ToolBar是否可用
         */
        public static final String LEFT_BAR_OPEN = "left_bar_open";
        /**
         * 右侧ToolBar是否可用
         */
        public static final String RIGHT_BAR_OPEN = "right_bar_open";
        /**
         * FloatBar是否可用(也可以直接控制两侧ToolBar是否可用)
         */
        public static final String BAR_OPEN = "bar_open";
        /**
         * ToolBar是否显示(用于短暂控制ToolBar显示状态：比如批注截图操作，Freeze操作等)
         */
        public static final String BAR_APPEAR = "bar_appear";
        /**
         * ToolBar大小模式
         */
        public static final String BAR_SIZE = "bar_size";

        /**
         * ToolBar 那种信号源通道下显示
         * 1 --- 只Android下显示
         * 2 --- 只Source下显示
         */
        public static final String BAR_SOURCE_CHANNEL_SHOW = "bar_source_channel_show";
    }


    public static final class OffTime extends PowerTime {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/off_time");


        public static 
        List<HHTPowerTime> getAllPowerOffTime( ContentResolver resolver) {
            return getAllHHTPowerTime(CONTENT_URI, resolver);

        }

        public static 
        HHTPowerTime getPowerOffTimeById( ContentResolver resolver, int id) {
            return getHHTPowerTimeById(CONTENT_URI, resolver, id);

        }

        public static boolean putAllPowerOffTime( ContentResolver resolver,  List<HHTPowerTime> timeList) {

            return putAllHHTPowerTime(CONTENT_URI, resolver, timeList);
        }

        public static boolean putPowerOffTime( ContentResolver resolver,  HHTPowerTime time) {

            return putHHTPowerTime(CONTENT_URI, resolver, time);
        }

        public static int clearPowerOffTimeById( ContentResolver resolver, int id) {
            Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
            return removeData(uri, resolver);

        }

        public static int clearAllPowerOffTime( ContentResolver resolver) {
            return removeData(CONTENT_URI, resolver);
        }


        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }


    }


    public static final class OnTime extends PowerTime {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/on_time");


        public static 
        List<HHTPowerTime> getAllPowerOnTime( ContentResolver resolver) {
            return getAllHHTPowerTime(CONTENT_URI, resolver);

        }

        public static 
        HHTPowerTime getPowerOnTimeById( ContentResolver resolver, int id) {
            return getHHTPowerTimeById(CONTENT_URI, resolver, id);

        }

        public static boolean putAllPowerOnTime( ContentResolver resolver,  List<HHTPowerTime> timeList) {

            return putAllHHTPowerTime(CONTENT_URI, resolver, timeList);
        }

        public static boolean putPowerOnTime( ContentResolver resolver,  HHTPowerTime time) {

            return putHHTPowerTime(CONTENT_URI, resolver, time);
        }

        public static int clearPowerOnTimeById( ContentResolver resolver, int id) {
            Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
            return removeData(uri, resolver);

        }

        public static int clearAllPowerOnTime( ContentResolver resolver) {
            return removeData(CONTENT_URI, resolver);
        }


        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }


    }


    public static final class Developer extends NameValueTable {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/developer");


        public static String getString( ContentResolver resolver,  String name) {
            return getString(CONTENT_URI, resolver, name);
        }


        public static boolean putString( ContentResolver resolver,  String name,  String value) {

            return putString(resolver, CONTENT_URI, name, value);
        }


        public static int getInt( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name));
        }

        public static boolean putInt( ContentResolver resolver,  String name, int value) {
            return putString(resolver, CONTENT_URI, name, value + "");
        }

        public static boolean getBoolean( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name)) == TRUE_VALUE;
        }

        public static boolean putBoolean( ContentResolver resolver,  String name, boolean value) {
            return putString(resolver, CONTENT_URI, name, value ? TRUE_STR : FALSE_STR);
        }

        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }


    }


    public static final class App extends NameValueTable {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/app");


        public static String getString( ContentResolver resolver,  String name) {
            return getString(CONTENT_URI, resolver, name);
        }


        public static boolean putString( ContentResolver resolver,  String name,  String value) {

            return putString(resolver, CONTENT_URI, name, value);
        }


        public static int getInt( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name));
        }

        public static boolean putInt( ContentResolver resolver,  String name, int value) {
            return putString(resolver, CONTENT_URI, name, value + "");
        }

        public static boolean getBoolean( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name)) == TRUE_VALUE;
        }

        public static boolean putBoolean( ContentResolver resolver,  String name, boolean value) {
            return putString(resolver, CONTENT_URI, name, value ? TRUE_STR : FALSE_STR);
        }

        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }

        /**
         * 定制应用(重命名、添加最喜爱应用列表等)
         */
        public static final String CUSTOM_APP_LIST = "custom_app_list";

        /**
         * 定制应用是否在应用列表中显示
         */
        public static final String GADGETS_LOCK_LIST = "gadgets_lock_list";


    }


    public static final class Other extends NameValueTable {


        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/other");


        public static String getString( ContentResolver resolver,  String name) {
            return getString(CONTENT_URI, resolver, name);
        }


        public static boolean putString( ContentResolver resolver,  String name,  String value) {

            return putString(resolver, CONTENT_URI, name, value);
        }


        public static int getInt( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name));
        }

        public static boolean putInt( ContentResolver resolver,  String name, int value) {
            return putString(resolver, CONTENT_URI, name, value + "");
        }

        public static boolean getBoolean( ContentResolver resolver,  String name) {
            return Integer.parseInt(getString(CONTENT_URI, resolver, name)) == TRUE_VALUE;
        }

        public static boolean putBoolean( ContentResolver resolver,  String name, boolean value) {
            return putString(resolver, CONTENT_URI, name, value ? TRUE_STR : FALSE_STR);
        }

        public static Uri getUriFor( String name) {

            return getUriFor(CONTENT_URI, name);
        }

        /**
         * Freeze标志
         */
        public static final String FREEZE = "freeze";
        /**
         * Pause标志
         */
        public static final String PAUSE = "pause";
        /**
         * SoundOnly标志
         */
        public static final String SOUND_ONLY = "sound_only";
        /**
         * 滤蓝光标志
         */
        public static final String BLUELIGHT_FILTER = "bluelight_filter";
        /**
         * 滤蓝光等级
         */
        public static final String BLUELIGHT_FILTER_VALUE = "bluelight_filter_value";

    }


    /**
     * Common base hhtpower time tables
     */
    private static class PowerTime extends NameValueTable {


        protected static 
        List<HHTPowerTime> getAllHHTPowerTime(Uri uri, ContentResolver resolver) {
            Cursor cursor = resolver.query(uri, null, null, null, null);
            if (cursor == null || cursor.getCount() == 0) {
                return null;
            }
            List<HHTPowerTime> list = new ArrayList<>();
            while (cursor.moveToNext()) {
                list.add(parseCursorToHHTPowerTime(cursor));
            }
            cursor.close();
            return list;
        }

        protected static 
        HHTPowerTime getHHTPowerTimeById(Uri uri, ContentResolver resolver, int id) {
            Cursor cursor = resolver.query(ContentUris.withAppendedId(uri, id), new String[]{id + ""}, null, null, null);
            HHTPowerTime powerTime = null;
            if (cursor == null || cursor.getCount() == 0) {
                return powerTime;
            }
            cursor.moveToFirst();
            powerTime = parseCursorToHHTPowerTime(cursor);

            cursor.close();

            return powerTime;
        }


        protected static boolean putAllHHTPowerTime(Uri uri, ContentResolver resolver, List<HHTPowerTime> timeList) {
            JSONArray jsonArray = new JSONArray();
            int size = timeList.size();
            for (int i = 0; i < size; i++) {
                HHTPowerTime time = timeList.get(i);

                jsonArray.put(parseHHTPowerTimeToJSONObject(time));
            }
            try {
                ContentValues conValue = new ContentValues();
                conValue.put(VALUE, jsonArray.toString());
                resolver.insert(uri, conValue);

                return true;
            } catch (SQLException e) {
                Log.w(TAG, "Can't put all power time ", e);
                return false;
            }
        }

        protected static boolean putHHTPowerTime(Uri uri, ContentResolver resolver, HHTPowerTime time) {

            try {
                ContentValues conValue = new ContentValues();
                conValue.put(NAME, time._id + "");
                conValue.put(VALUE, parseHHTPowerTimeToJSONObject(time).toString());
                resolver.update(uri, conValue, null, null);
                return true;
            } catch (SQLException e) {
                Log.w(TAG, "Can't set key " + time._id + " in " + uri, e);
                return false;
            }

        }

        protected static int removeData(Uri uri, ContentResolver resolver) {
            return resolver.delete(uri, null, null);

        }

        private static JSONObject parseHHTPowerTimeToJSONObject(HHTPowerTime time) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.putOpt("_id", time._id);
                jsonObject.putOpt("sun", time.sun);
                jsonObject.putOpt("mon", time.mon);
                jsonObject.putOpt("tues", time.tues);
                jsonObject.putOpt("wed", time.wed);
                jsonObject.putOpt("thur", time.thur);
                jsonObject.putOpt("fri", time.fri);
                jsonObject.putOpt("sat", time.sat);
                jsonObject.putOpt("hour", time.hour);
                jsonObject.putOpt("min", time.min);
                jsonObject.putOpt("timeEnable", time.timeEnable);
                jsonObject.putOpt("startSource", time.startSource);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return jsonObject;
        }

        private static HHTPowerTime parseCursorToHHTPowerTime(Cursor cursor) {

            HHTPowerTime powerTime = new HHTPowerTime();
            powerTime._id = cursor.getInt(cursor.getColumnIndex("_id"));
            powerTime.sun = cursor.getInt(cursor.getColumnIndex("sun"));
            powerTime.mon = cursor.getInt(cursor.getColumnIndex("mon"));
            powerTime.tues = cursor.getInt(cursor.getColumnIndex("tues"));
            powerTime.wed = cursor.getInt(cursor.getColumnIndex("wed"));
            powerTime.thur = cursor.getInt(cursor.getColumnIndex("thur"));
            powerTime.fri = cursor.getInt(cursor.getColumnIndex("fri"));
            powerTime.sat = cursor.getInt(cursor.getColumnIndex("sat"));
            powerTime.hour = cursor.getInt(cursor.getColumnIndex("hour"));
            powerTime.min = cursor.getInt(cursor.getColumnIndex("min"));
            powerTime.timeEnable = cursor.getInt(cursor.getColumnIndex("timeEnable"));
            powerTime.startSource = cursor.getString(cursor.getColumnIndex("startSource"));

            return powerTime;
        }


        protected static final String TYPE_OFF_TIME = "off_time";

        protected static final String TYPE_ON_TIME = "on_time";


    }


    /**
     * Common base for tables of name/value settings.
     */
    protected static class NameValueTable implements BaseColumns {

        public static final String AUTHORITY = "com.hht.function";

        public static final String NAME = "key";
        public static final String VALUE = "value";

        protected static boolean putString(ContentResolver resolver, Uri uri,
                                           String name, String value) {

            try {
                ContentValues conValue = new ContentValues();
                conValue.put(NAME, name);
                conValue.put(VALUE, value);
                resolver.update(uri, conValue, null, null);
                return true;
            } catch (SQLException e) {
                Log.w(TAG, "Can't set key " + name + " in " + uri, e);
                return false;
            }

        }


        protected static String getString(Uri uri, ContentResolver resolver, String name) {
            Cursor cursor = resolver.query(uri, new String[]{name}, null, null, null);
            String value = null;
            if (cursor == null || cursor.getCount() == 0) {
                return value;
            }
            cursor.moveToFirst();
            value = cursor.getString(cursor.getColumnIndex(name));
            cursor.close();
            if (value == null) {
                throw new IllegalStateException("This key " + name + " is not stored locally!");
            }
            return value;
        }

        public static Uri getUriFor(Uri uri, String name) {

            if (name == null) {
                return uri;
            }

            if (name.isEmpty()) {
                return uri;
            }

            return Uri.withAppendedPath(uri, name);
        }
    }

}
