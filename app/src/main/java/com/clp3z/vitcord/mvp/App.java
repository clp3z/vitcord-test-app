package com.clp3z.vitcord.mvp;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

/**
 * Created by Clelia López on 5/5/17
 */

@SuppressWarnings("FieldCanBeLocal")
public class App
        extends Application {

    /**
     * Attributes
     */
    private static boolean onProduction = false;

    private Context applicationContext = null;

    private Picasso.Builder picassoBuilder = null;


    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize application context
        applicationContext = this;
    }

    // Picasso

    public Picasso.Builder getPicassoBuilder(Context context) {
        if (picassoBuilder == null)
            picassoBuilder = new Picasso.Builder(context);

        return picassoBuilder;
    }

    // Release Mode

    public static boolean isOnProduction() {
        return onProduction;
    }

    // Application Context

    public Context getApplicationContext() {
        return applicationContext;
    }
}
