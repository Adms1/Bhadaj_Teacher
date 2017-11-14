package anandniketan.com.bhadajteacher.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajteacher.Interfacess.onStudentHomeWorkStatus;
import anandniketan.com.bhadajteacher.Interfacess.onWorkStatus;
import anandniketan.com.bhadajteacher.Models.HomeworkModel;
import anandniketan.com.bhadajteacher.Models.WorkPlanResponse.WorkPlanDatum;
import anandniketan.com.bhadajteacher.R;

/**
 * Created by admsandroid on 11/13/2017.
 */

public class ExpandableListAdapterWorkPlan extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, ArrayList<WorkPlanDatum>> listDataChild;
    private TextView work_name_txt, startdate_txt, enddate_txt;
    private RadioGroup status_radiogroup;
    private RadioButton done_rdb, pendding_rdb;
    private onWorkStatus onWorkStatus;
    private ArrayList<String> getEditworkplan = new ArrayList<String>();

    public ExpandableListAdapterWorkPlan(Context context, List<String> listDataHeader, HashMap<String, ArrayList<WorkPlanDatum>> listDataChild, onWorkStatus onWorkStatus) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
        this.onWorkStatus = onWorkStatus;
    }

    @Override
    public ArrayList<WorkPlanDatum> getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             final boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_work_plan, null);
        }
        final ArrayList<WorkPlanDatum> childData = getChild(groupPosition, 0);
        work_name_txt = (TextView) convertView.findViewById(R.id.work_name_txt);
        startdate_txt = (TextView) convertView.findViewById(R.id.startdate_txt);
        enddate_txt = (TextView) convertView.findViewById(R.id.enddate_txt);
        status_radiogroup = (RadioGroup) convertView.findViewById(R.id.status_radiogroup);
        done_rdb = (RadioButton) convertView.findViewById(R.id.done_rdb);
        pendding_rdb = (RadioButton) convertView.findViewById(R.id.pendding_rdb);

        if (childData.get(childPosition).getStatus().equalsIgnoreCase("1")) {
            done_rdb.setChecked(true);
        } else {
            pendding_rdb.setChecked(true);
        }

        work_name_txt.setText(childData.get(childPosition).getWork());
        startdate_txt.setText(childData.get(childPosition).getFromDate());
        enddate_txt.setText(childData.get(childPosition).getToDate());
        if(childData.get(childPosition).getRemarks().equalsIgnoreCase("")){
            childData.get(childPosition).setRemarks(".");
        }
        status_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.done_rdb:
                        getEditworkplan.clear();
                        childData.get(childPosition).setStatus("1");
                        getEditworkplan.add(childData.get(childPosition).getFKWorkID() + "|" + childData.get(childPosition).getPKTeacherworkID() + "|"
                                + childData.get(childPosition).getWork() + "|" + childData.get(childPosition).getStatus() + "|" +
                                childData.get(childPosition).getFromDate() + "|" + childData.get(childPosition).getToDate() + "|" +
                                childData.get(childPosition).getRemarks());
                        onWorkStatus.onWorkStatus();
                        break;
                    case R.id.pendding_rdb:
                        getEditworkplan.clear();
                        childData.get(childPosition).setStatus("0");
                        getEditworkplan.add(childData.get(childPosition).getFKWorkID() + "|" + childData.get(childPosition).getPKTeacherworkID() + "|"
                                + childData.get(childPosition).getWork() + "|" + childData.get(childPosition).getStatus() + "|" +
                                childData.get(childPosition).getFromDate() + "|" + childData.get(childPosition).getToDate() + "|" +
                                childData.get(childPosition).getRemarks());
                        onWorkStatus.onWorkStatus();
                        break;
                    default:
                }

            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];
        String headerTitle4 = headerTitle[3];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_workplan, null);
        }
        TextView Standard_txt, Class_txt, Subject_txt, month_txt, view_txt;


        Standard_txt = (TextView) convertView.findViewById(R.id.Standard_txt);
        Class_txt = (TextView) convertView.findViewById(R.id.Class_txt);
        Subject_txt = (TextView) convertView.findViewById(R.id.Subject_txt);
        month_txt = (TextView) convertView.findViewById(R.id.month_txt);
        view_txt = (TextView) convertView.findViewById(R.id.view_txt);


        Standard_txt.setText(headerTitle1);
        Class_txt.setText(headerTitle2);
        Subject_txt.setText(headerTitle3);
        month_txt.setText(headerTitle4);

        if (isExpanded) {
            view_txt.setTextColor(context.getResources().getColor(R.color.present_header));
        } else {
            view_txt.setTextColor(context.getResources().getColor(R.color.absent_header));
        }


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


    public ArrayList<String> getDataworkplan() {
        return getEditworkplan;
    }
}

