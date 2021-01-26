package com.clp3z.vitcord.mvp.base.network;

import com.clp3z.vitcord.mvp.base.interfaces.Listener;
import com.clp3z.vitcord.mvp.base.util.Logger;
import com.clp3z.vitcord.mvp.model.response.User;

/**
 * Created by Clelia López on 6/16/18
 */
public class MyProfileRequest
        extends ServerRequestStrategy <Void, User> {

    public MyProfileRequest(Void parameters, int returnCode, Listener.OnServerResponseListener listener) {
        super(parameters, returnCode, listener);

        TAG = getClass().getSimpleName();
        logger = new Logger(TAG);
    }

    @Override
    public void setCall() {
        call = Client.getRestAPIService().getMyProfile();
    }
}
