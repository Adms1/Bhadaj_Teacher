package anandniketan.com.bhadajteacher.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import anandniketan.com.bhadajteacher.Activities.LoginActivity;
import anandniketan.com.bhadajteacher.AsyncTasks.GetLeaveHeadAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.GetStandardSectionAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.InsertLeaveAsyncTask;
import anandniketan.com.bhadajteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class LeaveFragment extends Fragment implements View.OnClickListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private static String dateFinal;
    private static boolean isFromDate = false;
    Fragment fragment;
    FragmentManager fragmentManager;
    GetLeaveHeadAsyncTask getLeaveHeadAsyncTask = null;
    LeaveMainModel getleaveheadResponse;
    InsertLeaveAsyncTask insertLeaveAsyncTask = null;
    HashMap<Integer, String> spinnerHeadIdNameMap;
    int Year, Month, Day;
    Calendar calendar;
    private View rootView;
    private Context mContext;
    private Button btnLogout, btnBacktest_homework, btnSave, btnCancel;
    private Spinner leave_days_spinner, leave_heads_spinner;
    private TextView txtstartDate, txtendDate;
    private EditText edtPurpose;
    private ProgressDialog progressDialog = null;
    private ArrayList<String> leaveDayArray;
    private String headIdStr, headNameStr, leaveDaysStr, leaveReasonStr, leaveStartDateStr, leaveEndDateStr, leaveIdStr = "",
            editLeaveDaysStr, editLeaveStartDateStr, editLeaveEndDateStr, editLeaveReasonStr, editLeaveHeadStr, geteditLeaveDetailStr = "";
    private com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog;

    public LeaveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leave, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnBacktest_homework = (Button) rootView.findViewById(R.id.btnBacktest_homework);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnCancel = (Button) rootView.findViewById(R.id.btnCancel);

        leave_days_spinner = (Spinner) rootView.findViewById(R.id.leave_days_spinner);
        leave_heads_spinner = (Spinner) rootView.findViewById(R.id.leave_heads_spinner);

        txtstartDate = (TextView) rootView.findViewById(R.id.txtstartDate);
        txtendDate = (TextView) rootView.findViewById(R.id.txtendDate);
        edtPurpose = (EditText) rootView.findViewById(R.id.edtPurpose);
        fillEditLEaveDetail();
        fillleavedaysspinner();
        getLeaveHeadDetail();
    }

    public void setListners() {
        btnLogout.setOnClickListener(this);
        btnBacktest_homework.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        leave_heads_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                headNameStr = leave_heads_spinner.getSelectedItem().toString();
                String getSelectedheadid = spinnerHeadIdNameMap.get(leave_heads_spinner.getSelectedItemPosition());

                Log.d("value", headNameStr + " " + getSelectedheadid);
                headIdStr = getSelectedheadid.toString();
                Log.d("headIdStr", headIdStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        leave_days_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leaveDaysStr = leave_days_spinner.getSelectedItem().toString();
                Log.d("leaveDaysStr", leaveDaysStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txtstartDate.setOnClickListener(this);
        txtendDate.setOnClickListener(this);

    }

    public void getLeaveHeadDetail() {
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

                        getLeaveHeadAsyncTask = new GetLeaveHeadAsyncTask(params);
                        getleaveheadResponse = getLeaveHeadAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (getleaveheadResponse.getFinalArray().size() > 0) {
                                    fillleaveheadspinner();
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

    public void fillleaveheadspinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");

        ArrayList<String> headname = new ArrayList<>();
        for (int z = 0; z < firstValue.size(); z++) {
            headname.add(firstValue.get(z));
            for (int i = 0; i < getleaveheadResponse.getFinalArray().size(); i++) {
                headname.add(getleaveheadResponse.getFinalArray().get(i).getEmployeeName());
            }
        }
        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);
        ArrayList<Integer> headId = new ArrayList<>();
        for (int m = 0; m < firstValueId.size(); m++) {
            headId.add(firstValueId.get(m));
            for (int j = 0; j < getleaveheadResponse.getFinalArray().size(); j++) {
                headId.add(getleaveheadResponse.getFinalArray().get(j).getEmployeeID());
            }
        }
        String[] spinnerheadIdArray = new String[headId.size()];

        spinnerHeadIdNameMap = new HashMap<Integer, String>();
        for (int i = 0; i < headId.size(); i++) {
            spinnerHeadIdNameMap.put(i, String.valueOf(headId.get(i)));
            spinnerheadIdArray[i] = headname.get(i).trim();
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(leave_heads_spinner);

            popupWindow.setHeight(spinnerheadIdArray.length > 5 ? 500 : spinnerheadIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerheadIdArray);
        leave_heads_spinner.setAdapter(adapterYear);

        for (int i = 0; i < headname.size(); i++) {
            if (editLeaveHeadStr.equalsIgnoreCase(headname.get(i))) {
                leave_heads_spinner.setSelection(i);
                break;
            } else {
                leave_heads_spinner.setSelection(0);
            }
        }
    }

    public void fillleavedaysspinner() {
        ArrayList<String> leaveday = new ArrayList<>();
        leaveday.add("--Select--");
        leaveday.add("0.5");
        leaveday.add("1");
        leaveday.add("1.5");
        leaveday.add("2");
        leaveday.add("2.5");
        leaveday.add("3");
        leaveday.add("3.5");
        leaveday.add("4");
        leaveday.add("4.5");
        leaveday.add("5");
        leaveday.add("5.5");
        leaveday.add("6");
        leaveday.add("6.5");
        leaveday.add("7");
        leaveday.add("7.5");
        leaveday.add("8");
        leaveday.add("8.5");
        leaveday.add("9");
        leaveday.add("9.5");
        leaveday.add("10");
        leaveday.add("10.5");
        leaveday.add("11");
        leaveday.add("11.5");
        leaveday.add("12");
        leaveday.add("12.5");
        leaveday.add("13");
        leaveday.add("13.5");
        leaveday.add("14");
        leaveday.add("14.5");
        leaveday.add("15");
        leaveday.add("15.5");
        leaveday.add("16");
        leaveday.add("16.5");
        leaveday.add("17");
        leaveday.add("17.5");
        leaveday.add("18");
        leaveday.add("18.5");
        leaveday.add("19");
        leaveday.add("19.5");
        leaveday.add("20");
        leaveday.add("20.5");
        leaveday.add("21");
        leaveday.add("21.5");
        leaveday.add("22");
        leaveday.add("22.5");
        leaveday.add("23");
        leaveday.add("23.5");
        leaveday.add("24");
        leaveday.add("24.5");
        leaveday.add("25");
        leaveday.add("25.5");
        leaveday.add("26");
        leaveday.add("26.5");
        leaveday.add("27");
        leaveday.add("27.5");
        leaveday.add("28");
        leaveday.add("28.5");
        leaveday.add("29");
        leaveday.add("29.5");
        leaveday.add("30");
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(leave_days_spinner);

            popupWindow.setHeight(leaveday.size() > 5 ? 500 : leaveday.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, leaveday);
        leave_days_spinner.setAdapter(adapterYear);
        for (int i = 0; i < leaveday.size(); i++) {
            if (editLeaveDaysStr.equalsIgnoreCase(leaveday.get(i))) {
                leave_days_spinner.setSelection(i);
                break;
            } else {
                leave_days_spinner.setSelection(0);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
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
                break;
            case R.id.btnBacktest_homework:
                fragment = new ShowLeaveFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.btnSave:
                getInsertLeaveValue();
                break;
            case R.id.btnCancel:
                leave_days_spinner.setSelection(0);
                leave_heads_spinner.setSelection(0);
                txtstartDate.setText("");
                txtendDate.setText("");
                edtPurpose.setText("");
                break;
            case R.id.txtstartDate:
                isFromDate = true;
                datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(LeaveFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.txtendDate:
                isFromDate = false;
                datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(LeaveFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                break;
        }
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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
            txtstartDate.setText(dateFinal);
        } else {
            txtendDate.setText(dateFinal);
        }
    }


    public void InsertLeaveDetail() {
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
                        params.put("LeaveID", leaveIdStr);
                        params.put("FromDate", leaveStartDateStr);
                        params.put("ToDate", leaveEndDateStr);
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        params.put("HeadID", headIdStr);
                        params.put("LeaveDays", leaveDaysStr);
                        params.put("Reason", leaveReasonStr);

                        insertLeaveAsyncTask = new InsertLeaveAsyncTask(params);
                        getleaveheadResponse = insertLeaveAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (getleaveheadResponse.getSuccess().equalsIgnoreCase("True")) {
                                    Utility.ping(mContext, "Leave added successfully");
                                    leave_days_spinner.setSelection(0);
                                    leave_heads_spinner.setSelection(0);
                                    txtstartDate.setText("");
                                    txtendDate.setText("");
                                    edtPurpose.setText("");
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

    public void getInsertLeaveValue() {
        leaveStartDateStr = txtstartDate.getText().toString();
        leaveEndDateStr = txtendDate.getText().toString();
        leaveReasonStr = edtPurpose.getText().toString();

        if (!leaveDaysStr.equalsIgnoreCase("--Select--") && !leaveStartDateStr.equalsIgnoreCase("")
                && !leaveEndDateStr.equalsIgnoreCase("") && !leaveReasonStr.equalsIgnoreCase("")
                && !headIdStr.equalsIgnoreCase("0")) {
            InsertLeaveDetail();
        } else {
            Utility.ping(mContext, "Please enter all field value");
        }
    }

    public void fillEditLEaveDetail() {
        geteditLeaveDetailStr = String.valueOf(getArguments().getSerializable("LeaveDetail"));

        Log.d("array", geteditLeaveDetailStr);
        geteditLeaveDetailStr=  geteditLeaveDetailStr.substring(1, geteditLeaveDetailStr.length()-1);
        String[] spilt = geteditLeaveDetailStr.split("\\|");

        editLeaveDaysStr = spilt[1];
        editLeaveStartDateStr = spilt[2];
        editLeaveEndDateStr = spilt[3];
        editLeaveReasonStr = spilt[4];
        editLeaveHeadStr = spilt[5];

        if (geteditLeaveDetailStr.equalsIgnoreCase("")) {
            leaveIdStr = "0";
        } else {
            leaveIdStr = spilt[0];
        }
        txtstartDate.setText(editLeaveStartDateStr);
        txtendDate.setText(editLeaveEndDateStr);
        edtPurpose.setText(editLeaveReasonStr);

    }
}
