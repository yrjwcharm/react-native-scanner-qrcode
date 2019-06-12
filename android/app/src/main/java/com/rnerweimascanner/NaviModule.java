package com.rnerweimascanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by hurong_pc111 on 2019/6/11.
 */

public class NaviModule extends ReactContextBaseJavaModule implements EasyPermissions.PermissionCallbacks {
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private Promise mPromise;
    private final  ActivityEventListener activityEventListener=new BaseActivityEventListener(){
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            super.onActivityResult(activity, requestCode, resultCode, data);
            try{
                if(requestCode==666&&resultCode == Activity.RESULT_OK){
                       String result=data.getStringExtra("result");
                        mPromise.resolve(result);
                }else if(requestCode==100&&resultCode==200){
                    String result=data.getStringExtra("result");
                    mPromise.resolve(result);
                }else{
                    mPromise.resolve("");
                }


            }catch (Exception e){
                Log.d("333",e.getMessage());
                mPromise.resolve("");
            }

        }
    };
    public NaviModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(activityEventListener);
    }

    @Override
    public String getName() {
        return "NaviModule";
    }
    /**
     * js页面跳转到activity 并传数据
     * @param name
     */

    @ReactMethod
    public void startActivityByClassName(String name,final Promise promise){
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(getCurrentActivity(), permissions)) {
            EasyPermissions.requestPermissions(getCurrentActivity(), "扫描二维码需要打开相机和散光灯的权限", REQUEST_CODE_QRCODE_PERMISSIONS, permissions);
        }else{
            try{
                Activity currentActivity = getCurrentActivity();
                if(null!=currentActivity){
                    Class aimActivity = Class.forName(name);
                    Intent intent = new Intent(currentActivity,aimActivity);
                    currentActivity.startActivityForResult(intent,100);
                }
                // Store the promise to resolve/reject when picker returns data
                mPromise=promise;
            }catch(Exception e){
                mPromise.reject("", e.getMessage());
                mPromise = null;
                throw new JSApplicationIllegalArgumentException(
                        "无法打开activity页面: "+e.getMessage());

            }
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
        EasyPermissions.onRequestPermissionsResult(i, strings, ints, this);
    }

}
