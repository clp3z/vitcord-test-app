package com.clp3z.vitcord.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.contracts.HomeModelProvided;
import com.clp3z.vitcord.mvp.base.contracts.ProfileFragmentProvided;
import com.clp3z.vitcord.mvp.base.contracts.ProfileFragmentRequired;
import com.clp3z.vitcord.mvp.base.generic.GenericPresenter;
import com.clp3z.vitcord.mvp.base.interfaces.Listener;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.base.network.MyProfileRequest;
import com.clp3z.vitcord.mvp.model.HomeModel;
import com.clp3z.vitcord.mvp.model.response.User;
import com.clp3z.vitcord.mvp.view.activity.ContainerActivity;
import com.clp3z.vitcord.mvp.view.activity.HomeActivity;
import com.clp3z.vitcord.mvp.view.activity.ProfileActivity;
import com.clp3z.vitcord.mvp.view.fragment.HomeFragment;

import java.lang.ref.WeakReference;

/**
 * Created by Clelia López on 03/10/2015
 */
@SuppressWarnings("FieldCanBeLocal")
public class ProfilePresenter
        extends GenericPresenter<PresenterMethods.Required, HomeModelProvided, HomeModel>
        implements PresenterMethods.Required, ProfileFragmentProvided, Listener.OnServerResponseListener<User> {

    /**
     * Attributes
     */
    private WeakReference<ProfileFragmentRequired> view;
    private Context context;

    /**
     * Return codes
     */
    private static int GET_MY_PROFILE = 1;


    /**
     * Hook method called when a new instance of this presenter is created.
     *
     * @param view A reference to the View layer.
     */
    @Override
    public void onCreate(ProfileFragmentRequired view) {

        // Initialized the defined WeakReference
        this.view = new WeakReference<>(view);

        context = this.view.get().getActivityContext();

        // Invoke the special onCreate() method in GenericPresenter to instantiate the model
        super.onCreate(HomeModel.class, this);
    }

    /**
     * Hook method dispatched by the GenericActivity framework to update a View object after a
     * runtime configuration change has occurred.
     *
     * @param view active RequiredFragmentMethods
     */
    @Override
    public void onConfigurationChange(ProfileFragmentRequired view) {

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
        Intent intent = new Intent(context, HomeActivity.class);

        // Title bar set up
        Bundle bundle = new Bundle();
        bundle.putString(ContainerActivity.getToolbarTitleTag(), getContext().getString(R.string.following_label));

        // Data set up
        intent.putExtra(ContainerActivity.getBundleTag(), bundle);
        intent.putParcelableArrayListExtra(ContainerActivity.getArrayParcelableExtrasTag(),
                getModel().getFollowings());

        // Start simple activity
        context.startActivity(intent);
    }

    @Override
    public void initialize() {
        // Fetches and updates count / (Different instance of HomeModel)
        getModel().fetch();
        view.get().updateFollowingCount(getModel().getFollowingsCount());

        // Starts request to get profile
        getMyProfile();
    }

    @Override
    public void getMyProfile() {
        MyProfileRequest myProfileRequest = new MyProfileRequest(null, GET_MY_PROFILE, this);
        myProfileRequest.performServerRequest();
    }

    @Override
    public void onServerResponse(User response, int returnCode) {
        // GET_MY_PROFILE
        if (returnCode == 1) {
            if (response != null)
                view.get().updateProfile(response);
        }
    }
}
