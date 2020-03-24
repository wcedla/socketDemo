package com.wcedla.socketdemo.data;

import com.wcedla.socketdemo.utils.ByteArrayConveter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author wcedla, Email wcedla@live.com, Date on 2019/8/20.
 * PS: The code may be millions of lines,but remember comment first please
 */
public class SetJsonBeanData {

    private static SetJsonBeanData setJsonBeanData;
    private ReadDataJsonBean.ParameterDataClass parameterDataClass;

    public static SetJsonBeanData getInstance() {
        if (setJsonBeanData == null) {
            synchronized (SetJsonBeanData.class) {
                if (setJsonBeanData == null) {
                    setJsonBeanData = new SetJsonBeanData();
                }
            }
        }
        return setJsonBeanData;
    }

    public ReadDataJsonBean.ParameterDataClass analysisParameter(List<ParameterData> parameterDataList){
        parameterDataClass=new ReadDataJsonBean.ParameterDataClass();
        for (ParameterData parameterData:parameterDataList){
            setData(parameterData.getParameterCode(),parameterData.getParameterName());
        }
        return parameterDataClass;
    }


    private void setData(String parameterCode, String parameterData) {
        switch (parameterCode) {
            case "0001":
                ReadDataBeanList.DeviceNumberData deviceNumberData = new ReadDataBeanList.DeviceNumberData();
                deviceNumberData.setDeviceNumber(getDeviceNumber(parameterData));
                parameterDataClass.setDeviceNumberData(deviceNumberData);
                break;
            case "0002":
                ReadDataBeanList.FpgaVersionData fpgaVersionData = new ReadDataBeanList.FpgaVersionData();
                fpgaVersionData.setFpgaVersion(getVersion(parameterData));
                parameterDataClass.setFpgaVersionData(fpgaVersionData);
                break;
            case "0003":
                ReadDataBeanList.ArmVersionData armVersionData = new ReadDataBeanList.ArmVersionData();
                armVersionData.setArmVersion(getVersion(parameterData));
                parameterDataClass.setArmVersionData(armVersionData);
                break;
            case "0004":
                ReadDataBeanList.EnvironmentTemperatureData environmentTemperatureData = new ReadDataBeanList.EnvironmentTemperatureData();
                if (parameterData.length() == 2) {
                    environmentTemperatureData.setEnvironmentTemperatur(Integer.parseInt(parameterData, 16) + "");
                } else {
                    environmentTemperatureData.setEnvironmentTemperatur(getFloatData(parameterData));
                }
                parameterDataClass.setEnvironmentTemperatureData(environmentTemperatureData);
                break;
            case "0005":
                ReadDataBeanList.CpuUsageData cpuUsageData = new ReadDataBeanList.CpuUsageData();
                if(parameterData.length()==2){
                    cpuUsageData.setCpuUsage(Integer.parseInt(parameterData,16)+"");
                }else {
                    cpuUsageData.setCpuUsage(getFloatData(parameterData));
                }
                parameterDataClass.setCpuUsageData(cpuUsageData);
                break;
            case "0006":
                ReadDataBeanList.FpgaUsuageData fpgaUsuageData = new ReadDataBeanList.FpgaUsuageData();
                if(parameterData.length()==2){
                    fpgaUsuageData.setFpgaUsuage(Integer.parseInt(parameterData,16)+"");
                }else {
                    fpgaUsuageData.setFpgaUsuage(getFloatData(parameterData));
                }
                parameterDataClass.setFpgaUsuageData(fpgaUsuageData);
                break;
            case "003A":
                ReadDataBeanList.LocationData locationData = getLocationData(parameterData);
                parameterDataClass.setLocationData(locationData);
                break;
            case "003B":
                ReadDataBeanList.StarTimeData starTimeData = new ReadDataBeanList.StarTimeData();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
                starTimeData.setStarTime(simpleDateFormat.format(new Date(Long.parseLong(setJsonBeanData.getLongData(parameterData))*1000)));
                parameterDataClass.setStarTimeData(starTimeData);
                break;
            case "003C":
                ReadDataBeanList.StatusUploadData statusUploadData = new ReadDataBeanList.StatusUploadData();
                String[] parameterArray=parameterData.split(" ");
                if(parameterArray.length>1) {
                    statusUploadData.setStatusUpload(Integer.parseInt(parameterArray[1] + parameterArray[0], 16) + "");
                }else if(parameterArray.length==1) {
                    statusUploadData.setStatusUpload(Integer.parseInt(parameterData, 16) + "");
                }
                parameterDataClass.setStatusUploadData(statusUploadData);
                break;
            case "003D":
                ReadDataBeanList.LocationSpeedData speedData = getSpeedData(parameterData);
                parameterDataClass.setLocationSpeedData(speedData);
                break;
            case "0201":
                ReadDataBeanList.TimeSyncData timeSyncData = getTimeSyncData(parameterData);
                parameterDataClass.setTimeSyncData(timeSyncData);
                break;
            case "0220":
                ReadDataBeanList.JzWorkStatusData jzWorkStatusData = getJzWorkStatusData(parameterData);
                parameterDataClass.setJzWorkStatusData(jzWorkStatusData);
                break;
            case "2036":
                ReadDataBeanList.GpsStatusData gpsStatusData = getGpsStatusData(parameterData);
                parameterDataClass.setGpsStatusData(gpsStatusData);
                break;
            case "2037":
                ReadDataBeanList.GlonassStatusData glonassStatusData = getGlonassStatusData(parameterData);
                parameterDataClass.setGlonassStatusData(glonassStatusData);
                break;
            case "2038":
                ReadDataBeanList.SystemStatus systemStatus = getSystemStatus(parameterData);
                parameterDataClass.setSystemStatus(systemStatus);
                break;
            case "8002":
                ReadDataBeanList.DetectStatusData detectStatusData = getDetectStatusData(parameterData);
                parameterDataClass.setDetectStatusData(detectStatusData);
                break;
            case "8003":
                ReadDataBeanList.IqData iqData = getIqData(parameterData);
                parameterDataClass.setIqData(iqData);
                break;
            case "8004":
                ReadDataBeanList.GpsData gpsData = getGpsData(parameterData);
                parameterDataClass.setGpsData(gpsData);
                break;
            case "8005":
                ReadDataBeanList.CompassData compassData = getCompassData(parameterData);
                parameterDataClass.setCompassData(compassData);
                break;
            case "8006":
                ReadDataBeanList.TraceAlarmData traceAlarmData = getTraceAlarmData(parameterData);
                parameterDataClass.setTraceAlarmData(traceAlarmData);
                break;
            case "8011":
                ReadDataBeanList.FrequencyResultData frequencyResultData = getFrequencyResultData(parameterData);
                parameterDataClass.setFrequencyResultData(frequencyResultData);
                break;
            case "8020":
                ReadDataBeanList.TraceData traceData = getTraceData(parameterData);
                parameterDataClass.setTraceData(traceData);
                break;
            case "8042":
                ReadDataBeanList.DirectionResultData directionResultData = getDirectionResultData(parameterData);
                parameterDataClass.setDirectionResultData(directionResultData);
                break;
            case "8044":
                ReadDataBeanList.DirectionLost directionLost = getDirectionLost(parameterData);
                parameterDataClass.setDirectionLost(directionLost);
                break;
            case "8060":
                ReadDataBeanList.InitStatusData initStatusData = getInitStatusData(parameterData);
                parameterDataClass.setInitStatusData(initStatusData);
                break;
            default:
                break;
        }
    }

