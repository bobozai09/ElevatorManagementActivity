package management.elevator.com.elevatormanagementactivity.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.AlmsubActivity;
import management.elevator.com.elevatormanagementactivity.activity.RetScanLiftItActivity;
import management.elevator.com.elevatormanagementactivity.activity.TransctionActivity;
import management.elevator.com.elevatormanagementactivity.activity.WebViewActivity;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.bean.RetScanLiftItemBean;
import management.elevator.com.elevatormanagementactivity.bean.UpadateBanner;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import management.elevator.com.elevatormanagementactivity.widget.RecycleViewDivider;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 *
 */
public class FragmentCommon extends Fragment implements EasyPermissions.PermissionCallbacks {

    Banner banner;
    @BindById(R.id.search_box)
    EditText searchBox;
    private RecycleAdapter recycleAdapter;
    private RecyclerView recyclerView;
    private List<String> data = new ArrayList<String>();
    public static final int REQUEST_CODD = 111;
    public static final int REQUEST_CAMERA_PERM = 101;
    public static final int HOMEBANNER = 102;
    public static final int NOSEARCHFORRESULT = 103;
    public static final int HAVERESULT = 104;
    public static final int HAVERESULT2 = 105;
    public static final int LOADNOTICE = 106;
    ImageView img_Scan, img_MessageCenter;
    TextView noticeTextView, notice_no_see;
    int showTime = 2000;
    EditText searchInbox;
    Intent intent;
    UpadateBanner upadateBanner;
    RelativeLayout relNotice;
    ImageView imgMessageCenter;
    String token;
    String tempname;
    List testlist;
    UpadateBanner.Data updatebeandata;
    private static List<String> banners = new ArrayList<>();
    String[] bean = {"我的事务", "故障申告", "地理位置", "故障统计", "通知中心", "知识中心", "联系人", "新闻中心", "报表统计"};
    int[] image = {R.mipmap.my_work, R.mipmap.fault_declaration, R.mipmap.my_location,
            R.mipmap.fault_total, R.mipmap.message_center, R.mipmap.knowlege_center,
            R.mipmap.my_contas, R.mipmap.news_center, R.mipmap.my_report};
    RecycleAdapter adapter;
    Message message;

