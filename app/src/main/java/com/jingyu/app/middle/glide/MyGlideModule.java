package com.jingyu.app.middle.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //builder.setDiskCache(new ExternalCacheDiskCacheFactory(context));

        //MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        //int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        //int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        //int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        //int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        //builder.setMemoryCache(new LruResourceCache(1024*1024*24));//默认24m
        //builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        //builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
        //builder.setDiskCache(new DiskLruCacheFactory());
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        // glide.register(GlideUrl.class, InputStream.class, new HttpUrlGlideUrlLoader.Factory()); // 默认HttpUrlConnection
        // glide.register(GlideUrl.class, InputStream.class, new MyOkHttpGlideUrlLoader.Factory());// 自定义okhttp
    }
}