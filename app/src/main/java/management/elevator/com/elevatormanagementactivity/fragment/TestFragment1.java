package management.elevator.com.elevatormanagementactivity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.adapter.MyViewPagerApapter;

/**
 * Created by janiszhang on 2016/6/10.
 */

public class TestFragment1 extends Fragment implements TabLayout.OnTabSelectedListener {

    private View viewContent;
    private TabLayout tab_essence;
    private ViewPager vp_essence;
    private MyViewPagerApapter viewPagerApapter;
    private String[] titles = new String[]{"未完成工单", "历史工单", "单位工单"};
    private List<Fragment> fragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        init(view);
        return view;
    }

    private void init(View viewContent) {
        tab_essence = (TabLayout) viewContent.findViewById(R.id.tab_essence);
        vp_essence = (ViewPager) viewContent.findViewById(R.id.vp_essence);
        tab_essence.setTabMode(TabLayout.MODE_FIXED);
        for (String tab : titles) {
            tab_essence.addTab(tab_essence.newTab().setText(tab));
        }
        tab_essence.setOnTabSelectedListener(this);
        fragments.add(new ContentFragment());
        fragments.add(new TickHistoryFragment());
        fragments.add(new TickCorpFragment());
        viewPagerApapter = new MyViewPagerApapter(getActivity().getSupportFragmentManager(), titles, fragments);
        vp_essence.setAdapter(viewPagerApapter);
        tab_essence.setupWithViewPager(vp_essence);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_essence.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
