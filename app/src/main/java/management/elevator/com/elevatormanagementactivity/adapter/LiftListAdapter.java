package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.LiftListBean;
import management.elevator.com.elevatormanagementactivity.widget.Constant;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Administrator on 2016/12/23 0023.
 */

public class LiftListAdapter extends RecyclerView.Adapter<LiftListAdapter.LiftListViewHolder> {
    LiftListBean.Data data;
    private LayoutInflater mlayoutInflater;
    private Context mContext;
    SharedPreferences sp;
    ArrayList<LiftListBean.Data> mlist = new ArrayList<LiftListBean.Data>();

    public LiftListAdapter(Context context) {
        mContext = context;
        mlayoutInflater = LayoutInflater.from(context);
    }

    public LiftListAdapter(Context context, LiftListBean obj) {
        this(context);
        mlist.addAll(obj.getDatas());
    }

    @Override
    public LiftListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mlayoutInflater.inflate(R.layout.item_equiment_status, parent, false);
        return new LiftListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LiftListViewHolder holder, int position) {
        data = mlist.get(position);
        Log.i("data====>", "" + data);
        for (int i = 0; i < mlist.size(); i++) {
            holder.text_sn.setText("SN NO:" + data.getSN());
            holder.text_dev_status.setText("" + data.getSTATUS());
            holder.text_l_area.setText("" + data.getL_AREA());
            holder.text_l_park.setText("" + data.getL_PARK());
            holder.text_mc_info.setText("" + data.getMC_INFO());
        }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class LiftListViewHolder extends RecyclerView.ViewHolder {
        public TextView text_mc_info, text_l_park, text_l_area, text_dev_status, text_sn;
        public LinearLayout line_left_status;
        public LiftListViewHolder(View itemView) {
            super(itemView);
            text_sn = ButterKnife.findById(itemView, R.id.text_sn);
            text_dev_status = ButterKnife.findById(itemView, R.id.text_dev_status);
            text_l_area = ButterKnife.findById(itemView, R.id.text_l_area);
            text_l_park = ButterKnife.findById(itemView, R.id.text_l_park);
            text_mc_info = ButterKnife.findById(itemView, R.id.text_mc_info);
            line_left_status = ButterKnife.findById(itemView, R.id.line_left_status);
        }
    }
}
