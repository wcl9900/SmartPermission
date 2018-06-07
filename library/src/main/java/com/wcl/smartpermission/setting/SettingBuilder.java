package com.wcl.smartpermission.setting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

import com.wcl.smartpermission.setting.callback.SettingCallBack;

/**
 * 权限设定构建器
 * Created by wangchunlong on 2018/6/7.
 */

public class SettingBuilder {
    Activity activity;
    SettingCallBack settingCallBack;

    public SettingBuilder(Activity activity) {
        this.activity = activity;
    }

    public SettingBuilder callBackSetting(SettingCallBack settingCallBack){
        this.settingCallBack = settingCallBack;
        return this;
    }

    public void request(){
        if(activity == null){
            throw new RuntimeException("activity is null!!!");
        }

        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(SettingFragment.TAG);
        if(fragment == null || !(fragment instanceof SettingFragment)){
            fragment = new SettingFragment();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, SettingFragment.TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        SettingFragment settingFragment = (SettingFragment) fragment;
        settingFragment.setSettingCallBack(settingCallBack);
        settingFragment.openSetting();
    }
}
