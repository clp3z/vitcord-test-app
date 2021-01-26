package com.clp3z.vitcord.mvp.base.interfaces;

import android.content.Context;

/**
 * The base interface that a Model class must implement.
 *
 * Created by Clelia López on 5/5/17
 */
public interface ModelFrameworkMethods<RPM> {

    /**
     * Hook method dispatched by the GenericModel framework to initialize a Model object
     * after it's been instantiated.
     *
     * @param context activity context
     * @param presenter The currently active RequiredModelMethods.
     */
    void onCreate(Context context, RPM presenter);

    /**
     * Hook method called when the Model object is destroyed.
     *
     * @param isChangingConfigurations True if a runtime configuration triggered the onDestroy() call.
     */
    void onDestroy(boolean isChangingConfigurations);
}

