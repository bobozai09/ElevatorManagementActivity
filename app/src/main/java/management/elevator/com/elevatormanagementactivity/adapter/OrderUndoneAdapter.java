package management.elevator.com.elevatormanagementactivity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.zip.Inflater;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;

/**
 * Created by Administrator on 2016/11/30 0030.
 */

public class OrderUndoneAdapter extends RecyclerView.Adapter implements View.OnClickListener {
private  onRecycleViewItemClickListener myOnItemClickListener=null;
    private  String []Ordernumber;
    private  String[]OrderStatus;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_message,null);
        NaviHolder holder=new NaviHolder(view);
view.setOnClickListener(this);
        return holder;
    }
    public OrderUndoneAdapter(String[]ordernumber,String[]orderStatus){
        this.Ordernumber=ordernumber;
        this.OrderStatus=orderStatus;
    }
    @Override
    public void onClick(View v) {
        if (myOnItemClickListener!=null){
            myOnItemClickListener.onItemClick(v, (String) v.getTag());

        }
    }

    public  static interface onRecycleViewItemClickListener{
void onItemClick(View view,String data);

}
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NaviHolder)holder).order_numer.setText(Ordernumber[position]);
        ((NaviHolder)holder).status.setText(OrderStatus[position]);
    }

    @Override
    public int getItemCount() {
        return Ordernumber.length;
    }
    private  class NaviHolder extends RecyclerView.ViewHolder{
        /**工单编号
         * 工单状态 紧急 次要 重要 警告
         * 是否已接单
         * 故障内容
         * 故障派单时间
         * 故障派单人
         * 故障地址
         * 接单
         * 拒绝/查看
         *
         */
        public TextView order_numer,status,is_receiver,order_message,sendtime,sendpersomeone,sendaddress;
public Button btn_accept,btn_refrush;
        public NaviHolder(View itemView) {
            super(itemView);
            order_numer= ButterKnife.findById(itemView, R.id.order_number);
            status=ButterKnife.findById(itemView,R.id.order_status);
            is_receiver=ButterKnife.findById(itemView,R.id.order_is_receiver);
            order_message=ButterKnife.findById(itemView,R.id.ordermessage);
            sendtime=ButterKnife.findById(itemView,R.id.order_sendtime);

            sendpersomeone=ButterKnife.findById(itemView,R.id.order_sendpersomeone);
            sendaddress=ButterKnife.findById(itemView,R.id.order_sendaddress);
            btn_accept=ButterKnife.findById(itemView,R.id.order_btn_accept);
            btn_refrush=ButterKnife.findById(itemView,R.id.order_btn_refrush);

        }
    }
    public  void  setOnItemClickListener(onRecycleViewItemClickListener listener){
        this.myOnItemClickListener=listener;

    }
}
