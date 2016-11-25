package management.elevator.com.elevatormanagementactivity.adapter;

import android.support.v4.app.Fragment;

import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;

import management.elevator.com.elevatormanagementactivity.fragment.MainFragment;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class ChildFragmentAdapter  implements FragmentNavigatorAdapter{
    public static final String[] TABS={"Friends","Group","Offices"};
    @Override
    public Fragment onCreateFragment(int i) {
        return  MainFragment.newInstance(TABS[i]);
    }

    @Override
    public String getTag(int i) {
        return MainFragment.TAG+TABS[i];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
