package com.newline.simple.chip;

import android.os.RemoteException;

import com.hht.simple.BuildConfig;
import com.hht.tools.log.Logger;

public class UniteImpl {
    public static final String PATH_SP = "/dev/ttyAMA1";
    public static final String PATH_OPS = "/dev/ttyAMA0";

    public static boolean isLangGuo() {
        return BuildConfig.BUILD_TYPE.equals("lango");
    }
}
