package management.elevator.com.elevatormanagementactivity.adapter;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;

import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;

import management.elevator.com.elevatormanagementactivity.fragment.ContactsFragment;
import management.elevator.com.elevatormanagementactivity.fragment.MainFragment;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class FragmentAdapter implements FragmentNavigatorAdapter {
    private  static final String TABS[]={"首页","工单","设备","我的"};
    @Override
    public Fragment onCreateFragment(int i) {
        if (i==1){
            return ContactsFragment.newInstance(i);

        }
        return MainFragment.newInstance(TABS[i]);
    }

    @Override
    public String getTag(int i) {
        if (i==1){
            return ContactsFragment.TAG;

        }else if(i==0){
            return MainFragment.TAG+TABS[i];

        }

        return MainFragment.TAG+TABS[i];
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
