package anandniketan.com.anbcteacher.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Adapter.AttendanceListAdapter;
import anandniketan.com.anbcteacher.AsyncTasks.GetInsertAttendanceAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetNewStaffAttendanceAsyncTask;
import anandniketan.com.anbcteacher.Models.Attendance.StaffInsertAttendenceModel;
import anandniketan.com.anbcteacher.Models.Attendance.StaffNewAttendenceModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;

public class OneFragmentAtt extends Fragment implements DatePickerDialog.OnDateSetListener {

    public OneFragmentAtt() {
        // Required empty public constructor
    }

    int position;
    String classId, standardId;

    @SuppressLint("ValidFragment")
    public OneFragmentAtt(int i, String classId, String StandardId) {
        this.position = i;
        this.classId = classId;
        this.standardId = StandardId;

    }

    private Context mContext;
    private View rootView;
    private ProgressDialog progressDialog = null;
    private static Button start_date;
    private DatePickerDialog datePickerDialog;
    int mYear, mMonth, mDay;
    int Year, Month, Day;
    Calendar calendar;
    private TextView total_student_txt, present_txt, absent_txt, leave_txt, txtNoRecords;
    private LinearLayout student_list_linear;
    private ListView student_list;
    private GetNewStaffAttendanceAsyncTask getNewStaffAttendanceAsyncTask = null;
    StaffNewAttendenceModel staffNewAttendenceModelResponse;

    AttendanceListAdapter attendanceListAdapter = null;

