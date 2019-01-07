package anandniketan.com.anbcteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.wdullaer.materialdatetimepicker.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import anandniketan.com.anbcteacher.Activities.LoginActivity;
import anandniketan.com.anbcteacher.Adapter.ExpandableListAdapterMarks;
import anandniketan.com.anbcteacher.AsyncTasks.TeacherGetTestMarksAsyncTask;
import anandniketan.com.anbcteacher.Models.FinalArrayGetTermModel;
import anandniketan.com.anbcteacher.Models.NewResponse.FinalArray;
import anandniketan.com.anbcteacher.Models.NewResponse.MainResponse;
import anandniketan.com.anbcteacher.Models.NewResponse.StudentDatum;
import anandniketan.com.anbcteacher.Models.NewResponse.SubjectMark;
import anandniketan.com.anbcteacher.Models.TermModel;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.ApiHandler;
import anandniketan.com.anbcteacher.Utility.CustomEditText;
import anandniketan.com.anbcteacher.Utility.Utility;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MarksFragment extends Fragment {
    private View rootView;

    private TextView txtNoRecordsMarks;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private TeacherGetTestMarksAsyncTask getTestMarksAsyncTask = null;
    private int lastExpandedPosition = -1;
    private LinearLayout Marks_header, class_linear, search_linear;
    private Spinner class_spinner;
    private ImageView search_img;
    private CustomEditText search_edt;
    private boolean searchflag = false;
    private ExpandableListAdapterMarks listAdapterMarks;
    private ExpandableListView lvExpMarks;
    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<SubjectMark>> listDataChild = new HashMap<>();
    private HashMap<String, String> listDatafooter = new HashMap<>();
    private String spinnerSelectedValue, value;
    private MainResponse dataresponse;
    private HashMap<Integer,String> mTermIdDatas;
    private LinearLayout term_Linear;
    private Spinner termSpinner;
    private String FinalTermId = "3";
    private List<FinalArrayGetTermModel> finalArrayGetTermModels;
    private HashMap<Integer, String> spinnerTermMap;
    private ImageView mSearchImage;



    public MarksFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_marks, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        //getMarksData();
        return rootView;
    }

    public void initViews() {
        txtNoRecordsMarks = (TextView) rootView.findViewById(R.id.txtNoRecordsMarks);
        lvExpMarks = (ExpandableListView) rootView.findViewById(R.id.lvExpMarks);
        Marks_header = (LinearLayout) rootView.findViewById(R.id.Marks_header);
        class_linear = (LinearLayout) rootView.findViewById(R.id.class_linear);
        search_linear = (LinearLayout) rootView.findViewById(R.id.search_linear);
        class_spinner = (Spinner) rootView.findViewById(R.id.class_spinner);
        search_img = (ImageView) rootView.findViewById(R.id.search_img);
        search_edt = (CustomEditText) rootView.findViewById(R.id.search_edt);
        term_Linear = (LinearLayout)rootView.findViewById(R.id.term_linear);
        termSpinner = (Spinner)rootView.findViewById(R.id.term_spinner);
        mSearchImage = (ImageView)rootView.findViewById(R.id.search_img) ;
        setUserVisibleHint(true);

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            callTermApi();

        }
        // execute your data loading logic.
    }
    public void setListners() {
        lvExpMarks.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpMarks.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                spinnerSelectedValue = adapterView.getItemAtPosition(j).toString();
                Log.d("spinner", spinnerSelectedValue);
                String[] array = spinnerSelectedValue.split("->");
                Log.d("Array", Arrays.toString(array));
                List<FinalArray> filterFinalArray = new ArrayList<FinalArray>();
                for (FinalArray arrayObj : dataresponse.getFinalArray()) {
                    if (arrayObj.getStandardClass().equalsIgnoreCase(array[0].trim()) && arrayObj.getTestName().equalsIgnoreCase(array[1].trim())) {
                        filterFinalArray.add(arrayObj);
                    }
                }
                setExpandableListView(filterFinalArray);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        termSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = termSpinner.getSelectedItem().toString();
                String getid = spinnerTermMap.get(termSpinner.getSelectedItemPosition());

                Log.d("value", name + " " + getid);
                FinalTermId = getid.toString();
                Log.d("FinalTermIdStr", FinalTermId);
                getMarksData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_edt.setVisibility(View.VISIBLE);
            }
        });


        search_edt.setDrawableClickListener(new CustomEditText.DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case LEFT:
                        //Do something here
                        break;
                    case RIGHT:
                       search_edt.setText("");
                       break;
                    default:
                        break;
                }
            }
        });

        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {

//                    Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.ic_clear);
//                    drawable = DrawableCompat.wrap(drawable);
//                    DrawableCompat.setTint(drawable,Color.TRANSPARENT);
//                    DrawableCompat.setTintMode(drawable,PorterDuff.Mode.SRC_ATOP);
                    search_edt.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.cross_11, 0);
                } else {

//                    Drawable drawable = ContextCompat.getDrawable(getActivity(),R.drawable.ic_search);
//                    drawable = DrawableCompat.wrap(drawable);
//                    DrawableCompat.setTint(drawable,ContextCompat.getColor(getActivity(),R.color.linear_color));
//                    DrawableCompat.setTintMode(drawable,PorterDuff.Mode.SRC_ATOP);
                    search_edt.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.ic_search, 0);
                }

