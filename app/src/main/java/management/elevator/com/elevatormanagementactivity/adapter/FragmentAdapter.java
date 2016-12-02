package management.elevator.com.elevatormanagementactivity.adapter;

import android.provider.ContactsContract;
import android.support.v4.app.Fragment;

import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;



/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class FragmentAdapter implements FragmentNavigatorAdapter {
    @Override
    public Fragment onCreateFragment(int i) {
        return null;
    }

    @Override
    public String getTag(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
//    private  static final String TABS[]={"首页","工单","设备","我的"};
//    @Override
//    public Fragment onCreateFragment(int i) {
//        if (i==1){
//            return ContactsFragment.newInstance(i);
//
//        }
//        return MainFragment.newInstance(TABS[i]);
//    }
//
//    @Override
//    public String getTag(int i) {
//        if (i==1){
//            return ContactsFragment.TAG+TABS[i];
//
//        }else if(i==0) {
//            return MainFragment.TAG + TABS[i];
//
////        }else if (i==2){
////
////            return
////        }
//        }
//        return MainFragment.TAG+TABS[i];
//    }
//
//    @Override
//    public int getCount() {
//        return TABS.length;
//    }
}
