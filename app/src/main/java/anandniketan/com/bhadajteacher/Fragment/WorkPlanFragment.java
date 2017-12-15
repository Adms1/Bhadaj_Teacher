package anandniketan.com.bhadajteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajteacher.Activities.LoginActivity;
import anandniketan.com.bhadajteacher.Adapter.ExpandableListAdapterWorkPlan;
import anandniketan.com.bhadajteacher.AsyncTasks.GetTeacherWorkPlanAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.TeacherUpdateWorkPlanCompletionAsyncTask;
import anandniketan.com.bhadajteacher.Interfacess.onWorkStatus;
import anandniketan.com.bhadajteacher.Models.WorkPlanResponse.UpdateWorkStatusModel;
import anandniketan.com.bhadajteacher.Models.WorkPlanResponse.WorkPlanDatum;
import anandniketan.com.bhadajteacher.Models.WorkPlanResponse.WorkPlanMainResponseModel;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class WorkPlanFragment extends Fragment {
    private View rootView;
    private Context mContext;
    private Button btnBack_workplan, btnFilterWorkPlan, btnLogout;
    private TextView txtNoRecordsworkplan;
    private Spinner spinfromdate, spintodate;
    private LinearLayout workplan_header;
    private ExpandableListView lvExpworkplan;
    private int lastExpandedPosition = -1;
    private ArrayList<String> year1 = new ArrayList<>();
    private ArrayList<String> monthyear = new ArrayList<String>();
    private String SelectedMonthfrom, SelectedYearfrom, SelectedMonthto, SelectedYearto;
    private ProgressDialog progressDialog = null;
    private GetTeacherWorkPlanAsyncTask getTeacherWorkPlanAsyncTask = null;
    private WorkPlanMainResponseModel workPlanMainResponseModel;
    private RelativeLayout date_rel;
    private ExpandableListAdapterWorkPlan expandableListAdapterWorkPlan;
    List<String> listDataHeader;
    HashMap<String, ArrayList<WorkPlanDatum>> listDataChild;
    private TeacherUpdateWorkPlanCompletionAsyncTask teacherUpdateWorkPlanCompletionAsyncTask = null;
    private UpdateWorkStatusModel updateWorkStatusModel;
    private String WorkIDstr, WorkPlanIDstr, TeacherWorkstr, CompleteStatusstr, FromDatestr, ToDatestr, Remark, getvalue, selctedValuefrom = "", selctedValueTo = "";


    public WorkPlanFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_work_plan, container, false);
        mContext = getActivity();

        initViews();
        return rootView;
    }

    public void initViews() {
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnBack_workplan = (Button) rootView.findViewById(R.id.btnBack_workplan);
        btnFilterWorkPlan = (Button) rootView.findViewById(R.id.btnFilterWorkPlan);
        txtNoRecordsworkplan = (TextView) rootView.findViewById(R.id.txtNoRecordsworkplan);
        spinfromdate = (Spinner) rootView.findViewById(R.id.spinfromdate);
        spintodate = (Spinner) rootView.findViewById(R.id.spintodate);
        workplan_header = (LinearLayout) rootView.findViewById(R.id.workplan_header);
        lvExpworkplan = (ExpandableListView) rootView.findViewById(R.id.lvExpworkplan);
        date_rel = (RelativeLayout) rootView.findViewById(R.id.date_rel);

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinfromdate);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(spintodate);
            // Set popupWindow height to 500px
            popupWindow.setHeight(300);
            popupWindow1.setHeight(300);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }



        fillSpinner();
        setListner();
    }

    public void setListner() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        btnBack_workplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        lvExpworkplan.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpworkplan.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        btnFilterWorkPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeworkData();
            }
        });
        spinfromdate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selctedValuefrom = adapterView.getItemAtPosition(i).toString();
                Log.d("fromdate", selctedValuefrom);
                String[] splitvalue = selctedValuefrom.split("\\-");

                SelectedMonthfrom = splitvalue[0];
                SelectedYearfrom = splitvalue[1].replaceFirst(">", "");
                Log.d("SelectedMonthfrom", SelectedMonthfrom + "|" + SelectedYearfrom);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spintodate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selctedValueTo = adapterView.getItemAtPosition(i).toString();
                Log.d("Todate", selctedValueTo);
                String[] splitvalueyear = selctedValueTo.split("\\-");
                SelectedMonthto = splitvalueyear[0];
                SelectedYearto = splitvalueyear[1].replaceFirst(">", "");
                Log.d("SelectedMonthto", SelectedMonthto + "|" + SelectedYearto);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    public void fillSpinner() {
        final Calendar calendar = Calendar.getInstance();
        int currentyear = calendar.get(Calendar.YEAR);
        int nextyear = calendar.get(Calendar.YEAR) + 1;
        int previousyear = calendar.get(Calendar.YEAR) - 1;
        year1.add(String.valueOf(previousyear));
        year1.add(String.valueOf(currentyear));
        year1.add(String.valueOf(nextyear));

        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayList<String> months = new ArrayList<>();
        for (int i = 0; i < getResources().getStringArray(R.array.month).length; i++) {
            months.add(getResources().getStringArray(R.array.month)[i]);
        }

        for (int k = 0; k < year1.size(); k++) {
            for (int j = 0; j < months.size(); j++) {
                monthyear.add(months.get(j) + "->" + year1.get(k));
            }
        }
        Log.d("monthyear", "" + monthyear);

        ArrayAdapter<String> adapterMonthyearfrom = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, monthyear);
        spinfromdate.setAdapter(adapterMonthyearfrom);
