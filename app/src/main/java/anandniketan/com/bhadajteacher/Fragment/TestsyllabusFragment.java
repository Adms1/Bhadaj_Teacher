package anandniketan.com.bhadajteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import anandniketan.com.bhadajteacher.Adapter.EditTestDetailsListAdapter;
import anandniketan.com.bhadajteacher.Adapter.Test_syllabusAdapter;
import anandniketan.com.bhadajteacher.Adapter.TestsyllabusListAdapter;
import anandniketan.com.bhadajteacher.AsyncTasks.TeacherGetTestSyllabusAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.TeacherUpdateTestDetailAsyncTask;
import anandniketan.com.bhadajteacher.Interfacess.onEditTest;
import anandniketan.com.bhadajteacher.Models.Test_SyllabusModel;
import anandniketan.com.bhadajteacher.Models.UpdateTestDetailModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class TestsyllabusFragment extends Fragment {
    private View rootView;
    private TextView txtNoRecordstest;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private TeacherGetTestSyllabusAsyncTask teacherGetTestSyllabusAsyncTask = null;
    private ArrayList<Test_SyllabusModel> test_syllabusModels = new ArrayList<>();
    private ListView test_syllabus_list;
    Test_syllabusAdapter test_syllabusAdapter = null;
    TestsyllabusListAdapter testsyllabusListAdapter = null;
    private LinearLayout test_header;
    FragmentManager fragmentManager;
    //use for Dialog
    private AlertDialog alertDialogAndroid = null;
    private Button close_btn, Edit_btn;
    private TextView edit_test_txt, edit_test_date_txt, edit_test_grade_txt, edit_test_subject_txt;
    //    private ListView listData;
    int Year, Month, Day;
    int mYear, mMonth, mDay;
    Calendar calendar;
    private DatePickerDialog datePickerDialog;
    ArrayList<String> syllbusarray = new ArrayList<>();
    String spiltStr, splitarray;
    //use for fill Listdata
    EditTestDetailsListAdapter editTestDetailsListAdapter;
    ArrayList<String> text;

    private TeacherUpdateTestDetailAsyncTask updateTestDetailAsyncTask = null;
    UpdateTestDetailModel updateTestDetailModel;
    String TSMasterIDstr, TestIDstr, TestDatestr, SubjectIDstr, SectionIDstr, finalTxtstr;

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
        txtNoRecordstest = (TextView) rootView.findViewById(R.id.txtNoRecordstest);
        test_syllabus_list = (ListView) rootView.findViewById(R.id.test_syllabus_list);
        test_header = (LinearLayout) rootView.findViewById(R.id.test_header);

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
                        test_syllabusModels = teacherGetTestSyllabusAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (test_syllabusModels.size() > 0) {
                                    txtNoRecordstest.setVisibility(View.GONE);
                                    testsyllabusListAdapter = new TestsyllabusListAdapter(getActivity(), getActivity().getFragmentManager(), test_syllabusModels, new onEditTest() {
                                        @Override
                                        public void getEditTest() {
                                            EditTestSubmit();
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

    public void EditTestSubmit() {
        String arrayvalue = String.valueOf(testsyllabusListAdapter.getDataforEditTest());
        Log.d(" arrayvalue", arrayvalue);
        arrayvalue = arrayvalue.substring(1, arrayvalue.length() - 1);
        Log.d(" afterarrayvalue", arrayvalue);
        String[] splitarrayvalue = arrayvalue.split("\\|");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse(splitarrayvalue[4]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TestDatestr = output.format(d);
        TSMasterIDstr = splitarrayvalue[0];
        TestIDstr = splitarrayvalue[1];
        SubjectIDstr = splitarrayvalue[2];
        SectionIDstr = splitarrayvalue[3];

        String valueString=testsyllabusListAdapter.getEditStr();
        Log.d("valueString",valueString);
        valueString=valueString.substring(1,valueString.length()-1);
        Log.d("aftervalueString",valueString);
        finalTxtstr =valueString;

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
                        params.put("TestID", TestIDstr);
                        params.put("TestDate", TestDatestr);
                        params.put("SubjectID", SubjectIDstr);
                        params.put("SectionID", SectionIDstr);
                        params.put("Arydetail", finalTxtstr);

                        updateTestDetailAsyncTask = new TeacherUpdateTestDetailAsyncTask(params);
                        updateTestDetailModel = updateTestDetailAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (updateTestDetailModel.getSuccess().equalsIgnoreCase("True")) {
                                    progressDialog.dismiss();
                                    getTestSyllabusData();
                                } else {
                                    progressDialog.dismiss();
                                    Utility.ping(mContext, "Something error");
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

