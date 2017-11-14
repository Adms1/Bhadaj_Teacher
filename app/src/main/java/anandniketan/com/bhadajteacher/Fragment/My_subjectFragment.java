package anandniketan.com.bhadajteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.bhadajteacher.Adapter.MysubjectAdapetr;
import anandniketan.com.bhadajteacher.AsyncTasks.GetTeacherAssignedSubjectAsyncTask;
import anandniketan.com.bhadajteacher.Models.TeacherAssignedSubjectModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;

public class My_subjectFragment extends Fragment {

    public My_subjectFragment() {
        // Required empty public constructor
    }

    private Context mContext;
    private View rootView;
    private ProgressDialog progressDialog = null;
    private GetTeacherAssignedSubjectAsyncTask getTeacherAssignedSubjectAsyncTask = null;
    private ArrayList<TeacherAssignedSubjectModel> teacherAssignedSubjectModels = new ArrayList<>();
    MysubjectAdapetr mysubjectAdapetr = null;
    private ListView subject_list;
    private LinearLayout header_linear;
    private TextView txtNoRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_subject, container, false);
        mContext = getActivity();

        init();
        setListner();


        return rootView;
    }

    public void init() {


        header_linear = (LinearLayout) rootView.findViewById(R.id.header_linear);
        txtNoRecords = (TextView) rootView.findViewById(R.id.txtNoRecords);
        subject_list = (ListView) rootView.findViewById(R.id.subject_list);

        setUserVisibleHint(true);
    }

    public void setListner() {
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            setTodayschedule();
        }
        // execute your data loading logic.
    }
    public void setTodayschedule() {
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

                        getTeacherAssignedSubjectAsyncTask = new GetTeacherAssignedSubjectAsyncTask(params);
                        teacherAssignedSubjectModels = getTeacherAssignedSubjectAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (teacherAssignedSubjectModels.size() > 0) {
                                    txtNoRecords.setVisibility(View.GONE);

                                    mysubjectAdapetr = new MysubjectAdapetr(getActivity(), teacherAssignedSubjectModels);
                                    subject_list.setAdapter(mysubjectAdapetr);
                                    subject_list.deferNotifyDataSetChanged();
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecords.setVisibility(View.VISIBLE);
                                    header_linear.setVisibility(View.GONE);
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
