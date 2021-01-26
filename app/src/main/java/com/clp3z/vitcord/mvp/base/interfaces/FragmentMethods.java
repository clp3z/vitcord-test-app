package com.clp3z.vitcord.mvp.base.interfaces;

import android.content.Context;

import com.clp3z.vitcord.mvp.base.generic.GenericActivity;

/**
 * This interface describes which capabilities must be implemented, and therefore provided, to the
 * Presenter object by any Fragment within the MVP framework.
 *
 * Created by Clelia López on 5/5/17
 */
public interface FragmentMethods {

    /**
     * Returns the activity at which this fragment is attached
     */
    <T extends GenericActivity> T getActivity(Class<T> classType);

    /**
     *
     * Returns parent activity's context
     */
    Context getActivityContext();
}
