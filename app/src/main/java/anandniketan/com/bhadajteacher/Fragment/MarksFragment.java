package anandniketan.com.bhadajteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajteacher.Adapter.ExpandableListAdapterMarks;
import anandniketan.com.bhadajteacher.AsyncTasks.TeacherGetTestMarksAsyncTask;
import anandniketan.com.bhadajteacher.Models.NewResponse.FinalArray;
import anandniketan.com.bhadajteacher.Models.NewResponse.MainResponse;
import anandniketan.com.bhadajteacher.Models.NewResponse.StudentDatum;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class MarksFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackMarks;
    private TextView txtNoRecordsMarks;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private TeacherGetTestMarksAsyncTask getTestMarksAsyncTask = null;
    private int lastExpandedPosition = -1;
    private LinearLayout Marks_header, class_linear, search_linear;
    private Spinner class_spinner;
    private ImageView search_img;
    private EditText search_edt;
    boolean searchflag = false;

    ExpandableListAdapterMarks listAdapterMarks;
    ExpandableListView lvExpMarks;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<anandniketan.com.bhadajteacher.Models.NewResponse.SubjectMark>> listDataChild = new HashMap<>();
    HashMap<String, String> listDatafooter = new HashMap<>();
    String spinnerSelectedValue, value;
    MainResponse response;

    public MarksFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_marks, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        txtNoRecordsMarks = (TextView) rootView.findViewById(R.id.txtNoRecordsMarks);
        btnBackMarks = (Button) rootView.findViewById(R.id.btnBackMarks);
        lvExpMarks = (ExpandableListView) rootView.findViewById(R.id.lvExpMarks);
        Marks_header = (LinearLayout) rootView.findViewById(R.id.Marks_header);
        class_linear = (LinearLayout) rootView.findViewById(R.id.class_linear);
        search_linear = (LinearLayout) rootView.findViewById(R.id.search_linear);
        class_spinner = (Spinner) rootView.findViewById(R.id.class_spinner);
        search_img = (ImageView) rootView.findViewById(R.id.search_img);
        search_edt = (EditText) rootView.findViewById(R.id.search_edt);

        getMarksData();
    }

    public void setListners() {

        btnBackMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
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
                for (FinalArray arrayObj : response.getFinalArray()) {
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

//        search_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (searchflag == false) {
//                    search_linear.setVisibility(View.VISIBLE);
//                    searchflag = true;
//                } else {
//                    search_linear.setVisibility(View.GONE);
//                    searchflag = false;
//                }
//            }
//        });
        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    search_edt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.cross_11, 0);
                } else {
                    search_edt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0);
                }

                List<FinalArray> filterFinalArray = new ArrayList<FinalArray>();
                String[] array = spinnerSelectedValue.split("->");
                for (FinalArray arrayObj : response.getFinalArray()) {
                    if (arrayObj.getStandardClass().equalsIgnoreCase(array[0].trim()) && arrayObj.getTestName().equalsIgnoreCase(array[1].trim())) {
                        for (StudentDatum studentDatum : arrayObj.getStudentData()) {
                            if (studentDatum.getStudentName().toLowerCase().contains(s.toString().toLowerCase())) {
                                filterFinalArray.add(arrayObj);
                            }
                        }
                    }

                }
                setExpandableListView(filterFinalArray);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getMarksData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        getTestMarksAsyncTask = new TeacherGetTestMarksAsyncTask(params);
                        response = getTestMarksAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                    if (response.getFinalArray().size() > 0) {
                                        txtNoRecordsMarks.setVisibility(View.GONE);
                                        class_linear.setVisibility(View.VISIBLE);

                                        fillspinner();
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecordsMarks.setVisibility(View.VISIBLE);
                                        Marks_header.setVisibility(View.GONE);
                                        class_linear.setVisibility(View.GONE);

                                    }

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            Utility.ping(mContext, "Network not available");
        }
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

    public void fillspinner() {
        ArrayList<String> row = new ArrayList<String>();

        for (int z = 0; z < response.getFinalArray().size(); z++) {
            row.add(response.getFinalArray().get(z).getStandardClass() + " -> " + response.getFinalArray().get(z).getTestName());
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, row);
        class_spinner.setAdapter(adapterYear);
    }
}
