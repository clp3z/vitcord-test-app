package com.clp3z.vitcord.mvp.model;

import com.clp3z.vitcord.mvp.base.generic.GenericModel;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.base.util.Logger;

/**
  * Created by Clelia López on 04/12/2015.
 */
public class GlobalModel
        extends GenericModel<PresenterMethods.Required> {

    private String TAG = getClass().getSimpleName();
    protected Logger logger = new Logger(TAG);

    public GlobalModel() {
        super();
    }

    @Override
    public void fetch() {
        // Do nothing
    }
}
