package com.clp3z.vitcord.mvp.base.util;

import android.os.Build;

/**
 * Created by Clelia López on 9/22/16
 */

public class AndroidTools {

    /**
     * Reference: https://source.android.com/setup/start/build-numbers

    Pie	            9               API level 28
    Oreo	        8.1.0	        API level 27
    Oreo	        8.0.0	        API level 26
    Nougat	        7.1	            API level 25
    Nougat	        7.0	            API level 24
    Marshmallow	    6.0	            API level 23
    Lollipop	    5.1	            API level 22
    Lollipop	    5.0	            API level 21
    KitKat	        4.4 - 4.4.4	    API level 19

    */

    public static int getAndroidAPI() {
        int version;
        switch (Build.VERSION.RELEASE.substring(0,3)) {
            case "4.4": version = 19; break;        // KitKat
            case "5.0": version = 21; break;        // Lollipop
            case "5.1": version = 22; break;        // Lollipop
            case "6.0": version = 23; break;        // Marshmallow
            case "7.0": version = 24; break;        // Nougat
            case "7.1": version = 25; break;        // Nougat
            case "8.0.0": version = 26; break;      // Oreo
            case "8.1.0": version = 27; break;      // Oreo
            case "9": version = 28; break;          // Pie
            default: version = -1; break;
        }
        return version;
    }
}
