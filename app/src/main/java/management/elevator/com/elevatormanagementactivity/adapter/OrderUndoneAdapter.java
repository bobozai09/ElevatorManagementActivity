package management.elevator.com.elevatormanagementactivity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.zip.Inflater;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.TickSelfBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;

/**
 * Created by Administrator on 2016/11/30 0030.
 */

public class OrderUndoneAdapter extends RecyclerView.Adapter<OrderUndoneAdapter.OrderUndoneTextViewHolder> {
    TickSelfBean.Data data;

    private LayoutInflater mlayoutInflater;
    private Context mContext;
    SharedPreferences sp;
    ArrayList<TickSelfBean.Data> mlist = new ArrayList<TickSelfBean.Data>();

    public OrderUndoneAdapter(Context context) {
        mContext = context;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public OrderUndoneAdapter(Context context, TickSelfBean obj) {
        this(context);
        mlist.addAll(obj.getDatas());
    }

    @Override
    public OrderUndoneTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mlayoutInflater.inflate(R.layout.item_order_message, parent, false);
        return new OrderUndoneTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderUndoneTextViewHolder holder, int position) {
        data = mlist.get(position);
        final String token = Constant.TOKEN;
        for (int i = 0; i < mlist.size(); i++) {
            final String tid = String.valueOf(data.getTID());
            holder.order_numer.setText(tid);
            holder.status.setText(data.getS_RANK());
            int irank = data.getI_RANK();
            if (irank == 0) {
                holder.status.setBackgroundResource(R.drawable.order_btn_message_zero);
            } else if (irank == 1) {
                holder.status.setBackgroundResource(R.drawable.order_btn_message_one);
            } else if (irank == 2) {
                holder.status.setBackgroundResource(R.drawable.order_btn_message_two);
            } else if (irank == 3) {
                holder.status.setBackgroundResource(R.drawable.oder_btn_message);
            }
            String status = data.getS_STATUS();
            holder.is_receiver.setText(status);
            if (status.equals("未接单")) {
                holder.btn_accept.setVisibility(View.VISIBLE);
                holder.btn_accept.setText("接单");
                holder.btn_refrush.setText("拒绝");
            } else if (status.equals("处理中")){
                holder.btn_accept.setVisibility(View.GONE);
                holder.btn_refrush.setText("查看");
            }
            holder.order_message.setText("故障内容： " + data.getTICK());
            holder.sendtime.setText("派单时间：" + data.getDIS_DTM());
            holder.order_type.setText("故障类型： " + data.getTYPE());
            holder.sendpersomeone.setText("派 单 人：" + data.getDIS_MAN());

        }

//// TODO: 2016/12/7 0007
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class OrderUndoneTextViewHolder extends RecyclerView.ViewHolder {
        public TextView order_numer, status, is_receiver, order_message, sendtime, sendpersomeone, sendaddress;
        public TextView order_type;
        public Button btn_accept, btn_refrush;

        public OrderUndoneTextViewHolder(View itemView) {
            super(itemView);
            order_numer = ButterKnife.findById(itemView, R.id.order_number);
            status = ButterKnife.findById(itemView, R.id.order_status);
            is_receiver = ButterKnife.findById(itemView, R.id.order_is_receiver);
            order_message = ButterKnife.findById(itemView, R.id.ordermessage);
            sendtime = ButterKnife.findById(itemView, R.id.order_sendtime);
            sendpersomeone = ButterKnife.findById(itemView, R.id.order_sendpersomeone);
            sendaddress = ButterKnife.findById(itemView, R.id.order_sendaddress);
            btn_accept = ButterKnife.findById(itemView, R.id.order_btn_accept);
            btn_refrush = ButterKnife.findById(itemView, R.id.order_btn_refrush);
            order_type = ButterKnife.findById(itemView, R.id.ordertype);
        }
    }

}