    private ImageView insert_attendance_img;
    private LinearLayout header_linear;
    //use for insert Attendance
    private GetInsertAttendanceAsyncTask getInsertAttendanceAsyncTask = null;
    StaffInsertAttendenceModel staffInsertAttendenceModelResponse;
    String Attendanceidstr, Attendacestatusstr, studentidstr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_one_fragment_att, container, false);
        mContext = getActivity();
        Log.d("Current Position ", "Position = " + position + " user id  : " + classId + "standard id :" + standardId);
        init();
        setListner();
        setGetstaffAttendanceDetail();

        return rootView;
    }

    public void init() {

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        header_linear = (LinearLayout) rootView.findViewById(R.id.header_linear);
        start_date = (Button) rootView.findViewById(R.id.start_date);
        total_student_txt = (TextView) rootView.findViewById(R.id.total_student_txt);
        present_txt = (TextView) rootView.findViewById(R.id.present_txt);
        absent_txt = (TextView) rootView.findViewById(R.id.absent_txt);
        leave_txt = (TextView) rootView.findViewById(R.id.leave_txt);
        txtNoRecords = (TextView) rootView.findViewById(R.id.txtNoRecords);

        student_list_linear = (LinearLayout) rootView.findViewById(R.id.student_list_linear);
        student_list = (ListView) rootView.findViewById(R.id.student_list);
        insert_attendance_img = (ImageView) rootView.findViewById(R.id.insert_attendance_img);
        start_date.setText(Utility.getTodaysDate());
        setUserVisibleHint(true);
    }

    public void setListner() {


        insert_attendance_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertAttendance();
            }
        });
        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(OneFragmentAtt.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.setMaxDate(calendar);

                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Selected Date : " + Day + "/" + Month + "/" + Year;
        String datestr = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

        mDay = dayOfMonth;
        mMonth = monthOfYear + 1;
        mYear = year;
        String d, m, y;
        d = Integer.toString(mDay);
        m = Integer.toString(mMonth);
        y = Integer.toString(mYear);

        if (mDay < 10) {
            d = "0" + d;
        }
        if (mMonth < 10) {
            m = "0" + m;
        }

        start_date.setText(d + "/" + m + "/" + y);
        setGetstaffAttendanceDetail();
    }

    public void setGetstaffAttendanceDetail() {
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
                        params.put("AttDate", start_date.getText().toString());
                        params.put("StdID", standardId);//AppConfiguration.stdid
                        params.put("ClsID", classId);//AppConfiguration.clsid

                        getNewStaffAttendanceAsyncTask = new GetNewStaffAttendanceAsyncTask(params);
                        staffNewAttendenceModelResponse = getNewStaffAttendanceAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (staffNewAttendenceModelResponse.getFinalArray().size() > 0) {
                                    txtNoRecords.setVisibility(View.GONE);
                                    prepareList();
                                    attendanceListAdapter = new AttendanceListAdapter(getActivity(), staffNewAttendenceModelResponse);
                                    student_list.setAdapter(attendanceListAdapter);
                                    student_list.deferNotifyDataSetChanged();
                                    insert_attendance_img.setVisibility(View.VISIBLE);
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecords.setVisibility(View.VISIBLE);
                                    insert_attendance_img.setVisibility(View.GONE);
                                    header_linear.setVisibility(View.GONE);
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

    public void prepareList() {
        total_student_txt.setText(Html.fromHtml("Total Students : " + "<font color='#1B88C8'>" + "<b>" + staffNewAttendenceModelResponse.getFinalArray().get(0).getTotal() + "</b>"));
        present_txt.setText(Html.fromHtml("Present : " + "<font color='#a4c639'>" + "<b>" + staffNewAttendenceModelResponse.getFinalArray().get(0).getTotalPresent() + "</b>"));
        absent_txt.setText(Html.fromHtml("Absent : " + "<font color='#ff0000'>" + "<b>" + staffNewAttendenceModelResponse.getFinalArray().get(0).getTotalAbsent() + "</b>"));
        leave_txt.setText(Html.fromHtml("Leave : " + "<font color='#ff9623'>" + "<b>" + staffNewAttendenceModelResponse.getFinalArray().get(0).getTotalLeave() + "</b>"));

        if (!staffNewAttendenceModelResponse.getFinalArray().get(0).getStudentDetail().get(0).getAttendenceStatus().equalsIgnoreCase("-2")) {
            insert_attendance_img.setBackgroundResource(R.drawable.update_1);
        } else {
            insert_attendance_img.setBackgroundResource(R.drawable.submit);
        }
        for (int j = 0; j < staffNewAttendenceModelResponse.getFinalArray().get(0).getStudentDetail().size(); j++) {
            if (staffNewAttendenceModelResponse.getFinalArray().get(0).getStudentDetail().get(j).getAttendenceStatus().equalsIgnoreCase("-2")) {
                staffNewAttendenceModelResponse.getFinalArray().get(0).getStudentDetail().get(j).setAttendenceStatus("1");
            }
        }
    }
   public void InsertAttendance() {
        final ArrayList<String> Attendanceid = new ArrayList<>();
        final ArrayList<String> Attendacestatus = new ArrayList<>();
        final ArrayList<String> studid = new ArrayList<>();

        for (int i = 0; i < staffNewAttendenceModelResponse.getFinalArray().size(); i++) {
            for (int j = 0; j < staffNewAttendenceModelResponse.getFinalArray().get(i).getStudentDetail().size(); j++) {
                Attendanceid.add(String.valueOf(staffNewAttendenceModelResponse.getFinalArray().get(i).getStudentDetail().get(j).getAttendanceID()));
                Attendacestatus.add(String.valueOf(staffNewAttendenceModelResponse.getFinalArray().get(i).getStudentDetail().get(j).getAttendenceStatus()));
                studid.add(String.valueOf(staffNewAttendenceModelResponse.getFinalArray().get(i).getStudentDetail().get(j).getStudentID()));
            }
        }
        Log.d("Attendanceid", "" + Attendanceid);
        Log.d("Attendacestatus", "" + Attendacestatus);
        Log.d("studid", "" + studid);

        Attendanceidstr = "";
        for (String s : Attendanceid) {
            Attendanceidstr = Attendanceidstr + "," + s;
        }
        Log.d("Attendanceidstr", Attendanceidstr);
        Attendanceidstr = Attendanceidstr.substring(1, Attendanceidstr.length());
        Log.d("finalstatusStr", Attendanceidstr);

        Attendacestatusstr = "";
        for (String s : Attendacestatus) {
            Attendacestatusstr = Attendacestatusstr + "," + s;
        }
        Log.d("Attendacestatusstr", Attendacestatusstr);
        Attendacestatusstr = Attendacestatusstr.substring(1, Attendacestatusstr.length());
        Log.d("Attendacestatusstr", Attendacestatusstr);

        studentidstr = "";
        for (String s : studid) {
            studentidstr = studentidstr + "," + s;
        }
        Log.d("studentidstr", studentidstr);
        studentidstr = studentidstr.substring(1, studentidstr.length());
        Log.d("studentidstr", studentidstr);


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
                        params.put("StandardID", standardId);
                        params.put("ClassID", classId);
                        params.put("Date", start_date.getText().toString());
                        params.put("Comment", "test");
                        params.put("AttendacneStatus", Attendacestatusstr);
                        params.put("CurrantDate", Utility.getTodaysDate());
                        params.put("StudentID", studentidstr);
                        params.put("AttendanceID", Attendanceidstr);


                        getInsertAttendanceAsyncTask = new GetInsertAttendanceAsyncTask(params);
                        staffInsertAttendenceModelResponse = getInsertAttendanceAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (staffInsertAttendenceModelResponse.getFinalArray().size() > 0) {
                                    Utility.ping(mContext, "Save Sucessfully");
                                    updateAttendace();
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

    public void updateAttendace() {
        for (int i = 0; i < staffInsertAttendenceModelResponse.getFinalArray().size(); i++) {
            total_student_txt.setText(Html.fromHtml("Total Students : " + "<font color='#1B88C8'>" + "<b>" + String.valueOf(staffInsertAttendenceModelResponse.getFinalArray().get(0).getTotal())));
            present_txt.setText(Html.fromHtml("Present : " + "<font color='#a4c639'>" + "<b>" + String.valueOf(staffInsertAttendenceModelResponse.getFinalArray().get(0).getTotalPresent())));
            absent_txt.setText(Html.fromHtml("Absent : " + "<font color='#ff0000'>" + "<b>" + String.valueOf(staffInsertAttendenceModelResponse.getFinalArray().get(0).getTotalAbsent())));
            leave_txt.setText(Html.fromHtml("Leave : " + "<font color='#ff9623'>" + "<b>" + String.valueOf(staffInsertAttendenceModelResponse.getFinalArray().get(0).getTotalLeave())));

        }
    }
}
