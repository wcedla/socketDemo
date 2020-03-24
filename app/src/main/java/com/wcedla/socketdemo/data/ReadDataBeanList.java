package com.wcedla.socketdemo.data;

import java.util.List;

/**
 * @author wcedla, Email wcedla@live.com, Date on 2019/8/20.
 * PS: The code may be millions of lines,but remember comment first please
 */
public class ReadDataBeanList {

    /**
     * 设备编号解析类
     */
    public static class DeviceNumberData {

        private String deviceNumber;

        public String getDeviceNumber() {
            return deviceNumber;
        }

        public void setDeviceNumber(String deviceNumber) {
            this.deviceNumber = deviceNumber;
        }
    }

    /**
     * FPGA版本号解析类
     */
    public static class FpgaVersionData {

        private String fpgaVersion;

        public String getFpgaVersion() {
            return fpgaVersion;
        }

        public void setFpgaVersion(String fpgaVersion) {
            this.fpgaVersion = fpgaVersion;
        }
    }

    /**
     * ARM版本号解析类
     */
    public static class ArmVersionData {

        private String armVersion;

        public String getArmVersion() {
            return armVersion;
        }

        public void setArmVersion(String armVersion) {
            this.armVersion = armVersion;
        }
    }

    /**
     * 环境温度解析类
     */
    public static class EnvironmentTemperatureData {

        private String environmentTemperatur;

        public String getEnvironmentTemperatur() {
            return environmentTemperatur;
        }

        public void setEnvironmentTemperatur(String environmentTemperatur) {
            this.environmentTemperatur = environmentTemperatur;
        }
    }

    /**
     * CPU使用率解析类
     */
    public static class CpuUsageData {

        private String cpuUsage;

        public String getCpuUsage() {
            return cpuUsage;
        }

        public void setCpuUsage(String cpuUsage) {
            this.cpuUsage = cpuUsage;
        }
    }

    /**
     * FPGA发射通道使用率
     */
    public static class FpgaUsuageData {

        private String fpgaUsuage;

        public String getFpgaUsuage() {
            return fpgaUsuage;
        }

        public void setFpgaUsuage(String fpgaUsuage) {
            this.fpgaUsuage = fpgaUsuage;
        }
    }

    /**
     * 定位位置解析类
     */
    public static class LocationData {

        /**
         * 经度，double,8字节
         */
        private String longitude;

        /**
         * 纬度，double,8字节
         */
        private String latitude;

        /**
         * 高度,int,4字节
         */
        private String height;

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }

    /**
     * 授时时间解析类
     */
    public static class StarTimeData {

        private String starTime;

        public String getStarTime() {
            return starTime;
        }

        public void setStarTime(String starTime) {
            this.starTime = starTime;
        }
    }

    /**
     * 状态上报解析类
     */
    public static class StatusUploadData {

        private String statusUpload;

        public String getStatusUpload() {
            return statusUpload;
        }

        public void setStatusUpload(String statusUpload) {
            this.statusUpload = statusUpload;
        }
    }

    /**
     * 定位速度解析类
     */
    public static class LocationSpeedData {

        /**
         * x轴方向速度，单位m/s
         */
        private String xSpeed;

        /**
         * y轴方向速度，单位m/s
         */
        private String ySpeed;

        /**
         * z轴方向速度，单位m/s
         */
        private String zSpeed;

        public String getxSpeed() {
            return xSpeed;
        }

        public void setxSpeed(String xSpeed) {
            this.xSpeed = xSpeed;
        }

        public String getySpeed() {
            return ySpeed;
        }

        public void setySpeed(String ySpeed) {
            this.ySpeed = ySpeed;
        }

        public String getzSpeed() {
            return zSpeed;
        }

        public void setzSpeed(String zSpeed) {
            this.zSpeed = zSpeed;
        }
    }

    /**
     * 授时同步状态解析类
     */
    public static class TimeSyncData {

        private String workStatus;

        private String locationStatus;

