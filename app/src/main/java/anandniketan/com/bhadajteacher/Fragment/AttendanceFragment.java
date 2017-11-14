package anandniketan.com.bhadajteacher.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import anandniketan.com.bhadajteacher.Activities.LoginActivity;
import anandniketan.com.bhadajteacher.Adapter.Pager;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class AttendanceFragment extends Fragment {
    private View rootView;
    private Button btnBackAttendance,btnLogout;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext;

    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_attendance, container, false);
        mContext = getActivity();
        init();
        setListner();
        return rootView;

    }

    public void init() {
//Initializing the tablayout
        btnBackAttendance = (Button) rootView.findViewById(R.id.btnBackAttendance);
        btnLogout=(Button)rootView.findViewById(R.id.btnLogout);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void setListner() {
        btnBackAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

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
    }


    private void setupViewPager(ViewPager viewPager) {

        Pager adapter = new Pager(getActivity().getSupportFragmentManager());
        LayoutInflater inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < AppConfiguration.rows.size(); i++) {
            Log.d("size", "" + AppConfiguration.rows.size());

            OneFragmentAtt fView = new OneFragmentAtt(i, AppConfiguration.rows.get(i).getClassID(), AppConfiguration.rows.get(i).getStandardID());
            View view = fView.getView();
            adapter.addFrag(fView, String.valueOf(AppConfiguration.rows.get(i).getStandard() + "-" + AppConfiguration.rows.get(i).getClasses()));

        }
        viewPager.setAdapter(adapter);
    }


}
