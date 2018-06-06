package com.wcl.smartpermission;

/**
 * 权限授权结果回调
 * Created by wangchunlong on 2018/6/6.
 */

public interface SmartPermissionCallBack {
    /**
     * 判断单个权限是否授权成功
     * @param permission 权限
     * @param grant 是否授权成功
     */
    void grantEach(String permission, boolean grant);

    /**
     * 判断所有权限是否授权成功
     * @param grant 是否授权成功
     */
    void grantAll(boolean grant);
}