        private String secondStatus;

        private String syncStatus;

        private String ntpStatus;

        private String starReceiveComplete;

        private String bookReceiverComplete;

        public String getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(String workStatus) {
            this.workStatus = workStatus;
        }

        public String getLocationStatus() {
            return locationStatus;
        }

        public void setLocationStatus(String locationStatus) {
            this.locationStatus = locationStatus;
        }

        public String getSecondStatus() {
            return secondStatus;
        }

        public void setSecondStatus(String secondStatus) {
            this.secondStatus = secondStatus;
        }

        public String getSyncStatus() {
            return syncStatus;
        }

        public void setSyncStatus(String syncStatus) {
            this.syncStatus = syncStatus;
        }

        public String getNtpStatus() {
            return ntpStatus;
        }

        public void setNtpStatus(String ntpStatus) {
            this.ntpStatus = ntpStatus;
        }

        public String getStarReceiveComplete() {
            return starReceiveComplete;
        }

        public void setStarReceiveComplete(String starReceiveComplete) {
            this.starReceiveComplete = starReceiveComplete;
        }

        public String getBookReceiverComplete() {
            return bookReceiverComplete;
        }

        public void setBookReceiverComplete(String bookReceiverComplete) {
            this.bookReceiverComplete = bookReceiverComplete;
        }
    }

    /**
     * 晶振工作状态解析类
     */
    public static class JzWorkStatusData {

        private String jzWorkStatus;

        public String getJzWorkStatus() {
            return jzWorkStatus;
        }

        public void setJzWorkStatus(String jzWorkStatus) {
            this.jzWorkStatus = jzWorkStatus;
        }
    }

    /**
     * GPS伪卫星状态解析类
     */
    public static class GpsStatusData {

        private String clockStatus;

        private String codeBStatus;

        private String radioStatus;

        private String shotStatus;

        public String getClockStatus() {
            return clockStatus;
        }

        public void setClockStatus(String clockStatus) {
            this.clockStatus = clockStatus;
        }

        public String getCodeBStatus() {
            return codeBStatus;
        }

        public void setCodeBStatus(String codeBStatus) {
            this.codeBStatus = codeBStatus;
        }

        public String getRadioStatus() {
            return radioStatus;
        }

        public void setRadioStatus(String radioStatus) {
            this.radioStatus = radioStatus;
        }

        public String getShotStatus() {
            return shotStatus;
        }

        public void setShotStatus(String shotStatus) {
            this.shotStatus = shotStatus;
        }
    }

    /**
     * GLONASS伪卫星状态解析类
     */
    public static class GlonassStatusData {

        private String clockStatus;

        private String codeBStatus;

        private String radioStatus;

        private String gnssStatus;

        private String shotStatus;

        public String getClockStatus() {
            return clockStatus;
        }

        public void setClockStatus(String clockStatus) {
            this.clockStatus = clockStatus;
        }

        public String getCodeBStatus() {
            return codeBStatus;
        }

        public void setCodeBStatus(String codeBStatus) {
            this.codeBStatus = codeBStatus;
        }

        public String getRadioStatus() {
            return radioStatus;
        }

        public void setRadioStatus(String radioStatus) {
            this.radioStatus = radioStatus;
        }

        public String getGnssStatus() {
            return gnssStatus;
        }

        public void setGnssStatus(String gnssStatus) {
            this.gnssStatus = gnssStatus;
        }

        public String getShotStatus() {
            return shotStatus;
        }

        public void setShotStatus(String shotStatus) {
            this.shotStatus = shotStatus;
        }
    }

    /**
     * 系统状态信息解析类
     */
    public static class SystemStatus {

        private LocationData locationData;

        private LocationSpeedData locationSpeedData;

        private TimeSyncData timeSyncData;

        private GpsStatusData gpsStatusData;

        private GlonassStatusData glonassStatusData;

        public LocationData getLocationData() {
            return locationData;
        }

