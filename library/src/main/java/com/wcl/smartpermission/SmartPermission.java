package com.wcl.smartpermission;

import android.app.Activity;

/**权限申请入口，通过此类构建权限申请
 * Created by wangchunlong on 2018/6/6.
 */

public class SmartPermission {
    public static PermissionBuilder build(Activity activity){
        PermissionBuilder builder = new PermissionBuilder(activity);
        return builder;
    }
}
