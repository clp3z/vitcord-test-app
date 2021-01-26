package com.clp3z.vitcord.mvp.base.contracts;

import com.clp3z.vitcord.mvp.base.interfaces.ModelMethods;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.model.response.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clelia López on 4/23/19
 */

public interface HomeModelProvided
        extends ModelMethods<PresenterMethods.Required> {

    /**
     * Save following user on SharedPreference and memory
     */
    void saveFollowing(User user);

    /**
     * Save following user from SharedPreference and memory
     */
    void removeFollowing(User user);

    /**
     * Retrieve following users from SharedPreference and memory
     */
    ArrayList<User> getFollowings();

    int getFollowingsCount();

    /**
     * Searches user in the persistent collection
     */
    boolean doesExist(User user);
}
