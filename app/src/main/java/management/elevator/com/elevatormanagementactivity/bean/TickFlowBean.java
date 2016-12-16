package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/13 0013.
 *
 */

public class TickFlowBean {
    public ArrayList<TickFlowBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<TickFlowBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<TickFlowBean.Data> datas;
    public static class Data {


        /**
         * 工单流程列表
         * DTM : 2016-12-02 17:54:36
         * MAN : 赵刚
         * INF : 界面手动派发工单:2016120217541
         * PHOTO :
         */

        private String DTM;
        private String MAN;
        private String INF;
        private String PHOTO;

        public String getDTM() {
            return DTM;
        }

        public void setDTM(String DTM) {
            this.DTM = DTM;
        }

        public String getMAN() {
            return MAN;
        }

        public void setMAN(String MAN) {
            this.MAN = MAN;
        }

        public String getINF() {
            return INF;
        }

        public void setINF(String INF) {
            this.INF = INF;
        }

        public String getPHOTO() {
            return PHOTO;
        }

        public void setPHOTO(String PHOTO) {
            this.PHOTO = PHOTO;
        }
    }
}
