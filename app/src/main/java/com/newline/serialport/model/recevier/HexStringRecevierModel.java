package com.newline.serialport.model.recevier;

import android.support.annotation.NonNull;
import android.util.Log;

import com.newline.serialport.setting.HHTDeviceManager;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/24 11:02
 * @describe 回复Hex String串口信息
 */
public abstract class HexStringRecevierModel extends RecevierSerialPortModel {
    //查询OTT版本号
    protected static final String QUERY_TYPE_OTT_VERSION = "7A";
    //查询设备序列号
    protected static final String QUERY_TYPE_SERIAL_NUMBER = "7C";
    //查询版控信息
    protected static final String QUERY_TYPE_VERSION_CONTROL_TYPE = "7E";
    //查询集成包版本号
    protected static final String QUERY_TYPE_FULL_PCAKAGE_VERSION = "8A";
    //查询设备型号
    protected static final String QUERY_TYPE_MODEL_VERSION = "8C";
    //查询音频版版本
    protected static final String QUERY_TYPE_AUDIO_VERSION = "8E";
    //查询音频版型号
    protected static final String QUERY_TYPE_AUDIO_MODEL = "9A";

    //1.包头格式
    private static final String PACKAGE_HEADER = "7F";
    /**
     *  2.数据{@link #dataHex}的长度N/2 -1 + 7
     *  例如： dataHex = "53 54 12 34"  length = 8/2 -1 + 7 = 11 转16进制,最终lengthHex = 0B
     */
    protected String lengthHex;
    //3.查询指令格式
    private static final String TYPE_QUERY_DATA = "0A";
    //4.查询数据类型
    protected String queryType;
    //5.数据的内容
    protected String dataHex;
    //6.校验信息
    private static final String CHECK_SUM = "01";
    //7.数据包结束格式
    private static final String PACKAGE_END = "CF";

    public HexStringRecevierModel(HHTDeviceManager hhtDeviceManager) {
        super(hhtDeviceManager);
    }

    @Override
    public void action() {
            dataHex = RecevierSerialPortModel.str2HexStr(getRawData());
            queryType = getQueryType();
            lengthHex = Integer.toHexString(dataHex.length()/2 -1+7);
            if(lengthHex.length() %2 == 1){
                //需前面补0
                lengthHex =  "0"+ lengthHex;
            }
//            checkSum = new StringBuffer().append(PACKAGE_HEADER)
//                    .append(lengthHex)
//                    .append(TYPE_QUERY_DATA)
//                    .append(queryType)
//                    .append(dataHex).toString();
    }

    /**
     * 回复内容就是1+2+3+4+5+6+7
     * @return
     */
    @Override
    public String retryContent() {
       StringBuffer sb = new StringBuffer();
       sb.append(PACKAGE_HEADER)
               .append(lengthHex)
               .append(TYPE_QUERY_DATA)
               .append(queryType)
               .append(dataHex)
               .append(CHECK_SUM)
               .append(PACKAGE_END);
       return sb.toString();
    }

    /**
     * 获取实际数据
     * @return  rawData
     */
    @NonNull
    abstract String getRawData();

    /**
     * 获取查询数据类型
     * @return
     */
    @NonNull
    abstract String getQueryType();

}
