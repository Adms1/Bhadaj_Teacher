package anandniketan.com.anbcteacher.Fragment;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.anbcteacher.Activities.LoginActivity;
import anandniketan.com.anbcteacher.Activities.MyBounceInterpolator;
import anandniketan.com.anbcteacher.Adapter.ImageAdapter;
import anandniketan.com.anbcteacher.Adapter.UserBirthdayListAdapter;
import anandniketan.com.anbcteacher.AsyncTasks.DeleteDeviceDetailTsk;
import anandniketan.com.anbcteacher.AsyncTasks.DeviceVersionAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetStaffProfileAsyncTask;
import anandniketan.com.anbcteacher.Models.BirthDayDetailModel;
import anandniketan.com.anbcteacher.Models.ClassDetailModel;
import anandniketan.com.anbcteacher.Models.DeviceVersionModel;
import anandniketan.com.anbcteacher.Models.UserProfileModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.NotificationBadge;
import anandniketan.com.anbcteacher.Utility.Utility;
import de.hdodenhof.circleimageview.CircleImageView;

import static anandniketan.com.anbcteacher.Utility.Utility.getPref;

public class HomeFragment extends Fragment implements UserBirthdayListAdapter.OnNotificationCount {

    static int previousHeight;
    boolean flag = false;
    int timeDuration = 500;
    DeviceVersionModel deviceVersionModel;
    UserProfileModel userProfileModel;
    private List<BirthDayDetailModel> mBirthDayData = new ArrayList<BirthDayDetailModel>();
    private View rootView;
    private Button btnMenu, btn_notification, menu_linear, btnLogout;
    private GridView grid_view;
    private ImageView logo;
    private LinearLayout header;
    private Context mContext;
    private Fragment fragment = null;
    private FragmentManager fragmentManager = null;
    //    Change Megha 04-09-2017
    private CircleImageView profile_image;
    private ImageLoader imageLoader;
    private TextView student_name_txt, student_classname_txt;
    private ProgressDialog progressDialog;
    private GetStaffProfileAsyncTask getStaffProfileAsyncTask = null;
    private AlertDialog alertDialogAndroid = null;
    private boolean isVersionCodeUpdated = false;
    private int versionCode = 0;
    private DeviceVersionAsyncTask deviceVersionAsyncTask = null;
    private String notificationMsg;
    private RecyclerView mRvNotificationList;
    private TextView mTvNotificationEmptyMsg;
    private UserBirthdayListAdapter userBirthdayListAdapter;
    private NotificationBadge badge;
    private UserBirthdayListAdapter.OnNotificationCount onNotificationCountRef;
    private String notification_type;
    private boolean isNotificationCollapsed = false;
    private View mHeaderView;


    public HomeFragment() {
    }

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        previousHeight = prevHeight;

        Log.d("previousHeight", "" + previousHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity().getApplicationContext();
        initViews();
        setListners();

        onNotificationCountRef = (UserBirthdayListAdapter.OnNotificationCount)this;
        if (Utility.isNetworkConnected(mContext)) {
            getVersionUpdateInfo();
           // getUserProfile();
        } else {
            Utility.ping(mContext, "Network not available");

        }
        return rootView;
    }

    public void initViews() {
        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        menu_linear = (Button) rootView.findViewById(R.id.menu_linear);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        grid_view = (GridView) rootView.findViewById(R.id.grid_view);
        grid_view.setAdapter(new ImageAdapter(mContext));
        student_name_txt = (TextView) rootView.findViewById(R.id.student_name_txt);
        student_classname_txt = (TextView) rootView.findViewById(R.id.student_classname_txt);
        header = (LinearLayout) rootView.findViewById(R.id.LL_header);
        mRvNotificationList = (RecyclerView)rootView.findViewById(R.id.rv_notificationlist);
        mTvNotificationEmptyMsg = (TextView)rootView.findViewById(R.id.tv_notification_empty_view);
        btn_notification = (Button)rootView.findViewById(R.id.btn_notification);
        badge = (NotificationBadge)rootView.findViewById(R.id.notification_badge);
        profile_image = (CircleImageView) rootView.findViewById(R.id.profile_image);
        mHeaderView = (View)rootView.findViewById(R.id.view_transparent);

        imageLoader = ImageLoader.getInstance();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .threadPriority(Thread.MAX_PRIORITY)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
                .build();
        imageLoader.init(config.createDefault(mContext));

        final Animation myAnim = AnimationUtils.loadAnimation(mContext,R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 5);
        myAnim.setInterpolator(interpolator);
        grid_view.startAnimation(myAnim);


    }

