package com.clp3z.vitcord.mvp.base.util;

import android.content.Context;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.global.Enums;

/**
 * Created by Clelia López on 8/25/16
 */
public class FontTools {

    /**
     * Attributes
     */
    private static Enums.CustomTypeface defaultTypeface = Enums.CustomTypeface.ROBOTO_REGULAR;
    private static final int FONT_ARRAY = R.array.fonts;


    public static String getFontTypeface(Context context, Integer typeface) {
        String fontArray[];
        fontArray = context.getResources().getStringArray(FONT_ARRAY);
        return fontArray[typeface];
    }

    public static String getDefaultFontType(Context context) {
        String fontArray[] = context.getResources().getStringArray(FONT_ARRAY);
        return fontArray[defaultTypeface.getValue()];
    }
}
