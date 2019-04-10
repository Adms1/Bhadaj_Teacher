package anandniketan.com.anbcteacher.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import anandniketan.com.anbcteacher.Models.HomeWorkResponse.FinalArrayhomeworkstatus;
import anandniketan.com.anbcteacher.Models.HomeWorkResponse.TeacherStudentHomeworkStatusModel;
import anandniketan.com.anbcteacher.R;


/**
 * Created by admsandroid on 11/7/2017.
 */

public class HomeWorkStatusListAdapter extends BaseAdapter {
    private Context mContext;
    private TeacherStudentHomeworkStatusModel studentsubjectarrayList;

    // Constructor
    public HomeWorkStatusListAdapter(Context c, TeacherStudentHomeworkStatusModel studentsubjectarrayList) {
        mContext = c;
        this.studentsubjectarrayList = studentsubjectarrayList;

    }

    private class ViewHolder {
        TextView homework_status_student_name_txt;
        //        CheckBox done_chk, pendding_chk;
        RadioGroup homework_group;
        RadioButton done_chk, pendding_chk;
    }

    @Override
    public int getCount() {
        return studentsubjectarrayList.getFinalArray().size();
    }

    @Override
    public Object getItem(int position) {
        return studentsubjectarrayList.getFinalArray().get(position);
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
            convertView = mInflater.inflate(R.layout.list_adapter_item, null);
            viewHolder.homework_status_student_name_txt = convertView.findViewById(R.id.homework_status_student_name_txt);
            viewHolder.done_chk = convertView.findViewById(R.id.done_chk);
            viewHolder.pendding_chk = convertView.findViewById(R.id.pendding_chk);
            viewHolder.homework_group = convertView.findViewById(R.id.homework_group);

            final FinalArrayhomeworkstatus subjObj = studentsubjectarrayList.getFinalArray().get(position);

            try {
                viewHolder.homework_status_student_name_txt.setText(subjObj.getStudentName());

                viewHolder.homework_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        RadioButton rb = group.findViewById(checkedId);
                        if (null != rb && checkedId > -1) {

                            // checkedId is the RadioButton selected
                            switch (checkedId) {
                                case R.id.done_chk:
                                    subjObj.setHomeWorkStatus("1");
                                    break;

                                case R.id.pendding_chk:
                                    subjObj.setHomeWorkStatus("0");
                                    break;
                            }

                        }
                    }
                });

                switch (Integer.parseInt(subjObj.getHomeWorkStatus())) {
                    case 0:
                        viewHolder.pendding_chk.setChecked(true);
                        break;
                    case 1:
                        viewHolder.done_chk.setChecked(true);
                        break;
                    case -1:
                        viewHolder.done_chk.setChecked(true);
                        break;
                    default:
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

}

