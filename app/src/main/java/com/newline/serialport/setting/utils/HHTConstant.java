package com.newline.serialport.setting.utils;

/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTSettingLib
 * @email momo.weiye@gmail.com
 * @time 2019/4/11 15:24
 * @describe
 */
public class HHTConstant {

    //=========to hhtservice or quicksetting =====================

    //是否开启滤蓝光的广播
    public static final String ACTION_IS_FILTER = "com.android.settings.action.is.filter";
    //调整蓝光等级的广播
    public static final String ACTION_FILTER_LEVEL="com.android.settings.action.filterlevel";
    //被通知清除白板批注资料的广播
    public static final String ACTION_CLEAR_WHITEBOARD_SCREENSHOT_ONCE = "com.hht.action.CLEAR_WHITEBOARD_SCREENSHOT_ONCE";

    //===========to whiteboard =============================

    //通知白板app结束自己的广播
    public static final String ACTION_END_METTING = "com.hht.action.END_METTING";
    //打开批注的广播
    public static final String ACTION_OPEN_ANNOTATION = "com.hht.floatbar.glassboard";
    //打开白板的广播
    public static final String ACTION_OPEN_WHITEBOARD = "com.hht.floatbar.whiteboard";
    //台湾白板包名
    public static final String TW_WHITBOARD_PACKAGE = "tw.com.hitevision.whiteboard";
    //台湾白板静态广播接收器全类名
    public static final String TW_WHITBOARD_RECEIVER_CLASS = "tw.com.hitevision.whiteboard.android.receivers.WhiteboardBroadcastReceiver";
    //深圳白板包名
    public static final String SZ_WHITBOARD_PACKAGE = "com.paike.zjc";
    //深圳白板静态广播接收器全类名
    public static final String SZ_WHITBOARD_RECEIVER_CLASS = "com.paike.zjc.broadcast.ZBroadcastReceiver";


    //=========to floatbar ===========
    public static final String ACTION_START_FLOATBAR = "com.hht.floatbar.action.start";
    public static final String ACTION_STOP_FLOATBAR = "com.hht.floatbar.action.stop";
    public static final String FLOATBAR_PACKAGE = "com.hht.floatbar";
    public static final String FLOATBAR_CLASS = "com.hht.floatbar.receiver.FloatbarReceiver";

    //========= to remote controller==
    public static final String ACTION_SOUND_ONLY_ON = "COM.IST.SINGLE.MODE.ON";
    public static final String ACTION_SOUND_ONLY_OFF = "COM.IST.SINGLE.MODE.OFF";

    public static final String ACTION_FREEZE = "android.intent.action.view.shot.change";
    //冻屏包名
    public static final String FREEZE_PACKAGE = "com.hht.freezescreen";
    //冻屏广播接收器全类名
    public static final String FREEZE_RECEIVER_CLASS = "com.hht.freezescreen.FreezeScreenReceiver";
}
