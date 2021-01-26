package com.clp3z.vitcord.mvp.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.generic.GenericActivity;
import com.clp3z.vitcord.mvp.base.interfaces.ActivityMethods;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.presenter.TemplatePresenterActivity;
import com.clp3z.vitcord.mvp.view.adapter.ViewPagerAdapter;
import com.clp3z.vitcord.mvp.view.fragment.HomeFragment;
import com.clp3z.vitcord.mvp.view.fragment.ProfileFragment;

/**
 * Created by Clelia López on 04/20/2019
 */
public class MainActivity
        extends GenericActivity<ActivityMethods, PresenterMethods.Activity, TemplatePresenterActivity>
        implements ActivityMethods, View.OnClickListener {

    /**
     * Attributes
     */
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    /**
     * This hook method is called when the Activity is instantiated.
     *
     * @param savedInstanceState saved previous state, it may be null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(TemplatePresenterActivity.class, this);

        // Initialize all view components defined in the activity's layout
        initializeViews();
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        setViewPager();
        setTabLayout();
    }

    /**
     * Specifies which fragments will be contained on the {@param viewpager} and sets its adapter.
     */
    private void setViewPager() {
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), R.string.home_label, R.drawable.bg_tab_home);
        adapter.addFragment(new ProfileFragment(), R.string.profile_label, R.drawable.bg_tab_profile);
        viewPager.setAdapter(adapter);
    }

    /**
     * Customizes each tab individually
     */
    private void setTabLayout() {
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(adapter.getTabView(i));
            }
        }
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param view Indicates the view component pressed by the user
     */
    @Override
    public void onClick(View view) {
        getPresenter().handleClick(view.getId());
    }
}
