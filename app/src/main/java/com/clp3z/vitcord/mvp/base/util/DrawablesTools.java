package com.clp3z.vitcord.mvp.base.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by Clelia López on 3/10/17
 */

public class DrawablesTools {

    public static void tint(Context context, int drawable, int color) {
        Drawable drawableIcon = ContextCompat.getDrawable(context, drawable);
        assert drawableIcon != null;
        drawableIcon = DrawableCompat.wrap(drawableIcon);
        if (AndroidTools.getAndroidAPI() >= 21)
            drawableIcon.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN);
        else {
            DrawableCompat.setTintMode(drawableIcon, PorterDuff.Mode.SRC_IN);
            DrawableCompat.setTint(drawableIcon, ContextCompat.getColor(context, color));
        }
    }


    static int getId(Context context, String name) {
        Resources resources = context.getResources();
        return resources.getIdentifier(name, "drawable", context.getPackageName());
    }

    public static Drawable getDrawable(Context context, String name) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(name, "drawable", context.getPackageName());
        return ContextCompat.getDrawable(context, resourceId);
    }
}