    public void setListners() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.doLogout(getActivity());
            }
        });
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    fragment = new TodayscheduleFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 1;
                } else if (position == 1) {
                    fragment = new SubjectFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 2;
                } else if (position == 2) {
                    fragment = new TimeTableFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 3;
                } else if (position == 3) {
                    if (AppConfiguration.rows.size() > 0) {
                        fragment = new AttendanceFragment();
                        fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .setCustomAnimations(0, 0)
                                .replace(R.id.frame_container, fragment).commit();
                        AppConfiguration.firsttimeback = true;
                        AppConfiguration.position = 4;
                    } else {
                        new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme))
                                .setCancelable(false)
                                .setMessage(getResources().getString(R.string.teacher_permission))
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .show();
                    }
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 4;
                } /*else if (position == 4) {
                    fragment = new WorkPlanFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 5;
                }*/ else if (position == 4) {
                    fragment = new HomeworkFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 6;
                } else if (position == 5) {
                    fragment = new TestMainFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 7;
                } else if (position == 6) {
                    fragment = new MarksMainFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 8;
                } else if (position == 7) {
                    fragment = new ShowLeaveFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 9;
                }else if (position == 8) {
                    fragment = new SettingFragment();
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(0, 0)
                            .replace(R.id.frame_container, fragment).commit();
                    AppConfiguration.firsttimeback = true;
                    AppConfiguration.position = 10;
                }
            }
        });

        mHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isNotificationCollapsed) {
                    menu_linear.setSelected(false);
                    collapse(header, timeDuration, previousHeight);

                    isNotificationCollapsed = true;
                } else{
                    menu_linear.setSelected(true);
                    expand(header, timeDuration - 200, 450);
                    isNotificationCollapsed = false;
                }
            }
        });

        menu_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mHeaderView.performClick();
            }
        });

