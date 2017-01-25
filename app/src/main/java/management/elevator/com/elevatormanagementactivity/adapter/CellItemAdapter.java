package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.CellItemBean;
import management.elevator.com.elevatormanagementactivity.bean.RetParkBean;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class CellItemAdapter extends RecyclerView.Adapter<CellItemAdapter.CellItemViewholder> implements View.OnClickListener {
    CellItemBean.Data data;
    private RecycleAdapter.onRecycleViewItemClickListener myOnItemClickListener = null;
    private LayoutInflater mlayoutInflater;
    private Context mContext;
    ArrayList<CellItemBean.Data> mlist = new ArrayList<CellItemBean.Data>();

    public CellItemAdapter(Context context) {
        mContext = context;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public CellItemAdapter(Context context, CellItemBean obj) {
        this.mContext = context;
        mlist.addAll(obj.getDatas());
    }

    public static interface onRecycleViewItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public CellItemViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell_message, parent, false);
        view.setOnClickListener(this);
        return new CellItemViewholder(view);
    }

    @Override
    public void onBindViewHolder(final CellItemViewholder holder, int position) {
        data = mlist.get(position);
        for (int i = 0; i < mlist.size(); i++) {
            holder.textItemCellName.setText(data.getTMPL());//
            holder.textItemCellDaydate.setText(data.getDTM_END());
            int status = data.getI_STATUS();
           switch (status){
               case 0:
                   holder.imgCellImage.setImageResource(R.mipmap.icon_cell_0);
                   holder.textCellStatus.setText("待维保");
                   break;
               case 1:
                   holder.imgCellImage.setImageResource(R.mipmap.icon_cell_1);
                   holder.textCellStatus.setText("确认中");
                   break;
               case 2:
                   holder.imgCellImage.setImageResource(R.mipmap.icon_done);
                   holder.textCellStatus.setText("已完成");
                   break;
               case 3:
                   holder.imgCellImage.setImageResource(R.mipmap.icon_cell_3);
                   holder.textCellStatus.setText("已超期");
                   break;
           }
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

    public static class CellItemViewholder extends RecyclerView.ViewHolder {
        @BindById(R.id.text_item_cell_name)
        TextView textItemCellName;
        @BindById(R.id.text_item_cell_daydate)
        TextView textItemCellDaydate;
        @BindById(R.id.img_cell_image)
        ImageView imgCellImage;
        @BindById(R.id.text_cell_status)
        TextView textCellStatus;

        public CellItemViewholder(View itemView) {
            super(itemView);
            textItemCellName = ButterKnife.findById(itemView, R.id.text_item_cell_name);
            textItemCellDaydate = ButterKnife.findById(itemView, R.id.text_item_cell_daydate);
            textCellStatus = ButterKnife.findById(itemView, R.id.text_cell_status);
            imgCellImage = ButterKnife.findById(itemView, R.id.img_cell_image);
        }
    }
}