package anandniketan.com.anbcteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.anbcteacher.Activities.LoginActivity;
import anandniketan.com.anbcteacher.Adapter.ExpandableListAdapterHomeWork;
import anandniketan.com.anbcteacher.Adapter.HomeWorkStatusListAdapter;
import anandniketan.com.anbcteacher.Adapter.ProfileAdapter;
import anandniketan.com.anbcteacher.AsyncTasks.GetStaffProfileAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetTeacherDailyWorkAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.StaffChangePasswordAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherStudentHomeworkStatusAsynctask;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherStudentHomeworkStatusInsertUpdateAsyncTask;
import anandniketan.com.anbcteacher.Interfacess.onEditTest;
import anandniketan.com.anbcteacher.Interfacess.onInboxRead;
import anandniketan.com.anbcteacher.Interfacess.onStudentHomeWorkStatus;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.HomeworkModel;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.HomeworkStatusInsertUpdateModel;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.TeacherStudentHomeworkStatusModel;
import anandniketan.com.anbcteacher.Models.UserFinalArray;
import anandniketan.com.anbcteacher.Models.UserProfileModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;
import anandniketan.com.anbcteacher.databinding.FragmentSettingBinding;


public class SettingFragment extends Fragment {

    private FragmentSettingBinding settingBinding;
    private UserProfileModel userProfileModel;
    private ProfileAdapter profileAdapter;
    private List<UserFinalArray> personalDetails;
    private List<UserFinalArray> departmentDetails;
    private List<UserFinalArray> passwordDetails;
    private String newPasswordStr,oldPasswordStr;
    private View rootView;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetStaffProfileAsyncTask getStaffProfileAsyncTask = null;
    private StaffChangePasswordAsyncTask staffChangePasswordAsyncTask = null;


    public SettingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        mContext = getActivity();
        rootView = settingBinding.getRoot();
        initViews();
        setListners();
        oldPasswordStr = Utility.getPref(getActivity(),"pwd");
        return rootView;
    }

    public void initViews() {
        getUserProfile();
    }

    public void setListners() {
        settingBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
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
                                getActivity().finish();
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

        settingBinding.btnBacktestSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }

    public void getUserProfile() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        getStaffProfileAsyncTask = new GetStaffProfileAsyncTask(params);
                        userProfileModel = getStaffProfileAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (userProfileModel.getFinalArray().size() > 0) {
                                    personalDetails = new ArrayList<>();
                                    departmentDetails = new ArrayList<>();
                                    passwordDetails = new ArrayList<>();

                                    for (int i = 0; i < userProfileModel.getFinalArray().size(); i++) {
                                        personalDetails.add(userProfileModel.getFinalArray().get(i));
                                        departmentDetails.add(userProfileModel.getFinalArray().get(i));
                                        passwordDetails.add(userProfileModel.getFinalArray().get(i));
                                    }

                                    profileAdapter = new ProfileAdapter(mContext, personalDetails, departmentDetails, passwordDetails, new onEditTest() {
                                        @Override
                                        public void getEditTest() {
                                            newPasswordStr = String.valueOf(profileAdapter.getEditPaswordDetail());
                                            newPasswordStr = newPasswordStr.substring(1,newPasswordStr.length()-1);

                                            if (!newPasswordStr.equalsIgnoreCase("")) {
                                                getUserPassword();
                                            } else {

                                            }
                                        }
                                    });
                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext,OrientationHelper.VERTICAL, false);
                                    settingBinding.profileListRcv.setLayoutManager(mLayoutManager);
                                    settingBinding.profileListRcv.setItemAnimator(new DefaultItemAnimator());
                                    settingBinding.profileListRcv.setAdapter(profileAdapter);

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


    public void getUserPassword() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        params.put("NewPassword", newPasswordStr);
                        staffChangePasswordAsyncTask = new StaffChangePasswordAsyncTask(params);
                        userProfileModel = staffChangePasswordAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (userProfileModel.getSuccess().equalsIgnoreCase("True")) {
                                    Utility.ping(mContext, "Password Change Successfully");
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
}
