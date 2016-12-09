package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class TickSelfBean {

    public ArrayList<Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<Data> datas) {
        this.datas = datas;
    }

    private ArrayList<Data> datas;

    public static class Data {

        /**
         * TID : 2016120217541
         * TYPE : 电梯维保工单
         * NAME : 电梯维保通知
         * TICK : 电梯(0230304050506001)临近维保时间(2016-10-03),请在(2016-10-18)之前对电梯进行维保,并提交记录:电梯半月维保
         * I_STATUS : 0
         * S_STATUS : 未接单
         * DIS_MAN : 赵刚
         * DIS_DTM : 2016-12-02 17:54:36
         * I_RANK : 3
         * S_RANK : 重要
         * IS_URGED : false
         * IS_OVT : false
         * LMT_TIME : 2016-12-03 10:34:36
         * DEL_TIME :
         */

        private long TID;
        private String TYPE;
        private String NAME;
        private String TICK;
        private int I_STATUS;
        private String S_STATUS;
        private String DIS_MAN;
        private String DIS_DTM;
        private int I_RANK;
        private String S_RANK;
        private boolean IS_URGED;
        private boolean IS_OVT;
        private String LMT_TIME;
        private String DEL_TIME;

        public long getTID() {
            return TID;
        }

        public void setTID(long TID) {
            this.TID = TID;
        }

        public String getTYPE() {
            return TYPE;
        }

        public void setTYPE(String TYPE) {
            this.TYPE = TYPE;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public String getTICK() {
            return TICK;
        }

        public void setTICK(String TICK) {
            this.TICK = TICK;
        }

        public int getI_STATUS() {
            return I_STATUS;
        }

        public void setI_STATUS(int I_STATUS) {
            this.I_STATUS = I_STATUS;
        }

        public String getS_STATUS() {
            return S_STATUS;
        }

        public void setS_STATUS(String S_STATUS) {
            this.S_STATUS = S_STATUS;
        }

        public String getDIS_MAN() {
            return DIS_MAN;
        }

        public void setDIS_MAN(String DIS_MAN) {
            this.DIS_MAN = DIS_MAN;
        }

        public String getDIS_DTM() {
            return DIS_DTM;
        }

        public void setDIS_DTM(String DIS_DTM) {
            this.DIS_DTM = DIS_DTM;
        }

        public int getI_RANK() {
            return I_RANK;
        }

        public void setI_RANK(int I_RANK) {
            this.I_RANK = I_RANK;
        }

        public String getS_RANK() {
            return S_RANK;
        }

        public void setS_RANK(String S_RANK) {
            this.S_RANK = S_RANK;
        }

        public boolean isIS_URGED() {
            return IS_URGED;
        }

        public void setIS_URGED(boolean IS_URGED) {
            this.IS_URGED = IS_URGED;
        }

        public boolean isIS_OVT() {
            return IS_OVT;
        }

        public void setIS_OVT(boolean IS_OVT) {
            this.IS_OVT = IS_OVT;
        }

        public String getLMT_TIME() {
            return LMT_TIME;
        }

        public void setLMT_TIME(String LMT_TIME) {
            this.LMT_TIME = LMT_TIME;
        }

        public String getDEL_TIME() {
            return DEL_TIME;
        }

        public void setDEL_TIME(String DEL_TIME) {
            this.DEL_TIME = DEL_TIME;
        }
    }
}
