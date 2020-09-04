package com.newline.serialport.model.recevier;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

import com.newline.serialport.dao.SerialPortDAO;
import com.newline.serialport.model.AndroidUpgradeBean;
import com.newline.serialport.setting.HHTDeviceManager;
import com.newline.serialport.utils.GlobalConfig;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/8/28 14:24
 * @describe 更新Android系统数据包
 *
 * 中间部分的有用信息 -> 版本，型号(版控)，文件路径，文件的md5   中间用 ' | ' 分割
 */
public class UpgradeRecevierModel extends RecevierSerialPortModel {

    //1.包头格式
    private static final String PACKAGE_HEADER = "7F";
//    /**
//     *  2.数据{@link #dataHex}的长度N/2 -1+7
//     *  例如： dataHex = "53 54 12 34"  length = 8/2 -1 + 7 = 11 转16进制,最终lengthHex = 0B
//     */
    protected String lengthHex;
    //3.提供信息指令格式
    //private static final String TYPE_OFFER_DATA = "0B";
    //4.提供AndroidUpgrade数据类型
    //private static String OFFER_TYPE = "7A";
    //3+4
    private static final String DATA_TYPE = "0B7A";
    //5.数据的内容
    //protected String dataHex;
    //6.校验信息
    //private static final String CHECK_SUM = "01";
    //7.数据包结束格式
    //private static final String PACKAGE_END = "CF";

    //数据包结束固定格式 （6+7）
    private static final String PACKAGE_END_FIX = "01CF";

    private String rawData;
    //转换后真实数据内容
    private String realData;


    private Context mContext;
    private AndroidUpgradeBean androidUpgradeBean;

    /**
     *
     * @param hhtDeviceManager
     * @param rawData  传输过来的所有数据
     * @param dataHex  实际有用的数据
     * @param context
     */
    public UpgradeRecevierModel(HHTDeviceManager hhtDeviceManager, String rawData,String dataHex, Context context) {
        super(hhtDeviceManager);
        this.rawData = rawData;
        //16进制字符串 转换为普通字符串
        realData = RecevierSerialPortModel.hexStr2Str(dataHex);
        //分隔符 |
        String[] split = realData.split("\\|");
        if(split.length == 4){
            androidUpgradeBean = new AndroidUpgradeBean();
            androidUpgradeBean.setVersion(split[0]);
            androidUpgradeBean.setModel(split[1]);
            androidUpgradeBean.setUpgradePackagePath(split[2]);
            androidUpgradeBean.setMd5(split[3]);
            androidUpgradeBean.setRecordTime(Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai")).getTime().toString());
        }
        mContext = context;
    }


    @Override
    public void action() {
        //通知Android系统，升级信息
        if(androidUpgradeBean == null){
            return ;
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(GlobalConfig.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(GlobalConfig.KEY_SP_ANDROID_UPGRADE_INFO,androidUpgradeBean.parseToJSONObject().toString());
        edit.commit();

        putDataChangeMessage();
        Log.d("newlinePort","recevier android upgrade:"+androidUpgradeBean.toString());

    }

    @Override
    public String retryContent() {
        return rawData;
    }


    /**
     * 通知数据更新
     */
    private void putDataChangeMessage(){
        try {
            ContentValues conValue = new ContentValues();
            conValue.put(SerialPortDAO.NAME, SerialPortDAO.KEY_ANDROID_UPGRADE);
            conValue.put(SerialPortDAO.VALUE, "");
            mContext.getContentResolver().update(Uri.withAppendedPath(SerialPortDAO.SERIAL_PORT_URI, SerialPortDAO.KEY_ANDROID_UPGRADE), conValue, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 数据是否匹配Android升级包 格式
     * @param rawData
     * @return 若匹配，返回有用信息。若不匹配，则返回null
     */
    public static String match(String rawData){
        String data = rawData.replace(" ","");
        if(data!= null && data.length()>12){
            boolean startMatch = data.startsWith(PACKAGE_HEADER);
            boolean endMatch = data.endsWith(PACKAGE_END_FIX);
            boolean dataTypeMatch = data.regionMatches(4,DATA_TYPE,0,4);


            //校验前后数据格式无误
            if(startMatch && endMatch && dataTypeMatch){
                //校验数据长度是否无误
                String dataLength = data.substring(2,4);
                int realLength = new BigInteger(dataLength, 16).intValue()-7+1;
                boolean dataLengthMatch = (data.length()/2-6) == realLength;
                if(dataLengthMatch){
                    //"7F0A0B8A 12345678  01CF";   获取中间实际数据
                    return data.substring(8,data.length()-4);
                }else{
                    return null;
                }

            }
        }

        return null;

    }



}