        public void setLocationData(LocationData locationData) {
            this.locationData = locationData;
        }

        public LocationSpeedData getLocationSpeedData() {
            return locationSpeedData;
        }

        public void setLocationSpeedData(LocationSpeedData locationSpeedData) {
            this.locationSpeedData = locationSpeedData;
        }

        public TimeSyncData getTimeSyncData() {
            return timeSyncData;
        }

        public void setTimeSyncData(TimeSyncData timeSyncData) {
            this.timeSyncData = timeSyncData;
        }

        public GpsStatusData getGpsStatusData() {
            return gpsStatusData;
        }

        public void setGpsStatusData(GpsStatusData gpsStatusData) {
            this.gpsStatusData = gpsStatusData;
        }

        public GlonassStatusData getGlonassStatusData() {
            return glonassStatusData;
        }

        public void setGlonassStatusData(GlonassStatusData glonassStatusData) {
            this.glonassStatusData = glonassStatusData;
        }
    }

    /**
     * 探测设备状态解析类
     */
    public static class DetectStatusData {

        /**
         * Gps数据
         */
        private GpsData gpsData;

        /**
         * 初始化状态
         */
        private InitStatusData initStatusData;

        /**
         * 授时同步状态
         */
        private TimeSyncData timeSyncData;

        public GpsData getGpsData() {
            return gpsData;
        }

        public void setGpsData(GpsData gpsData) {
            this.gpsData = gpsData;
        }

        public InitStatusData getInitStatusData() {
            return initStatusData;
        }

        public void setInitStatusData(InitStatusData initStatusData) {
            this.initStatusData = initStatusData;
        }

        public TimeSyncData getTimeSyncData() {
            return timeSyncData;
        }

        public void setTimeSyncData(TimeSyncData timeSyncData) {
            this.timeSyncData = timeSyncData;
        }
    }

    /**
     * 原始IQ数据解析类
     */
    public static class IqData {

    }

    /**
     * GPS数据解析类
     */
    public static class GpsData {

        private String legalLocation;

        private String locationDate;

        private String locationTime;

        private String longitudeData;

        private String longitudeDirection;

        private String latitudeData;

        private String latitudeDirection;

        private String speedData;

        private String directionData;

        private String legalStarCount;

        private String altitudeData;

        public String getLegalLocation() {
            return legalLocation;
        }

        public void setLegalLocation(String legalLocation) {
            this.legalLocation = legalLocation;
        }

        public String getLocationDate() {
            return locationDate;
        }

        public void setLocationDate(String locationDate) {
            this.locationDate = locationDate;
        }

        public String getLocationTime() {
            return locationTime;
        }

        public void setLocationTime(String locationTime) {
            this.locationTime = locationTime;
        }

        public String getLongitudeData() {
            return longitudeData;
        }

        public void setLongitudeData(String longitudeData) {
            this.longitudeData = longitudeData;
        }

        public String getLongitudeDirection() {
            return longitudeDirection;
        }

        public void setLongitudeDirection(String longitudeDirection) {
            this.longitudeDirection = longitudeDirection;
        }

        public String getLatitudeData() {
            return latitudeData;
        }

        public void setLatitudeData(String latitudeData) {
            this.latitudeData = latitudeData;
        }

        public String getLatitudeDirection() {
            return latitudeDirection;
        }

        public void setLatitudeDirection(String latitudeDirection) {
            this.latitudeDirection = latitudeDirection;
        }

        public String getSpeedData() {
            return speedData;
        }

        public void setSpeedData(String speedData) {
            this.speedData = speedData;
        }

        public String getDirectionData() {
            return directionData;
        }

        public void setDirectionData(String directionData) {
            this.directionData = directionData;
        }

        public String getLegalStarCount() {
            return legalStarCount;
        }

        public void setLegalStarCount(String legalStarCount) {
            this.legalStarCount = legalStarCount;
        }

        public String getAltitudeData() {
            return altitudeData;
        }

