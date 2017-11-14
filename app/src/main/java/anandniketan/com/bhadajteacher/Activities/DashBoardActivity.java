package anandniketan.com.bhadajteacher.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;


import java.util.ArrayList;

import anandniketan.com.bhadajteacher.Adapter.MenuoptionItemAdapter;
import anandniketan.com.bhadajteacher.Fragment.AttendanceFragment;
import anandniketan.com.bhadajteacher.Fragment.HomeFragment;
import anandniketan.com.bhadajteacher.Fragment.HomeworkFragment;
import anandniketan.com.bhadajteacher.Fragment.SubjectFragment;
import anandniketan.com.bhadajteacher.Fragment.TimeTableFragment;
import anandniketan.com.bhadajteacher.Models.MenuoptionItemModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;

public class DashBoardActivity extends FragmentActivity {
    static DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    Context mContext;
    ActionBarDrawerToggle mDrawerToggle;
    static RelativeLayout leftRl;
    private ArrayList<MenuoptionItemModel> navDrawerItems_main;
    private MenuoptionItemAdapter adapter_menu_item;
    String MenuName[];
    String token;
    int dispPOS = 0;
    SharedPreferences SP;
    public static String filename = "Valustoringfile";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private String putData = "0";

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
//        Log.d("Dashboard : fromNotification", fromWhere);
//        onNewIntent(getIntent());
//        if (getIntent().getStringExtra("message") != null) {
//            putData = getIntent().getStringExtra("message").toString();
//            Log.d("notificationData",putData);
//        }
//        if (getIntent().getStringExtra("fromNotification") != null) {
//            String key = getIntent().getStringExtra("fromNotification").toString();
//            Log.d("key", key);
//            if (key.equalsIgnoreCase("HW")) {
//                displayView(3);
//            } else if (key.equalsIgnoreCase("CW")) {
//                displayView(4);
//            } else if (key.equalsIgnoreCase("Attendance")) {
//                displayView(2);
//            } else if (key.equalsIgnoreCase("Announcement")) {
//                Intent is = new Intent(DashBoardActivity.this, NotificationFragment.class);
//                is.putExtra("message", putData);
//                startActivity(is);
//            }
//        } else {
//            displayView(0);
//        }
//        System.out.println("Dashboard Message :" + getIntent().getStringExtra("message"));
//        System.out.println("Dashboard Extra : " + getIntent().getStringExtra("fromNotification"));

    }

    private void Initialize() {
        // TODO Auto-generated method stub
        MenuName = getResources().getStringArray(R.array.menuoption1);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftRl = (RelativeLayout) findViewById(R.id.whatYouWantInLeftDrawer);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems_main = new ArrayList<MenuoptionItemModel>();
        adapter_menu_item = new MenuoptionItemAdapter(DashBoardActivity.this, navDrawerItems_main);
        for (int i = 0; i < MenuName.length; i++) {
            navDrawerItems_main.add(new MenuoptionItemModel(MenuName[i]));
        }
        mDrawerList.setAdapter(adapter_menu_item);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
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
                break;
            case 1:
                fragment = new AttendanceFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 2:
                fragment = new SubjectFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 3:
                fragment = new TimeTableFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 5:
                fragment = new HomeworkFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
            case 13:
                Utility.setPref(mContext, "unm", "");
                Utility.setPref(mContext, "pwd", "");
                Utility.setPref(mContext, "StaffID", "");
                Utility.setPref(mContext, "Emp_Code", "");
                Utility.setPref(mContext, "Emp_Name", "");
                Utility.setPref(mContext, "DepratmentID", "");
                Utility.setPref(mContext, "DesignationID", "");
                Utility.setPref(mContext, "DeviceId", "");
                Intent intentLogin = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                finish();
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

    public static void onLeft() {
        // TODO Auto-generated method stub
        mDrawerList.setSelectionAfterHeaderView();
        mDrawerLayout.openDrawer(leftRl);
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
        displayView(0);
    }
}
