package com.jingyu.android.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author fengjingyu@foxmail.com
 *
 */
public class BroadcastUtil {

    public static void register(Context context, int priority, String action, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.setPriority(priority);
        filter.addAction(action);
        context.registerReceiver(receiver, filter);
    }

    public static void unRegister(Context context, BroadcastReceiver receiver) {
        context.unregisterReceiver(receiver);
    }

    public static void sendReceiver(Context context, String action, String command_key, String command_value) {
        Intent intent = new Intent();
        intent.setAction(action);
        intent.putExtra(command_key, command_value);
        context.sendBroadcast(intent);
    }

    public static void sendReceiver(Context context, String action, String[] command_keys, String[] command_values) {
        Intent intent = new Intent();
        intent.setAction(action);
        int size = command_keys.length;
        for (int i = 0; i < size; i++) {
            intent.putExtra(command_keys[i], command_values[i]);
        }
        context.sendBroadcast(intent);
    }
}
