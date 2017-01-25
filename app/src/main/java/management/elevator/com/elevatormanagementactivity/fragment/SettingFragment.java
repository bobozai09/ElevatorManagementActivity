package management.elevator.com.elevatormanagementactivity.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.FeedBackActivity;
import management.elevator.com.elevatormanagementactivity.activity.UpdateMessageActivity;
import management.elevator.com.elevatormanagementactivity.activity.maintenance.PopChooseDateWin;
import management.elevator.com.elevatormanagementactivity.widget.AppManager;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/11/15 0015.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {
    @BindById(R.id.img_my_head)
    CircleImageView imgMyHead;
    @BindById(R.id.line_update_my_message)
    LinearLayout lineUpdateMyMessage;
    @BindById(R.id.line_bindphonenunmber)
    LinearLayout lineBindphonenunmber;
    @BindById(R.id.line_my_location)
    LinearLayout lineMyLocation;
    @BindById(R.id.line_update_pass)
    LinearLayout lineUpdatePass;
    @BindById(R.id.line_message_of_use)
    LinearLayout lineMessageOfUse;
    @BindById(R.id.line_safeofdev)
    LinearLayout lineSafeofdev;
    @BindById(R.id.btn_exit)
    Button btnExit;
    Intent intent;
    TextView mylocation;
    PopChooseDateWin pop;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_myself, container, false);
        PreIOC.binder(this, view);
        imgMyHead = (CircleImageView) view.findViewById(R.id.img_my_head);
        lineUpdateMyMessage = (LinearLayout) view.findViewById(R.id.line_update_my_message);
        lineBindphonenunmber = (LinearLayout) view.findViewById(R.id.line_bindphonenunmber);
        lineMyLocation = (LinearLayout) view.findViewById(R.id.line_my_location);
        lineUpdatePass = (LinearLayout) view.findViewById(R.id.line_update_pass);
        lineMessageOfUse = (LinearLayout) view.findViewById(R.id.line_message_of_use);
        lineSafeofdev = (LinearLayout) view.findViewById(R.id.line_safeofdev);
        btnExit = (Button) view.findViewById(R.id.btn_exit);
        mylocation = (TextView) view.findViewById(R.id.text_mylocation);
        initView();
        return view;
    }

    private void initView() {
        imgMyHead.setOnClickListener(this);
        lineUpdateMyMessage.setOnClickListener(this);
        lineBindphonenunmber.setOnClickListener(this);
        lineMyLocation.setOnClickListener(this);
        lineUpdatePass.setOnClickListener(this);
        lineMessageOfUse.setOnClickListener(this);
        lineSafeofdev.setOnClickListener(this);
        btnExit.setOnClickListener(this);
//        mylocation.setText(Constant.);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreIOC.unBinder(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_message_of_use:
                intent = new Intent();
                intent.setClass(getContext(), FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.line_update_my_message:
                intent = new Intent();
                intent.setClass(getContext(), UpdateMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定退出嘛？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        AppManager.getAppManager().AppExit();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            default:
                break;

        }
    }

}
