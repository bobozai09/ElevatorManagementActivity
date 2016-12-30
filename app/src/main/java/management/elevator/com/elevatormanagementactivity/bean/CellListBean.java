package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/30 0030.
 */

public class CellListBean {
    public ArrayList<CellListBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<CellListBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<CellListBean.Data> datas;
    public static class Data {


        /**
         * ID : 100007
         * DESC : 14#
         * LOCAL :
         */

        private int ID;
        private String DESC;
        private String LOCAL;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getDESC() {
            return DESC;
        }

        public void setDESC(String DESC) {
            this.DESC = DESC;
        }

        public String getLOCAL() {
            return LOCAL;
        }

        public void setLOCAL(String LOCAL) {
            this.LOCAL = LOCAL;
        }
    }

}
