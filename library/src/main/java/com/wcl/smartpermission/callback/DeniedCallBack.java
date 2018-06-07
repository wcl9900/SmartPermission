package com.wcl.smartpermission.callback;

import java.util.List;

/**
 * 禁止权限回调
 * Created by wangchunlong on 2018/6/7.
 */

public interface DeniedCallBack {
    /**
     * 被用户手动禁止的权限回调
     * @param deniedPermissions 被禁止的权限列表
     * @param permissionNames 被禁止的权限的别名列表，可用来提示用户权限申请信息
     */
    void denied(List<String> deniedPermissions, List<String> permissionNames);
}
