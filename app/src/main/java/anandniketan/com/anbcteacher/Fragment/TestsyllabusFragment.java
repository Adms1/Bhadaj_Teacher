package anandniketan.com.anbcteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Adapter.EditTestDetailsListAdapter;
import anandniketan.com.anbcteacher.Adapter.TestsyllabusListAdapter;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherGetTestSyllabusAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherUpdateTestDetailAsyncTask;
import anandniketan.com.anbcteacher.Interfacess.onEditTest;
import anandniketan.com.anbcteacher.Models.TestModel.FinalArrayTestDataModel;
import anandniketan.com.anbcteacher.Models.TestModel.GetEditTestModel;
import anandniketan.com.anbcteacher.Models.TestModel.UpdateTestDetailModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;


public class TestsyllabusFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    GetEditTestModel editTestResponse;
    TestsyllabusListAdapter testsyllabusListAdapter = null;
    FragmentManager fragmentManager;
    int Year, Month, Day;
    int mYear, mMonth, mDay;
    Calendar calendar;
    ArrayList<String> syllbusarray = new ArrayList<String>();
    String finalStr, editString;
    //use for fill Listdata
    EditTestDetailsListAdapter editTestDetailsListAdapter;
    ArrayList<String> text;
    UpdateTestDetailModel updateTestDetailModel;
    String TSMasterIDstr, TestNamestr, TestDatestr, SubjectIDstr, SectionIDstr, finalTxtstr, StandardIDStr;
    private View rootView;
    private TextView txtNoRecordstest;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private TeacherGetTestSyllabusAsyncTask teacherGetTestSyllabusAsyncTask = null;
    private ListView test_syllabus_list;
    private LinearLayout test_header;
    //use for Dialog
    private AlertDialog alertDialogAndroid = null;
    private Button close_btn, Edit_btn;
    private TextView edit_test_txt, edit_test_date_txt, edit_test_grade_txt, edit_test_subject_txt;
    private ListView listData;
    private DatePickerDialog datePickerDialog;
    private TeacherUpdateTestDetailAsyncTask updateTestDetailAsyncTask = null;
    //fill listview
    private ArrayList<FinalArrayTestDataModel> listEditTestData;

    public TestsyllabusFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_testsyllabus, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        return rootView;
    }

    public void initViews() {
        txtNoRecordstest = rootView.findViewById(R.id.txtNoRecordstest);
        test_syllabus_list = rootView.findViewById(R.id.test_syllabus_list);
        test_header = rootView.findViewById(R.id.test_header);

        setUserVisibleHint(true);
    }

    public void setListners() {
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            getTestSyllabusData();
        }
        // execute your data loading logic.
    }

    public void getTestSyllabusData() {
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
                        teacherGetTestSyllabusAsyncTask = new TeacherGetTestSyllabusAsyncTask(params);
                        editTestResponse = teacherGetTestSyllabusAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (editTestResponse.getFinalArray().size() > 0) {
                                    txtNoRecordstest.setVisibility(View.GONE);
                                    listEditTestData = new ArrayList<FinalArrayTestDataModel>();
                                    for (int i = 0; i < editTestResponse.getFinalArray().size(); i++) {
                                        listEditTestData.add(editTestResponse.getFinalArray().get(i));
                                    }
                                    testsyllabusListAdapter = new TestsyllabusListAdapter(getActivity(), listEditTestData, editTestResponse, new onEditTest() {
                                        @Override
                                        public void getEditTest() {
                                            Dialog();
                                        }
                                    });
                                    test_syllabus_list.setAdapter(testsyllabusListAdapter);
                                    test_syllabus_list.deferNotifyDataSetChanged();
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordstest.setVisibility(View.VISIBLE);
                                    test_header.setVisibility(View.GONE);
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

    public void Dialog() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.list_edit_row, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alertDialogAndroid.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.show();


        close_btn = layout.findViewById(R.id.close_btn);
        Edit_btn = layout.findViewById(R.id.Edit_btn);
        edit_test_txt = layout.findViewById(R.id.edit_test_txt);
        edit_test_date_txt = layout.findViewById(R.id.edit_test_date_txt);
        edit_test_grade_txt = layout.findViewById(R.id.edit_test_grade_txt);
        edit_test_subject_txt = layout.findViewById(R.id.edit_test_subject_txt);
        listData = layout.findViewById(R.id.listData);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        String SetValue = testsyllabusListAdapter.getSetData().toString();
        Log.d("setValue", SetValue);
        String[] setValueArray = SetValue.split("\\|");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse(setValueArray[3]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(d);
        finalStr = "";
        finalStr = setValueArray[0].substring(1, setValueArray[0].length() - 0);
        edit_test_txt.setText(finalStr);
        edit_test_date_txt.setText(formattedTime);
        edit_test_grade_txt.setText(setValueArray[1]);
        edit_test_subject_txt.setText(setValueArray[2]);

        ArrayList<String> number = new ArrayList<String>();
        syllbusarray.clear();
        String value;
        for (int i = 0; i < 10; i++) {
            number.add(String.valueOf(i));
            if (i < testsyllabusListAdapter.syllbusArray().size()) {
                value = testsyllabusListAdapter.syllbusArray().get(i);
                value = value.replaceFirst("\\[", "");
                syllbusarray.add(value);
                Log.d("ADDsyllbusarray", value);
//                syllbusarray.add(testsyllabusListAdapter.syllbusArray().get(i));
            } else {
                syllbusarray.add("");
            }
        }
//        for (int j = 0; j < syllbusarray.size(); j++) {
//            syllbusDataResponse.set(j,syllbusarray.get(j))
//        }
        Log.d("number", "" + number);
        Log.d("syllbusarray", "" + syllbusarray);

        editTestDetailsListAdapter = new EditTestDetailsListAdapter(mContext, syllbusarray, number);
        listData.setAdapter(editTestDetailsListAdapter);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogAndroid.dismiss();
            }
        });
        edit_test_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = DatePickerDialog.newInstance(TestsyllabusFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        Edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtstr = "";
                text = new ArrayList<String>();
                for (int i = 0; i < listData.getChildCount(); i++) {
                    View mView = listData.getChildAt(i);
                    EditText myEditText = mView.findViewById(R.id.syllabus_txt);
                    if (!myEditText.getText().toString().trim().equalsIgnoreCase("")) {
                        txtstr = txtstr + myEditText.getText().toString() + "|&";
                    }
                }
                text.add(txtstr);
                Log.d("EditValue", text.toString());

//                for (int i = 0; i < editTestDetailsListAdapter.getCount(); i++) {
//                    FinalArrayTestDataModel testObj = (FinalArrayTestDataModel) editTestDetailsListAdapter.getItem(i);
//                    int test=testObj.getTestSyllabus().size();
//                   for(int j=0;j<test;j++){
//                       TestSyllabusModel testtype=testObj.getTestSyllabus().get(j);
//                       txtstr=txtstr+testtype.getSyllabus()+"|&";
//                   }
//                    text.add(txtstr);
//                }
//
//                Log.d("EditValue", text.toString());
////                EditTestSubmit();

//                for (int i = 0; i < editTestDetailsListAdapter.syllbusarrayList().size(); i++) {
//                    String data = editTestDetailsListAdapter.syllbusarrayList().get(i);
//                    text.add(data);
//                }
//
//                Log.d("EditValue", text.toString());
                EditTestSubmit();
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
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

        edit_test_date_txt.setText(d + "/" + m + "/" + y);
    }

    public void EditTestSubmit() {
        String arrayvalue = String.valueOf(testsyllabusListAdapter.getDataforEditTest());
        Log.d(" arrayvalue", arrayvalue);
        arrayvalue = arrayvalue.substring(1, arrayvalue.length() - 1);
        String[] splitarrayvalue = arrayvalue.split("\\|");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse(splitarrayvalue[4]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TestDatestr = edit_test_date_txt.getText().toString();
        TSMasterIDstr = splitarrayvalue[0];
        TestNamestr = splitarrayvalue[1];
        SubjectIDstr = splitarrayvalue[2];
        SectionIDstr = splitarrayvalue[3];
        StandardIDStr = splitarrayvalue[5];

        String valueString = text.toString();
        Log.d("valueString", valueString);
        valueString = valueString.substring(1, valueString.length() - 1);
        Log.d("aftervalueString", valueString);
        finalTxtstr = valueString;

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
                        params.put("TSMasterID", TSMasterIDstr);
                        params.put("TestName", TestNamestr);
                        params.put("TestDate", TestDatestr);
                        params.put("SubjectID", SubjectIDstr);
                        params.put("SectionID", SectionIDstr);
                        params.put("Arydetail", finalTxtstr);
                        params.put("StandardID", StandardIDStr);
                        updateTestDetailAsyncTask = new TeacherUpdateTestDetailAsyncTask(params);
                        updateTestDetailModel = updateTestDetailAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(updateTestDetailModel != null) {
                                    if (updateTestDetailModel.getSuccess().equalsIgnoreCase("True")) {
                                        progressDialog.dismiss();
                                        alertDialogAndroid.dismiss();
                                        getTestSyllabusData();
                                    } else {
                                        progressDialog.dismiss();
                                        Utility.ping(mContext, updateTestDetailModel.getMessage());
                                    }
                                }else{
                                    Utility.ping(mContext, updateTestDetailModel.getMessage());
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



