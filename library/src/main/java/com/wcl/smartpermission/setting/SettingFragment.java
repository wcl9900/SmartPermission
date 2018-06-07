package com.wcl.smartpermission.setting;

import android.app.Fragment;
import android.content.Intent;

import com.wcl.smartpermission.setting.callback.SettingCallBack;

/**
 * 权限设定界面Fragment桥接器
 * Created by wangchunlong on 2018/6/7.
 */

public class SettingFragment extends Fragment {
    public static final String TAG = "SmartSettingFragment";
    /**
     * 申请码
     */
    public static int REQUEST_SETTING_CODE = 222;

    SettingCallBack settingCallBack;

    SettingLauncher settingLauncher;

    public SettingFragment() {
        settingLauncher = new SettingLauncher(this);
    }

    public void setSettingCallBack(SettingCallBack settingCallBack) {
        this.settingCallBack = settingCallBack;
    }

    /**
     * 打开权限设定
     */
    public void openSetting(){
        settingLauncher.start(REQUEST_SETTING_CODE);
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
