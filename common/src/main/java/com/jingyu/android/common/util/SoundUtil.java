package com.jingyu.android.common.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;

public class SoundUtil {

    public static void setSpeakerphone(Activity activity, boolean on) {
        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        if (on) {
            audioManager.setSpeakerphoneOn(true);
            audioManager.setMode(AudioManager.MODE_NORMAL);
        } else {
            audioManager.setSpeakerphoneOn(false);
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        }
    }

    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * uses-permission android:name="android.permission.VIBRATE"
     *
     * long milliseconds:震动的时长，单位是毫秒
     *
     * long[] pattern:自定义震动模式 。数组中数字的含义依次是静止的时长，震动时长，静止时长，震动时长。。。时长的单位是毫秒
     *
     * boolean isRepeat:是否反复震动，如果是true，反复震动，如果是false，只震动一次
     */
    public static void Vibrate(Context context, long[] pattern, boolean isRepeat) {
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
