package anandniketan.com.bhadajteacher.Adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import anandniketan.com.bhadajteacher.Models.TeacherTodayScheduleModel;
import anandniketan.com.bhadajteacher.R;


/**
 * Created by admsandroid on 9/22/2017.
 */

public class TodayscheduleAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<TeacherTodayScheduleModel> teacherTodayScheduleModels = new ArrayList<>();

    // Constructor
    public TodayscheduleAdapter(Context c, ArrayList<TeacherTodayScheduleModel> teacherTodayScheduleModels) {
        mContext = c;
        this.teacherTodayScheduleModels = teacherTodayScheduleModels;

    }

    private class ViewHolder {
        TextView lecture_txt, timing_txt, standard_txt, class_txt, subject_txt;
    }

    @Override
    public int getCount() {
        return teacherTodayScheduleModels.size();
    }

    @Override
    public Object getItem(int position) {
        return teacherTodayScheduleModels.get(position);
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
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_todayschedule, null);

            viewHolder.lecture_txt = (TextView) convertView.findViewById(R.id.lecture_txt);
            viewHolder.timing_txt = (TextView) convertView.findViewById(R.id.timing_txt);
            viewHolder.standard_txt = (TextView) convertView.findViewById(R.id.standard_txt);
            viewHolder.class_txt = (TextView) convertView.findViewById(R.id.class_txt);
            viewHolder.subject_txt = (TextView) convertView.findViewById(R.id.subject_txt);

            try {

                viewHolder.lecture_txt.setText(teacherTodayScheduleModels.get(position).getLecture());
                viewHolder.timing_txt.setText(teacherTodayScheduleModels.get(position).getTiming());
                viewHolder.standard_txt.setText(teacherTodayScheduleModels.get(position).getStandard());
                viewHolder.class_txt.setText(teacherTodayScheduleModels.get(position).getClassname());
                viewHolder.subject_txt.setText(teacherTodayScheduleModels.get(position).getSubject());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

}


