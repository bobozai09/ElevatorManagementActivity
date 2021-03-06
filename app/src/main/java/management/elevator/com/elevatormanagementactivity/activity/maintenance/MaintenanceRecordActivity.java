package management.elevator.com.elevatormanagementactivity.activity.maintenance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mingle.widget.ShapeLoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.DOMImplementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import management.elevator.com.elevatormanagementactivity.BaseActivity;
import management.elevator.com.elevatormanagementactivity.R;
import management.elevator.com.elevatormanagementactivity.adapter.MaintanceRecordAdapter;
import management.elevator.com.elevatormanagementactivity.bean.MaintenanceRecordBean;
import management.elevator.com.elevatormanagementactivity.bean.Model;
import management.elevator.com.elevatormanagementactivity.utils.GetSession;
import management.elevator.com.elevatormanagementactivity.utils.ImageUtils;
import management.elevator.com.elevatormanagementactivity.utils.UtilsForImage;
import management.elevator.com.elevatormanagementactivity.widget.Constant;
import management.elevator.com.elevatormanagementactivity.widget.SpaceItemDecoration;
import wang.raye.preioc.PreIOC;
import wang.raye.preioc.annotation.BindById;

import static management.elevator.com.elevatormanagementactivity.R.id.default_activity_button;
import static management.elevator.com.elevatormanagementactivity.R.id.img_takephoto;
import static management.elevator.com.elevatormanagementactivity.R.id.none;

/**
 * Created by Administrator on 2017/1/4 0004.
 * 电梯半月维保 月维保季度维保和年维保
 */

