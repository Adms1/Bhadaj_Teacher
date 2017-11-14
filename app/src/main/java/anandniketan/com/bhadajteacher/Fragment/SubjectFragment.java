package anandniketan.com.bhadajteacher.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import anandniketan.com.bhadajteacher.Activities.DashBoardActivity;
import anandniketan.com.bhadajteacher.Adapter.SubjectpagerAdapter;
import anandniketan.com.bhadajteacher.R;
import anandniketan.com.bhadajteacher.Utility.AppConfiguration;


public class SubjectFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackSubject;

    private TabLayout tabLayout_subject;
    private ViewPager viewPager;
    private Context mContext;
    String ClassId;

    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_subject, container, false);
        mContext = getActivity();
        init();
        setListner();
        return rootView;

    }

    public void init() {
//Initializing the tablayout

        btnMenu = (Button) rootView.findViewById(R.id.btnMenu);
        btnBackSubject = (Button) rootView.findViewById(R.id.btnBackSubject);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);


        tabLayout_subject = (TabLayout) rootView.findViewById(R.id.tabLayout_subject);
        tabLayout_subject.addTab(tabLayout_subject.newTab().setText("My Subject"), true);
        tabLayout_subject.addTab(tabLayout_subject.newTab().setText("Student Subject"));
        tabLayout_subject.setTabMode(TabLayout.MODE_FIXED);
        tabLayout_subject.setTabGravity(TabLayout.GRAVITY_FILL);

        for (int i = 0; i < AppConfiguration.rows.size(); i++) {
            ClassId = AppConfiguration.rows.get(0).getClassID();
        }

        SubjectpagerAdapter adapter = new SubjectpagerAdapter(getFragmentManager(), tabLayout_subject.getTabCount());
//Adding adapter to pager
        viewPager.setAdapter(adapter);
//        tabLayout_schedule.setupWithViewPager(viewPager);
    }

    public void setListner() {
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.onLeft();
            }
        });
        btnBackSubject.setOnClickListener(new View.OnClickListener() {
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
                tabLayout_subject));
        tabLayout_subject.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
