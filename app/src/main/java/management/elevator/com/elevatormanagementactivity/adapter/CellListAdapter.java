package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.CellListBean;
import management.elevator.com.elevatormanagementactivity.bean.LiftListBean;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class CellListAdapter extends RecyclerView.Adapter<CellListAdapter.CellListViewholder> implements View.OnClickListener {
    CellListBean.Data data;
    private LayoutInflater mlayoutInflater;
    private Context mContext;
    SharedPreferences sp;
    private RecycleAdapter.onRecycleViewItemClickListener myOnItemClickListener = null;

    ArrayList<CellListBean.Data> mlist = new ArrayList<CellListBean.Data>();

    public CellListAdapter(Context context) {
        mContext = context;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public CellListAdapter(Context context, CellListBean obj) {
        this(context);
        mlist.addAll(obj.getDatas());
    }
    public static interface onRecycleViewItemClickListener {
        void onItemClick(View view, String data);

    }
    @Override
    public CellListAdapter.CellListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mlayoutInflater.inflate(R.layout.item_elevator_maintenance, parent, false);
        view.setOnClickListener(this);
        return new CellListAdapter.CellListViewholder(view);
    }

    @Override
    public void onBindViewHolder(final CellListAdapter.CellListViewholder holder, int position) {
        data = mlist.get(position);
        Log.i("data====>", "" + data);
        for (int i = 0; i < mlist.size(); i++) {
            holder.textEqiutmentAddress.setText("电梯编号：" + data.getID() + "");
            holder.textEqiutmentNumber.setText("梯号：" + data.getDESC());
            holder.textQzone.setText(data.getLOCAL());
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public void seOnItemClickListener(RecycleAdapter.onRecycleViewItemClickListener listener) {
        this.myOnItemClickListener = listener;


    }

    @Override
    public void onClick(View v) {
        if (myOnItemClickListener != null) {
            myOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    public static class CellListViewholder extends RecyclerView.ViewHolder {
        @BindById(R.id.text_eqiutment_address)
        TextView textEqiutmentAddress;
        @BindById(R.id.text_eqiutment_number)
        TextView textEqiutmentNumber;
        @BindById(R.id.text_qzone)
        TextView textQzone;
        TextView textCellID;

        public CellListViewholder(View itemView) {
            super(itemView);
            textEqiutmentAddress = ButterKnife.findById(itemView, R.id.text_eqiutment_address);
            textEqiutmentNumber = ButterKnife.findById(itemView, R.id.text_eqiutment_number);
            textQzone = ButterKnife.findById(itemView, R.id.text_qzone);
            textCellID = ButterKnife.findById(itemView, R.id.text_cell_id);
        }
    }
}