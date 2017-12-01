package com.nicro.latte.ui.camera;

import android.net.Uri;

import com.nicro.latte.delegates.PermissionCheckerDelegate;
import com.nicro.latte.util.file.FileUtil;

/**
 * 照相机调用类
 * Created by rongwenzhao on 2017/12/1.
 */

public class LatteCamera {

    /**
     * 获得要剪裁的文件uri
     *
     * @return
     */
    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
