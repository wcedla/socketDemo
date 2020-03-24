package com.wcedla.socketdemo.data;

public class SocketData {

    public static final boolean DEBUG_VERSION = true;

    public static final String LOG_TAG = "wcedlaLog";

    public static final int UDP_RESPONSE_TIMEOUT = 1000;

    public static final String IP = "192.168.54.100";

    public static final int PORT = 6666;

    public static final int RECEIVE_MAX_LENGTH = 2800;

//    public static String IP="47.99.125.151";
//    public static String PORT="8087";

//    public static String IP="192.168.10.30";
//    public static String PORT="9000";

    /**
     * 读取设备编号
     */
    public static final int READ_DEVICE_NUMBER = 1;

    /**
     * 读取FPGA版本号
     */
    public static final int READ_FPGA_VERSION = 2;

    /**
     * 读取ARM版本号
     */
    public static final int READ_ARM_VERSION = 3;

    /**
     * 读取环境温度
     */
    public static final int READ_ENVIRONMENT_TEMPERATURE = 4;

    /**
     * 读取CPU使用率
     */
    public static final int READ_CPU_USUAGE = 5;

    /**
     * 读取FPGA发射通道使用率
     */
    public static final int READ_FPGA_USUAGE = 6;

    /**
     * 读取定位位置
     */
    public static final int READ_LOCATION = 7;

    /**
     * 读取授时时间
     */
    public static final int READ_STAR_TIME = 8;

    /**
     * 读取状态上报间隔
     */
    public static final int READ_STATUS_UPLOAD = 9;

    /**
     * 读取定位速度
     */
    public static final int READ_LOCATION_SPEED = 10;

    /**
     * 读取授时同步状态
     */
    public static final int READ_SYNC_STATUS = 11;

    /**
     * 读取晶振工作状态
     */
    public static final int READ_JZ_STATUS = 12;

    /**
     * 读取GPS伪卫星状态
     */
    public static final int READ_GPS_STATUS = 13;

    /**
     * 读取GLONASS伪卫星状态
     */
    public static final int READ_GLONASS_STATUS = 14;

    /**
     * 读取系统状态信息
     */
    public static final int READ_SYSTEM_STATUS = 15;

    /**
     * 读取探测设备状态
     */
    public static final int READ_DETECT_STATUS = 16;


    /**
     * 读取原始IQ数据
     */
    public static final int READ_IQ_DATA = 17;

    /**
     * 读取电子罗盘数据
     */
    public static final int READ_COMPASS_DATA = 18;

    /**
     * 读取监测告警数据
     */
    public static final int READ_DETECT_ALARM = 19;

    /**
     * 读取GPS数据
     */
    public static final int READ_GPS_DATA = 20;

    /**
     * 读取频谱数据
     */
    public static final int READ_FREQUENCY_DATA = 21;

    /**
     * 读取跟踪数据
     */
    public static final int READ_TRACE_DATA = 22;

    /**
     * 读取测向结果
     */
    public static final int READ_DIRECTION_DATA = 23;

    /**
     * 读取测向消失
     */
    public static final int READ_DIRECTION_LOST = 24;

    /**
     * 读取初始化状态
     */
    public static final int READ_INIT_STATUS = 25;

    public static final int CYCLE_SCAN = 0;

    public static final int SECTOR_SCAN = 1;

    public static final int PANORAMIC_SCAN = 2;

    public static final int FIRST_ANTENNA = 0;

    public static final int SECOND_ANTENNA = 1;

    public static final int THIRD_ANTENNA = 2;

    public static final int FOURTH_ANTENNA = 3;

    public static final int FIFTH_ANTENNA = 4;

    public static final int SIXTH_ANTENNA = 5;

    public static final int SEVENTH_ANTENNA = 6;

    public static final int EIGHTH_ANTENNA = 7;

    public static final int ALL_ANTENNA = 8;

    public static final int CITY_MODE = 0;

    public static final int SUBURBAN_MODE = 1;
}
