package management.elevator.com.elevatormanagementactivity.activity.maintenance;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import management.elevator.com.elevatormanagementactivity.R;
import wang.raye.preioc.annotation.BindById;

import static management.elevator.com.elevatormanagementactivity.R.id.btn_main_choose_half_year;
import static management.elevator.com.elevatormanagementactivity.R.id.btn_main_choose_three_month;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class PopChooseDateWin extends PopupWindow {

    Button btnMainChooseAMonth;

    Button btnMainChooseThreeMonth;

    Button btnMainChooseHalfYear;

    Button btnMainChooseAllYear;

    Button btnCancel;

    LinearLayout popLayout;
    private Context mContext;
    private View view;

    //    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.item_choose_maintenance_update_days);
//        PreIOC.binder(this);
//    }
    public PopChooseDateWin(Context mContext, View.OnClickListener itemonclick) {
        this.view = LayoutInflater.from(mContext).inflate(R.layout.item_choose_maintenance_update_days, null);
        btnMainChooseAMonth = (Button) view.findViewById(R.id.btn_main_choose_a_half_month);

        btnMainChooseThreeMonth = (Button) view.findViewById(btn_main_choose_three_month);
        btnMainChooseHalfYear = (Button) view.findViewById(btn_main_choose_half_year);
        btnMainChooseAllYear = (Button) view.findViewById(R.id.btn_main_choose_all_year);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnMainChooseAllYear.setOnClickListener(itemonclick);
        btnMainChooseThreeMonth.setOnClickListener(itemonclick);
        btnMainChooseAMonth.setOnClickListener(itemonclick);
        btnMainChooseHalfYear.setOnClickListener(itemonclick);
        this.setOutsideTouchable(true);
        this.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = v.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }

                }
                return true;
            }
        });
        this.setContentView(this.view);
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.pop_up_anim);
    }
}
