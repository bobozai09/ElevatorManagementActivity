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
        View view=mlayoutInflater.inflate(R.layout.item_order_message,parent,false);

return  new OrderUndoneTextViewHolder(view);
//        return new OrderUndoneTextViewHolder(mlayoutInflater.inflate(R.layout.item_order_message, parent, false));
    }

    @Override
    public void onBindViewHolder(OrderUndoneTextViewHolder holder, int position) {
        data = mlist.get(position);
        final String token=Constant.TOKEN;
        for (int i=0;i<mlist.size();i++){
            final String tid=String.valueOf(data.getTID());
            holder.order_numer.setText("工单编号: " + tid);
            holder.status.setText(data.getS_RANK());
            String status = data.getS_STATUS();
            holder.is_receiver.setText(status);
            if (status.equals("未接单")) {
                holder.btn_accept.setText("接单");
                holder.btn_refrush.setText("拒绝");
            } else {
                holder.btn_accept.setVisibility(View.GONE);
                holder.btn_refrush.setText("查看");
            }
            holder.order_message.setText("故障内容： "+data.getTICK());
            holder.sendtime.setText("派单时间："+data.getDIS_DTM());
            holder.order_type.setText("故障类型： "+data.getTYPE());
            holder.sendpersomeone.setText("派 单 人："+data.getDIS_MAN());
            holder.btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("orderundoneadapter",tid);
                    Log.i("orderundoneadaptertoken",token);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String domain = Constant.BASE_URL + Constant.TICKER;
//                            String params = Constant.OPER + "=" +
//                                    Constant.TICK_HOLD + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TID+"="+tid;
//                            String json = GetSession.post(domain, params);
//                            if (!json.equals("+ER+")){
//
//
//                            }
//                        }
//                    }).start();
                }
            });
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
//            btn_accept.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

        }
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
//
//
//            }
//        }
    }
    private  void  TickHole(final  String token,final String tid){
        Log.i("---tickhole","");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.TICKER;
                String params = Constant.OPER + "=" +
                        Constant.TICK_HOLD + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TID+"="+tid;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")){


                }
            }
        }).start();

    }
}
