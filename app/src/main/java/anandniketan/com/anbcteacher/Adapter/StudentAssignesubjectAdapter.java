package anandniketan.com.anbcteacher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse.MainResponseStudentSubject;
import anandniketan.com.anbcteacher.R;

/**
 * Created by admsandroid on 9/25/2017.
 */

public class StudentAssignesubjectAdapter extends BaseAdapter {
    private Context mContext;
    private MainResponseStudentSubject studentsubjectarrayList;
    private ArrayList<String> studentnamelist;
    private StudentGridsubjectAdapter adapter;

    // Constructor
    public StudentAssignesubjectAdapter(Context c, ArrayList<String> studentnamelist, MainResponseStudentSubject studentsubjectarrayList) {
        mContext = c;
        this.studentsubjectarrayList = studentsubjectarrayList;
        this.studentnamelist = studentnamelist;
    }

    private class ViewHolder {
        TextView txt_studentName;
        GridView subject_grid_view;
    }

    @Override
    public int getCount() {
        return studentnamelist.size();
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
            convertView = mInflater.inflate(R.layout.list_row_student_assign, null);
            viewHolder.txt_studentName = (TextView) convertView.findViewById(R.id.txt_studentName);
            viewHolder.subject_grid_view = (GridView) convertView.findViewById(R.id.subject_grid_view);


            try {
                viewHolder.txt_studentName.setText(studentnamelist.get(position));
                adapter = new StudentGridsubjectAdapter(mContext, studentsubjectarrayList.getFinalArray().get(position));
                viewHolder.subject_grid_view.setAdapter(adapter);

                setDynamicHeight(viewHolder.subject_grid_view);

            } catch (Exception e) {
                e.printStackTrace();
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private void setDynamicHeight(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = gridViewAdapter.getCount();
        int rows = 0;

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        float x = 1;
        if( items > 3 ){
            x = items/3;
            rows = (int) (x + 1);
            totalHeight *= rows;
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
}

