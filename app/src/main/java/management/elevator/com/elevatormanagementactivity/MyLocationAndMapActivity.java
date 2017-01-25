package management.elevator.com.elevatormanagementactivity;

import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;

import org.apache.http.util.EncodingUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import management.elevator.com.elevatormanagementactivity.widget.Constant;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * 获取当前位置
 */

public class MyLocationAndMapActivity extends CheckPermissionActivity
        implements
        OnCheckedChangeListener,
        OnClickListener,
        CompoundButton.OnCheckedChangeListener {
    @BindById(R.id.tex_title)
    TextView texTitle;
    private RadioGroup rgLocationMode;
    private EditText etInterval;
    private EditText etHttpTimeout;
    private CheckBox cbOnceLocation;
    private CheckBox cbAddress;
    private CheckBox cbGpsFirst;
    private CheckBox cbCacheAble;
    private CheckBox cbOnceLastest;
    private CheckBox cbSensorAble;
    private TextView tvResult, textTitle;
    private Button btLocation;
    private WebView webview;
    private ImageView imgBack;
    //    Bundle bundler;
    String url;
    int id;
    /**
     * 开始定位
     */
    public final static int MSG_LOCATION_START = 0;
    /**
     * 定位完成
     */
    public final static int MSG_LOCATION_FINISH = 1;
    /**
     * 停止定位
     */
    public final static int MSG_LOCATION_STOP = 2;
    private static final String TAG = "MyLocationAndMapActivity";
    public final static String KEY_URL = "URL";
    public final static String URL_H5LOCATION = "file:///android_asset/location.html";
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        PreIOC.binder(this);
        initView();
        //初始化定位
        initLocation();
//            initData();

    }


    private void initData() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    //初始化控件
    private void initView() {
        imgBack= (ImageView) findViewById(R.id.img_back);
        rgLocationMode = (RadioGroup) findViewById(R.id.rg_locationMode);
        webview = (WebView) findViewById(R.id.web_mylocation);
        textTitle =(TextView) findViewById(R.id.tex_title);
        textTitle.setText("值班签到");
//        webview.loadUrl("http://www.baidu.com/");
        etInterval = (EditText) findViewById(R.id.et_interval);
        etHttpTimeout = (EditText) findViewById(R.id.et_httpTimeout);

        cbOnceLocation = (CheckBox) findViewById(R.id.cb_onceLocation);
        cbGpsFirst = (CheckBox) findViewById(R.id.cb_gpsFirst);
        cbAddress = (CheckBox) findViewById(R.id.cb_needAddress);
        cbCacheAble = (CheckBox) findViewById(R.id.cb_cacheAble);
        cbOnceLastest = (CheckBox) findViewById(R.id.cb_onceLastest);
        cbSensorAble = (CheckBox) findViewById(R.id.cb_sensorAble);

        tvResult = (TextView) findViewById(R.id.tv_result);
        btLocation = (Button) findViewById(R.id.bt_location);

        rgLocationMode.setOnCheckedChangeListener(this);
        cbAddress.setOnCheckedChangeListener(this);
        cbCacheAble.setOnCheckedChangeListener(this);
        btLocation.setOnClickListener(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadsImagesAutomatically(true);
        imgBack.setOnClickListener(this);
//        bundler = new Bundle();
//        bundler = this.getIntent().getExtras();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (null == locationOption) {
            locationOption = new AMapLocationClientOption();
        }
        switch (checkedId) {
            case R.id.rb_batterySaving:
                locationOption.setLocationMode(AMapLocationMode.Battery_Saving);
                break;
            case R.id.rb_deviceSensors:
                locationOption.setLocationMode(AMapLocationMode.Device_Sensors);
                break;
            case R.id.rb_hightAccuracy:
                locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
                break;
            default:
                break;
        }
    }

    /**
     * 设置控件的可用状态
     */
    private void setViewEnable(boolean isEnable) {
        for (int i = 0; i < rgLocationMode.getChildCount(); i++) {
            rgLocationMode.getChildAt(i).setEnabled(isEnable);
        }
        etInterval.setEnabled(isEnable);
        etHttpTimeout.setEnabled(isEnable);
        cbOnceLocation.setEnabled(isEnable);
        cbGpsFirst.setEnabled(isEnable);
        cbAddress.setEnabled(isEnable);
        cbCacheAble.setEnabled(isEnable);
        cbOnceLastest.setEnabled(isEnable);
        cbSensorAble.setEnabled(isEnable);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_location) {
            if (btLocation.getText().equals(
                    getResources().getString(R.string.startLocation))) {
                setViewEnable(false);
                btLocation.setText(getResources().getString(
                        R.string.stopLocation));
                tvResult.setText("正在定位...");
                startLocation();
            } else {
                setViewEnable(true);
                btLocation.setText(getResources().getString(
                        R.string.startLocation));
                stopLocation();
                tvResult.setText("定位停止");
            }
        }else if (v.getId()==R.id.img_back){
            finish();

        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (null == locationOption) {
            locationOption = new AMapLocationClientOption();
        }
        switch (buttonView.getId()) {
            case R.id.cb_needAddress:
                if (null != locationOption) {
                    locationOption.setNeedAddress(isChecked);
                }
                break;
            case R.id.cb_cacheAble:
                if (null != locationOption) {
                    locationOption.setLocationCacheEnable(isChecked);
                }
                break;
            default:
                break;
        }
        if (null != locationClient) {
            locationClient.setLocationOption(locationOption);
        }
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
        startLocation();
        //  Log.i("myloca",""+Constant.LONGITUDE+"\n"+Constant.LATITUDE);
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(20000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(10000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        return mOption;
    }

    public synchronized static String getLocationStr(AMapLocation location) {
        if (null == location) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + location.getLocationType() + "\n");
            sb.append("经    度    : " + location.getLongitude() + "\n");
            sb.append("纬    度    : " + location.getLatitude() + "\n");
            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + location.getProvider() + "\n");

            String longitude = location.getLongitude() + "";
            Constant.LONGITUDE = longitude;
            String Latitude = location.getLatitude() + "";
            Constant.LATITUDE = Latitude;
            if (location.getProvider().equalsIgnoreCase(
                    LocationManager.GPS_PROVIDER)) {
                // 以下信息只有提供者是GPS时才会有
                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : "
                        + location.getSatellites() + "\n");
            } else {
                // 提供者是GPS时是没有以下信息的
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");
                //定位完成的时间
                sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
            }
        } else {
            //定位失败
            sb.append("定位失败" + "\n");
            sb.append("错误码:" + location.getErrorCode() + "\n");
            sb.append("错误信息:" + location.getErrorInfo() + "\n");
            sb.append("错误描述:" + location.getLocationDetail() + "\n");
        }
        //定位之后的回调时间
        sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

        return sb.toString();
    }

    private static SimpleDateFormat sdf = null;

    public synchronized static String formatUTC(long l, String strPattern) {
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
//                String result = LocationUtils.getLocationStr(loc);
//                tvResult.setText(result);

                String result = getLocationStr(loc);
                Log.i("myloca", "" + Constant.LONGITUDE + "\n" + Constant.LATITUDE);
                // tvResult.setText(result);
                id = Constant.LOGIN_USERID;
                String location = Constant.LONGITUDE + "," + Constant.LATITUDE;
                String uid = id + "" + "," + location;
                Log.d("2333333", "" + url);
                url = Constant.BASE_URL + Constant.WORKSIGN;
                webview.postUrl(url, EncodingUtils.getBytes(uid + "", "utf-8"));
                initData();

            } else {
                tvResult.setText("定位失败，loc is null");
            }
        }
    };

    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(cbAddress.isChecked());
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(cbGpsFirst.isChecked());
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(cbCacheAble.isChecked());
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(cbOnceLastest.isChecked());
        //设置是否使用传感器
        locationOption.setSensorEnable(cbSensorAble.isChecked());
        String strInterval = etInterval.getText().toString();
        if (!TextUtils.isEmpty(strInterval)) {
            try {
                // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
                locationOption.setInterval(Long.valueOf(strInterval));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        String strTimeout = etHttpTimeout.getText().toString();
        if (!TextUtils.isEmpty(strTimeout)) {
            try {
                // 设置网络请求超时时间
                locationOption.setHttpTimeOut(Long.valueOf(strTimeout));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
