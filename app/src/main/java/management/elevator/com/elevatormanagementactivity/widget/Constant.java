package management.elevator.com.elevatormanagementactivity.widget;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class Constant {
    public static final  String USERNAME="uname";
    public  static String PASSWORD="upass";
    public  static String LOGIN_TOKEN="token";
    public static String OPER="oper";
    public static String LOGIN_INFO="login";
    public static String DEVICESID="devid";
    public static String IDCODE="idcode";
    public static String RESET="reset";
    public static String CODE="code";
    public  static String HttpSessionId=null;
    public static List<String> HttpSetCookies	= null;
    public static String AUTH="auth";
    //当前工单
    public  static String TICK_SELF="tick-self";
    //历史工单
    public  static String TICK_HIST="tick-hist";
    //单位工单
    public  static String TICK_CORP="tick-corp";
    /**
     * 接口地址
     */
    public  static String BASE_URL="http://m.sptesyun.com:9002";
    /**
     * 账号登录
     */
    public static String LOGIN="/api/login.json";
    /***
     * 重置密码获取验证码
     */

    public static String INFO="/api/info.json";
    public static  String TICKER="/api/ticket.json";
    public static String LOCATION_IME="357748051865320";



}
