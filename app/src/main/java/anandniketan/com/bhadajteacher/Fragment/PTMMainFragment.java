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

import anandniketan.com.bhadajteacher.Adapter.PTMPageAdapter;
import anandniketan.com.bhadajteacher.R;


public class PTMMainFragment extends Fragment {
    private View rootView;
    private Button btnMenu, btnBackPtm_main;

    private TabLayout tabLayout_ptm_main;
    private ViewPager viewPager;
    private Context mContext;
    PTMPageAdapter adapter;

    public PTMMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_ptmmain, container, false);
        mContext = getActivity();
        init();
        setListner();
        return rootView;

    }

    public void init() {
//Initializing the tablayout

        btnBackPtm_main= (Button) rootView.findViewById(R.id.btnBackPtm_main);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);


        tabLayout_ptm_main = (TabLayout) rootView.findViewById(R.id.tabLayout_ptm_main);
        tabLayout_ptm_main.addTab(tabLayout_ptm_main.newTab().setText("Inbox"),true);
        tabLayout_ptm_main.addTab(tabLayout_ptm_main.newTab().setText("Sent"));
        tabLayout_ptm_main.addTab(tabLayout_ptm_main.newTab().setText("Create"));
        tabLayout_ptm_main.setTabMode(TabLayout.MODE_FIXED);
        tabLayout_ptm_main.setTabGravity(TabLayout.GRAVITY_FILL);


        adapter = new PTMPageAdapter(getFragmentManager(), tabLayout_ptm_main.getTabCount());
//Adding adapter to pager
        viewPager.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }

    public void setListner() {
        btnBackPtm_main.setOnClickListener(new View.OnClickListener() {
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
                tabLayout_ptm_main));
        tabLayout_ptm_main.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
