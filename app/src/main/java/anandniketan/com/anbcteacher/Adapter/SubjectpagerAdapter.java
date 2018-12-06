package anandniketan.com.anbcteacher.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import anandniketan.com.anbcteacher.Fragment.My_subjectFragment;
import anandniketan.com.anbcteacher.Fragment.StudentAssignesubject;
import anandniketan.com.anbcteacher.Utility.AppConfiguration;


/**
 * Created by admsandroid on 9/25/2017.
 */

public class SubjectpagerAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public SubjectpagerAdapter(FragmentManager fm, int tabCount) {
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
                My_subjectFragment tab1 = new My_subjectFragment();
                return tab1;
            case 1:
                StudentAssignesubject tab2 = new StudentAssignesubject();
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



