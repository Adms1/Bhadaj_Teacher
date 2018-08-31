package anandniketan.com.bhadajteacher.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import anandniketan.com.bhadajteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.bhadajteacher.R;

public class LeaveDetailAdapter extends RecyclerView.Adapter<LeaveDetailAdapter.MyViewHolder> {

    LeaveMainModel arrayList;
    private Context mContext;

    public LeaveDetailAdapter(Context mContext, LeaveMainModel arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public LeaveDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_detail_item, parent, false);
        return new LeaveDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LeaveDetailAdapter.MyViewHolder holder, int position) {
        holder.category_name_txt.setText(arrayList.getLeaveDetails().get(position).getCategory());
        holder.total_txt.setText(arrayList.getLeaveDetails().get(position).getTotal());
        holder.used_txt.setText(arrayList.getLeaveDetails().get(position).getUsed());
        holder.remaining_txt.setText(arrayList.getLeaveDetails().get(position).getRemaining());
    }

    @Override
    public int getItemCount() {
        return arrayList.getLeaveDetails().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView category_name_txt,total_txt,used_txt,remaining_txt;
        public MyViewHolder(View view) {
            super(view);
            category_name_txt=(TextView)view.findViewById(R.id.category_name_txt);
            total_txt=(TextView)view.findViewById(R.id.total_txt);
            used_txt=(TextView)view.findViewById(R.id.used_txt);
            remaining_txt=(TextView)view.findViewById(R.id.remaining_txt);

        }
    }
}


