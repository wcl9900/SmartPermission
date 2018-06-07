package com.wcl.smartpermission;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

import com.wcl.smartpermission.callback.DeniedCallBack;
import com.wcl.smartpermission.callback.GrantCallBack;

import java.util.ArrayList;

public class PermissionBuilder{
    Activity activity;
    ArrayList<String> permissionList;
    SmartPermissionCallBack permissionCallBack;

    GrantCallBack grantCallBack;
    DeniedCallBack deniedCallBack;

    PermissionBuilder(Activity activity) {
        this.activity = activity;
        permissionList = new ArrayList<>();
    }

    /**
     * 加入多个申请权限
     * @param permissions
     * @return
     */
    public PermissionBuilder addPermissions(String... permissions){
        if(permissions == null) return this;
        for (String permission : permissions){
            if(!permissionList.contains(permission)){
                permissionList.add(permission);
            }
        }
        return this;
    }

    /**
     * 加入单个申请权限
     * @param permission
     * @return
     */
    public PermissionBuilder addPermission(String permission){
        if(permission == null) return this;
        if(!permissionList.contains(permission)){
            permissionList.add(permission);
        }
        return this;
    }

    /**
     * 设定权限授权监听回调
     * @param permissionCallBack
     * @return
     */
    public PermissionBuilder callBack(SmartPermissionCallBack permissionCallBack){
        this.permissionCallBack = permissionCallBack;
        return this;
    }

    /**
     * 设定所有授权成功回调
     * @param grantCallBack
     * @return
     */
    public PermissionBuilder callBackGrant(GrantCallBack grantCallBack){
        this.grantCallBack = grantCallBack;
        return this;
    }

    /**
     * 权限被禁止回调
     * @param deniedCallBack
     * @return
     */
    public PermissionBuilder callBackDenied(DeniedCallBack deniedCallBack){
        this.deniedCallBack = deniedCallBack;
        return this;
    }

    /**
     * 启动权限申请
     */
    public void request(){
        if(activity == null){
            throw new RuntimeException("activity is null!!!");
        }

        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(PermissionFragment.TAG);
        if(fragment == null || !(fragment instanceof PermissionFragment)){
            fragment = new PermissionFragment();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, PermissionFragment.TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }

        PermissionFragment permissionFragment = (PermissionFragment) fragment;

        permissionFragment.setPermissionCallBack(permissionCallBack);
        permissionFragment.setGrantCallBack(grantCallBack);
        permissionFragment.setDeniedCallBack(deniedCallBack);

        permissionFragment.requestPermissions(permissionList);
    }
}