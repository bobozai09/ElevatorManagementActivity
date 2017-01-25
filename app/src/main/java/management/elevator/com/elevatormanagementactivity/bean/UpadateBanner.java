package management.elevator.com.elevatormanagementactivity.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/10 0010.
 * 首页banner
 */

public class UpadateBanner {
    public ArrayList<UpadateBanner.Data> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<UpadateBanner.Data> datas) {
        this.datas=datas;
    }

    private ArrayList<UpadateBanner.Data> datas;
    public static class Data {


        /**
         * ID : 7
         * IDX : 1 顺序
         * TIT : 关注电梯安全 标题
         * URL :  链接
         * IMG : f5493c209b566cfbab3df11e6a46b5d3.png 图片名字
         * TAG : true 状态
         * VER : 1483946967035 版本
         *
         */

        private int ID;
        private int IDX;
        private String TIT;
        private String URL;
        private String IMG;
        private boolean TAG;
        private long VER;

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

        public String getTIT() {
            return TIT;
        }

        public void setTIT(String TIT) {
            this.TIT = TIT;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public String getIMG() {
            return IMG;
        }

        public void setIMG(String IMG) {
            this.IMG = IMG;
        }

        public boolean isTAG() {
            return TAG;
        }

        public void setTAG(boolean TAG) {
            this.TAG = TAG;
        }

        public long getVER() {
            return VER;
        }

        public void setVER(long VER) {
            this.VER = VER;
        }
    }
}
