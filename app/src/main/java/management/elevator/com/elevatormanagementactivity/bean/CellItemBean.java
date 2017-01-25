package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

import management.elevator.com.elevatormanagementactivity.adapter.CellItemAdapter;

/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class CellItemBean {
    public ArrayList<CellItemBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<CellItemBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<CellItemBean.Data> datas;
    public static class Data {

        /**
         * ID : 229
         * TMPL : 电梯半月维保
         * DTM_END : 2017-05-30(147天后)
         * I_STATUS : 0
         */

        private int ID;
        private String TMPL;
        private String DTM_END;
        private int I_STATUS;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getTMPL() {
            return TMPL;
        }

        public void setTMPL(String TMPL) {
            this.TMPL = TMPL;
        }

        public String getDTM_END() {
            return DTM_END;
        }

        public void setDTM_END(String DTM_END) {
            this.DTM_END = DTM_END;
        }

        public int getI_STATUS() {
            return I_STATUS;
        }

        public void setI_STATUS(int I_STATUS) {
            this.I_STATUS = I_STATUS;
        }
    }


}
