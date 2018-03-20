package com.jingyu.app.middle;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.jingyu.app.R;

import java.io.File;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public final class MyImg {

    public static final int PLACE_HOLDER = R.mipmap.ic_launcher;
    public static final int ERROR = com.jingyu.app.R.mipmap.ic_launcher_round;
    private static Context appContext;

    public static void initImg(Application application) {
        appContext = application;
    }

    public static void preload(String url) {
        Glide.with(appContext).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).preload();
    }

    public static void display(String url, ImageView imageView) {
        Glide.with(appContext)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void display(String url, ImageView imageView, int placeHolderImgId, int errorImgId) {
        Glide.with(appContext)
                .load(url)
                .placeholder(placeHolderImgId)
                .error(errorImgId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void displayGif(String url, ImageView imageView, int placeHolderImgId, int errorImgId) {
        Glide.with(appContext)
                .load(url)
                .asGif()
                .error(errorImgId)
                .placeholder(placeHolderImgId)
                .into(imageView);
    }

    public static void displayGiftAsBitmap(String url, ImageView imageView, int placeHolderImgId, int errorImgId) {
        Glide.with(appContext)
                .load(url)
                .asBitmap()
                .error(errorImgId)
                .placeholder(placeHolderImgId)
                .into(imageView);
    }

    public static void displayLocalVideo(String filePath, ImageView imageView, int placeHolderImgId, int errorImgId) {
        Glide.with(appContext)
                .load(Uri.fromFile(new File(filePath)))
                .error(errorImgId)
                .placeholder(placeHolderImgId)
                .into(imageView);
    }

    public static void displayCircle(String url, ImageView imageView) {
        Glide.with(appContext)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
                .bitmapTransform(new CropCircleTransformation(appContext))
                .into(imageView);
    }

    public static void displayRoundCorner(String url, ImageView imageView, int degree) {
        Glide.with(appContext)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .error(ERROR)
                .bitmapTransform(new RoundedCornersTransformation(appContext, degree, degree))
                .into(imageView);
    }

    public static void clearMemoryCache() {
        Glide.get(appContext).clearMemory();
    }

    public static void clearDiskCache() {
        Glide.get(appContext).clearDiskCache();
    }

    public interface DownloadListerner {
        void onFinish(File file);
    }

    public void getDiskCacheFileAsync(final String url, final DownloadListerner downloadListerner) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FutureTarget<File> target = Glide.with(appContext)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    if (downloadListerner != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                downloadListerner.onFinish(imageFile);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
