package anandniketan.com.bhadajteacher.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajteacher.Models.NewResponse.SubjectMark;
import anandniketan.com.bhadajteacher.R;

/**
 * Created by admsandroid on 9/27/2017.
 */

public class ExpandableListAdapterMarks  extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<SubjectMark>> listChildData;
    private HashMap<String, String> listfooterDate;

    public ExpandableListAdapterMarks(Context context, List<String> listDataHeader,
                                      HashMap<String, List<SubjectMark>> listChildData, HashMap<String, String> listfooterDate) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.listChildData = listChildData;
        this.listfooterDate = listfooterDate;
    }

    @Override
    public anandniketan.com.bhadajteacher.Models.NewResponse.SubjectMark getChild(int groupPosition, int childPosititon) {
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
        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (childPosition > 0 && childPosition < getChildrenCount(groupPosition) - 1) {

            SubjectMark currentchild = getChild(groupPosition, childPosition - 1);
            convertView = infalInflater.inflate(R.layout.list_item_marks, null);

            TextView txtSubject, txtMarks;
            txtSubject = (TextView) convertView.findViewById(R.id.txtSubject);
            txtMarks = (TextView) convertView.findViewById(R.id.txtMarks);

            txtSubject.setText(currentchild.getSubject());
            txtMarks.setText(currentchild.getMarks());

        } else if (childPosition == getChildrenCount(groupPosition) - 1) {

            convertView = infalInflater.inflate(R.layout.marks_footer, null);
            TextView txttotal_footer, txtgain_footer;
            txttotal_footer = (TextView) convertView.findViewById(R.id.txttotal_footer);
            txtgain_footer = (TextView) convertView.findViewById(R.id.txtgain_footer);
            txtgain_footer.setText(String.valueOf(listfooterDate.get(_listDataHeader.get(groupPosition))));
        } else {
            convertView = infalInflater.inflate(R.layout.list_item_marks_header, null);
            TextView txtSubject_header, txtMarks_header;
            txtSubject_header = (TextView) convertView.findViewById(R.id.txtSubject_header);
            txtMarks_header = (TextView) convertView.findViewById(R.id.txtMarks_header);

        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildData.get(this._listDataHeader.get(groupPosition)).size() + 2;
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

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_marks, null);
        }
        TextView Student_name_txt, gr_no_txt, percentage_txt, view_txt;
        Student_name_txt = (TextView) convertView.findViewById(R.id.Student_name_txt);
        gr_no_txt = (TextView) convertView.findViewById(R.id.gr_no_txt);
        percentage_txt = (TextView) convertView.findViewById(R.id.percentage_txt);
        view_txt = (TextView) convertView.findViewById(R.id.view_txt);

        Student_name_txt.setText(headerTitle1);
        gr_no_txt.setText(headerTitle2);
        percentage_txt.setText(headerTitle3);

        if (isExpanded) {
            view_txt.setTextColor(_context.getResources().getColor(R.color.present_header));
        } else {
            view_txt.setTextColor(_context.getResources().getColor(R.color.absent_header));
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
}

