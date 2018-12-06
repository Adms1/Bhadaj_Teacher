package anandniketan.com.anbcteacher.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import anandniketan.com.anbcteacher.Fragment.AddTestFragment;
import anandniketan.com.anbcteacher.Fragment.TestsyllabusFragment;


/**
 * Created by admsandroid on 9/28/2017.
 */

public class TestMainAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public TestMainAdapter(FragmentManager fm, int tabCount) {
        super(fm);
//Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
//Returning the current tabs
        switch (position) {
            case 0:
                TestsyllabusFragment tab1 = new TestsyllabusFragment();
                return tab1;
            case 1:
                AddTestFragment tab2 = new AddTestFragment();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}



