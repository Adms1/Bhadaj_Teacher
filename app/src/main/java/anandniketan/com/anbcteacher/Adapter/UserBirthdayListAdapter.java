package anandniketan.com.anbcteacher.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import anandniketan.com.anbcteacher.AsyncTasks.SentBirthDayWishTask;
import anandniketan.com.anbcteacher.Models.BirthDayDetailModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;

public class UserBirthdayListAdapter extends RecyclerView.Adapter<UserBirthdayListAdapter.MyViewHolder>{

    private List<BirthDayDetailModel> birthdayList;
    private Context context;
    private ProgressDialog progressDialog;
    private SentBirthDayWishTask sentBirthDayWishTask;
    private boolean sentFlagFromServer = false;
    private OnNotificationCount onNotificationCountRef;
    private int countNotification;


    public UserBirthdayListAdapter(Context context,List<BirthDayDetailModel> birthdayList,OnNotificationCount onNotificationCount) {
        this.birthdayList = birthdayList;
        this.context  = context;
        onNotificationCountRef = (OnNotificationCount) onNotificationCount;
    }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layot_birthday_list_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

           final BirthDayDetailModel birthDayDetailModel = birthdayList.get(position);
            holder.tvUserName.setText(birthDayDetailModel.getName());
            holder.tvUserDept.setText(birthDayDetailModel.getDepartment());
            holder.tvUserType.setText("("+birthDayDetailModel.getType()+")");


            if(birthDayDetailModel.getFlag().equalsIgnoreCase("True")){
//                if(countNotification >= 0) {
//                    --countNotification;
//                    onNotificationCountRef.getNotificationCount(countNotification);
//                }
                holder.btnSend.setVisibility(View.GONE);
                holder.mCardContainer.setBackgroundColor(ContextCompat.getColor(context,R.color.lightwhite));

            }else{
                holder.btnSend.setVisibility(View.VISIBLE);
                holder.mCardContainer.setBackgroundColor(ContextCompat.getColor(context,R.color.mdtp_white));
//                if(countNotification >= 0){
//                   ++countNotification;
//                    onNotificationCountRef.getNotificationCount(countNotification);
//
//                }
            }
            holder.btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sentBirthdayWishTask(birthDayDetailModel.getType(),birthDayDetailModel.getID(),position);
                }
            });

        }

        @Override
        public int getItemCount() {
            return birthdayList.size();
        }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName,tvUserType,tvUserDept;
        private CardView mCardContainer;
        public AppCompatButton btnSend;

        public MyViewHolder(View view) {
            super(view);
            tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
            tvUserType = (TextView) view.findViewById(R.id.tv_user_desg);
            tvUserDept = (TextView) view.findViewById(R.id.tv_user_desgName);
            btnSend = (AppCompatButton)view.findViewById(R.id.btn_send);
            mCardContainer = (CardView)view.findViewById(R.id.container);
        }
    }

    public void clearNotificationCount(){
        countNotification = 0;
    }

    private void sentBirthdayWishTask(final String type, final int receiverId, final int pos) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("StaffID",Utility.getPref(context, "StaffID"));
                    params.put("Type",type);
                    params.put("ReceiverID",String.valueOf(receiverId));

                    sentBirthDayWishTask = new SentBirthDayWishTask(params);
                    sentFlagFromServer = sentBirthDayWishTask.execute().get();
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            try {
                                if (birthdayList != null) {
                                    birthdayList.get(pos).setFlag("True");
                                    countNotification = 0;

                                    for(int count = 0;count<birthdayList.size();count++){
                                        if(birthdayList.get(count).getFlag().equalsIgnoreCase("False")){
                                            countNotification++;
                                        }
                                    }
                                    notifyDataSetChanged();
                                    if(countNotification >= 0) {
                                        onNotificationCountRef.getNotificationCount(countNotification);
                                    }

                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public interface OnNotificationCount{
        void getNotificationCount(int count);
    }


}
