package anandniketan.com.anbcteacher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import anandniketan.com.anbcteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse.MainResponseStudentSubject;
import anandniketan.com.anbcteacher.R;

public class AddMarksAdapter extends BaseAdapter {
    private Context mContext;
    private LeaveMainModel marksResponse;


    public AddMarksAdapter(Context mContext, LeaveMainModel marksResponse) {
       this. mContext = mContext;
        this.marksResponse = marksResponse;
    }

    @Override
    public int getCount() {
        return marksResponse.getFinalArray().size();
    }

    @Override
    public Object getItem(int position) {
        return marksResponse.getFinalArray().get(position);
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
            convertView = mInflater.inflate(R.layout.add_marks_items, null);
            viewHolder.Student_name_txt = (TextView) convertView.findViewById(R.id.Student_name_txt);
            viewHolder.gr_no_txt = (TextView)convertView.findViewById(R.id.gr_no_txt);
            viewHolder.Marks_txt = (EditText)convertView.findViewById(R.id.Marks_txt);

            try {
                viewHolder.Student_name_txt.setText(marksResponse.getFinalArray().get(position).getStudentName());
                viewHolder.gr_no_txt.setText(marksResponse.getFinalArray().get(position).getGRNO());
            } catch (Exception e) {
                e.printStackTrace();
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(viewHolder.Marks_txt != null) {

            viewHolder.Marks_txt.setText(marksResponse.getFinalArray().get(position).getMark());

            if (!viewHolder.Marks_txt.getText().toString().equalsIgnoreCase("")) {
                marksResponse.getFinalArray().get(position).setCheckStatus("1");
                marksResponse.getFinalArray().get(position).setMark(viewHolder.Marks_txt.getText().toString());
            } else {
                marksResponse.getFinalArray().get(position).setCheckStatus("0");
            }

            viewHolder.Marks_txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.toString().length() > 0) {
                        marksResponse.getFinalArray().get(position).setCheckStatus("1");
                        marksResponse.getFinalArray().get(position).setMark(viewHolder.Marks_txt.getText().toString());
                    } else {
                        marksResponse.getFinalArray().get(position).setCheckStatus("0");
                    }
                }
            });
        }

        return convertView;
    }

    private class ViewHolder {
        TextView Student_name_txt, gr_no_txt;
        EditText Marks_txt;
    }

}

/*extends RecyclerView.Adapter<AddMarksAdapter.MyViewHolder> {
    LeaveMainModel marksResponse;
    private Context context;

    public AddMarksAdapter(Context mContext, LeaveMainModel marksResponse) {
        context = mContext;
        marksResponse = marksResponse;
    }


    @Override
    public AddMarksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_marks_items, parent, false);

        return new AddMarksAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddMarksAdapter.MyViewHolder holder, final int position) {
        holder.Student_name_txt.setText(marksResponse.getFinalArray().get(position).getStudentName());
        holder.gr_no_txt.setText(marksResponse.getFinalArray().get(position).getGRNO());
        holder.Marks_txt.setText(marksResponse.getFinalArray().get(position).getMark());


    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return marksResponse.getFinalArray().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Student_name_txt, gr_no_txt;
        EditText Marks_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            Student_name_txt = (TextView) itemView.findViewById(R.id.Student_name_txt);
            gr_no_txt = (TextView) itemView.findViewById(R.id.gr_no_txt);
            Marks_txt=(EditText)itemView.findViewById(R.id.Marks_txt);
        }
    }
}*/



