package management.elevator.com.elevatormanagementactivity.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.activity.TickHistViewActivity;
import management.elevator.com.elevatormanagementactivity.activity.TransctionActivity;
import management.elevator.com.elevatormanagementactivity.adapter.RecycleAdapter;
import management.elevator.com.elevatormanagementactivity.widget.RecycleViewDivider;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import wang.raye.preioc.annotation.BindById;

/**
 *
 */
public class FragmentCommon extends Fragment implements EasyPermissions.PermissionCallbacks {

    private RecycleAdapter recycleAdapter;
    private RecyclerView recyclerView;
    private List<String> data = new ArrayList<String>();
    public static final int REQUEST_CODD = 111;
    public static final int REQUEST_CAMERA_PERM = 101;
    ImageView img_Scan, img_MessageCenter;
    EditText searchInbox;

    String[] bean = {"我的事务", "故障申告", "地理位置", "故障统计", "通知中心", "知识中心", "联系人", "新闻中心", "报表统计"};
    int[] image = {R.mipmap.my_work, R.mipmap.fault_declaration, R.mipmap.my_location,
            R.mipmap.fault_total, R.mipmap.message_center, R.mipmap.knowlege_center,
            R.mipmap.my_contas, R.mipmap.news_center, R.mipmap.my_report};
    RecycleAdapter adapter;

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
        ZXingLibrary.initDisplayOpinion(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        img_MessageCenter = (ImageView) view.findViewById(R.id.img_message_center);
        img_Scan = (ImageView) view.findViewById(R.id.img_scan);
        searchInbox = (EditText) view.findViewById(R.id.search_box);


        initTitle();
        initView();
        initData();
        return view;
    }

    private void initView() {
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
                        toInient(0);
                        break;
                    default:
                        break;


                }

            }
        });

    }

    private void toInient(int number) {
        switch (number) {
            case 0:
                Intent intent = new Intent();
                intent.setClass(getContext(), TransctionActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initTitle() {
        setSearchIcon();
        img_Scan.setOnClickListener(new FragmentCommon.ButtonOnclickListener(img_Scan.getId()));
    }

    private void initData() {
        for (int i = 0; i < bean.length; i++) {
            data.add("" + i);


        }
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

                } else if (bundle.getInt(CodeUtils.RESULT_STRING) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(getActivity(), "从设置页面返回...", Toast.LENGTH_SHORT)
                    .show();

        }

    }
}
