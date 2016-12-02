package management.elevator.com.elevatormanagementactivity.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.widget.UnderLineLinearLayout;

/**
 * Created by Administrator on 2016/12/1 0001.
 * 显示工单详情
 */

public class Order_SpecificMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private UnderLineLinearLayout mUnderLinelayout;
int i=0;
    ImageView backImageview;
    Button add_btn;
    LinearLayout line_order_status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_specimessage);
        init();
    }
    private void init(){
        backImageview= (ImageView) findViewById(R.id.img_back);
backImageview.setOnClickListener(this);
        add_btn= (Button) findViewById(R.id.additem);
        add_btn.setOnClickListener(this);
        mUnderLinelayout= (UnderLineLinearLayout) findViewById(R.id.underline_order_message);
line_order_status= (LinearLayout) findViewById(R.id.line_order_status);
        line_order_status.setVisibility(View.GONE);
    }
    private void addItem(){
        View v= LayoutInflater.from(this).inflate(R.layout.item_vertical,mUnderLinelayout,false);
        ((TextView)v.findViewById(R.id.tx_action)).setText(""+i);
        ((TextView)v.findViewById(R.id.tx_action_time)).setText("2016-12-01");
        ((TextView) v.findViewById(R.id.tx_action_status)).setText("finish");
        mUnderLinelayout.addView(v);
        i++;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            case  R.id.additem:
                addItem();
                break;
            default:
                break;

        }
    }
}