public class MaintenanceRecordActivity extends BaseActivity implements View.OnClickListener {
    private String imageFilePath; //拍照和选择照片后图片路径
    private File cropFile; //裁剪后的图片文件
    private Uri pickPhotoImageUri; //API22以下相册选择图片uri
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 10002;
    private static final int PICK_ACTIVITY_REQUEST_CODE = 10003;
    private static final int CROP_ACTIVITY_REQUEST_CODE = 10008;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 10010;
    @BindById(R.id.img_back)
    ImageView imgBack;
    @BindById(R.id.tex_title)
    TextView texTitle;
    @BindById(R.id.img_other)
    TextView imgOther;
    Intent intent;
    String token;
    MaintenanceRecordBean bean;
    MaintenanceRecordBean.Data data;
    private static final int INITDATA = 101;
    @BindById(R.id.edittext_messge)
    EditText edittextMessge;
    @BindById(R.id.img_takephoto2)
    ImageView imgTakephoto2;
    @BindById(R.id.btn_upload)
    Button btnUpload;
    private MaintanceRecordAdapter adapter;
    @BindById(R.id.rec_main_record)
    RecyclerView recMainRecord;
    private List<String> saveData;
    private String mfilename;
    private Uri mImageUri; //图片路径Uri
    private String mImagePath;
    private Bitmap mBitmap;
    private static String encodeString = null;
    private int length = 0;
    private String mc_start;
    private String mc_end;
    private int tmpl;
    private String message;
    private static final int UPLOADASTATUS = 104;
    private File mOutputFile;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private ShapeLoadingDialog shapeLoadingDialog;
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_record);
        shapeLoadingDialog = new ShapeLoadingDialog(this);
        PreIOC.binder(this);

        texTitle.setText("维保详情");
        intent = getIntent();
        id=intent.getStringExtra("OLD"+"");
        Log.d("", "onCreate: "+id);
        Date now = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
        mc_start = dateformat.format(now);
        saveData = new ArrayList<>();
        btnUpload.setOnClickListener(this);
        imgTakephoto2.setOnClickListener(this);
        recMainRecord.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        int spaceInPixes = getResources().getDimensionPixelSize(R.dimen.activity_8dp);
        recMainRecord.addItemDecoration(new SpaceItemDecoration(spaceInPixes));
        imgBack.setOnClickListener(this);
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        String str = sp.getString("path", null);
        if (str != null) {
            mOutputFile = new File(str);
        }
        initData();

    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause: ");
        edit = sp.edit();
        edit.putString("path", mOutputFile.getAbsolutePath());
        edit.apply();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INITDATA:
                    final MaintenanceRecordBean obj = (MaintenanceRecordBean) msg.obj;
                    adapter = new MaintanceRecordAdapter(getApplicationContext(), obj) {
                        @Override
                        public void onBindViewHolder(final MaintanceRecordViewHolder holder, final int position) {
                            super.onBindViewHolder(holder, position);
                            holder.rgChooseStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                int chooseid = obj.getDatas().get(position).getID();

                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    switch (checkedId) {
                                        case R.id.rb_status_one:
                                            holder.rbStatusTwo.setChecked(false);
                                            holder.rbStatusThree.setChecked(false);
                                            holder.rbStatusFour.setChecked(false);
                                            saveDatas(0, chooseid);
                                            break;
                                        case R.id.rb_status_two:
                                            holder.rbStatusOne.setChecked(false);
                                            holder.rbStatusThree.setChecked(false);
                                            holder.rbStatusFour.setChecked(false);
                                            saveDatas(1, chooseid);
//                                            Toast.makeText(getApplicationContext(), "male", Toast.LENGTH_SHORT).show();
                                            break;
                                        case R.id.rb_status_three:
                                            holder.rbStatusTwo.setChecked(false);
                                            holder.rbStatusOne.setChecked(false);
                                            holder.rbStatusFour.setChecked(false);
                                            saveDatas(2, chooseid);
                                            break;
                                        case R.id.rb_status_four:
                                            holder.rbStatusTwo.setChecked(false);
                                            holder.rbStatusThree.setChecked(false);
                                            holder.rbStatusOne.setChecked(false);
                                            saveDatas(3, chooseid);
                                            break;
                                    }
                                }
                            });
                        }
                    };
                    recMainRecord.setAdapter(adapter);
                    break;
                case UPLOADASTATUS:
                    String result = (String) msg.obj;
                    if (result.equals("succ")) {
                        Toast.makeText(getApplicationContext(), "提交成功！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "提交失败！", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tmpl = intent.getIntExtra("TMPL", 0);
                token = Constant.TOKEN;
                String domain = Constant.URL_TRANSCTION;
                String params = Constant.OPER + "=" +
                        Constant.DEV_MC_TMPL + "&" + Constant.LOGIN_TOKEN + "=" + token + "&" + Constant.TMPL + "=" + tmpl;
                String json = GetSession.post(domain, params);
                if (!json.equals("+ER+")) {
                    ArrayList<MaintenanceRecordBean.Data> mlist = new ArrayList<MaintenanceRecordBean.Data>();
                    try {
                        bean = new MaintenanceRecordBean();
                        JSONArray jsonarray = new JSONArray(json);
                        int len = jsonarray.length();
                        Constant.LENGTH = jsonarray.length();
                        for (int i = 0; i < len; i++) {
                            JSONObject js = jsonarray.getJSONObject(i);
                            data = new MaintenanceRecordBean.Data();
                            data.setID(js.getInt("ID"));
                            data.setIDX(js.getInt("IDX"));
                            data.setNAME(js.getString("NAME"));
                            data.setNEED(js.getString("NEED"));
                            mlist.add(data);
                        }
                        bean.setDatas(mlist);
                        Message message = new Message();
                        message.what = INITDATA;
                        message.obj = bean;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }
        }).start();
    }

    private void saveDatas(int nuber, int choosid) {
        for (int i = 0; i < saveData.size(); i++) {
            String item = saveData.get(i);
            String key = item.substring(0, item.indexOf("="));
            Log.d("saveData", "saveData:item==> " + key);
            if (Integer.parseInt(key) == choosid) {
                Log.d("saveData", "saveData:remove===> " + item);
                saveData.remove(item);
            }
        }
        String newItem = choosid + "=" + nuber;
        saveData.add(newItem);
    }

    private void updateMessage() {
        new MyAsyncTask().execute();
}

   class  MyAsyncTask extends AsyncTask<Void,Void,Boolean>{

       @Override
       protected Boolean doInBackground(Void... params) {
           Log.d("", "doInBackground: ");
           try {
               Date now = new Date();
               SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
               mc_end = dateformat.format(now);
               final String jsondata = stringTOJson();
               Log.d("", "doInBackground: jsondata==>"+jsondata);
               Log.d("jsondata", "run: "+jsondata);
               String domain = Constant.URL_TRANSCTION;
               String param = Constant.OPER + "=" +
                       Constant.DEV_MC_SUBMIT + "&" + Constant.LOGIN_TOKEN +
                       "=" + token + "&" + "data=" + jsondata;
               String json = GetSession.post(domain, param);
               Log.d("onclick", "run: " + jsondata);
               Log.d("onclick", "run: " + domain);
               Log.d("onclick", "run: " + params);
               Log.d("onclick", "run: " + json);
               if (!json.equals("+ER+")){
                   try {
                       JSONObject js = new JSONObject(json);
                       String result = js.getString("result");
                       Message message = new Message();
                       message.obj = result;
                       message.what = UPLOADASTATUS;
                       handler.sendMessage(message);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

               }
               return true;
           }catch (Exception e){
               e.printStackTrace();
           }
           return false;
       }

       @Override
       protected void onPostExecute(Boolean aBoolean) {
           super.onPostExecute(aBoolean);
           if (aBoolean){
               Log.d("", "onPostExecute: success");
           }else{
               Log.d("", "onPostExecute: failed");
           }
       }
   }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload:
                Log.d("onclick", "onClick: ");
                if (canUpload()) {
                    updateMessage();
                }
                break;
            case R.id.img_takephoto2:
                takepic();
                break;
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }

    }

    private boolean canUpload() {
        message = edittextMessge.getText().toString().trim();
        Log.i("", "" + (Constant.LENGTH) + "");
        if (saveData.size() == Constant.LENGTH) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "你还有选项未选取完成", Toast.LENGTH_LONG).show();
        }
        if (message.length() == 0) {
            Toast.makeText(getApplicationContext(), "随便说点什么吧", Toast.LENGTH_LONG).show();
            return false;
        }
        if (encodeString.length() != 0) {
            Toast.makeText(this, "请先拍照上传", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public void takepic() {
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date(System.currentTimeMillis());
//        mfilename = format.format(date);
//        Log.i("data", "" + mfilename);
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        File outputImage = new File(path, mfilename + ".jpg");
//        try {
//            if (outputImage.exists()) {
//                outputImage.delete();
//            }
//            outputImage.createNewFile();
//            mImagePath = path + "/" + mfilename + ".jpg";
//            Log.d("data", "mImagePath=" + mImagePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mImageUri = Uri.fromFile(outputImage);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
//        startActivityForResult(intent, 0);
//        String sdPath = Environment.getExternalStorageDirectory()
//                .getAbsolutePath();
//        mOutputFile = new File(sdPath, System.currentTimeMillis() + ".jpg");
//        Intent newIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Uri uri;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uri = FileProvider.getUriForFile(this, "management.elevator.com.elevatormanagementactivity", mOutputFile);
//            newIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        } else {
//            uri = Uri.fromFile(mOutputFile);
//        }
//        newIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        startActivityForResult(newIntent, 0);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                crop(false);
//            try {
//                Bitmap mBitmap = BitmapFactory.decodeFile(mOutputFile.getAbsolutePath());
//                imgTakephoto2.setImageBitmap(mBitmap);
//                encodeString = ImageUtils.bitmapToString(mOutputFile.getAbsolutePath());
//                Log.d("onclick",""+encodeString);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            if (requestCode==CROP_ACTIVITY_REQUEST_CODE){

                if (data != null) {
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapFactory.decodeFile(cropFile.getPath());
                        imgTakephoto2.setImageBitmap(bitmap);
                        encodeString = ImageUtils.bitmapToString(bitmap.toString());
                        Log.d("test", "onActivityResult: "+encodeString);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    private String stringTOJson() {
        String jsonresult = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < saveData.size(); i++) {
            if (i == saveData.size() - 1) {
                sb.append(saveData.get(i));
            } else {
                sb.append(saveData.get(i)).append(",");
            }
        }
        JSONObject object = new JSONObject();
        String p = "data:image/jpg;base64," + encodeString;
        try {
            p = URLEncoder.encode(p, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
//            JSONArray jsonarray=new JSONArray();
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("SPE", 0);
            jsonObj.put("MC_STA", mc_start);
            jsonObj.put("MC_END", mc_end);
            jsonObj.put("OID", id);
            jsonObj.put("TMPL", tmpl);
            jsonObj.put("MC_IMG", p);
            jsonObj.put("MC_DATA", sb.toString());
            jsonObj.put("MC_MARK", message);
            jsonresult = jsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonresult;

    }
}
