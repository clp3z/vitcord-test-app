package com.clp3z.vitcord.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.clp3z.vitcord.R;
import com.clp3z.vitcord.mvp.base.util.Logger;

import java.util.ArrayList;

/**
 * Created by Clelia López on 8/28/18
 */
@SuppressWarnings("unused")
public abstract class ContainerActivity<F extends Fragment, T extends Parcelable>
    extends AppCompatActivity {

    /**
     * String used to identify the extras stored in intent / from parcelable model.
     *
     * If there are no parcelable data to be set, the concrete class header must used the
     * EmptyParcelable, as: extends ContainerActivity< SearchFavoritesFragment, EmptyParcelable >
     */
    private final static String PARCELABLE_EXTRAS = "fragment_parcel";

    /**
     * String used to identify the arrays of {@param Parcelable} stored in intent.
     */
    private final static String ARRAY_PARCELABLE_EXTRAS = "fragment_parcel_array";

    /**
     * Bundle
     */
    private final static String BUNDLE_EXTRAS = "bundle_extras";

    /**
     * Bundle field used to set toolbar title
     */
    private final static String TOOLBAR_TITLE = "toolbar_title";

    /**
     * Attributes
     */
    private final String TAG = getClass().getSimpleName();
    private Logger logger = new Logger(TAG);
    private Bundle fragmentBundle = null;
    private T fragmentParcelable = null;
    private ArrayList<T> parcelableList;

    private RelativeLayout dialogLoading;


    /**
     * Initializes the activity setting the layout and action bar properties.
     * It also retrieves the data extras that were set on the intent at creation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        dialogLoading = findViewById(R.id.dialog_loading);

        // Retrieve building information
        Intent intent = getIntent();
        fragmentBundle = intent.getBundleExtra(BUNDLE_EXTRAS);
        fragmentParcelable = intent.getParcelableExtra(PARCELABLE_EXTRAS);
        parcelableList = intent.getParcelableArrayListExtra(ARRAY_PARCELABLE_EXTRAS);

        // Setting action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(fragmentBundle.getString(TOOLBAR_TITLE));
        }
    }

    /**
     * Creates a fragment instance using the default constructor, via reflection
     *
     * @param fragmentClass fragment class object that is will be attached to the activity
     */
    protected void initializeFragment(Class<F> fragmentClass) {
        try {

            // Instantiate fragment
            Fragment fragment = fragmentClass.newInstance();

            // Set arguments / information
            if (fragmentBundle != null)
                fragment.setArguments(fragmentBundle);

            if (fragmentParcelable != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(PARCELABLE_EXTRAS, fragmentParcelable);
                fragment.setArguments(bundle);
            }

            if (parcelableList != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ARRAY_PARCELABLE_EXTRAS, parcelableList);
                fragment.setArguments(bundle);
            }

            // Attach fragment
            getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_frame_layout, fragment, fragment.getClass().getSimpleName())
                .commit();

        } catch (InstantiationException | IllegalAccessException exception) {
            logger.logError("initializeFragment",
                    exception.getMessage() + "/" + exception.getCause());
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void hideDialogLoading() {
        dialogLoading.setVisibility(View.GONE);
    }

    public static String getParcelableExtrasTag() {
        return PARCELABLE_EXTRAS;
    }

    public static String getArrayParcelableExtrasTag() {
        return ARRAY_PARCELABLE_EXTRAS;
    }

    public static String getBundleTag() {
        return BUNDLE_EXTRAS;
    }

    public static String getToolbarTitleTag() {
        return TOOLBAR_TITLE;
    }

    /**
     * @return View container for this activity
     */
    public View getRootView() {
        return  ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
    }
}

