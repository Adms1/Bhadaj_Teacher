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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.anbcteacher.Activities.LoginActivity;
import anandniketan.com.anbcteacher.Adapter.ExpandableListAdapterTimeTable;
import anandniketan.com.anbcteacher.AsyncTasks.DeleteTimetableAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetLectureDetailsAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetStandardSectionAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetTeacherGetTimetableAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.InsertTimetableAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.TimeTableSubjectDetailAsyncTask;
import anandniketan.com.anbcteacher.Interfacess.onDeleteLecture;
import anandniketan.com.anbcteacher.Models.TimeTable.DeleteLectureModel;
import anandniketan.com.anbcteacher.Models.TimeTable.GetStandardSectionModel;
import anandniketan.com.anbcteacher.Models.TimeTable.InsertLectureModel;
import anandniketan.com.anbcteacher.Models.TimeTable.TeacherGetTimetableModel;
import anandniketan.com.anbcteacher.Models.TimeTable.GetTimeTableSubjectModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;

public class TimeTableFragment extends Fragment {
    private View rootView;
    private Button btnBackTimeTable, btnLogout;
    private TextView txtNoRecordsTimetable;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetTeacherGetTimetableAsyncTask getTeacherGetTimetableAsyncTask = null;
    private ArrayList<TeacherGetTimetableModel> timetableModels = new ArrayList<>();
    private int lastExpandedPosition = -1;

    ExpandableListAdapterTimeTable listAdapterTimeTable;
    ExpandableListView lvExpTimeTable;
    List<String> listDataHeader;
    HashMap<String, ArrayList<TeacherGetTimetableModel.TimeTable.TimeTableData>> listDataChild;

    //use for adpaterview
    private DeleteTimetableAsyncTask deleteTimetableAsyncTask = null;
    DeleteLectureModel deleteLectureModel;
    String finalLectureIdArray;
    private AlertDialog alertDialogAndroid = null;
    private Button close_btn, add_lecture_btn;
    private Spinner edit_grade_spinner, edit_subject_spinner, edit_Starttime_spinner, edit_Starttime1_spinner, edit_Endtime_spinner, edit_Endtime1_spinner;
    private TextView edit_day_txt, edit_day_lecture_txt,start_time_hour_lecture_txt,end_time_hour_lecture_txt;
    private LinearLayout edit_lecture_section_llListData;
    private CheckBox checkBox;
    HashMap<Integer, String> standardIdMap;
    HashMap<Integer, String> subjectIdMap;
    String Standardid, Subejctid, checknamestr, checkidstr, starttimehour, starttimeminit, endtimehour, endtimeminit, Day, Lecture, selectedStandard, SelectedStandardname;
    private ArrayList<String> classnamearray = new ArrayList<String>();
    private ArrayList<String> classidarray = new ArrayList<String>();
    private int selectedPosition = -1;

    //use for fill dialogView
    private TimeTableSubjectDetailAsyncTask timeTableSubjectDetailAsyncTask = null;
    GetTimeTableSubjectModel timeTableSubjectResponse;
    private GetLectureDetailsAsyncTask getLectureDetailsAsyncTask=null;


    //use for insertLecture
    private InsertTimetableAsyncTask insertTimetableAsyncTask = null;
    InsertLectureModel insertLectureModel;
    String finalclassIdStr="";

    //use for fill standard
    private GetStandardSectionAsyncTask getStandardSectionAsyncTask = null;
    private GetStandardSectionModel getStandardSectionModelResponse;

    public TimeTableFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_time_table, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        txtNoRecordsTimetable = (TextView) rootView.findViewById(R.id.txtNoRecordsTimetable);
        btnBackTimeTable = (Button) rootView.findViewById(R.id.btnBackTimeTable);
        lvExpTimeTable = (ExpandableListView) rootView.findViewById(R.id.lvExpTimeTable);

