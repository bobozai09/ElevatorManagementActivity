package management.elevator.com.elevatormanagementactivity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.Order_SpecificMessageActivity;
import management.elevator.com.elevatormanagementactivity.adapter.OrderUndoneAdapter;
import management.elevator.com.elevatormanagementactivity.widget.SpaceItemDecoration;

/**
 * Created by janiszhang on 2016/6/6.
 */

public class ContentFragment extends Fragment {

private OrderUndoneAdapter adapter;
    String []number={"工单编号：0122334455555","工单编号：0122334455555"};
    String []status={"紧急","重要"};
    private View viewContent;
    private int mType = 0;
    private String mTitle;
RecyclerView orderReceiver;

    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //布局文件中只有一个居中的TextView
        viewContent = inflater.inflate(R.layout.fragment_context,container,false);
        ButterKnife.bind(getActivity());
//        TextView textView = (TextView) viewContent.findViewById(R.id.tv_context);
//        textView.setText(this.mTitle);
      init(viewContent);
        return viewContent;
    }
    private   void init(View view){
        int spaceInPixes=getResources().getDimensionPixelSize(R.dimen.activity_8dp);
        orderReceiver= (RecyclerView) view.findViewById(R.id.rec_order);
        orderReceiver.setLayoutManager(new LinearLayoutManager(getActivity()));
       orderReceiver.addItemDecoration(new SpaceItemDecoration(spaceInPixes));
        adapter=new OrderUndoneAdapter(number,status);
        orderReceiver.setAdapter(adapter);
      adapter.setOnItemClickListener(new OrderUndoneAdapter.onRecycleViewItemClickListener() {
          @Override
          public void onItemClick(View view, String data) {
              Intent intent=new Intent();
              intent.setClass(getActivity(), Order_SpecificMessageActivity.class);
              startActivity(intent);
          }
      });


    }


}
