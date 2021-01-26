package com.clp3z.vitcord.mvp.base.generic;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.clp3z.vitcord.mvp.base.interfaces.FragmentMethods;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterFrameworkMethods;
import com.clp3z.vitcord.mvp.base.manager.RetainedFragmentManager;
import com.clp3z.vitcord.mvp.base.util.Logger;

/**
 * This class provides a framework for mediating access to an object residing in the Presenter
 * layer in the Model-View-Presenter (MVP) pattern. It automatically handles runtime configuration
 * changes in conjunction with an instance of the Presenter (P), which must implement the
 * PresenterMethods interfaces.
 * It extends LifecycleLoggingFragment so all lifecycle hook method calls are automatically logged.
 *
 * The three types used by a GenericActivity are the following:
 *
 * RequiredViewMethods (RVM): the class or interfaces that defines the methods available to the
 * Presenter object from the View layer.
 *
 * ProvidedPresenterMethods (PPM: the class or interfaces that defines the methods available to the
 * View layer from the Presenter object.
 *
 * Presenter (P): the class used by the GenericActivity framework to instantiate a Presenter object.
 *
 * Modified by
 * @author Clelia López
 */
public abstract class GenericFragment<RVM, PPM, P extends PresenterFrameworkMethods<RVM>>
        extends LifecycleLoggingFragment
        implements FragmentMethods {

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    protected Logger logger = new Logger(TAG);
    private String presenterTAG;
    private P presenter;
    private RetainedFragmentManager retainedFragmentManager;
    protected Activity activityContext;
    protected boolean isRetainedFragment;


    /**
     * Hook method called when a fragment is attached to its hosting activity.
     *
     * @param context Activity context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            activityContext = (Activity) context;

        retainedFragmentManager = new RetainedFragmentManager(
                activityContext.getFragmentManager(), TAG);
    }

    /**
     * Initialize or reinitialize the Presenter layer.
     * Handle configuration-related events, including the initial creation of an Activity and any
     * subsequent runtime configuration changes.
     * This must be called after the onCreate(Bundle saveInstanceState) method.
     *
     * @param presenter Class used to create a Presenter object.
     * @param view Reference to the RequiredViewMethods object.
     */
    public void onCreate(Class<P> presenter, RVM view) {
        logger.log("onCreate", "(stacktrace)");

        if(isRetainedFragment) {
            presenterTAG = presenter.getSimpleName();
            //noinspection TryWithIdenticalCatches
            try {
                if (retainedFragmentManager.firstTimeIn())
                    initialize(presenter, view);
                else
                    reinitialize(presenter, view);
            } catch (Fragment.InstantiationException e) {
                logger.log("onCreate", e.getMessage());
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                logger.log("onCreate", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            try {
                initializeNotRetained(presenter, view);
            } catch (IllegalAccessException e) {
                logger.log("onCreate", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Initialize the GenericFragment fields.
     *
     * @throws IllegalAccessException exception
     * @throws Fragment.InstantiationException exception
     */
    private void initialize(Class<P> presenter, RVM view)
            throws Fragment.InstantiationException, IllegalAccessException {

        logger.log("initialize", "(stacktrace)");

        // Create the Presenter object.
        try {
            this.presenter = presenter.newInstance();
        } catch (InstantiationException | java.lang.InstantiationException e) {
            logger.log("initialize", e.getMessage());
            e.printStackTrace();
        }

        // Put instances into the RetainedFragmentManager under a simple name.
        retainedFragmentManager.put(presenterTAG, this.presenter);

        // Calls onCreate hook method on the Presenter layer.
        this.presenter.onCreate(view);
    }

    /**
     * Initialize the GenericFragment fields without retained functionality
     *
     * @throws Fragment.InstantiationException exception
     * @throws IllegalAccessException exception
     */
    private void initializeNotRetained(Class<P> presenter, RVM view)
            throws Fragment.InstantiationException, IllegalAccessException {

        logger.log("initializeNotRetained", "(stacktrace)");

        // Create the Presenter object.
        try {
            this.presenter = presenter.newInstance();
        } catch (InstantiationException | java.lang.InstantiationException e) {
            logger.log("initializeNotRetained", e.getMessage());
            e.printStackTrace();
        }

        // Calls onCreate hook method on the Presenter layer.
        this.presenter.onCreate(view);
    }

    /**
     * Reinitialize the GenericActivity fields after a runtime configuration change.
     *
     * @throws IllegalAccessException exception
     * @throws Fragment.InstantiationException exception
     */
    private void reinitialize(Class<P> presenter, RVM view)
            throws Fragment.InstantiationException, IllegalAccessException {

        logger.log("reinitialize", "(stacktrace)");

        // Restoring states from the RetainedFragmentManager under a simple name.
        this.presenter = retainedFragmentManager.get(presenterTAG);

        // Checks Presenter state
        if (this.presenter == null)
            initialize(presenter, view);
        else
            this.presenter.onConfigurationChange(view);
    }

    /**
     * Return the ProvidedPresenterMethods instance for use by application logic in the View layer.
     */
    @SuppressWarnings("unchecked")
    public PPM getPresenter() {
        return (PPM) this.presenter;
    }

    /**
     * Return Activity's context
     */
    public Context getActivityContext() {
        return activityContext;
    }

    /**
     * Return parent Activity
     */
    public <T extends GenericActivity> T getActivity(Class<T> classType) {
        return classType.cast(getActivity());
    }
}
