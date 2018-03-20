package com.jingyu.android.common.log;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.jingyu.android.common.Configs;
import com.jingyu.android.common.util.AndroidFileUtil;
import com.jingyu.android.common.bean.JsonParse;
import com.jingyu.java.mytool.file.FileCreater;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.Date;

/**
 * @author fengjingyu@foxmail.com
 *         1 可以控制频率的吐司
 *         2 输出log到控制台
 *         3 输出log到文件
 *         4 日志的清空
 *         <p>
 *         使用前初始化initLog方法
 */
public class Logger {

    public static final String TAG_SYSTEM_OUT = "System.out";

    public static final int ALL = 0;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int ERROR = 6;
    public static final int NOTHING = 9;

    private Logger() {
    }

    private static Options options = new Options();

    private static Application application;

    public static class Options {
        /**
         * 调试土司是否显示
         */
        public boolean isShowDebugToast = true;
        /**
         * 日志是否输出到控制台 NOTHING(控制台不输出),ALL(控制台全部输出),DEBUG,INFO,ERROR
         */
        public int consoleLogLevel = ALL;
        /**
         * e类型的日志是否写到日志文件中
         */
        public boolean isErrorLog2File = true;
        /**
         * 毫秒内的连续toast不显示
         */
        public int toastTimeGap = 2000;
        /**
         * 字节,缓存文件达到70M就会清空 1024*1024*70 = 73400320
         */
        public long logFileLimitSize = 73400320;
        /**
         * 编码
         */
        public String encoding = "utf-8";
        /**
         * 存储log的目录
         */
        public File logDir;
        /**
         * error日志文件名
         */
        private String errorLogFileName = "error_log.txt";

        private File getErrorLogFile() {
            return FileCreater.createFile(getLogDir(), errorLogFileName);
        }

        public File getLogDir() {
            File dir = FileCreater.createDir(logDir);
            if (dir != null) {
                return dir;
            } else {
                return logDir = AndroidFileUtil.getAndroidDir(application, Configs.DEFAULT_LOG_DIR_NAME);
            }
        }
    }

    public static Options getOptions() {
        return options;
    }

    public static void initLog(Application application, Options options) {
        if (application == null || options == null) {
            throw new RuntimeException("Logger初始化的参数不能为null");
        }
        Logger.application = application;
        Logger.options = options;
    }

    /**
     * 记录上一次吐司的时间,屏蔽连续吐司
     */
    private static long recordLastToastTime;

    public static void longToast(Object msg) {
        longToast(false, msg);
    }

    public static void longToast(boolean showImmediately, Object msg) {
        toast(showImmediately, msg, Toast.LENGTH_LONG);
    }

    public static void shortToast(Object msg) {
        shortToast(false, msg);
    }

    public static void shortToast(boolean showImmediately, Object msg) {
        toast(showImmediately, msg, Toast.LENGTH_SHORT);
    }

    public static void dShortToast(boolean showImmediately, Object msg) {
        if (options.isShowDebugToast) {
            shortToast(showImmediately, msg);
        }
    }

    public static void dShortToast(Object msg) {
        dShortToast(false, msg);
    }

    public static void dLongToast(Object msg) {
        dLongToast(false, msg);
    }

    public static void dLongToast(boolean showImmediately, Object msg) {
        if (options.isShowDebugToast) {
            longToast(showImmediately, msg);
        }
    }

    private static void toast(boolean isShowImmediately, Object msg, int showtType) {
        if (application != null) {
            if (isShowImmediately || System.currentTimeMillis() - recordLastToastTime > options.toastTimeGap) {
                Toast.makeText(application, msg + "", showtType).show();
                recordLastToastTime = System.currentTimeMillis();
            }
        }
    }

    public static void d(String tag, Object msg) {
        if (options.consoleLogLevel <= DEBUG) {
            Log.d(tag, msg + "");
        }
    }

    public static void d(Object msg) {
        d(TAG_SYSTEM_OUT, msg);
    }

    public static void i(String tag, Object msg) {
        if (options.consoleLogLevel <= INFO) {
            Log.i(tag, msg + "");
        }
    }

    public static void i(Object msg) {
        i(TAG_SYSTEM_OUT, msg);
    }

    public static void e(Object msg) {
        e(msg, null);
    }

    private static final String LINE = "--";
    private static final String ARROW = ">>";

    public static void e(Object msg, Exception e) {
        e(TAG_SYSTEM_OUT, msg, e);
    }

    public static void e(String tag, Object msg, Exception e) {
        String result = "";

        if (e != null) {
            result = Log.getStackTraceString(e);
        }

        result = ARROW + msg + LINE + result;
        if (options.isShowDebugToast) {
            longToast(true, result);
        }
        if (options.consoleLogLevel <= ERROR) {
            Log.e(tag, result);
        }
        if (options.isErrorLog2File) {
            write(result, true);
        }
    }

    public static synchronized boolean deleteLogFile() {
        File file = options.getErrorLogFile();
        if (file != null && file.exists()) {
            return file.delete();
        }
        return false;
    }

    private static synchronized void write(String content, boolean is_append) {
        if (application != null && !TextUtils.isEmpty(content)) {
            BufferedOutputStream bufferedOutputStream = null;
            try {
                File file = options.getErrorLogFile();
                if (file == null || !file.exists()) {
                    return;
                }

                // 日志满了
                if (file.length() > options.logFileLimitSize) {
                    file.delete();
                }

                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, is_append));
                bufferedOutputStream.write((content + LINE + DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT).format(new Date()) + "  end  " + System.getProperty("line.separator")).getBytes(options.encoding));
                bufferedOutputStream.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 格式化打印json到控制台
     */
    public static void logFormatContent(boolean addLine, String tag, String hint, String str) {
        if (options.consoleLogLevel <= INFO) {
            String content = JsonParse.format(str);
            try {
                Log.i(tag, "－－－－－－－－－－－－－－－－－－  " + hint + " MSG BEGIN －－－－－－－－－－－－－－－－－－");
                if (content != null) {
                    if (addLine) {
                        String[] msgLines = content.split("\n");
                        for (String oneLine : msgLines) {
                            Log.i(tag, "| " + oneLine);
                        }
                    } else {
                        Log.i(tag, content);
                    }
                } else {
                    Log.i(tag, "传入logFormatContent()的内容为空");
                }
                Log.i(tag, "－－－－－－－－－－－－－－－－－－  " + hint + "  MSG END －－－－－－－－－－－－－－－－－－");
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(TAG_SYSTEM_OUT, "logFormatContent----" + content, ex);
            }
        }
    }

    public static void logFormatContent(String tag, String hint, String content) {
        logFormatContent(false, tag, hint, content);
    }

}