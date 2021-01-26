package com.clp3z.vitcord.mvp.base.interfaces;

/**
 * This interface describes which capabilities must be implemented, and therefore provided, to the
 * Presenter object by any Model within the MVP framework.
 *
 * Created by Clelia López on 5/5/17
 */
@SuppressWarnings("SameParameterValue")
public interface ModelMethods<RPM>
        extends ModelFrameworkMethods<RPM> {

    /**
     * Saves any value on a general shared preference file
     *
     * @param key chosen key name
     * @param value chosen value type
     */
    <T> void saveOnSharedPreference(String key, T value);

    /**
     * Retrieves value from the general shared preference file
     *
     * @param key chosen key name
     * @param defaultValue expected value if no associated value exist
     */
    <T> Object getFromSharedPreference(String key, T defaultValue);

    /**
     * Retrieves value from the general shared preference file
     *
     * @param key chosen key name
     * @param defaultValue expected value if no associated value exist
     */
    <T> T getFromSharedPreference(Class<T> classType, String key, T defaultValue);

    /**
     * Fetches persistent data if exist
     */
    void fetch();
}
