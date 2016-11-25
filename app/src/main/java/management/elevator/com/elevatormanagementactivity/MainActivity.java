package management.elevator.com.elevatormanagementactivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import management.elevator.com.elevatormanagementactivity.adapter.FragmentAdapter;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.broadcast.BroadcastManager;
import management.elevator.com.elevatormanagementactivity.utils.SharedPrefUtils;
import management.elevator.com.elevatormanagementactivity.widget.BottomNavigatorView;
import management.elevator.com.elevatormanagementactivity.widget.RecycleViewDivider;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2016/11/16 0016.
 */

public class MainActivity extends BaseActivity implements BottomNavigatorView.OnBottomNavigatorViewItemClickListener, EasyPermissions.PermissionCallbacks {
    private static final int DEFAULT_POSITION = 0;

    private FragmentNavigator mNavigator;

    private BottomNavigatorView bottomNavigatorView;

    private MenuItem mLoginMenu;

    private MenuItem mLogoutMenu;
    private RecyclerView recyclerView;
    private List<String> data = new ArrayList<String>();
    private RecycleAdapter recycleAdapter;
    ImageView img_Scan, img_MessageCenter;
    EditText searchInbox;

    /**
     * 扫描跳转
     *
     * @param savedInstanceState
     */
    public static final int REQUEST_CODD = 111;
    public static final int REQUEST_CAMERA_PERM = 101;
    String[] bean = {"我的事务", "故障申告", "地理位置", "故障统计", "通知中心", "知识中心", "联系人", "新闻中心", "报表统计"};
    int[] image = {R.mipmap.my_work, R.mipmap.fault_declaration, R.mipmap.my_location,
            R.mipmap.fault_total, R.mipmap.message_center, R.mipmap.knowlege_center,
            R.mipmap.my_contas, R.mipmap.news_center, R.mipmap.my_report};
    RecycleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ZXingLibrary.initDisplayOpinion(this);

        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new FragmentAdapter(), R.id.container);
        mNavigator.setDefaultPosition(DEFAULT_POSITION);
        mNavigator.onCreate(savedInstanceState);

        bottomNavigatorView = (BottomNavigatorView) findViewById(R.id.bottomNavigatorView);
        if (bottomNavigatorView != null) {
            bottomNavigatorView.setOnBottomNavigatorViewItemClickListener(this);
        }
        setCurrentTab(mNavigator.getCurrentPosition());
        BroadcastManager.register(this, mLoginStatusChangeReceiver, Action.LOGIN, Action.LOGOUT);

        initTitle();
        initData();
        initView();
    }
    private void setSearchIcon() {
        Drawable drawable = getResources().getDrawable(R.drawable.img_search);
        drawable.setBounds(10, 10, 1 * (drawable.getMinimumWidth()) / 2, 1 * (drawable.getMinimumHeight()) / 2);
        searchInbox.setCompoundDrawables(drawable, null, null, null);
    }

    private void initTitle() {

        img_MessageCenter = (ImageView) findViewById(R.id.img_message_center);
        img_Scan = (ImageView) findViewById(R.id.img_scan);
        searchInbox = (EditText) findViewById(R.id.search_box);
        setSearchIcon();

        img_Scan.setOnClickListener(new ButtonOnclickListener(img_Scan.getId()));
    }

    private void initData() {
        for (int i = 0; i < bean.length; i++) {
        data.add(""+i);


        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayout.HORIZONTAL));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayout.VERTICAL));
        recycleAdapter = new RecycleAdapter(bean,image);
//       recycleAdapter.setOnItemClickListener();
        recyclerView.setAdapter(recycleAdapter);
recycleAdapter.seOnItemClickListener(new RecycleAdapter.onRecycleViewItemClickListener() {
    @Override
    public void onItemClick(View view, String data) {

        int position=recyclerView.getChildAdapterPosition(view);

        Toast.makeText(MainActivity.this,position+"122121",Toast.LENGTH_SHORT).show();
    }
});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mLoginMenu = menu.findItem(R.id.action_login);
        mLogoutMenu = menu.findItem(R.id.action_logout);
        toggleMenu(SharedPrefUtils.isLogin(this));
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigator.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_exception:
//                startActivity(new Intent(this, ExceptionActivity.class));
                return true;
            case R.id.action_login:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.action_logout:
                logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        BroadcastManager.unregister(this, mLoginStatusChangeReceiver);
        super.onDestroy();
    }

    @Override
    public void onBottomNavigatorViewItemClick(int position, View view) {
        setCurrentTab(position);
    }

    private void logout() {
        SharedPrefUtils.logout(this);
        BroadcastManager.sendLogoutBroadcast(this, 1);
    }

    private void onUserLogin(int position) {
        if (position == -1) {
            resetAllTabsAndShow(mNavigator.getCurrentPosition());
        } else {
            resetAllTabsAndShow(position);
        }
        toggleMenu(true);
    }

    private void onUserLogout(int position) {
        if (position == -1) {
            resetAllTabsAndShow(mNavigator.getCurrentPosition());
        } else {
            resetAllTabsAndShow(position);
        }
        toggleMenu(false);
    }

    private void setCurrentTab(int position) {
        mNavigator.showFragment(position);
        bottomNavigatorView.select(position);
    }

    private void resetAllTabsAndShow(int position) {
        mNavigator.resetFragments(position, true);
        bottomNavigatorView.select(position);
    }

    private void toggleMenu(boolean login) {
        if (login) {
            mLoginMenu.setVisible(false);
            mLogoutMenu.setVisible(true);
        } else {
            mLoginMenu.setVisible(true);
            mLogoutMenu.setVisible(false);
        }
    }

    private BroadcastReceiver mLoginStatusChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                int position = intent.getIntExtra("EXTRA_POSITION", -1);
                if (action.equals(Action.LOGIN)) {
                    onUserLogin(position);
                } else if (action.equals(Action.LOGOUT)) {
                    onUserLogout(position);
                }
            }
        }
    };

//    @Override
//    public void onItemClick(int position) {
//
//    }

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

            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Toast.makeText(this, "" + result, Toast.LENGTH_LONG).show();

                } else if (bundle.getInt(CodeUtils.RESULT_STRING) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(this, "从设置页面返回...", Toast.LENGTH_SHORT)
                    .show();

        }
//        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show();
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
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            onClick(viewId);
        } else {
            EasyPermissions.requestPermissions(this, "需要请求canera权限", REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    private void onClick(int buttonId) {
        switch (buttonId) {
            case R.id.img_scan:
                Intent intent = new Intent(getApplication(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODD);
                break;
            default:
                break;
        }


    }
}
