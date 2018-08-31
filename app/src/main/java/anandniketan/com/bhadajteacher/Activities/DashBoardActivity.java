package anandniketan.com.bhadajteacher.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;


import anandniketan.com.bhadajteacher.Fragment.HomeFragment;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.Utility;

public class DashBoardActivity extends FragmentActivity {
    static DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    Context mContext;
    ActionBarDrawerToggle mDrawerToggle;
    static RelativeLayout leftRl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mContext = this;
        Initialize();
        displayView(0);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_launcher, // nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            @SuppressLint("NewApi")
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            @SuppressLint("NewApi")
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        AppConfiguration.firsttimeback = true;

    }

    private void Initialize() {
        // TODO Auto-generated method stub

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftRl = (RelativeLayout) findViewById(R.id.whatYouWantInLeftDrawer);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    Fragment fragment = null;
    int myid;
    boolean first_time_trans = true;

    public void displayView(int position) {
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 0;
                break;

        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (fragment instanceof HomeFragment) {
                if (first_time_trans) {
                    first_time_trans = false;
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();

                } else {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                }
            } else {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawers();
        } else {
            // error in creating fragment
            Log.e("Dashboard", "Error in creating fragment");
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (AppConfiguration.firsttimeback) {
            if (AppConfiguration.position != 0) {
                displayView(0);
            }
            AppConfiguration.firsttimeback = false;
            Utility.ping(mContext, "Press again to exit");
        } else {
            finish();
            System.exit(0);
        }
    }
}
