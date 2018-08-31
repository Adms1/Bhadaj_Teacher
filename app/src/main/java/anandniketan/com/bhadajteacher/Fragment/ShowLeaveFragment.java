package anandniketan.com.bhadajteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajteacher.Activities.LoginActivity;
import anandniketan.com.bhadajteacher.Adapter.LeaveDetailAdapter;
import anandniketan.com.bhadajteacher.Adapter.LeaveListAdapter;
import anandniketan.com.bhadajteacher.AsyncTasks.DeleteStaffLeaveAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.GetLeaveDataAsyncTask;
import anandniketan.com.bhadajteacher.Interfacess.onDeleteButton;
import anandniketan.com.bhadajteacher.Interfacess.onEditTest;
import anandniketan.com.bhadajteacher.Models.LeaveModel.LeaveFinalArray;
import anandniketan.com.bhadajteacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class ShowLeaveFragment extends Fragment implements View.OnClickListener {
    Fragment fragment;
    FragmentManager fragmentManager;
    Context mContext;
    View line_view;
    LeaveDetailAdapter leaveDetailAdapter;
    LeaveListAdapter leaveListAdapter;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<LeaveFinalArray>> listDataChild = new HashMap<>();
    private View rootView;
    private FloatingActionButton add_leave_fab_btn;
    private Button btnLogout, btnBacktest_homework;
    private TextView txtNoRecordsClasswork;
    private LinearLayout header_linear, header_leave;
    private RecyclerView listLeavedetail;
    private ExpandableListView listLeave;
    private ProgressDialog progressDialog = null;
    private GetLeaveDataAsyncTask getLeaveDataAsyncTask = null;
    private DeleteStaffLeaveAsyncTask deleteStaffLeaveAsyncTask=null;
    private LeaveMainModel leaveDataResponse;
    private int lastExpandedPosition = -1;
    String deleteLeaveIdStr;
    ArrayList<String> editLeaveDetailArray;

    public ShowLeaveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_leave, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        add_leave_fab_btn = (FloatingActionButton) rootView.findViewById(R.id.add_leave_fab_btn);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnBacktest_homework = (Button) rootView.findViewById(R.id.btnBacktest_homework);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        header_linear = (LinearLayout) rootView.findViewById(R.id.header_linear);
        listLeave = (ExpandableListView) rootView.findViewById(R.id.listLeave);
        header_leave = (LinearLayout) rootView.findViewById(R.id.header_leave);
        listLeavedetail = (RecyclerView) rootView.findViewById(R.id.listLeavedetail);
        getLeaveDetail();
    }


    public void setListners() {
        add_leave_fab_btn.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnBacktest_homework.setOnClickListener(this);
        listLeave.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    listLeave.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_leave_fab_btn:
                fragment = new LeaveFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.btnLogout:
                new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme))
                        .setCancelable(false)
                        .setTitle("Logout")
                        .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                        .setMessage("Are you sure you want to logout? ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Utility.setPref(mContext, "StaffID", "");
                                Utility.setPref(mContext, "Emp_Code", "");
                                Utility.setPref(mContext, "Emp_Name", "");
                                Utility.setPref(mContext, "DepratmentID", "");
                                Utility.setPref(mContext, "DesignationID", "");
                                Utility.setPref(mContext, "DeviceId", "");
                                Utility.setPref(mContext, "unm", "");
                                Utility.setPref(mContext, "pwd", "");
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                getActivity().startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();
                break;
            case R.id.btnBacktest_homework:
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
                break;
        }
    }

    public void getLeaveDetail() {
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


                        getLeaveDataAsyncTask = new GetLeaveDataAsyncTask(params);
                        leaveDataResponse = getLeaveDataAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (leaveDataResponse.getSuccess().equalsIgnoreCase("True")) {
                                    if (leaveDataResponse.getFinalArray().size() > 0) {
                                        header_linear.setVisibility(View.VISIBLE);
                                        LeaveDetailList();
                                    } else {
                                        header_linear.setVisibility(View.GONE);

                                    }
                                    if (leaveDataResponse.getLeaveDetails().size() > 0) {
                                        header_leave.setVisibility(View.VISIBLE);
                                        leaveDetailAdapter = new LeaveDetailAdapter(mContext, leaveDataResponse);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                                        listLeavedetail.setLayoutManager(mLayoutManager);
                                        listLeavedetail.setItemAnimator(new DefaultItemAnimator());
                                        listLeavedetail.setAdapter(leaveDetailAdapter);

                                    } else {
                                        header_leave.setVisibility(View.GONE);
                                        listLeavedetail.setVisibility(View.GONE);
                                    }
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

    public void LeaveDetailList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<LeaveFinalArray>>();

        for (int j = 0; j < leaveDataResponse.getFinalArray().size(); j++) {
            listDataHeader.add(leaveDataResponse.getFinalArray().get(j).getCreateDate() + "|" +
                    leaveDataResponse.getFinalArray().get(j).getLeaveDays() + "|" +
                    leaveDataResponse.getFinalArray().get(j).getStatus());

            ArrayList<LeaveFinalArray> rows = new ArrayList<LeaveFinalArray>();
            rows.add(leaveDataResponse.getFinalArray().get(j));
            listDataChild.put(listDataHeader.get(j), rows);
        }

        leaveListAdapter = new LeaveListAdapter(mContext, listDataHeader, listDataChild, new onEditTest() {
            @Override
            public void getEditTest() {
                editLeaveDetailArray=new ArrayList<>();
                editLeaveDetailArray=leaveListAdapter.getAllId();
                fragment = new LeaveFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("LeaveDetail", editLeaveDetailArray);
                fragment.setArguments(bundle);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        }, new onDeleteButton() {
            @Override
            public void deleteSentMessage() {
                deleteLeaveIdStr=leaveListAdapter.deleteId;
                new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme))
                        .setCancelable(false)
                        .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                        .setMessage("Are you sure you want to delete leave? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteLeaveDetail();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();


            }
        });
        listLeave.setAdapter(leaveListAdapter);
        leaveListAdapter.notifyDataSetChanged();
        listLeave.deferNotifyDataSetChanged();
    }

    public void deleteLeaveDetail() {
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
                        params.put("LeaveID",deleteLeaveIdStr);


                        deleteStaffLeaveAsyncTask = new DeleteStaffLeaveAsyncTask(params);
                        leaveDataResponse = deleteStaffLeaveAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (leaveDataResponse.getSuccess().equalsIgnoreCase("True")) {
                                          Utility.ping(mContext,"Leave delete succesfully");
                                          getLeaveDetail();
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

}
