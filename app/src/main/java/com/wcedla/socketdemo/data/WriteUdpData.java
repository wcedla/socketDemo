package com.wcedla.socketdemo.data;

import com.orhanobut.logger.Logger;
import com.wcedla.socketdemo.utils.ByteArrayConveter;
import com.wcedla.socketdemo.utils.UdpUtils;

import java.util.List;

import static com.wcedla.socketdemo.data.SocketData.ALL_ANTENNA;
import static com.wcedla.socketdemo.data.SocketData.CITY_MODE;
import static com.wcedla.socketdemo.data.SocketData.CYCLE_SCAN;
import static com.wcedla.socketdemo.data.SocketData.PANORAMIC_SCAN;
import static com.wcedla.socketdemo.data.SocketData.SECTOR_SCAN;
import static com.wcedla.socketdemo.data.SocketData.SUBURBAN_MODE;

/**
 * @author wcedla, Email wcedla@live.com, Date on 2019/8/19.
 * PS: The code may be millions of lines,but remember comment first please
 */
public class WriteUdpData {

    private static WriteUdpData writeUdpData;
    private String frameHead;

    /**
     * 单例模式
     *
     * @param deviceNumber 设备编码
     * @return 当前类实例
     */
    public static WriteUdpData getInstance(String deviceNumber) {
        if (writeUdpData == null) {
            synchronized (WriteUdpData.class) {
                if (writeUdpData == null) {
                    writeUdpData = new WriteUdpData();
                }
            }
        }
        writeUdpData.frameHead = writeUdpData.dealFrameHead(deviceNumber);
        Logger.d("写命令得到的帧头:" + writeUdpData.frameHead);
        return writeUdpData;
    }

    private String dealFrameHead(String deviceNumber) {
        StringBuilder frameHeadBuilder = new StringBuilder();
        String[] deviceNumberArray = deviceNumber.split(" ");
        frameHeadBuilder.append("26 ");
        for (int i = deviceNumberArray.length - 1; i >= 0; i--) {
            frameHeadBuilder.append(deviceNumberArray[i]);
            frameHeadBuilder.append(" ");
        }
        frameHeadBuilder.append("00 00 ");
        return frameHeadBuilder.toString();
    }

