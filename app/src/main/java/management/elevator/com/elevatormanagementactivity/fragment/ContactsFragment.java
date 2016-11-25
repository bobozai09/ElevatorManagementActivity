package management.elevator.com.elevatormanagementactivity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PointerIconCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspsine.fragmentnavigator.FragmentNavigator;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.adapter.ChildFragmentAdapter;
import management.elevator.com.elevatormanagementactivity.widget.TabLayout;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class ContactsFragment extends Fragment {
    public static final  String TAG=ContactsFragment.class.getSimpleName();
    private FragmentNavigator mNavigator;
    private TabLayout tabLayout;
    public  ContactsFragment(){


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      mNavigator=new FragmentNavigator(getChildFragmentManager(),new ChildFragmentAdapter(), R.id.childContainer);
        mNavigator.setDefaultPosition(0);
        mNavigator.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout= (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setOnTabItemClickListener(new TabLayout.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(int position, View view) {
                setCurrentTab(position);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCurrentTab(mNavigator.getCurrentPosition());
    }
    public void setCurrentTab(int position){
        mNavigator.showFragment(position);
        tabLayout.select(position);
    }
    public static Fragment newInstance(int position){
        Fragment fragment=new ContactsFragment();
        return fragment;

    }
}