    public static FragmentCommon newInstance(String text) {
        FragmentCommon fragmentCommon = new FragmentCommon();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        fragmentCommon.setArguments(bundle);
        return fragmentCommon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common, container, false);
        ButterKnife.bind(getActivity());
        PreIOC.binder(this, view);
        ZXingLibrary.initDisplayOpinion(getActivity());
        String[] testurl = view.getResources().getStringArray(R.array.url4);
        token = Constant.TOKEN;
        testlist = Arrays.asList(testurl);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        img_MessageCenter = (ImageView) view.findViewById(R.id.img_message_center);
        img_Scan = (ImageView) view.findViewById(R.id.img_scan);
        searchInbox = (EditText) view.findViewById(R.id.search_box);
        banner = (Banner) view.findViewById(R.id.banner);
        img_MessageCenter = (ImageView) view.findViewById(R.id.img_message_center);
        noticeTextView = (TextView) view.findViewById(R.id.text_notice);
        notice_no_see = (TextView) view.findViewById(R.id.text_no_see);
        relNotice = (RelativeLayout) view.findViewById(R.id.rel_notice);
        initmessagecenter();
        initTitle();
        initView();
        initNotice();
        initData();
        InitHideNoitce();
        return view;
    }

    private void InitHideNoitce() {

        notice_no_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relNotice.setVisibility(View.GONE);
            }
        });

    }

    private void initmessagecenter() {
        img_MessageCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toInient(102);
            }
        });

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HOMEBANNER:
                    final UpadateBanner obj = (UpadateBanner) msg.obj;
                    banner.setImageLoader(new GlideImageload());
                    banner.setDelayTime(showTime);
                    for (int i = 0; i < obj.getDatas().size(); i++) {
                        banners.add(Constant.IMG_BASEURL + obj.getDatas().get(i).getIMG());
                    }
                    banner.setImages(banners);
                    banner.start();
                    break;

                case HAVERESULT:
                    Toast.makeText(getActivity(), "扫描成功，正在加载数据 请稍后。。。。", Toast.LENGTH_LONG).show();
                    toInient(100);
                    break;
                case HAVERESULT2:
                    Toast.makeText(getActivity(), "扫描成功，正在加载数据 请稍后。。。。", Toast.LENGTH_LONG).show();
                    toInient(100);
                    break;
                case NOSEARCHFORRESULT:
                    Toast.makeText(getActivity(), "无数据", Toast.LENGTH_LONG).show();
                    break;
                case LOADNOTICE:
                    String mconte = (String) msg.obj;
                    String[] array = mconte.split(",");
                    String result = array[0];
                    String person = array[1];
                    noticeTextView.setText(result);
                    break;
                default:
                    break;
            }
        }
    };

    private void initNotice() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.HOME_URL;
                String params = Constant.OPER + "=" +
                        Constant.UPDATE_NOTIC + "&" + Constant.LOGIN_TOKEN + "=" + token;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String result = jsonObject.getString("result");
                        String reason = jsonObject.getString("reason");
                        String messagecontext = result + "," + reason;
                        message = new Message();
                        message.what = LOADNOTICE;
                        message.obj = messagecontext;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        }).start();

    }

    private void initView() {
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setImageLoader(new GlideImageload());
        banner.setDelayTime(1500);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayout.HORIZONTAL));
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayout.VERTICAL));

        recycleAdapter = new RecycleAdapter(bean, image);
        recyclerView.setAdapter(recycleAdapter);
        recycleAdapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                int position = recyclerView.getChildAdapterPosition(view);
                switch (position) {
                    case 0:
                    case 1:
                    case 5:
                    case 7:
                    case 8:
                    case 2:
                    case 3:
                    case 4:
                    case 6:
                        toInient(position);
                        break;
                    default:
                        break;
                }
            }
        });
        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getActivity().getWindow().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    tempname = searchBox.getText().toString().trim();
                    if (tempname.length() > 0) {
                        Log.i("fragmentcommon", "onKey: " + tempname);
                        ScanAndSearchResult(1, tempname);
                    }
                }
                return false;
            }
        });

    }

    private void toInient(int number) {

        switch (number) {
            case 0:
                intent = new Intent();
                intent.setClass(getContext(), TransctionActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent();
                intent.setClass(getContext(), AlmsubActivity.class);
                startActivity(intent);
                break;
            case 5:
            case 7:
            case 8:
            case 2:
            case 3:
            case 4:
            case 6:
            case 102:
                intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("ID", number);
                intent.putExtras(bundle);
                intent.setClass(getContext(), WebViewActivity.class);
                startActivity(intent);
                break;
            case 100:
                intent = new Intent();
                intent.setClass(getContext(), RetScanLiftItActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }

    }

    private void initTitle() {
        setSearchIcon();
        img_Scan.setOnClickListener(new ButtonOnclickListener(img_Scan.getId()));
    }

    private void initData() {
        for (int i = 0; i < bean.length; i++) {
            data.add("" + i);
        }
        //获取顶部banner
        new Thread(new Runnable() {
            @Override
            public void run() {

                String domain = Constant.BASE_URL + Constant.HOME_URL;
                String params = Constant.OPER + "=" +
                        Constant.UPDATA_BANNER + "&" + Constant.LOGIN_TOKEN + "=" + token;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    ArrayList<UpadateBanner.Data> mlist_flow = new ArrayList<UpadateBanner.Data>();
                    try {
                        upadateBanner = new UpadateBanner();
                        JSONArray jsonArray = new JSONArray(json);
                        for (int u = 0; u < jsonArray.length(); u++) {
                            updatebeandata = new UpadateBanner.Data();
                            JSONObject jsonObject = jsonArray.getJSONObject(u);
                            updatebeandata.setID(jsonObject.getInt("ID"));
                            updatebeandata.setIDX(jsonObject.getInt("IDX"));
                            updatebeandata.setIMG(jsonObject.getString("IMG"));
                            updatebeandata.setTAG(jsonObject.getBoolean("TAG"));
                            updatebeandata.setTIT(jsonObject.getString("TIT"));
                            updatebeandata.setURL(jsonObject.getString("URL"));
                            updatebeandata.setVER(jsonObject.getLong("VER"));

                            mlist_flow.add(updatebeandata);
                        }
                        upadateBanner.setDatas(mlist_flow);
                        message = new Message();
                        message.obj = upadateBanner;
                        message.what = HOMEBANNER;
                        handler.sendMessage(message);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();


    }

    private void setSearchIcon() {
        Drawable drawable = getResources().getDrawable(R.drawable.img_search);
        drawable.setBounds(10, 10, 1 * (drawable.getMinimumWidth()) / 2, 1 * (drawable.getMinimumHeight()) / 2);
        searchInbox.setCompoundDrawables(drawable, null, null, null);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(getActivity(), "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前应用需要申请CAMERA权限，需要打开设置页面么？")
                    .setTitle("权限申请").setPositiveButton("").setNegativeButton("", null).setRequestCode(REQUEST_CAMERA_PERM)
                    .build().show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    private void cameraTask(int viewId) {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            onClick(viewId);
        } else {
            EasyPermissions.requestPermissions(this, "需要请求canera权限", REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PreIOC.unBinder(this);
    }


    class ButtonOnclickListener implements View.OnClickListener {
        private int buttonId;

        public ButtonOnclickListener(int buttonId) {
            this.buttonId = buttonId;

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.img_message_center) {

            } else if (v.getId() == R.id.img_scan) {

                cameraTask(buttonId);

            } else if (v.getId() == R.id.img_message_center) {
                toInient(102);

            }
        }
    }

    private void onClick(int buttonId) {
        switch (buttonId) {
            case R.id.img_scan:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODD);
                break;
            default:
                break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理扫描结果
         */
        if (requestCode == REQUEST_CODD) {
            //处理扫描结果
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {

                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "" + result, Toast.LENGTH_LONG).show();
                    ScanAndSearchResult(0, result);

                } else if (bundle.getInt(CodeUtils.RESULT_STRING) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(getActivity(), "从设置页面返回...", Toast.LENGTH_SHORT)
                    .show();

        }

    }

    public class GlideImageload extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    RetScanLiftItemBean retScanLiftItemBean;
    RetScanLiftItemBean.Data retscanData;

    private void ScanAndSearchResult(final int way, final String content) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.HOME_URL;
                String params = Constant.OPER + "=" +
                        Constant.SCAN_LIFT + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.WAY + "=" + way + "&" + Constant.SN + "=" + content;
                String json = GetSession.post(domain, params);
                message = new Message();
                if (!json.equals("+ER+")) {
                    if (json.equals("[]")) {
                        message.what = NOSEARCHFORRESULT;
                        handler.sendMessage(message);

                    } else {
                        Constant.WAYS = way;
                        Constant.SNS = content;
                        if (way == 1) {
                            message.what = HAVERESULT2;
                        } else if (way == 0) {
                            message.what = HAVERESULT;
                        }
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();


    }

}
