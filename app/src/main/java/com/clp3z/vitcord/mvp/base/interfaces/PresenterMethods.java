package com.clp3z.vitcord.mvp.base.interfaces;

import android.content.Context;

import com.clp3z.vitcord.mvp.base.contracts.HomeFragmentRequired;
import com.clp3z.vitcord.mvp.base.contracts.ProfileFragmentRequired;

/**
 * Created by Clelia López on 5/5/17
 */
public interface PresenterMethods {

    /**
     * This interface describes which capabilities must be implemented, and therefore provided
     * to the Activity, by any Presenter within the MVP framework.
     */
    interface Activity
            extends PresenterFrameworkMethods<ActivityMethods> {

        /**
         * Initialize presenter execution
         */
        void initialize();

        /**
         * Hook method dispatched by an activity or fragment indicating the id of the view element that
         * was pressed. The presenter will perform the appropriate action.
         *
         * @param viewId view Id
         */
        void handleClick(int viewId);
    }

    /**
     * This interface describes which capabilities must be implemented, and therefore provided
     * to the Fragment, by any Presenter within the MVP framework.
     */
    interface Fragment
            extends PresenterFrameworkMethods<FragmentMethods> {

        /**
         * Initialize presenter execution
         */
        void initialize();

        /**
         * Hook method dispatched by an activity or fragment indicating the id of the view element that
         * was pressed. The presenter will perform the appropriate action.
         *
         * @param viewId view Id
         */
        void handleClick(int viewId);
    }

    /**
     * This interface describes which capabilities must be implemented, and therefore provided
     * to the HomeFragment, by HomePresenter.
     */
    interface HomeFragment
        extends PresenterFrameworkMethods<HomeFragmentRequired> {

        /**
         * Initialize presenter execution
         */
        void initialize();

        /**
         * Hook method dispatched by an activity or fragment indicating the id of the view element that
         * was pressed. The presenter will perform the appropriate action.
         *
         * @param viewId view Id
         */
        void handleClick(int viewId);
    }

    /**
     * This interface describes which capabilities must be implemented, and therefore provided
     * to the ProfileFragment, by ProfilePresenter.
     */
    interface ProfileFragment
            extends PresenterFrameworkMethods<ProfileFragmentRequired> {

        /**
         * Initialize presenter execution
         */
        void initialize();

        /**
         * Hook method dispatched by an activity or fragment indicating the id of the view element that
         * was pressed. The presenter will perform the appropriate action.
         *
         * @param viewId view Id
         */
        void handleClick(int viewId);
    }

    /**
     * This interface describes which capabilities must be implemented and are required by the Model
     * object in any Presenter
     */
    interface Required {

        /**
         * @return activity or fragment context
         */
        Context getContext();
    }
}
