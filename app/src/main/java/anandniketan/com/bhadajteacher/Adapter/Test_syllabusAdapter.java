package anandniketan.com.bhadajteacher.Adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import anandniketan.com.bhadajteacher.Interfacess.onEditTest;
import anandniketan.com.bhadajteacher.Models.Test_SyllabusModel;
import anandniketan.com.bhadajteacher.R;


/**
 * Created by admsandroid on 9/26/2017.
 */

@SuppressWarnings("Since15")
public class Test_syllabusAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Test_SyllabusModel> test_syllabusModels = new ArrayList<>();
    FragmentManager activity;
    private onEditTest onEditTest;
    private ArrayList<String> EditTestdataCheck = new ArrayList<String>();
    private ArrayList<String> syllbusArray = new ArrayList<String>();
    private ArrayList<String> editTestData = new ArrayList<String>();

    // Constructor
    public Test_syllabusAdapter(Context c, FragmentManager activity, ArrayList<Test_SyllabusModel> test_syllabusModels, onEditTest onEditTest) {
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

            try {
                String sr = String.valueOf(position + 1);
                viewHolder.srno_txt.setText(sr);
                viewHolder.test_name_txt.setText(test_syllabusModels.get(position).getTestName().trim());
                viewHolder.grade_txt.setText(test_syllabusModels.get(position).getStandardClass());
                viewHolder.subject_txt.setText(test_syllabusModels.get(position).getSubject());


                viewHolder.edit_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditTestdataCheck.add(test_syllabusModels.get(position).getTestName() + "|" + test_syllabusModels.get(position).getTestDate() + "|"
                                + test_syllabusModels.get(position).getStandardClass() + "|" + test_syllabusModels.get(position).getSubject());
                        syllbusArray.clear();
                        for (int i = 0; i < test_syllabusModels.get(position).getGetSyllabusData().size(); i++) {
                            syllbusArray.add(test_syllabusModels.get(position).getGetSyllabusData().get(i).getSyllabus());
                        }
                        Log.d("syllbusArray", "" + syllbusArray);
                        editTestData.add(test_syllabusModels.get(position).getTSMasterID() + "|" + test_syllabusModels.get(position).getTestID() + "|" +
                                test_syllabusModels.get(position).getSubjectID() + "|" + test_syllabusModels.get(position).getSectionID());
                        onEditTest.getEditTest();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }


    public ArrayList<String> getEditData() {
        return EditTestdataCheck;
    }

    public ArrayList<String> getSyllabusDataArray() {
        return syllbusArray;
    }

    public ArrayList<String> getDataforEditTest() {
        return editTestData;
    }
}



