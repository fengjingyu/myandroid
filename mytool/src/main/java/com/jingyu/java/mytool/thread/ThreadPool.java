package com.jingyu.java.mytool.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fengjingyu@foxmail.com
 * 获取单例的线程池
 */
public class ThreadPool {

    private ThreadPool() {
    }

    private static class SingleThreadPool {
        private static final ExecutorService instance = Executors.newSingleThreadExecutor();
    }

    private static class CacheThreadPool {
        private static final ExecutorService instance = Executors.newCachedThreadPool();
    }

    private static class FixThreadPool {
        private static final ExecutorService instance = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1);
    }

    public static ExecutorService getSingle() {
        return SingleThreadPool.instance;
    }

    public static ExecutorService getCache() {
        return CacheThreadPool.instance;
    }

    public static ExecutorService getFix() {
        return FixThreadPool.instance;
    }


}
