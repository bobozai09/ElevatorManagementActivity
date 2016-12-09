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

import java.util.Arrays;

import management.elevator.com.elevatormanagementactivity.R;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class MainFragment extends Fragment {
    private View viewContent;
    private TabLayout tab_essence;
    private ViewPager vp_essence;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      viewContent=inflater.inflate(R.layout.fragment_test,container,false);
        initConnentView(viewContent);
        initData();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    public void  initConnentView(View viewContent){
        this.tab_essence = (TabLayout) viewContent.findViewById(R.id.tab_essence);
        this.vp_essence = (ViewPager) viewContent.findViewById(R.id.vp_essence);


    }
    public void initData() {
        //获取标签数据
        String[] titles = getResources().getStringArray(R.array.home_video_tab);

        //创建一个viewpager的adapter
        TestFragmentAdapter adapter = new TestFragmentAdapter(getFragmentManager(), Arrays.asList(titles));
        this.vp_essence.setAdapter(adapter);
        this.vp_essence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("=------","====");
            }
        });

        //将TabLayout和ViewPager关联起来
        this.tab_essence.setupWithViewPager(this.vp_essence);
    }
}