    private String getDeviceNumber(String parameterData) {
        String[] deviceNumberArray = parameterData.split(" ");
        StringBuilder deviceNumberBuilder = new StringBuilder();
        for (int i = deviceNumberArray.length - 1; i >= 0; i--) {
            deviceNumberBuilder.append(deviceNumberArray[i]);
            if (i > 0) {
                deviceNumberBuilder.append(" ");
            }
        }
        return deviceNumberBuilder.toString();
    }

    private String getVersion(String parameterData) {
        int mainVersion = Integer.parseInt(parameterData.split(" ")[0], 16);
        int minorVersion = Integer.parseInt(parameterData.split(" ")[1], 16);
        return mainVersion + "." + minorVersion;
    }

    private String getFloatData(String parameterData) {
        byte[] floatDataByte = new byte[4];
        String[] floatArray = parameterData.split(" ");
        int index = 0;
        for (int i = floatArray.length - 1; i >= 0; i--) {
            floatDataByte[index] = (byte) Integer.parseInt(floatArray[i], 16);
            index += 1;
        }
        System.out.println("获取float值为:"+ByteArrayConveter.getFloat(floatDataByte, 0));
        return ByteArrayConveter.getFloat(floatDataByte, 0) + "";
    }

    private ReadDataBeanList.LocationData getLocationData(String parameterData) {
        ReadDataBeanList.LocationData locationData = new ReadDataBeanList.LocationData();
        String[] locationDataArray = parameterData.split(" ");
        String height = getFloatData(locationDataArray[16] + " " + locationDataArray[17] + " " + locationDataArray[18] + " " + locationDataArray[19]);
        String longitude = getDoubleData(locationDataArray[8] + " " + locationDataArray[9] + " " + locationDataArray[10] + " " + locationDataArray[11] + " " + locationDataArray[12] + " " + locationDataArray[13] + " " + locationDataArray[14] + " " + locationDataArray[15]);
        String latitude = getDoubleData(locationDataArray[0] + " " + locationDataArray[1] + " " + locationDataArray[2] + " " + locationDataArray[3] + " " + locationDataArray[4] + " " + locationDataArray[5] + " " + locationDataArray[6] + " " + locationDataArray[7]);
        locationData.setHeight(height);
        locationData.setLatitude(latitude);
        locationData.setLongitude(longitude);
        System.out.println("lat:"+latitude+",long:"+longitude+",height:"+height);
        return locationData;
    }

