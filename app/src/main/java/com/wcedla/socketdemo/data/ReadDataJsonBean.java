package com.wcedla.socketdemo.data;

import com.wcedla.socketdemo.data.ReadDataBeanList;

/**
 * @author wcedla, Email wcedla@live.com, Date on 2019/8/27.
 * PS: The code may be millions of lines,but remember comment first please
 *
 * 用于生成读命令返回JSON数据的bean
 *
 */

public class ReadDataJsonBean {

    private String deviceNumber;

    private String FPGAVersion;

    private String softwareVersion;

    private String dataLength;

    private ParameterDataClass parameterDataClass;

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getFPGAVersion() {
        return FPGAVersion;
    }

    public void setFPGAVersion(String FPGAVersion) {
        this.FPGAVersion = FPGAVersion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public ParameterDataClass getParameterDataClass() {
        return parameterDataClass;
    }

    public void setParameterDataClass(ParameterDataClass parameterDataClass) {
        this.parameterDataClass = parameterDataClass;
    }

    public static class ParameterDataClass {

        private ReadDataBeanList.DeviceNumberData deviceNumberData;

        private ReadDataBeanList.FpgaVersionData fpgaVersionData;

        private ReadDataBeanList.ArmVersionData armVersionData;

        private ReadDataBeanList.EnvironmentTemperatureData environmentTemperatureData;

        private ReadDataBeanList.CpuUsageData cpuUsageData;

        private ReadDataBeanList.FpgaUsuageData fpgaUsuageData;

        private ReadDataBeanList.LocationData locationData;

        private ReadDataBeanList.StarTimeData starTimeData;

        private ReadDataBeanList.StatusUploadData statusUploadData;

        private ReadDataBeanList.LocationSpeedData locationSpeedData;

        private ReadDataBeanList.TimeSyncData timeSyncData;

        private ReadDataBeanList.JzWorkStatusData jzWorkStatusData;

        private ReadDataBeanList.GpsStatusData gpsStatusData;

        private ReadDataBeanList.GlonassStatusData glonassStatusData;

        private ReadDataBeanList.SystemStatus systemStatus;

        private ReadDataBeanList.DetectStatusData detectStatusData;

        private ReadDataBeanList.IqData iqData;

        private ReadDataBeanList.GpsData gpsData;

        private ReadDataBeanList.CompassData compassData;

        private ReadDataBeanList.TraceAlarmData traceAlarmData;

        private ReadDataBeanList.FrequencyResultData frequencyResultData;

        private ReadDataBeanList.TraceData traceData;

        private ReadDataBeanList.DirectionResultData directionResultData;

        private ReadDataBeanList.DirectionLost directionLost;

        private ReadDataBeanList.InitStatusData initStatusData;

        public ReadDataBeanList.DeviceNumberData getDeviceNumberData() {
            return deviceNumberData;
        }

        public void setDeviceNumberData(ReadDataBeanList.DeviceNumberData deviceNumberData) {
            this.deviceNumberData = deviceNumberData;
        }

        public ReadDataBeanList.FpgaVersionData getFpgaVersionData() {
            return fpgaVersionData;
        }

        public void setFpgaVersionData(ReadDataBeanList.FpgaVersionData fpgaVersionData) {
            this.fpgaVersionData = fpgaVersionData;
        }

        public ReadDataBeanList.ArmVersionData getArmVersionData() {
            return armVersionData;
        }

        public void setArmVersionData(ReadDataBeanList.ArmVersionData armVersionData) {
            this.armVersionData = armVersionData;
        }

        public ReadDataBeanList.EnvironmentTemperatureData getEnvironmentTemperatureData() {
            return environmentTemperatureData;
        }

        public void setEnvironmentTemperatureData(ReadDataBeanList.EnvironmentTemperatureData environmentTemperatureData) {
            this.environmentTemperatureData = environmentTemperatureData;
        }

        public ReadDataBeanList.CpuUsageData getCpuUsageData() {
            return cpuUsageData;
        }

        public void setCpuUsageData(ReadDataBeanList.CpuUsageData cpuUsageData) {
            this.cpuUsageData = cpuUsageData;
        }

        public ReadDataBeanList.FpgaUsuageData getFpgaUsuageData() {
            return fpgaUsuageData;
        }

        public void setFpgaUsuageData(ReadDataBeanList.FpgaUsuageData fpgaUsuageData) {
            this.fpgaUsuageData = fpgaUsuageData;
        }

        public ReadDataBeanList.LocationData getLocationData() {
            return locationData;
        }

        public void setLocationData(ReadDataBeanList.LocationData locationData) {
            this.locationData = locationData;
        }

        public ReadDataBeanList.StarTimeData getStarTimeData() {
            return starTimeData;
        }

        public void setStarTimeData(ReadDataBeanList.StarTimeData starTimeData) {
            this.starTimeData = starTimeData;
        }

        public ReadDataBeanList.StatusUploadData getStatusUploadData() {
            return statusUploadData;
        }

        public void setStatusUploadData(ReadDataBeanList.StatusUploadData statusUploadData) {
            this.statusUploadData = statusUploadData;
        }

        public ReadDataBeanList.LocationSpeedData getLocationSpeedData() {
            return locationSpeedData;
        }

        public void setLocationSpeedData(ReadDataBeanList.LocationSpeedData locationSpeedData) {
            this.locationSpeedData = locationSpeedData;
        }

        public ReadDataBeanList.TimeSyncData getTimeSyncData() {
            return timeSyncData;
        }

        public void setTimeSyncData(ReadDataBeanList.TimeSyncData timeSyncData) {
            this.timeSyncData = timeSyncData;
        }

        public ReadDataBeanList.JzWorkStatusData getJzWorkStatusData() {
            return jzWorkStatusData;
        }

        public void setJzWorkStatusData(ReadDataBeanList.JzWorkStatusData jzWorkStatusData) {
            this.jzWorkStatusData = jzWorkStatusData;
        }

        public ReadDataBeanList.GpsStatusData getGpsStatusData() {
            return gpsStatusData;
        }

        public void setGpsStatusData(ReadDataBeanList.GpsStatusData gpsStatusData) {
            this.gpsStatusData = gpsStatusData;
        }

        public ReadDataBeanList.GlonassStatusData getGlonassStatusData() {
            return glonassStatusData;
        }

        public void setGlonassStatusData(ReadDataBeanList.GlonassStatusData glonassStatusData) {
            this.glonassStatusData = glonassStatusData;
        }

        public ReadDataBeanList.SystemStatus getSystemStatus() {
            return systemStatus;
        }

        public void setSystemStatus(ReadDataBeanList.SystemStatus systemStatus) {
            this.systemStatus = systemStatus;
        }

        public ReadDataBeanList.DetectStatusData getDetectStatusData() {
            return detectStatusData;
        }

        public void setDetectStatusData(ReadDataBeanList.DetectStatusData detectStatusData) {
            this.detectStatusData = detectStatusData;
        }

        public ReadDataBeanList.IqData getIqData() {
            return iqData;
        }

        public void setIqData(ReadDataBeanList.IqData iqData) {
            this.iqData = iqData;
        }

        public ReadDataBeanList.GpsData getGpsData() {
            return gpsData;
        }

        public void setGpsData(ReadDataBeanList.GpsData gpsData) {
            this.gpsData = gpsData;
        }

        public ReadDataBeanList.CompassData getCompassData() {
            return compassData;
        }

        public void setCompassData(ReadDataBeanList.CompassData compassData) {
            this.compassData = compassData;
        }

        public ReadDataBeanList.TraceAlarmData getTraceAlarmData() {
            return traceAlarmData;
        }

        public void setTraceAlarmData(ReadDataBeanList.TraceAlarmData traceAlarmData) {
            this.traceAlarmData = traceAlarmData;
        }

        public ReadDataBeanList.FrequencyResultData getFrequencyResultData() {
            return frequencyResultData;
        }

        public void setFrequencyResultData(ReadDataBeanList.FrequencyResultData frequencyResultData) {
            this.frequencyResultData = frequencyResultData;
        }

        public ReadDataBeanList.TraceData getTraceData() {
            return traceData;
        }

        public void setTraceData(ReadDataBeanList.TraceData traceData) {
            this.traceData = traceData;
        }

        public ReadDataBeanList.DirectionResultData getDirectionResultData() {
            return directionResultData;
        }

        public void setDirectionResultData(ReadDataBeanList.DirectionResultData directionResultData) {
            this.directionResultData = directionResultData;
        }

        public ReadDataBeanList.DirectionLost getDirectionLost() {
            return directionLost;
        }

        public void setDirectionLost(ReadDataBeanList.DirectionLost directionLost) {
            this.directionLost = directionLost;
        }

        public ReadDataBeanList.InitStatusData getInitStatusData() {
            return initStatusData;
        }

        public void setInitStatusData(ReadDataBeanList.InitStatusData initStatusData) {
            this.initStatusData = initStatusData;
        }
    }
}
