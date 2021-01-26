package com.clp3z.vitcord.mvp.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.util.FontTools;

/**
 * Custom TextView with attributes font and typeface
 *
 * Created by Clelia López on 8/27/16
 */
public class CustomEditText
        extends AppCompatEditText {

    /**
     * Attributes
     */
    private boolean isValid;


    public CustomEditText(Context context) {
        super(context);
        if (!isInEditMode())
            setDefaultTypeface(context);
    }

    public CustomEditText(Context context, AttributeSet attributes) {
        super(context, attributes);
        if (!isInEditMode())
            parseAttributes(context, attributes);
    }

    /**
     * Parse view attributes typeface
     *
     * @param context view context
     * @param attributes attribute set
     */
    public void parseAttributes(Context context, AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.CustomTextView);

        String textViewTypeface = typedArray.getString(R.styleable.CustomEditText_typeface);
        if (textViewTypeface != null) {
            String path = FontTools.getFontTypeface(context, Integer.parseInt(textViewTypeface));
            setTypeFace(context, path);
        }

        typedArray.recycle();
    }

    /**
     * Set typeface
     *
     *  @param path relative path to font typeface
     */
    public void setTypeFace(Context context, String path) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), path);
        if (typeface != null)
            setTypeface(typeface);
    }

    /**
     * Set default typeface
     *
     * @param context view context
     */
    public void setDefaultTypeface(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), FontTools.getDefaultFontType(context));
        if (typeface != null)
            setTypeface(typeface);
    }

    /**
     * Method used to customize the default error icon
     */
    public void setError(CharSequence error) {
        Drawable icon;
        if (error == null) {
            setCompoundDrawables(null, null, null, null);
            icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_check);
            assert icon != null;
            icon.setBounds(0, 0, icon.getIntrinsicWidth() - 10, icon.getIntrinsicHeight() - 10);
            setCompoundDrawables(null, null, icon, null);
            setError(null, icon);
        } else {
            icon = ContextCompat.getDrawable(getContext(), R.drawable.ic_circle_cancel);
            assert icon != null;
            icon.setBounds(0, 0, icon.getIntrinsicWidth() - 10, icon.getIntrinsicHeight() - 10);
            setCompoundDrawables(null, null, icon, null);
            setError(error, icon);
        }
    }

    public String getValue() {
        assert getText() != null;
        return getText().toString().trim();
    }

    @Override
    public boolean performClick() {
        super.performClick();

        return true;
    }
}
