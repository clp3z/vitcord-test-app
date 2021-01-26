package com.clp3z.vitcord.mvp.base.interfaces;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.clp3z.vitcord.mvp.base.manager.RetainedFragmentManager;
import com.clp3z.vitcord.mvp.base.global.Enums;

/**
 * This interface describes which capabilities must be implemented, and therefore provided, to the
 * Presenter object by any Activity within the MVP framework.
 *
 * Created by Clelia López on 5/5/17
 */
public interface ActivityMethods {

    /**
     * Return the RetainedFragmentManager.
     */
    RetainedFragmentManager getRetainedFragmentManager();

    /**
     * Return the Activity context.
     */
    Context getActivityContext();

    /**
     * Toolbar setter
     *
     * @param toolbar Object
     * @param isHomeEnable true if it should return to previous activity
     */
    void setToolbar(Toolbar toolbar, boolean isHomeEnable);

    /**
     * Toolbar getter
     *
     * @return Toolbar
     */
    Toolbar getToolbar();

    /**
     * Sets permission listener callback invoked by onRequestPermissionsResult after a permission
     * request has been made.
     *
     * @param permissionListener listener object
     */
    void setPermissionListener(Listener.PermissionListener permissionListener);

    /**
     * Hides menu item with id {{@param id}
     */
    void hideMenuItem(int id);

    /**
     * Shows menu item with id {{@param id}
     */
    void showMenuItem(int id);

    /**
     * Sets a new icon for item with id {{@param id}
     * @param iconId id resource drawable
     */
    void setItemIcon(int id, int iconId);

    /**
     * @return View container for this activity
     */
    View getRootView();

    /**
     * Starts another activity
     *
     * @param activityClass class parameter
     */
    void startActivity(Class<?> activityClass);

    /**
     * Allows fragment placement in the activity
     *
     * @param containerViewId container layout id
     * @param fragment fragment instance
     */
    <T extends Fragment> void placeFragment(int containerViewId, T fragment);

    /**
     * Allows fragment replacement in the activity
     *
     * @param containerViewId container layout id
     * @param fragment fragment instance
     * @param isStacked indicates whether fragment should be pushed to the back stack
     */
    <T extends Fragment> void replaceFragment(int containerViewId, T fragment, boolean isStacked);

    /**
     * Replace a view with another
     *
     * @param id Id of the view that is going to be replaced
     * @param replacement Replacement view object
     */
    void replaceView(int id, View replacement);

    /**
     * Sets a custom dialog view to be displayed on request of the fragment
     */
    void setDialog(View view);

    /**
     * Sets a custom dialog to be displayed on request of the fragment
     */
    void setDialog(DialogFragment dialog);

    /**
     * Displays dialog with type {@param type}
     *
     * @param type chosen type
     */
    void showDialog(Enums.DialogType type);

    /**
     * Dismiss dialog with type {@param type}
     *
     * @param type chosen type
     */
    void dismissDialog(Enums.DialogType type);

    /**
     * Renders a snack bar at the bottom of the screen
     *
     * @param view parent view
     * @param message resource ID with desired message
     * @param textColor resource ID with desired text color
     * @param backgroundColor resource ID with desired background color
     * @param textSize resource size color
     * @param action resource ID with desired action title
     * @param duration metrics
     * @param listener OnClickListener on container activity or fragment
     */
    @SuppressWarnings("SameParameterValue")
    @SuppressLint("NewApi")
    void showSnackBar(View view, int message, int textColor, int backgroundColor,
                      int textSize, int action, int duration, @Nullable View.OnClickListener listener);

    /**
     * This method is used to hide a keyboard after a user has finished typing the url.
     *
     * @param windowToken the token of the window that is making the request, returned by
     * View.getWindowToken()
     */
    void hideKeyboard(IBinder windowToken);

    /**
     * Update activity view content with new {@param data}
     */
    <T> void updateView(T data);

    /**
     * Update activity callback.
     *
     * At his point, the Presenter has requested the data to the Model, the Model has executed the
     * request,has parsed the data and has retrieved them back to the presenter, for the view to use.
     */
    void updateView();
}