//                if (count > 2) {
                    List<StudentDatum> filterFinalArray = new ArrayList<StudentDatum>();
                    String[] array = spinnerSelectedValue.split("->");
                    Log.d("arrayString", Arrays.toString(array) + " == " + s.toString());
                    for (FinalArray arrayObj : dataresponse.getFinalArray()) {
                        if (arrayObj.getStandardClass().equalsIgnoreCase(array[0].trim()) && arrayObj.getTestName().equalsIgnoreCase(array[1].trim())) {
                            for (int i = 0; i < arrayObj.getStudentData().size(); i++) {
                                if (arrayObj.getStudentData().get(i).getStudentName().toLowerCase().contains(s.toString().toLowerCase())) {
                                    filterFinalArray.add(arrayObj.getStudentData().get(i));
                                }
                            }
                        }
                    }
                    Log.d("FilterArray", "" + filterFinalArray.size());

                   // listAdapterMarks.filterData(s.toString(),filterFinalArray);
                    setSearchExpandableListView(filterFinalArray);
//                } else {
//                    String[] array = spinnerSelectedValue.split("->");
//                    Log.d("Array", Arrays.toString(array));
//                    List<FinalArray> filterFinalArray = new ArrayList<FinalArray>();
//                    for (FinalArray arrayObj : dataresponse.getFinalArray()) {
//                        if (arrayObj.getStandardClass().equalsIgnoreCase(array[0].trim()) && arrayObj.getTestName().equalsIgnoreCase(array[1].trim())) {
//                            filterFinalArray.add(arrayObj);
//                        }
//                    }
//                    //listAdapterMarks.filterData(s.toString(),filterFinalArray);
//
//                    setExpandableListView(filterFinalArray);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // CALL Term API HERE
    private void callTermApi() {

        if (!Utility.isNetworkConnected(mContext)) {
            Utility.ping(getActivity(),"Network Error");
            return;
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiHandler.getApiService().getTerm(getTermDetail(), new retrofit.Callback<TermModel>() {
            @Override
            public void success(TermModel termModel, Response response) {
                progressDialog.dismiss();
                if (termModel == null) {
                    Utility.ping(mContext, "Something Wrong");
                    return;
                }
                if (termModel.getSuccess() == null) {
                    Utility.ping(mContext, "Something Wrong");
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("false")) {
                    Utility.ping(mContext, "Record not found");
                    return;
                }
                if (termModel.getSuccess().equalsIgnoreCase("True")) {
                    finalArrayGetTermModels = termModel.getFinalArray();
                    if (finalArrayGetTermModels != null) {
                        fillTermSpinner();

                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                error.getMessage();
                Utility.ping(mContext,"Something Wrong");
            }
        });

    }

    private Map<String, String> getTermDetail() {
        Map<String, String> map = new HashMap<>();
        return map;
    }



    public void fillTermSpinner() {
        ArrayList<Integer> TermId = new ArrayList<Integer>();
        for (int i = 0; i < finalArrayGetTermModels.size(); i++) {
            TermId.add(finalArrayGetTermModels.get(i).getTermId());
        }
        ArrayList<String> Term = new ArrayList<String>();
        for (int j = 0; j < finalArrayGetTermModels.size(); j++) {
            Term.add(finalArrayGetTermModels.get(j).getTerm());
        }

        String[] spinnertermIdArray = new String[TermId.size()];

        spinnerTermMap = new HashMap<Integer, String>();
        for (int i = 0; i < TermId.size(); i++) {
            spinnerTermMap.put(i, String.valueOf(TermId.get(i)));
            spinnertermIdArray[i] = Term.get(i).trim();
        }
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(termSpinner);

            popupWindow.setHeight(spinnertermIdArray.length > 4 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adapterTerm = new ArrayAdapter<String>(mContext,R.layout.spinner_layout,spinnertermIdArray);
        termSpinner.setAdapter(adapterTerm);
        FinalTermId = spinnerTermMap.get(0);
    }


    public void getMarksData() {
//        if (Utility.isNetworkConnected(mContext)) {
//            progressDialog = new ProgressDialog(mContext);
//            progressDialog.setMessage("Please Wait...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        HashMap<String, String> params = new HashMap<String, String>();
//                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
//                        getTestMarksAsyncTask = new TeacherGetTestMarksAsyncTask(params);
//                        response = getTestMarksAsyncTask.execute().get();
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressDialog.dismiss();
//                                    if (response.getFinalArray().size() > 0) {
//                                        txtNoRecordsMarks.setVisibility(View.GONE);
//                                        class_linear.setVisibility(View.VISIBLE);
//                                        fillspinner();
//                                    } else {
//                                        progressDialog.dismiss();
//                                        txtNoRecordsMarks.setVisibility(View.VISIBLE);
//                                        Marks_header.setVisibility(View.GONE);
//                                        class_linear.setVisibility(View.GONE);
//
//                                    }
//
//                            }
//                        });
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        } else {
//            Utility.ping(mContext, "Network not available");
//        }


        if (!Utility.isNetworkConnected(mContext)) {
                Utility.ping(getActivity(),"Please check your internet connection");
                return;
            }
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();


           // Utils.showDialog(getActivity());
            ApiHandler.getApiService().GetTeacherTestMarks(getDetail(), new retrofit.Callback<MainResponse>() {
                @Override
                public void success(MainResponse announcementModel, Response response) {
                    //Utils.dismissDialog();
                    progressDialog.dismiss();
                    if (announcementModel == null) {
                        Utility.ping(mContext, "Something wrong");
                        txtNoRecordsMarks.setVisibility(View.VISIBLE);
                        Marks_header.setVisibility(View.GONE);
                        class_linear.setVisibility(View.GONE);
                        lvExpMarks.setVisibility(View.GONE);
                        search_linear.setVisibility(View.GONE);
                        return;
                    }
                    if (announcementModel.getSuccess() == null) {
                        Utility.ping(mContext,"Something wrong");
                        txtNoRecordsMarks.setVisibility(View.VISIBLE);
                        Marks_header.setVisibility(View.GONE);
                        class_linear.setVisibility(View.GONE);
                        lvExpMarks.setVisibility(View.GONE);
                        search_linear.setVisibility(View.GONE);

                        return;
                    }
                    if (announcementModel.getSuccess().equalsIgnoreCase("false")) {
                        Utility.ping(mContext,"No Record Found");
                        txtNoRecordsMarks.setVisibility(View.VISIBLE);
                        Marks_header.setVisibility(View.GONE);
                        class_linear.setVisibility(View.GONE);
                        lvExpMarks.setVisibility(View.GONE);
                        search_linear.setVisibility(View.GONE);

                        return;
                    }
                    if (announcementModel.getSuccess().equalsIgnoreCase("True")) {
                        dataresponse = announcementModel;
                        if (dataresponse != null) {
                            txtNoRecordsMarks.setVisibility(View.GONE);
                            class_linear.setVisibility(View.VISIBLE);
                            lvExpMarks.setVisibility(View.VISIBLE);
                            search_linear.setVisibility(View.VISIBLE);

                            fillspinner();

                        } else {
                            txtNoRecordsMarks.setVisibility(View.VISIBLE);
                            Marks_header.setVisibility(View.GONE);
                            class_linear.setVisibility(View.GONE);
                            lvExpMarks.setVisibility(View.GONE);
                            search_linear.setVisibility(View.GONE);

                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    error.printStackTrace();
                    error.getMessage();
                    txtNoRecordsMarks.setVisibility(View.VISIBLE);
                    Marks_header.setVisibility(View.GONE);
                    class_linear.setVisibility(View.GONE);
                    lvExpMarks.setVisibility(View.GONE);
                    search_linear.setVisibility(View.GONE);

                    Utility.ping(mContext,error.getMessage());
                }
            });

        }

        private Map<String, String> getDetail() {
            Map<String, String> map = new HashMap<>();
            map.put("StaffID",Utility.getPref(getActivity(),"StaffID"));
            map.put("TermID",FinalTermId);
            return map;
        }


    private void setExpandableListView(List<FinalArray> array) {
        listDataHeader = new ArrayList<>();
        listDataChild.clear();
        listDatafooter.clear();
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).getStudentData().size() > 0) {
                Marks_header.setVisibility(View.VISIBLE);
                search_edt.setVisibility(View.VISIBLE);
                for (int j = 0; j < array.get(i).getStudentData().size(); j++) {
                    listDataHeader.add(array.get(i).getStudentData().get(j).getStudentName() + "|" + array.get(i).getStudentData().get(j).getGRNO() + "|" + array.get(i).getStudentData().get(j).getPercentage());
                    listDataChild.put(array.get(i).getStudentData().get(j).getStudentName() + "|" + array.get(i).getStudentData().get(j).getGRNO() + "|" + array.get(i).getStudentData().get(j).getPercentage(), array.get(i).getStudentData().get(j).getSubjectMarks());
                    listDatafooter.put(array.get(i).getStudentData().get(j).getStudentName() + "|" + array.get(i).getStudentData().get(j).getGRNO() + "|" + array.get(i).getStudentData().get(j).getPercentage(), String.valueOf(array.get(i).getStudentData().get(j).getTotalGainedMarks()) + "/" + String.valueOf(array.get(i).getStudentData().get(j).getTotalMarks()));
                }
            } else {
                Marks_header.setVisibility(View.GONE);
                search_edt.setVisibility(View.GONE);
            }
        }
        listAdapterMarks = new ExpandableListAdapterMarks(getActivity(), listDataHeader, listDataChild, listDatafooter);
        lvExpMarks.setAdapter(listAdapterMarks);
    }


    private void setSearchExpandableListView(List<StudentDatum> array) {
        listDataHeader = new ArrayList<>();
        listDataChild.clear();
        listDatafooter.clear();
        for (int i = 0; i < array.size(); i++) {
            if (array.size() > 0) {
                Marks_header.setVisibility(View.VISIBLE);
                search_edt.setVisibility(View.VISIBLE);
                for (int j = 0; j < array.size(); j++) {
                    listDataHeader.add(array.get(j).getStudentName() + "|" + array.get(j).getGRNO() + "|" + array.get(j).getPercentage());
                    listDataChild.put(array.get(j).getStudentName() + "|" + array.get(j).getGRNO() + "|" + array.get(j).getPercentage(), array.get(j).getSubjectMarks());
                    listDatafooter.put(array.get(j).getStudentName() + "|" + array.get(j).getGRNO() + "|" + array.get(j).getPercentage(), String.valueOf(array.get(j).getTotalGainedMarks()) + "/" + String.valueOf(array.get(j).getTotalMarks()));
                }
            } else {
                Marks_header.setVisibility(View.GONE);
                search_edt.setVisibility(View.GONE);
            }
        }
        listAdapterMarks = new ExpandableListAdapterMarks(getActivity(), listDataHeader, listDataChild, listDatafooter);
        lvExpMarks.setAdapter(listAdapterMarks);
    }


    public void fillspinner() {
        ArrayList<String> row = new ArrayList<String>();

        mTermIdDatas = new HashMap<Integer, String>();

        for (int z = 0; z < dataresponse.getFinalArray().size(); z++) {
            row.add(dataresponse.getFinalArray().get(z).getStandardClass() + " -> " + dataresponse.getFinalArray().get(z).getTestName());
        }
        HashSet hs = new HashSet();
        hs.addAll(row);
        row.clear();
        row.addAll(hs);
        Log.d("marks", "" + row);

        Collections.sort(row);
        System.out.println("Sorted ArrayList in Java - Ascending order : " + row);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(class_spinner);

            popupWindow.setHeight(row.size() > 5 ? 500 : row.size() * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, row);
        class_spinner.setAdapter(adapterYear);
    }
}
