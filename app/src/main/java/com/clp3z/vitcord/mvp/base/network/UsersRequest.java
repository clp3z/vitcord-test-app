package com.clp3z.vitcord.mvp.base.network;

import com.clp3z.vitcord.mvp.base.interfaces.Listener;
import com.clp3z.vitcord.mvp.base.util.Logger;
import com.clp3z.vitcord.mvp.model.response.User;

import java.util.List;

/**
 * Created by Clelia López on 6/16/18
 */
public class UsersRequest
        extends ServerRequestStrategy <List<Integer>, List<User>> {

    public UsersRequest(List<Integer> parameters, int returnCode, Listener.OnServerResponseListener listener) {
        super(parameters, returnCode, listener);

        TAG = getClass().getSimpleName();
        logger = new Logger(TAG);
    }

    @Override
    public void setCall() {
        call = Client.getRestAPIService().getUsers(parameter.get(0), parameter.get(1));
    }
}
