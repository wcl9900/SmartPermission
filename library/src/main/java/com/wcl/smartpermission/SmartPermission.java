package com.wcl.smartpermission;

import android.app.Activity;

import com.wcl.smartpermission.setting.SettingBuilder;

/**权限申请入口，通过此类构建权限申请
 * Created by wangchunlong on 2018/6/6.
 */

public class SmartPermission {
    /**
     * 构建权限申请
     * @param activity
     * @return
     */
    public static PermissionBuilder build(Activity activity){
        PermissionBuilder builder = new PermissionBuilder(activity);
        return builder;
    }

    /**
     * 构建应用权限设定界面
     * @param activity
     * @return
     */
    public static SettingBuilder buildSetting(Activity activity){
        SettingBuilder settingBuilder = new SettingBuilder(activity);
        return settingBuilder;
    }
}
