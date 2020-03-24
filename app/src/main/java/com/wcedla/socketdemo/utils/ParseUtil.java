/*  car eye 车辆管理平台
 * 企业网站:www.shenghong-technology.com
 * 车眼管理平台   www.car-eye.cn
 * 车眼开源网址:https://github.com/Car-eye-admin
 * Copyright
 */


package com.wcedla.socketdemo.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


/**
 * 项目名称：DSS_808
 * 类名称：ParseUtil
 * 类描述：协议解析工具类
 * 创建人：Administrator
 * 创建时间：2016-6-20 下午6:51:43
 * 修改人：Administrator
 * 修改时间：2016-6-20 下午6:51:43
 * 修改备注：
 *
 * @version 1.0
 */
public class ParseUtil {

    /**
     * 将10进制的byte数组里的数据转换为16进制显示，不足两位的前面补0
     *
     * @param b 需要转换的存储10进制值的byte数组
     * @return 转换后的String值
     */
    public static String parseToHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            stringBuilder.append(hex.toUpperCase());
            if (i != b.length - 1) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 字符串截取或者扩容，
     * 当第二个参数比第一个字符串长时，
     * 字符串末尾补充字符‘0’
     * 直到长度等于第二个参数的长度
     *
     * @param value
     * @param len
     * @return
     */
    public static String subString(String value, int len) {
        if (value == null) {
            value = "";
        }
        int length = value.length();
        if (length > len) {
            value = value.substring(0, len);
        } else {
            for (int i = 0; i < (len - length); i++) {
                value = "0" + value;
            }
        }
        return value;

    }

    /**
     * 将字符串根据ASCII表转换为byte数组
     *
     * @param str
     * @return
     */
    public static byte[] stringToByte(String str) {
        try {
            return (str).getBytes("GBK");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * int类型转成byte数组
     */
    public static byte[] int2Byte(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    /**
     * 截取byte数组
     *
     * @param src
     * @param startIndex
     * @param length
     * @return
     */
    public static byte[] subByte(byte[] src, int startIndex, int length) {
        byte[] des = new byte[length];
        int i = 0;
        for (int j = startIndex; i < length; ++j) {
            des[i] = src[j];
            ++i;
        }
        return des;
    }

    /**
     * 将long型转换为byte数组（高位在前）
     * @param num
     * @return
     */
    public static byte[] long2Bytes(long num) {
        byte[] byteNum = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }


    /**
     * String的字符串转换成unicode的String
     */
    public static String stringToUnicode(String strText) throws Exception {
        char c;
        String strRet = "";
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
            if (intAsc > 128) {
                strRet += "\\u" + strHex;
            } else {
                // 低位在前面补00
                strRet += "\\u00" + strHex;
            }
        }
        return strRet;
    }

    /**
     * unicode的String转换成String的字符串
     */
    public static String unicodeToString(String hex) {
        int t = hex.length() / 6;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 6, (i + 1) * 6);
            // 高位需要补上00再转
            String s1 = s.substring(2, 4) + "00";
            // 低位直接转
            String s2 = s.substring(4);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

    /**
     * 将二进制转换成字符串
     *
     * @param src
     * @param startIndex
     * @param length
     * @return
     */
    public static String byteToString(byte[] src, int startIndex, int length) {
        byte[] des = new byte[length];
        int i = 0;
        for (int j = startIndex; i < length; ++j) {
            des[i] = src[j];
           i+=1;
        }
        String str = "";
        try {
            str = new String(des, "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str.trim();
    }

    /**
     * 域名转换成IP
     *
     * @param domain
     * @return
     */
    public static String domainToIp(String domain) {
        String ip = "";
        try {
            InetAddress inetHost = InetAddress.getByName(domain);
            ip = inetHost.getHostAddress();
            System.out.println("IP=" + ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static boolean isIp(String ip) {
        boolean b = false;
        if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
            String[] s = ip.split("\\.");
            if (Integer.parseInt(s[0]) < 255) {
                if (Integer.parseInt(s[1]) < 255) {
                    if (Integer.parseInt(s[2]) < 255) {
                        if (Integer.parseInt(s[3]) < 255) {
                            b = true;
                        }
                    }
                }
            }
        }
        return b;
    }

    /**
     * @param mContext
     * @param className
     * @return
     */
    public static boolean isServiceRun(Context mContext, String className) {
        boolean isRun = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(40);
        int size = serviceList.size();
        for (int i = 0; i < size; i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRun = true;
                break;
            }
        }
        return isRun;
    }

    /**
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 读取application 节点 meta-data 信息
     *
     * @param mContext
     * @param key
     * @return
     */
    public static String readMetaDataFromApplication(Context mContext,
                                                     String key) {
        try {
            ApplicationInfo appInfo = mContext.getPackageManager()
                    .getApplicationInfo(mContext.getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 保存文件到sd卡
     *
     * @param bytes 二进制数组
     * @param _path 保存路径
     * @return true 成功 false 失败
     */
    public static boolean saveToFile(byte[] bytes, String _path) {

        try {
            BufferedOutputStream os = null;
            File file = new File(_path, "TCIOV_Nebula.apk");
            int end = _path.lastIndexOf(File.separator);
            String _filePath = _path.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            os.write(bytes);
            if (os != null) {
                os.close();
            }
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 获取SD卡路径
     *
     * @return path
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }

        return sdDir.toString();
    }

    /**
     * install apk
     *
     * @param context
     * @param filePath
     * @return 0 means normal, 1 means file not exist, 2 means other exception
     * error
     */
    public static int installSlient(Context context, String filePath) {
        File file = new File(filePath);
        if (filePath == null || filePath.length() == 0
                || (file = new File(filePath)) == null || file.length() <= 0
                || !file.exists() || !file.isFile()) {
            return 1;
        }

        String[] args = {"pm", "install", "-r", filePath};
        ProcessBuilder processBuilder = new ProcessBuilder(args);

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        int result;
        try {
            process = processBuilder.start();
            successResult = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            String s;

            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }

            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = 2;
        } catch (Exception e) {
            e.printStackTrace();
            result = 2;
        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }

        // TODO should add memory is not enough here
        if (successMsg.toString().contains("Success")
                || successMsg.toString().contains("success")) {
            result = 0;
        } else {
            result = 2;
        }
        Log.d("installSlient", "successMsg:" + successMsg + ", ErrorMsg:"
                + errorMsg);
        return result;
    }

    /**
     * 获取代码版本号
     *
     * @param context
     * @return
     */
    public static int getCodeVersion(Context context) {
        int codeVersion = 0;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            codeVersion = info.versionCode; // 版本名
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return codeVersion;

    }

}
