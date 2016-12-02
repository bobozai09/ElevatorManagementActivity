package management.elevator.com.elevatormanagementactivity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import management.elevator.com.elevatormanagementactivity.FragmentSample;
import management.elevator.com.elevatormanagementactivity.R;

/**
 * Created by Administrator on 2016/11/29 0029.
 */

public class UseInFragment extends FragmentActivity {
    FragmentSample fragmentSample;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_in_fragment);
        fragmentSample=new FragmentSample();
        getSupportFragmentManager().beginTransaction().add(R.id.frame,fragmentSample).show(fragmentSample).commit();
    }
}
