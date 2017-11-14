package anandniketan.com.bhadajteacher.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import anandniketan.com.bhadajteacher.Activities.LoginActivity;
import anandniketan.com.bhadajteacher.Adapter.SchedulepagerAdapter;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class ScheduleFragment extends Fragment {
    private View rootView;
    private Button btnBackSchedule, btnLogout;

    private TabLayout tabLayout_schedule;
    private ViewPager viewPager;
    private Context mContext;


    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        mContext = getActivity();
        init();
        setListner();
        return rootView;

    }

    public void init() {
//Initializing the tablayout
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnBackSchedule = (Button) rootView.findViewById(R.id.btnBackSchedule);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);


        tabLayout_schedule = (TabLayout) rootView.findViewById(R.id.tabLayout_schedule);
        tabLayout_schedule.addTab(tabLayout_schedule.newTab().setText("Today Schedule"), true);
//        tabLayout_schedule.addTab(tabLayout_schedule.newTab().setText(Html.fromHtml("Lesson Plan \n Schedule")));
        tabLayout_schedule.setTabMode(TabLayout.MODE_FIXED);
        tabLayout_schedule.setTabGravity(TabLayout.GRAVITY_FILL);


        SchedulepagerAdapter adapter = new SchedulepagerAdapter(getFragmentManager(), tabLayout_schedule.getTabCount());
//Adding adapter to pager
        viewPager.setAdapter(adapter);
//        tabLayout_schedule.setupWithViewPager(viewPager);
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
        btnBackSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                tabLayout_schedule));
        tabLayout_schedule.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