        getTimeTableData();
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
        btnBackTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpTimeTable.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpTimeTable.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getTimeTableData() {
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
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        getTeacherGetTimetableAsyncTask = new GetTeacherGetTimetableAsyncTask(params);
                        timetableModels = getTeacherGetTimetableAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (timetableModels.size() > 0) {
                                    txtNoRecordsTimetable.setVisibility(View.GONE);
                                    prepaareList();
                                    listAdapterTimeTable = new ExpandableListAdapterTimeTable(getActivity(), listDataHeader, listDataChild, new onDeleteLecture() {
                                        @Override
                                        public void getDeleteLecture() {
                                            ArrayList<String> id = new ArrayList<>();
                                            ArrayList<String> array = listAdapterTimeTable.getTimeTableId();
                                            for (int j = 0; j < array.size(); j++) {
                                                id.add(array.get(j).toString());
                                            }
                                            finalLectureIdArray = String.valueOf(id);
                                            finalLectureIdArray = finalLectureIdArray.substring(1, finalLectureIdArray.length() - 1);
                                            Log.d("finalLectureIdArray", finalLectureIdArray);
                                            if (Utility.isNetworkConnected(mContext)) {
                                                if (!finalLectureIdArray.equalsIgnoreCase("")) {
                                                    new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            try {
                                                                HashMap<String, String> params = new HashMap<String, String>();
                                                                params.put("TimetableID", finalLectureIdArray.trim());

                                                                deleteTimetableAsyncTask = new DeleteTimetableAsyncTask(params);
                                                                deleteLectureModel = deleteTimetableAsyncTask.execute().get();
                                                                getActivity().runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        progressDialog.dismiss();
                                                                        if (deleteLectureModel.getFinalArray().size() >= 0) {
                                                                            Utility.ping(mContext, "Delete Lecture.");
                                                                            getTimeTableData();
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
                                                    Utility.ping(mContext, "something went wrong.");
                                                }
                                            } else {
                                                Utility.ping(mContext, "Network not available");
                                            }
                                        }

                                        @Override
                                        public void getAddLecture() {
                                            Dialog();
                                        }
                                    });
                                    lvExpTimeTable.setAdapter(listAdapterTimeTable);
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsTimetable.setVisibility(View.VISIBLE);
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
        listDataChild = new HashMap<String, ArrayList<TeacherGetTimetableModel.TimeTable.TimeTableData>>();

        for (int i = 0; i < timetableModels.get(0).getGetTimeTable().size(); i++) {
            listDataHeader.add(timetableModels.get(0).getGetTimeTable().get(i).getDay());
            ArrayList<TeacherGetTimetableModel.TimeTable.TimeTableData> rows = new ArrayList<TeacherGetTimetableModel.TimeTable.TimeTableData>();
            for (int j = 0; j < timetableModels.get(0).getGetTimeTable().get(i).getGetTimeTableData().size(); j++) {

                rows.add(timetableModels.get(0).getGetTimeTable().get(i).getGetTimeTableData().get(j));

            }
            listDataChild.put(listDataHeader.get(i), rows);
        }
    }

    public void Dialog() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.list_item_add_lecture, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();
        alertDialogAndroid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        wlp.gravity = Gravity.CENTER;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.show();


        add_lecture_btn = (Button) layout.findViewById(R.id.add_lecture_btn);
        close_btn = (Button) layout.findViewById(R.id.close_btn);
        edit_grade_spinner = (Spinner) layout.findViewById(R.id.edit_grade_spinner);
        edit_subject_spinner = (Spinner) layout.findViewById(R.id.edit_subject_spinner);
        edit_Starttime_spinner = (Spinner) layout.findViewById(R.id.edit_Starttime_spinner);
        edit_Starttime1_spinner = (Spinner) layout.findViewById(R.id.edit_Starttime1_spinner);
        edit_Endtime_spinner = (Spinner) layout.findViewById(R.id.edit_Endtime_spinner);
        edit_Endtime1_spinner = (Spinner) layout.findViewById(R.id.edit_Endtime1_spinner);
        edit_day_txt = (TextView) layout.findViewById(R.id.edit_day_txt);
        edit_day_lecture_txt = (TextView) layout.findViewById(R.id.edit_day_lecture_txt);
        start_time_hour_lecture_txt=(TextView)layout.findViewById(R.id.start_time_hour_lecture_txt);
        end_time_hour_lecture_txt=(TextView)layout.findViewById(R.id.end_time_hour_lecture_txt);
        edit_lecture_section_llListData = (LinearLayout) layout.findViewById(R.id.edit_lecture_section_llListData);

        getSpinnerData();
        setTodayschedule();
        fillStartEndTimeHourspinner();
        edit_day_txt.setText(listAdapterTimeTable.getDay());
        edit_day_lecture_txt.setText(listAdapterTimeTable.getLecture());

        Day = edit_day_txt.getText().toString();
        Lecture = edit_day_lecture_txt.getText().toString();

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });
        edit_grade_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectedStandardname = edit_grade_spinner.getSelectedItem().toString();
                String getSelectedStandardid = standardIdMap.get(edit_grade_spinner.getSelectedItemPosition());

                Log.d("value", SelectedStandardname + " " + getSelectedStandardid);
                Standardid = getSelectedStandardid.toString();
                Log.d("requestfor", Standardid);


                if (!SelectedStandardname.equalsIgnoreCase("")) {
                    classnamearray.clear();
                    classidarray.clear();
                    fillsection();
                }
                getLectureTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        edit_subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SelectedSubjectname = edit_subject_spinner.getSelectedItem().toString();
                String getSelectedSubjectid = subjectIdMap.get(edit_subject_spinner.getSelectedItemPosition());

                Log.d("value", SelectedSubjectname + " " + getSelectedSubjectid);
                Subejctid = getSelectedSubjectid.toString();
                Log.d("subjectId", Subejctid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edit_Starttime_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                starttimehour = edit_Starttime_spinner.getSelectedItem().toString();
                Log.d("starttimehour", starttimehour);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edit_Starttime1_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                starttimeminit = edit_Starttime1_spinner.getSelectedItem().toString();
                Log.d("starttimeminit", starttimeminit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edit_Endtime_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endtimehour = edit_Endtime_spinner.getSelectedItem().toString();
                Log.d("endtimehour", endtimehour);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edit_Endtime1_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endtimeminit = edit_Endtime1_spinner.getSelectedItem().toString();
                Log.d("endtimeminit", endtimeminit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        add_lecture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLecture();
            }
        });
    }

