package com.jingyu.android.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.provider.MediaStore.Video.Thumbnails;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class BitmapUtil {

    public static boolean compressBitmap(Bitmap bitmap, File file, int quality) {
        if (bitmap == null || file == null) {
            return false;
        }
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bufferedOutputStream);
            bufferedOutputStream.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap decodeStream(InputStream input, Config type, int simpleSize) {
        if (input == null || simpleSize <= 0) {
            return null;
        }
        try {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = simpleSize;
            bitmapOptions.inPreferredConfig = type;
            bitmapOptions.inJustDecodeBounds = false;
            // BitmapFactory.decodeByteArray(data, 0, data.length, options);
            return BitmapFactory.decodeStream(input, null, bitmapOptions);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static BitmapFactory.Options decodeImageSize(InputStream input) {
        if (input == null) {
            return null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, options);
            return options;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int calculateInSampleSize(int imgWidth, int imgHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;
        if (imgHeight > reqHeight || imgWidth > reqWidth) {
            final int heightRatio = Math.round((float) imgHeight / (float) reqHeight);
            final int widthRatio = Math.round((float) imgWidth / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (options == null) {
            return 0;
        } else {
            return calculateInSampleSize(options.outWidth, options.outHeight, reqWidth, reqHeight);
        }
    }

    public static int calculateInSampleSize(InputStream inputStream, int reqWidth, int reqHeight) {
        return calculateInSampleSize(decodeImageSize(inputStream), reqWidth, reqHeight);
    }

    public static Drawable getApkIcon(Context context, String apkPath) {
        // 获得包管理器
        PackageManager pm = context.getPackageManager();
        // 获得指定路径的apk文件的相关信息
        PackageInfo info = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.publicSourceDir = apkPath;
            Drawable icon = appInfo.loadIcon(pm);
            return icon;
        }
        return null;
    }

    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    public Bitmap inputStreamToBitmap(Resources resources, InputStream inputStream) {
        return new BitmapDrawable(resources, inputStream).getBitmap();
    }

    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    public static Drawable byteToDrawable(Resources resources, byte[] b) {
        return bitmapToDrawable(resources, byteToBitmap(b));
    }

    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    public static Drawable bitmapToDrawable(Resources resources, Bitmap b) {
        return b == null ? null : new BitmapDrawable(resources, b);
    }

    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float) newWidth / org.getWidth(), (float) newHeight / org.getHeight());
    }

    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }

    public static Bitmap getVideoThumbnail(File file) {
        return ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), Thumbnails.MICRO_KIND);
    }

    public static Bitmap getVideoFirstFrameKIND(File file) {
        return ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), Thumbnails.MINI_KIND);
    }

    public static Bitmap getVideoFirstFrameFull(File file) {
        return ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), Thumbnails.FULL_SCREEN_KIND);
    }

    public static Bitmap postRotateBitamp(Bitmap bmp, float degree) {
        int bmpWidth = bmp.getWidth();
        int bmpHeight = bmp.getHeight();
        // 产生resize后的Bitmap对象
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
    }

    public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
        float[] floats = null;
        switch (flag) {
            case 0: // 水平反转
                floats = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
                break;
            case 1: // 垂直反转
                floats = new float[]{1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
                break;
        }

        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }
        return bmp;
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 获取获得带倒影的图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 图片去色,返回灰度图片
     *
     * @param bmpOriginal 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    /**
     * 把图片变成圆角
     *
     * @param bitmap 需要修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            left = 0;
            bottom = width;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

        // 画白色圆圈
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, width / 2, width / 2 - 4 / 2, paint);
        return output;
    }

    public static Bitmap makeReflectionBitmap(Bitmap originalImage) {
        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        // 实现图片的反转
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        matrix.postRotate(360f);

        // 创建反转后的图片Bitmap对象，图片高是原图的一半。
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
        // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
        // 创建画布对象，将原图画于画布，起点是原点位置。
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);// (originalImage, matrix,
        // new Paint());
        // 将反转后的图片画到画布中。
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        // 创建线性渐变LinearGradient 对象。
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0X70ffffff, 0X00ffffff, TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

}
