package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.RetScanLiftItemBean;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class RetScanLiftItemAdapter extends RecyclerView.Adapter<RetScanLiftItemAdapter.Viewholder> implements View.OnClickListener {
    RetScanLiftItemBean.Data data;

    private RecycleAdapter.onRecycleViewItemClickListener myOnItemClickListener = null;
    private LayoutInflater mlayoutInflater;
    private Context mContext;
    SharedPreferences sp;
    ArrayList<RetScanLiftItemBean.Data> mlist = new ArrayList<RetScanLiftItemBean.Data>();

    public RetScanLiftItemAdapter(Context context) {
        mContext = context;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public RetScanLiftItemAdapter(Context context, RetScanLiftItemBean obj) {
        this(context);
        mlist.addAll(obj.getDatas());
    }

    public static interface onRecycleViewItemClickListener {
        void onItemClick(View view, String data);

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mlayoutInflater.inflate(R.layout.item_search_result, parent, false);
        view.setOnClickListener(this);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(final Viewholder holder, int position) {
        data = mlist.get(position);
        final String token = Constant.TOKEN;
        for (int i = 0; i < mlist.size(); i++) {
//            final String tid = String.valueOf(data.getTID());
        holder.textResLSn.setText("电梯SN："+data.getSN()+"");
//            holder.status.setText(data.getS_RANK());
            holder.textResName.setText("电梯梯号： " + data.getNAME()+"");
            holder.textResLArea.setText("电梯区域： " + data.getL_AREA()+"");
       holder.textTickNumber.setText(data.getTICK_NUM()+"");
//
         holder.textResLPark.setText("楼盘区域：" + data.getL_PARK());
//            holder.order_sendaddress.setText("归档时间：" + data.getDEL_TIME());
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

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView textResLSn;
        TextView textResName;
        TextView textResLArea;
        TextView textResLPark;
        TextView textTickNumber;

        public Viewholder(View itemView) {
            super(itemView);
            textResLSn = ButterKnife.findById(itemView, R.id.text_res_l_sn);
            textResName = ButterKnife.findById(itemView, R.id.text_res_name);
            textResLArea = ButterKnife.findById(itemView, R.id.text_res_l_area);
            textTickNumber = ButterKnife.findById(itemView, R.id.text_tick_number);
            textResLPark = ButterKnife.findById(itemView, R.id.text_res_l_park);

        }
    }
}