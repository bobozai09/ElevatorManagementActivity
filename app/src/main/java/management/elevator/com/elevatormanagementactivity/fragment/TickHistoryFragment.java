package management.elevator.com.elevatormanagementactivity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class TickHistoryFragment extends Fragment {
    private int mType = 1;
    private String mTitle;
    private View viewContent;
    RecyclerView recyclerView;
    SharedPreferences sp;
    String token;
    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewContent=inflater.inflate(R.layout.fragment_context,container,false);
        ButterKnife.bind(getActivity());
        sp = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        token = sp.getString(Constant.LOGIN_TOKEN, "");
        initData();
        return viewContent;
    }
    private void  initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.TICKER;
                String params = Constant.OPER + "=" +
                        Constant.TICK_HIST + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + "p=1"+"&m=10";
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    try {
                        JSONArray jsonArray=new JSONArray(json);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();

    }
}
