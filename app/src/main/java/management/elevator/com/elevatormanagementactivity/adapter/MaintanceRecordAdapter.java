package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.MaintenanceRecordBean;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class MaintanceRecordAdapter extends RecyclerView.Adapter<MaintanceRecordAdapter.MaintanceRecordViewHolder> implements View.OnClickListener {
    MaintenanceRecordBean.Data data;

    private RecycleAdapter.onRecycleViewItemClickListener myOnItemClickListener = null;
    private LayoutInflater mlayoutInflater;
    private Context mContext;
    ArrayList<MaintenanceRecordBean.Data> mlist = new ArrayList<MaintenanceRecordBean.Data>();

    public MaintanceRecordAdapter(Context context) {
        mContext = context;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public MaintanceRecordAdapter(Context context, MaintenanceRecordBean obj) {
        this.mContext = context;
        mlist.addAll(obj.getDatas());
    }

    public static interface onRecycleViewItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public MaintanceRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maintenance_record, parent, false);
        view.setOnClickListener(this);
        return new MaintanceRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MaintanceRecordViewHolder holder, int position) {
        data = mlist.get(position);
        for (int i = 0; i < mlist.size(); i++) {
            holder.textMainNeed.setText(data.getNEED());
            holder.textMainName.setText(data.getNAME());
            holder.textMainIdx.setText(data.getIDX() + "");
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    @Override
    public void onClick(View v) {
        if (myOnItemClickListener != null) {
            myOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    public void seOnItemClickListener(RecycleAdapter.onRecycleViewItemClickListener listener) {
        this.myOnItemClickListener = listener;
    }

    public static class MaintanceRecordViewHolder extends RecyclerView.ViewHolder {
     public    TextView textMainIdx;
        public   TextView textMainName;
        public   TextView textMainNeed;
        public   RadioButton rbStatusOne;
        public  RadioButton rbStatusTwo;
        public   RadioButton rbStatusThree;
        public   RadioButton rbStatusFour;
        public    RadioGroup rgChooseStatus;
        public MaintanceRecordViewHolder(View itemView) {
            super(itemView);
            textMainIdx = ButterKnife.findById(itemView, R.id.text_main_idx_2);
            textMainName = ButterKnife.findById(itemView, R.id.text_main_name);
            textMainNeed = ButterKnife.findById(itemView, R.id.text_main_need);
            rbStatusOne=ButterKnife.findById(itemView,R.id.rb_status_one);
            rbStatusTwo=ButterKnife.findById(itemView,R.id.rb_status_two);
            rbStatusThree=ButterKnife.findById(itemView,R.id.rb_status_three);
            rbStatusFour=ButterKnife.findById(itemView,R.id.rb_status_four);
            rgChooseStatus=ButterKnife.findById(itemView,R.id.rg_choose_status);
        }
    }
}