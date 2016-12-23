package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.TickHistoryBean;
import management.elevator.com.elevatormanagementactivity.bean.TickSelfBean;
import management.elevator.com.elevatormanagementactivity.widget.Constant;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class TickHistoryAdapter extends RecyclerView.Adapter<TickHistoryAdapter.TickHistoryTextViewHolder> {
    TickHistoryBean.Data data;

    private LayoutInflater mlayoutInflater;
    private Context mContext;
    SharedPreferences sp;
    ArrayList<TickHistoryBean.Data> mlist = new ArrayList<TickHistoryBean.Data>();

    public TickHistoryAdapter(Context context) {
        mContext = context;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public TickHistoryAdapter(Context context, TickHistoryBean obj) {
        this(context);
        mlist.addAll(obj.getDatas());
    }

    @Override
    public TickHistoryAdapter.TickHistoryTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mlayoutInflater.inflate(R.layout.item_order_message, parent, false);
        return new TickHistoryAdapter.TickHistoryTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TickHistoryAdapter.TickHistoryTextViewHolder holder, int position) {
        data = mlist.get(position);
        final String token = Constant.TOKEN;
        for (int i = 0; i < mlist.size(); i++) {
            final String tid = String.valueOf(data.getTID());
            holder.order_numer.setText(tid);
            holder.status.setText(data.getS_RANK());
            holder.order_type.setText("故障类型： " + data.getTYPE());
            holder.order_message.setText("故障内容： " + data.getNAME());
            holder.sendtime.setText("派单时间：" + data.getDIS_DTM());

            holder.sendpersomeone.setText("派 单 人：" + data.getDIS_MAN());
            holder.order_sendaddress.setText("归档时间：" + data.getDEL_TIME());
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class TickHistoryTextViewHolder extends RecyclerView.ViewHolder {
        public TextView order_numer, status, is_receiver, order_message, sendtime, sendpersomeone, sendaddress, order_sendaddress;
        public TextView order_type;
        public Button btn_accept, btn_refrush;
        public LinearLayout linearstatus, line_sendsomeoneaddress, line_order_message;

        public TickHistoryTextViewHolder(View itemView) {
            super(itemView);
            linearstatus = ButterKnife.findById(itemView, R.id.line_order_status);
            line_sendsomeoneaddress = ButterKnife.findById(itemView, R.id.line_sendsomeoneaddress);
            line_order_message = ButterKnife.findById(itemView, R.id.line_order_message);
            line_order_message.setVisibility(VISIBLE);
            line_sendsomeoneaddress.setVisibility(VISIBLE);
            linearstatus.setVisibility(GONE);
            order_numer = ButterKnife.findById(itemView, R.id.order_number);
            status = ButterKnife.findById(itemView, R.id.order_status);
            is_receiver = ButterKnife.findById(itemView, R.id.order_is_receiver);
            order_message = ButterKnife.findById(itemView, R.id.ordermessage);
            sendtime = ButterKnife.findById(itemView, R.id.order_sendtime);
            sendpersomeone = ButterKnife.findById(itemView, R.id.order_sendpersomeone);
            sendaddress = ButterKnife.findById(itemView, R.id.order_sendaddress);
            order_type = ButterKnife.findById(itemView, R.id.ordertype);
            order_sendaddress = ButterKnife.findById(itemView, R.id.order_sendaddress);
        }
    }
}
