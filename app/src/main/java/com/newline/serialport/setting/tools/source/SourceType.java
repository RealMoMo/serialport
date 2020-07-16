package com.newline.serialport.setting.tools.source;




import android.support.annotation.StringDef;

import com.hht.middleware.model.SourceValue;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Realmo
 * @version 1.0.0
 * @name hht setting lib
 * @email momo.weiye@gmail.com
 * @time 2019/7/23 15:13
 * @describe
 */
@StringDef({SourceType.OPS, SourceType.HDMI1, SourceType.HDMI2,
        SourceType.HDMI3, SourceType.VGA, SourceType.HDMI_FRONT,
        SourceType.TYPE_C, SourceType.DP})
@Retention(RetentionPolicy.SOURCE)
public @interface SourceType {

    String OPS = SourceValue.OPS;
    String HDMI1 = SourceValue.HDMI1;
    String HDMI2 = SourceValue.HDMI2;
    String HDMI3 = SourceValue.HDMI3;
    String VGA = SourceValue.VGA;
    String HDMI_FRONT = SourceValue.FRONT;
    String TYPE_C = SourceValue.TYPEC;
    String DP = SourceValue.DP;
}
