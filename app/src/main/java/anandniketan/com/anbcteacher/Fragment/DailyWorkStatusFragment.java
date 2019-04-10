package anandniketan.com.anbcteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Activities.LoginActivity;
import anandniketan.com.anbcteacher.Adapter.HomeWorkStatusListAdapter;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherStudentHomeworkStatusAsynctask;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherStudentHomeworkStatusInsertUpdateAsyncTask;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.HomeworkStatusInsertUpdateModel;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.TeacherStudentHomeworkStatusModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;


public class DailyWorkStatusFragment extends Fragment {
    TeacherStudentHomeworkStatusModel teacherStudentHomeworkStatusResponse;
    HomeWorkStatusListAdapter homeWorkStatusListAdapter = null;
    HomeworkStatusInsertUpdateModel homeworkStatusInsertUpdateModelResponse;
    String homeworkIdstr;
    String homeworkdetailidstr = "";
    String HWTermId, HWStandardId, HWClassId, HWSubjectId, HWClassName, HWStandardName, HWDate, HWStartDate, HWEndDate, HWSubjectName;
    private View rootView;
    private Button btnBacktest_homework, btnLogout;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    //use for homeworkstatus
    private ImageView insert_homework_status_img;
    private LinearLayout header_linear;
    private ListView student_homework_status_list;
    private TextView txtNoRecordshomeworkstatus, standard_txt, subject_txt;
    //use for fillstudentlist listview
    private TeacherStudentHomeworkStatusAsynctask teacherStudentHomeworkStatusAsynctask = null;
    //use for inserthomeworkstatus
    private TeacherStudentHomeworkStatusInsertUpdateAsyncTask teacherStudentHomeworkStatusInsertUpdateAsyncTask = null;

    public DailyWorkStatusFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_daily_work_status, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        HWTermId = getArguments().getString("HWTermId");
        HWStandardId = getArguments().getString("HWStandardId");
        HWClassId = getArguments().getString("HWClassId");
        HWSubjectId = getArguments().getString("HWSubjectId");
        HWClassName = getArguments().getString("HWClassName");
        HWStandardName = getArguments().getString("HWStandardName");
        HWDate = getArguments().getString("HWDate");
        HWStartDate = getArguments().getString("HWStartDate");
        HWEndDate = getArguments().getString("HWEndDate");
        HWSubjectName = getArguments().getString("HWSubjectName");

        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnBacktest_homework = (Button) rootView.findViewById(R.id.btnBacktest_homework);

        insert_homework_status_img = (ImageView) rootView.findViewById(R.id.insert_homework_status_img);
        header_linear = (LinearLayout) rootView.findViewById(R.id.header_linear);
        student_homework_status_list = (ListView) rootView.findViewById(R.id.student_homework_status_list);
        txtNoRecordshomeworkstatus = (TextView) rootView.findViewById(R.id.txtNoRecordshomeworkstatus);
        standard_txt = (TextView) rootView.findViewById(R.id.standard_txt);
        subject_txt = (TextView) rootView.findViewById(R.id.subject_txt);

