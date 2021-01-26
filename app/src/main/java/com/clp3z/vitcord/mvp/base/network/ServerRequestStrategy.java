package com.clp3z.vitcord.mvp.base.network;

import android.support.annotation.NonNull;

import com.clp3z.vitcord.mvp.base.interfaces.Listener;
import com.clp3z.vitcord.mvp.base.util.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Clelia López on 6/16/18
 */
@SuppressWarnings("WeakerAccess")
public abstract class ServerRequestStrategy <P,R> {

    /**
     * Attributes
     */
    protected String TAG;
    protected Logger logger;

    /**
     * POJO that maps the parameters required by the server to execute this request
     */
    protected P parameter;

    /**
     * Identifier to differentiate from multiple calls on same class
     */
    protected int returnCode;

    /**
     * An invocation of a Retrofit method that sends a request to a web server and returns a response
     */
    protected Call<R> call;

    /**
     * Callback listener to be invoke when the server returns
     */
    protected Listener.OnServerResponseListener listener;


    public ServerRequestStrategy(P parameter, int returnCode, Listener.OnServerResponseListener listener) {
        this.parameter = parameter;
        this.returnCode = returnCode;
        this.listener = listener;
    }

    /**
     * Initializes retrofit call with the appropriate {@param @RestAPIServer}
     */
    public abstract void setCall();

    /**
     * Implements the actual server request using Retrofit
     */
    public void performServerRequest() {

        setCall();

        if (call != null) {
            call.enqueue(
                new Callback<R>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onResponse(@NonNull Call<R> call, @NonNull Response<R> response) {
                        if (response.isSuccessful())
                            listener.onServerResponse(response.body(), returnCode);
                        else {
                            logger.logError("onResponse", "Something went wrong! \n" + response.message());
                            listener.onServerResponse(null, -1);
                        }
                    }

                    @SuppressWarnings("unchecked")
                    @Override
                    public void onFailure(@NonNull Call<R> call, @NonNull Throwable throwable) {
                        logger.logError("onFailure", throwable.getCause().toString());
                        listener.onServerResponse(null, -1);
                    }
                }
            );
        } else
            throw new NullPointerException("call:Call<R> is null");
    }
}
