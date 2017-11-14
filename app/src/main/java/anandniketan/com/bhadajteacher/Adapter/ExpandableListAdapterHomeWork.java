package anandniketan.com.bhadajteacher.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajteacher.Interfacess.onStudentHomeWorkStatus;
import anandniketan.com.bhadajteacher.Models.HomeworkModel;
import anandniketan.com.bhadajteacher.R;

/**
 * Created by admsandroid on 10/13/2017.
 */

public class ExpandableListAdapterHomeWork extends BaseExpandableListAdapter {

    private Context _context;
    boolean visible = true;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, ArrayList<HomeworkModel.HomeworkData>> _listDataChild;
    String FontStyle, splitFont1, splitFont2, splitFont3, splitFont4;
    TextView subject_title_txt, homwork_name_txt, chapter_name_txt, objective_txt;
    Button StudentHomeWorkStatus_btn;
    ImageView imgRightSign;
    LinearLayout chapter_linear, objective_linear, que_linear;
    Typeface typeface;
    onStudentHomeWorkStatus _onStudentHomeWorkStatus;
    ArrayList<String> getId = new ArrayList<>();
    private String date = new String();

    public ExpandableListAdapterHomeWork(Context context, List<String> listDataHeader,
                                         HashMap<String, ArrayList<HomeworkModel.HomeworkData>> listChildData,
                                         onStudentHomeWorkStatus onStudentHomeWorkStatus) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._onStudentHomeWorkStatus = onStudentHomeWorkStatus;
    }

    @Override
    public ArrayList<HomeworkModel.HomeworkData> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             final boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<HomeworkModel.HomeworkData> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_home_work, null);
        }

        homwork_name_txt = (TextView) convertView.findViewById(R.id.homwork_name_txt);
        chapter_name_txt = (TextView) convertView.findViewById(R.id.chapter_name_txt);
        objective_txt = (TextView) convertView.findViewById(R.id.objective_txt);
        StudentHomeWorkStatus_btn = (Button) convertView.findViewById(R.id.StudentHomeWorkStatus_btn);

        FontStyle = "";
        splitFont1 = "";
        splitFont2 = "";
        splitFont3 = "";
        FontStyle = childData.get(childPosition).getFont();

        String object = childData.get(childPosition).getWorkPlan().replace("&nbsp;", "");
        String homeworkname = childData.get(childPosition).getHomeWork().replace("&nbsp;", "");
        String chaptername = childData.get(childPosition).getClassWork().replace("&nbsp;", "");

        homwork_name_txt.setText((homeworkname.trim()));
        chapter_name_txt.setText((object.trim()));
        objective_txt.setText(chaptername.trim());

//        if (!FontStyle.equalsIgnoreCase("-|-|-|-")) {
//            String[] splitFontStyle = FontStyle.split("\\|");
//            Log.d("SplitFOnt", splitFontStyle[0]);
//            splitFont1 = splitFontStyle[0].toString();
//            splitFont2 = splitFontStyle[1].toString();
//            splitFont3 = splitFontStyle[2].toString();
//            splitFont4 = splitFontStyle[3].toString();
//
//            SetLanguageHomework(splitFont1);
//            SetLanguageChapterName(splitFont2);
//            SetLanguageObjective(splitFont3);
//
//            homwork_name_txt.setText((homeworkname.trim()));
//            chapter_name_txt.setText((chaptername.trim()));
//            objective_txt.setText((object.trim()));
//
//        } else {
//            typeface = Typeface.createFromAsset(_context.getAssets(), "Font/arial.ttf");
//            homwork_name_txt.setTypeface(typeface);
//            chapter_name_txt.setTypeface(typeface);
//            objective_txt.setTypeface(typeface);
//
//            homwork_name_txt.setText((homeworkname.trim()));
//            chapter_name_txt.setText((chaptername.trim()));
//            objective_txt.setText((object.trim()));
//        }
        StudentHomeWorkStatus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId.add(childData.get(childPosition).getStandardID() + "|" + childData.get(childPosition).getClassID() +
                        "|" + childData.get(childPosition).getSubjectID() + "|" + childData.get(childPosition).getTermID());
                _onStudentHomeWorkStatus.getStudentHomeWorkStatus();
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];
        String headerTitle4 = headerTitle[3];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_homework, null);
        }
        TextView Date_txt, Standard_txt, Class_txt, Subject_txt;
        ImageView arrow_txt;

        Date_txt = (TextView) convertView.findViewById(R.id.Date_txt);
        Standard_txt = (TextView) convertView.findViewById(R.id.Standard_txt);
        Class_txt = (TextView) convertView.findViewById(R.id.Class_txt);
        Subject_txt = (TextView) convertView.findViewById(R.id.Subject_txt);
        arrow_txt = (ImageView) convertView.findViewById(R.id.arrow_txt);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