        public void setAltitudeData(String altitudeData) {
            this.altitudeData = altitudeData;
        }
    }

    /**
     * 电子罗盘数据解析类
     */
    public static class CompassData {

    }

    /**
     * 监测告警解析类
     */
    public static class TraceAlarmData {

        private String commandType;

        private String directionFrequency;

        private String parameterCount;

        private List<DroneData> droneDataList;

        public String getCommandType() {
            return commandType;
        }

        public void setCommandType(String commandType) {
            this.commandType = commandType;
        }

        public String getDirectionFrequency() {
            return directionFrequency;
        }

        public void setDirectionFrequency(String directionFrequency) {
            this.directionFrequency = directionFrequency;
        }

        public String getParameterCount() {
            return parameterCount;
        }

        public void setParameterCount(String parameterCount) {
            this.parameterCount = parameterCount;
        }

        public List<DroneData> getDroneDataList() {
            return droneDataList;
        }

        public void setDroneDataList(List<DroneData> droneDataList) {
            this.droneDataList = droneDataList;
        }

        public static class DroneData{

            private String catchFrequency;

            private String catchCount;

            private String droneTypeData;

            public String getCatchFrequency() {
                return catchFrequency;
            }

            public void setCatchFrequency(String catchFrequency) {
                this.catchFrequency = catchFrequency;
            }

            public String getCatchCount() {
                return catchCount;
            }

            public void setCatchCount(String catchCount) {
                this.catchCount = catchCount;
            }

            public String getDroneTypeData() {
                return droneTypeData;
            }

            public void setDroneTypeData(String droneTypeData) {
                this.droneTypeData = droneTypeData;
            }
        }
    }

    /**
     * 频谱结果数据解析类(未使用)
     */
    public static class FrequencyResultData {

        private String frequencyNumber;

        private String frequencyCount;

        private String startIndex;

        private String frequencyPointCount;

        private String levelData;

        public String getFrequencyNumber() {
            return frequencyNumber;
        }

        public void setFrequencyNumber(String frequencyNumber) {
            this.frequencyNumber = frequencyNumber;
        }

        public String getFrequencyCount() {
            return frequencyCount;
        }

        public void setFrequencyCount(String frequencyCount) {
            this.frequencyCount = frequencyCount;
        }

        public String getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(String startIndex) {
            this.startIndex = startIndex;
        }

        public String getFrequencyPointCount() {
            return frequencyPointCount;
        }

        public void setFrequencyPointCount(String frequencyPointCount) {
            this.frequencyPointCount = frequencyPointCount;
        }

        public String getLevelData() {
            return levelData;
        }

        public void setLevelData(String levelData) {
            this.levelData = levelData;
        }
    }

    /**
     * 跟踪数据解析类
     */
    public static class TraceData {

    }

    /**
     * 测向结果数据解析类
     */
    public static class DirectionResultData {

        private String directionFrequency;

        private String directionAngle;

        private String signalStrength;

        private String compassData;

        public String getDirectionFrequency() {
            return directionFrequency;
        }

        public void setDirectionFrequency(String directionFrequency) {
            this.directionFrequency = directionFrequency;
        }

        public String getDirectionAngle() {
            return directionAngle;
        }

        public void setDirectionAngle(String directionAngle) {
            this.directionAngle = directionAngle;
        }

        public String getSignalStrength() {
            return signalStrength;
        }

        public void setSignalStrength(String signalStrength) {
            this.signalStrength = signalStrength;
        }

        public String getCompassData() {
            return compassData;
        }

        public void setCompassData(String compassData) {
            this.compassData = compassData;
        }
    }

    /**
     * 测向信号丢失解析类
     */
    public static class DirectionLost {

    }

    /**
     * 初始化状态解析类
     */
    public static class InitStatusData {

        private String initProgress;

        public String getInitProgress() {
            return initProgress;
        }

        public void setInitProgress(String initProgress) {
            this.initProgress = initProgress;
        }
    }


}
