package anandniketan.com.anbcteacher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;


import java.util.ArrayList;

import anandniketan.com.anbcteacher.Models.TextModel;
import anandniketan.com.anbcteacher.R;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class AddTestDetalisListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> number;
    private ArrayList<TextModel> textData;
    // Constructor
    public AddTestDetalisListAdapter(Context c, ArrayList<String> number,ArrayList<TextModel> mDataList) {
        mContext = c;
        this.number = number;
        textData = mDataList;

    }

    private class ViewHolder {
        TextView syllbus_edt;

    }

    @Override
    public int getCount() {
        return number.size();
    }

    @Override
    public Object getItem(int position) {
        return number.get(position);
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
            convertView = mInflater.inflate(R.layout.list_edittext, null);
            viewHolder.syllbus_edt = (EditText) convertView.findViewById(R.id.syllabus_txt);

            try {

                //if(viewHolder.syllbus_edt.getText().toString() != null) viewHolder.syllbus_edt.setText(viewHolder.syllbus_edt.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }
        if(viewHolder.syllbus_edt != null) {
            viewHolder.syllbus_edt.setText(textData.get(position).getTextString());

        }
        viewHolder.syllbus_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                textData.get(position).setTextString(s.toString());
            }
        });

        return convertView;
    }
}



