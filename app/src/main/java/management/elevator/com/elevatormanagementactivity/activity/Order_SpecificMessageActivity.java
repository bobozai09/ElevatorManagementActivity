package management.elevator.com.elevatormanagementactivity.activity;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.SNIServerName;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.bean.TickFlowBean;
import management.elevator.com.elevatormanagementactivity.bean.TickViewBean;
import management.elevator.com.elevatormanagementactivity.utils.BitmapCompressUtils;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.utils.ImageUtils;
import management.elevator.com.elevatormanagementactivity.utils.UriUtils;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import management.elevator.com.elevatormanagementactivity.widget.UnderLineLinearLayout;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2016/12/1 0001.
 * 显示工单详情
 */

public class Order_SpecificMessageActivity extends TakePhotoActivity implements View.OnClickListener {

    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.order_number)
    TextView orderNumber;
    @BindById(R.id.order_status)
    TextView orderStatus;
    @BindById(R.id.order_is_receiver)
    TextView orderIsReceiver;
    @BindById(R.id.ordertype)
    TextView ordertype;
    @BindById(R.id.ordermessage)
    TextView ordermessage;
    @BindById(R.id.order_sendtime)
    TextView orderSendtime;
    @BindById(R.id.order_sendpersomeone)
    TextView orderSendpersomeone;
    @BindById(R.id.order_sendaddress)
    TextView orderSendaddress;
    @BindById(R.id.order_btn_accept)
    Button orderBtnAccept;
    @BindById(R.id.order_btn_refrush)
    Button orderBtnRefrush;
    @BindById(R.id.line_sendsomeoneaddress)
    LinearLayout sendSomeOneAddress;
    @BindById(R.id.txt_source)
    TextView txtSource;
    @BindById(R.id.txt_malfunction)
    TextView txtMalfunction;
    @BindById(R.id.txt_lmt_tiem)
    TextView txtLmtTime;
    @BindById(R.id.btn_tick_new)
    Button btn_takephoto;
    @BindById(R.id.img_takephoto2)
    ImageView img_takephoto2;
    @BindById(R.id.img_takephoto3)
    ImageView img_takephoto3;
    @BindById(R.id.img_takephoto)
    ImageView img_takephoto;

    @BindById(R.id.btn_wanjiguidang)
    Button wanjieguidang;
    //    @BindById(R.id.img_show)
