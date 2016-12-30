package management.elevator.com.elevatormanagementactivity.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.TickHistoryBean;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/11 0011.
 * d单位工单
 */

public class TickCorpFragment extends Fragment {
    @BindById(R.id.viewstub_one)
    ViewStub viewstubOne;
    @BindById(R.id.rec_order)
    RecyclerView recOrder;
    private int mType = 1;
    private String mTitle;
    private View viewContent;
    RecyclerView recyclerView;
    SharedPreferences sp;
    String token;
    TickHistoryBean bean;
    TickHistoryBean.Data data;
    LinearLayout linearLayout_nodata;
    private static final int NODATA_SHOW_NO_MESSAGE = 101;
    private static final int NODATASHOW = 110;

    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContent = inflater.inflate(R.layout.fragment_context, container, false);
        ButterKnife.bind(getActivity());
        token = Constant.TOKEN;
        initData();
        initView(viewContent);
        PreIOC.binder(this, viewContent);
        return viewContent;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NODATA_SHOW_NO_MESSAGE:
                    break;
                case NODATASHOW:
                    try {
                        viewstubOne.inflate();
                    } catch (Exception e) {
                        viewstubOne.setVisibility(View.VISIBLE);
                    }

                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Message message = handler.obtainMessage();
                String domain = Constant.BASE_URL + Constant.TICKER;
                String params = Constant.OPER + "=" + Constant.TICK_CORP + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + "p=1";
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")){
                    try {
                        if (json.equals("[]")) {
                           Message  message = new Message();
                            message.what = NODATASHOW;
                            handler.sendMessage(message);
                        }
                        JSONArray jsonArray = new JSONArray(json);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();


    }

    private void initView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.rec_order);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreIOC.unBinder(this);
    }
}
