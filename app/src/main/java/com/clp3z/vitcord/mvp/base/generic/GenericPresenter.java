package com.clp3z.vitcord.mvp.base.generic;

import android.content.Context;

import com.clp3z.vitcord.mvp.base.interfaces.ActivityMethods;
import com.clp3z.vitcord.mvp.base.interfaces.ModelFrameworkMethods;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterMethods;
import com.clp3z.vitcord.mvp.base.util.Logger;

/**
 * This class provides a framework that enables a Presenter to access an object residing in the
 * Model layer in the Model-View-Presenter (MVP) pattern.
 *
 * The three types used by a GenericActivity are the following:
 *
 * RequiredPresenterMethods (RPM): the class or interfaces that defines the methods available to the
 * Model object from the Presenter layer.
 *
 * ProvidedModelMethods (PMM: the class or interfaces that defines the methods available to the
 * Presenter layer from the Model object.
 *
 * Model (M): the class used by the GenericPresenter framework to instantiate a Model object.
 *
 * Modified by
 * @author Clelia López
 */
public abstract class GenericPresenter<RPM, PMM, M extends ModelFrameworkMethods<RPM>>
    implements PresenterMethods.Required {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    protected Logger logger = new Logger(TAG);
    private M model;


    /**
     * Hook method that's called when the GenericPresenter is created.
     *
     * @param model Class object that's used to create an model object.
     * @param presenter Reference to the RequiredPresenterMethods object.
     */
    public void onCreate(Class<M> model, RPM presenter) {

        logger.log("onCreate", "(stacktrace)");

        // noinspection TryWithIdenticalCatches
        try {
            initialize(model, presenter);
        } catch (InstantiationException e) {
            logger.log("onCreate", "handleConfiguration - " + e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.log("onCreate", "handleConfiguration - " + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the GenericPresenter fields.
     *
     * @throws IllegalAccessException exception
     * @throws InstantiationException exception
     */
    private void initialize(Class<M> model, RPM presenter)
        throws InstantiationException, IllegalAccessException {

        logger.log("initialize", "(stacktrace)");

        // Create the Model object.
        this.model = model.newInstance();

        // Perform model initialization.
        this.model.onCreate(getContext(), presenter);
    }

    /**
     * Hook method dispatched by the GenericActivity framework to update a View object after a
     * runtime configuration change has occurred.
     *
     * @param view active RequiredActivityMethods
     */
    public void onConfigurationChange(ActivityMethods view) {
        // Implemented on concrete presenter if needed
    }

    /**
     * Hook method called when the Presenter object is destroyed.
     *
     * @param isChangingConfigurations True if a runtime configuration triggered the onDestroy call.
     */
    public void onDestroy(boolean isChangingConfigurations) {
        // Implemented on concrete presenter if needed
    }

    /**
     * Return the initialized ProvidedMethods instance for use by the Presenter layer.
     */
    @SuppressWarnings("unchecked")
    public PMM getModel() {
        return (PMM) this.model;
    }

    /**
     * @return activity or fragment context
     */
    public abstract Context getContext();

    /**
     * Hook method dispatched by an activity or fragment indicating the id of the view element that
     * was pressed. The presenter will perform the appropriate action.
     *
     * @param viewId view Id
     */
    public void handleClick(int viewId) {
        // Implemented on concrete presenter if needed
    }
}

