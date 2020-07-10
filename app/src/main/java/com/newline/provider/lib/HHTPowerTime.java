package com.newline.provider.lib;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Realmo
 * @version 1.0.0
 * @name HHTProvider
 * @email momo.weiye@gmail.com
 * @time 2020/3/4 18:25
 * @describe
 */
public class HHTPowerTime implements Parcelable {

    public int _id;

    public int sun;
    public int mon;
    public int tues;
    public int wed;
    public int thur;
    public int fri;
    public int sat;
    /**
     * [0,23]
     */
    public int hour;
    /**
     * [0,59]
     */
    public int min;
    /**
     * default enable
     */
    public int timeEnable = 1;

    public String startSource;


    public HHTPowerTime() {
    }

    public HHTPowerTime(int _id) {
        this._id = _id;
    }


    public JSONObject parseToJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("_id", this._id);
            jsonObject.putOpt("sun", this.sun);
            jsonObject.putOpt("mon", this.mon);
            jsonObject.putOpt("tues", this.tues);
            jsonObject.putOpt("wed", this.wed);
            jsonObject.putOpt("thur", this.thur);
            jsonObject.putOpt("fri", this.fri);
            jsonObject.putOpt("sat", this.sat);
            jsonObject.putOpt("hour", this.hour);
            jsonObject.putOpt("min", this.min);
            jsonObject.putOpt("timeEnable", this.timeEnable);
            jsonObject.putOpt("startSource", this.startSource);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return jsonObject;
    }


    protected HHTPowerTime(Parcel in) {
        _id = in.readInt();
        sun = in.readInt();
        mon = in.readInt();
        tues = in.readInt();
        wed = in.readInt();
        thur = in.readInt();
        fri = in.readInt();
        sat = in.readInt();
        hour = in.readInt();
        min = in.readInt();
        timeEnable = in.readInt();
        startSource = in.readString();
    }

    public static final Creator<HHTPowerTime> CREATOR = new Creator<HHTPowerTime>() {
        @Override
        public HHTPowerTime createFromParcel(Parcel in) {
            return new HHTPowerTime(in);
        }

        @Override
        public HHTPowerTime[] newArray(int size) {
            return new HHTPowerTime[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(sun);
        dest.writeInt(mon);
        dest.writeInt(tues);
        dest.writeInt(wed);
        dest.writeInt(thur);
        dest.writeInt(fri);
        dest.writeInt(sat);
        dest.writeInt(hour);
        dest.writeInt(min);
        dest.writeInt(timeEnable);
        dest.writeString(startSource);
    }

    @Override
    public String toString() {
        return "HHTPowerTime{" +
                "_id=" + _id +
                ", sun=" + sun +
                ", mon=" + mon +
                ", tues=" + tues +
                ", wed=" + wed +
                ", thur=" + thur +
                ", fri=" + fri +
                ", sat=" + sat +
                ", hour=" + hour +
                ", min=" + min +
                ", timeEnable=" + timeEnable +
                ", startSource='" + startSource + '\'' +
                '}';
    }
}