    private String getLongData(String parameterData) {
        byte[] longDataByte = new byte[8];
        String[] longArray = parameterData.split(" ");
        int index = 0;
        for (int i = longArray.length - 1; i >= 0; i--) {
            longDataByte[index] = (byte) Integer.parseInt(longArray[i], 16);
            index += 1;
        }
        System.out.println("获取long值为:"+ByteArrayConveter.getLong(longDataByte, 0));
        return ByteArrayConveter.getLong(longDataByte, 0) + "";
    }

    private String getDoubleData(String parameterData) {
        byte[] doubleDataByte = new byte[8];
        String[] doubleArray = parameterData.split(" ");
        int index = 0;
        for (int i = doubleArray.length - 1; i >= 0; i--) {
            doubleDataByte[index] = (byte) Integer.parseInt(doubleArray[i], 16);
            index += 1;
        }
        System.out.println("获取double值为:"+ByteArrayConveter.getDouble(doubleDataByte, 0));
        return ByteArrayConveter.getDouble(doubleDataByte, 0) + "";
    }

    private ReadDataBeanList.LocationSpeedData getSpeedData(String parameterData) {
        ReadDataBeanList.LocationSpeedData speedData = new ReadDataBeanList.LocationSpeedData();
        String[] speedArray = parameterData.split(" ");
        String zSpeed = getFloatData(speedArray[0] + " " + speedArray[1] + " " + speedArray[2] + " " + speedArray[3]);
        String ySpeed = getFloatData(speedArray[4] + " " + speedArray[5] + " " + speedArray[6] + " " + speedArray[7]);
        String xSpeed = getFloatData(speedArray[8] + " " + speedArray[9] + " " + speedArray[10] + " " + speedArray[11]);
        speedData.setxSpeed(xSpeed);
        speedData.setySpeed(ySpeed);
        speedData.setzSpeed(zSpeed);
        System.out.print("速度值:x:"+xSpeed+",y:"+ySpeed+",z:"+zSpeed);
        return speedData;
    }

    private ReadDataBeanList.TimeSyncData getTimeSyncData(String parameterData) {
        ReadDataBeanList.TimeSyncData timeSyncData = new ReadDataBeanList.TimeSyncData();
        int legalCode = Integer.parseInt(parameterData.substring(0, 2), 16);
        int workStatus = legalCode & 0x00000001;
        int locationStatus = (legalCode & 0x00000002)>>1;
        int secondStatus = (legalCode & 0x00000004)>>2;
        int syncStatus = (legalCode & 0x00000008)>>3;
        int ntpStatus = (legalCode & 0x00000010)>>4;
        int starReceiveComplete = (legalCode & 0x00000020)>>5;
        int bookReceiverComplete = (legalCode & 0x00000040)>>6;
        System.out.print("授时同步状态值(bit0-bit6):"+workStatus+","+locationStatus+","+secondStatus+","+syncStatus+","+ntpStatus+","+starReceiveComplete+","+bookReceiverComplete);
        timeSyncData.setLocationStatus(locationStatus + "");
        timeSyncData.setWorkStatus(workStatus + "");
        timeSyncData.setSecondStatus(secondStatus + "");
        timeSyncData.setBookReceiverComplete(bookReceiverComplete + "");
        timeSyncData.setStarReceiveComplete(starReceiveComplete + "");
        timeSyncData.setSyncStatus(syncStatus + "");
        timeSyncData.setNtpStatus(ntpStatus + "");
        return timeSyncData;
    }

