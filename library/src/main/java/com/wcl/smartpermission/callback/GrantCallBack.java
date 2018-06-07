package com.wcl.smartpermission.callback;

import java.util.List;

/**
 * 授权成功回调
 * Created by wangchunlong on 2018/6/7.
 */

public interface GrantCallBack {
    /**
     * 所有申请权限授权成功回调
     * @param permissions
     */
    void grant(List<String> permissions);
}
