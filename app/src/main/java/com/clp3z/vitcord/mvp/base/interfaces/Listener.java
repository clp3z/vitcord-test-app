package com.clp3z.vitcord.mvp.base.interfaces;

import com.clp3z.vitcord.mvp.model.response.User;

/**
 * Created by Clelia López on 3/8/17
 */

public interface Listener {

    interface PermissionListener {
        void startComponent();
        void showDialog();
    }

    interface OnItemClickListener {
        void onItemSelected(User user);
    }

    interface OnFollowListener {
        void followUser(User user);
        void unfollowUser(User user);
    }

    interface OnServerResponseListener<R> {

        /**
         * Process the response returned by the web server of type {@param <R>}
         *
         * @param response POJO that maps web server's response
         */
        void onServerResponse(R response, int returnCode);
    }

    interface ExecutorListener {
        void execute(String name);
    }

    interface BroadcastListener<T> {
        void onReceive(T result);
    }
}

