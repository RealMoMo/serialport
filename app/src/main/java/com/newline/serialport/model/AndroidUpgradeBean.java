package com.newline.serialport.model;

import android.support.annotation.Keep;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Realmo
 * @version 1.0.0
 * @name serialport
 * @email momo.weiye@gmail.com
 * @time 2020/9/2 12:15
 * @describe Android 升级包信息类
 */
@Keep
public class AndroidUpgradeBean {




    /**
     * 升级包文件，对应在ops的路径
     */
    private String upgradePackagePath;
    /**
     * 机型
     */
    private String model;
    /**
     * 升级包版本
     */
    private String version;
    /**
     * 升级包文件md5
     */
    private String md5;

    //===========================以下是自增内容 非发送端传输的数据==============================

    /**
     * 记录传输的升级时间 （Asia/Shanghai）
     */
    private String recordTime;

    public void setUpgradePackagePath(String upgradePackagePath) {
        this.upgradePackagePath = upgradePackagePath;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUpgradePackagePath() {
        return upgradePackagePath;
    }

    public String getModel() {
        return model;
    }

    public String getVersion() {
        return version;
    }

    public String getMd5() {
        return md5;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String toString() {
        return "AndroidUpgradeBean{" +
                "upgradePackagePath='" + upgradePackagePath + '\'' +
                ", model='" + model + '\'' +
                ", version='" + version + '\'' +
                ", md5='" + md5 + '\'' +
                ", recordTime='" + recordTime + '\'' +
                '}';
    }

    public JSONObject parseToJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("upgradePackagePath", upgradePackagePath);
            jsonObject.putOpt("model", model);
            jsonObject.putOpt("version", version);
            jsonObject.putOpt("md5", md5);
            jsonObject.putOpt("recordTime", recordTime);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return jsonObject;
    }
}
