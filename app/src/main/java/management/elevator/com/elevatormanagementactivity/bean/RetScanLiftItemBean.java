package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class RetScanLiftItemBean {
    public ArrayList<RetScanLiftItemBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<RetScanLiftItemBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<RetScanLiftItemBean.Data> datas;
    public static class Data {
        private int ID=0;
        private String SN="";
        private String NAME="";
        private String L_AREA="";
        private String L_PARK="";
        private int TICK_NUM=0;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getSN() {
            return SN;
        }

        public void setSN(String SN) {
            this.SN = SN;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getL_AREA() {
            return L_AREA;
        }

        public void setL_AREA(String l_AREA) {
            L_AREA = l_AREA;
        }

        public String getL_PARK() {
            return L_PARK;
        }

        public void setL_PARK(String l_PARK) {
            L_PARK = l_PARK;
        }

        public int getTICK_NUM() {
            return TICK_NUM;
        }

        public void setTICK_NUM(int TICK_NUM) {
            this.TICK_NUM = TICK_NUM;
        }
    }
}
