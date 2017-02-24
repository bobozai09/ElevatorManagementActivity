package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public class UpdateInfo {
    public ArrayList<UpdateInfo.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<UpdateInfo.Data> datas) {
        this.datas = datas;
    }

    private ArrayList<UpdateInfo.Data> datas;

    public static class Data {
        private int vercode;
        private String vername;
        private String appname;
        private String verdesc;

        public int getVercode() {
            return vercode;
        }

        public void setVercode(int vercode) {
            this.vercode = vercode;
        }

        public String getVername() {
            return vername;
        }

        public void setVername(String vername) {
            this.vername = vername;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getVerdesc() {
            return verdesc;
        }

        public void setVerdesc(String verdesc) {
            this.verdesc = verdesc;
        }
    }
}