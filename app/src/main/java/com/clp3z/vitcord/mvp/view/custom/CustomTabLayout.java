package com.clp3z.vitcord.mvp.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.util.FontTools;

/**
 * Created by Clelia López on 9/8/16
 */
public class CustomTabLayout
        extends TabLayout
        implements TabLayout.OnTabSelectedListener {

    /**
     * Attributes
     */
    private Typeface typeface;
    private Typeface defaultTypeface;


    public CustomTabLayout(Context context) {
        super(context);

        initializeView();
    }

    public CustomTabLayout(Context context, AttributeSet attributes) {
        super(context, attributes);
        if (!isInEditMode())
            parseAttributes(context, attributes);

        initializeView();
    }

    public void initializeView() {
        this.addOnTabSelectedListener(this);
    }

    /**
     * Parse view attributes typeface
     *
     * @param context view context
     * @param attributes attribute set
     */
    public void parseAttributes(Context context, AttributeSet attributes) {
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.CustomTabLayout);

        String textViewTypeface = typedArray.getString(R.styleable.CustomTabLayout_typeface);
        if (textViewTypeface != null) {
            String path = FontTools.getFontTypeface(context, Integer.parseInt(textViewTypeface));
            typeface = Typeface.createFromAsset(context.getAssets(), path);
        } else
            defaultTypeface = Typeface.createFromAsset(context.getAssets(), FontTools.getDefaultFontType(context));

        typedArray.recycle();
    }

    @Override
    public void onTabSelected(Tab tab) {
        ViewGroup container = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) container.getChildAt(tab.getPosition());
        AppCompatTextView textView = (AppCompatTextView) tabView.getChildAt(1);
        textView.setTypeface(typeface);
    }

    @Override
    public void onTabUnselected(Tab tab) {
        ViewGroup container = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) container.getChildAt(tab.getPosition());
        AppCompatTextView textView = (AppCompatTextView) tabView.getChildAt(1);
        textView.setTypeface(typeface);
    }

    @Override
    public void onTabReselected(Tab tab) {
        // no-op
    }
}
