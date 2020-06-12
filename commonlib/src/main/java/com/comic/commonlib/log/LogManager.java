package com.comic.commonlib.log;

import android.util.Log;

import com.comic.commonlib.BuildConfig;

/*
* TODO...本地存储的逻辑暂未实现
* */
public class LogManager {
    private static boolean isLogEnable = true;
    private static final int INFO = 1;
    private static final int DEBUG = 2;
    private static final int WARN = 3;
    private static final int ERROR = 4;
    private static final int VERBOSE = 5;

    public static boolean isLogEnable() {
        return isLogEnable;
    }

    public static void i(String tag, String format, Object... args) {
        if(isLogEnable) {
            doLog(tag, logContentFormat(format,args),INFO);
        }
        writeLogToFile(tag,logContentFormat(format,args),INFO);
    }


    public static void e(String tag, String format, Object... args) {
        if(isLogEnable) {
            doLog(tag, logContentFormat(format,args),ERROR);
        }
        writeLogToFile(tag,logContentFormat(format,args),ERROR);
    }

    public static void d(String tag, String format, Object... args) {
        if(isLogEnable) {
            doLog(tag, logContentFormat(format,args),DEBUG);
        }
        writeLogToFile(tag,logContentFormat(format,args),DEBUG);
    }

    public static void w(String tag, String format, Object... args) {
        if(isLogEnable) {
            doLog(tag, logContentFormat(format,args),WARN);
        }
        writeLogToFile(tag,logContentFormat(format,args),WARN);
    }

    public static void v(String tag, String format, Object... args) {
        if(isLogEnable) {
            doLog(tag, logContentFormat(format,args),VERBOSE);
        }
        writeLogToFile(tag,logContentFormat(format,args),VERBOSE);
    }
    /**
     * Debug
     * @param tag
     * @param msg
     */
    public static void d(final String tag, final String msg) {
        if (isLogEnable) {
            doLog(tag, msg,DEBUG);
        }
        writeLogToFile(tag,msg,DEBUG);
    }


    /**
     * Verbose
     * @param tag
     * @param msg
     */
    public static void v(final String tag, final String msg) {
        if (isLogEnable) {
            doLog(tag, msg,VERBOSE);
        }
        writeLogToFile(tag,msg,VERBOSE);
    }

    /**
     * Warning
     * @param tag
     * @param msg
     */
    public static void w(final String tag, final String msg) {
        if (isLogEnable) {
            doLog(tag, msg,WARN);
        }
        writeLogToFile(tag,msg,WARN);
    }

    /**
     * Error
     * @param tag
     * @param msg
     */
    public static void e(final String tag, final String msg) {
        if (isLogEnable) {
            doLog(tag, msg,ERROR);
        }
        writeLogToFile(tag,msg,ERROR);
    }
    /**
     * Information
     * @param tag
     * @param msg
     */
    public static void i(final String tag, final String msg) {
        if (isLogEnable) {
            doLog(tag, msg,INFO);
        }
        writeLogToFile(tag,msg,INFO);
    }

    private static String logContentFormat(String format, Object... args){
        String result = String.format(format,args);
        return result;
    }

    //TODO...
    private static void writeLogToFile(final String tag, final String msg, final int level) {
    }

    //TODO...
    private static void writeLogToFile(String tag, int level, String fileName, int lineNumber, String methodName, String msg) {

    }

    private static void doLog(String tag, String msg , int level) {
        final String fileName;
        final int lineNumber;
        final String methodName;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement element = null;
        if(stackTraceElements != null && stackTraceElements.length > 4) {
            element = stackTraceElements[4];
        }
        if (element!=null) {
            fileName = element.getFileName();
            lineNumber = element.getLineNumber();
            methodName = element.getMethodName();
        }else {
            fileName = "";
            lineNumber = -1;
            methodName = "";
        }
        switch (level){
            case DEBUG:
                Log.d(tag, rebuildMsg(fileName,lineNumber,methodName, msg));
                break;
            case INFO:
                Log.i(tag, rebuildMsg(fileName,lineNumber,methodName, msg));
                break;
            case VERBOSE:
                Log.v(tag, rebuildMsg(fileName,lineNumber,methodName, msg));
                break;
            case ERROR:
                Log.e(tag, rebuildMsg(fileName,lineNumber,methodName, msg));
                break;
            case WARN:
                Log.w(tag, rebuildMsg(fileName,lineNumber,methodName, msg));
                break;
        }
    }

    /**
     * Rebuild Log Msg
     * @param msg
     * @return
     */
    private static String rebuildMsg(String fileName, int lineNumber, String methodName, String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append(fileName);
        sb.append(" (line:");
        sb.append(lineNumber);
        sb.append(") ");
        sb.append("(methodName:");
        sb.append(methodName+") ");
        sb.append(msg);
        return sb.toString();
    }
}
