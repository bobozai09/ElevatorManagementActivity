package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class MaintenanceRecordBean {
    public ArrayList<MaintenanceRecordBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<MaintenanceRecordBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<MaintenanceRecordBean.Data> datas;
    public static class Data {

        /**
         * ID : 21001
         * IDX : 1
         * NAME : 机房，滑轮间环境
         * NEED : 清洁，门窗完好，照明正常
         */

        private int ID;
        private int IDX;
        private String NAME;
        private String NEED;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getIDX() {
            return IDX;
        }

        public void setIDX(int IDX) {
            this.IDX = IDX;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getNEED() {
            return NEED;
        }

        public void setNEED(String NEED) {
            this.NEED = NEED;
        }
    }
}
