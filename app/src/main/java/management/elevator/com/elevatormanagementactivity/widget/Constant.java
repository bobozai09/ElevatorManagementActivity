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
    public static String TID="tid";
    public static String REASON="reason";
    public static String INF="inf";
    public static String PHOTO="photo";
    public  static  String TOKEN=null;
    //当前工单
    public  static String TICK_SELF="tick-self";
    //工单结单提交
    public  static String TICK_DONE="tick-done";
    //历史工单
    public  static String TICK_HIST="tick-hist";
    //单位工单
    public  static String TICK_CORP="tick-corp";

    /***
     * 单位工单拦接
     */
    public  static String TICK_HOLD="tick-hold";
    /**
     * 当前工单拒接
     */
    public  static String TICK_REJECT="tick-reject";
    /**
     * 当前工单接单
     */
    public  static  String TICK_RECEIVE="tick-receive";


     /**
     *
     * 接口地址
     */
    public  static String BASE_URL="http://m.sptesyun.com:9002";
    /**
     * 历史工单详情
     **/
    public static String TICK_HIST_VIEW="tick-hist-view";
    /***
     * 历史工单流程列表
     */
    public static String TICK_HIST_FLOW="tick-hist-flow";
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
    public static String DEVLIFT="/api/devlift.json";

/**
 * svn address
 *  https://125.62.26.12/svn/esims-UI
 *   管理后台
 *   http://www.sptesyun.com/main.jsp
 *
 */
/**
 * 当前工单详情
 */
    public static String TICK_VIEW="tick-view";
    /***
     * 当前工单流程列表
     */
    public static String TICK_FLOW="tick-flow";
    /**
     * 工单流程提交
     */
    public static String TICK_NEW_FLOW="tick-new-flow";
    public  static String URL=BASE_URL + TICKER;
    public static String DEVLIFTURL=BASE_URL+DEVLIFT;

    /**
     * 设备
     */
    public static String LIFT_NUM="lift-num";
    public static String LIFT_LIST="lift-list";

/***
 * 我的事务
 */
   public  static String DEVMCJSON="/api/devmc.json";
/***
 * 设备维护 电梯或中断待保养数量
 */
    public  static String MC_BADGE_NUM="mc-badge-num";


    public  static String URL_TRANSCTION=BASE_URL+DEVMCJSON;
/**
 * 获取小区列表
 */
    public static String PARK_LIST="park-list";
    /**
     * 获取小区所属设备列表
     */
    public  static String PARK_DEV_LIST="park-dev-list";
    public static String SPE="spe";
    public static String ID="id";
    /***
     * 一般常量
     */
    public  static int LIFT_MC_NUM;
    public static int ICCM_MC_NUM;
}
