package com.wcl.smartpermission.setting;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.wcl.smartpermission.setting.callback.SettingCallBack;

/**
 * Created by wangchunlong on 2018/6/7.
 */

public class SettingFragment extends Fragment {
    public static final String TAG = "SmartSettingFragment";
    /**
     * 申请码
     */
    public static int REQUEST_SETTING_CODE = 222;

    SettingCallBack settingCallBack;

    public SettingFragment() {
    }

    public void setSettingCallBack(SettingCallBack settingCallBack) {
        this.settingCallBack = settingCallBack;
    }

    /**
     * 打开权限设定
     */
    public void openSetting(){
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, REQUEST_SETTING_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SETTING_CODE){
            if(settingCallBack != null){
                settingCallBack.setting();
            }
            release();
        }
    }
    /**
     * 释放资源，从Activity移除{@link SettingFragment}
     */
    private void release(){
        getActivity().getFragmentManager().beginTransaction().remove(this);
    }
}
