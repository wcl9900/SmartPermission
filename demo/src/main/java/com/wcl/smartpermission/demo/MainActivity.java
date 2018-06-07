package com.wcl.smartpermission.demo;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wcl.smartpermission.SmartPermission;
import com.wcl.smartpermission.callback.DeniedCallBack;
import com.wcl.smartpermission.callback.GrantCallBack;
import com.wcl.smartpermission.setting.callback.SettingCallBack;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            SmartPermission.build(this)
                    .addPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS
                    )
                    .addPermission(Manifest.permission.CAMERA)
//                    .callBack(new SmartPermissionCallBack() {
//                        @Override
//                        public void grantEach(String permission, boolean grant) {
//                            toast(permission + " "+grant);
//                        }
//
//                        @Override
//                        public void grantAll(boolean grant) {
//                            toast(" 是否所有权限授权成功>> " + grant);
//                        }
//                    })
                    .callBackGrant(new GrantCallBack() {
                        @Override
                        public void grant(List<String> permissions) {
                            toast("所有权限授权成功！");
                        }
                    })
                    .callBackDenied(new DeniedCallBack() {
                        @Override
                        public void denied(List<String> deniedPermissions, List<String> permissionNames) {
                            StringBuffer names = new StringBuffer();
                            for (int index = 0; index < permissionNames.size(); index++){
                                names.append(index+1+"、").append(permissionNames.get(index)).append("\n");
                            }
                            names.append("\n")
                                    .append("否则无法运行，是否打开设置？");
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("程序需要以下权限")
                                    .setMessage(names.toString())
                                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SmartPermission.buildSetting(MainActivity.this)
                                                    .callBackSetting(new SettingCallBack() {
                                                        @Override
                                                        public void setting() {
                                                            toast("设定回调！！！");
                                                            MainActivity.this.onClick(findViewById(R.id.button));
                                                        }
                                                    }).request();
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        }
                    })
                    .request();
        }
    }

    private void toast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
