package anandniketan.com.anbcteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import anandniketan.com.anbcteacher.Activities.LoginActivity;
import anandniketan.com.anbcteacher.AsyncTasks.InsertHWCWAsyncTask;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.HomeworkStatusInsertUpdateModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;


public class AddDailyWorkFragment extends Fragment {
    EditText classwork_add_edt, homework_add_edt;
    TextView standard_txt, subject_txt;
    String daybookIdStr, homeworkStr = "", classworkStr = "", HWClassName, HWStandardName, HWDate, HWStartDate, HWEndDate, HWSubjectName;
    InsertHWCWAsyncTask insertHWCWAsyncTask = null;
    HomeworkStatusInsertUpdateModel insertHWCWResponse;
    private View rootView;
    private Button btnBacktest_homework, btnLogout, btnsubmitHWCW;
    private Context mContext;
    private ProgressDialog progressDialog = null;

    public AddDailyWorkFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_daily_work, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnLogout = rootView.findViewById(R.id.btnLogout);
        btnBacktest_homework = rootView.findViewById(R.id.btnBacktest_homework);
        btnsubmitHWCW = rootView.findViewById(R.id.btnsubmitHWCW);
        classwork_add_edt = rootView.findViewById(R.id.classwork_add_edt);
        homework_add_edt = rootView.findViewById(R.id.homework_add_edt);
        standard_txt = rootView.findViewById(R.id.standard_txt);
        subject_txt = rootView.findViewById(R.id.subject_txt);
        daybookIdStr = getArguments().getString("daybookIdStr");
        HWClassName = getArguments().getString("HWClassName");
        HWStandardName = getArguments().getString("HWStandardName");
        HWDate = getArguments().getString("HWDate");
        HWStartDate = getArguments().getString("HWStartDate");
        HWEndDate = getArguments().getString("HWEndDate");
        HWSubjectName = getArguments().getString("HWSubjectName");

        standard_txt.setText(HWStandardName + " - " + HWClassName);
        subject_txt.setText(HWSubjectName);
        Bundle args = getArguments();
        if (args != null) {
            if (getArguments().getString("homework") != null) {
                homeworkStr = getArguments().getString("homework");
                if (!homeworkStr.equalsIgnoreCase("1")) {
                    homework_add_edt.setText(homeworkStr);
                }
            }
            if (getArguments().getString("classwork") != null) {
                classworkStr = getArguments().getString("classwork");
                if (!classworkStr.equalsIgnoreCase("1")) {
                    classwork_add_edt.setText(classworkStr);
                }
            }
        }
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
        btnBacktest_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeworkFragment();
                Bundle arg = new Bundle();
                arg.putString("HWStartDate", HWStartDate);
                arg.putString("HWEndDate", HWEndDate);
                arg.putString("SelectDate", HWDate);
                arg.putString("HWStandardName", HWStandardName);
                arg.putString("HWClassName", HWClassName);
                arg.putString("HWSubjectName", HWSubjectName);
                fragment.setArguments(arg);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();

            }
        });
        btnsubmitHWCW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeworkStr = homework_add_edt.getText().toString();
                classworkStr = classwork_add_edt.getText().toString();
                if (!classworkStr.equalsIgnoreCase("")) {
                    if (!homeworkStr.equalsIgnoreCase("")) {

                        if (!classworkStr.contains("\\|")) {
                            if (!homeworkStr.contains("\\|")) {
                                getInsertdailywork();
                            } else {
                                homework_add_edt.setError("Character | not allowed in homework");
                            }
                        } else {
                            classwork_add_edt.setError("Character | not allowed in classwork");
                        }
                    } else {
                        homework_add_edt.setError("Please enter homework");
                    }

                } else {
                    classwork_add_edt.setError("Please enter classwork");
                }
            }
        });
    }

    public void getInsertdailywork() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (Utility.isNetworkConnected(mContext)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("DayBookId", daybookIdStr);
                        params.put("HW", homeworkStr);
                        params.put("CW", classworkStr);
                        insertHWCWAsyncTask = new InsertHWCWAsyncTask(params);
                        insertHWCWResponse = insertHWCWAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (insertHWCWResponse.getSuccess().equalsIgnoreCase("True")) {
                                    Utility.ping(mContext, "Save Succesfully");
                                    Fragment fragment = new HomeworkFragment();
                                    Bundle arg = new Bundle();
                                    arg.putString("HWStartDate", HWStartDate);
                                    arg.putString("HWEndDate", HWEndDate);
                                    arg.putString("SelectDate", HWDate);
                                    arg.putString("HWStandardName", HWStandardName);
                                    arg.putString("HWClassName", HWClassName);
                                    arg.putString("HWSubjectName", HWSubjectName);
                                    fragment.setArguments(arg);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .setCustomAnimations(0, 0)
                                            .replace(R.id.frame_container, fragment).commit();
                                    progressDialog.dismiss();
                                } else {
                                    progressDialog.dismiss();
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
