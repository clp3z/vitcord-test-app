package com.clp3z.vitcord.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.clp3z.vitcord.mvp.base.contracts.HomeFragmentProvided;
import com.clp3z.vitcord.mvp.base.contracts.HomeFragmentRequired;
import com.clp3z.vitcord.mvp.base.contracts.HomeModelProvided;
import com.clp3z.vitcord.mvp.base.generic.GenericPresenter;
import com.clp3z.vitcord.mvp.base.interfaces.Listener;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.base.manager.FutureTaskManager;
import com.clp3z.vitcord.mvp.base.network.UsersRequest;
import com.clp3z.vitcord.mvp.base.util.PagerScrollListener;
import com.clp3z.vitcord.mvp.model.HomeModel;
import com.clp3z.vitcord.mvp.model.response.User;
import com.clp3z.vitcord.mvp.view.activity.ContainerActivity;
import com.clp3z.vitcord.mvp.view.activity.ProfileActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.clp3z.vitcord.mvp.base.global.Constants.FOLLOWING_PARAMETER;
import static com.clp3z.vitcord.mvp.base.global.Constants.PAGE_SIZE;
import static com.clp3z.vitcord.mvp.base.global.Constants.START_PAGE;
import static com.clp3z.vitcord.mvp.base.global.Constants.TOTAL_PAGES;

/**
 * Created by Clelia López on 04/21/2019
 */
@SuppressWarnings("FieldCanBeLocal")
public class HomePresenter
        extends GenericPresenter<PresenterMethods.Required, HomeModelProvided, HomeModel>
        implements
            PresenterMethods.Required,
            HomeFragmentProvided,
            Listener.OnServerResponseListener<List<User>>,
            Listener.ExecutorListener,
            Listener.OnItemClickListener,
            Listener.OnFollowListener {

    /**
     * Attributes
     */
    private WeakReference<HomeFragmentRequired> view;
    private Context context;

    /**
     * Current page
     */
    private int currentPage = 1;

    /**
     * Return codes
     */
    private static int GET_USERS_CALL = 1;
    private static int SEARCH_USERS_BY_ID_CALL = 2;
    private static int SEARCH_USERS_BY_NAME_CALL = 3;

    private UsersRequest usersRequest;

    private boolean isLoading;
    private boolean isLastPage;

    private LocalBroadcastManager localBroadcastManager;

    private boolean isReusing;


    /**
     * Hook method called when a new instance of this presenter is created.
     *
     * @param view A reference to the View layer.
     */
    @Override
    public void onCreate(HomeFragmentRequired view) {

        // Initialized the defined WeakReference
        this.view = new WeakReference<>(view);

        context = this.view.get().getActivityContext();

        // Invoke the special onCreate() method in GenericPresenter to instantiate the model
        super.onCreate(HomeModel.class, this);

        // Initializing local broadcast manager
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    /**
     * Hook method dispatched by the GenericActivity framework to update a View object after a
     * runtime configuration change has occurred.
     *
     * @param view active RequiredFragmentMethods
     */
    @Override
    public void onConfigurationChange(HomeFragmentRequired view) {

    }

    @Override
    public Context getContext() {
        return context;
    }

    /**
     * Called when the user clicks a button to perform some action
     *
     * @param viewId Indicates the id of the button pressed by the user
     */
    @Override
    public void handleClick(int viewId) {
        // no-op
    }

    @Override
    public void initialize() {
        // Do nothing
    }

    @Override
    public void initialize(boolean isReusing) {
        // Fetches data if exist
        getModel().fetch();

        view.get().getAdapter().setPresenter(this);
        view.get().getAdapter().setOnItemClickListener(this);
        view.get().getAdapter().setOnFollowListener(this);

        this.isReusing = isReusing;

        if (isReusing)
            view.get().getAdapter().addAll(getModel().getFollowings());
        else
            // Starts request for first 10 users
            getUsers();
    }

    @Override
    public void onItemSelected(User user) {
        Intent intent = new Intent(context, ProfileActivity.class);

        // Title bar set up
        Bundle bundle = new Bundle();
        bundle.putString(ContainerActivity.getToolbarTitleTag(), user.getFirstName() + "'s Profile");

        // Data set up
        intent.putExtra(ContainerActivity.getBundleTag(), bundle);
        intent.putExtra(ContainerActivity.getParcelableExtrasTag(), user);

        // Start simple activity
        context.startActivity(intent);
    }

    @Override
    public void followUser(User user) {
        getModel().saveFollowing(user);

        // Notify to update
        localBroadcastManager.sendBroadcast(getCounterInter());
    }

    @Override
    public void unfollowUser(User user) {
        getModel().removeFollowing(user);

        // Remove on adapter
        if (isReusing)
            view.get().getAdapter().remove(user);

        // Notify to update
        localBroadcastManager.sendBroadcast(getCounterInter());
    }

    private Intent getCounterInter() {
        Intent intent = new Intent(FOLLOWING_PARAMETER);
        intent.putExtra(FOLLOWING_PARAMETER, getModel().getFollowingsCount());
        return intent;
    }

    @Override
    public User searchUserByName(String name) {
        return null;
    }

    @Override
    public User searchUserById(String id) {
        return null;
    }

    @Override
    public void getUsers() {
        // Downloading items to later load on RecyclerView, this operation needs to end before refresh
        isLoading = true;

        usersRequest = new UsersRequest(
                new ArrayList<>(Arrays.asList(currentPage, PAGE_SIZE)), GET_USERS_CALL, this);
        usersRequest.performServerRequest();
    }

    @Override
    public void onServerResponse(List<User> response, int returnCode) {
        switch (returnCode) {
            // GET_USERS_CALL
            case 1:
                if (response != null) {

                    // Verify if loading item should be removed
                    if (currentPage != START_PAGE)
                        view.get().getAdapter().removeLoadingItem();

                    // Update data collection on RecyclerView
                    view.get().getAdapter().addAll(response);

                    // Update progress bar, since it's no longer refreshing
                    view.get().setSwipeRefreshState(false);

                    // Verify if limit page has been reached
                    if (currentPage < TOTAL_PAGES)
                        view.get().getAdapter().addLoading();
                    else
                        isLastPage = true;

                    // Update loading state, since it's now completed
                    isLoading = false;

                    // Next request should be for the following page
                    currentPage++;

                    // Wait for seconds to prepare
                    FutureTaskManager.executeAfter(this, "splash", 3, false);
                }
                break;
        }
    }

    @Override
    public void setPagerScrollerListener(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        recyclerView.addOnScrollListener(new HomePagerScrollListener(linearLayoutManager));
    }

    @Override
    public void execute(String name) {
        // Dismissing dialog
        view.get().dismissLoadingDialog();
    }


    /**
     * Concrete implementation of {@param PagerScrollListener}
     */
    class HomePagerScrollListener
            extends PagerScrollListener {

        HomePagerScrollListener(LinearLayoutManager layoutManager) {
            super(layoutManager);
        }

        @Override
        protected void loadMoreItems() {
            if (!isReusing)
                getUsers();
        }

        @Override
        public boolean isLastPage() {
            return isLastPage;
        }

        @Override
        public boolean isLoading() {
            return isLoading;
        }
    }
}
