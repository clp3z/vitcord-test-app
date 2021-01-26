package com.clp3z.vitcord.mvp.base.contracts;

import com.clp3z.vitcord.mvp.base.interfaces.FragmentMethods;
import com.clp3z.vitcord.mvp.model.response.User;

/**
 * Created by Clelia López on 4/21/19
 */

public interface ProfileFragmentRequired
        extends FragmentMethods {

    void updateProfile(User user);

    void updateFollowingCount(int count);
}
