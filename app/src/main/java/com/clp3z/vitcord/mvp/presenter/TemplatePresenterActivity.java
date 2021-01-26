package com.clp3z.vitcord.mvp.presenter;

import android.content.Context;

import com.clp3z.vitcord.mvp.base.generic.GenericPresenter;
import com.clp3z.vitcord.mvp.base.interfaces.ActivityMethods;
import com.clp3z.vitcord.mvp.base.interfaces.ModelMethods;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.model.GlobalModel;

import java.lang.ref.WeakReference;


/**
 * Created by Clelia López on 03/10/2015
 */
public class TemplatePresenterActivity
        extends GenericPresenter<PresenterMethods.Required, ModelMethods, GlobalModel>
        implements PresenterMethods.Required, PresenterMethods.Activity {

    /**
     * Attributes
     */
    private WeakReference<ActivityMethods> view;
    private Context context;


    /**
     * Hook method called when a new instance of this presenter is created.
     *
     * @param view A reference to the View layer.
     */
    @Override
    public void onCreate(ActivityMethods view) {
        // Initialized the defined WeakReference
        this.view = new WeakReference<>(view);

        context = this.view.get().getActivityContext();

        // Invoke the special onCreate() method in GenericPresenter to instantiate the model
        super.onCreate(GlobalModel.class, this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public ModelMethods getModel() {
        return new GlobalModel();
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void initialize() {

    }
}
