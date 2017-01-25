package management.elevator.com.elevatormanagementactivity.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;


import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.utils.ViewFindUtils;

/**
 * Created by janiszhang on 2016/6/10.
 */

public class TestFragment1 extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    private final String[] mTitles = new String[]{"未完成工单", "历史工单", "单位工单"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_1);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout_1);
        viewPager.setAdapter(new CustomAdapter(getChildFragmentManager(), getActivity().getApplicationContext()));


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
//        viewPager.setOffscreenPageLimit(2);
        return view;
    }


    private class CustomAdapter extends FragmentPagerAdapter {

        private String fragments[] = {"未完成工单", "历史工单", "单位工单"};
//FragmentManager childFragment=getChildFragmentManager();
        public CustomAdapter(FragmentManager supportFragmentManager , Context applicationContext) {

            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ContentFragment();
                case 1:
                    return new TickHistoryFragment();
                case 2:
                    return new TickCorpFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }
}
