package com.clp3z.vitcord.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.generic.GenericPresenter;
import com.clp3z.vitcord.mvp.base.interfaces.Listener;
import com.clp3z.vitcord.mvp.model.response.User;
import com.clp3z.vitcord.mvp.presenter.HomePresenter;
import com.clp3z.vitcord.mvp.view.custom.BaseViewHolder;
import com.clp3z.vitcord.mvp.view.custom.CircleImageView;
import com.clp3z.vitcord.mvp.view.custom.CustomTextView;
import com.clp3z.vitcord.mvp.view.custom.CustomToggleButton;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Clelia López on 4/20/19
 */

public class HomeAdapter
        extends RecyclerView.Adapter<BaseViewHolder> {

    /**
     * Attributes
     */
    private List<User> users;

    private Context context;
    private static final int VIEW_TYPE_LOADING = 0;

    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private HomePresenter presenter;

    private Listener.OnItemClickListener onItemClickListener;
    private Listener.OnFollowListener onFollowListener;


    public HomeAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(inflater.inflate(R.layout.item_list_home, parent, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(inflater.inflate(R.layout.item_list_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_NORMAL:
                ((ViewHolder)holder).nameTextView.setText(context.getString(R.string.name_placeholder,
                        users.get(position).getFirstName(), users.get(position).getLastName()));

                ((ViewHolder)holder).followingTextView.setText(context.getString(R.string.number_placeholder,
                        users.get(position).getFollowing().size()));

                Picasso.get()
                    .load(users.get(position).getAvatar())
                    .into(((ViewHolder)holder).avatarCircleImageView);

                // Implement persistence checking
                if (presenter.getModel().doesExist(users.get(position)))
                    ((ViewHolder)holder).followingToggleButton.setChecked(true);
                else
                    ((ViewHolder)holder).followingToggleButton.setChecked(false);

                break;

            case VIEW_TYPE_LOADING:
                // Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible)
            return position == users.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        else
            return VIEW_TYPE_NORMAL;
    }

    private void add(User user) {
        users.add(user);
        notifyItemInserted(users.size() - 1);
    }

    public void addAll(List<User> users) {
        for (User response : users)
            add(response);
    }

    public void remove(User user) {
        int position = users.indexOf(user);
        if (position > -1) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }

    private User getItem(int position) {
        return users.get(position);
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new User());
    }

    public void removeLoadingItem() {
        isLoaderVisible = false;
        int position = users.size() - 1;
        User item = getItem(position);
        if (item != null) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void setOnItemClickListener(Listener.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnFollowListener(Listener.OnFollowListener onFollowListener) {
        this.onFollowListener = onFollowListener;
    }

    public void setPresenter(GenericPresenter presenter) {
        this.presenter = (HomePresenter) presenter;
    }

    class ViewHolder
            extends BaseViewHolder
            implements View.OnClickListener {

        /**
         * Attributes
         */
        CircleImageView avatarCircleImageView;
        CustomTextView nameTextView;
        CustomTextView followingTextView;
        CustomToggleButton followingToggleButton;


        ViewHolder(View view) {
            super(view);
            avatarCircleImageView = view.findViewById(R.id.avatar_circle_image_view);
            nameTextView = view.findViewById(R.id.name_text_view);
            followingToggleButton = view.findViewById(R.id.following_toggle_button);
            followingTextView = view.findViewById(R.id.following_text_view);

            view.setOnClickListener(this);
            followingToggleButton.setOnClickListener(this);
        }

        @Override
        protected void clear() {
            // Do nothing
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (view.getId() == R.id.following_toggle_button) {
                if (followingToggleButton.isChecked())
                    onFollowListener.followUser(users.get(position));
                else
                    onFollowListener.unfollowUser(users.get(position));
            } else {
                onItemClickListener.onItemSelected(users.get(position));
            }
        }
    }


    static class FooterHolder
            extends BaseViewHolder {

        ProgressBar progressBar;

        FooterHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progress_bar);
        }

        @Override
        protected void clear() {
            // Do nothing
        }
    }
}
