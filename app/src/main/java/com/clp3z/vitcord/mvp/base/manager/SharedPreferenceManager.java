package com.clp3z.vitcord.mvp.base.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.clp3z.vitcord.mvp.base.util.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by Clelia López on 9/25/16
 */
public class SharedPreferenceManager {

    private static final String TAG = "SharedPreferenceManager";
    private static Logger logger = new Logger(TAG);

    /**
     * Get default shared preference object
     *
     * @param context application / activity context
     * @return SharedPreference reference object
     */
    private static SharedPreferences getSharedPreference(Context context) {
        if (context != null)
            return PreferenceManager.getDefaultSharedPreferences(context);
        else {
            logger.logError("getSharedPreference", "Context is null");
            throw new NullPointerException("Context is null");
        }
    }

    /**
     * Put generic value in Default shared preference file
     *
     * @param context application / activity context
     * @param key chosen key name
     * @param value generic T value
     */
    public static <T> void putValue(Context context, String key, T value) {
        Class typeClass = value.getClass();
        if (Boolean.class.isAssignableFrom(typeClass)) {
            getSharedPreference(context)
                .edit()
                .putBoolean(key, (Boolean)value)
                .apply();
        } else if (Integer.class.isAssignableFrom(typeClass)) {
            getSharedPreference(context)
                .edit()
                .putInt(key, (Integer)value)
                .apply();
        } else if (Long.class.isAssignableFrom(typeClass)) {
            getSharedPreference(context)
                .edit()
                .putLong(key, (Long)value)
                .apply();
        } else if (String.class.isAssignableFrom(typeClass)) {
            getSharedPreference(context)
                .edit()
                .putString(key, (String)value)
                .apply();
        }
    }

    /**
     * Get generic value from Default shared preference file
     *
     * @param key associated key name
     * @param defaultValue generic T expected value if no associated value exist
     * @return key associated or default T value if key doesn't exist on Default shared preference file
     */
    public static <T> Object getValue(Context context, String key, T defaultValue) {
        Object result = null;
        Class typeClass = defaultValue.getClass();
        if (Boolean.class.isAssignableFrom(typeClass))
            result = getSharedPreference(context).getBoolean(key, (Boolean)defaultValue);
        else if (Integer.class.isAssignableFrom(typeClass))
            result = getSharedPreference(context).getInt(key, (Integer)defaultValue);
        else if (Long.class.isAssignableFrom(typeClass))
            result = getSharedPreference(context).getLong(key, (Long)defaultValue);
        else if (String.class.isAssignableFrom(typeClass))
            result = getSharedPreference(context).getString(key, (String)defaultValue);
        return result;
    }

    /**
     * Get generic value from Default shared preference file
     *
     * @param key associated key name
     * @param defaultValue generic T expected value if no associated value exist
     * @return key associated or default T value if key doesn't exist on Default shared preference file
     */
    public static <T> T getValue(Context context, Class<T> classType, String key, T defaultValue) {
        T result = null;
        if (Boolean.class.isAssignableFrom(classType))
            result = classType.cast(getSharedPreference(context).getBoolean(key, (Boolean)defaultValue));
        else if (Integer.class.isAssignableFrom(classType))
            result = classType.cast(getSharedPreference(context).getInt(key, (Integer)defaultValue));
        else if (Long.class.isAssignableFrom(classType))
            result = classType.cast(getSharedPreference(context).getLong(key, (Long)defaultValue));
        else if (String.class.isAssignableFrom(classType))
            result = classType.cast(getSharedPreference(context).getString(key, (String)defaultValue));
        return result;
    }

    /**
     * Clear  Default shared preference file
     *
     * @param context application / activity context
     */
    public static void clear(Context context) {
        getSharedPreference(context).edit().clear().apply();
    }

    /**
     * Saves any object to common Shared Preferences
     *
     * @param context Context for initialization
     * @param key Entry name to reference the saved object
     * @param data Object that needs to be saved
     */
    public static void saveObject(Context context, String key, Object data) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
        String json = gson.toJson(data);
        editor.putString(key, json);
        editor.apply();
    }

    /**
     * Gets the generic object stored in shared preferences under the given key.
     *
     * @param context Context for initialization
     * @param key Entry name that references the saved object
     * @param type The specific genericized type of the generic class
     * @return Object stored
     */
    public static Object getObject(Context context, String key, Type type) {
        Gson gson = new Gson();
        String json = getSharedPreference(context).getString(key, "");
        return gson.fromJson(json, type);
    }
}