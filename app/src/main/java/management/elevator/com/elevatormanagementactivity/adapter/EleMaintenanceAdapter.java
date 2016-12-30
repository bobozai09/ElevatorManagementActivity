package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.RetParkBean;
import management.elevator.com.elevatormanagementactivity.bean.TickHistoryBean;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class EleMaintenanceAdapter extends RecyclerView.Adapter<EleMaintenanceAdapter.EleMaintenanceTextViewHolder> implements View.OnClickListener {
    RetParkBean.Data data;

    private RecycleAdapter.onRecycleViewItemClickListener myOnItemClickListener = null;
    private LayoutInflater mlayoutInflater;
    private Context mContext;
    ArrayList<RetParkBean.Data> mlist = new ArrayList<RetParkBean.Data>();

    public EleMaintenanceAdapter(Context context) {
      mContext   = context;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public EleMaintenanceAdapter(Context context, RetParkBean obj) {
       this.mContext=context;
        mlist.addAll(obj.getDatas());
    }
//public EleMaintenanceAdapter(Context context,String []str1,String []str2){
//    this.mContext=context;
//    this.str1=str1;
//    this.str2=str2;
//    this.mlayoutInflater = LayoutInflater.from(context);
//}
    public static interface onRecycleViewItemClickListener {
        void onItemClick(View view, String data);

    }

    @Override
    public EleMaintenanceTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_elevator_maintenance, parent, false);
        view.setOnClickListener(this);
        return new EleMaintenanceTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EleMaintenanceTextViewHolder holder, int position) {
        data = mlist.get(position);
        for (int i = 0; i < mlist.size(); i++) {
            holder.textQzone.setText(data.getAREA());//区域
            holder.textEqiutmentAddress.setText(data.getNAME());
            holder.textCellID.setText(data.getID()+"");
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

    public static class EleMaintenanceTextViewHolder extends RecyclerView.ViewHolder {
//        @BindById(R.id.cir_eqiutment_head)
//        CircularImageView cirEqiutmentHead;
        @BindById(R.id.text_eqiutment_address)
        TextView textEqiutmentAddress;
        @BindById(R.id.text_eqiutment_number)
        TextView textEqiutmentNumber;
        @BindById(R.id.text_qzone)
        TextView textQzone;
        TextView textCellID;

        public EleMaintenanceTextViewHolder(View itemView) {
            super(itemView);
//            cirEqiutmentHead= ButterKnife.findById(itemView,R.id.cir_eqiutment_head);
            textEqiutmentAddress=ButterKnife.findById(itemView,R.id.text_eqiutment_address);
            textEqiutmentNumber=ButterKnife.findById(itemView,R.id.text_eqiutment_number);
            textQzone=ButterKnife.findById(itemView,R.id.text_qzone);
            textCellID=ButterKnife.findById(itemView,R.id.text_cell_id);
        }
    }
}
