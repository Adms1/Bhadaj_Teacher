package anandniketan.com.anbcteacher.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import anandniketan.com.anbcteacher.AsyncTasks.TeacherGetTestSyllabusAsyncTask;
import anandniketan.com.anbcteacher.Interfacess.onEditTest;
import anandniketan.com.anbcteacher.Models.TestModel.FinalArrayTestDataModel;
import anandniketan.com.anbcteacher.Models.TestModel.GetEditTestModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class TestsyllabusListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FinalArrayTestDataModel> test_syllabusModels = new ArrayList<>();
    FragmentManager activity;
    private ArrayList<String> editTestData = new ArrayList<String>();
    private ArrayList<String> setData = new ArrayList<String>();
    private ArrayList<String> syllbusarray = new ArrayList<String>();
    private onEditTest onEditTest;


    // Constructor
    public TestsyllabusListAdapter(Context c, ArrayList<FinalArrayTestDataModel> test_syllabusModels, GetEditTestModel editTestResponse, onEditTest onEditTest) {
        mContext = c;
        this.test_syllabusModels = test_syllabusModels;
        this.activity = activity;
        this.onEditTest = onEditTest;
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

            Glide.with(mContext)
                    .load(AppConfiguration.DOMAIN_LIVE_ICONS+"Edit.png")
                    .apply(new RequestOptions()
                            .fitCenter()
                            .placeholder(R.drawable.profile_pic_holder)
                    )
                    .into(viewHolder.edit_txt);
            try {
                String sr = String.valueOf(position + 1);
                try {
                    String date= test_syllabusModels.get(position).getTestDate();
                    SimpleDateFormat spf=new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
                    Date newDate=spf.parse(date);
                    spf= new SimpleDateFormat("dd,MMM");
                    date = spf.format(newDate);
                    viewHolder.srno_txt.setText(date);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }

                viewHolder.test_name_txt.setText(test_syllabusModels.get(position).getTestName().trim());
                viewHolder.grade_txt.setText(test_syllabusModels.get(position).getStandardClass());
                viewHolder.subject_txt.setText(test_syllabusModels.get(position).getSubject());

                viewHolder.edit_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setData.clear();
                        syllbusarray.clear();
                        setData.add(test_syllabusModels.get(position).getTestName() + "|" +
                                test_syllabusModels.get(position).getStandardClass() + "|" +
                                test_syllabusModels.get(position).getSubject() + "|" +
                                test_syllabusModels.get(position).getTestDate());
                        editTestData.add(test_syllabusModels.get(position).getTSMasterID() + "|" + test_syllabusModels.get(position).getTestName() + "|" +
                                test_syllabusModels.get(position).getSubjectID() + "|" + test_syllabusModels.get(position).getSectionID() + "|" +
                                test_syllabusModels.get(position).getTestDate()+"|"+test_syllabusModels.get(position).getStandardID());
                        for (int i = 0; i < test_syllabusModels.get(position).getTestSyllabus().size(); i++) {
                            syllbusarray.add(test_syllabusModels.get(position).getTestSyllabus().get(i).getSyllabus());
                            Log.d("firsttimesyllbusarray",syllbusarray.toString());
                        }
                        onEditTest.getEditTest();
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

    public ArrayList<String> getSetData() {
        return setData;
    }

    public ArrayList<String> syllbusArray() {
        return syllbusarray;
    }
}




