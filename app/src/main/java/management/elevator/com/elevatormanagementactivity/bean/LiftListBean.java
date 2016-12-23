package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * w我的电梯列表
 * Created by Administrator on 2016/12/23 0023.
 */

public class LiftListBean {
    public ArrayList<LiftListBean.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<LiftListBean.Data> datas) {
        this.datas = datas;
    }
    private ArrayList<LiftListBean.Data> datas;
    public static class Data {

        /**
         * ID : 21
         * SN : 8007a20327e3
         * L_AREA : 重庆市.重庆市.江北区
         * L_PARK : 海王星办公室
         * L_POINT :
         * DEV_NAME :
         * DEV_TYPE : 乘用电梯
         * DEV_VND : 无名厂家
         * STATUS : 有告警(4)
         * MC_INFO : 暂无维保待做
         */

        private int ID;
        private String SN;
        private String L_AREA;
        private String L_PARK;
        private String L_POINT;
        private String DEV_NAME;
        private String DEV_TYPE;
        private String DEV_VND;
        private String STATUS;
        private String MC_INFO;

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

        public String getL_AREA() {
            return L_AREA;
        }

        public void setL_AREA(String L_AREA) {
            this.L_AREA = L_AREA;
        }

        public String getL_PARK() {
            return L_PARK;
        }

        public void setL_PARK(String L_PARK) {
            this.L_PARK = L_PARK;
        }

        public String getL_POINT() {
            return L_POINT;
        }

        public void setL_POINT(String L_POINT) {
            this.L_POINT = L_POINT;
        }

        public String getDEV_NAME() {
            return DEV_NAME;
        }

        public void setDEV_NAME(String DEV_NAME) {
            this.DEV_NAME = DEV_NAME;
        }

        public String getDEV_TYPE() {
            return DEV_TYPE;
        }

        public void setDEV_TYPE(String DEV_TYPE) {
            this.DEV_TYPE = DEV_TYPE;
        }

        public String getDEV_VND() {
            return DEV_VND;
        }

        public void setDEV_VND(String DEV_VND) {
            this.DEV_VND = DEV_VND;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getMC_INFO() {
            return MC_INFO;
        }

        public void setMC_INFO(String MC_INFO) {
            this.MC_INFO = MC_INFO;
        }
    }
}