        standard_txt.setText(HWStandardName + " - " + HWClassName);
        subject_txt.setText(HWSubjectName);
        getStudentHomeworkStatus();
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
        insert_homework_status_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInserthomeworkstatus();
            }
        });
    }

    public void getStudentHomeworkStatus() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("TermID", HWTermId);
                        params.put("Date", HWDate);
                        params.put("StandardID", HWStandardId);//StandardIdStr
                        params.put("ClassID", HWClassId);
                        params.put("SubjectID", HWSubjectId);
                        params.put("TeacherID", Utility.getPref(getActivity(), "StaffID"));

                        teacherStudentHomeworkStatusAsynctask = new TeacherStudentHomeworkStatusAsynctask(params);
                        teacherStudentHomeworkStatusResponse = teacherStudentHomeworkStatusAsynctask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (teacherStudentHomeworkStatusResponse.getSuccess().equalsIgnoreCase("True")) {
                                    if (teacherStudentHomeworkStatusResponse.getFinalArray().size() > 0) {
                                        if (teacherStudentHomeworkStatusResponse.getFinalArray().get(0).getHomeWorkStatus().equalsIgnoreCase("-1")) {
                                            insert_homework_status_img.setImageResource(R.drawable.submit);
                                        } else {
                                            insert_homework_status_img.setImageResource(R.drawable.update_1);
                                        }
                                        header_linear.setVisibility(View.VISIBLE);
                                        student_homework_status_list.setVisibility(View.VISIBLE);
                                        txtNoRecordshomeworkstatus.setVisibility(View.GONE);
                                        insert_homework_status_img.setVisibility(View.VISIBLE);
                                        changestatus();
                                        homeWorkStatusListAdapter = new HomeWorkStatusListAdapter(getActivity(), teacherStudentHomeworkStatusResponse);
                                        student_homework_status_list.setAdapter(homeWorkStatusListAdapter);
                                        student_homework_status_list.deferNotifyDataSetChanged();
                                    } else {
                                        progressDialog.dismiss();
                                        header_linear.setVisibility(View.GONE);
                                        student_homework_status_list.setVisibility(View.GONE);
                                        txtNoRecordshomeworkstatus.setVisibility(View.VISIBLE);
                                        insert_homework_status_img.setVisibility(View.GONE);
                                    }
                                } else {
                                    header_linear.setVisibility(View.GONE);
                                    txtNoRecordshomeworkstatus.setVisibility(View.VISIBLE);
                                    insert_homework_status_img.setVisibility(View.GONE);
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

    public void changestatus() {
        for (int i = 0; i < teacherStudentHomeworkStatusResponse.getFinalArray().size(); i++) {
            if (teacherStudentHomeworkStatusResponse.getFinalArray().get(i).getHomeWorkStatus().equalsIgnoreCase("-1")) {
                teacherStudentHomeworkStatusResponse.getFinalArray().get(i).setHomeWorkStatus("1");
            }
        }
    }

    public void getInserthomeworkstatus() {
        ArrayList<String> newArray = new ArrayList<>();
        boolean isEnable = false;
        String studentString = "";
        for (int j = 0; j < teacherStudentHomeworkStatusResponse.getFinalArray().size(); j++) {
            homeworkIdstr = teacherStudentHomeworkStatusResponse.getFinalArray().get(0).getHomeWorkID();
            if (!isEnable) {
                studentString = String.valueOf(teacherStudentHomeworkStatusResponse.getFinalArray().get(j).getHomeWorkDetailID()) + ","
                        + teacherStudentHomeworkStatusResponse.getFinalArray().get(j).getStudentID() + "," +
                        teacherStudentHomeworkStatusResponse.getFinalArray().get(j).getHomeWorkStatus();
                isEnable = true;
            } else {
                studentString = studentString + "|" + String.valueOf(teacherStudentHomeworkStatusResponse.getFinalArray().get(j).getHomeWorkDetailID()) + ","
                        + teacherStudentHomeworkStatusResponse.getFinalArray().get(j).getStudentID() + "," +
                        teacherStudentHomeworkStatusResponse.getFinalArray().get(j).getHomeWorkStatus();
            }
        }
        newArray.add(studentString);
        homeworkdetailidstr = "";
        for (String s : newArray) {
            homeworkdetailidstr = homeworkdetailidstr + "," + s;
        }
        homeworkdetailidstr = homeworkdetailidstr.substring(1, homeworkdetailidstr.length());

        Log.d("homeworkdetailidstr", "" + homeworkdetailidstr);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (Utility.isNetworkConnected(mContext)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("HomeWorkID", homeworkIdstr);
                        params.put("HomeWorkDetailID", homeworkdetailidstr);
                        params.put("StandardID", HWStandardId);
                        params.put("ClassID", HWClassId);
                        params.put("SubjectID", HWSubjectId);
                        params.put("Date", HWDate);

                        teacherStudentHomeworkStatusInsertUpdateAsyncTask = new TeacherStudentHomeworkStatusInsertUpdateAsyncTask(params);
                        homeworkStatusInsertUpdateModelResponse = teacherStudentHomeworkStatusInsertUpdateAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (homeworkStatusInsertUpdateModelResponse.getFinalArray().size() >= 0) {
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
