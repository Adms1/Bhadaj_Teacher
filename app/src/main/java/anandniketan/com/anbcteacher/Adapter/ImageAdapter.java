package anandniketan.com.anbcteacher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;


/**
 * Created by admsandroid on 9/20/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public String[] mThumbIds = {
            AppConfiguration.DOMAIN_LIVE_IMAGES +"Schedule.png",
            AppConfiguration.DOMAIN_LIVE_IMAGES +"Subjects.png",
            AppConfiguration.DOMAIN_LIVE_IMAGES +"Time%20Table.png",
            AppConfiguration.DOMAIN_LIVE_IMAGES +"Attendance.png",
//            AppConfiguration.DOMAIN_LIVE_IMAGES +"Work%20Plan.png",
            AppConfiguration.DOMAIN_LIVE_IMAGES +"Daily%20Work.png",
            AppConfiguration.DOMAIN_LIVE_IMAGES +"Test_Syllabus.png",
            AppConfiguration.DOMAIN_LIVE_IMAGES +"Marks.png",
            AppConfiguration.DOMAIN_LIVE_IMAGES +"Leave%20Details.png",
           AppConfiguration.DOMAIN_LIVE_IMAGES+"settings.png"
    };

    public String[] mThumbNames = {"Schedule", "Subjects", "Time table", "Attendance", /*"Work Plan",*/ "Daily Work",
            "Test/Syllabus", "Marks","Leave Details","Settings" /*"PTM"*/};

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

        String url = mThumbIds[position];
    //    Log.d("url", url);

        Glide.with(mContext)
                .load(url)
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.profile_pic_holder)
                )
                .into(imgGridOptions);
        txtGridOptionsName.setText(mThumbNames[position]);
        return convertView;
    }

}