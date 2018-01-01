package anandniketan.com.bhadajteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import anandniketan.com.bhadajteacher.Adapter.ListAdapterCreate;
import anandniketan.com.bhadajteacher.AsyncTasks.PTMTeacherStudentInsertDetailAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.TeacherGetClassSubjectWiseStudentAsyncTask;
import anandniketan.com.bhadajteacher.Interfacess.onCheckBoxChnage;
import anandniketan.com.bhadajteacher.Models.MainPtmSentMessageResponse;
import anandniketan.com.bhadajteacher.Models.PTMCreateResponse.FinalArrayStudentForCreate;
import anandniketan.com.bhadajteacher.Models.PTMCreateResponse.MainResponseDisplayStudent;
import anandniketan.com.bhadajteacher.Models.PTMCreateResponse.StudentDatum;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class CreateFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private View rootView;
    private TextView txtNoRecordsCreate, txtNoRecordsCreateStudent;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private TeacherGetClassSubjectWiseStudentAsyncTask getClassSubjectWiseStudentAsyncTask = null;
    private LinearLayout Create_header, Create_class_linear;
    private Spinner Create_class_spinner;
    private ListView lvCreate;
    private ImageView insert_message_img;
    private AlertDialog alertDialogAndroid = null;
    private Button close_btn, send_btn;
    private static TextView insert_message_date_txt;
    private EditText insert_message_subject_txt, insert_message_Message_txt;
    private DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    Calendar calendar;
    int mYear, mMonth, mDay;
    ListAdapterCreate listAdapterCreate;
    private ArrayList<StudentDatum> arrayList;
    String spinnerSelectedValue, value;
    MainResponseDisplayStudent response;

    private PTMTeacherStudentInsertDetailAsyncTask getPTMTeacherStudentInsertDetailAsyncTask = null;
    MainPtmSentMessageResponse mainPtmSentMessageResponse;
    String finalStudentArray;
    private static String dateFinal;

    public CreateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create, container, false);
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

        txtNoRecordsCreate = (TextView) rootView.findViewById(R.id.txtNoRecordsCreate);
        txtNoRecordsCreateStudent = (TextView) rootView.findViewById(R.id.txtNoRecordsCreateStudent);
        Create_header = (LinearLayout) rootView.findViewById(R.id.Create_header);
        Create_class_linear = (LinearLayout) rootView.findViewById(R.id.Create_class_linear);
        Create_class_spinner = (Spinner) rootView.findViewById(R.id.Create_class_spinner);
        lvCreate = (ListView) rootView.findViewById(R.id.lvCreate);
        insert_message_img = (ImageView) rootView.findViewById(R.id.insert_message_img);


        setUserVisibleHint(true);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            getStudentData();
        }
        // execute your data loading logic.
    }

    public void setListners() {
        Create_class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                spinnerSelectedValue = adapterView.getItemAtPosition(j).toString();
                Log.d("spinner", spinnerSelectedValue);
                String[] array = spinnerSelectedValue.split("-");
                String value = array[2].replaceFirst(">", "");
                Log.d("Array", Arrays.toString(array));
                Log.d("Array1", value);
                List<FinalArrayStudentForCreate> filterFinalArray = new ArrayList<FinalArrayStudentForCreate>();
                for (FinalArrayStudentForCreate arrayObj : response.getFinalArraycreate()) {
                    if (arrayObj.getStandard().equalsIgnoreCase(array[0].trim()) &&
                            arrayObj.getClassname().equalsIgnoreCase(array[1].trim()) && arrayObj.getSubject().equalsIgnoreCase(value.trim())) {
                        filterFinalArray.add(arrayObj);
                    }
                }
                setExpandableListView(filterFinalArray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        insert_message_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendMessage();
            }
        });
    }

    public void getStudentData() {
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
                        getClassSubjectWiseStudentAsyncTask = new TeacherGetClassSubjectWiseStudentAsyncTask(params);
                        response = getClassSubjectWiseStudentAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (response.getFinalArraycreate().size() > 0) {
                                    txtNoRecordsCreate.setVisibility(View.GONE);
                                    fillspinner();
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsCreate.setVisibility(View.VISIBLE);
                                    Create_header.setVisibility(View.GONE);
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

    private void setExpandableListView(List<FinalArrayStudentForCreate> array) {
        arrayList = new ArrayList<>();
        arrayList.clear();

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getStudentData().size() > 0) {
                Create_header.setVisibility(View.VISIBLE);
                txtNoRecordsCreateStudent.setVisibility(View.GONE);
                insert_message_img.setVisibility(View.GONE);
                for (int j = 0; j < array.get(i).getStudentData().size(); j++) {
                    arrayList.add(array.get(i).getStudentData().get(j));
                }

                Log.d("arrayList", "" + arrayList.size());
            } else {
                txtNoRecordsCreateStudent.setVisibility(View.VISIBLE);
                Create_header.setVisibility(View.GONE);
                insert_message_img.setVisibility(View.GONE);
            }
        }
        for (int k = 0; k < arrayList.size(); k++) {
            arrayList.get(k).setCheck("0");
        }
        listAdapterCreate = new ListAdapterCreate(getActivity(), arrayList, getActivity().getFragmentManager(), new onCheckBoxChnage() {
            @Override
            public void getChecked() {
                insert_message_img.setVisibility(View.GONE);
                ArrayList<StudentDatum> updatedData = listAdapterCreate.getDatas();
                Boolean data = false;
                for (int i = 0; i < updatedData.size(); i++) {
                    if (updatedData.get(i).getCheck().equalsIgnoreCase("1")) {
                        data = true;
                        Log.d("Position , Checked or not", "" + i + " : " + updatedData.get(i).getCheck());
                    }
                }
                if (data) {
                    insert_message_img.setVisibility(View.VISIBLE);
                } else {
                    insert_message_img.setVisibility(View.GONE);
                }
            }
        });
        lvCreate.setAdapter(listAdapterCreate);
        lvCreate.deferNotifyDataSetChanged();

    }

    public void fillspinner() {
        ArrayList<String> row = new ArrayList<String>();

        for (int z = 0; z < response.getFinalArraycreate().size(); z++) {
            row.add(response.getFinalArraycreate().get(z).getStandard() + "-" +
                    response.getFinalArraycreate().get(z).getClassname() + "->" +
                    response.getFinalArraycreate().get(z).getSubject());
        }
        HashSet hs = new HashSet();
        hs.addAll(row);
        row.clear();
        row.addAll(hs);
        Log.d("marks", "" + row);

        Collections.sort(row);
        System.out.println("Sorted ArrayList in Java - Ascending order : " + row);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(Create_class_spinner);

            popupWindow.setHeight(row.size() > 5 ? 500 : row.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, row);
        Create_class_spinner.setAdapter(adapterYear);
    }

    public void SendMessage() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.insert_message_item, null);

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

        insert_message_date_txt = (TextView) layout.findViewById(R.id.insert_message_date_txt);
        insert_message_subject_txt = (EditText) layout.findViewById(R.id.insert_message_subject_txt);
        insert_message_Message_txt = (EditText) layout.findViewById(R.id.insert_message_Message_txt);
        send_btn = (Button) layout.findViewById(R.id.send_message_btn);
        close_btn = (Button) layout.findViewById(R.id.close_btn);

        insert_message_date_txt.setText(Utility.getTodaysDate());


        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });

        insert_message_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(CreateFragment.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setOkText("Done");
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> id = new ArrayList<>();
                final String messageDate, messageSubject, messageMessageLine;
                ArrayList<String> array = listAdapterCreate.getData();
                for (int j = 0; j < array.size(); j++) {
                    id.add(array.get(j).toString());
                }
                finalStudentArray = String.valueOf(id);
                finalStudentArray = finalStudentArray.substring(1, finalStudentArray.length() - 1);
                messageDate = insert_message_date_txt.getText().toString();
                messageSubject = insert_message_subject_txt.getText().toString();
                messageMessageLine = insert_message_Message_txt.getText().toString();
                Log.d("StudentArray", "" + id);


                if (Utility.isNetworkConnected(mContext)) {
                    if (!finalStudentArray.equalsIgnoreCase("") && !messageDate.equalsIgnoreCase("") &&
                            !messageSubject.equalsIgnoreCase("") && !messageMessageLine.equalsIgnoreCase("")) {
                        progressDialog = new ProgressDialog(mContext);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();


                        Log.d("finalStudentArray", finalStudentArray);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    HashMap<String, String> params = new HashMap<String, String>();
                                    params.put("MessageID", "0");
                                    params.put("FromID", Utility.getPref(mContext, "StaffID"));
                                    params.put("ToID", finalStudentArray);
                                    params.put("MeetingDate", messageDate);
                                    params.put("SubjectLine", messageSubject);
                                    params.put("Description", messageMessageLine);

                                    getPTMTeacherStudentInsertDetailAsyncTask = new PTMTeacherStudentInsertDetailAsyncTask(params);
                                    mainPtmSentMessageResponse = getPTMTeacherStudentInsertDetailAsyncTask.execute().get();
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            if (mainPtmSentMessageResponse.getFinalArray().size() >= 0) {
                                                Utility.ping(mContext, "Send Sucessfully");
                                                alertDialogAndroid.dismiss();
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
                        Utility.ping(mContext, "Blank field not allowed.");
                    }
                } else {
                    Utility.ping(mContext, "Network not available");
                }
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

        insert_message_date_txt.setText(d + "/" + m + "/" + y);
    }
}
