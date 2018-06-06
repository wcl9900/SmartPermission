package com.wcl.smartpermission.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wcl.smartpermission.SmartPermission;
import com.wcl.smartpermission.SmartPermissionCallBack;

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
                    .callBack(new SmartPermissionCallBack() {
                        @Override
                        public void grantEach(String permission, boolean grant) {
                            Toast.makeText(MainActivity.this, permission + " "+grant, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void grantAll(boolean grant) {
                            Toast.makeText(MainActivity.this, " 是否所有权限授权成功>> " + grant, Toast.LENGTH_SHORT).show();
                        }
                    })
//                    .callBack(new SmartPermissionCallBackAdapter() {
//                        @Override
//                        public void grantEach(String permission, boolean grant) {
//                            Toast.makeText(MainActivity.this, permission + " "+grant, Toast.LENGTH_SHORT).show();
//                        }
//                    })
                    .request();
        }
    }
}
