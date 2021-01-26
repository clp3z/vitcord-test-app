package com.clp3z.vitcord.mvp.view.custom;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Clelia López on 4/21/19
 */

public abstract class BaseViewHolder
        extends RecyclerView.ViewHolder {


    private int currentPosition;


    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        currentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
