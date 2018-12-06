package anandniketan.com.anbcteacher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse.FinalArrayStudentSubject;
import anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse.StudentSubject;
import anandniketan.com.anbcteacher.R;


/**
 * Created by admsandroid on 11/3/2017.
 */

public class StudentGridsubjectAdapter extends BaseAdapter {
    private Context mContext;
    private FinalArrayStudentSubject studentsubjectarrayList;

    // Constructor
    public StudentGridsubjectAdapter(Context c, FinalArrayStudentSubject studentsubjectarrayList) {
        mContext = c;
        this.studentsubjectarrayList = studentsubjectarrayList;
    }

    private class ViewHolder {
        CheckBox check_subject;
    }

    @Override
    public int getCount() {
        return studentsubjectarrayList.getStudentSubject().size();
    }

    @Override
    public Object getItem(int position) {
        return studentsubjectarrayList.getStudentSubject().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_student_checkbox, null);
            viewHolder.check_subject = (CheckBox) convertView.findViewById(R.id.check_subject);
            final StudentSubject subjObj = studentsubjectarrayList.getStudentSubject().get(position);
            try {
                viewHolder.check_subject.setText(subjObj.getSubject());
                viewHolder.check_subject.setTag(subjObj.getSubjectID());
                if (subjObj.getCheckedStatus().equalsIgnoreCase("1")) {
                    viewHolder.check_subject.setChecked(true);
                } else {
                    viewHolder.check_subject.setChecked(false);
                }

                viewHolder.check_subject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            subjObj.setCheckedStatus("1");
                        } else {
                            subjObj.setCheckedStatus("0");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

}
