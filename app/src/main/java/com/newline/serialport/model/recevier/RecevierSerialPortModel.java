package com.newline.serialport.model.recevier;

import android.content.Context;

import com.newline.serialport.model.AudioVersionUpgradeBean;
import com.newline.serialport.setting.HHTDeviceManager;
import java.lang.ref.WeakReference;
import java.math.BigInteger;

/**
 * 接收串口数据基类
 */
public abstract class RecevierSerialPortModel {

    private static String targetCode = "";

    WeakReference<HHTDeviceManager> hhtDeviceManager;

    public boolean changeAndroidDevice = false;
    public int changeAndroidFunctionType = -1;

    public static final int VOLUME_CHANGED_FUNCTION_TYPE =  1;
    public static final int VOLUME_MUTE_FUNCTION_TYPE =  VOLUME_CHANGED_FUNCTION_TYPE+1;
    public static final int MIC_MUTE_FUNCTION_TYPE =  VOLUME_CHANGED_FUNCTION_TYPE+2;

    public RecevierSerialPortModel(HHTDeviceManager hhtDeviceManager) {
        this.hhtDeviceManager = new WeakReference(hhtDeviceManager);
    }

    /**
     * 解析并获取接收串口数据实体类
     * @param controllingCode
     * @param size
     * @param context
     * @param hhtDeviceManager
     * @return
     */
    public static RecevierSerialPortModel getSerialPortModelByControllingCode(String controllingCode, int size, Context context, HHTDeviceManager hhtDeviceManager) {
        char[] codes = controllingCode.toCharArray();
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

        switch (targetCode) {
            case V811MicMuteRecevierModel.CONTROLLING_CODE:{
                return  new V811MicMuteRecevierModel(hhtDeviceManager);
            }
            case V811MicUnMuteRecevierModel.CONTROLLING_CODE:{
                return  new V811MicUnMuteRecevierModel(hhtDeviceManager);
            }
            case VolumeMuteRecevierModel.CONTROLLING_CODE:{
                return  new VolumeMuteRecevierModel(hhtDeviceManager);
            }
            case VolumeUnMuteRecevierModel.CONTROLLING_CODE:{
                return new VolumeUnMuteRecevierModel(hhtDeviceManager);
            }
            case V811OpsReadyRecevierModel.CONTROLLING_CODE:{
                return new V811OpsReadyRecevierModel(context,hhtDeviceManager);
            }
            case SyncStatusRecevierModel.CONTROLLING_CODE:{
                return new SyncStatusRecevierModel(hhtDeviceManager);
            }
            case ProductVersionRecevierModel.CONTROLLING_CODE:{
                return new ProductVersionRecevierModel(hhtDeviceManager);
            }
            case SerialNumberRecevierModel.CONTROLLING_CODE:{
                return new SerialNumberRecevierModel(hhtDeviceManager);
            }
            case VersionControlRecevierModel.CONTROLLING_CODE:{
                return new VersionControlRecevierModel(hhtDeviceManager);
            }
            case FullPackageVersionRecevierModel.CONTROLLING_CODE:{
                return new FullPackageVersionRecevierModel(hhtDeviceManager);
            }
            case ProductModelRecevierModel.CONTROLLING_CODE:{
                return new ProductModelRecevierModel(hhtDeviceManager);
            }
            case V811PowerOffRecevierModel.CONTROLLING_CODE:{
                return new V811PowerOffRecevierModel(hhtDeviceManager,context);
            }
            case V811RebootRecevierModel.CONTROLLING_CODE:{
                return new V811RebootRecevierModel(hhtDeviceManager,context);
            }
            case V811WakeUpRecevierModel.CONTROLLING_CODE:{
                return new V811WakeUpRecevierModel(hhtDeviceManager,context);
            }
            case V811SleepRecevierModel.CONTROLLING_CODE:{
                return new V811SleepRecevierModel(hhtDeviceManager,context);
            }
            case AudioVersionRecevierModel.CONTROLLING_CODE:{
                return new AudioVersionRecevierModel(hhtDeviceManager);
            }
            case AudioModelRecevierModel.CONTROLLING_CODE:{
                return new AudioModelRecevierModel(hhtDeviceManager);
            }
            case V811ScreenStatusRecevierModel.CONTROLLING_CODE:{
                return new V811ScreenStatusRecevierModel(hhtDeviceManager);
            }
            case MacAddressRecevierModel.CONTROLLING_CODE:{
                return new MacAddressRecevierModel(hhtDeviceManager);
            }
            default:{

            }break;
        }

        if(targetCode.length() == 32) {
            if (targetCode.contains(VolumeRecevierModel.CONTROLLING_CODE)) {
                return new VolumeRecevierModel(hhtDeviceManager, hexStringtoInt(catchKeyValue(targetCode)));
            }
        }

        //处理特殊指令
        return getSpecModel(targetCode,hhtDeviceManager,context);

//        return null;
    }

    /**
     * 解析是否特殊串口数据
     * @param rawData
     * @param hhtDeviceManager
     * @param context
     * @return 解析数据匹配，则返回对应串口数据实体类。否则，返回null
     */
    private static RecevierSerialPortModel getSpecModel(String rawData,HHTDeviceManager hhtDeviceManager,Context context){
        Object matchInfo = null;
        matchInfo = UpgradeRecevierModel.match(rawData);
        if(matchInfo!= null){
            return new UpgradeRecevierModel(hhtDeviceManager, rawData,(String) matchInfo,context);
        }
        matchInfo = SetFullPackageVersionRecevierModel.match(rawData);
        if(matchInfo != null){
            return new SetFullPackageVersionRecevierModel(hhtDeviceManager,rawData, (String) matchInfo);
        }
        matchInfo = UpgradeAudioVersionRecevierModel.match(rawData);
        if(matchInfo!= null){
            return new UpgradeAudioVersionRecevierModel(hhtDeviceManager, rawData,(String) matchInfo,context);
        }
        return null;
    }

    /**
     * 处理
     */
    public abstract void action();

    /**
     * 回复数据
     * @return
     */
    public abstract String retryContent();


    public static String getTargetCode(String controllingCode, int size){
        char[] codes = controllingCode.toCharArray();
        String temp ="";
        if (codes[0] == '7' && codes[1] == 'F') {
            temp = "";
        } else {
            temp += " ";
        }
        for (int i = 0; i < size * 2; i++) {
            temp += codes[i];
            if (i!= 0 && i % 2 != 0 && i!=(size*2 -1)) {
                temp += " ";
            }
        }
        return temp;
    }

    /**
     * 获取指令倒数第二段内容
     * @param targetCode
     * @return
     */
    protected static String catchKeyValue(String targetCode) {
        String value = targetCode.substring(27, 29);
        return value;
    }

    protected static int hexStringtoInt(String hexCount){
        BigInteger big = new BigInteger(hexCount, 16);
        return big.intValue();
    }


    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    protected static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
        }
        return sb.toString().trim();
    }


    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     * @param hexStr
     * @return
     */
    protected static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
}
