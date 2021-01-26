package com.clp3z.vitcord.mvp.base.generic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.interfaces.ActivityMethods;
import com.clp3z.vitcord.mvp.base.interfaces.Listener;
import com.clp3z.vitcord.mvp.base.interfaces.PresenterFrameworkMethods;
import com.clp3z.vitcord.mvp.base.manager.RetainedFragmentManager;
import com.clp3z.vitcord.mvp.base.util.AndroidTools;
import com.clp3z.vitcord.mvp.base.util.Logger;
import com.clp3z.vitcord.mvp.base.global.Constants;
import com.clp3z.vitcord.mvp.base.global.Enums;

/**
 * This class provides a framework for mediating access to an object residing in the Presenter
 * layer in the Model-View-Presenter (MVP) pattern. It automatically handles runtime configuration
 * changes in conjunction with an instance of the Presenter (P), which must implement the
 * PresenterMethods interfaces.
 * It extends LifecycleLoggingActivity so all lifecycle hook method calls are automatically logged.
 * It also provides access to the Activity and Application contexts in the View layer.
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
public abstract class GenericActivity<RVM, PPM, P extends PresenterFrameworkMethods<RVM>>
        extends LifecycleLoggingActivity
        implements ActivityMethods {

    /**
     * Attributes
     */
    private String TAG = getClass().getSimpleName();
    protected Logger logger = new Logger(TAG);
    private String presenterTAG;
    private P presenter;
    protected Toolbar toolbar = null;
    protected View dialogView = null;
    protected DialogFragment dialog = null;
    protected Menu menu;
    protected Listener.PermissionListener permissionListener = null;

    /**
     * Permissions
     */
    protected boolean readExternalStorage = false;
    protected boolean writeExternalStorage = false;
    protected boolean camera = false;
    protected boolean recordAudio = false;
    protected boolean accessFineLocation = false;
    protected boolean accessCoarseLocation = false;
    protected boolean readContacts = false;
    protected boolean writeContacts = false;
    protected boolean readPhoneState = false;
    protected boolean callPhone = false;
    protected boolean all = true;

    /**
     * Used to retain the ProvidedPresenterMethods state between runtime configuration changes
     */
    private final RetainedFragmentManager retainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(), TAG);


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

        presenterTAG = presenter.getSimpleName();
        // noinspection TryWithIdenticalCatches
        try {
            if (retainedFragmentManager.firstTimeIn())
                initialize(presenter, view);
            else
                reinitialize(presenter, view);
        } catch (InstantiationException e) {
            logger.log("onCreate", e.getMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.log("onCreate", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the GenericActivity fields.
     *
     * @throws IllegalAccessException exception
     * @throws InstantiationException exception
     */
    private void initialize(Class<P> presenter, RVM view)
            throws InstantiationException, IllegalAccessException {

        logger.log("initialize", "(stacktrace)");

        // Create the Presenter object.
        this.presenter = presenter.newInstance();

        // Put instances into the RetainedFragmentManager under a simple name.
        retainedFragmentManager.put(presenterTAG, this.presenter);

        // Calls onCreate hook method on the Presenter layer.
        this.presenter.onCreate(view);
    }

    /**
     * Reinitialize the GenericActivity fields after a runtime configuration change.
     *
     * @throws IllegalAccessException exception
     * @throws InstantiationException exception
     */
    private void reinitialize(Class<P> presenter, RVM view)
            throws InstantiationException, IllegalAccessException {

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
     * Return the RetainedFragmentManager.
     */
    public RetainedFragmentManager getRetainedFragmentManager() {
        return retainedFragmentManager;
    }

    /**
     * Return the Activity context.
     */
    @Override
    public Context getActivityContext() {
        return this;
    }

    /**
     * Toolbar setter
     *
     * @param toolbar Widget view
     */
    @SuppressLint("NewApi")
    public void setToolbar(Toolbar toolbar, boolean isHomeEnable) {
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (isHomeEnable) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
            }
        }
    }

    /**
     * Toolbar getter
     *
     * @return Toolbar
     */
    public Toolbar getToolbar() {
        return toolbar;
    }


    /**
     * Sets permission listener callback invoked by onRequestPermissionsResult after a permission
     * request has been made.
     *
     * @param permissionListener listener object
     */
    public void setPermissionListener(Listener.PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
            @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            switch (requestCode) {
                case Constants.READ_EXTERNAL_STORAGE:
                    readExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.WRITE_EXTERNAL_STORAGE:
                    writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.CAMERA:
                    camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.RECORD_AUDIO:
                    recordAudio = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.FINE_LOCATION:
                    accessFineLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.COURSE_LOCATION:
                    accessCoarseLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.READ_CONTACTS:
                    readContacts = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.WRITE_CONTACTS:
                    writeContacts = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.READ_PHONE_STATE:
                    readPhoneState = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.CALL_PHONE:
                    callPhone = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    break;
                case Constants.ALL:
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            all = false;
                            break;
                        }
                    }
                break;
            }
        }

        if (readExternalStorage || writeExternalStorage || camera || recordAudio ||
                accessFineLocation || accessCoarseLocation || readContacts || writeContacts ||
                readPhoneState || callPhone || all) {

            if (permissionListener != null)
                permissionListener.startComponent();
        } else
            permissionListener.showDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Complete as appropriate

        return super.onOptionsItemSelected(item);
    }

    /**
     * Hides menu item with id {{@param id}
     */
    public void hideMenuItem(int id) {
        if (menu != null) {
            MenuItem item = menu.findItem(id);
            item.setVisible(false);
        }
    }

    /**
     * Shows menu item with id {{@param id}
     */
    public void showMenuItem(int id) {
        if (menu != null) {
            MenuItem item = menu.findItem(id);
            item.setVisible(true);
        }
    }

    /**
     * Sets a new icon for item with id {{@param id}
     * @param iconId id resource drawable
     */
    public void setItemIcon(int id, int iconId) {
        if (menu != null) {
            MenuItem item = menu.findItem(id);
            item.setIcon(iconId);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * @return View container for this activity
     */
    public View getRootView() {
        return  ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * Starts another activity
     *
     * @param activityClass class parameter
     */
    public void startActivity(Class<?> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    /**
     * Allows fragment placement in the activity
     *
     * @param containerViewId container layout id
     * @param fragment fragment instance
     */
    public <T extends Fragment> void placeFragment(int containerViewId, T fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .add(containerViewId, fragment, fragment.getClass().getSimpleName())
            .commit();
    }

    /**
     * Allows fragment replacement in the activity
     *
     * @param containerViewId container layout id
     * @param fragment fragment instance
     * @param isStacked indicates whether fragment should be pushed to the back stack
     */
    public <T extends Fragment> void replaceFragment(int containerViewId, T fragment, boolean isStacked) {
        if (isStacked) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getName())
                .commit();
        } else {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragment.getClass().getSimpleName())
                .commit();
        }
    }

    /**
     * Replace a view with another
     *
     * @param id Id of the view that is going to be replaced
     * @param replacement view object replacement
     */
    public void replaceView(int id, View replacement) {
        View view = findViewById(id);
        ViewGroup parent;
        if (view != null) {
            parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            parent.removeViewAt(index);
            parent.addView(replacement, index);
        } else
            logger.log("replaceView", "Cannot replace, \"id\" was not found for view replacement: "
                    + replacement.getClass().getSimpleName());
    }

    /**
     * Sets a custom dialog view to be displayed on request of the fragment
     */
    public void setDialog(View view) {
        dialogView = view;
    }

    /**
     * Sets a custom dialog to be displayed on request of the fragment
     */
    public void setDialog(DialogFragment dialog) {
        this.dialog = dialog;
    }

    /**
     * Displays dialog with type {@param type}
     *
     * @param type chosen type
     */
    public void showDialog(Enums.DialogType type) {
        switch (type) {
            case ALERT:
                break;
            case VIEW:
                dialogView.setVisibility(View.VISIBLE);
                break;
            case CUSTOM:
                dialog.show(getSupportFragmentManager(), "CustomDialog");
                break;
        }
    }

    /**
     * Dismiss dialog with type {@param type}
     *
     * @param type chosen type
     */
    public void dismissDialog(Enums.DialogType type) {
        switch (type) {
            case ALERT:
                break;
            case VIEW:
                dialogView.setVisibility(View.GONE);
                break;
            case CUSTOM:
                dialog.dismiss();
                break;
        }
    }

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
    @SuppressLint("NewApi")
    public void showSnackBar(View view, int message, int textColor, int backgroundColor,
            int textSize, int action, int duration, @Nullable View.OnClickListener listener) {

        Snackbar snackbar;

        if (listener != null) {
            snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(action, listener)
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            snackbar = Snackbar
                .make(view, message, duration)
                .setDuration(duration);
        }

        int textViewId = android.support.design.R.id.snackbar_text;
        TextView textView = snackbar.getView().findViewById(textViewId);
        textView.setTypeface(null, Typeface.BOLD);

        if (textColor > 0)
            textView.setTextColor(ContextCompat.getColor(this, textColor));

        if (backgroundColor > 0) {
            View container = snackbar.getView();
            container.setBackgroundColor(ContextCompat.getColor(this, backgroundColor));
        }

        if (textSize > 0) {
            if (AndroidTools.getAndroidAPI() >= 17)
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }

        snackbar.show();
    }

    /**
     * This method is used to hide the keyboard after a user has finished typing the url.
     *
     * @param windowToken the token of the window that is making the request, returned by View.getWindowToken()
     */
    public void hideKeyboard(IBinder windowToken) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Update activity view content with new {@param data}
     */
    @Override
    public <T> void updateView(T data) {
        // Implemented on concrete activity if needed
    }

    /**
     * Update activity callback.
     *
     * At his point, the Presenter has requested the data to the Model, the Model has executed the
     * request,has parsed the data and has retrieved them back to the presenter, for the view to use.
     */
    @Override
    public void updateView() {
        // Implemented on concrete activity if needed
    }
}
