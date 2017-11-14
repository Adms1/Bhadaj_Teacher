package anandniketan.com.bhadajteacher.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import anandniketan.com.bhadajteacher.AsyncTasks.TeacherGetTestSyllabusAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.TeacherUpdateTestDetailAsyncTask;
import anandniketan.com.bhadajteacher.Interfacess.CallBack;
import anandniketan.com.bhadajteacher.Interfacess.onEditTest;
import anandniketan.com.bhadajteacher.Models.Test_SyllabusModel;
import anandniketan.com.bhadajteacher.Models.UpdateTestDetailModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class TestsyllabusListAdapter extends BaseAdapter implements DatePickerDialog.OnDateSetListener {
    private Context mContext;
    private ArrayList<Test_SyllabusModel> test_syllabusModels = new ArrayList<>();
    AlertDialog alertDialogAndroid = null;
    private DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    Calendar calendar;
    int mYear, mMonth, mDay;
    Button close_btn, Edit_btn;
    private static TextView edit_test_txt, edit_test_date_txt, edit_test_grade_txt, edit_test_subject_txt;
    ListView listData;
    FragmentManager activity;
    private ArrayList<String> text = new ArrayList<>();
    private TeacherGetTestSyllabusAsyncTask teacherGetTestSyllabusAsyncTask = null;
    EditTestDetailsListAdapter editTestDetailsListAdapter;
    ArrayList<String> syllbusarray = new ArrayList<>();

    private ArrayList<String> editTestData = new ArrayList<String>();
    private String editString = new String();
    private onEditTest onEditTest;

    // Constructor
    public TestsyllabusListAdapter(Context c, FragmentManager activity, ArrayList<Test_SyllabusModel> test_syllabusModels, onEditTest onEditTest) {
        mContext = c;
        this.test_syllabusModels = test_syllabusModels;
        this.activity = activity;
        this.onEditTest = onEditTest;
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

        edit_test_date_txt.setText(d + "/" + m + "/" + y);
    }


    private class ViewHolder {
        TextView srno_txt, test_name_txt, grade_txt, subject_txt;
        ImageView edit_txt;
    }

    @Override
    public int getCount() {
        return test_syllabusModels.size();
    }

    @Override
    public Object getItem(int position) {
        return test_syllabusModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            final LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_test_syllabus, null);

            viewHolder.srno_txt = (TextView) convertView.findViewById(R.id.srno_txt);
            viewHolder.test_name_txt = (TextView) convertView.findViewById(R.id.test_name_txt);
            viewHolder.grade_txt = (TextView) convertView.findViewById(R.id.grade_txt);
            viewHolder.edit_txt = (ImageView) convertView.findViewById(R.id.edit_txt);
            viewHolder.subject_txt = (TextView) convertView.findViewById(R.id.subject_txt);

            try {
                String sr = String.valueOf(position + 1);
                viewHolder.srno_txt.setText(sr);
                viewHolder.test_name_txt.setText(test_syllabusModels.get(position).getTestName().trim());
                viewHolder.grade_txt.setText(test_syllabusModels.get(position).getStandardClass());
                viewHolder.subject_txt.setText(test_syllabusModels.get(position).getSubject());

                viewHolder.edit_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                        WindowManager.LayoutParams wlp = window.getAttributes();

                        wlp.gravity = Gravity.CENTER;
                        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                        window.setAttributes(wlp);
                        alertDialogAndroid.show();


                        close_btn = (Button) layout.findViewById(R.id.close_btn);
                        Edit_btn = (Button) layout.findViewById(R.id.Edit_btn);
                        edit_test_txt = (TextView) layout.findViewById(R.id.edit_test_txt);
                        edit_test_date_txt = (TextView) layout.findViewById(R.id.edit_test_date_txt);
                        edit_test_grade_txt = (TextView) layout.findViewById(R.id.edit_test_grade_txt);
                        edit_test_subject_txt = (TextView) layout.findViewById(R.id.edit_test_subject_txt);
                        listData = (ListView) layout.findViewById(R.id.listData);

                        calendar = Calendar.getInstance();
                        Year = calendar.get(Calendar.YEAR);
                        Month = calendar.get(Calendar.MONTH);
                        Day = calendar.get(Calendar.DAY_OF_MONTH);

                        close_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialogAndroid.dismiss();
//                                getTestSyllabusData();
                            }
                        });

                        Edit_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String txtstr = "";
                                text = new ArrayList<String>();
                                for (int i = 0; i < listData.getChildCount(); i++) {

                                    View mView = listData.getChildAt(i);
                                    EditText myEditText = (EditText) mView.findViewById(R.id.syllabus_txt);
                                    if (!myEditText.getText().toString().trim().equalsIgnoreCase("")) {
                                        txtstr = txtstr + myEditText.getText().toString() + "|&";
                                    }
                                }
                                text.add(txtstr);
                                String textstr = text.toString();
                                Log.d("join", "" + textstr.toString());
                                editString = textstr.toString();
                                editTestData.add(test_syllabusModels.get(position).getTSMasterID() + "|" + test_syllabusModels.get(position).getTestID() + "|" +
                                        test_syllabusModels.get(position).getSubjectID() + "|" + test_syllabusModels.get(position).getSectionID() + "|" +
                                        test_syllabusModels.get(position).getTestDate());
                                onEditTest.getEditTest();
                            }
                        });
                        edit_test_date_txt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                datePickerDialog = DatePickerDialog.newInstance(TestsyllabusListAdapter.this, Year, Month, Day);
                                datePickerDialog.setThemeDark(false);
                                datePickerDialog.setOkText("Done");
                                datePickerDialog.showYearPickerFirst(false);
                                datePickerDialog.setAccentColor(Color.parseColor("#1B88C8"));
                                datePickerDialog.setTitle("Select Date From DatePickerDialog");
                                datePickerDialog.show(activity, "DatePickerDialog");
                            }
                        });

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
                        Date d = null;
                        try {
                            d = sdf.parse(test_syllabusModels.get(position).getTestDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String formattedTime = output.format(d);

                        edit_test_txt.setText(Html.fromHtml(test_syllabusModels.get(position).getTestName()));
                        edit_test_date_txt.setText(formattedTime);
                        edit_test_grade_txt.setText(Html.fromHtml(test_syllabusModels.get(position).getStandardClass()));
                        edit_test_subject_txt.setText(Html.fromHtml(test_syllabusModels.get(position).getSubject()));
                        ArrayList<String> number = new ArrayList<String>();
                        syllbusarray.clear();
                        String value;
                        for (int i = 0; i < 10; i++) {
                            number.add(String.valueOf(i));
                            if (i < test_syllabusModels.get(position).getGetSyllabusData().size()) {
                                value = test_syllabusModels.get(position).getGetSyllabusData().get(i).getSyllabus();
                                value = value.replaceFirst("\\[", "");
                                Log.d("value", value);
                                syllbusarray.add(value);
                            } else {
                                syllbusarray.add("");
                            }
                        }
                        Log.d("number", "" + number);
                        Log.d("syllbusarray", "" + syllbusarray);

                        editTestDetailsListAdapter = new EditTestDetailsListAdapter(mContext, syllbusarray, number);
                        listData.setAdapter(editTestDetailsListAdapter);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    public ArrayList<String> getDataforEditTest() {
        return editTestData;
    }

    public String getEditStr() {
        return editString;
    }
}