//        Date d = null;
//        try {
//            d = sdf.parse(headerTitle1);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String formattedTime = output.format(d);

        Date_txt.setText(headerTitle1);
        Standard_txt.setText(headerTitle2);
        Class_txt.setText(headerTitle3);
        Subject_txt.setText(headerTitle4);

        if (isExpanded) {
            arrow_txt.setBackgroundResource(R.drawable.arrow_1_42_down);
            date = Date_txt.getText().toString();
        } else {
            arrow_txt.setBackgroundResource(R.drawable.arrow_1_42);
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

    public void SetLanguageHomework(String type) {
        switch (type) {
            case "ArivNdr POMt":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Arvinder.ttf");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-1":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Gujrati-Saral-1.ttf");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-2":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL2.TTF");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-3":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL3.TTF");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-4":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL4.TTF");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-4":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/H-SARAL0.TTF");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-1":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral1.TTF");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-2":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral2.TTF");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-3":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral3.TTF");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Shivaji05":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Shivaji05.ttf");
                homwork_name_txt.setTypeface(typeface);
                break;
            case "Shruti":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Shruti.ttf");
                homwork_name_txt.setTypeface(typeface);
                break;
            default:
        }
    }

    public void SetLanguageChapterName(String type) {
        switch (type) {
            case "ArivNdr POMt":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Arvinder.ttf");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-1":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Gujrati-Saral-1.ttf");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-2":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL2.TTF");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-3":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL3.TTF");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-4":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL4.TTF");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-4":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/H-SARAL0.TTF");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-1":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral1.TTF");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-2":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral2.TTF");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-3":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral3.TTF");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Shivaji05":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Shivaji05.ttf");
                chapter_name_txt.setTypeface(typeface);
                break;
            case "Shruti":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Shruti.ttf");
                chapter_name_txt.setTypeface(typeface);
                break;
            default:
        }
    }

    public void SetLanguageObjective(String type) {
        switch (type) {
            case "ArivNdr POMt":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Arvinder.ttf");
                objective_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-1":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Gujrati-Saral-1.ttf");
                objective_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-2":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL2.TTF");
                objective_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-3":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL3.TTF");
                objective_txt.setTypeface(typeface);
                break;
            case "Gujrati Saral-4":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/G-SARAL4.TTF");
                objective_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-4":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/H-SARAL0.TTF");
                objective_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-1":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral1.TTF");
                objective_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-2":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral2.TTF");
                objective_txt.setTypeface(typeface);
                break;
            case "Hindi Saral-3":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/h-saral3.TTF");
                objective_txt.setTypeface(typeface);
                break;
            case "Shivaji05":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Shivaji05.ttf");
                objective_txt.setTypeface(typeface);
                break;
            case "Shruti":
                typeface = Typeface.createFromAsset(_context.getAssets(), "Font/Shruti.ttf");
                objective_txt.setTypeface(typeface);
                break;
            default:
        }
    }
    public ArrayList<String> getAllId() {
        return getId;
    }

    public String getDate() {
        return date;
    }
}


