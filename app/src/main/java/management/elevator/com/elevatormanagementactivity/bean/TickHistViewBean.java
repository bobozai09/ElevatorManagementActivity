package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class TickHistViewBean {
    public ArrayList<TickHistViewBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<TickHistViewBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<TickHistViewBean.Data> datas;
    public static class Data {


        /**
         * TID : 2016120217541
         * I_TYPE : 20
         * S_TYPE : 电梯维保工单
         * TMPL : 21
         * NAME : 电梯维保通知
         * TICK : 电梯(0230304050506001)临近维保时间(2016-10-03),请在(2016-10-18)之前对电梯进行维保,并提交记录:电梯半月维保
         * I_STATUS : 3
         * S_STATUS : 已完成
         * DIS_MAN : 赵刚
         * DIS_DTM : 2016-12-02 17:54:36
         * I_RANK : 3
         * S_RANK : 重要
         * IS_URGED : false
         * IS_OVT : false
         * LMT_TIME : 2016-12-03 10:34:36
         * DEV_SPE : 0
         * DEV_OID : 3
         * DEV_OSN : 0230304050506001
         * DEV_LOC : 渝中区.恒大城
         * ALM_CATE : 业务告警
         * ALM_STA : 2016-09-30 17:56:00
         * ALM_CONTENT : 电梯(0230304050506001)临近维保时间(2016-10-03),请在(2016-10-18)之前对电梯进行维保,并提交记录:电梯半月维保
         * ALM_ADVISE :
         * DEL_DTM : 2016-12-21 15:58:40
         */

        private long TID;
        private int I_TYPE;
        private String S_TYPE;
        private int TMPL;
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
        private int DEV_SPE;
        private int DEV_OID;
        private String DEV_OSN;
        private String DEV_LOC;
        private String ALM_CATE;
        private String ALM_STA;
        private String ALM_CONTENT;
        private String ALM_ADVISE;
        private String DEL_DTM;

        public long getTID() {
            return TID;
        }

        public void setTID(long TID) {
            this.TID = TID;
        }

        public int getI_TYPE() {
            return I_TYPE;
        }

        public void setI_TYPE(int I_TYPE) {
            this.I_TYPE = I_TYPE;
        }

        public String getS_TYPE() {
            return S_TYPE;
        }

        public void setS_TYPE(String S_TYPE) {
            this.S_TYPE = S_TYPE;
        }

        public int getTMPL() {
            return TMPL;
        }

        public void setTMPL(int TMPL) {
            this.TMPL = TMPL;
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

        public int getDEV_SPE() {
            return DEV_SPE;
        }

        public void setDEV_SPE(int DEV_SPE) {
            this.DEV_SPE = DEV_SPE;
        }

        public int getDEV_OID() {
            return DEV_OID;
        }

        public void setDEV_OID(int DEV_OID) {
            this.DEV_OID = DEV_OID;
        }

        public String getDEV_OSN() {
            return DEV_OSN;
        }

        public void setDEV_OSN(String DEV_OSN) {
            this.DEV_OSN = DEV_OSN;
        }

        public String getDEV_LOC() {
            return DEV_LOC;
        }

        public void setDEV_LOC(String DEV_LOC) {
            this.DEV_LOC = DEV_LOC;
        }

        public String getALM_CATE() {
            return ALM_CATE;
        }

        public void setALM_CATE(String ALM_CATE) {
            this.ALM_CATE = ALM_CATE;
        }

        public String getALM_STA() {
            return ALM_STA;
        }

        public void setALM_STA(String ALM_STA) {
            this.ALM_STA = ALM_STA;
        }

        public String getALM_CONTENT() {
            return ALM_CONTENT;
        }

        public void setALM_CONTENT(String ALM_CONTENT) {
            this.ALM_CONTENT = ALM_CONTENT;
        }

        public String getALM_ADVISE() {
            return ALM_ADVISE;
        }

        public void setALM_ADVISE(String ALM_ADVISE) {
            this.ALM_ADVISE = ALM_ADVISE;
        }

        public String getDEL_DTM() {
            return DEL_DTM;
        }

        public void setDEL_DTM(String DEL_DTM) {
            this.DEL_DTM = DEL_DTM;
        }
    }


    }
