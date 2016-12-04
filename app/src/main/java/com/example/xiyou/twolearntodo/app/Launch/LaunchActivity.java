package com.example.xiyou.twolearntodo.app.Launch;

        import android.Manifest;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
        import android.view.View;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.xiyou.twolearntodo.R;
        import com.example.xiyou.twolearntodo.app.main.MainActivity;
        import com.squareup.okhttp.OkHttpClient;
        import com.squareup.okhttp.Request;
        import com.squareup.okhttp.Response;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;

public class LaunchActivity extends Activity {

    private final String updateUrl = "http://119.29.181.219:8080/checkUpdate/getVersion";

    private final int UPDATE = 0x000;

    private final int NEWEST = 0x001;

    private final int DEFAULT = 0x002;

    private final int VERSION_ERROR = 0x003;

    private final int PROGRESS = 0x004;

    private final int DONE = 0x006;

    private final int ERROR = 0x007;

    private final int START = 0x008;

    private TextView progress;
    private ProgressBar progressBar;
    private String downloadUrl = "";
    private int currentProgress;
    private String filePath = "";
    private String fileLength = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        int versionCode = getVersionCode();
        if (versionCode != -1) {
            CheckUpdate checkUpdate = new CheckUpdate(versionCode + "");
            checkUpdate.start();
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler.sendEmptyMessageAtTime(VERSION_ERROR, 0);
                }
            }, 2000);
        }
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent = new Intent();
            switch (msg.what){
                case UPDATE :
                    /**
                     * 有新版本，提醒用户更新
                     */
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LaunchActivity.this);
                    dialog.setTitle("提示").setMessage("发现新版本, 是否立即更新?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /**
                             * 申请存储权限
                             */
                            boolean allow = accessPermission();
                            if(allow){
                                /**
                                 * 开始下载
                                 */
                                startDownload();
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setCancelable(false).create().show();
                    break;
                case PROGRESS :
                    /**
                     * 更新进度
                     */
                    int percent = currentProgress * 100 / Integer.parseInt(fileLength);
                    progress.setText("正在下载... " + percent + "%");
                    progressBar.setProgress(currentProgress);
                    break;
                case DONE :
                    /**
                     * 下载完成，安装新版本
                     */
                    Toast.makeText(LaunchActivity.this, "下载完成!", Toast.LENGTH_SHORT).show();
                    intent.setAction(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse("file://" + filePath);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    startActivity(intent);
                    finish();
                    break;
                case ERROR :
                    /**
                     * 下载失败
                     */
                    Toast.makeText(LaunchActivity.this, "网络连接出现问题, 下载失败", Toast.LENGTH_SHORT).show();
                case NEWEST :
                    /**
                     * 已是最新版本
                     */
                case DEFAULT :
                    /**
                     * 参数错误||服务器文件缺失
                     */
                case VERSION_ERROR :
                    /**
                     * 获取版本错误
                     */
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessageAtTime(START, 0);
                        }
                    }, 1000);
                    break;
                case START :
                    intent.setClass(LaunchActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    public int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("get packageinfo error", e.toString());
            return -1;
        }
    }

    public class CheckUpdate extends Thread {

        private String versionCode;

        public CheckUpdate(String versionCode) {
            this.versionCode = versionCode;
        }

        @Override
        public void run() {
            getVersionInfo(updateUrl, versionCode);
        }
    }

    public class DownloadApk extends Thread {

        private String downloadUrl;

        public DownloadApk(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        @Override
        public void run() {
            downloadApk(downloadUrl);
        }
    }

    public void getVersionInfo(String url, String versionCode) {
        OkHttpClient okHttpClient = new OkHttpClient();
        /**
         * 请求体
         */
        Request request = new Request.Builder().url(url + "?versionCode=" + versionCode + "&appName=西邮两学一做").build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() == 200) {
                String versionInfo = response.body().string();
                JSONObject object = new JSONObject(versionInfo);
                Message msg = handler.obtainMessage();
                if (object.get("result").equals("success")) {
                    if (object.get("update").equals("true")) {
                        /**
                         * 发现新版本
                         */
                        downloadUrl = object.getString("newVersionUrl");
                        fileLength = object.getString("fileLength");
                        msg.what = UPDATE;
                        handler.sendMessage(msg);
                    } else {
                        /**
                         * 已是最新版本
                         */
                        msg.what = NEWEST;
                        handler.sendMessage(msg);
                    }
                } else {
                    /**
                     * 参数错误||服务器文件缺失
                     */
                    msg.what = DEFAULT;
                    handler.sendMessage(msg);
                }
            }
        } catch (IOException e) {
            Log.e("get versionInfo error", e.toString());
        } catch (JSONException e) {
            Log.e("json data error", e.toString());
        }
    }

    public void downloadApk(String url) {
        String rootPath = Environment.getExternalStorageDirectory() + "/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            Log.e("state--->", String.valueOf(response.code()));
            if (response.code() == 200) {
                File file = new File(rootPath + "/TwoLearnToDo/apk");
                if (!file.exists()) {
                    file.mkdirs();
                }
                String fileName = url.substring(url.lastIndexOf("/") + 1);
                File file1 = new File(file.getPath() + "/" + fileName);
                filePath = file1.getAbsolutePath();
                if(file1.exists()){
                    file1.delete();
                }
                InputStream is = response.body().byteStream();
                FileOutputStream os = new FileOutputStream(filePath);
                byte[] b = new byte[1024];
                int c;
                while ((c = is.read(b)) != -1) {
                    os.write(b, 0, c);
                    Message msg = handler.obtainMessage();
                    msg.what = PROGRESS;
                    currentProgress += c;
                    msg.arg1 = currentProgress;
                    handler.sendMessage(msg);
                    Log.e("readProgress", c + "");
                    Log.e("currentProgress", currentProgress + "");
                }
                is.close();
                os.close();
                Message msg = handler.obtainMessage();
                msg.what = DONE;
                handler.sendMessage(msg);
            }
        } catch (IOException e) {
            Log.e("get apk file error", e.toString());
            Message msg = handler.obtainMessage();
            msg.what = ERROR;
            handler.sendMessage(msg);
        }
    }

    public boolean accessPermission(){
        if (ContextCompat.checkSelfPermission(LaunchActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LaunchActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1 :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    /**
                     * 授予权限，
                     */
                    startDownload();
                } else {
                    /**
                     * 拒绝授予
                     */
                    Toast.makeText(LaunchActivity.this, "更新失败! 请在系统设置中对本应用开启存储权限", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LaunchActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    public void startDownload(){
        /**
         * 显示下载进度
         */
        View progressWindow = getLayoutInflater().inflate(R.layout.update, null);
        progress = (TextView) progressWindow.findViewById(R.id.progress);
        progressBar = (ProgressBar) progressWindow.findViewById(R.id.progressbar);
        progressBar.setProgress(0);
        progressBar.setMax(Integer.parseInt(fileLength));
        AlertDialog.Builder progressView = new AlertDialog.Builder(LaunchActivity.this);
        progressView.setView(progressWindow);
        progressView.setCancelable(false).create().show();
        /**
         * 开始下载
         */
        DownloadApk downloadApk = new DownloadApk(downloadUrl);
        downloadApk.start();
    }
}
