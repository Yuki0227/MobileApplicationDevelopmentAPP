package com.app.task;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.MyApplication;
import com.app.R;
import com.app.task.entity.TaskAssign;
import com.app.util.CommonUtils;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.smailnet.emailkit.EmailKit;
import com.smailnet.microkv.MicroKV;

import java.util.List;

public class ScanCodeActivity extends AppCompatActivity {

    public static final int DEFAULT_VIEW = 0x22;
    private static final int REQUEST_CODE_SCAN = 0X01;
    private Integer taskId;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        taskId = bundle.getInt("taskId");
        position = bundle.getInt("position");

        Window window = getWindow();
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setFlags(flag, flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    DEFAULT_VIEW);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || grantResults == null || grantResults.length < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (requestCode == DEFAULT_VIEW) {
            //start ScankitActivity for scanning barcode
            ScanUtil.startScan(ScanCodeActivity.this, REQUEST_CODE_SCAN, new HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //receive result after your activity finished scanning
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        // Obtain the return value of HmsScan from the value returned by the onActivityResult method by using ScanUtil.RESULT as the key value.
        if (requestCode == REQUEST_CODE_SCAN) {
            Object obj = data.getParcelableExtra(ScanUtil.RESULT);
            if (obj instanceof HmsScan) {
                if (!TextUtils.isEmpty(((HmsScan) obj).getOriginalValue())) {
                    //Toast.makeText(this, ((HmsScan) obj).getOriginalValue(), Toast.LENGTH_SHORT).show();
                    Integer scanContent =  new Integer (((HmsScan) obj).getOriginalValue());
                    if(taskId.equals(scanContent)){
                        CommonUtils.showShortMsg(this, "扫码成功,任务完成");
                        List<TaskAssign> taskAssignList = TaskFactory.getTask(MyApplication.getUser().getId());
                        TaskAssign taskAssign = taskAssignList.get(position);
                        taskAssign.setStatus(1);
                        //System.out.println("scan-->"+taskAssign);
                        TaskFactory.updateTask(taskAssign);
                        finish();
                    } else{
                        //CommonUtils.showShortMsg(this, "扫码失败,二维码与任务不匹配");
                        //CommonUtils.showDlgMsg(this,"扫码失败,二维码与任务不匹配");
                        //finish();
                        new AlertDialog.Builder(this)
                                .setTitle("")
                                .setMessage("二维码与任务不匹配")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).create().show();
                    }
                }
                return;
            }
        }
    }
}