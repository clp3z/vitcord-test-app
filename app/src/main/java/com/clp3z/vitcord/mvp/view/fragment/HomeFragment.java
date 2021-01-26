package com.clp3z.vitcord.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.contracts.HomeFragmentProvided;
import com.clp3z.vitcord.mvp.base.contracts.HomeFragmentRequired;
import com.clp3z.vitcord.mvp.base.generic.GenericFragment;
import com.clp3z.vitcord.mvp.base.global.Enums;
import com.clp3z.vitcord.mvp.model.response.User;
import com.clp3z.vitcord.mvp.presenter.HomePresenter;
import com.clp3z.vitcord.mvp.view.activity.MainActivity;
import com.clp3z.vitcord.mvp.view.adapter.HomeAdapter;
import com.clp3z.vitcord.mvp.view.custom.CustomEditText;
import com.clp3z.vitcord.mvp.view.custom.CustomTextView;
import com.clp3z.vitcord.mvp.view.dialog.LoadingDialog;

import java.util.ArrayList;

/**
 * Created by Clelia López on 03/10/2017
 */
@SuppressWarnings("FieldCanBeLocal")
public class HomeFragment
        extends GenericFragment<HomeFragmentRequired, HomeFragmentProvided, HomePresenter>
        implements
            HomeFragmentRequired,
            View.OnClickListener,
            View.OnTouchListener,
            SwipeRefreshLayout.
            OnRefreshListener {

    /**
     * Attributes
     */
    private LinearLayout linearLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomEditText searchEditText;
    private CustomTextView titleTextView;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;

    private boolean isHomeActivity;


    /**
     * Hook method called to set up the fragment's user interface. It returns a View object,
     * that is given to the hosting activity to install it into its view hierarchy.
     *
     * @param container view parent of the fragment in the activity, therefore its container
     * @param savedInstanceState object that contains saved state information.
     * @return View object returned by the inflation process
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize retained fragment state
        isRetainedFragment = true;

        // Retrieve data
        Bundle arguments = getArguments();
        if (arguments != null)
            isHomeActivity = true;

        super.onCreate(HomePresenter.class, this);

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        return linearLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Setting loading dialog until loaded
        if (activityContext instanceof  MainActivity) {
            ((MainActivity) activityContext).setDialog(new LoadingDialog());
            displayLoadingDialog();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (activityContext instanceof MainActivity) {
            homeAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {

        // EditText set up
        searchEditText = linearLayout.findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new SearchTextWatcher());
        searchEditText.setOnTouchListener(this);

        // SwipeRefresh set up
        swipeRefreshLayout = linearLayout.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // RecyclerView set up
        recyclerView = linearLayout.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        homeAdapter = new HomeAdapter(activityContext, new ArrayList<User>());
        recyclerView.setAdapter(homeAdapter);

        // Sets pager scroll listener for infinite scrolling
        getPresenter().setPagerScrollerListener(recyclerView, layoutManager);

        if (isHomeActivity) {
            titleTextView = linearLayout.findViewById(R.id.title_text_view);
            titleTextView.setVisibility(View.GONE);
        }

        // Initialize presenter for either network operations or persistent
        getPresenter().initialize(isHomeActivity);
    }

    /**
     * Hook OnClickListener
     */
    @Override
    public void onClick(View view) {
        getPresenter().handleClick(view.getId());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_RIGHT = 2;

        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            // Right Drawable
            if(motionEvent.getRawX() >= (searchEditText.getRight() -
                    searchEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                searchEditText.setText("");

                searchEditText.performClick();
                return true;

            // Left Drawable
            } else if(motionEvent.getRawX() <= (searchEditText.getCompoundDrawables()[DRAWABLE_LEFT]
                    .getBounds().width())) {

                // TODO: implement fade animation
                searchEditText.setVisibility(View.GONE);

                searchEditText.performClick();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRefresh() {
        // Only refresh on scroll
        setSwipeRefreshState(false);
    }

    @Override
    public void displayLoadingDialog() {
        if (activityContext instanceof  MainActivity)
            ((MainActivity) activityContext).showDialog(Enums.DialogType.CUSTOM);
    }

    @Override
    public void dismissLoadingDialog() {
        if (activityContext instanceof  MainActivity)
            ((MainActivity) activityContext).dismissDialog(Enums.DialogType.CUSTOM);
    }

    @Override
    public HomeAdapter getAdapter() {
        return homeAdapter;
    }

    @Override
    public void setSwipeRefreshState(boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

    /**
     * Observes changes on EditText and performs request to the presenter, which later performs
     * actual network request
     */
    class SearchTextWatcher
            implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence textSequence, int start, int count, int after) {
            // No action / Mandatory implementation
        }

        @Override
        public void onTextChanged(CharSequence textSequence, int start, int before, int count) {
            // TODO searchFavoritesAdapter.getFilter().filter(textSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable textSequence) {
            // No action / Mandatory implementation
        }
    }
}
