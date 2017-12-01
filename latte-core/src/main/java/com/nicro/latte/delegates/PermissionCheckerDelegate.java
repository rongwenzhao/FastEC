package com.nicro.latte.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.nicro.latte.ui.camera.CameraImageBean;
import com.nicro.latte.ui.camera.LatteCamera;
import com.nicro.latte.ui.camera.RequestCodes;
import com.nicro.latte.util.logger.LatteLogger;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 动态权限检测的delegate
 * Created by rongwenzhao on 2017/11/18.
 */

@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    /**
     * 不是直接调用的方法。是被权限库动态生成新方法的。该方法不能为private，也不能为static
     */
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        LatteCamera.start(this);
    }

    /**
     * 这个是真正调用的方法
     */
    public void startCameraWithCheck() {
        PermissionCheckerDelegatePermissionsDispatcher.checkWriteWithCheck(this);
        PermissionCheckerDelegatePermissionsDispatcher.checkRedWithCheck(this);
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithCheck(this);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    /**
     * 弹出对话框，由用户选择
     *
     * @param request
     */
    /*@OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }*/

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    //我们调用camera时，存储在CameraBean中的值.
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)//裁剪的源路径与裁剪后的路径一样，就是直接覆盖裁剪前的图片。
                            .withMaxResultSize(400, 400)//宽高，可以按需设置
                            .start(getContext(), this);//开始剪裁
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = LatteCamera.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCropPath))//将图库中图片裁剪后，存放在pickResult对应路径中
                                .withMaxResultSize(400, 400)//宽高，可以按需设置
                                .start(getContext(), this);//开始剪裁
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    //在此处理图片剪裁后的逻辑。(就是上面拍照，图库选择 进入剪裁后，成功的返回)
                    final Uri cropUri = UCrop.getOutput(data);
                    LatteLogger.d("CROP_URI" + cropUri);
                    //拿到剪裁后的数据进行处理

                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    //存储权限申请
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void checkWrite() {

    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void checkRed() {

    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onWriteaDenied() {
        Toast.makeText(getContext(), "不允许写文件", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onWriteNever() {
        Toast.makeText(getContext(), "永久拒绝写文件", Toast.LENGTH_LONG).show();
    }

    /*@OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onWriteRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }*/

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onRedDenied() {
        Toast.makeText(getContext(), "不允读文件", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onRedNever() {
        Toast.makeText(getContext(), "永久拒绝读文件", Toast.LENGTH_LONG).show();
    }

    /*@OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void onRedRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }*/

}
