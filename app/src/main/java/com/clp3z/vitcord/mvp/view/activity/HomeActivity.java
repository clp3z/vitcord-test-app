package com.clp3z.vitcord.mvp.view.activity;

import android.os.Bundle;

import com.clp3z.vitcord.mvp.model.response.User;
import com.clp3z.vitcord.mvp.view.fragment.HomeFragment;

/**
 * Created by Clelia López on 4/21/19
 */

public class HomeActivity
        extends ContainerActivity<HomeFragment, User> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initializes ContainerActivity
        super.onCreate(savedInstanceState);

        // Creates the fragment instance and attaches the fragment
        super.initializeFragment(HomeFragment.class);
    }
}
