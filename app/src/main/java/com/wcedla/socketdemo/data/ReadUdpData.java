package com.wcedla.socketdemo.data;

import android.util.SparseArray;

import com.orhanobut.logger.Logger;
import com.wcedla.socketdemo.utils.UdpUtils;

import static com.wcedla.socketdemo.data.SocketData.READ_ARM_VERSION;
import static com.wcedla.socketdemo.data.SocketData.READ_COMPASS_DATA;
import static com.wcedla.socketdemo.data.SocketData.READ_CPU_USUAGE;
import static com.wcedla.socketdemo.data.SocketData.READ_DETECT_ALARM;
import static com.wcedla.socketdemo.data.SocketData.READ_DETECT_STATUS;
import static com.wcedla.socketdemo.data.SocketData.READ_DEVICE_NUMBER;
import static com.wcedla.socketdemo.data.SocketData.READ_DIRECTION_DATA;
import static com.wcedla.socketdemo.data.SocketData.READ_DIRECTION_LOST;
import static com.wcedla.socketdemo.data.SocketData.READ_ENVIRONMENT_TEMPERATURE;
import static com.wcedla.socketdemo.data.SocketData.READ_FPGA_USUAGE;
import static com.wcedla.socketdemo.data.SocketData.READ_FPGA_VERSION;
import static com.wcedla.socketdemo.data.SocketData.READ_FREQUENCY_DATA;
import static com.wcedla.socketdemo.data.SocketData.READ_GLONASS_STATUS;
import static com.wcedla.socketdemo.data.SocketData.READ_GPS_DATA;
import static com.wcedla.socketdemo.data.SocketData.READ_GPS_STATUS;
import static com.wcedla.socketdemo.data.SocketData.READ_INIT_STATUS;
import static com.wcedla.socketdemo.data.SocketData.READ_IQ_DATA;
import static com.wcedla.socketdemo.data.SocketData.READ_JZ_STATUS;
import static com.wcedla.socketdemo.data.SocketData.READ_LOCATION;
import static com.wcedla.socketdemo.data.SocketData.READ_LOCATION_SPEED;
import static com.wcedla.socketdemo.data.SocketData.READ_STAR_TIME;
import static com.wcedla.socketdemo.data.SocketData.READ_STATUS_UPLOAD;
import static com.wcedla.socketdemo.data.SocketData.READ_SYNC_STATUS;
import static com.wcedla.socketdemo.data.SocketData.READ_SYSTEM_STATUS;
import static com.wcedla.socketdemo.data.SocketData.READ_TRACE_DATA;

/**
 * @author wcedla, Email wcedla@live.com, Date on 2019/8/19.
 * PS: The code may be millions of lines,but remember comment first please
 */
public class ReadUdpData {

    private static ReadUdpData readUdpData;

    private String frameHead;

    private SparseArray<String> readDataMap = new SparseArray<>();

    /**
     * 单例模式
     *
     * @param deviceNumber 设备号
     * @return 本类实例
     */
    public static ReadUdpData getInstance(String deviceNumber) {
        if (readUdpData == null) {
            synchronized (ReadUdpData.class) {
                if (readUdpData == null) {
                    readUdpData = new ReadUdpData();
                }
            }
        }
        readUdpData.frameHead = readUdpData.dealFrameHead(deviceNumber);
        Logger.d("读命令得到的帧头:" + readUdpData.frameHead);
        return readUdpData;
    }

    private String dealFrameHead(String deviceNumber) {
        StringBuilder frameHeadBuilder = new StringBuilder();
        String[] deviceNumberArray = deviceNumber.split(" ");
        frameHeadBuilder.append("26 ");
        for (int i = deviceNumberArray.length - 1; i >= 0; i--) {
            frameHeadBuilder.append(deviceNumberArray[i]);
            frameHeadBuilder.append(" ");
        }
        frameHeadBuilder.append("00 00");
        return frameHeadBuilder.toString();
    }

    public String getReadData(int... codeList) {
        synchronized (ReadUdpData.class) {
            initReadData();
            StringBuilder readDataBuilder = new StringBuilder();
            readDataBuilder.append("00 03 ");
            readDataBuilder.append(UdpUtils.getInstance().formatHexString(Integer.toHexString(codeList.length * 2), 2, true));
            readDataBuilder.append(" ");
            for (int readcode : codeList) {
                String code = UdpUtils.getInstance().formatHexString(readDataMap.get(readcode), 2, true);
                readDataBuilder.append(code);
                readDataBuilder.append(" ");
            }
            readDataBuilder.append(UdpUtils.getInstance().getParameterCrc(readDataBuilder.toString()).toUpperCase());
            String readFrameBody = readDataBuilder.toString();
            Logger.d("读命令帧体参数:" + readFrameBody);
            String readFrameData = frameHead + " " + readFrameBody;
            Logger.d("组装读命令帧:" + readFrameData);
            return readFrameData;
        }
    }

    private void initReadData() {
        readDataMap.put(READ_DEVICE_NUMBER, "1");
        readDataMap.put(READ_FPGA_VERSION, "2");
        readDataMap.put(READ_ARM_VERSION, "3");
        readDataMap.put(READ_ENVIRONMENT_TEMPERATURE, "4");
        readDataMap.put(READ_CPU_USUAGE, "5");
        readDataMap.put(READ_FPGA_USUAGE, "6");
        readDataMap.put(READ_LOCATION, "3A");
        readDataMap.put(READ_STAR_TIME, "3B");
        readDataMap.put(READ_STATUS_UPLOAD, "3C");
        readDataMap.put(READ_LOCATION_SPEED, "3D");
        readDataMap.put(READ_SYNC_STATUS, "201");
        readDataMap.put(READ_JZ_STATUS, "220");
        readDataMap.put(READ_GPS_STATUS, "2036");
        readDataMap.put(READ_GLONASS_STATUS, "2037");
        readDataMap.put(READ_SYSTEM_STATUS, "2038");
        readDataMap.put(READ_DETECT_STATUS, "8002");
        readDataMap.put(READ_IQ_DATA, "8003");
        readDataMap.put(READ_GPS_DATA, "8004");
        readDataMap.put(READ_COMPASS_DATA, "8005");
        readDataMap.put(READ_DETECT_ALARM, "8006");
        readDataMap.put(READ_FREQUENCY_DATA, "8011");
        readDataMap.put(READ_TRACE_DATA, "8020");
        readDataMap.put(READ_DIRECTION_DATA, "8042");
        readDataMap.put(READ_DIRECTION_LOST, "8044");
        readDataMap.put(READ_INIT_STATUS, "8060");
    }

}
