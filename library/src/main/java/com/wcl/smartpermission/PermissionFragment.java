package com.wcl.smartpermission;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;

import com.wcl.smartpermission.callback.DeniedCallBack;
import com.wcl.smartpermission.callback.GrantCallBack;

import java.util.ArrayList;
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
    private GrantCallBack grantCallBack;
    private DeniedCallBack deniedCallBack;

    private Map<String, Boolean> permissionGrantResultMap;

    public PermissionFragment() {
        permissionGrantResultMap = new LinkedHashMap<>();
    }

    public void setPermissionCallBack(SmartPermissionCallBack permissionCallBack) {
        this.permissionCallBack = permissionCallBack;
    }

    public void setGrantCallBack(GrantCallBack grantCallBack) {
        this.grantCallBack = grantCallBack;
    }

    public void setDeniedCallBack(DeniedCallBack deniedCallBack) {
        this.deniedCallBack = deniedCallBack;
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
        boolean grantAll = true;
        Set<String> permissions = permissionGrantResultMap.keySet();
        ArrayList<String> grantPermissionList = new ArrayList<>();
        ArrayList<String> deniedPermissionList = new ArrayList<>();

        for (String permission : permissions){
           boolean grant = permissionGrantResultMap.get(permission);
            grantAll &= grant;
            if(grant){
                grantPermissionList.add(permission);
            }
            else {
                deniedPermissionList.add(permission);
            }
            if(permissionCallBack != null) {
                permissionCallBack.grantEach(permission, grant);
            }
        }
        if(permissionCallBack != null) {
            permissionCallBack.grantAll(grantAll);
        }
        if(grantAll){
            if(grantCallBack != null){
                grantCallBack.grant(grantPermissionList);
            }
        }
        else {
            if(deniedCallBack != null){
                List<String> permissionNames = Permission.transformText(getActivity(), deniedPermissionList);
                deniedCallBack.denied(deniedPermissionList, permissionNames);
            }
        }
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
