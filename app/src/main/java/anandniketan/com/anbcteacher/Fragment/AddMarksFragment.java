package anandniketan.com.anbcteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.anbcteacher.Adapter.AddMarksAdapter;
import anandniketan.com.anbcteacher.AsyncTasks.GetMarksAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetStandardSectionForMarksAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetTermAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.GetTestForMarksAsyncTask;
import anandniketan.com.anbcteacher.AsyncTasks.InsertMarksAsyncTask;
import anandniketan.com.anbcteacher.Models.LeaveModel.LeaveFinalArray;
import anandniketan.com.anbcteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse.FinalArrayStudentSubject;
import anandniketan.com.anbcteacher.Models.StudentAssignSubjectResponse.StudentSubject;
import anandniketan.com.anbcteacher.R;
import anandniketan.com.anbcteacher.Utility.Utility;


public class AddMarksFragment extends Fragment {
    LeaveMainModel termDetailResponse, standardsectionResponse, testmarkssubjectResponse, marksResponse;
    HashMap<Integer, String> spinnerTermIdNameMap;
    HashMap<Integer, String> spinnerclassIdNameMap;
    HashMap<Integer, String> spinnertestmarkssubjectIdNameMap;
    String[] spinnertermnameIdArray;
    String[] spinnerclassnameIdArray;
    String[] spinnertestmarkssubjectnameIdArray;
    String termIdStr, termNameStr, sectionNameStr, sectionIdStr, testsubjectIdStr, testsubjectNameStr, detailIdStr = "";
    AddMarksAdapter addMarksAdapter;
    private View rootView;
    private TextView txtNoRecordsMarks;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private GetTermAsyncTask getTermAsyncTask = null;
    private GetStandardSectionForMarksAsyncTask getStandardSectionForMarksAsyncTask = null;
    private GetTestForMarksAsyncTask getTestForMarksAsyncTask = null;
    private GetMarksAsyncTask getMarksAsyncTask = null;
    private InsertMarksAsyncTask insertMarksAsyncTask = null;
    private LinearLayout header_list;
    private Spinner term_spinner, section_spinner, subject_spinner;
    private ListView student_list_rcv;
    private TextView total_mark_txt;
    private ImageView insert_attendance_img;
    private static boolean keyboardHidden = true;
    private static int reduceHeight = 0;

    public AddMarksFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_marks, container, false);
        mContext = getActivity();

        initViews();
        setListners();
        //getTermData();
        return rootView;
    }


    public void initViews() {

        txtNoRecordsMarks = (TextView) rootView.findViewById(R.id.txtNoRecordsMarks);
        header_list = (LinearLayout) rootView.findViewById(R.id.header_list);
        term_spinner = (Spinner) rootView.findViewById(R.id.term_spinner);
        section_spinner = (Spinner) rootView.findViewById(R.id.section_spinner);
        subject_spinner = (Spinner) rootView.findViewById(R.id.subject_spinner);
        student_list_rcv = (ListView) rootView.findViewById(R.id.student_list_rcv);
        total_mark_txt = (TextView) rootView.findViewById(R.id.total_mark_txt);
        insert_attendance_img = (ImageView) rootView.findViewById(R.id.insert_attendance_img);


        setUserVisibleHint(true);

        //final View decorView = getActivity().getWindow().getDecorView();

//        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Rect rect = new Rect();
//                decorView.getWindowVisibleDisplayFrame(rect);
//
//                int displayHeight = rect.bottom - rect.top;
//                int height = decorView.getHeight();
//                boolean keyboardHiddenTemp = (double)displayHeight / height > 0.8 ;
//                int mylistviewHeight = student_list_rcv.getMeasuredHeight();
//
//                if (keyboardHiddenTemp != keyboardHidden) {
//                    keyboardHidden = keyboardHiddenTemp;
//
//                    if (!keyboardHidden) {
//                        reduceHeight = height - displayHeight;
//                        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,mylistviewHeight - reduceHeight);
//                        student_list_rcv.setLayoutParams(mParam);
//                        student_list_rcv.requestLayout();
//
//                    } else {
//                        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,mylistviewHeight + reduceHeight);
//                        student_list_rcv.setLayoutParams(mParam);
//                        student_list_rcv.requestLayout();
//                    }
//                }
//
//            }
//        });
      //  student_list_rcv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            getTermData();
        }
        // execute your data loading logic.
    }


    //...