    private ReadDataBeanList.JzWorkStatusData getJzWorkStatusData(String parameterData) {
        ReadDataBeanList.JzWorkStatusData jzWorkStatusData = new ReadDataBeanList.JzWorkStatusData();
        int code = Integer.parseInt(parameterData.substring(0, 2), 16);
        System.out.print("晶振状态:"+code);
        jzWorkStatusData.setJzWorkStatus(code + "");
        return jzWorkStatusData;
    }

    private ReadDataBeanList.GpsStatusData getGpsStatusData(String parameterData) {
        ReadDataBeanList.GpsStatusData gpsStatusData = new ReadDataBeanList.GpsStatusData();
        int code = Integer.parseInt(parameterData.substring(0, 2), 16);
        int clockStatus = code & 0x00000001;
        int codeBStatus = (code & 0x00000002)>>1;
        int radioStatus = (code & 0x00000008)>>3;
        int shotStatus = (code & 0x00000020)>>5;
        System.out.print("GPS伪卫星状态:(bit0 1 3 5)"+clockStatus+","+codeBStatus+","+radioStatus+","+shotStatus);
        gpsStatusData.setClockStatus(clockStatus + "");
        gpsStatusData.setCodeBStatus(codeBStatus + "");
        gpsStatusData.setRadioStatus(radioStatus + "");
        gpsStatusData.setShotStatus(shotStatus + "");
        return gpsStatusData;
    }

    private ReadDataBeanList.GlonassStatusData getGlonassStatusData(String parameterData) {
        ReadDataBeanList.GlonassStatusData glonassStatusData = new ReadDataBeanList.GlonassStatusData();
        int code = Integer.parseInt(parameterData.substring(0, 2), 16);
        int clockStatus = code & 0x00000001;
        int codeBStatus = (code & 0x00000002)>>1;
        int radioStatus = (code & 0x00000008)>>3;
        int gnssStatus = (code & 0x00000010)>>4;
        int shotStatus = (code & 0x00000020)>>5;
        System.out.print("GLONASS伪卫星状态(bit0 1 3 4 5):"+clockStatus+","+codeBStatus+","+radioStatus+","+gnssStatus+","+shotStatus);
        glonassStatusData.setClockStatus(clockStatus + "");
        glonassStatusData.setCodeBStatus(codeBStatus + "");
        glonassStatusData.setGnssStatus(gnssStatus + "");
        glonassStatusData.setRadioStatus(radioStatus + "");
        glonassStatusData.setShotStatus(shotStatus + "");
        return glonassStatusData;
    }

    private ReadDataBeanList.SystemStatus getSystemStatus(String parameter) {
        ReadDataBeanList.SystemStatus systemStatus = new ReadDataBeanList.SystemStatus();
        String locationDataString = parameter.substring(0, 59);
        String speedString = parameter.substring(60, 95);
        String timeSyncString = parameter.substring(96, 107);
        String gpsStatusString = parameter.substring(108, 119);
        String glonassStatusString = parameter.substring(120, 131);
        System.out.println("设备系统状态对应字符串:"+locationDataString+","+speedString+","+timeSyncString+","+gpsStatusString+","+glonassStatusString);
        ReadDataBeanList.LocationData locationData = getLocationData(locationDataString);
        ReadDataBeanList.LocationSpeedData locationSpeedData = getSpeedData(speedString);
        ReadDataBeanList.TimeSyncData timeSyncData = getTimeSyncData(timeSyncString);
        ReadDataBeanList.GpsStatusData gpsStatusData = getGpsStatusData(gpsStatusString);
        ReadDataBeanList.GlonassStatusData glonassStatusData = getGlonassStatusData(glonassStatusString);
        systemStatus.setGlonassStatusData(glonassStatusData);
        systemStatus.setGpsStatusData(gpsStatusData);
        systemStatus.setLocationData(locationData);
        systemStatus.setLocationSpeedData(locationSpeedData);
        systemStatus.setTimeSyncData(timeSyncData);
        return systemStatus;
    }