//        setDynamicHeight(spinfromdate);

        ArrayAdapter<String> adapterMonthyearto = new ArrayAdapter<String>(mContext, R.layout.spinner_layout, monthyear);
        spintodate.setAdapter(adapterMonthyearto);
    }



    public void setSelection() {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH) + 1;
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        ArrayList<String> setmonthyear = new ArrayList<>();
        ArrayList<String> months = new ArrayList<>();
        for (int i = 0; i < getResources().getStringArray(R.array.month).length; i++) {
            months.add(getResources().getStringArray(R.array.month)[i]);
        }
//        spinfromdate.setSelection(months.indexOf(months.get(mm - 1)));
        Log.d("spinfromdate", "" + spinfromdate.getSelectedItem().toString());
        ArrayList<String> year2 = new ArrayList<>();
        for (int i = 0; i < year1.size(); i++) {
            year2.add(year1.get(i));
        }
        String putvalue = String.valueOf(months.indexOf(months.get(mm - 1)));
        String putvalueyear = String.valueOf(year2.indexOf(year2.get(yy)));
//        spintodate.setSelection(Integer.parseInt(putvalue));
        Log.d("putvalue", putvalue);
        Log.d("putvalueyear", putvalueyear);
        setmonthyear.add(putvalue + "->" + putvalueyear);
        spinfromdate.setSelection(setmonthyear.indexOf(setmonthyear));
    }

    
    public void getHomeworkData() {
        if (!SelectedMonthfrom.equalsIgnoreCase("") && !SelectedYearfrom.equalsIgnoreCase("")
                && !SelectedMonthto.equalsIgnoreCase("") && !SelectedYearto.equalsIgnoreCase("")) {
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
                            params.put("StaffID", Utility.getPref(mContext, "StaffID"));//
                            params.put("fromMonth", SelectedMonthfrom);
                            params.put("fromYear", SelectedYearfrom);
                            params.put("ToMonth", SelectedMonthto);
                            params.put("ToYear", SelectedYearto);

                            getTeacherWorkPlanAsyncTask = new GetTeacherWorkPlanAsyncTask(params);
                            workPlanMainResponseModel = getTeacherWorkPlanAsyncTask.execute().get();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    if (workPlanMainResponseModel.getFinalArray().size() > 0) {
                                        txtNoRecordsworkplan.setVisibility(View.GONE);
                                        date_rel.setVisibility(View.VISIBLE);
                                        workplan_header.setVisibility(View.VISIBLE);
                                        lvExpworkplan.setVisibility(View.VISIBLE);
                                        progressDialog.dismiss();
                                        prepareList();
                                        expandableListAdapterWorkPlan = new ExpandableListAdapterWorkPlan(getActivity(), listDataHeader, listDataChild, new onWorkStatus() {
                                            @Override
                                            public void onWorkStatus() {
                                                Dialog();
                                            }
                                        });
                                        lvExpworkplan.setAdapter(expandableListAdapterWorkPlan);
                                        lvExpworkplan.deferNotifyDataSetChanged();
                                    } else {
                                        progressDialog.dismiss();
                                        txtNoRecordsworkplan.setVisibility(View.VISIBLE);
                                        workplan_header.setVisibility(View.GONE);
                                        lvExpworkplan.setVisibility(View.GONE);
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
        } else {
            progressDialog.dismiss();
            Utility.ping(mContext, "Blank filed not allowed.");
        }

    }

    public void prepareList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<WorkPlanDatum>>();
        for (int i = 0; i < workPlanMainResponseModel.getFinalArray().size(); i++) {
            listDataHeader.add(workPlanMainResponseModel.getFinalArray().get(i).getStandard() + "|" +
                    workPlanMainResponseModel.getFinalArray().get(i).getClass_() + "|" +
                    workPlanMainResponseModel.getFinalArray().get(i).getSubject() + "|" +
                    workPlanMainResponseModel.getFinalArray().get(i).getMonth());

            ArrayList<WorkPlanDatum> rows = new ArrayList<WorkPlanDatum>();
            for (int j = 0; j < workPlanMainResponseModel.getFinalArray().get(i).getData().size(); j++) {
                rows.add(workPlanMainResponseModel.getFinalArray().get(i).getData().get(j));
            }
            listDataChild.put(listDataHeader.get(i), rows);
        }
    }

    public void Dialog() {
        new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme))
                .setCancelable(false)
                .setTitle("Logout")
                .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                .setMessage("Are you sure you want to update status ? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        updateWorkStatus();
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

    public void updateWorkStatus() {
        getvalue = String.valueOf(expandableListAdapterWorkPlan.getDataworkplan());
        Log.d("getvalue", getvalue);
        getvalue = getvalue.substring(1, getvalue.length() - 1);
        String[] spiltvalue = getvalue.split("\\|");
        WorkIDstr = spiltvalue[0];
        WorkPlanIDstr = spiltvalue[1];
        TeacherWorkstr = spiltvalue[2];
        CompleteStatusstr = spiltvalue[3];
        FromDatestr = spiltvalue[4];
        ToDatestr = spiltvalue[5];
        Remark = spiltvalue[6];
        Log.d("ToDatestr", ToDatestr);
        Log.d("Remark", Remark);

        Log.d("value", WorkIDstr + "," + WorkPlanIDstr + "," + TeacherWorkstr + "," + CompleteStatusstr + "," + FromDatestr + "," + ToDatestr + "," + Remark);
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
                        params.put("WorkID", WorkIDstr);
                        params.put("WorkPlanID", WorkPlanIDstr);
                        params.put("TeacherWork", TeacherWorkstr);
                        params.put("CompleteStatus", CompleteStatusstr);
                        params.put("FromDate", FromDatestr);
                        params.put("ToDate", ToDatestr);
                        params.put("Remark", Remark);


                        teacherUpdateWorkPlanCompletionAsyncTask = new TeacherUpdateWorkPlanCompletionAsyncTask(params);
                        updateWorkStatusModel = teacherUpdateWorkPlanCompletionAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (updateWorkStatusModel.getSuccess().equalsIgnoreCase("True")) {
                                    progressDialog.dismiss();
                                    Utility.ping(mContext, "Update Status");
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
