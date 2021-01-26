package com.clp3z.vitcord.mvp.base.contracts;

import com.clp3z.vitcord.mvp.base.interfaces.FragmentMethods;
import com.clp3z.vitcord.mvp.model.response.User;
import com.clp3z.vitcord.mvp.view.adapter.HomeAdapter;

/**
 * Created by Clelia López on 4/21/19
 */

public interface HomeFragmentRequired
        extends FragmentMethods {

    void displayLoadingDialog();

    void dismissLoadingDialog();

    HomeAdapter getAdapter();

    @SuppressWarnings("SameParameterValue")
    void setSwipeRefreshState(boolean isRefreshing);
}
