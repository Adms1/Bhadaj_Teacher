package anandniketan.com.bhadajteacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.bhadajteacher.Adapter.ExpandableListAdapterInbox;
import anandniketan.com.bhadajteacher.AsyncTasks.PTMTeacherStudentGetDetailAsyncTask;
import anandniketan.com.bhadajteacher.AsyncTasks.PTMTeacherStudentInsertDetailAsyncTask;
import anandniketan.com.bhadajteacher.Interfacess.onInboxRead;
import anandniketan.com.bhadajteacher.Models.MainPtmSentMessageResponse;
import anandniketan.com.bhadajteacher.Models.PTMInboxResponse.FinalArrayInbox;
import anandniketan.com.bhadajteacher.Models.PTMInboxResponse.MainPtmInboxResponse;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class InboxFragment extends Fragment {
    private View rootView;
    private TextView txtNoRecordsinbox;
    private Context mContext;
    private ProgressDialog progressDialog = null;
    private PTMTeacherStudentGetDetailAsyncTask ptmTeacherStudentGetDetailAsyncTask = null;
    private int lastExpandedPosition = -1;
    private LinearLayout inbox_header;


    ExpandableListAdapterInbox expandableListAdapterInbox;
    ExpandableListView lvExpinbox;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<FinalArrayInbox>> listDataChild = new HashMap<>();
    MainPtmInboxResponse response;
    String messageidstr, FromIdstr, ToIdstr, messageDatestr, messageSubjectstr, messageMessageLinestr;
    private PTMTeacherStudentInsertDetailAsyncTask getPTMTeacherStudentInsertDetailAsyncTask = null;
    MainPtmSentMessageResponse mainPtmSentMessageResponse;

    public InboxFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_inbox, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        txtNoRecordsinbox = (TextView) rootView.findViewById(R.id.txtNoRecordsinbox);
        lvExpinbox = (ExpandableListView) rootView.findViewById(R.id.lvExpinbox);
        inbox_header = (LinearLayout) rootView.findViewById(R.id.inbox_header);
        setUserVisibleHint(true);

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            getInboxData();
        }
        // execute your data loading logic.
    }

    public void setListners() {
        lvExpinbox.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lvExpinbox.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    public void getInboxData() {
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
                        params.put("UserID", Utility.getPref(mContext, "StaffID"));
                        params.put("UserType", "staff");
                        params.put("MessgaeType", "Inbox");
                        ptmTeacherStudentGetDetailAsyncTask = new PTMTeacherStudentGetDetailAsyncTask(params);
                        response = ptmTeacherStudentGetDetailAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (response.getFinalArray().size() > 0) {
                                    txtNoRecordsinbox.setVisibility(View.GONE);
                                    inbox_header.setVisibility(View.VISIBLE);
                                    setExpandableListData();
                                    expandableListAdapterInbox = new ExpandableListAdapterInbox(getActivity(), listDataHeader, listDataChild, new onInboxRead() {
                                        @Override
                                        public void readMessageStatus() {
                                            ArrayList<String> array = expandableListAdapterInbox.getData();
                                            Log.d("array", "" + array.size());
                                            for (int i = 0; i < array.size(); i++) {
                                                String spiltvalue = array.get(i);
                                                Log.d("spiltvalue", spiltvalue);
                                                String value[] = spiltvalue.trim().split("\\|");

                                                messageidstr = value[0];
                                                FromIdstr = value[1];
                                                ToIdstr = value[2];
                                                messageDatestr = value[3];
                                                messageSubjectstr = value[4];
                                                messageMessageLinestr = value[5];
                                                Log.d("messageid", messageidstr);
                                            }
                                            if (Utility.isNetworkConnected(mContext)) {
                                                new Thread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        try {
                                                            HashMap<String, String> params = new HashMap<String, String>();
                                                            params.put("MessageID", messageidstr);
                                                            params.put("FromID", FromIdstr);
                                                            params.put("ToID", ToIdstr);
                                                            params.put("MeetingDate", messageDatestr);
                                                            params.put("SubjectLine", messageSubjectstr);
                                                            params.put("Description", messageMessageLinestr);
                                                            params.put("Flag", "Student");

                                                            getPTMTeacherStudentInsertDetailAsyncTask = new PTMTeacherStudentInsertDetailAsyncTask(params);
                                                            mainPtmSentMessageResponse = getPTMTeacherStudentInsertDetailAsyncTask.execute().get();
                                                            getActivity().runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (mainPtmSentMessageResponse.getFinalArray().size() >= 0) {
                                                                        getInboxData();
                                                                    } else {
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
                                    });
                                    lvExpinbox.setAdapter(expandableListAdapterInbox);
                                    expandableListAdapterInbox.notifyDataSetChanged();
                                    lvExpinbox.deferNotifyDataSetChanged();
                                } else {
                                    progressDialog.dismiss();
                                    txtNoRecordsinbox.setVisibility(View.VISIBLE);
                                    inbox_header.setVisibility(View.GONE);
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

    public void setExpandableListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<FinalArrayInbox>>();

        for (int j = 0; j < response.getFinalArray().size(); j++) {
            listDataHeader.add(response.getFinalArray().get(j).getUserName() + "|" +
                    response.getFinalArray().get(j).getMeetingDate() + "|" +
                    response.getFinalArray().get(j).getSubjectLine() + "|" +
                    response.getFinalArray().get(j).getReadStatus());

            ArrayList<FinalArrayInbox> rows = new ArrayList<FinalArrayInbox>();
            rows.add(response.getFinalArray().get(j));
            listDataChild.put(listDataHeader.get(j), rows);
        }
    }

}
