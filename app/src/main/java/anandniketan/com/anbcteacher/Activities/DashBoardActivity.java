package anandniketan.com.anbcteacher.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import anandniketan.com.anbcteacher.Fragment.HomeFragment;
import anandniketan.com.anbcteacher.Fragment.HomeworkFragment;
import anandniketan.com.anbcteacher.Fragment.ShowLeaveFragment;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.DialogUtils;
import anandniketan.com.anbcteacher.Utility.FontListParser;
import anandniketan.com.anbcteacher.Utility.Utility;

public class DashBoardActivity extends FragmentActivity {
    static DrawerLayout mDrawerLayout;
    static ListView mDrawerList;
    Context mContext;
    ActionBarDrawerToggle mDrawerToggle;
    static RelativeLayout leftRl;
    private String notificationMsg,notificationType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        mContext = this;
        Initialize();
        displayView(0);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,
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
        try {
            notificationMsg = Utility.getPref(DashBoardActivity.this,"Push_Notification_message");
            notificationType = Utility.getPref(DashBoardActivity.this,"notification_type");
        }catch (Exception ex){
            ex.printStackTrace();
        }

      //  DialogUtils.showGIFDialog(DashBoardActivity.this,"Test User");
//        final List<FontListParser.SystemFont> fonts = FontListParser.safelyGetSystemFonts();
//        String[] items = new String[fonts.size()];
//        for (int i = 0; i < fonts.size(); i++) {
//            items[i] = fonts.get(i).name;
//        }
//
//        new AlertDialog.Builder(this).setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                FontListParser.SystemFont selectedFont = fonts.get(which);
//                // TODO: do something with the font
//                Toast.makeText(getApplicationContext(), selectedFont.path, Toast.LENGTH_LONG).show();
//            }
//        }).show();




        try {


            if (notificationType != null) {
                if (!TextUtils.isEmpty(notificationType)) {
                    if (notificationMsg != null) {
                        if (!TextUtils.isEmpty(notificationMsg)) {
                            if (notificationType.equalsIgnoreCase("Birthday")) {

                                String name = Utility.getPref(DashBoardActivity.this, "notification_name");

                                if (name != null) {
                                    if (!TextUtils.isEmpty(name)) {
                                        String fullname = name.replace("|"," ");
                                        DialogUtils.showGIFDialog(DashBoardActivity.this,fullname);

                                        Utility.setPref(DashBoardActivity.this,"Push_Notification_message","");
                                        Utility.setPref(DashBoardActivity.this,"notification_type","");
                                        Utility.setPref(DashBoardActivity.this,"notification_name","");

                                    }
                                }

                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }






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
    FragmentManager fragmentManager;
    int myid;
    boolean first_time_trans = true;

    public void displayView(int position) {
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                myid = fragment.getId();
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                AppConfiguration.firsttimeback = false;
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
                if (AppConfiguration.position==11){
                    fragment = new HomeworkFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 6;
                }else if (AppConfiguration.position==12){
                    fragment = new ShowLeaveFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 9;
                }else if (AppConfiguration.position== 9){
                    fragment = new HomeFragment();
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = false;
                    AppConfiguration.position = 9;
                }


                else{
                    displayView(0);

                    Utility.ping(mContext, "Press again to exit");
                }
            }
            AppConfiguration.firsttimeback = false;

        } else {

            finish();
            System.exit(0);
        }
    }
}
