package anandniketan.com.bhadajteacher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import anandniketan.com.bhadajteacher.R;


/**
 * Created by admsandroid on 9/20/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public Integer[] mThumbIds = {
            R.drawable.myschedule, R.drawable.mysubjects, R.drawable.timetable,
            R.drawable.attendance, R.drawable.workplan, R.drawable.dailywork,
            R.drawable.test, R.drawable.marks,
            R.drawable.ptm
    };

    public String[] mThumbNames = {"Schedule", "Subjects", "Time table", "Attendence", "Work Plan", "Daily Work",
            "Test/Syllabus", "Marks", "PTM"};

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgGridOptions = null;
        TextView txtGridOptionsName = null;

        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.grid_cell,null);

        imgGridOptions = (ImageView) convertView.findViewById(R.id.imgGridOptions);
        txtGridOptionsName = (TextView) convertView.findViewById(R.id.txtGridOptionsName);

        imgGridOptions.setImageResource(mThumbIds[position]);
        txtGridOptionsName.setText(mThumbNames[position]);
        return convertView;
    }

}