    public void setTodayschedule() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
//            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));

                        timeTableSubjectDetailAsyncTask = new TimeTableSubjectDetailAsyncTask(params);
                        timeTableSubjectResponse = timeTableSubjectDetailAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (timeTableSubjectResponse.getFinalArray().size() > 0) {
                                    fillsubjectspinner();
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
    public void getLectureTime() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
//            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("LectureID", Lecture);
                        params.put("StandardID",Standardid);

                        getLectureDetailsAsyncTask = new GetLectureDetailsAsyncTask(params);
                        timeTableSubjectResponse = getLectureDetailsAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (timeTableSubjectResponse.getFinalArray().size() > 0) {
                                    start_time_hour_lecture_txt.setText(timeTableSubjectResponse.getFinalArray().get(0).getStartTimeHour()+" : "+
                                    timeTableSubjectResponse.getFinalArray().get(0).getStartTimeMin());
                                    end_time_hour_lecture_txt.setText(timeTableSubjectResponse.getFinalArray().get(0).getEndTimeHour()+" : "+
                                            timeTableSubjectResponse.getFinalArray().get(0).getEndTimeMin());
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
    public void getSpinnerData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
//            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();

                        getStandardSectionAsyncTask = new GetStandardSectionAsyncTask(params);
                        getStandardSectionModelResponse = getStandardSectionAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (getStandardSectionModelResponse.getFinalArray().size() > 0) {
                                    fillstandanrdspinner();
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

    public void fillstandanrdspinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");

        ArrayList<String> standardname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            standardname.add(firstValue.get(z));
            for (int i = 0; i < getStandardSectionModelResponse.getFinalArray().size(); i++) {
                standardname.add(getStandardSectionModelResponse.getFinalArray().get(i).getStandard());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> standardId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            standardId.add(firstValueId.get(m));
            for (int j = 0; j < getStandardSectionModelResponse.getFinalArray().size(); j++) {
                standardId.add(getStandardSectionModelResponse.getFinalArray().get(j).getStandardID());
            }
        }
        String[] spinnerstandardIdArray = new String[standardId.size()];

        standardIdMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            standardIdMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = standardname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(edit_grade_spinner);

            popupWindow.setHeight(spinnerstandardIdArray.length > 5 ? 500 : spinnerstandardIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        edit_grade_spinner.setAdapter(adapterYear);
        Standardid = "0";
    }

    public void fillsection() {
        ArrayList<String> sectionArray = new ArrayList<String>();
        sectionArray.clear();
        for (int m = 0; m < getStandardSectionModelResponse.getFinalArray().size(); m++) {
            if (SelectedStandardname.equalsIgnoreCase(getStandardSectionModelResponse.getFinalArray().get(m).getStandard())) {
                for (int k = 0; k < getStandardSectionModelResponse.getFinalArray().get(m).getSectionDetail().size(); k++) {
                    sectionArray.add(getStandardSectionModelResponse.getFinalArray().get(m).getSectionDetail().get(k).getSection()
                            + "|" + getStandardSectionModelResponse.getFinalArray().get(m).getSectionDetail().get(k).getSectionID());
                    Log.d("sectionArray", "" + sectionArray);
                }
            }
        }

        if (edit_lecture_section_llListData.getChildCount() > 0) {
            edit_lecture_section_llListData.removeAllViews();
        }

        try {
            for (int i = 0; i < sectionArray.size(); i++) {
                View convertView = LayoutInflater.from(mContext).inflate(R.layout.list_checkbox, null);
                checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);

                String checkvalue = sectionArray.get(i);
                String[] splitvalue = checkvalue.split("\\|");
                Log.d("checkvalue", checkvalue);
                checkBox.setText(splitvalue[0]);
                checkBox.setTag(splitvalue[1]);
                if (i == 0) {
                    checkBox.setChecked(true);
                    checknamestr = checkBox.getText().toString();
                    checkidstr = checkBox.getTag().toString();
                    classnamearray.add(checknamestr);
                    classidarray.add(checkidstr);
                }
                checkBox.setOnClickListener(onStateChangedListener(checkBox, i));
                edit_lecture_section_llListData.addView(convertView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private View.OnClickListener onStateChangedListener(final CheckBox checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checknamestr = checkBox.getText().toString();
                checkidstr = checkBox.getTag().toString();
                if (checkBox.isChecked()) {
                    selectedPosition = position;
                    classnamearray.add(checknamestr);
                    classidarray.add(checkidstr);
                    Log.d("classnamearray", classnamearray.toString());
                    Log.d("classidarray", classidarray.toString());
                } else {
                    selectedPosition = -1;
                    classnamearray.remove(checknamestr);
                    classidarray.remove(checkidstr);
                    Log.d("classnamearray", classnamearray.toString());
                    Log.d("classidarray", classidarray.toString());
                }
            }
        };
    }

    public void fillsubjectspinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");

        ArrayList<Integer> subjectId = new ArrayList<Integer>();
        ArrayList<String> rowsubject = new ArrayList<String>();

        for (int i = 0; i < firstValue.size(); i++) {
            rowsubject.add(firstValue.get(i));
            for (int z = 0; z < timeTableSubjectResponse.getFinalArray().size(); z++) {
                rowsubject.add(timeTableSubjectResponse.getFinalArray().get(z).getSubject());
//                subjectId.add(timeTableSubjectResponse.getFinalArray().get(z).getSubjectID());
            }
        }

        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        for (int m = 0; m < firstValueId.size(); m++) {
            subjectId.add(firstValueId.get(m));
            for (int j = 0; j < timeTableSubjectResponse.getFinalArray().size(); j++) {
                subjectId.add(timeTableSubjectResponse.getFinalArray().get(j).getSubjectID());
            }
        }

        Log.d("rowsubjectbefore", "" + rowsubject);
        Log.d("subjectIdbefore", "" + subjectId);


        String[] spinnersubjectIdArray = new String[subjectId.size()];

        subjectIdMap = new HashMap<Integer, String>();
        for (int i = 0; i < subjectId.size(); i++) {
            subjectIdMap.put(i, String.valueOf(subjectId.get(i)));
            spinnersubjectIdArray[i] = rowsubject.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(edit_subject_spinner);

            popupWindow.setHeight(spinnersubjectIdArray.length > 5 ? 500 : spinnersubjectIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersubjectIdArray);
        edit_subject_spinner.setAdapter(adapterYear);
    }

    public void fillStartEndTimeHourspinner() {
        ArrayList<String> starthours = new ArrayList<String>();

        ArrayList<String> starthoursselect = new ArrayList<>();
        starthoursselect.add("--Select--");

        for (int k = 0; k < starthoursselect.size(); k++) {
            starthours.add(starthoursselect.get(k));
            for (int j = 0; j < 24; j++) {
                if (j < 10) {
                    starthours.add(String.valueOf("0" + j));
                } else {
                    starthours.add(String.valueOf(j));
                }
            }
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(edit_Starttime_spinner);

            popupWindow.setHeight(starthours.size() > 5 ? 500 : starthours.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, starthours);
        edit_Starttime_spinner.setAdapter(adapterYear);


        ArrayList<String> start1hours = new ArrayList<String>();
        ArrayList<String> start1hoursselect = new ArrayList<>();
        start1hoursselect.add("--Select--");

        for (int k = 0; k < start1hoursselect.size(); k++) {
            start1hours.add(start1hoursselect.get(k));
            for (int j = 0; j < 59; j++) {
                if (j < 10) {
                    start1hours.add(String.valueOf("0" + j));
                } else {
                    start1hours.add(String.valueOf(j));
                }
            }
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(edit_Starttime1_spinner);

            popupWindow.setHeight(start1hours.size() > 5 ? 500 : start1hours.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterstarthour = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, start1hours);
        edit_Starttime1_spinner.setAdapter(adapterstarthour);

        ArrayList<String> endhours = new ArrayList<String>();
        ArrayList<String> endhoursselect = new ArrayList<>();
        endhoursselect.add("--Select--");

        for (int k = 0; k < endhoursselect.size(); k++) {
            endhours.add(endhoursselect.get(k));
            for (int j = 0; j < 23; j++) {
                if (j < 10) {
                    endhours.add(String.valueOf("0" + j));
                } else {
                    endhours.add(String.valueOf(j));
                }
            }
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(edit_Endtime_spinner);

            popupWindow.setHeight(endhours.size() > 5 ? 500 : endhours.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterendhour = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, endhours);
        edit_Endtime_spinner.setAdapter(adapterendhour);

        ArrayList<String> end1hours = new ArrayList<String>();
        ArrayList<String> end1hoursselect = new ArrayList<>();
        end1hoursselect.add("--Select--");

        for (int k = 0; k < end1hoursselect.size(); k++) {
            end1hours.add(end1hoursselect.get(k));
            for (int j = 0; j < 59; j++) {
                if (j < 10) {
                    end1hours.add(String.valueOf("0" + j));
                } else {
                    end1hours.add(String.valueOf(j));
                }
            }
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(edit_Endtime1_spinner);

            popupWindow.setHeight(end1hours.size() > 5 ? 500 : end1hours.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterend1hour = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, end1hours);
        edit_Endtime1_spinner.setAdapter(adapterend1hour);
    }

    public void addLecture() {

        String classIdStr = "";
        for (String s : classidarray) {
            classIdStr = classIdStr + "," + s;
        }


        if(!classIdStr.equalsIgnoreCase("")) {
            finalclassIdStr = classIdStr.substring(1, classIdStr.length());
            Log.d("finalclassIdStr", finalclassIdStr);
        }else{
            Utility.ping(mContext,"Please Select Standard");
        }
        if (!Subejctid.equalsIgnoreCase("0")) {
            if (!finalclassIdStr.equalsIgnoreCase("") && !Standardid.equalsIgnoreCase("")
                    && !Subejctid.equalsIgnoreCase("") && !Day.equalsIgnoreCase("")
                    && !Lecture.equalsIgnoreCase("")) {
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
                                params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                                params.put("ClassID", finalclassIdStr);
                                params.put("StandardID", Standardid);
                                params.put("SubjectID", Subejctid);
                                params.put("DayName", Day);
                                params.put("LectureName", Lecture);


                                insertTimetableAsyncTask = new InsertTimetableAsyncTask(params);
                                insertLectureModel = insertTimetableAsyncTask.execute().get();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        if (insertLectureModel.getFinalArray().size() >= 0) {
                                            Utility.ping(mContext, "Add Lecture.");
                                            alertDialogAndroid.dismiss();
                                            getTimeTableData();
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
            } else {
                Utility.ping(mContext, "Blank field not available");
            }
        }else{
            Utility.ping(mContext,"Please Fill All Field.");
        }
    }
}
