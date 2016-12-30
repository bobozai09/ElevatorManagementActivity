package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.allenliu.badgeview.BadgeFactory;
import com.allenliu.badgeview.BadgeView;

import java.util.ArrayList;
import java.util.IdentityHashMap;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.TickSelfBean;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TranscctionViewHolder> implements View.OnClickListener {
    private LayoutInflater mlayoutInflater;
    Context context;
    private String[] titles;
    private int[] icons;
    private String[] introduce;
    private RecycleAdapter.onRecycleViewItemClickListener myOnItemClickListener=null;
    public TransactionAdapter(Context contexts, String[] titles, int[] icons, String[] introduce) {
        this.icons = icons;
        this.titles = titles;
        this.introduce = introduce;
        this.context = contexts;
        this.mlayoutInflater = LayoutInflater.from(context);
    }
    public static interface onRecycleViewItemClickListener{
        void onItemClick(View view,String data);
    }
    public TransactionAdapter(Context context) {
        this.context = context;
        Log.i("testcontext", "" + context);
        this.mlayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TranscctionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, null);
        view.setOnClickListener(this);
        return new TranscctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TranscctionViewHolder holder, int position) {
        for (int i = 0; i < titles.length; i++) {

            holder.textTransactionIntroduction.setText(introduce[position]);
            holder.textTransactionTitle.setText(titles[position]);
            holder.imgTransactionImg.setImageResource(icons[position]);
            if (position == 2) {
                int lift_mc_number = Constant.LIFT_MC_NUM;
                if (lift_mc_number > 0) {
                    Log.i("lift_mc_number", "" + context);
                    BadgeFactory.createCircle(context).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setBadgeCount(lift_mc_number).bind(holder.imgTransactionImg);
                } else if (lift_mc_number >= 99) {
                    BadgeFactory.createCircle(context).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setBadgeCount(99).bind(holder.imgTransactionImg);
                }
            }
            if (position == 3) {
                int iccm_mc_num = Constant.ICCM_MC_NUM;
                if (iccm_mc_num > 0) {
                    Log.i("lift_mc_number", "" + context);
                    BadgeFactory.createCircle(context).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setBadgeCount(iccm_mc_num).bind(holder.imgTransactionImg);
                } else if (iccm_mc_num >= 99) {
                    BadgeFactory.createCircle(context).setBadgeGravity(Gravity.RIGHT | Gravity.TOP).setBadgeCount(99).bind(holder.imgTransactionImg);
                }
            }

        }

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    @Override
    public void onClick(View v) {
        if (myOnItemClickListener!=null){
            myOnItemClickListener.onItemClick(v,(String) v.getTag());
        }
    }
    public void seOnItemClickListener(RecycleAdapter.onRecycleViewItemClickListener listener){
        this.myOnItemClickListener=listener;
    }

    public static class TranscctionViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTransactionImg;
        TextView textTransactionTitle;
        TextView textTransactionIntroduction;

        public TranscctionViewHolder(View itemView) {
            super(itemView);
            textTransactionIntroduction = ButterKnife.findById(itemView, R.id.text_transaction_introduction);
            textTransactionTitle = ButterKnife.findById(itemView, R.id.text_transaction_title);
            imgTransactionImg = ButterKnife.findById(itemView, R.id.img_transaction_img);
        }
    }

}