//    ImageView show;
    private String imgString;
    @BindById(R.id.txt_yijian)
    EditText yijian;
    private UnderLineLinearLayout mUnderLinelayout;
    int i = 0;
    ImageView backImageview;

    LinearLayout line_order_status;
    private File file;
    public static final int REQUEST_CODE_CAMERA = 10;
    Intent intent;
    TickViewBean bean;
    TickFlowBean bean_of_flow;
    private static final int LOADDATA = 101;
    private static final int LOADTICKHIST = 102;
    private static final int TICKHISTVIEW = 103;
    private static final String FILE_PATH = "/sdcard/syscamera.jpg";
    ImageView show;
    private static String encodeString = null;
    private String mfilename;
    private Uri mImageUri; //图片路径Uri
    private String mImagePath;
    private Bitmap mBitmap;
    private static String iamgeutil = null;

    //    private PhotoViewAttacher mAttacher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_specimessage);
        PreIOC.binder(this);

        init();
        initData();
        initTickFlow();
    }

    private void init() {
        backImageview = (ImageView) findViewById(R.id.img_back);
        backImageview.setOnClickListener(this);
        mUnderLinelayout = (UnderLineLinearLayout) findViewById(R.id.underline_order_message);
        line_order_status = (LinearLayout) findViewById(R.id.line_order_status);
        line_order_status.setVisibility(View.GONE);
        sendSomeOneAddress.setVisibility(View.VISIBLE);
        btn_takephoto.setOnClickListener(this);
        wanjieguidang.setOnClickListener(this);
        show = (ImageView) findViewById(R.id.img_show);
        img_takephoto2.setOnClickListener(this);
        img_takephoto3.setOnClickListener(this);
    }

    private void initTickFlow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.TICKER;
                final  String tid = intent.getStringExtra("TID");
                final String token = Constant.TOKEN;
                String params = Constant.OPER + "=" +
                        Constant.TICK_FLOW + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TID + "=" + tid;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    TickFlowBean.Data data;
                    bean_of_flow = new TickFlowBean();
                    ArrayList<TickFlowBean.Data> mlist_flow = new ArrayList<TickFlowBean.Data>();
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String dtm = jsonObject.getString("DTM");
                            String man = jsonObject.getString("MAN");
                            String inf = jsonObject.getString("INF");
                            String photo = jsonObject.getString("PHOTO");
                            data = new TickFlowBean.Data();
                            data.setDTM(dtm);
                            data.setMAN(man);
                            data.setINF(inf);
                            data.setPHOTO(photo);
                            mlist_flow.add(data);
                        }
                        bean_of_flow.setDatas(mlist_flow);

                        Message message = new Message();
                        message.obj = bean_of_flow;
                        message.what = LOADTICKHIST;
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initData() {
        intent = getIntent();
        final String token = Constant.TOKEN;
        final String tid = intent.getStringExtra("TID");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.TICKER;
                String params = Constant.OPER + "=" +
                        Constant.TICK_VIEW + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TID + "=" + tid;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    try {
                        ArrayList<TickViewBean.Data> mlist = new ArrayList<TickViewBean.Data>();
                        TickViewBean.Data data;
                        JSONObject jsonObject = new JSONObject(json);
                        bean = new TickViewBean();
                        long tid = jsonObject.getLong("TID");
                        int i_type = jsonObject.getInt("I_TYPE");
                        String s_type = jsonObject.getString("S_TYPE");
                        int tmpl = jsonObject.getInt("TMPL");
                        String name = jsonObject.getString("NAME");
                        String tick = jsonObject.getString("TICK");
                        int i_status = jsonObject.getInt("I_STATUS");
                        String s_status = jsonObject.getString("S_STATUS");
                        String dis_man = jsonObject.getString("DIS_MAN");
                        String dis_dtm = jsonObject.getString("DIS_DTM");
                        int i_rank = jsonObject.getInt("I_RANK");
                        String s_rank = jsonObject.getString("S_RANK");
                        boolean is_urged = jsonObject.getBoolean("IS_URGED");
                        boolean is_ovt = jsonObject.getBoolean("IS_OVT");
                        String lmt_time = jsonObject.getString("LMT_TIME");
                        int dev_spe = jsonObject.getInt("DEV_SPE");
                        int dev_oid = jsonObject.getInt("DEV_OID");
                        String dev_osn = jsonObject.getString("DEV_OSN");
                        String dev_loc = jsonObject.getString("DEV_LOC");
                        String alm_cate = jsonObject.getString("ALM_CATE");
                        String alm_sta = jsonObject.getString("ALM_STA");
                        String alm_content = jsonObject.getString("ALM_CONTENT");
                        String alm_advise = jsonObject.getString("ALM_ADVISE");
                        String del_dtm = jsonObject.getString("DEL_DTM");
                        data = new TickViewBean.Data();
                        data.setTID(tid);
                        data.setI_TYPE(i_type);
                        data.setS_TYPE(s_type);
                        data.setTMPL(tmpl);
                        data.setNAME(name);
                        data.setTICK(tick);
                        data.setI_STATUS(i_status);
                        data.setS_STATUS(s_status);
                        data.setDIS_MAN(dis_man);
                        data.setDIS_DTM(dis_dtm);
                        data.setI_RANK(i_rank);
                        data.setS_RANK(s_rank);
                        data.setIS_URGED(is_urged);
                        data.setIS_OVT(is_ovt);
                        data.setLMT_TIME(lmt_time);
                        data.setDEV_SPE(dev_spe);
                        data.setDEV_OID(dev_oid);
                        data.setDEV_OSN(dev_osn);
                        data.setDEV_LOC(dev_loc);
                        data.setALM_CATE(alm_cate);
                        data.setALM_STA(alm_sta);
                        data.setALM_CONTENT(alm_content);
                        data.setALM_ADVISE(alm_advise);
                        data.setDEL_DTM(del_dtm);
                        mlist.add(data);
                        bean.setDatas(mlist);
                        Message message = new Message();
                        message.obj = bean;
                        message.what = LOADDATA;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TICKHISTVIEW:
                    String result = (String) msg.obj;
                    if (result.equals("succ")) {
                      Toast.makeText(getApplicationContext(),"工单提交成功！",Toast.LENGTH_LONG).show();
                        yijian.setText("");

                    }else {
                        Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                    }
                    break;
                case LOADDATA:
                    final TickViewBean obj = (TickViewBean) msg.obj;
                    Log.i("order -specific", "" + obj.getDatas().get(0));
                    orderNumber.setText(String.valueOf(obj.getDatas().get(0).getTID()));
                    orderStatus.setText(obj.getDatas().get(0).getS_RANK());
                    orderIsReceiver.setText(obj.getDatas().get(0).getS_STATUS());
                    ordertype.setText("故障内容：" + obj.getDatas().get(0).getTICK());
                    orderSendtime.setText("派单时间：" + obj.getDatas().get(0).getDIS_DTM());
                    orderSendpersomeone.setText("派单人：" + obj.getDatas().get(0).getDIS_MAN());
                    orderSendaddress.setText("地   址:" + obj.getDatas().get(0).getDEV_LOC());
                    txtSource.setText(obj.getDatas().get(0).getALM_CATE());
                    txtMalfunction.setText(obj.getDatas().get(0).getDEV_OSN());
                    txtLmtTime.setText(obj.getDatas().get(0).getLMT_TIME());
                    break;
                case LOADTICKHIST:
                    final TickFlowBean obj_hist = (TickFlowBean) msg.obj;
                    Log.i("data",obj_hist.toString());
                    for (int i = 0; i < obj_hist.getDatas().size(); i++) {
                        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_vertical, mUnderLinelayout, false);
                        ((TextView) v.findViewById(R.id.tx_action)).setText("" + obj_hist.getDatas().get(i).getINF());
                        ((TextView) v.findViewById(R.id.tx_action_time)).setText("" + obj_hist.getDatas().get(i).getDTM());
                        ((TextView) v.findViewById(R.id.tx_action_status)).setText("");
                        mUnderLinelayout.addView(v);
                        i++;
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_tick_new:
                takepic();
                break;
            case R.id.btn_wanjiguidang:
                String text = yijian.getText().toString().trim();
                if (encodeString.length() == 0) {
                    Toast.makeText(this, "请先拍照上传", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (text.equals("")) {
                    Toast.makeText(this, "随便说点什么吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitArchive(text);
                break;
//            case R.id.img_takephoto2:
//                takepic();
//                break;
//            case  R.id.img_takephoto3:
//                takepic();
//                break;
            default:
                break;

        }
    }

    private void submitArchive(final String word) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String p = "data:image/jpg;base64," + encodeString;
                try {
                    p = URLEncoder.encode(p, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String domain = Constant.BASE_URL + Constant.TICKER;
                final String tid = intent.getStringExtra("TID");
                final String token = Constant.TOKEN;
                String params = Constant.OPER + "=" +
                        Constant.TICK_NEW_FLOW + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TID + "=" + tid + "&" + Constant.INF + "=" + word + "&" + Constant.PHOTO + "=" + p;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String result = jsonObject.getString("result");
                        Message message = new Message();
                        message.obj = result;
                        message.what = TICKHISTVIEW;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void takepic() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        mfilename = format.format(date);
        Log.i("data", "" + mfilename);
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File outputImage = new File(path, mfilename + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
            mImagePath = path + "/" + mfilename + ".jpg";
            Log.d("data", "mImagePath=" + mImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageUri = Uri.fromFile(outputImage);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 0) {
            try {
                mBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                img_takephoto.setImageBitmap(mBitmap);
                encodeString = ImageUtils.bitmapToString(mImagePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
