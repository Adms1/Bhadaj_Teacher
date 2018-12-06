package anandniketan.com.anbcteacher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse.TeacherAssignedSubjectModel;
import anandniketan.com.anbcteacher.R;

/**
 * Created by admsandroid on 9/25/2017.
 */

public class MysubjectAdapetr extends BaseAdapter {
    private Context mContext;
    private ArrayList<TeacherAssignedSubjectModel> teacherAssignedSubjectModels = new ArrayList<>();

    // Constructor
    public MysubjectAdapetr(Context c, ArrayList<TeacherAssignedSubjectModel> teacherAssignedSubjectModels) {
        mContext = c;
        this.teacherAssignedSubjectModels = teacherAssignedSubjectModels;

    }

    private class ViewHolder {
        TextView standard_txt, class_txt, subject_txt;
    }

    @Override
    public int getCount() {
        return teacherAssignedSubjectModels.size();
    }

    @Override
    public Object getItem(int position) {
        return teacherAssignedSubjectModels.get(position);
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
            convertView = mInflater.inflate(R.layout.list_row_mysubject, null);

            viewHolder.standard_txt = (TextView) convertView.findViewById(R.id.standard_txt);
            viewHolder.class_txt = (TextView) convertView.findViewById(R.id.class_txt);
            viewHolder.subject_txt = (TextView) convertView.findViewById(R.id.subject_txt);

            try {
                viewHolder.standard_txt.setText(teacherAssignedSubjectModels.get(position).getStandard());
                viewHolder.class_txt.setText(teacherAssignedSubjectModels.get(position).getClassname());
                viewHolder.subject_txt.setText(teacherAssignedSubjectModels.get(position).getSubject());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

}



