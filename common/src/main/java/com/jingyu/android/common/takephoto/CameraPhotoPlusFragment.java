package com.jingyu.android.common.takephoto;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.jingyu.android.common.activity.PlusFragment;
import com.jingyu.android.common.Configs;
import com.jingyu.android.common.util.AndroidFileUtil;
import com.jingyu.android.common.log.Logger;
import com.jingyu.android.common.util.AndroidIOUtil;
import com.jingyu.android.common.util.BitmapUtil;
import com.jingyu.java.mytool.file.FileCreater;
import com.jingyu.java.mytool.util.DateUtil;
import java.io.File;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author fengjingyu@foxmail.com
 *  从摄像头获取图片
 */
public class CameraPhotoPlusFragment extends PlusFragment {
    // 打开摄像头的请求码
    public static final int CAMERA_REQUEST_CODE = 0;
    // 裁剪的请求码
    public static final int RESIZE_REQUEST_CODE = 1;
    // 权限的请求码
    public static final int REQUEST_CODE_CAMERA_PERMISSIONS = 2;
    // 拍照的图片
    private File cameraOutputFile;
    // 裁剪的图片
    private File cropOutputFile;
    // 存储图片的文件夹
    private File savePhotoDir;
    // 是否允许裁剪图片，默认为不允许
    private boolean isResize;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Executor singleThread = Executors.newSingleThreadExecutor();

    public interface OnCameraListener {
        void onPhotoSuccess(File originPhoto, File smallPhoto);
    }

    private OnCameraListener listener;

    public void setOnCameraListener(OnCameraListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (isPermissionGranted(Manifest.permission.CAMERA) && isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openCamera();
        } else if (isPermissionGranted(Manifest.permission.CAMERA) && !isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // 有相机权限,没有写的权限
            permissionRequest(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_CAMERA_PERMISSIONS);
        } else if (!isPermissionGranted(Manifest.permission.CAMERA) && isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // 没有相机权限,有写的权限
            permissionRequest(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA_PERMISSIONS);
        } else if (!isPermissionGranted(Manifest.permission.CAMERA) && !isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // 没有相机权限,没有写的权限
            permissionRequest(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_CAMERA_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Logger.d(this + "--onRequestPermissionsResult");
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_PERMISSIONS: {
                boolean isAllSuccess = true;
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        isAllSuccess = false;
                        break;
                    }
                }

                if (isAllSuccess) {
                    openCamera();
                } else {
                    Logger.shortToast("请到设置界面打开权限");
                }
            }
            break;
        }
    }

    private void openCamera() {
        // 连续拍照时需重置
        cameraOutputFile = null;
        cropOutputFile = null;
        if ((cameraOutputFile = createCameraOutputFile()) != null && cameraOutputFile.exists()) {
            try {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraOutputFile));
                cameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.shortToast("打开拍照失败");
            }
        } else {
            Logger.shortToast("未检测到外部存储设备");
        }
    }

    private void openCrop(Uri uri) {
        if ((cropOutputFile = createCropOutputFile()) != null && cropOutputFile.exists()) {
            try {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(uri, "image/*");
                intent.putExtra("crop", "true");
                // 裁剪框的比例，1:1
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                //图片格式
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropOutputFile));
                startActivityForResult(intent, RESIZE_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
                Logger.shortToast("打开裁剪图片失败");
            }
        } else {
            Logger.shortToast("未检测到外部存储设备");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d(this + "--onActivityResult--" + (data == null ? "data为null" : ""));
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (isResize) {
                        openCrop(Uri.fromFile(cameraOutputFile));
                    } else {
                        processInThread(cameraOutputFile, null, createSmallOutputFile(cameraOutputFile), false);
                    }
                    break;
                case RESIZE_REQUEST_CODE:
                    processInThread(cameraOutputFile, cropOutputFile, createSmallOutputFile(cropOutputFile), true);
                    break;
            }
        } else {
            deleteFile(cameraOutputFile);
            deleteFile(cropOutputFile);
        }
    }

    // 用到的参数全部传入,避免线程的问题,因为可以多次拍摄,所以可能成员变量cameraOutputFile ,cropOutputFile,isResize等值会被改变
    private void processInThread(final File cameraOutputFile, final File cropOutputFile, final File smallOutputFile, final boolean isResizeImage) {
        singleThread.execute(new Runnable() {
            @Override
            public void run() {
                process(cameraOutputFile, cropOutputFile, smallOutputFile, isResizeImage);
            }
        });
    }

    private void process(final File cameraOutputFile, final File cropOutputFile, final File smallOutputFile, final boolean isResizeImage) {
        boolean result = false;
        Bitmap bitmap = null;
        try {
            Uri uri = isResizeImage ? Uri.fromFile(cropOutputFile) : Uri.fromFile(cameraOutputFile);
            // 像素尺寸压缩
            int sampleSize = BitmapUtil.calculateInSampleSize(AndroidIOUtil.getUriInputStream(getActivity(), uri), 300, 300);
            bitmap = BitmapUtil.decodeStream(AndroidIOUtil.getUriInputStream(getActivity(), uri), Bitmap.Config.RGB_565, sampleSize);
            // 占用磁盘空间压缩
            result = BitmapUtil.compressBitmap(bitmap, smallOutputFile, 60);

            if (result) {
                // 确保是在主线程中回调
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onPhotoSuccess(isResizeImage ? cropOutputFile : cameraOutputFile, smallOutputFile);
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }

            if (result) {
                if (isResizeImage) {
                    deleteFile(cameraOutputFile);
                }
            } else {
                deleteFile(cameraOutputFile);
                deleteFile(cropOutputFile);
                deleteFile(smallOutputFile);
            }
        }
    }


    private void deleteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    private File createCameraOutputFile() {
        return FileCreater.createFile(getPhotoDir(), "camera_" + getTime() + ".jpg");
    }

    private File createCropOutputFile() {
        return FileCreater.createFile(getPhotoDir(), "camera_crop_" + getTime() + ".jpg");
    }

    private File createSmallOutputFile(File originOutputFile) {
        if (originOutputFile != null && originOutputFile.exists()) {
            return FileCreater.createFile(getPhotoDir(), "small_" + originOutputFile.getName());
        }
        return null;
    }

    public File getPhotoDir() {
        File dir = FileCreater.createDir(savePhotoDir);
        if (dir != null) {
            return dir;
        } else {
            if (getActivity() != null) {
                // 相机无法进入内部存储
                return savePhotoDir = AndroidFileUtil.ExternalAndroid.getDir(getActivity(), Configs.DEFAULT_PHOTO_DIR_NAME);
            }
            return null;
        }
    }

    private String getTime() {
        return DateUtil.format(new Date(), DateUtil.FORMAT_FULL_CN);
    }

    public void setSavePhotoDir(File savePhotoDir) {
        this.savePhotoDir = savePhotoDir;
    }

    public void setResizeImage(boolean isResize) {
        this.isResize = isResize;
    }

}