    //todo 组合数据体，未验证
    private ReadDataBeanList.DetectStatusData getDetectStatusData(String parameterData) {
        ReadDataBeanList.DetectStatusData detectStatusData=new ReadDataBeanList.DetectStatusData();
        String gpsString = parameterData.substring(0,137);
        String initString = parameterData.substring(138,143);
        String timeSyncString = parameterData.substring(144,155);
        ReadDataBeanList.GpsData gpsData = getGpsData(gpsString);
        ReadDataBeanList.InitStatusData initStatusData=getInitStatusData(initString);
        ReadDataBeanList.TimeSyncData timeSyncData=getTimeSyncData(timeSyncString);
        detectStatusData.setGpsData(gpsData);
        detectStatusData.setInitStatusData(initStatusData);
        detectStatusData.setTimeSyncData(timeSyncData);
        return detectStatusData;
    }

    /**
     * 参数表无解释
     * @param parameterData
     * @return
     */
    private ReadDataBeanList.IqData getIqData(String parameterData) {
        ReadDataBeanList.IqData iqData = new ReadDataBeanList.IqData();
        return iqData;
    }

    private ReadDataBeanList.GpsData getGpsData(String parameterData) {
        ReadDataBeanList.GpsData gpsData = new ReadDataBeanList.GpsData();
        String[] gpsDataArray = parameterData.split(" ");
        String altitude = getFloatData(gpsDataArray[0] + " " + gpsDataArray[1] + " " + gpsDataArray[2] + " " + gpsDataArray[3]);
        String legalStar = Integer.parseInt(gpsDataArray[4], 16) + "";
        String direction = getFloatData(gpsDataArray[5] + " " + gpsDataArray[6] + " " + gpsDataArray[7] + " " + gpsDataArray[8]);
        String speed = getFloatData(gpsDataArray[9] + " " + gpsDataArray[10] + " " + gpsDataArray[11] + " " + gpsDataArray[12]);
        String latitudeDirection = (char) Integer.parseInt(gpsDataArray[13], 16) + "";
        String latitude = getLongData(gpsDataArray[14] + " " + gpsDataArray[15] + " " + gpsDataArray[16] + " " + gpsDataArray[17] + " " + gpsDataArray[18] + " " + gpsDataArray[19] + " " + gpsDataArray[20] + " " + gpsDataArray[21]);
        String longitudeDirection = (char) Integer.parseInt(gpsDataArray[22], 16) + "";
        String longitude = getLongData(gpsDataArray[23] + " " + gpsDataArray[24] + " " + gpsDataArray[25] + " " + gpsDataArray[26] + " " + gpsDataArray[27] + " " + gpsDataArray[28] + " " + gpsDataArray[29] + " " + gpsDataArray[30]);
        String locationTime = getTime(gpsDataArray[31] + " " + gpsDataArray[32] + " " + gpsDataArray[33] + " " + gpsDataArray[34] + " " + gpsDataArray[35] + " " + gpsDataArray[36] + " " + gpsDataArray[37]);
        String locationDate = getDate(gpsDataArray[38] + " " + gpsDataArray[39] + " " + gpsDataArray[40] + " " + gpsDataArray[41] + " " + gpsDataArray[42] + " " + gpsDataArray[43] + " " + gpsDataArray[44]);
        String locationLegal = (char) Integer.parseInt(gpsDataArray[45], 16) + "";
        gpsData.setAltitudeData(altitude);
        gpsData.setDirectionData(direction);
        gpsData.setLatitudeData(latitude);
        gpsData.setLongitudeData(longitude);
        gpsData.setLatitudeDirection(latitudeDirection);
        gpsData.setLongitudeDirection(longitudeDirection);
        gpsData.setLegalLocation(locationLegal);
        gpsData.setLegalStarCount(legalStar);
        gpsData.setLocationDate(locationDate);
        gpsData.setLocationTime(locationTime);
        gpsData.setSpeedData(speed);
        return gpsData;
    }