    /**
     * 重启设备
     *
     * @return 重启设备命令帧
     */
    public String setReboot() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 09 00 01 01 06 54 4F 4F 42 45 52 1B 63";
            return frameHead + frameBody;
        }
    }

    /**
     * 重置设备
     *
     * @return 重置设备命令帧
     */
    public String setReset() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 08 00 02 01 05 54 45 53 45 52 47 D8";
            return frameHead + frameBody;
        }
    }

    /**
     * 设置32颗gps的星历数据（暂时使用不到）
     */
    private void setGpsData() {

    }

    /**
     * 伪卫星重启
     *
     * @return 伪卫星重启命令帧
     */
    public String setRestartStar() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 FF 20 00 74 60";
            return frameHead + frameBody;
        }
    }

    /**
     * 下发频谱参数，比较重要的设置之一
     *
     * @param spectrumDataList 组装的频谱数据list
     * @return 下发频谱参数命令帧
     */
    public String setSpectrumData(List<SpectrumData> spectrumDataList) {
        synchronized (WriteUdpData.class) {
            String segments = UdpUtils.getInstance().formatHexString(Integer.toHexString(spectrumDataList.size()), 4, true);
            StringBuilder itemBuilder = new StringBuilder();
            itemBuilder.append("00 10 ");
            itemBuilder.append(UdpUtils.getInstance().formatHexString(Integer.toHexString(spectrumDataList.size() * 49 + 7), 2, true));
            itemBuilder.append(" 10 80 ");
            itemBuilder.append(UdpUtils.getInstance().formatHexString(Integer.toHexString(spectrumDataList.size() * 49 + 4), 1, true));
            itemBuilder.append(" ");
            itemBuilder.append(segments);
            for (SpectrumData spectrumData : spectrumDataList) {
                itemBuilder.append(" ");
                //开始频率
                itemBuilder.append(UdpUtils.getInstance().reverseByteArray(ByteArrayConveter.getByteArray(spectrumData.getBeginFrequency()), 8));
                itemBuilder.append(" ");
                //结束频率
                itemBuilder.append(UdpUtils.getInstance().reverseByteArray(ByteArrayConveter.getByteArray(spectrumData.getEndFrequency()), 8));
                itemBuilder.append(" ");
                //step(未使用)
                itemBuilder.append("00 00 00 00");
                itemBuilder.append(" ");
                //freqCount(作为初始化标记，为1就是进行初始化，为0就是切换扫描模式)
                itemBuilder.append(UdpUtils.getInstance().formatHexString(Integer.toHexString(spectrumData.getFrequencyCount()), 4, true));
                itemBuilder.append(" ");
                //VoltLevel(未使用)
                itemBuilder.append("00 00");
                itemBuilder.append(" ");
                //Ifbw (未使用)
                itemBuilder.append("00 00 00 00");
                itemBuilder.append(" ");
                //Detector  (未使用)
                itemBuilder.append("00");
                itemBuilder.append(" ");
                //RfWorkMode   (未使用)
                itemBuilder.append("00");
                itemBuilder.append(" ");
                //RfAtt    (未使用)
                itemBuilder.append("00");
                itemBuilder.append(" ");
                //IFAtt     (未使用)
                itemBuilder.append("00");
                itemBuilder.append(" ");
                //AntKind      (未使用)
                itemBuilder.append("00");
                itemBuilder.append(" ");
                //AntPolar       (未使用)
                itemBuilder.append("00");
                itemBuilder.append(" ");
                //Alarm        (未使用)
                itemBuilder.append("00 00 00 00");
                itemBuilder.append(" ");
                //AlarmType        (未使用)
                itemBuilder.append("00");
                itemBuilder.append(" ");
                //IsDF         (未使用)
                itemBuilder.append("00 00 00 00");
                itemBuilder.append(" ");
                //ValidTimes          (未使用)
                itemBuilder.append("00 00 00 00");
            }
            itemBuilder.append(" ");
            itemBuilder.append(UdpUtils.getInstance().getParameterCrc(itemBuilder.toString()).toUpperCase());
            Logger.d("写命令得到频率帧:" + frameHead + itemBuilder.toString());
            return frameHead + itemBuilder.toString();
        }
    }

    /**
     * 频谱数据上传开
     *
     * @return 打开频谱数据上传命令帧
     */
    public String setSpectrumUploadOn() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 12 80 00 9C 55";
            Logger.d("写命令打开频谱数据上传:" + frameHead + frameBody);
            return frameHead + frameBody;
        }
    }

    /**
     * 频谱数据上传关
     *
     * @return 关闭频谱数据上传命令帧
     */
    public String setSpectrumUploadOff() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 13 80 00 CD 95";
            Logger.d("写命令关闭频谱数据上传:" + frameHead + frameBody);
            return frameHead + frameBody;
        }
    }

    /**
     * * 设置扫描模式
     *
     * @param scanWay     扫描方式，0-周扫，1-扇扫，2-全景扫描
     * @param antennaList 设置天线单元组
     * @return 设置扫描模式命令帧
     */
    public String setScanMode(int scanWay, int... antennaList) {
        synchronized (WriteUdpData.class) {
            String scanWayString = "";
            String antennaString = "";
            StringBuilder scanModeBuilder = new StringBuilder();
            scanModeBuilder.append("00 10 0C 00 14 80 09 ");
            if (scanWay == CYCLE_SCAN) {
                scanWayString = "00";
            } else if (scanWay == SECTOR_SCAN) {
                scanWayString = "01";
            } else if (scanWay == PANORAMIC_SCAN) {
                scanWayString = "02";
            }
            if (antennaList.length > 0 && antennaList[0] == ALL_ANTENNA) {
                antennaString = "01 01 01 01 01 01 01 01 ";
            } else {
                String[] antennaStringArray = new String[]{"00", "00", "00", "00", "00", "00", "00", "00"};
                for (int antenna : antennaList) {
                    antennaStringArray[antenna] = "01";
                }
                StringBuilder antennaBuilder = new StringBuilder();
                for (String s : antennaStringArray) {
                    antennaBuilder.append(s);
                    antennaBuilder.append(" ");
                }
                antennaString = antennaBuilder.toString();
                Logger.d("天线值:" + antennaString);
            }
            scanModeBuilder.append(scanWayString);
            scanModeBuilder.append(" ");
            scanModeBuilder.append(antennaString);
            scanModeBuilder.append(UdpUtils.getInstance().getParameterCrc(scanModeBuilder.toString()).toUpperCase());
            String frameBody = scanModeBuilder.toString();
            Logger.d("扫描模式帧数据:" + frameHead + frameBody);
            return frameHead + frameBody;
        }
    }

    /**
     * 设置环境模式
     *
     * @param mode 环境模式选择 ,CITY_MODE,SUBURBAN_MODE
     * @return 设置环境模式命令帧
     */
    public String setEnvironmentMode(int mode) {
        synchronized (WriteUdpData.class) {
            String frameBody = "";
            if (mode == CITY_MODE) {
                frameBody = "00 10 04 00 15 80 01 00 54 3A";
            } else if (mode == SUBURBAN_MODE) {
                frameBody = "00 10 04 00 15 80 01 01 95 FA";
            }
            Logger.d("环境模式帧数据:" + frameHead + frameBody);
            return frameHead + frameBody;
        }
    }

    /**
     * 打开跟踪功能
     *
     * @return 打开跟踪功能命令帧
     */
    public String setTraceOn() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 21 80 00 6C 5A";
            return frameHead + frameBody;
        }
    }

    /**
     * 关闭跟踪功能
     *
     * @return 关闭跟踪功能命令帧
     */
    public String setTraceOff() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 22 80 00 9C 5A";
            return frameHead + frameBody;
        }
    }

    /**
     * 监测模式为飞控
     *
     * @return 设置监测模式为飞控命令帧
     */
    public String setMonitorFly() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 30 80 00 3C 5F";
            return frameHead + frameBody;
        }
    }

    /**
     * 监测模式为图传
     *
     * @return 设置监控模式为图传命令帧
     */
    public String setMonitorImg() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 31 80 00 6D 9F";
            return frameHead + frameBody;
        }
    }

    /**
     * 衰减控制
     *
     * @param attenuationValue 衰减控制值0-30db
     * @return 设置衰减控制命令帧
     */
    public String setAttenuation(double attenuationValue) {
        synchronized (WriteUdpData.class) {
            StringBuilder attenuationBuilder = new StringBuilder();
            attenuationBuilder.append("00 10 0B 00 33 80 08 ");
            String attenuationString = UdpUtils.getInstance().reverseByteArray(ByteArrayConveter.getByteArray(attenuationValue), 8);
            attenuationBuilder.append(attenuationString);
            attenuationBuilder.append(" ");
            attenuationBuilder.append(UdpUtils.getInstance().getParameterCrc(attenuationBuilder.toString()).toUpperCase());
            String frameBody = attenuationBuilder.toString();
            Logger.d("衰减控制数据:" + frameHead + frameBody);
            return frameHead + frameBody;
        }
    }

    /**
     * 打开测向
     *
     * @return 打开测向命令帧
     */
    public String setDirectionOn() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 40 80 00 3D 84";
            return frameHead + frameBody;
        }
    }

    /**
     * 关闭测向
     *
     * @return 关闭测向命令帧
     */
    public String setDirectionOff() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 41 80 00 6C 44";
            return frameHead + frameBody;
        }
    }

    /**
     * 设置测向单频
     *
     * @param frequency 测向频率
     * @param bandWidth 带宽
     * @return 设置测向单频命令帧
     */
    public String setSingleDirectionFrequency(long frequency, int bandWidth) {
        synchronized (WriteUdpData.class) {
            StringBuilder singleDirectionBuilder = new StringBuilder();
            singleDirectionBuilder.append("00 10 0F 00 45 80 0C ");
            singleDirectionBuilder.append(UdpUtils.getInstance().reverseByteArray(ByteArrayConveter.getByteArray(frequency), 8));
            singleDirectionBuilder.append(" ");
            singleDirectionBuilder.append(UdpUtils.getInstance().formatHexString(Integer.toHexString(bandWidth), 4, true));
            singleDirectionBuilder.append(" ");
            singleDirectionBuilder.append(UdpUtils.getInstance().getParameterCrc(singleDirectionBuilder.toString()).toUpperCase());
            String frameBody = singleDirectionBuilder.toString();
            Logger.d("测向单频数据:" + frameHead + frameBody);
            return frameHead + frameBody;
        }
    }

    /**
     * 开始工作
     *
     * @return 开始工作命令帧
     */
    public String setStartWork() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 50 80 00 3C 41";
            return frameHead + frameBody;
        }
    }

    /**
     * 停止工作
     *
     * @return 停止工作命令帧
     */
    public String setStopWork() {
        synchronized (WriteUdpData.class) {
            String frameBody = "00 10 03 00 51 80 00 6D 81";
            return frameHead + frameBody;
        }
    }

}
