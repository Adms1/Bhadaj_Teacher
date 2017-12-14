package anandniketan.com.bhadajteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajteacher.Activities.LoginActivity;
import anandniketan.com.bhadajteacher.Adapter.ExpandableListAdapterTimeTable;
import anandniketan.com.bhadajteacher.AsyncTasks.DeleteTimetableAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.GetTeacherAssignedSubjectAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.GetTeacherGetTimetableAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.InsertTimetableAsyncTask;
import anandniketan.com.bhadajteacher.Interfacess.onDeleteLecture;
import anandniketan.com.bhadajteacher.Models.DeleteLectureModel;
import anandniketan.com.bhadajteacher.Models.InsertLectureModel;
import anandniketan.com.bhadajteacher.Models.TeacherAssignedSubjectModel;
import anandniketan.com.bhadajteacher.Models.TeacherGetTimetableModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;

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
    private TextView edit_day_txt, edit_day_lecture_txt;
    private LinearLayout edit_lecture_section_llListData;
    private CheckBox checkBox;
    HashMap<Integer, String> standardIdMap;
    HashMap<Integer, String> subjectIdMap;
    String Standardid, Subejctid, checknamestr, checkidstr, starttimehour, starttimeminit, endtimehour, endtimeminit, Day, Lecture, selectedStandard;
    private ArrayList<String> classnamearray = new ArrayList<String>();
    private ArrayList<String> classidarray = new ArrayList<String>();
    private int selectedPosition = -1;

    //use for fill dialogView
    private GetTeacherAssignedSubjectAsyncTask getTeacherAssignedSubjectAsyncTask = null;
    private ArrayList<TeacherAssignedSubjectModel> teacherAssignedSubjectModels = new ArrayList<>();

    //use for insertLecture
    private InsertTimetableAsyncTask insertTimetableAsyncTask = null;
    InsertLectureModel insertLectureModel;

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

        wlp.gravity = Gravity.CENTER_HORIZONTAL;
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
        edit_lecture_section_llListData = (LinearLayout) layout.findViewById(R.id.edit_lecture_section_llListData);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(edit_grade_spinner);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(edit_subject_spinner);
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(edit_Starttime_spinner);
            android.widget.ListPopupWindow popupWindow3 = (android.widget.ListPopupWindow) popup.get(edit_Starttime1_spinner);
            android.widget.ListPopupWindow popupWindow4 = (android.widget.ListPopupWindow) popup.get(edit_Endtime_spinner);
            android.widget.ListPopupWindow popupWindow5 = (android.widget.ListPopupWindow) popup.get(edit_Endtime1_spinner);
            // Set popupWindow height to 500px
            popupWindow.setHeight(300);
            popupWindow1.setHeight(200);
            popupWindow2.setHeight(300);
            popupWindow3.setHeight(300);
            popupWindow4.setHeight(300);
            popupWindow5.setHeight(300);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }




        setTodayschedule();
        fillStartEndTimeHourspinner();
        edit_day_txt.setText(listAdapterTimeTable.getDay());
        edit_day_lecture_txt.setText(listAdapterTimeTable.getLecture());

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });
        edit_grade_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String SelectedStandardname = edit_grade_spinner.getSelectedItem().toString();
                String getSelectedStandardid = standardIdMap.get(edit_grade_spinner.getSelectedItemPosition());

                Log.d("value", SelectedStandardname + " " + getSelectedStandardid);
                Standardid = getSelectedStandardid.toString();
                Log.d("requestfor", Standardid);
                classnamearray.clear();
                classidarray.clear();
                if (teacherAssignedSubjectModels.size() > 0) {
                    selectedStandard = parent.getSelectedItem().toString();
                    fillsection();
                    fillsubjectspinner();
                }
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
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));

                        getTeacherAssignedSubjectAsyncTask = new GetTeacherAssignedSubjectAsyncTask(params);
                        teacherAssignedSubjectModels = getTeacherAssignedSubjectAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (teacherAssignedSubjectModels.size() > 0) {
                                    fillstandardspinner();
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

    public void fillstandardspinner() {
        ArrayList<Integer> standardId = new ArrayList<Integer>();
        ArrayList<String> row = new ArrayList<String>();

        for (int z = 0; z < teacherAssignedSubjectModels.size(); z++) {
            standardId.add(Integer.parseInt(teacherAssignedSubjectModels.get(z).getStandardID()));
        }
        for (int z = 0; z < teacherAssignedSubjectModels.size(); z++) {
            row.add(teacherAssignedSubjectModels.get(z).getStandard());
        }

        String[] spinnerstandardIdArray = new String[standardId.size()];

        standardIdMap = new HashMap<Integer, String>();
        for (int i = 0; i < standardId.size(); i++) {
            standardIdMap.put(i, String.valueOf(standardId.get(i)));
            spinnerstandardIdArray[i] = row.get(i).trim();
        }
        Log.d("spinnerstandardIdArray", Arrays.toString(spinnerstandardIdArray));
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerstandardIdArray);
        edit_grade_spinner.setAdapter(adapterYear);

    }

    public void fillsection() {
        ArrayList<String> sectionArray = new ArrayList<String>();
        String standardTxt = teacherAssignedSubjectModels.get(0).getStandard();
        sectionArray.clear();
        for (int m = 0; m < teacherAssignedSubjectModels.size(); m++) {
            if (selectedStandard.equalsIgnoreCase(teacherAssignedSubjectModels.get(m).getStandard())) {
                sectionArray.add(teacherAssignedSubjectModels.get(m).getClassname() + "|" + teacherAssignedSubjectModels.get(m).getClassID());
                Log.d("sectionArray", "" + sectionArray);
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
        ArrayList<Integer> subjectId = new ArrayList<Integer>();
        ArrayList<String> rowsubject = new ArrayList<String>();
        String standardTxt = teacherAssignedSubjectModels.get(0).getStandard();

        for (int z = 0; z < teacherAssignedSubjectModels.size(); z++) {
            if (standardTxt.equalsIgnoreCase(teacherAssignedSubjectModels.get(z).getStandard())) {
                rowsubject.add(teacherAssignedSubjectModels.get(z).getSubject());
                subjectId.add(Integer.parseInt(teacherAssignedSubjectModels.get(z).getSubjectID()));
            }
        }
        Log.d("subjectId", "" + subjectId);
        String[] spinnersubjectIdArray = new String[subjectId.size()];

        subjectIdMap = new HashMap<Integer, String>();
        for (int i = 0; i < subjectId.size(); i++) {
            subjectIdMap.put(i, String.valueOf(subjectId.get(i)));
            spinnersubjectIdArray[i] = rowsubject.get(i).trim();
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnersubjectIdArray);
        edit_subject_spinner.setAdapter(adapterYear);
    }

    public void fillStartEndTimeHourspinner() {
        ArrayList<String> starthours = new ArrayList<String>();

        for (int j = 0; j < 24; j++) {
            if (j < 10) {
                starthours.add(String.valueOf("0" + j));
            } else {
                starthours.add(String.valueOf(j));
            }
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, starthours);
        edit_Starttime_spinner.setAdapter(adapterYear);


        ArrayList<String> start1hours = new ArrayList<String>();

        for (int j = 0; j < 59; j++) {
            if (j < 10) {
                start1hours.add(String.valueOf("0" + j));
            } else {
                start1hours.add(String.valueOf(j));
            }
        }
        ArrayAdapter<String> adapterstarthour = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, start1hours);
        edit_Starttime1_spinner.setAdapter(adapterstarthour);

        ArrayList<String> endhours = new ArrayList<String>();

        for (int j = 0; j < 23; j++) {
            if (j < 10) {
                endhours.add(String.valueOf("0" + j));
            } else {
                endhours.add(String.valueOf(j));
            }
        }
        ArrayAdapter<String> adapterendhour = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, endhours);
        edit_Endtime_spinner.setAdapter(adapterendhour);

        ArrayList<String> end1hours = new ArrayList<String>();

        for (int j = 0; j < 59; j++) {
            if (j < 10) {
                end1hours.add(String.valueOf("0" + j));
            } else {
                end1hours.add(String.valueOf(j));
            }
        }
        ArrayAdapter<String> adapterend1hour = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, end1hours);
        edit_Endtime1_spinner.setAdapter(adapterend1hour);
    }

    public void addLecture() {
        Day = edit_day_txt.getText().toString();
        Lecture = edit_day_lecture_txt.getText().toString();
        String classIdStr = "";
        for (String s : classidarray) {
            classIdStr = classIdStr + "," + s;
        }
        final String finalclassIdStr = classIdStr.substring(1, classIdStr.length());
        Log.d("finalclassIdStr", finalclassIdStr);
        if (!finalclassIdStr.equalsIgnoreCase("") && !Standardid.equalsIgnoreCase("") && !Subejctid.equalsIgnoreCase("") && !Day.equalsIgnoreCase("")
                && !Lecture.equalsIgnoreCase("") && !starttimehour.equalsIgnoreCase("") && !starttimeminit.equalsIgnoreCase("")
                && !endtimehour.equalsIgnoreCase("") && !endtimeminit.equalsIgnoreCase("")) {
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
                            params.put("Strttimehour", starttimehour);
                            params.put("StrttimeMin", starttimeminit);
                            params.put("Endtimehour", endtimehour);
                            params.put("EndtimeMin", endtimeminit);

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
    }
}
