package anandniketan.com.bhadajteacher.Fragment;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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

import anandniketan.com.bhadajteacher.Activities.DashBoardActivity;
import anandniketan.com.bhadajteacher.Activities.LoginActivity;
import anandniketan.com.bhadajteacher.Activities.MyBounceInterpolator;
import anandniketan.com.bhadajteacher.Adapter.ImageAdapter;
import anandniketan.com.bhadajteacher.AsyncTasks.DeviceVersionAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.GetStaffProfileAsyncTask;
import anandniketan.com.bhadajteacher.Models.DeviceVersionModel;
import anandniketan.com.bhadajteacher.Models.UserProfileModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.Utility;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

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
    private ArrayList<UserProfileModel> userProfileModels = new ArrayList<>();
    private GetStaffProfileAsyncTask getStaffProfileAsyncTask = null;
    boolean flag = false;
    static int previousHeight;
    int timeDuration = 500;
    private AlertDialog alertDialogAndroid = null;
    private boolean isVersionCodeUpdated = false;
    private int versionCode = 0;
    private DeviceVersionAsyncTask deviceVersionAsyncTask = null;
    DeviceVersionModel deviceVersionModel;

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity().getApplicationContext();
        initViews();
        setListners();
        if (Utility.isNetworkConnected(mContext)) {
            //getVersionUpdateInfo();
            getUserProfile();
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
        header = (LinearLayout) rootView.findViewById(R.id.header);

        profile_image = (CircleImageView) rootView.findViewById(R.id.profile_image);
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

        final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 5);
        myAnim.setInterpolator(interpolator);

        grid_view.startAnimation(myAnim);


    }


    public void setListners() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme))
                        .setCancelable(false)
                        .setTitle("Logout")
                        .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                        .setMessage("Are you sure you want to logout? ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Utility.setPref(mContext, "StaffID", "");
                                Utility.setPref(mContext, "Emp_Code", "");
                                Utility.setPref(mContext, "Emp_Name", "");
                                Utility.setPref(mContext, "DepratmentID", "");
                                Utility.setPref(mContext, "DesignationID", "");
                                Utility.setPref(mContext, "DeviceId", "");
                                Utility.setPref(mContext, "unm", "");
                                Utility.setPref(mContext, "pwd", "");
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                getActivity().startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();
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
                    if (userProfileModels.get(0).getGetclassDetailsArrayList().size() > 0) {
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
                                .setMessage("You are not a Class Teacher.")
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
                    fragment = new MarksFragment();
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
                }
            }
        });
//        menu_linear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (menu_linear.isSelected()) {
//                    menu_linear.setSelected(false);
//                    collapse(header, timeDuration, previousHeight);
//                } else {
//                    menu_linear.setSelected(true);
//                    expand(header, timeDuration - 200, 450);
//                }
//            }
//        });
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
                    params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                    getStaffProfileAsyncTask = new GetStaffProfileAsyncTask(params);
                    userProfileModels = getStaffProfileAsyncTask.execute().get();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (userProfileModels.size() > 0) {
                                fillData();
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
        if (userProfileModels.get(0).getImage().equalsIgnoreCase("")) {
            imageLoader.displayImage(String.valueOf(R.drawable.profile_pic_holder), profile_image);
        } else {
            imageLoader.displayImage(userProfileModels.get(0).getImage(), profile_image);
        }

        student_name_txt.setText(userProfileModels.get(0).getEmp_Name() + "(" + userProfileModels.get(0).getEmp_Code() + ")");
        student_classname_txt.setText(userProfileModels.get(0).getDesignation());
        for (int i = 0; i < userProfileModels.get(0).getGetclassDetailsArrayList().size(); i++) {
            AppConfiguration.rows.add(userProfileModels.get(0).getGetclassDetailsArrayList().get(i));
        }
    }

    public void getVersionUpdateInfo() {
        if (Utility.isNetworkConnected(mContext)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("UserID", Utility.getPref(mContext, "StaffID"));
                        params.put("VersionID", String.valueOf(versionCode));//String.valueOf(versionCode)
                        params.put("UserType", "Staff");
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
                                    new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme))
                                            .setCancelable(false)
                                            .setTitle("Skool360 BhadajTeacher Update")
                                            .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                                            .setMessage("Please update to a new version of the app.")
                                            .setPositiveButton("Upgrade", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.anandniketan.skool360teacher"));
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

}

