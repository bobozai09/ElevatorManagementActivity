package management.elevator.com.elevatormanagementactivity.adapter;

import android.content.Context;
import android.support.design.internal.NavigationMenuItemView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class RecycleAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private String []titles;
    private int[]icons;
    private onRecycleViewItemClickListener myOnItemClickListener=null;
    public RecycleAdapter(String[]titles,int[]icons){
        this.icons=icons;
        this.titles=titles;
    }
    public static interface onRecycleViewItemClickListener{
        void onItemClick(View view,String data);

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new NaviHolder(View.inflate(parent.getContext(),R.layout.item_main,null));
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,null);
        NaviHolder holder=new NaviHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NaviHolder)holder).titleText.setText(titles[position]);
        ((NaviHolder)holder).imageView.setImageResource(icons[position]);
    }

    @Override
    public int getItemCount() {
        return icons.length;
    }

    @Override
    public void onClick(View v) {
   if (myOnItemClickListener!=null){
       myOnItemClickListener.onItemClick(v,(String) v.getTag());
   }
    }
public void seOnItemClickListener(onRecycleViewItemClickListener listener){
    this.myOnItemClickListener=listener;


}
    private class  NaviHolder extends RecyclerView.ViewHolder {
        public  TextView titleText;
        public  ImageView imageView;

        public NaviHolder(View itemView) {
            super(itemView);
            titleText= ButterKnife.findById(itemView,R.id.title_text);
            imageView=ButterKnife.findById(itemView,R.id.image_view);
        }
    }



}