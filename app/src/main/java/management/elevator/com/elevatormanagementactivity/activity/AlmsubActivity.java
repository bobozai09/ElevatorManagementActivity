package management.elevator.com.elevatormanagementactivity.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButton;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.utils.ImageUtils;
import management.elevator.com.elevatormanagementactivity.utils.Utils;
import management.elevator.com.elevatormanagementactivity.utils.UtilsForImage;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

/**
 * Created by Administrator on 2017/1/12 0012.
 * <p>
 * 提交申告
 */

public class AlmsubActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    public static final int REQUEST_CAMERA_PERM = 101;
    public static final int REQUEST_CODD = 111;
    private static final int REQUESRESULT = 112;
    public static final int TAKEPHOTRESULT = 0;
    public static final int SCANRESULT = 113;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10002;
    private static final int PICK_ACTIVITY_REQUEST_CODE = 10003;
    private static final int CROP_ACTIVITY_REQUEST_CODE = 10008;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 10010;
    private File cropFile; //裁剪后的图片文件
    Message message;
    private Uri pickPhotoImageUri; //API22以下相册选择图片uri
    String token;
    List<String> list;
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.img_other)
    TextView imgOther;
    @BindById(R.id.text_sn_number)
    TextView textSnNumber;
    @BindById(R.id.line_device_sn)
    LinearLayout lineDeviceSn;
    @BindById(R.id.line_trouble_more)
    LinearLayout lineTroubleMore;
    @BindById(R.id.edit_troule_address)
    TextView editTrouleAddress;
    @BindById(R.id.edit_trouble_context)
    EditText editTroubleContext;
    @BindById(R.id.img_phototake)
    ImageView imgPhototake;
    @BindById(R.id.btn_add_almsub)
    Button btnAddAlmsub;
    @BindById(R.id.spinner)
    MaterialSpinner spinner;
    String context;
    private static String encodeString = null;
    private String mfilename;
    private Uri mImageUri; //图片路径Uri
    private String mImagePath;
    //    private Bitmap mBitmap;
    String errormessge;
    String name;
    Uri uri;
    private File mOutputFile;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private static final String[] ANDROID_VERSIONS = {
            "测试", "照明灯不亮", "轿厢地板损坏", "门不能正常关闭"
    };
    private String imageFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almsub);
        PreIOC.binder(this);
        spinner.setItems(ANDROID_VERSIONS);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        String str = sp.getString("path", null);
        if (str != null) {
            mOutputFile = new File(str);
        }
        initView();
        permission();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case REQUESRESULT:
                    String message = (String) msg.obj;
                    String[] array = message.split(",");
                    String result = array[0];
                    String reason = array[1];
                    if (result.equals("fail")) {
                        Toast.makeText(getApplicationContext(), "" + reason, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "" + reason, Toast.LENGTH_LONG).show();
                    }
                    break;
                case SCANRESULT:
                    String mess = (String) msg.obj;
                    editTrouleAddress.setText(mess);
                    break;
                default:
                    break;

            }
        }
    };

    private void initView() {
        lineDeviceSn.setOnClickListener(new ButtonOnclickListener(lineDeviceSn.getId()));
        imgPhototake.setOnClickListener(this);
        btnAddAlmsub.setOnClickListener(this);
        texTitle.setText("故障申告");
        imgBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_almsub:
                getLoad();
                break;
            case R.id.img_phototake:
                takepic();
                break;
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前应用需要申请CAMERA权限，需要打开设置页面么？")
                    .setTitle("权限申请").setPositiveButton("").setNegativeButton("", null).setRequestCode(REQUEST_CAMERA_PERM)
                    .build().show();

        }

    }

    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    private void cameraTask(int viewId) {
        if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.CAMERA)) {
            onClick(viewId);
        } else {
            EasyPermissions.requestPermissions(this, "需要请求canera权限", REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    private void onClick(int buttonId) {
        switch (buttonId) {
            case R.id.line_device_sn:
                Intent intent = new Intent(getApplication(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODD);
                break;
            default:
                break;
        }

    }

    class ButtonOnclickListener implements View.OnClickListener {
        private int buttonId;

        public ButtonOnclickListener(int buttonId) {
            this.buttonId = buttonId;

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.line_device_sn) {
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
                    Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_LONG).show();
                    textSnNumber.setText(result);
                    getlocation(result);

                } else if (bundle.getInt(CodeUtils.RESULT_STRING) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getApplicationContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(getApplicationContext(), "从设置页面返回...", Toast.LENGTH_SHORT)
                    .show();
        } else if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            crop(false);
        } else if (requestCode == CROP_ACTIVITY_REQUEST_CODE) {
            if (data != null) {
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeFile(cropFile.getPath());
                    imgPhototake.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void crop(boolean isPick) {
        cropFile = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!cropFile.getParentFile().exists()) cropFile.getParentFile().mkdirs();
        Uri outputUri, imageUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            outputUri = UtilsForImage.file2Uri(this, cropFile);
            imageUri = UtilsForImage.file2Uri(this, new File(imageFilePath));
        } else {
            outputUri = Uri.fromFile(cropFile);
            imageUri = isPick ? pickPhotoImageUri : Uri.fromFile(new File(imageFilePath));
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        //授予"相机"保存文件的权限 针对API24+
        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, outputUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(intent, CROP_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause: ");
        edit = sp.edit();
        edit.putString("path", mOutputFile.getAbsolutePath());
        edit.apply();
    }

    private void getlocation(final String scansn) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String domain = Constant.BASE_URL + Constant.ALMSUB;
                String params = Constant.OPER + "=" + Constant.SCAN_LIFT + "&" + Constant.LOGIN_TOKEN + "=" + Constant.TOKEN + "&" + Constant.WAY + "=" + "0" + "&" + Constant.SN + "=" + scansn;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {

                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String result = jsonObject.getString("result");
                        message = new Message();
                        message.obj = result;
                        message.what = SCANRESULT;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    public void takepic() {
//
//        mImageUri = Uri.fromFile(outputImage);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
//        String sdPath = Environment.getExternalStorageDirectory()
//                .getAbsolutePath();
//        mOutputFile = new File(sdPath, System.currentTimeMillis() + ".jpg");
//        Intent newIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uri = FileProvider.getUriForFile(this, "management.elevator.com.elevatormanagementactivity", mOutputFile);
//            newIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        } else {
//            uri = Uri.fromFile(mOutputFile);
//        }
//        newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(newIntent, TAKEPHOTRESULT);

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            File imageFile = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!imageFile.getParentFile().exists()) imageFile.getParentFile().mkdirs();
            imageFilePath = imageFile.getPath();
            //兼容性判断
            Uri imageUri;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                imageUri = UtilsForImage.file2Uri(this, imageFile);
            } else {
                imageUri = Uri.fromFile(imageFile);
            }
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI

            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, imageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    private void getLoad() {
        context = textSnNumber.getText().toString().trim();
        errormessge = editTroubleContext.getText().toString().trim();
        if (errormessge.length() < 0) {
            Toast.makeText(getApplicationContext(), "故障原因不能为空", Toast.LENGTH_LONG).show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                name = spinner.getText().toString().trim();
                errormessge = editTroubleContext.getText().toString().trim();
                String domain = Constant.BASE_URL + Constant.ALMSUB;
                String params = Constant.OPER + "=" + Constant.SUB_ALARM + "&" + Constant.LOGIN_TOKEN + "=" + Constant.TOKEN +
                        "&" + Constant.SN + "=" + context + "&" + "name=" + name + "&" + "context=" + errormessge + "&" + "image=" + encodeString;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String result = jsonObject.getString("result");
                        String reason = jsonObject.getString("reason");
                        message = new Message();
                        message.what = REQUESRESULT;
                        message.obj = result + "," + reason;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();
    }

    /**
     * 申请权限
     */
    private void permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //拒绝权限以后
                showMessageOKCancel();
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }


    private void showMessageOKCancel() {
        new AlertDialog.Builder(AlmsubActivity.this)
                .setMessage("必须授予储存空间的权限！！！！！！！！！！！！！！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //同意授权
            } else {
                //拒绝授权后重新申请
                permission();
            }
        }
    }

}
