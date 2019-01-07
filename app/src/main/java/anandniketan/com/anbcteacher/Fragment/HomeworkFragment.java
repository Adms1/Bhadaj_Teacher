package anandniketan.com.anbcteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.anbcteacher.Activities.LoginActivity;
import anandniketan.com.anbcteacher.Adapter.ExpandableListAdapterHomeWork;
import anandniketan.com.anbcteacher.Adapter.HomeWorkStatusListAdapter;
import anandniketan.com.anbcteacher.AsyncTasks.GetTeacherDailyWorkAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherStudentHomeworkStatusAsynctask;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherStudentHomeworkStatusInsertUpdateAsyncTask;
import anandniketan.com.anbcteacher.Interfacess.onStudentHomeWorkStatus;
import anandniketan.com.anbcteacher.Interfacess.onWorkStatus;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.HomeworkModel;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.HomeworkStatusInsertUpdateModel;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.TeacherStudentHomeworkStatusModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;
import anandniketan.com.anbcteacher.Utility.Utility;


public class HomeworkFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private static TextView fromDate, toDate;
    private static String dateFinal;
    private static boolean isFromDate = false;
    int Year, Month, Day;
    Calendar calendar;
    int mYear, mMonth, mDay;
    ExpandableListView lvExpHomework;
    ExpandableListAdapterHomeWork listAdapter;
    List<String> listDataHeader;
    HashMap<String, ArrayList<HomeworkModel.HomeworkData>> listDataChild;
    TeacherStudentHomeworkStatusModel teacherStudentHomeworkStatusResponse;
    HomeWorkStatusListAdapter homeWorkStatusListAdapter = null;
    String DateStr, TermIdStr, StandardIdStr, ClassIdStr, SubjectIdStr, spiltStr, classNameStr, standardNameStr, subjectNameStr, daybookIdStr, homeworkStr, classworkStr;
    HomeworkStatusInsertUpdateModel homeworkStatusInsertUpdateModelResponse;
    String homeworkIdstr, HWStartDate = "", HWEndDate = "", HWDate = "", HWSubjectName = "", HWStandardName = "", HWClassName = "";
    String homeworkdetailidstr = "";
    private View rootView;
    private Button btnMenu, btnFilterHomework, btnBacktest_homework, btnLogout;
    private TextView txtNoRecordshomework;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private int lastExpandedPosition = -1;
    private DatePickerDialog datePickerDialog;
    private GetTeacherDailyWorkAsyncTask getTecherHomeworkAsyncTask = null;
    private ArrayList<HomeworkModel> homeWorkModels = new ArrayList<>();
    private RelativeLayout date_rel;
    private LinearLayout homework_header;
    //use for homeworkstatus
    private AlertDialog alertDialogAndroid = null;
    private Button close_btn;
    private ImageView insert_homework_status_img;
    private LinearLayout header_linear;
    private ListView student_homework_status_list;
    private TextView txtNoRecordshomeworkstatus, standard_txt;
    //use for fillstudentlist listview
    private TeacherStudentHomeworkStatusAsynctask teacherStudentHomeworkStatusAsynctask = null;
    //use for inserthomeworkstatus
    private TeacherStudentHomeworkStatusInsertUpdateAsyncTask teacherStudentHomeworkStatusInsertUpdateAsyncTask = null;

    public HomeworkFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_homework, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        AppConfiguration.position = 6;
        AppConfiguration.firsttimeback = true;

        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        fromDate = (TextView) rootView.findViewById(R.id.fromDate);
        toDate = (TextView) rootView.findViewById(R.id.toDate);
        btnFilterHomework = (Button) rootView.findViewById(R.id.btnFilterHomework);
        txtNoRecordshomework = (TextView) rootView.findViewById(R.id.txtNoRecordshomework);
        btnBacktest_homework = (Button) rootView.findViewById(R.id.btnBacktest_homework);
        lvExpHomework = (ExpandableListView) rootView.findViewById(R.id.lvExpHomework);
        date_rel = (RelativeLayout) rootView.findViewById(R.id.date_rel);
        homework_header = (LinearLayout) rootView.findViewById(R.id.homework_header);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Bundle args = getArguments();
        if (args != null) {
            HWStartDate = getArguments().getString("HWStartDate");
            HWEndDate = getArguments().getString("HWEndDate");
            HWDate = getArguments().getString("SelectDate");
            HWSubjectName = getArguments().getString("HWSubjectName");
            HWStandardName = getArguments().getString("HWStandardName");
            HWClassName = getArguments().getString("HWClassName");
        }
        if (HWStartDate.equalsIgnoreCase("") && HWEndDate.equalsIgnoreCase("")) {
            fromDate.setText(Utility.getTodaysDate());
            toDate.setText(Utility.getTodaysDate());
        } else {
            fromDate.setText(HWStartDate);
            toDate.setText(HWEndDate);
        }

        //load today's data first

        getHomeworkData(fromDate.getText().toString(), toDate.getText().toString());
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
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = true;
                datePickerDialog = DatePickerDialog.newInstance(HomeworkFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFromDate = false;
                datePickerDialog = DatePickerDialog.newInstance(HomeworkFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        btnFilterHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HWDate = "";
                if (!fromDate.getText().toString().equalsIgnoreCase("")) {
                    if (!toDate.getText().toString().equalsIgnoreCase("")) {
                        if (Utility.CheckDates(fromDate.getText().toString(), toDate.getText().toString()) == true) {
                            getHomeworkData(fromDate.getText().toString(), toDate.getText().toString());
                        } else {
                            Utility.pong(mContext, "Please select proper date.");
                        }
                    } else {
                        Utility.pong(mContext, "You need to select a to date");
                    }
                } else {
                    Utility.pong(mContext, "You need to select a from date");
                }
            }
        });

        btnBacktest_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpHomework.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpHomework.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getHomeworkData(final String fromDate, final String toDate) {
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
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));//
                        params.put("FromDT", fromDate);
                        params.put("ToDT", toDate);
                        homeWorkModels.clear();
                        getTecherHomeworkAsyncTask = new GetTeacherDailyWorkAsyncTask(params);
                        homeWorkModels = getTecherHomeworkAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (homeWorkModels.size() > 0) {
                                    txtNoRecordshomework.setVisibility(View.GONE);
                                    date_rel.setVisibility(View.VISIBLE);
                                    homework_header.setVisibility(View.VISIBLE);
                                    lvExpHomework.setVisibility(View.VISIBLE);
                                    progressDialog.dismiss();
                                    prepaareList();
                                    listAdapter = new ExpandableListAdapterHomeWork(getActivity(), listDataHeader,listDataChild,new onWorkStatus() {
                                        @Override
                                        public void onWorkStatus() {
                                            DateStr = listAdapter.getDate().toString();
                                            ArrayList<String> arrayList = listAdapter.getdaybookId();

                                            for (int i = 0; i < arrayList.size(); i++) {
                                                spiltStr = arrayList.get(i);
                                            }
                                            String[] splitValue = spiltStr.split("\\|");

                                            daybookIdStr = splitValue[0];
                                            standardNameStr = splitValue[1];
                                            classNameStr = splitValue[2];
                                            subjectNameStr = splitValue[3];
                                            classworkStr = splitValue[4];
                                            homeworkStr = splitValue[5];
                                            AppConfiguration.position = 11;
                                            AppConfiguration.firsttimeback = true;
                                            Fragment fragment = new AddDailyWorkFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("daybookIdStr",daybookIdStr);
                                            bundle.putString("homework",homeworkStr);
                                            bundle.putString("classwork",classworkStr);
                                            bundle.putString("HWClassName",classNameStr);
                                            bundle.putString("HWStandardName",standardNameStr);
                                            bundle.putString("HWSubjectName",subjectNameStr);
                                            bundle.putString("HWDate",DateStr);
                                            bundle.putString("HWStartDate",fromDate);
                                            bundle.putString("HWEndDate",toDate);
                                            fragment.setArguments(bundle);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            fragmentManager.beginTransaction()
                                                    .setCustomAnimations(0, 0)
                                                    .replace(R.id.frame_container, fragment).commit();
                                        }
                                    }, new onStudentHomeWorkStatus() {
                                        @Override
                                        public void getStudentHomeWorkStatus() {
                                            //Dialog();
                                            DateStr = listAdapter.getDate().toString();
                                            ArrayList<String> arrayList = listAdapter.getAllId();

                                            for (int i = 0; i < arrayList.size(); i++) {
                                                spiltStr = arrayList.get(i);
                                            }
                                            String[] splitValue = spiltStr.split("\\|");

                                            StandardIdStr = splitValue[0];
                                            ClassIdStr = splitValue[1];
                                            SubjectIdStr = splitValue[2];
                                            TermIdStr = splitValue[3];
                                            standardNameStr = splitValue[4];
                                            classNameStr = splitValue[5];
                                            subjectNameStr = splitValue[6];

                                            Log.d("value", TermIdStr + "" + StandardIdStr + "" + ClassIdStr + "" + SubjectIdStr + "" + classNameStr + "" + standardNameStr + "" + subjectNameStr);
                                            AppConfiguration.position = 11;
                                            AppConfiguration.firsttimeback = true;
                                            Fragment fragment = new DailyWorkStatusFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("HWTermId", TermIdStr);
                                            bundle.putString("HWStandardId", StandardIdStr);
                                            bundle.putString("HWClassId", ClassIdStr);
                                            bundle.putString("HWSubjectId", SubjectIdStr);
                                            bundle.putString("HWClassName", classNameStr);
                                            bundle.putString("HWStandardName", standardNameStr);
                                            bundle.putString("HWSubjectName", subjectNameStr);
                                            bundle.putString("HWDate", DateStr);
                                            bundle.putString("HWStartDate", fromDate);
                                            bundle.putString("HWEndDate", toDate);
                                            fragment.setArguments(bundle);
                                            FragmentManager fragmentManager = getFragmentManager();
                                            fragmentManager.beginTransaction()
                                                    .setCustomAnimations(0, 0)
                                                    .replace(R.id.frame_container, fragment).commit();
                                        }
                                    });
                                    lvExpHomework.setAdapter(listAdapter);
                                    if (!HWDate.equalsIgnoreCase("")) {
                                        for (int i = 0; i < homeWorkModels.get(0).getGethomeworkdata().size(); i++) {
                                            if (homeWorkModels.get(0).getGethomeworkdata().get(i).getDate().equalsIgnoreCase(HWDate) &&
                                                    homeWorkModels.get(0).getGethomeworkdata().get(i).getSubject().equalsIgnoreCase(HWSubjectName) &&
                                                    homeWorkModels.get(0).getGethomeworkdata().get(i).getStandard().equalsIgnoreCase(HWStandardName) &&
                                                    homeWorkModels.get(0).getGethomeworkdata().get(i).getClassName().equalsIgnoreCase(HWClassName)) {
                                                lvExpHomework.expandGroup(i);
                                                lvExpHomework.smoothScrollToPosition(i);
                                            }
                                        }
                                    }
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordshomework.setVisibility(View.VISIBLE);
                                    homework_header.setVisibility(View.GONE);
                                    lvExpHomework.setVisibility(View.GONE);
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

    public void prepaareList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<HomeworkModel.HomeworkData>>();

        for (int j = 0; j < homeWorkModels.get(0).getGethomeworkdata().size(); j++) {
            listDataHeader.add(homeWorkModels.get(0).getGethomeworkdata().get(j).getDate() + "|"
                    + homeWorkModels.get(0).getGethomeworkdata().get(j).getStandard() + "|"
                    + homeWorkModels.get(0).getGethomeworkdata().get(j).getClassName() + "|"
                    + homeWorkModels.get(0).getGethomeworkdata().get(j).getSubject());

            ArrayList<HomeworkModel.HomeworkData> rows = new ArrayList<HomeworkModel.HomeworkData>();

            rows.add(homeWorkModels.get(0).getGethomeworkdata().get(j));

            listDataChild.put(listDataHeader.get(j), rows);
        }


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear + 1, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        String d, m, y;
        d = Integer.toString(day);
        m = Integer.toString(month);
        y = Integer.toString(year);
        if (day < 10) {
            d = "0" + d;
        }
        if (month < 10) {
            m = "0" + m;
        }

        dateFinal = d + "/" + m + "/" + y;
        if (isFromDate) {
            fromDate.setText(dateFinal);
        } else {
            toDate.setText(dateFinal);
        }
    }

    public void Dialog() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.list_item_homework_status, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        alertDialogAndroid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.show();

        close_btn = (Button) layout.findViewById(R.id.close_btn);
        insert_homework_status_img = (ImageView) layout.findViewById(R.id.insert_homework_status_img);
        header_linear = (LinearLayout) layout.findViewById(R.id.header_linear);
        student_homework_status_list = (ListView) layout.findViewById(R.id.student_homework_status_list);
        txtNoRecordshomeworkstatus = (TextView) layout.findViewById(R.id.txtNoRecordshomeworkstatus);
        standard_txt = (TextView) layout.findViewById(R.id.standard_txt);
        getStudentHomeworkStatus();
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });
        insert_homework_status_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInserthomeworkstatus();
            }
        });
    }

    public void getStudentHomeworkStatus() {

        DateStr = listAdapter.getDate().toString();
        ArrayList<String> arrayList = listAdapter.getAllId();

        for (int i = 0; i < arrayList.size(); i++) {
            spiltStr = arrayList.get(i);
        }
        String[] splitValue = spiltStr.split("\\|");

        StandardIdStr = splitValue[0];
        ClassIdStr = splitValue[1];
        SubjectIdStr = splitValue[2];
        TermIdStr = splitValue[3];
        standardNameStr = splitValue[4];
        classNameStr = splitValue[5];

        Log.d("value", TermIdStr + "" + StandardIdStr + "" + ClassIdStr + "" + SubjectIdStr + "" + classNameStr + "" + standardNameStr);
        standard_txt.setText("Standard : " + standardNameStr + " - " + classNameStr);

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
                        params.put("TermID", TermIdStr);
                        params.put("Date", DateStr);
                        params.put("StandardID", StandardIdStr);//StandardIdStr
                        params.put("ClassID", ClassIdStr);
                        params.put("SubjectID", SubjectIdStr);
                        params.put("TeacherID",Utility.getPref(getActivity(),"StaffID"));
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
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("HomeWorkID", homeworkIdstr);
                        params.put("HomeWorkDetailID", homeworkdetailidstr);
                        params.put("StandardID", StandardIdStr);
                        params.put("ClassID", ClassIdStr);
                        params.put("SubjectID", SubjectIdStr);
                        params.put("Date", DateStr);

                        teacherStudentHomeworkStatusInsertUpdateAsyncTask = new TeacherStudentHomeworkStatusInsertUpdateAsyncTask(params);
                        homeworkStatusInsertUpdateModelResponse = teacherStudentHomeworkStatusInsertUpdateAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (homeworkStatusInsertUpdateModelResponse.getFinalArray().size() >= 0) {
                                    Utility.ping(mContext, "Save Succesfully");
                                    alertDialogAndroid.dismiss();
//                                    getHomeworkData(fromDate.getText().toString(), toDate.getText().toString());
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