    /**
     * 获取定位时间。
     * 7字节数据，hhmmss
     *
     * @param timeData
     * @return
     */
    private String getTime(String timeData) {
        String time = "";
        return time;
    }

    /**
     * 获取定位日期
     * 7字节数据,ddmmyy
     *
     * @param dateData
     * @return
     */
    private String getDate(String dateData) {
        String date = "";
        return date;
    }

    private ReadDataBeanList.CompassData getCompassData(String compassString) {
        ReadDataBeanList.CompassData compassData = new ReadDataBeanList.CompassData();
        return compassData;
    }

    private ReadDataBeanList.TraceAlarmData getTraceAlarmData(String traceAlarmString) {
        ReadDataBeanList.TraceAlarmData traceAlarmData = new ReadDataBeanList.TraceAlarmData();
        String[] traceAlarmArray = traceAlarmString.split(" ");
        String cmdType = traceAlarmArray[traceAlarmArray.length - 1];
        String directionFrequency = getLongData(traceAlarmArray[traceAlarmArray.length - 9] + " "
                + traceAlarmArray[traceAlarmArray.length - 8] + " "
                + traceAlarmArray[traceAlarmArray.length - 7] + " "
                + traceAlarmArray[traceAlarmArray.length - 6] + " "
                + traceAlarmArray[traceAlarmArray.length - 5] + " "
                + traceAlarmArray[traceAlarmArray.length - 4] + " "
                + traceAlarmArray[traceAlarmArray.length - 3] + " "
                + traceAlarmArray[traceAlarmArray.length - 2]);
        int droneCount = Integer.parseInt("0x" + traceAlarmArray[traceAlarmArray.length - 10] +
                traceAlarmArray[traceAlarmArray.length - 11] +
                traceAlarmArray[traceAlarmArray.length - 12] +
                traceAlarmArray[traceAlarmArray.length - 13], 16);
        List<ReadDataBeanList.TraceAlarmData.DroneData> droneDataList = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < droneCount; i++) {
            String catchFrequecncy = getLongData(traceAlarmArray[traceAlarmArray.length - (21 + index * 28)] + " "
                    + traceAlarmArray[traceAlarmArray.length - (20 + index * 28)] + " "
                    + traceAlarmArray[traceAlarmArray.length - (19 + index * 28)] + " "
                    + traceAlarmArray[traceAlarmArray.length - (18 + index * 28)] + " "
                    + traceAlarmArray[traceAlarmArray.length - (17 + index * 28)] + " "
                    + traceAlarmArray[traceAlarmArray.length - (16 + index * 28)] + " "
                    + traceAlarmArray[traceAlarmArray.length - (15 + index * 28)] + " "
                    + traceAlarmArray[traceAlarmArray.length - (14 + index * 28)]);
            String catchCount = Integer.parseInt("0x" + traceAlarmArray[traceAlarmArray.length - (22 + index * 28)] +
                    traceAlarmArray[traceAlarmArray.length - (23 + index * 28)] +
                    traceAlarmArray[traceAlarmArray.length - (24 + index * 28)] +
                    traceAlarmArray[traceAlarmArray.length - (25 + index * 28)], 16) + "";
            String droneName = (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (26 + index * 28)], 16) + "" +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (27 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (28 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (29 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (30 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (31 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (32 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (33 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (34 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (35 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (36 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (37 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (38 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (39 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (40 + index * 28)], 16) +
                    (char) Integer.parseInt(traceAlarmArray[traceAlarmArray.length - (41 + index * 28)], 16);
            ReadDataBeanList.TraceAlarmData.DroneData droneData = new ReadDataBeanList.TraceAlarmData.DroneData();
            droneData.setCatchCount(catchCount);
            droneData.setCatchFrequency(catchFrequecncy);
            droneData.setDroneTypeData(droneName);
            droneDataList.add(droneData);
            index += 1;
        }
        traceAlarmData.setCommandType(cmdType);
        traceAlarmData.setDirectionFrequency(directionFrequency);
        traceAlarmData.setParameterCount(droneCount + "");
        traceAlarmData.setDroneDataList(droneDataList);
        return traceAlarmData;
    }

    private ReadDataBeanList.FrequencyResultData getFrequencyResultData(String parameterData) {
        ReadDataBeanList.FrequencyResultData frequencyResultData = new ReadDataBeanList.FrequencyResultData();
        String[] parameterArray = parameterData.split(" ");
        String frequencyNumber = parameterArray[parameterArray.length - 20];
        String frequencyCount = parameterArray[parameterArray.length - 19];
        String startIndex = parameterArray[parameterArray.length - 18] + parameterArray[parameterArray.length - 17] + parameterArray[parameterArray.length - 16] + parameterArray[parameterArray.length - 15];
        String frequencyPointCount = parameterArray[parameterArray.length - 14] + parameterArray[parameterArray.length - 13] + parameterArray[parameterArray.length - 12] + parameterArray[parameterArray.length - 11];
        String levelData = parameterArray[parameterArray.length - 2] + parameterArray[parameterArray.length - 1];
        frequencyResultData.setFrequencyCount(frequencyCount);
        frequencyResultData.setFrequencyNumber(frequencyNumber);
        frequencyResultData.setFrequencyPointCount(frequencyPointCount);
        frequencyResultData.setLevelData(levelData);
        frequencyResultData.setStartIndex(startIndex);
        return frequencyResultData;
    }

    private ReadDataBeanList.TraceData getTraceData(String parameterData) {
        ReadDataBeanList.TraceData traceData = new ReadDataBeanList.TraceData();
        return traceData;
    }

    private ReadDataBeanList.DirectionResultData getDirectionResultData(String parameterData) {
        ReadDataBeanList.DirectionResultData directionResultData = new ReadDataBeanList.DirectionResultData();
        String[] parameterArray = parameterData.split(" ");
        String directionFrequency = getLongData(parameterArray[parameterArray.length - 13] + " " + parameterArray[parameterArray.length - 14] + " " + parameterArray[parameterArray.length - 15] + " " + parameterArray[parameterArray.length - 16] + parameterArray[parameterArray.length - 17] + " " + parameterArray[parameterArray.length - 18] + " " + parameterArray[parameterArray.length - 19] + " " + parameterArray[parameterArray.length - 20]);
        String directionAngle = getFloatData(parameterArray[parameterArray.length - 9] + " " + parameterArray[parameterArray.length - 10] + " " + parameterArray[parameterArray.length - 11] + " " + parameterArray[parameterArray.length - 12]);
        String signalStrength = getFloatData(parameterArray[parameterArray.length - 5] + " " + parameterArray[parameterArray.length - 6] + " " + parameterArray[parameterArray.length - 7] + " " + parameterArray[parameterArray.length - 8]);
        String compassData = getFloatData(parameterArray[parameterArray.length - 1] + " " + parameterArray[parameterArray.length - 2] + " " + parameterArray[parameterArray.length - 3] + " " + parameterArray[parameterArray.length - 4]);
        directionResultData.setCompassData(compassData);
        directionResultData.setDirectionAngle(directionAngle);
        directionResultData.setDirectionFrequency(directionFrequency);
        directionResultData.setSignalStrength(signalStrength);
        return directionResultData;
    }

    private ReadDataBeanList.DirectionLost getDirectionLost(String parameterData) {
        ReadDataBeanList.DirectionLost directionLost = new ReadDataBeanList.DirectionLost();
        return directionLost;
    }

    private ReadDataBeanList.InitStatusData getInitStatusData(String parameterData) {
        ReadDataBeanList.InitStatusData initStatusData = new ReadDataBeanList.InitStatusData();
        String[] parameterArray = parameterData.split(" ");
        String initProgress = Integer.parseInt(parameterArray[parameterArray.length - 2] + parameterArray[parameterArray.length - 1], 16) + "";
        initStatusData.setInitProgress(initProgress);
        return initStatusData;
    }

    public static void main(String[] args){
        SetJsonBeanData setJsonBeanData=new SetJsonBeanData();
        String value="29 91 44 2F A3 14 3A 40 2D 06 0F D3 3E D1 5D 40 6F 52 A3 42 0A D7 23 3C 7B 14 2E BE 29 5C 8F BD 00 00 00 00 10 00 00 00 14 00 00 00";
        String value2="0A D7 23 3C 7B 14 2E BE 29 5C 8F BD";
        //setJsonBeanData.getLocationData(value2);
        setJsonBeanData.getSystemStatus(value);
        //System.out.print();


    }

}
