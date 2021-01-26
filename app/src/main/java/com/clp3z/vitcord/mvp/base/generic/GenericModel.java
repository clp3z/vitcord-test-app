package com.clp3z.vitcord.mvp.base.generic;

import android.content.Context;

import com.clp3z.vitcord.mvp.base.interfaces.ModelMethods;
import com.clp3z.vitcord.mvp.base.manager.SharedPreferenceManager;
import com.clp3z.vitcord.mvp.base.util.Logger;

import java.lang.ref.WeakReference;

/**
 * Created by Clelia López on 9/25/16
 */
@SuppressWarnings("FieldCanBeLocal")
public abstract class GenericModel<RPM>
        implements ModelMethods<RPM> {

    /**
     * Attributes
     */
    private String TAG = getClass().getSimpleName();
    protected Logger logger = new Logger(TAG);
    protected Context context;
    protected WeakReference<RPM> presenter;


    /**
     * Hook method called when a new instance of a Model is created.
     *
     * @param context activity context
     * @param presenter a reference to the Presenter layer
     */
    public void onCreate(Context context, RPM presenter) {
        logger.log("onCreate", "(stacktrace)");

        this.presenter = new WeakReference<>(presenter);
        this.context = context;
    }

    /**
     * Hook method called to shutdown the Model layer
     *
     * @param isChangingConfiguration True if a runtime configuration triggered the onDestroy call
     */
    public void onDestroy(boolean isChangingConfiguration) {
        // no-op
    }

    /**
     * Saves any value on a general shared preference file
     *
     * @param key chosen key name
     * @param value chosen value type
     */
    @Override
    public <T> void saveOnSharedPreference(String key, T value) {
        SharedPreferenceManager.putValue(context, key, value);
    }

   /**
     * Retrieves value from the general shared preference file
     *
     * @param key chosen key name
     * @param defaultValue expected value if no associated value exist
     */
    @Override
    public <T> Object getFromSharedPreference(String key, T defaultValue) {
        return SharedPreferenceManager.getValue(context, key, defaultValue);
    }

    /**
     * Retrieves value from the general shared preference file
     *
     * @param key chosen key name
     * @param defaultValue expected value if no associated value exist
     */
    @Override
    public <T> T getFromSharedPreference(Class<T> classType, String key, T defaultValue) {
        return SharedPreferenceManager.getValue(context, classType, key, defaultValue);
    }
}