//        btn_notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(!isNotificationCollapsed) {
//                    btn_notification.setSelected(false);
//                    collapse(header, timeDuration, previousHeight);
//                    isNotificationCollapsed = true;
//                } else{
//                    btn_notification.setSelected(true);
//                    expand(header, timeDuration - 200, 450);
//                    isNotificationCollapsed = false;
//                }
//            }
//        });
    }

    public void getUserProfile() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("StaffID", getPref(mContext, "StaffID"));
                    getStaffProfileAsyncTask = new GetStaffProfileAsyncTask(params);
                    userProfileModel = getStaffProfileAsyncTask.execute().get();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if(userProfileModel.getFinalArray() != null) {
                                if (userProfileModel.getFinalArray().size() > 0) {
                                    fillData();

                                    String Bday = userProfileModel.getFinalArray().get(0).getDob();

                                    if(Bday != null){
                                        if(!TextUtils.isEmpty(Bday)){
                                            Utility.showUserBirthdayWish(getActivity(),Bday);
                                        }
                                    }
                                    mBirthDayData = userProfileModel.getBirthdayDetail();

                                    if (mBirthDayData != null) {
                                        if (mBirthDayData.size() > 0) {
                                            userBirthdayListAdapter = new UserBirthdayListAdapter(getActivity(),mBirthDayData,onNotificationCountRef);
                                            mRvNotificationList.setLayoutManager(new LinearLayoutManager(getActivity()));
                                            mRvNotificationList.setHasFixedSize(true);
                                            mRvNotificationList.setAdapter(userBirthdayListAdapter);
                                            userBirthdayListAdapter.clearNotificationCount();
                                            mRvNotificationList.setVisibility(View.VISIBLE);
                                            mTvNotificationEmptyMsg.setVisibility(View.GONE);
                                            isNotificationCollapsed = true;
                                            mHeaderView.setEnabled(true);
                                            mHeaderView.setClickable(true);
                                            menu_linear.setEnabled(true);
                                            int count= 0;
                                            for(int pos= 0;pos<mBirthDayData.size();pos++){
                                                if(mBirthDayData.get(pos).getFlag().equalsIgnoreCase("False")){
                                                    ++count;
                                                }
                                            }
                                            badge.setVisibility(View.VISIBLE);
                                            badge.setNumber(count);


                                        } else {
                                            mRvNotificationList.setVisibility(View.GONE);
                                            mTvNotificationEmptyMsg.setVisibility(View.VISIBLE);
                                            isNotificationCollapsed = false;
                                            mHeaderView.setEnabled(false);
                                            mHeaderView.setClickable(false);
                                            menu_linear.setEnabled(false);
                                            badge.setVisibility(View.GONE);
                                        }
                                    } else {
                                        mRvNotificationList.setVisibility(View.GONE);
                                        mTvNotificationEmptyMsg.setVisibility(View.VISIBLE);
                                        isNotificationCollapsed = false;
                                        mHeaderView.setEnabled(false);
                                        mHeaderView.setClickable(false);
                                        menu_linear.setEnabled(false);
                                        badge.setVisibility(View.GONE);
                                    }


                                    try {
                                        notification_type = getPref(getActivity(), "notification_type");
                                        notificationMsg = getPref(getActivity(), "Push_Notification_message");


                                        if (notification_type != null) {
                                            if (!TextUtils.isEmpty(notification_type)) {
                                                if (notification_type.equalsIgnoreCase("StaffLeave")) {
                                                    if (notificationMsg != null) {
                                                        if (!TextUtils.isEmpty(notificationMsg)) {
                                                            fragment = new ShowLeaveFragment();
                                                            fragmentManager = getFragmentManager();
                                                            fragmentManager.beginTransaction().setCustomAnimations(0, 0).replace(R.id.frame_container,fragment).commit();
                                                            AppConfiguration.firsttimeback = true;
                                                            AppConfiguration.position = 9;
//                                                            Utility.setPref(getActivity(), "Push_Notification_message", null);
//                                                            Utility.setPref(getActivity(), "notification_type", null);
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }





    public void fillData() {
        AppConfiguration.rows.clear();
//        if (userProfileModel.getFinalArray().get(0).get().equalsIgnoreCase("")) {
        imageLoader.displayImage(String.valueOf(R.drawable.profile_pic_holder), profile_image);
//        } else {
//            imageLoader.displayImage(userProfileModels.get(0).getImage(), profile_image);
//        }
        for (int i = 0; i < userProfileModel.getFinalArray().size(); i++) {
            student_name_txt.setText(userProfileModel.getFinalArray().get(i).getEmpName() + "(" + userProfileModel.getFinalArray().get(i).getEmpCode() + ")");
            student_classname_txt.setText(userProfileModel.getFinalArray().get(i).getDesignation());

        }
        for (int j = 0; j < userProfileModel.getClassDetail().size(); j++) {
            AppConfiguration.rows.add((ClassDetailModel) userProfileModel.getClassDetail().get(j));
        }
    }



    public void getVersionUpdateInfo() {
        if (Utility.isNetworkConnected(mContext)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("UserID",getPref(mContext, "StaffID"));
                        params.put("VersionID",String.valueOf(versionCode));//String.valueOf(versionCode)
                        params.put("UserType","Staff");
                        deviceVersionAsyncTask = new DeviceVersionAsyncTask(params);
                        deviceVersionModel = deviceVersionAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (deviceVersionModel.getSuccess().equalsIgnoreCase("True")) {
                                    isVersionCodeUpdated = true;
                                    Log.d("hellotrue", "" + isVersionCodeUpdated);
                                    getUserProfile();
                                } else {
                                    isVersionCodeUpdated = false;
                                    Log.d("hellofalse", "" + isVersionCodeUpdated);
                                    new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(),R.style.AppTheme))
                                            .setCancelable(false)
                                            .setTitle(getString(R.string.app_name)+" Update")
                                            .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                                            .setMessage("Please update to a new version of the app.")
                                            .setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getActivity().getPackageName()));
                                                    getActivity().startActivity(i);

                                                }
                                            })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // do nothing
                                                    Utility.pong(mContext, "You wont be able to use other funcationality without updating to a newer version");
                                                    getActivity().finish();
                                                }
                                            })
                                            .setIcon(R.drawable.ic_launcher)
                                            .show();

                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void checkpermission() {

    }

    @Override
    public void onResume(){
        super.onResume();
//        try {
//
//            if (notificationFrom != null && notificationMsg != null && notificationFlag != null) {
//                fragment = new ShowLeaveFragment();
//                fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(0, 0)
//                        .replace(R.id.frame_container, fragment).commit();
//                AppConfiguration.firsttimeback = true;
//                AppConfiguration.position = 9;
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
    }


    @Override
    public void getNotificationCount(int count) {
        try {
            if(count > 0){
                if(badge != null){
                    if(badge.getVisibility() == View.GONE){
                        badge.setVisibility(View.VISIBLE);
                        badge.setNumber(count);
                    }else{
                        badge.setNumber(count);
                    }
                }
            }
            else if(count <= 0){
                if(badge != null){
                    badge.setVisibility(View.GONE);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}

