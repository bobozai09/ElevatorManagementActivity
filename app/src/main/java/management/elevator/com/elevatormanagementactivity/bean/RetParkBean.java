package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/30 0030.
 * 小区电梯维保数量显示
 */

public class RetParkBean {

    public ArrayList<RetParkBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<RetParkBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<RetParkBean.Data> datas;
    public static class Data {
        public int ID = 0;    // 小区编号
        public String NAME = "";    // 小区名称
        public String AREA = "";    // 区域名称
        public String DEV_NUM = "";    // 设备数量
        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getAREA() {
            return AREA;
        }

        public void setAREA(String AREA) {
            this.AREA = AREA;
        }

        public String getDEV_NUM() {
            return DEV_NUM;
        }

        public void setDEV_NUM(String DEV_NUM) {
            this.DEV_NUM = DEV_NUM;
        }


    }
}
