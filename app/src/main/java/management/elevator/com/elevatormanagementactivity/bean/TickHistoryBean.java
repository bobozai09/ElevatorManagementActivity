package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class TickHistoryBean {
    public ArrayList<TickSelfBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<TickSelfBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<TickSelfBean.Data> datas;
    public static class Data {


    }
}
