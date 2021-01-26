package com.clp3z.vitcord.mvp.base.contracts;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.model.response.User;

import java.util.List;

/**
 * Interface that specifies which operation should be provided to HomeFragment, by HomePresenter
 * From the presenter layer to the view layer

 * Created by Clelia López on 4/20/19
 */

public interface HomeFragmentProvided
        extends PresenterMethods.HomeFragment {

    /**
     * Searches for users using the name given.
     * Pagination is considered, so subsequent calls are made to find best matches.
     *
     * Algorithm:
     * - Take 20
     * - Saves them in Map< Name,User >
     * - Apply startWith criteria and delete those who don't match
     * - Repeat until the first 20 are found
     * - Repeat each time a new letter is typed
     */
    User searchUserByName(String name);

    /**
     * Returns an user using their id.
     * Assumed unique.
     */
    User searchUserById(String id);

    /**
     * Returns users in pages of size 10.
     */
    void getUsers();

    /**
     * Sets RecyclerView.OnScrollListener which implements infinite scrolling
     */
    void setPagerScrollerListener(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager);

    /**
     * Initializes the operation in the presenter considering weather it was intended to execute
     * network operation (anew), or will be using local data (reusing)
     *
     * @param isReusing indicates if the fragment is being reused
     */
    void initialize(boolean isReusing);
}
