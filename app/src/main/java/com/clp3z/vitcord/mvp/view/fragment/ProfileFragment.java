package com.clp3z.vitcord.mvp.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.contracts.ProfileFragmentProvided;
import com.clp3z.vitcord.mvp.base.contracts.ProfileFragmentRequired;
import com.clp3z.vitcord.mvp.base.generic.GenericFragment;
import com.clp3z.vitcord.mvp.model.response.User;
import com.clp3z.vitcord.mvp.presenter.ProfilePresenter;
import com.clp3z.vitcord.mvp.view.activity.ContainerActivity;
import com.clp3z.vitcord.mvp.view.activity.ProfileActivity;
import com.clp3z.vitcord.mvp.view.custom.CircleImageView;
import com.clp3z.vitcord.mvp.view.custom.CustomTextView;
import com.squareup.picasso.Picasso;

import static com.clp3z.vitcord.mvp.base.global.Constants.FOLLOWING_PARAMETER;

/**
 * Created by Clelia López on 4/21/19
 */

@SuppressWarnings("FieldCanBeLocal")
public class ProfileFragment
        extends GenericFragment<ProfileFragmentRequired, ProfileFragmentProvided, ProfilePresenter>
        implements ProfileFragmentRequired, View.OnClickListener {

    /**
     * Attributes
     */
    private RelativeLayout relativeLayout;
    private CircleImageView avatarCircleImageView;
    private CustomTextView nameTextView;
    private CustomTextView descriptionTextView;
    private CustomTextView followingTextView;

    private CardView cardView;
    private CustomTextView profileTitleTextView;
    private View bottomView;

    private User user;
    private boolean onMyProfile = true;

    private BroadcastReceiver broadcastReceiver;


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
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize retained fragment state
        isRetainedFragment = true;

        // Retrieve data
        Bundle arguments = getArguments();
        if (arguments != null) {
            user = arguments.getParcelable(ContainerActivity.getParcelableExtrasTag());
            onMyProfile = false;
        }

        super.onCreate(ProfilePresenter.class, this);

        // Initialize the view components defined in the fragment's layout
        initializeViews();

        return relativeLayout;
    }

    /**
     * Initialize the Views and GUI widgets.
     */
    private void initializeViews() {
        avatarCircleImageView = relativeLayout.findViewById(R.id.avatar_circle_image_view);
        nameTextView = relativeLayout.findViewById(R.id.name_text_view);
        descriptionTextView = relativeLayout.findViewById(R.id.description_text_view);
        followingTextView = relativeLayout.findViewById(R.id.following_text_view);

        cardView = relativeLayout.findViewById(R.id.card_view);
        cardView.setOnClickListener(this);

        // Registers local broadcast receiver to update following counter
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // Update following count
                followingTextView.setText(getString(R.string.number_placeholder,
                        intent.getIntExtra(FOLLOWING_PARAMETER,0)));
            }
        };

        // Specify expected data on intent
        IntentFilter selectionIntentFilter = new IntentFilter();
        selectionIntentFilter.addAction(FOLLOWING_PARAMETER);

        // Register
        LocalBroadcastManager.getInstance(activityContext)
                .registerReceiver(broadcastReceiver, selectionIntentFilter);

        // Starts request to get profile
        if (onMyProfile)
            getPresenter().initialize();
        else {
            // Hiding required views
            profileTitleTextView = relativeLayout.findViewById(R.id.profile_title_text_view);
            bottomView = relativeLayout.findViewById(R.id.bottom_view);
            profileTitleTextView.setVisibility(View.GONE);
            bottomView.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);

            // Update user profile
            updateProfile(user);
        }
    }

    /**
     * Hook OnClickListener
     */
    @Override
    public void onClick(View view) {
        getPresenter().handleClick(view.getId());
    }

    @Override
    public void updateProfile(User user) {
        Picasso.get()
            .load(user.getAvatar())
            .into(avatarCircleImageView);

        nameTextView.setText(getString(R.string.name_placeholder, user.getFirstName(), user.getLastName()));
        descriptionTextView.setText(user.getDescription());

        // Hide loading dialog when attach to ProfileActivity
        if (activityContext instanceof ProfileActivity) {
            ProfileActivity activity = (ProfileActivity) activityContext;
            activity.hideDialogLoading();
        }
    }

    @Override
    public void updateFollowingCount(int count) {
        followingTextView.setText(getString(R.string.number_placeholder, count));
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(activityContext).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}

