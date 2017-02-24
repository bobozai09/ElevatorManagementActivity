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
    /**
     * 获取设备维修记录列表
     */
    public static String DEV_MC_LIST="dev-mc-list";
    /**
     * h获取是被维保项目列表
     */
    public static String DEV_MC_TMPL="dev-mc-tmpl";
    /**
     * 提交设备维保记录
     */
    public static String DEV_MC_SUBMIT="dev-mc-submit";
    /***
     * 查看维保结果明细
     */
    public static String DEV_MC_RESULT="dev-mc-result";
    public static String SPE="spe";
    public static String ID="id";
    public static String TMPL="tmpl";
    /***
     * 一般常量
     */
    public  static int LIFT_MC_NUM;
    public static int ICCM_MC_NUM;
    public  static int LENGTH=0;
    /**
     * 参数常量
     */
    public static String C="c=0";
    /**
     * 知识中心
     */
    public static String KNOWLEAGE="/view/know.json";
    /**
     * 新闻中心
     */
    public static String NEWS="/view/news.json";
    /**
     * 故障统计
     */
    public  static String STATALM="/view/statalm.json";
    /**
     * 报表统计
     */
    public static String REPORT="/view/report.json";
    /***
     * 我的地图
     */
    public static String MAP="/view/maps.json";
    /**
     * 我的通讯录
     */
    public  static String PHONEBOOK="/view/phonebook.json";
    /**
     * 我的首页baner
     */
    public  static String HOME_URL="/api/home.json";
    public static String UPDATA_BANNER="update-banner";
    /**
     * 扫描 电梯二维码
     */
    public static String SCAN_LIFT="scan-lift";
    /**
     * way
     */
    public static String WAY="way";
    public static String SN="sn";
    public  static int  WAYS;
    public static String SNS="";
    public  static String IMG_BASEURL="http://www.sptesyun.com/disk/app/homead/";
    public static String SCANLIFTDETAIL= "/view/scanliftdetail.json";
    //个人信息
    public static int LOGIN_USERID;
    public static String LOGIN_USERNAME="";
    public static String	LOGINNAME= "";	// 真实姓名
    public static String	HEAD= "";	// 头像
    public static String	CORP_NAME	= "";	// 公司名称
    public static String	DEPT_NAME	= "";	// 部门名称
    public static String	DUTY_NAME	= "";	// 职位名称
    public  static String	ROLE_NAME	= "";	// 角色名称

    /**
     * 我的事务
     */
    public static String WORKSIGN="/view/worksign.json";
    public static String WORKSELF="/view/workself.json";
    public static String PARKRUN="/view/parkrun.json";
    public static String DEVMCCHECK="/view/devmccheck.json";
    public static String LONGITUDE;
    public static String LATITUDE;
    /**
     * 获取告警列表
     */
    public static String ALMSUB="/api/almsub.json";
public static String GET_ALMNAME="get-almname";
    public static String SUB_ALARM="sub-alarm";
    public static String NOTICE="/view/notice.json";
    public static String UPDATE_NOTIC="update-notice";
    public static String LOGIN_SUCCESS_USERN_NAME="";


    public static String DOWNLOADURL="/app/ver.jsp";

}
