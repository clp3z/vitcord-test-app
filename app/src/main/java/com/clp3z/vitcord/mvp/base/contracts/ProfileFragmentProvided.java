package com.clp3z.vitcord.mvp.base.contracts;

import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;

/**
 * Created by Clelia López on 4/20/19
 */

public interface ProfileFragmentProvided
        extends PresenterMethods.ProfileFragment {

    /**
     * Performs call to update profile
     */
    void getMyProfile();
}