//...


    public void setListners() {
        term_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String termName = term_spinner.getSelectedItem().toString();
                String termId = spinnerTermIdNameMap.get(term_spinner.getSelectedItemPosition());


                Log.d("value", termName + " " + termId);
                termIdStr = termId.toString();
                termNameStr = termName;
                Log.d("termIdStr", termIdStr);
                getSectionData();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sectionName = section_spinner.getSelectedItem().toString();
                String sectionId = spinnerclassIdNameMap.get(section_spinner.getSelectedItemPosition());


                Log.d("value", sectionName + " " + sectionId);
                sectionIdStr = sectionId.toString();
                sectionNameStr = sectionName;
                Log.d("sectionIdStr", sectionIdStr);

                total_mark_txt.setText("");
                getTestMarksData();

                if (sectionNameStr.equalsIgnoreCase("--Select--")){
                    student_list_rcv.setVisibility(View.GONE);
                    header_list.setVisibility(View.GONE);
                    insert_attendance_img.setVisibility(View.GONE);
                    txtNoRecordsMarks.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subject_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String testsubjectName = subject_spinner.getSelectedItem().toString();
                String testsubjectId = spinnertestmarkssubjectIdNameMap.get(subject_spinner.getSelectedItemPosition());


                Log.d("value", testsubjectName + " " + testsubjectId);
                testsubjectIdStr = testsubjectId.toString();
                testsubjectNameStr = testsubjectName;
                Log.d("testsubjectIdStr", testsubjectIdStr);
                total_mark_txt.setText("");

                if (!testsubjectNameStr.equalsIgnoreCase("--Select--")) {
                    getMarkData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        insert_attendance_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> newArray = new ArrayList<>();
                for (int i = 0; i < addMarksAdapter.getCount(); i++) {
                    LeaveFinalArray studentInfoObj = (LeaveFinalArray) addMarksAdapter.getItem(i);
                    int stuId = studentInfoObj.getStudentID();
                    int marksId = Integer.parseInt(studentInfoObj.getMarkID());
                    String marks = studentInfoObj.getMark();
                    String grno = studentInfoObj.getGRNO();
                    boolean isEnable = false;
                    String studentString = "";
//                    for (int j = 0; j < studentInfoObj.; j++) {
                    LeaveFinalArray subObj = studentInfoObj;//.getFinalArray().get(j);
                    String status = subObj.getCheckStatus();
                    try {


//                    if(status != null) {
                        if (status.equalsIgnoreCase("1")) {
                            if (Float.parseFloat(marks) <= Float.parseFloat(total_mark_txt.getText().toString())) {
                                if (!isEnable) {
                                    studentString = String.valueOf(stuId) + "," + subObj.getMarkID() + "," + subObj.getMark() + "," + subObj.getGRNO();
                                    isEnable = true;
                                } else {
                                    studentString = studentString + "|" + String.valueOf(stuId) + "," + subObj.getMarkID() + "," + subObj.getMark() + "," + subObj.getGRNO();
                                }
                            } else {
                                Utility.ping(mContext, "Please enter proper marks");
                                break;
                            }
                        }
                    }catch (Exception ex){

                        ex.printStackTrace();
                    }
//                    }else {
//                        Utility.ping(mContext, "Please Enter marks");
//                        break;
//                    }
//                    }
                    newArray.add(studentString);
                }
                if (newArray.size() > 0) {
                    detailIdStr = "";
                    if (newArray.size()== marksResponse.getFinalArray().size()) {
                        for (String s : newArray) {
                            if (!s.equals("")) {
                                detailIdStr = detailIdStr + "|" + s;
                            }

                        }
                        detailIdStr = detailIdStr.substring(1, detailIdStr.length());
                        Log.d("detailIdStr ", detailIdStr);

                        getInsertMarksData();
                    }else{

                    }
                }
            }
        });

    }

    public void getTermData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            //  progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        getTermAsyncTask = new GetTermAsyncTask(params);
                        termDetailResponse = getTermAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (termDetailResponse.getFinalArray().size() > 0) {
                                    filltermspinner();
                                } else {
                                    progressDialog.dismiss();
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

    public void filltermspinner() {
        ArrayList<Integer> termId = new ArrayList<Integer>();
        ArrayList<String> termname = new ArrayList<String>();

        for (int j = 0; j < termDetailResponse.getFinalArray().size(); j++) {
            termId.add(termDetailResponse.getFinalArray().get(j).getTermId());
        }

        for (int k = 0; k < termDetailResponse.getFinalArray().size(); k++) {
            termname.add(termDetailResponse.getFinalArray().get(k).getTerm());
        }
        String[] spinnertermIdArray = new String[termId.size()];
        spinnertermIdArray = new String[termname.size()];

        spinnerTermIdNameMap = new HashMap<Integer, String>();
        for (int i = 0; i < termId.size(); i++) {
            spinnerTermIdNameMap.put(i, String.valueOf(termId.get(i)));
            spinnertermIdArray[i] = termname.get(i).trim();
        }
        Log.d("TestArray", "" + termname);
//        HashSet hs = new HashSet();
//        hs.addAll(testname);
//        testname.clear();
//        testname.addAll(hs);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(term_spinner);

            popupWindow.setHeight(spinnertermIdArray.length > 5 ? 500 : spinnertermIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adaptertest = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnertermIdArray);
        term_spinner.setAdapter(adaptertest);
    }

    public void getSectionData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            //  progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        params.put("TermID", termIdStr);
                        getStandardSectionForMarksAsyncTask = new GetStandardSectionForMarksAsyncTask(params);
                        standardsectionResponse = getStandardSectionForMarksAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (standardsectionResponse.getFinalArray().size() > 0) {
                                    fillsectionspinner();
                                } else {
                                    progressDialog.dismiss();
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

    public void fillsectionspinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");

        ArrayList<Integer> firstValueId = new ArrayList<>();
        firstValueId.add(0);

        ArrayList<Integer> classId = new ArrayList<Integer>();
        ArrayList<String> classname = new ArrayList<String>();
        for (int m = 0; m < firstValueId.size(); m++) {
            classId.add(firstValueId.get(m));
            for (int j = 0; j < standardsectionResponse.getFinalArray().size(); j++) {
                classId.add(Integer.valueOf(standardsectionResponse.getFinalArray().get(j).getClassID()));
            }
        }

        for (int z = 0; z < firstValue.size(); z++) {
            classname.add(firstValue.get(z));
            for (int k = 0; k < standardsectionResponse.getFinalArray().size(); k++) {
                classname.add(standardsectionResponse.getFinalArray().get(k).getClassName());
            }
        }

        String[] spinnerclassIdArray = new String[classId.size()];
        spinnerclassIdArray = new String[classname.size()];

        spinnerclassIdNameMap = new HashMap<Integer, String>();
        for (int i = 0; i < classId.size(); i++) {
            spinnerclassIdNameMap.put(i, String.valueOf(classId.get(i)));
            spinnerclassIdArray[i] = classname.get(i).trim();
        }
        Log.d("TestArray", "" + classname);
//        HashSet hs = new HashSet();
//        hs.addAll(testname);
//        testname.clear();
//        testname.addAll(hs);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(section_spinner);

            popupWindow.setHeight(spinnerclassIdArray.length > 5 ? 500 : spinnerclassIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adaptertest = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerclassIdArray);
        section_spinner.setAdapter(adaptertest);
    }

    public void getTestMarksData() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            //  progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        params.put("TermID", termIdStr);
                        params.put("ClassID", sectionIdStr);
                        getTestForMarksAsyncTask = new GetTestForMarksAsyncTask(params);
                        testmarkssubjectResponse = getTestForMarksAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (testmarkssubjectResponse.getFinalArray().size() > 0) {
                                    filltestsubjectspinner();
                                } else {
                                    progressDialog.dismiss();
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

    public void filltestsubjectspinner() {
        ArrayList<String> firstValue = new ArrayList<>();
        firstValue.add("--Select--");

        ArrayList<String> firstValueId = new ArrayList<>();
        firstValueId.add("0");

        ArrayList<String> testsubjectId = new ArrayList<String>();
        ArrayList<String> testsubjectname = new ArrayList<String>();
        for (int m = 0; m < firstValueId.size(); m++) {
            testsubjectId.add(firstValueId.get(m));
            for (int j = 0; j < testmarkssubjectResponse.getFinalArray().size(); j++) {
                testsubjectId.add(testmarkssubjectResponse.getFinalArray().get(j).getSubjectID());
            }
        }

        for (int z = 0; z < firstValue.size(); z++) {
            testsubjectname.add(firstValue.get(z));
            for (int k = 0; k < testmarkssubjectResponse.getFinalArray().size(); k++) {
                testsubjectname.add(testmarkssubjectResponse.getFinalArray().get(k).getSubjectName());
            }
        }

        String[] spinnerclassIdArray = new String[testsubjectId.size()];
        spinnerclassIdArray = new String[testsubjectname.size()];

        spinnertestmarkssubjectIdNameMap = new HashMap<Integer, String>();
        for (int i = 0; i < testsubjectId.size(); i++) {
            spinnertestmarkssubjectIdNameMap.put(i, String.valueOf(testsubjectId.get(i)));
            spinnerclassIdArray[i] = testsubjectname.get(i).trim();
        }
        Log.d("TestArray", "" + testsubjectname);
//        HashSet hs = new HashSet();
//        hs.addAll(testname);
//        testname.clear();
//        testname.addAll(hs);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(subject_spinner);

            popupWindow.setHeight(spinnerclassIdArray.length > 5 ? 500 : spinnerclassIdArray.length * 100);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }

        ArrayAdapter<String> adaptertest = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, spinnerclassIdArray);
        subject_spinner.setAdapter(adaptertest);
    }

    public void getMarkData() {
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
                        params.put("TermID", termIdStr);
                        params.put("ClassID", sectionIdStr);
                        params.put("SubjectID", testsubjectIdStr);

                        getMarksAsyncTask = new GetMarksAsyncTask(params);
                        marksResponse = getMarksAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (marksResponse.getFinalArray() != null) {
                                    if (marksResponse.getFinalArray().size() > 0) {
                                        header_list.setVisibility(View.VISIBLE);
                                        student_list_rcv.setVisibility(View.VISIBLE);
                                        txtNoRecordsMarks.setVisibility(View.GONE);
                                        insert_attendance_img.setVisibility(View.VISIBLE);
                                        total_mark_txt.setText(marksResponse.getTotalMarks());
                                        addMarksAdapter = new AddMarksAdapter(mContext, marksResponse);
//                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
//                                        student_list_rcv.setLayoutManager(mLayoutManager);
//                                        student_list_rcv.setItemAnimator(new DefaultItemAnimator());
                                        student_list_rcv.setAdapter(addMarksAdapter);

                                    } else {
                                        txtNoRecordsMarks.setVisibility(View.VISIBLE);
                                        header_list.setVisibility(View.GONE);
                                        student_list_rcv.setVisibility(View.GONE);
                                        insert_attendance_img.setVisibility(View.GONE);
                                        progressDialog.dismiss();
                                    }
                                } else {
                                    txtNoRecordsMarks.setVisibility(View.VISIBLE);
                                    header_list.setVisibility(View.GONE);
                                    student_list_rcv.setVisibility(View.GONE);
                                    progressDialog.dismiss();
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

    public void getInsertMarksData() {
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
                        params.put("TermID", termIdStr);
                        params.put("ClassID", sectionIdStr);
                        params.put("SubjectID", testsubjectIdStr);
                        params.put("DetailID", detailIdStr);
                        params.put("SubjectName", testsubjectNameStr);
                        params.put("TotalMarks",total_mark_txt.getText().toString());
                        insertMarksAsyncTask = new InsertMarksAsyncTask(params);
                        testmarkssubjectResponse = insertMarksAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (testmarkssubjectResponse != null) {
                                    if (testmarkssubjectResponse.getSuccess().equalsIgnoreCase("True")) {
                                        Utility.ping(mContext, testmarkssubjectResponse.getFinalArray().get(0).getMessage());
                                    } else {
                                        progressDialog.dismiss();
                                    }
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
}
