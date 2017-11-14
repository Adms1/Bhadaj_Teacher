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
import anandniketan.com.bhadajteacher.Adapter.TestMainAdapter;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.Utility;


public class TestMainFragment extends Fragment {
    private View rootView;
    private Button btnLogout, btnBacktest_main;

    private TabLayout tabLayout_test_main;
    private ViewPager viewPager;
    private Context mContext;
    TestMainAdapter adapter;

    public TestMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_test_main, container, false);
        mContext = getActivity();
        init();
        setListner();
        return rootView;

    }

    public void init() {
//Initializing the tablayout
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnBacktest_main = (Button) rootView.findViewById(R.id.btnBacktest_main);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);


        tabLayout_test_main = (TabLayout) rootView.findViewById(R.id.tabLayout_test_main);
        tabLayout_test_main.addTab(tabLayout_test_main.newTab().setText("Edit Test"), true);
        tabLayout_test_main.addTab(tabLayout_test_main.newTab().setText(Html.fromHtml("Add Test")));
        tabLayout_test_main.setTabMode(TabLayout.MODE_FIXED);
        tabLayout_test_main.setTabGravity(TabLayout.GRAVITY_FILL);


        adapter = new TestMainAdapter(getFragmentManager(), tabLayout_test_main.getTabCount());
//Adding adapter to pager
        viewPager.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
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
        btnBacktest_main.setOnClickListener(new View.OnClickListener() {
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
                tabLayout_test_main));
        tabLayout_test_main.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
