package com.jingyu.android.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author fengjingyu@foxmail.com
 *
 */
public class MediaUtil {

    /**
     * 开始播放
     */
    public static MediaPlayer openVoice(String url) {
        try {
            MediaPlayer mp = new MediaPlayer();
            // 置为初始状态
            mp.reset();
            // 设置文件路径
            mp.setDataSource(url);

            // 设置缓冲完成监听(当缓冲完成的时候,调用该监听器)
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                    }
                }
            });
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (mediaPlayer != null) {
                        finishPlaying(mediaPlayer);
                    }
                }
            });

            // 准备(缓冲)
            mp.prepare();
            return mp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 开始播放
     *
     * @param fileId R.raw.filename
     */
    public static MediaPlayer openVoice(MediaPlayer mediaPlayer, final Context context, final int fileId) {
        try {
            AssetFileDescriptor file = context.getResources().openRawResourceFd(fileId);

            // 置为初始状态
            mediaPlayer.reset();
            // 设置文件路径
            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());

            file.close();

            // 设置缓冲完成监听(当缓冲完成的时候,调用该监听器)
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                    }
                }
            });

            mediaPlayer.prepareAsync();
            return mediaPlayer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 结束播放
     */
    public static void finishPlaying(MediaPlayer mediaplayer) {
        if (mediaplayer != null) {
            mediaplayer.stop();
            mediaplayer.release();
        }
    }


    public static ArrayList<AudioBean> getAudioList(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        ArrayList<AudioBean> list = new ArrayList<AudioBean>();
        AudioBean bean = null;
        int index = 1;
        while (cursor.moveToNext()) {
            bean = new AudioBean();
            bean.index = index;
            bean._ID = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            bean.url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            bean.displayname = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            bean.length = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            bean.MIME_TYPE = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE));
            bean.DURATION = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            list.add(bean);
            index++;
        }
        return list;
    }


    public static ArrayList<ImageBean> getImageList(Context context) {
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        ArrayList<ImageBean> list = new ArrayList<ImageBean>();

        ImageBean bean = null;
        int index = 1;
        Cursor thumbCursor;
        while (cursor.moveToNext()) {
            bean = new ImageBean();
            bean.index = index;
            bean._ID = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            bean.url = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            bean.displayname = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            bean.length = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));

            bean.MIME_TYPE = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
            bean.MINI_THUMB_MAGIC = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MINI_THUMB_MAGIC));

            thumbCursor = resolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Thumbnails.IMAGE_ID + "=?", new String[]{bean._ID + ""}, null);

            if (thumbCursor.moveToNext()) {
                bean.thumbnail = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
            }
            thumbCursor.close();
            list.add(bean);
            index++;
        }

        return list;
    }

    public static ArrayList<VideoBean> getVideoList(Context context) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);
        ArrayList<VideoBean> list = new ArrayList<VideoBean>();
        VideoBean bean = null;
        int index = 1;
        Cursor cursorThubnail;
        while (cursor.moveToNext()) {
            bean = new VideoBean();
            bean.index = index;
            bean._ID = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
            bean.url = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            bean.displayname = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            bean.length = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
            bean.MIME_TYPE = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
            bean.DURATION = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
            // 查找对应视频的缩略表
            cursorThubnail = resolver.query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, null, " video_id=?", new String[]{bean._ID + ""}, null);
            if (cursorThubnail.moveToNext()) {
                bean.thumbnail = cursorThubnail.getString(cursorThubnail.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
            }
            cursorThubnail.close();
            list.add(bean);
            index++;
        }
        cursor.close();
        return list;
    }

    public static class AudioBean implements Serializable {

        private static final long serialVersionUID = 8288878050393530019L;

        public String displayname;
        public long length;
        public String url;
        public int index = 0;
        public int _ID;
        public String MIME_TYPE;
        public long DURATION;

        @Override
        public String toString() {
            return "SongMessage [displayname=" + displayname + ", length=" + length
                    + ", url=" + url + ", index=" + index + ", isMenuShow="
                    + ", _ID=" + _ID + ", MIME_TYPE=" + MIME_TYPE + ", DURATION="
                    + DURATION + "]";
        }
    }

    public static class ImageBean implements Serializable {
        private static final long serialVersionUID = -6847612368978583756L;

        public String displayname;
        public long length;
        public String url;

        public int index = 0;

        public int _ID;
        public String MIME_TYPE;
        public String MINI_THUMB_MAGIC;
        public String thumbnail;

        @Override
        public String toString() {
            return "ImageMessage [displayname=" + displayname + ", length="
                    + length + ", url=" + url + ", index=" + index + ", _ID=" + _ID
                    + ", MIME_TYPE=" + MIME_TYPE + ", MINI_THUMB_MAGIC="
                    + MINI_THUMB_MAGIC + ", thumbnail=" + thumbnail + "]";
        }

    }

    public static class VideoBean implements Serializable {

        private static final long serialVersionUID = 1410038234462714175L;

        public String displayname;
        public long length;
        public String url;
        public int index = 0;
        public int _ID;
        public String MIME_TYPE;
        public long DURATION;
        public String thumbnail;

        @Override
        public String toString() {
            return "VideoMessage [displayname=" + displayname + ", length="
                    + length + ", url=" + url + ", index=" + index + ", _ID=" + _ID
                    + ", MIME_TYPE=" + MIME_TYPE + ", DURATION=" + DURATION
                    + ", thumbnail=" + thumbnail + "]";
        }

    }
}
