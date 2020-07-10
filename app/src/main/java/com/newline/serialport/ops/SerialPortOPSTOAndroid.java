package com.newline.serialport.ops;

import android.content.Context;
import android.text.TextUtils;

import com.newline.serialport.HHTApplication;
import com.newline.serialport.utils.Sender;
import com.hht.tools.log.Logger;
import com.hht.tools.log.SystemPropertiesValue;
import com.newline.serialport.HHTApplication;
import com.newline.serialport.utils.Sender;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerialPortOPSTOAndroid {
    public static final String IDENTIFICATION_NUMBER = "99 A2 B3 C4 02 FF";

    public static final String START_IDENTIFICATION_NUMBER = "AB";

    public static final String START_SEND_IDENTIFICATION_NUMBER = "AC";

    public static final String CONTENT_IDENTIFICATION_NUMBER = "AD";

    public static final String STOP_SEND_IDENTIFICATION_NUMBER = "AE";

    public static final String STOP_IDENTIFICATION_NUMBER = "AF";

    public static final String DELETE_ALL_IDENTIFICATION_NUMBER = "90";

    public static final String DELETE_IDENTIFICATION_NUMBER = "91";

    public static final String STATUS_IDENTIFICATION_NUMBER = "93";

    public static final String CLEAR_IDENTIFICATION_NUMBER = "94";

    public static final String OPEN_APP_IDENTIFICATION_NUMBER = "A1";

    public static final String RECEIVER_MARK = "A0";
    public static final String SEND_MARK = "A1";
    public static final String WINDOWS_PATH = "/data/video/windows/";

    public static final String HEADER = "7F";
    public static final String ENDER = "CF";
    private String mFileName;
    private String mBuffer;
    private EN_PROCESS_STATE mState = EN_PROCESS_STATE.PS_IDLE;
    private EN_CMD_OPS_TO_ANDROID mCMD;
    public boolean isOPSTOAndroid(String mData) {
        if (mData == null) {
            return false;
        }
        return mData.contains(IDENTIFICATION_NUMBER);
    }

    protected String returnCode = "";

    public String HexString = "";

    public String getReturnCode() {
        return returnCode;
    }

    //00 正確 01 錯誤
    private static String getZZ(boolean hasError) {
        return hasError ? "01":"00";
    }

    private static String getNoErrorZZ() {
        return getZZ(false);
    }

    public void getSerialPortModelByOPSCode(Context context, String controllingCode, int size, OnReSendSerialPortListener listener) {
        Logger.setOpenLog(true);
        Logger.setSystemDebug(true);
        if (listener != null) {
            String command = "";
            char[] chars = controllingCode.toCharArray();
            for (int i = 0; i < size * 2; i++) {
                command += chars[i];
                if (i!= 0 && i % 2 != 0 && i!=(size*2 -1)) {
                    command += " ";
                }
            }
            Logger.d("command = "+command);
            if (TextUtils.isEmpty(HexString)) {
                HexString = command;
            } else {
                if (command.substring(0,2).equals("7F")) {
                    HexString = command;
                } else {
                    HexString = HexString + " "+ command;
                }
            }
//            if (SerialPortMuteUnMute.CONTROLLING_CODE.equals(command)) {
//                Logger.d("testtest");
//
//                SerialPortModel model = new SerialPortMuteUnMute();
//                new SerialPortMuteUnMute().action(context);
//                if (listener != null) {
//                    listener.onSend(model.getReturnCode());
//                    return;
//                }
//            }
            Logger.d("HexString = "+HexString);
            if (!isOPSTOAndroid(HexString)) {
                Logger.d("action error");
                return;
            }
            catchCommand(HexString);
//            try {
                if (HexString.substring(0, 2).equals("7F") && HexString.contains("CF") && mCMD != EN_CMD_OPS_TO_ANDROID.COTA_TRANSFER_FILE_CONTENT) {
                    if (HexString.contains("CF CF")) {
                        HexString = HexString.split("CF CF")[0] + "CF CF";
                    } else {
                        HexString = HexString.split("CF")[0] + "CF";
                    }
                    action(context, HexString);
                    HexString = "";
                    listener.onSend(returnCode.toUpperCase());
                    mCMD = null;
                } else if (mCMD == EN_CMD_OPS_TO_ANDROID.COTA_TRANSFER_FILE_CONTENT || mCMD == EN_CMD_OPS_TO_ANDROID.COTA_TRANSFER_FILE_NAME) {
                    action(context, HexString);
                    listener.onSend(returnCode.toUpperCase());
                }
                returnCode = "";
//            }catch (Exception e) {
//                Logger.e(e);
//                mState = EN_PROCESS_STATE.PS_IDLE;
//            }

        }
    }

    public void action(Context context, String command) {
        if (mCMD != null) {
            switch (mCMD) {
                case COTA_START_TRANSFER:
                    HHTApplication.timeTotalStamp = 0;
                    HHTApplication.totalCommander = 0;
                    Logger.i("START_IDENTIFICATION_NUMBER");
                    handleStartReady();
                    break;
                case COTA_TRANSFER_FILE_NAME:
                    Logger.i("START_SEND_IDENTIFICATION_NUMBER");
                    handleStartSendFile();
                    break;
                case COTA_TRANSFER_FILE_CONTENT:
                    Logger.i("CONTENT_IDENTIFICATION_NUMBER");
                    handleFileContent();
                    break;
                case COTA_TRANSFER_FILE_COMPLETED:
                    Logger.i("STOP_SEND_IDENTIFICATION_NUMBER");
                    handleStopSendFile();
                    break;
                case COTA_STOP_TRANSFER:
                    Logger.i("STOP_IDENTIFICATION_NUMBER");
                    handleEndAll();
                    Sender.sendOPSNewlineChanged(context);
                    break;
            }
        } else {
            if (command.contains(IDENTIFICATION_NUMBER +" " +DELETE_IDENTIFICATION_NUMBER)) {
                Logger.d("DELETE_IDENTIFICATION_NUMBER");
                handleDeleteIcon();
                mCMD = null;
                HexString = "";
                Sender.sendOPSNewlineChanged(context);
            } else if (command.contains(IDENTIFICATION_NUMBER +" " +DELETE_ALL_IDENTIFICATION_NUMBER + " " +RECEIVER_MARK)) {
                Logger.d("DELETE_ALL_IDENTIFICATION_NUMBER");
                handleDeleteAllIcon();
                mCMD = null;
                HexString = "";
                Sender.sendOPSNewlineChanged(context);
            } else if (command.contains(IDENTIFICATION_NUMBER +" " +STATUS_IDENTIFICATION_NUMBER)) {
                Logger.d("STATUS_IDENTIFICATION_NUMBER");
                handleStatus();
                HexString = "";
                mCMD = null;
            } else if (command.contains(IDENTIFICATION_NUMBER +" " +CLEAR_IDENTIFICATION_NUMBER + " " + RECEIVER_MARK)) {
                Logger.d("CLEAR_IDENTIFICATION_NUMBER");
                handleClear();
                HexString = "";
                mCMD = null;
            } else if (command.contains(IDENTIFICATION_NUMBER +" "+OPEN_APP_IDENTIFICATION_NUMBER + " " + RECEIVER_MARK)) {
                Logger.d("OPEN_APP_IDENTIFICATION_NUMBER");
                handleOpenApp();
                mCMD = null;
                HexString = "";
            }
        }
    }

    private void handleClear() {
        mState = EN_PROCESS_STATE.PS_SEND_COMMAND;
        String ZZ = getZZ(!HexString.contains("CF"));
        String totalString = IDENTIFICATION_NUMBER + " " + CLEAR_IDENTIFICATION_NUMBER +" "+SEND_MARK+ " " + ZZ;
        int total = (totalString).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        String CHK = checkSum(totalHex + " " + totalString);
        returnCode = (HEADER + " " + totalHex + " " + totalString + " "+ CHK + " " + ENDER);
        Logger.d("returnCode = " + returnCode);
        mState = EN_PROCESS_STATE.PS_IDLE;
    }

    private void handleOpenApp() {
        mState = EN_PROCESS_STATE.PS_SEND_COMMAND;
        String ZZ = getZZ(!HexString.contains("CF"));
        String contentText = HexString.split(RECEIVER_MARK)[1];
        Logger.d("contentText = "+ contentText);
        String appNameAsciiCode = contentText.contains("CF") ? contentText.substring(7, contentText.length() - 6) : contentText.substring(7, contentText.length());
        Logger.d("appNameAsciiCode = " + appNameAsciiCode);
        String appName = asciiToString(appNameAsciiCode);
        Logger.d("appName = " + appName);
        String totalString = IDENTIFICATION_NUMBER + " " + OPEN_APP_IDENTIFICATION_NUMBER +" "+SEND_MARK+ " " + ZZ;
        int total = (totalString + " " + appName).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        String CHK = checkSum(totalHex + " " + totalString + " " + appNameAsciiCode);
        returnCode = (HEADER + " " + totalHex + " " + totalString  + " " + appNameAsciiCode + " " + CHK + " " + ENDER);
        Logger.d("returnCode = " + returnCode);
        mState = EN_PROCESS_STATE.PS_IDLE;
    }

    private void catchCommand(String command) {
        if (mState == EN_PROCESS_STATE.PS_IDLE) {
            if (command.contains(IDENTIFICATION_NUMBER + " " +START_IDENTIFICATION_NUMBER + " " + RECEIVER_MARK)) {
                mState = EN_PROCESS_STATE.PS_RECEIVER_FILE;
                HexString = command;
                mCMD = EN_CMD_OPS_TO_ANDROID.COTA_START_TRANSFER;
            } else if (command.contains(IDENTIFICATION_NUMBER +" "+START_SEND_IDENTIFICATION_NUMBER + " " + RECEIVER_MARK)) {
                HexString = command;
                mCMD = EN_CMD_OPS_TO_ANDROID.COTA_TRANSFER_FILE_NAME;
            } else if (command.contains(IDENTIFICATION_NUMBER +" "+CONTENT_IDENTIFICATION_NUMBER + " " + RECEIVER_MARK)) {
                HexString = command;
                mCMD = EN_CMD_OPS_TO_ANDROID.COTA_TRANSFER_FILE_CONTENT;
            } else if (command.contains(IDENTIFICATION_NUMBER +" "+STOP_SEND_IDENTIFICATION_NUMBER + " " + RECEIVER_MARK)) {
                HexString = command;
                mCMD = EN_CMD_OPS_TO_ANDROID.COTA_TRANSFER_FILE_COMPLETED;
            } else if (command.contains(IDENTIFICATION_NUMBER +" "+STOP_IDENTIFICATION_NUMBER + " " + RECEIVER_MARK)) {
                mState = EN_PROCESS_STATE.PS_IDLE;
                HexString = command;
                mCMD = EN_CMD_OPS_TO_ANDROID.COTA_STOP_TRANSFER;
            } else if (mState == EN_PROCESS_STATE.PS_WAIT_REPLY) {
                mState = EN_PROCESS_STATE.PS_IDLE;
            } else {
                mState = EN_PROCESS_STATE.PS_IDLE;
            }
        }
        Logger.d("mCMD = "+ mCMD);
    }

    private void handleStatus() {
        String ZZ = getZZ(!HexString.contains("CF"));
        String totalString = IDENTIFICATION_NUMBER + " " + STATUS_IDENTIFICATION_NUMBER +" "+SEND_MARK+" "+ ZZ;
        int total = totalString.replaceAll(" ", "").length() / 2;
        Logger.d("totalString " +totalString + "  total = "+ total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" +totalHex;
        }
        String CHK = checkSum(totalHex + " "+totalString);
        returnCode = HEADER + " " + totalHex + " "+totalString + " " + CHK + " "+ENDER;
        Logger.d("returnCode = "+ returnCode);
        mState = EN_PROCESS_STATE.PS_IDLE;

    }

    private void handleDeleteAllIcon() {
        File dirs = new File(WINDOWS_PATH);
        if (dirs.exists()) {
            boolean suc = dirs.delete();
            Logger.d("delete all= "+ suc);
            String ZZ = getZZ(!HexString.contains("CF"));
            String totalString = IDENTIFICATION_NUMBER + " " + DELETE_ALL_IDENTIFICATION_NUMBER +" "+SEND_MARK+" "+ ZZ;
            int total = totalString.replaceAll(" ", "").length() / 2;
            Logger.d("totalString " +totalString + "  total = "+ total);
            String totalHex = Integer.toHexString(total + 1);//1 = checkSum
            if (totalHex.length() == 1) {
                totalHex = "0" +totalHex;
            }
            String CHK = checkSum(totalHex + " "+totalString);
            returnCode = HEADER + " " + totalHex + " " + totalString + " " + CHK + " "+ENDER;
            Logger.d("returnCode = "+ returnCode);
        }
        mState = EN_PROCESS_STATE.PS_IDLE;

    }

    private void handleDeleteIcon() {
        File dirs = new File(WINDOWS_PATH);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        String contentText = HexString.split(RECEIVER_MARK)[1];
        Logger.d("contentText = "+ contentText);
        int lowLevel = Integer.parseInt(contentText.substring(1,3), 16);
        int highLevel = Integer.parseInt(contentText.substring(4,6), 16);
        int totalSum = highLevel * 16 + lowLevel;
        Logger.d("totalSum = " +totalSum);
        Logger.d("totalSum = " +totalSum + " lowLevel "+lowLevel+ "   highLevel " + highLevel);
        String fileAsciiCode = "";
        if (HexString.contains("CF")) {
            fileAsciiCode = contentText.substring(7, contentText.length() - 6);
            Logger.d("fileAsciiCode " + fileAsciiCode);
        } else {
            fileAsciiCode = contentText.substring(7, contentText.length());
        }
        String fileName = asciiToString(fileAsciiCode);
        Logger.d("fileName = " + fileName);
        boolean suc = new File(WINDOWS_PATH+fileName+ ".png").delete();
        String ZZ = getZZ(!HexString.contains("CF"));

        String lowLevelStr =Integer.toHexString(lowLevel);
        if (lowLevelStr.length() == 1) {
            lowLevelStr = "0" + lowLevelStr;
        }
        String highLevelStr =Integer.toHexString(highLevel);
        if (highLevelStr.length() == 1) {
            highLevelStr = "0" + highLevelStr;
        }
        String totalString = IDENTIFICATION_NUMBER + " " + DELETE_IDENTIFICATION_NUMBER +" "+SEND_MARK+ " " + ZZ;
        int total = (totalString+ " "+lowLevelStr+" "+highLevelStr + " " +fileAsciiCode).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        String CHK = checkSum(totalHex + " " + totalString + " "+lowLevelStr+" "+highLevelStr + " " +fileAsciiCode);
        returnCode = HEADER + " " + totalHex + " " + totalString + " "+lowLevelStr+" "+highLevelStr + " " +fileAsciiCode+ " " + CHK + " " + ENDER;
        Logger.d("returnCode = " + returnCode);
        mState = EN_PROCESS_STATE.PS_IDLE;
    }

    private void handleEndAll() {
        SystemPropertiesValue.setProperty("persist.trans.file.complete", "1");
        String ZZ = getZZ(!HexString.contains("CF"));
        String contentText = HexString.split(RECEIVER_MARK)[1];
        Logger.d("contentText = "+ contentText);
        int lowLevel = Integer.parseInt(contentText.substring(1,3), 16);
        int highLevel = Integer.parseInt(contentText.substring(4,6), 16);
        int totalSum = highLevel * 16 + lowLevel;
        Logger.d("totalSum = " +totalSum + " lowLevel "+lowLevel+ "   highLevel " + highLevel);
        String lowLevelStr = Integer.toHexString(lowLevel);
        if (lowLevelStr.length() == 1) {
            lowLevelStr = "0" + lowLevelStr;
        }
        String highLevelStr =Integer.toHexString(highLevel);
        if (highLevelStr.length() == 1) {
            highLevelStr = "0" + highLevelStr;
        }
        String totalString = IDENTIFICATION_NUMBER + " " + STOP_IDENTIFICATION_NUMBER +" "+SEND_MARK+ " " + ZZ;
        int total = (totalString + " "+lowLevelStr+" "+highLevelStr).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        String CHK = checkSum(totalHex + " "+ totalString + " "+ lowLevelStr+" "+highLevelStr);
        returnCode = (HEADER + " " + totalHex + " "+ totalString + " "+ lowLevelStr+" "+highLevelStr + " " + CHK + " " + ENDER);
        Logger.d("returnCode = " + returnCode);
        mState = EN_PROCESS_STATE.PS_IDLE;
        mFileName = null;
    }


    private String lastHandler = "7F 0C 99 A2 B3 C4 02 FF AD A1 00 00 00 0D CF";
    private void handleFileContent() {
        if (mFileName == null) {
            Logger.d("mFilePath = " + mFileName);
        }
        String ZZ = getZZ(!HexString.contains("CF"));
        if (ZZ.equals("01")) {
            returnCode = lastHandler.replace("AD A1 00","AD A1 01");
            String CHK = returnCode.substring(returnCode.length() - 5, returnCode.length() - 3);
            Logger.i("CHK = "+CHK);
            int CHKInteger = Integer.parseInt(CHK, 16) + 1;
            returnCode = returnCode.replace(CHK, ""+CHKInteger);
            Logger.d("returnCode = " + returnCode.toUpperCase());
            return;
        }
        String[] dataSet = HexString.split(CONTENT_IDENTIFICATION_NUMBER +" "+RECEIVER_MARK);
//        Logger.d("dataSet " + dataSet.length);
        for (int i = 0; i < dataSet.length; i++) {
//            Logger.d("dataSet " + i + "  "+ dataSet[i]);
        }
        String lowLevelStr = "00";
        String highLevelStr = "00";
        if (dataSet.length > 1) {
            String contentText = dataSet[1];
            Logger.d("contentText = " + contentText);
            int lowLevel = Integer.parseInt(contentText.substring(1, 3), 16);
            int highLevel = Integer.parseInt(contentText.substring(4, 6), 16);
            int totalSum = highLevel * 16 + lowLevel;
            Logger.d("totalSum = " + totalSum + " lowLevel " + lowLevel + "   highLevel " + highLevel);
            if (ZZ.equals("00")) {
                String mData = contentText.substring(7, contentText.length() - 6);
                Logger.d("mData = " + mData);
                if (TextUtils.isEmpty(mBuffer)) {
                    mBuffer += mData;
                } else {
                    mBuffer = mBuffer + " " + mData;
                }
                Logger.d("mBuffer = " + mBuffer);
            }
            lowLevelStr = Integer.toHexString(lowLevel);
            if (lowLevelStr.length() == 1) {
                lowLevelStr = "0" + lowLevelStr;
            }
            highLevelStr =Integer.toHexString(highLevel);
            if (highLevelStr.length() == 1) {
                highLevelStr = "0" + highLevelStr;
            }
        }

        String totalString = IDENTIFICATION_NUMBER + " " + CONTENT_IDENTIFICATION_NUMBER +" "+SEND_MARK+ " " + ZZ;
        int total = (totalString + " "+lowLevelStr+" "+highLevelStr).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        String CHK = checkSum(totalHex + " " + totalString + " "+lowLevelStr+" "+highLevelStr);
        returnCode = HEADER + " " + totalHex + " " + totalString + " "+lowLevelStr+" "+highLevelStr+" "+CHK + " " + ENDER;
        lastHandler = returnCode;
        Logger.d("returnCode = " + returnCode.toUpperCase());
    }

    private void handleStopSendFile() {
        Logger.d("mBuffer final = "+ "   mBuffer = "+ mBuffer);
        String ZZ = getZZ(!HexString.contains("CF"));
        String contentText = HexString.split(RECEIVER_MARK)[1];
        Logger.d("contentText = "+ contentText);
        int lowLevel = Integer.parseInt(contentText.substring(1,3), 16);
        int highLevel = Integer.parseInt(contentText.substring(4,6), 16);
        int totalSum = highLevel * 16 + lowLevel;
        Logger.d("totalSum = " +totalSum + " lowLevel "+lowLevel+ "   highLevel " + highLevel);
        String lowLevelStr = Integer.toHexString(lowLevel);
        if (lowLevelStr.length() == 1) {
            lowLevelStr = "0" + lowLevelStr;
        }
        String highLevelStr =Integer.toHexString(highLevel);
        if (highLevelStr.length() == 1) {
            highLevelStr = "0" + highLevelStr;
        }
        if (mFileName != null && !TextUtils.isEmpty(mBuffer)) {
            byte[] imageBytes = hexToByte(mBuffer);
            byte2Image(imageBytes, WINDOWS_PATH + mFileName+ ".png");
        }
        mBuffer = "";
        HexString = "";
        String totalString = IDENTIFICATION_NUMBER + " " + STOP_SEND_IDENTIFICATION_NUMBER +" "+SEND_MARK+ " " + ZZ;
        String fileNameAsciiCode = stringToAscii(mFileName);
        int total = (totalString + " "+lowLevelStr+" "+highLevelStr + " "+fileNameAsciiCode).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        Logger.d("fileNameAsciiCode = "+fileNameAsciiCode);

        String CHK = checkSum(totalHex + " " + totalString + " "+lowLevelStr+" "+highLevelStr +" "+fileNameAsciiCode);
        returnCode = HEADER + " " + totalHex + " " + totalString + " "+lowLevelStr+" "+highLevelStr +" "+fileNameAsciiCode+ " "+CHK + " " + ENDER;
        Logger.d("returnCode = " + returnCode);
        mState = EN_PROCESS_STATE.PS_IDLE;
    }

    private void handleStartSendFile() {
        File dirs = new File(WINDOWS_PATH);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        String ZZ = getZZ(!HexString.contains("CF"));
        String contentText = HexString.split(START_SEND_IDENTIFICATION_NUMBER +" "+RECEIVER_MARK)[1];
        Logger.i("contentText = "+ contentText);
        int lowLevel = Integer.parseInt(contentText.substring(1,3), 16);
        int highLevel = Integer.parseInt(contentText.substring(4,6), 16);
        int totalSum = highLevel * 16 + lowLevel;
        Logger.d("totalSum = " +totalSum + " lowLevel "+lowLevel+ "   highLevel " + highLevel);
        String lowLevelStr = Integer.toHexString(lowLevel);
        if (lowLevelStr.length() == 1) {
            lowLevelStr = "0" + lowLevelStr;
        }
        String highLevelStr =Integer.toHexString(highLevel);
        if (highLevelStr.length() == 1) {
            highLevelStr = "0" + highLevelStr;
        }
        String fileAsciiCode = "";
        if (HexString.contains("CF")) {
            fileAsciiCode = contentText.substring(7, contentText.length() - 6);
            Logger.i("fileAsciiCode " + fileAsciiCode);
        } else {
            fileAsciiCode = contentText.substring(7, contentText.length());
        }
            String fileName = asciiToString(fileAsciiCode);
            Logger.i("fileName = " + fileName);
            mFileName = fileName;
            mBuffer = "";
            Logger.i("mFileName = " + mFileName);
            Logger.i("mFileName 2 = " + stringToAscii(fileName));
            fileAsciiCode = " "+ fileAsciiCode;
        String totalString = IDENTIFICATION_NUMBER + " " + START_SEND_IDENTIFICATION_NUMBER +" "+SEND_MARK+ " " + ZZ;
        int total = (totalString  + " "+lowLevelStr+" "+highLevelStr + fileAsciiCode).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        String CHK = checkSum(totalHex + " " + totalString  + " "+lowLevelStr+" "+highLevelStr + fileAsciiCode);
        returnCode = HEADER + " " + totalHex + " " + totalString  + " "+lowLevelStr+" "+highLevelStr + fileAsciiCode + " "+CHK + " " + ENDER;
        Logger.d("returnCode = " + returnCode);
        mState = EN_PROCESS_STATE.PS_IDLE;
    }

    private void handleStartReady() {
        SystemPropertiesValue.setProperty("persist.trans.file.complete", "0");
        String ZZ = getZZ(!HexString.contains("CF"));
        String contentText = HexString.split(RECEIVER_MARK)[1];
        Logger.d("contentText = "+ contentText);
        int lowLevel = Integer.parseInt(contentText.substring(1,3), 16);
        int highLevel = Integer.parseInt(contentText.substring(4,6), 16);
        int totalSum = highLevel * 16 + lowLevel;
        Logger.d("totalSum = " +totalSum + " lowLevel "+lowLevel+ "   highLevel " + highLevel);
        String lowLevelStr = Integer.toHexString(lowLevel);
        if (lowLevelStr.length() == 1) {
            lowLevelStr = "0" + lowLevelStr;
        }
        String highLevelStr =Integer.toHexString(highLevel);
        if (highLevelStr.length() == 1) {
            highLevelStr = "0" + highLevelStr;
        }
        String totalString = IDENTIFICATION_NUMBER + " " + START_IDENTIFICATION_NUMBER +" "+SEND_MARK+ " " + ZZ;
        int total = (totalString +  " "+lowLevelStr+" "+highLevelStr).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        String CHK = checkSum(totalHex + " " + totalString +  " "+lowLevelStr+" "+highLevelStr);
        returnCode = (HEADER + " " + totalHex + " " + totalString + " "+lowLevelStr+" "+highLevelStr + " " + CHK + " " + ENDER);
        Logger.d("returnCode = " + returnCode);
        mState = EN_PROCESS_STATE.PS_IDLE;
    }

    public static String getSendOpenAppCommand(String appName) {
        String totalString = IDENTIFICATION_NUMBER + " " + OPEN_APP_IDENTIFICATION_NUMBER +" "+RECEIVER_MARK;
        String appNameAsciiCode = stringToAscii(appName);
        int total = (totalString + " " +appNameAsciiCode).replaceAll(" ", "").length() / 2;
        Logger.d("totalString " + totalString + "  total = " + total);
        String totalHex = Integer.toHexString(total + 1);//1 = checkSum
        if (totalHex.length() == 1) {
            totalHex = "0" + totalHex;
        }
        String CHK = checkSum(totalHex + " " + totalString+ " " +appNameAsciiCode + " 01");
        return HEADER + " " + totalHex + " " + totalString+ " " +appNameAsciiCode + " " + CHK + " "+ ENDER;
    }

    //byte数组到图片
    public void byte2Image(byte[] data,String path) {
        if (path == null && data == null) {
            return;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(data, 0 ,data.length);
            fos.flush();
            Logger.d(" save success ");
        } catch (Exception e) {
            Logger.e(e);
            Logger.d(" save error ");
        }
    }

    public static String stringToAscii(String value) {
        Logger.d("value = "+value);
        if (value == null) {
            return "";
        }
        if (isContainChinese(value)) {
            Logger.d("包含 中文");
            String hexString = stringToHexString(value);
            String returnStr = "";
            char[] chars = hexString.toCharArray();
            int size = chars.length;
            for (int i = 0; i < size; i++) {
                returnStr += chars[i];
                if (i!= 0 && i % 2 != 0 && i!=(size -1)) {
                    returnStr += " ";
                }
            }
            return returnStr;
        }
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        Logger.d(" chars = "+ chars.length);
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append(Integer.toHexString((int) chars[i])).append(" ");
            } else {
                sbu.append(Integer.toHexString((int) chars[i]));
            }
        }
        return sbu.toString().toUpperCase();
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param string 要转换的字节数组
     * @return 转换后的结果
     */
    public static String stringToHexString(String string) {
        byte[] bytes = new byte[0];
        try {
            bytes = string.getBytes("UTF-8");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
//            Log.i("HWL", "bytes[i] = "+bytes[i] +"  i = "+i);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(" ");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i], 16));
        }

        byte[] bytes = hexToByte(value);
        try {
            String fileName = new String(bytes,"UTF-8");
            Logger.d("fileName " +fileName);

            return fileName;
        } catch (UnsupportedEncodingException e) {
            Logger.e(e);
        }

        return sbu.toString();
    }



    /**
     * hex转byte数组
     * @param hexString
     * @return
     */
    public static byte[] hexToByte(String hexString){
        int m = 0, n = 0;
        String[] hexSet = hexString.split(" ");
        int byteLen = hexSet.length; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        Logger.d("hexString = "+hexString);
        Logger.d("hexSet " + byteLen);
        for (int i = 0; i < byteLen; i++) {
            String hex = hexSet[i];
            if (hex.length() == 0) {
                continue;
            }
            int intVal = 0;
            if (hex.length() == 1) {
                intVal = Integer.decode("0x0" + hex.substring(0, 1));
            } else {
                intVal = Integer.decode("0x" + hex.substring(0, 1) + hex.substring(1, 2));
            }
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }

    private String hexToString(String text)  {
        if (!text.contains(" ")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String[] hexStrs = text.split(" ");
        for (int i = 0; i < hexStrs.length; i++) {
            int asciiCodes = Integer.parseInt(hexStrs[i], 16);
            //convert the decimal to character
            sb.append((char)asciiCodes);
//            temp.append(decimal);
        }
        return sb.toString();
    }

    private static String checkSum(String target) {
        Logger.d("target "+ target);
        if (!target.contains(" ")) {
            return "00";
        }
        String[] codes = target.split(" ");
        int sum = 0;
        for (int i = 0; i < codes.length; i++) {
            int number = Integer.parseInt(codes[i], 16);
            sum = sum + number;
        }
        String sumString = Integer.toHexString((sum & 0xff));
        if (sumString.length() == 1) {
            return "0" + sumString;
        }

        return sumString.toUpperCase();
    }
}
