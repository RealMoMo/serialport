package com.newline.serialport.model;

import android.content.Context;
import android.util.Log;

import com.hht.tools.log.Logger;

public class SerialPortModel {

    private static String targetCode = "";


    //Set xx 未处理
    public static SerialPortModel getSerialPortModelByControllingCode(String controllingCode, int size) {
        return getSerialPortModelByControllingCode(controllingCode, size, false);
    }
    //Set xx 未处理
    public static SerialPortModel getSerialPortModelByControllingCode(String controllingCode, int size, boolean isYISleep) {
        char[] codes = controllingCode.toCharArray();
        Log.i("HWL", " targetCode codes[0] = "+codes[0]+"  codes[1] = "+codes[1]);
        if (codes[0] == '7' && codes[1] == 'F') {
            targetCode = "";
        } else {
            targetCode += " ";
        }
        for (int i = 0; i < size * 2; i++) {
            targetCode += codes[i];
            if (i!= 0 && i % 2 != 0 && i!=(size*2 -1)) {
                targetCode += " ";
            }
        }
        Log.i("HWL", " targetCode "+targetCode);
        if (isYISleep && !targetCode.equals(SerialPortYISleep.CONTROLLING_CODE)) {
            return null;
        }
        switch (targetCode) {
            case SerialPortPowerOff.CONTROLLING_CODE: {//发送关机广播
                targetCode = "";
                return new SerialPortPowerOff();
            }
            case SerialPortMuteUnMute.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_VOLUME_MUTE
                targetCode = "";
                return new SerialPortMuteUnMute();
            }
            case SerialPortSignalSource.CONTROLLING_CODE: {//打开预览界面
                targetCode = "";
                return new SerialPortSignalSource();
            }
            case SerialPortEnableWhiteboard.CONTROLLING_CODE: {//发广播启动白板
                targetCode = "";
                return new SerialPortEnableWhiteboard();
            }
            case SerialPortDisplayStatus.CONTROLLING_CODE: {//发送广播打开屏显
                targetCode = "";
                return new SerialPortDisplayStatus();
            }
            case SerialPortTypec.CONTROLLING_CODE : {//发广播到中间件
                targetCode = "";
                return new SerialPortTypec();
            }
            case SerialPortHDMIFront.CONTROLLING_CODE: {//发广播到中间件
                targetCode = "";
                return new SerialPortHDMIFront();
            }
            case SerialPortHDMIRear1.CONTROLLING_CODE: {//发广播到中间件
                targetCode = "";
                return new SerialPortHDMIRear1();
            }
            case SerialPortHDMIRear2.CONTROLLING_CODE: {//发广播到中间件
                targetCode = "";
                return new SerialPortHDMIRear2();
            }
            case SerialPortHDMIRear4.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortHDMIRear4();
            }
            case SerialPortHDMIRear3OrVGA.CONTROLLING_CODE: {//发广播到中间件
                targetCode = "";
                return new SerialPortHDMIRear3OrVGA();
            }
            case SerialPortInternalPC.CONTROLLING_CODE: {//发广播到中间件
                targetCode = "";
                return new SerialPortInternalPC();
            }
            case SerialPortDP.CONTROLLING_CODE: {//发广播到中间件
                targetCode = "";
                return new SerialPortDP();
            }
            case SerialPortPageUp.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_PAGE_UP
                targetCode = "";
                return new SerialPortPageUp();
            }
            case SerialPortPageDown.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_PAGE_DOWN
                targetCode = "";
                return new SerialPortPageDown();
            }
            case SerialPortVOLDown.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_VOLUME_DOWN
                targetCode = "";
                return new SerialPortVOLDown();
            }
            case SerialPortVOLUp.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_VOLUME_UP
                targetCode = "";
                return new SerialPortVOLUp();
            }
            case SerialPortMicMuteOn.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_MUTE
                targetCode = "";
                return new SerialPortMicMuteOn();
            }
            case SerialPortMicMuteOff.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_MUTE
                targetCode = "";
                return new SerialPortMicMuteOff();
            }
            case SerialPortWOTMicMuteOn.CONTROLLING_CODE: {//lango 中间件
                targetCode = "";
                return new SerialPortWOTMicMuteOn();
            }
            case SerialPortWOTMicMuteOff.CONTROLLING_CODE: {//lango 中间件
                targetCode = "";
                return new SerialPortWOTMicMuteOff();
            }
            case SerialPortMicMuteStatus.CONTROLLING_CODE: {//获取状态
                targetCode = "";
                return new SerialPortMicMuteStatus();
            }
            case SerialPortWOTMicMuteStatus.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortWOTMicMuteStatus();
            }
            case SerialPortMenu.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_MENU
                targetCode = "";
                return new SerialPortMenu();
            }
            case SerialPortHomePage.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_HOME
                targetCode = "";
                return new SerialPortHomePage();
            }
            case SerialPortReturn.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_BACK
                targetCode = "";
                return new SerialPortReturn();
            }
            case SerialPortOK.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_ENTER
                targetCode = "";
                return new SerialPortOK();
            }
            case SerialPortLeft.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_DPAD_LEFT
                targetCode = "";
                return new SerialPortLeft();
            }
            case SerialPortRight.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_DPAD_RIGHT
                targetCode = "";
                return new SerialPortRight();
            }
            case SerialPortUp.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_DPAD_UP
                targetCode = "";
                return new SerialPortUp();
            }
            case SerialPortDown.CONTROLLING_CODE: {//发送KeyEvent.KEYCODE_DPAD_DOWN
                targetCode = "";
                return new SerialPortDown();
            }
            case SerialPortFirmwareVersion.CONTROLLING_CODE: {//打开HHTFactoryMenu
                targetCode = "";
                return new SerialPortFirmwareVersion();
            }
            case SerialPortModelName.CONTROLLING_CODE: {//打开HHTFactoryMenu
                targetCode = "";
                return new SerialPortModelName();
            }
            case SerialPortSerialNumberShow.CONTROLLING_CODE: {//打开HHTFactoryMenu
                targetCode = "";
                return new SerialPortSerialNumberShow();
            }
            case SerialPortEnableAnnotation.CONTROLLING_CODE: {// 发送广播打开批注
                targetCode = "";
                return new SerialPortEnableAnnotation();
            }
            case SerialPortIncreaseBackLightBrightness.CONTROLLING_CODE: {// +1 Settings.System.SCREEN_BRIGHTNESS
                targetCode = "";
                return new SerialPortIncreaseBackLightBrightness();
            }
            case SerialPortDecreaseBackLightBrightness.CONTROLLING_CODE: {// -1 Settings.System.SCREEN_BRIGHTNESS
                targetCode = "";
                return new SerialPortDecreaseBackLightBrightness();
            }
            case SerialPortSwitchToScreenLock.CONTROLLING_CODE: {//开关童锁
                targetCode = "";
                return new SerialPortSwitchToScreenLock();
            }
            case SerialPortScreenshot.CONTROLLING_CODE: {//截屏
                targetCode = "";
                return new SerialPortScreenshot();
            }
            case SerialPortSettings.CONTROLLING_CODE: {//打开设置菜单
                targetCode = "";
                return new SerialPortSettings();
            }
            case SerialPortFreezeToggle.CONTROLLING_CODE : {//冻屏开关 新增0701.xlsx
                targetCode = "";
                return new SerialPortFreezeToggle();
            }
            case SerialPortFreezeOn.CONTROLLING_CODE: {//打开冻屏 新增 0426.xlsx
                targetCode = "";
                return new SerialPortFreezeOn();
            }
            case SerialPortFreezeOff.CONTROLLING_CODE: {//关闭冻屏 新增 0426.xlsx
                targetCode = "";
                return new SerialPortFreezeOff();
            }
            case SerialPortMuteOn.CONTROLLING_CODE: {//打开静音 新增 0426.xlsx
                targetCode = "";
                return new SerialPortMuteOn();
            }
            case SerialPortMuteOff.CONTROLLING_CODE: {//关闭静音 新增 0426.xlsx
                targetCode = "";
                return new SerialPortMuteOff();
            }
            case SerialPortSwitchSafetyLockOn.CONTROLLING_CODE: {//打开童锁 新增 0426.xlsx
                targetCode = "";
                return new SerialPortSwitchSafetyLockOn();
            }
            case SerialPortSwitchSafetyLockOff.CONTROLLING_CODE: {//关闭童锁 新增 0426.xlsx
                targetCode = "";
                return new SerialPortSwitchSafetyLockOff();
            }
            case SerialPortSwitchBackLightOn.CONTROLLING_CODE: {//打开单独听 新增 0426.xlsx
                targetCode = "";
                return new SerialPortSwitchBackLightOn();
            }
            case SerialPortSwitchBackLightOff.CONTROLLING_CODE: {//关闭单独听 新增 0426.xlsx
                targetCode = "";
                return new SerialPortSwitchBackLightOff();
            }
            case SerialPortWOTCloseNewlineCast.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortWOTCloseNewlineCast();
            }
            case SerialPortWOTCloseNewlineBroadcast.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortWOTCloseNewlineBroadcast();
            }
            case SerialPortWOTActiveNewlineCast.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortWOTActiveNewlineCast();
            }
            case SerialPortWOTActiveNewlineBroadcast.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortWOTActiveNewlineBroadcast();
            }
            case SerialPortZ5ToolBarHide.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortZ5ToolBarHide();
            }
            case SerialPortZ5ToolBarAppear.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortZ5ToolBarAppear();
            }
            case SerialPortZ5CloseNewlineBroadcast.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortZ5CloseNewlineBroadcast();
            }
            case SerialPortZ5CloseNewlineCast.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortZ5CloseNewlineCast();
            }
            case SerialPortZ5EnableNewlineBroadcast.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortZ5EnableNewlineBroadcast();
            }
            case SerialPortZ5EnableNewlineCast.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortZ5EnableNewlineCast();
            }
            case SerialPortZ5ProceedFWUpdate.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortZ5ProceedFWUpdate();
            }
            case SerialPortSpeaker.QUERYING_CODE: {//查询扬声器是否静音
                targetCode = "";
                return new SerialPortSpeaker();
            }
            case SerialPortCurrentSignalSource.QUERYING_CODE: {//查询当前处于哪个信号源
                targetCode = "";
                return new SerialPortCurrentSignalSource();
            }
            case SerialPortSpeakerVolume.QUERYING_CODE: {//查询扬声器声音值
                targetCode = "";
                return new SerialPortSpeakerVolume();
            }
            case SerialPortScreenLockStatus.QUERYING_CODE: {//查询童锁是否显示
                targetCode = "";
                return new SerialPortScreenLockStatus();
            }
            case SerialPortWhiteboardState.QUERYING_CODE: {//查询白板是否打开
                targetCode = "";
                return new SerialPortWhiteboardState();
            }
            case SerialPortCheckOTA.QUERYING_CODE: {
                targetCode = "";
                return new SerialPortCheckOTA();
            }
            case SerialPortOEMBTypec.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortOEMBTypec();
            }
            case SerialPortYIConference.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortYIConference();
            }
            case SerialPortYISleep.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortYISleep();
            }
            case SerialPortYITask.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortYITask();
            }
            case SerialPortYIAutoVGA.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortYIAutoVGA();
            }
            case SerialPortYILockDown.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortYILockDown();
            }
            case SerialPortPowerRestart.CONTROLLING_CODE: {
                targetCode = "";
                return new SerialPortPowerRestart();
            }
        }
        Logger.i(" length =  "+ targetCode.length());
        if (targetCode.length() == 32) {
            if (targetCode.contains(SerialPortSetVolume.CONTROLLING_CODE)) {
                SerialPortSetVolume.volumeHexString = catchKeyValue(targetCode);
                targetCode = "";
                return new SerialPortSetVolume();
            } else if (targetCode.contains(SerialPortSetDisplayMode.CONTROLLING_CODE)) {
                SerialPortSetDisplayMode.ecomode = catchKeyValue(targetCode);
                targetCode = "";
                return new SerialPortSetDisplayMode();
            }
        } else if (targetCode.length() > 32) {
//            if (SerialPortOPSTOAndroid.isOPSTOAndroid(targetCode)) {
//                char[] numberCodes = targetCode.toCharArray();
//                if (numberCodes[numberCodes.length - 2] == 'C' && numberCodes[numberCodes.length - 1] == 'F') {
//                    SerialPortOPSTOAndroid.HexString = targetCode;
//                    targetCode = "";
//                    return new SerialPortOPSTOAndroid();
//                }
//            } else
                if (targetCode.contains(SerialPortSerialNumber.IDENTIFICATION_NUMBER)) {
                char[] numberCodes = targetCode.toCharArray();
                if (numberCodes[numberCodes.length - 2] == 'C' && numberCodes[numberCodes.length - 1] == 'F') {
                    SerialPortSerialNumber.HexString = targetCode;
                    targetCode = "";
                    return new SerialPortSerialNumber();
                }
            } else {
                targetCode = "";
            }
        }
        return null;
    }

    private static String catchKeyValue(String targetCode) {
        String keyStr = targetCode.substring(27, 29);
        Logger.i("keyStr =" +keyStr);
        return keyStr;
    }

    public String getReturnCode() {
        return "";
    }

    public void action(Context context) {
        Log.i(SerialPortModel.class.getSimpleName(), " do noting ");
    }
}
