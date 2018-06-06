package com.wcl.smartpermission;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用来处理权限申请的Fragment
 * Created by wangchunlong on 2018/6/6.
 */

public class PermissionFragment extends Fragment {
    public static final String TAG = "SmartPermissionFragment";
    /**
     * 权限申请码
     */
    public static int REQUEST_PERMISSION_CODE = 111;

    private SmartPermissionCallBack permissionCallBack;

    private Map<String, Boolean> permissionGrantResultMap;

    public PermissionFragment() {
        permissionGrantResultMap = new LinkedHashMap<>();
    }

    public void setPermissionCallBack(SmartPermissionCallBack permissionCallBack) {
        this.permissionCallBack = permissionCallBack;
    }

    public void requestPermissions(List<String> permissionList){
        List mPermissionList = permissionList;
        permissionGrantResultMap.clear();

        String[] permissions = new String[mPermissionList.size()];
        mPermissionList.toArray(permissions);
        for (String permission : permissions){
            if(isGranted(permission)){
                permissionGrantResultMap.put(permission, true);
                mPermissionList.remove(permission);
            }
            else {
                permissionGrantResultMap.put(permission, false);
            }
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mPermissionList.size() > 0){
            String[] needRequestPermissions = new String[mPermissionList.size()];
            permissionList.toArray(needRequestPermissions);
            requestPermissions(needRequestPermissions, REQUEST_PERMISSION_CODE);
        }
        else {
            handlePermissionCallBack();
        }
    }

    private void handlePermissionCallBack(){
        if(permissionCallBack == null) return;
        boolean grantAll = true;
        Set<String> permissions = permissionGrantResultMap.keySet();
        for (String permission : permissions){
           boolean grant = permissionGrantResultMap.get(permission);
            grantAll &= grant;
            permissionCallBack.grantEach(permission, grant);
        }
        permissionCallBack.grantAll(grantAll);
        release();
    }

    private boolean isGranted(String permission){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        return getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_CODE) {
            int size = permissions.length;
            for (int index = 0; index < size; index++) {
                boolean grant = grantResults[index] == PackageManager.PERMISSION_GRANTED;
                permissionGrantResultMap.put(permissions[index], grant);
            }
            handlePermissionCallBack();
        }
    }

    /**
     * 释放资源，从Activity移除{@link PermissionFragment}
     */
    private void release(){
        getActivity().getFragmentManager().beginTransaction().remove(this);
    }
}
