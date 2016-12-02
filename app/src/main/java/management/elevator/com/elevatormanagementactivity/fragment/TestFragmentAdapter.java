package management.elevator.com.elevatormanagementactivity.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by janiszhang on 2016/6/10.
 */
//继承FragmentStatePagerAdapter
public class TestFragmentAdapter extends FragmentStatePagerAdapter {

    public static final String TAB_TAG = "@dream@";

    private List<String> mTitles;

    public TestFragmentAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        //初始化Fragment数据
        ContentFragment fragment = new ContentFragment();
        if (position==0) {
            String[] title = mTitles.get(position).split(TAB_TAG);
            fragment.setType(Integer.parseInt(title[1]));
            fragment.setTitle(title[0]);
        }else if(position==1){
            String[] title = mTitles.get(position).split(TAB_TAG);
            fragment.setType(Integer.parseInt(title[1]));
            fragment.setTitle(title[0]);

        }else if (position==2){
            String[] title = mTitles.get(position).split(TAB_TAG);
            fragment.setType(Integer.parseInt(title[1]));
            fragment.setTitle(title[0]);

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position).split(TAB_TAG)[0];
    }
}

