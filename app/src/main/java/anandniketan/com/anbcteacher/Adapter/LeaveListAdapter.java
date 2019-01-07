package anandniketan.com.anbcteacher.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import anandniketan.com.anbcteacher.Interfacess.onDeleteButton;
import anandniketan.com.anbcteacher.Interfacess.onEditTest;
import anandniketan.com.anbcteacher.Models.LeaveModel.LeaveFinalArray;
import anandniketan.com.anbcteacher.R;


public class LeaveListAdapter extends BaseExpandableListAdapter {

    public String deleteId;
    String headerTitle1, headerTitle2, headerTitle3;
    ArrayList<String> getId;
    onDeleteButton onDeleteButton;
    onEditTest onEditTest;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<LeaveFinalArray>> listChildData;
    private TextView leave_date_txt, approve_day_txt, approve_by_txt, cl_txt, pl_txt, reason_txt,rejectText;
    private LinearLayout leave_date_linear,edit_delete_linear, approveday_date_linear, approve_by_linear, cl_pl_linear, reason_linear,reject_linear;
    private Button edit_btn, delete_btn;

    public LeaveListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<LeaveFinalArray>> listDataChild, onEditTest onEditTest, onDeleteButton onDeleteButton) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listDataChild;
        this.onEditTest = onEditTest;
        this.onDeleteButton = onDeleteButton;
    }

    @Override
    public LeaveFinalArray getChild(int groupPosition, int childPosititon) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_leave, null);
        }
        final LeaveFinalArray childData = getChild(groupPosition, 0);
        leave_date_txt = (TextView) convertView.findViewById(R.id.leave_date_txt);
        approve_day_txt = (TextView) convertView.findViewById(R.id.approve_day_txt);
        approve_by_txt = (TextView) convertView.findViewById(R.id.approve_by_txt);
        cl_txt = (TextView) convertView.findViewById(R.id.cl_txt);
        pl_txt = (TextView) convertView.findViewById(R.id.pl_txt);
        reason_txt = (TextView) convertView.findViewById(R.id.reason_txt);
        reject_linear = (LinearLayout)convertView.findViewById(R.id.reject_by_linear);
        rejectText = (TextView)convertView.findViewById(R.id.reject_by_txt);
        leave_date_linear = (LinearLayout) convertView.findViewById(R.id.leave_date_linear);
        approveday_date_linear = (LinearLayout) convertView.findViewById(R.id.approveday_date_linear);
        approve_by_linear = (LinearLayout) convertView.findViewById(R.id.approve_by_linear);
        cl_pl_linear = (LinearLayout) convertView.findViewById(R.id.cl_pl_linear);
        reason_linear = (LinearLayout) convertView.findViewById(R.id.reason_linear);
        edit_delete_linear=(LinearLayout)convertView.findViewById(R.id.edit_delete_linear);


        edit_btn = (Button) convertView.findViewById(R.id.edit_btn);
        delete_btn = (Button) convertView.findViewById(R.id.delete_btn);

        if (childData.getStatus().equalsIgnoreCase("Pending")) {
            edit_delete_linear.setVisibility(View.VISIBLE);
        } else {
            edit_delete_linear.setVisibility(View.GONE);
        }

        if (childData.getStatus().equalsIgnoreCase("Pending") || childData.getStatus().equalsIgnoreCase("Rejected")) {
            approveday_date_linear.setVisibility(View.GONE);
            approve_by_linear.setVisibility(View.GONE);
            cl_pl_linear.setVisibility(View.GONE);
        } else {
            approveday_date_linear.setVisibility(View.VISIBLE);
            approve_by_linear.setVisibility(View.VISIBLE);
            cl_pl_linear.setVisibility(View.VISIBLE);
        }

        if(childData.getStatus().equalsIgnoreCase("Rejected")){
            reject_linear.setVisibility(View.VISIBLE);
            rejectText.setText(childData.getARBy());
            reason_linear.setVisibility(View.GONE);

        }else{
            reject_linear.setVisibility(View.GONE);
        }

        leave_date_txt.setText(childData.getLeaveStartDate() + " - " + childData.getLeaveEndDate());

        //Utility.setPref(getActivity(), "Push_Notification_message", null);



        approve_day_txt.setText(childData.getARStartDate() + " - " + childData.getAREndDate() + "\n" + childData.getARDays() + " Days");
        approve_by_txt.setText(childData.getARBy());
        cl_txt.setText(childData.getCL());
        pl_txt.setText(childData.getPL());
        reason_txt.setText(childData.getReason());
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteId = String.valueOf(childData.getLeaveID());
                onDeleteButton.deleteSentMessage();
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getId = new ArrayList<>();
                getId.add(String.valueOf(childData.getLeaveID() + "|" + childData.getLeaveDays() +
                        "|" + childData.getLeaveStartDate() + "|" + childData.getLeaveEndDate() +
                        "|" + childData.getReason() + "|" + childData.getHeadName()));
                onEditTest.getEditTest();
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        headerTitle1 = headerTitle[0];
        headerTitle2 = headerTitle[1];
        headerTitle3 = headerTitle[2];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_leave, null);
        }
        TextView view_txt, date_txt, days_txt, status_txt;


        view_txt = (TextView) convertView.findViewById(R.id.view_txt);
        date_txt = (TextView) convertView.findViewById(R.id.date_txt);
        days_txt = (TextView) convertView.findViewById(R.id.days_txt);
        status_txt = (TextView) convertView.findViewById(R.id.status_txt);


        date_txt.setText(headerTitle1);
        days_txt.setText(headerTitle2);
        status_txt.setText(headerTitle3);

        if (headerTitle3.equalsIgnoreCase("Pending")) {
            status_txt.setTextColor(Color.parseColor("#FFD8B834"));
        } else if (headerTitle3.equalsIgnoreCase("Rejected")) {
            status_txt.setTextColor(Color.parseColor("#ed1c24"));
        } else {
            status_txt.setTextColor(Color.parseColor("#86c129"));
        }


        if (isExpanded) {
            view_txt.setCompoundDrawablesWithIntrinsicBounds( R.drawable.arrow_1_42_down, 0, 0, 0);
        } else {
            view_txt.setCompoundDrawablesWithIntrinsicBounds( R.drawable.arrow_1_42, 0, 0, 0);
        }
//        String showListItem = Utility.getPref(_context,"Push_Notification_message");
//
//        if(showListItem.contains(leave_date_txt.getText().toString())){
//            onGroupExpanded(groupPosition);
//        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public ArrayList<String> getAllId() {
        return getId;
    }


}



