package com.clp3z.vitcord.mvp.base.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.clp3z.vitcord.mvp.base.interfaces.Listener;

/**
 * Created by Clelia López on 4/12/20
 */
public class GenericBroadcastReceiver<T extends Parcelable>
        extends BroadcastReceiver {

    private String requestCode;
    private Listener.BroadcastListener<T> broadcastListener;


    public GenericBroadcastReceiver(String requestCode, Listener.BroadcastListener<T> broadcastListener) {
        this.requestCode = requestCode;
        this.broadcastListener = broadcastListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        T result = intent.getParcelableExtra(requestCode);
        broadcastListener.onReceive(result);
    }
}
