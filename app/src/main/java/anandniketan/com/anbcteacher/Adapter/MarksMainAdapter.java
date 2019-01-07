package anandniketan.com.anbcteacher.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import anandniketan.com.anbcteacher.Fragment.AddMarksFragment;
import anandniketan.com.anbcteacher.Fragment.AddTestFragment;
import anandniketan.com.anbcteacher.Fragment.MarksFragment;
import anandniketan.com.anbcteacher.Fragment.TestsyllabusFragment;

public class MarksMainAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public MarksMainAdapter(FragmentManager fm, int tabCount) {
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
                AddMarksFragment tab1 = new AddMarksFragment();
                return tab1;
            case 1:
                MarksFragment tab2 = new MarksFragment();
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




