package com.wcedla.socketdemo.data;

public class ReadDataParameter {

    private String parameterCode;

    private String parameterLength;

    private String parameterData;

    public ReadDataParameter(String parameterCode, String parameterLength, String parameterData) {
        this.parameterCode = parameterCode;
        this.parameterLength = parameterLength;
        this.parameterData = parameterData;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public String getParameterLength() {
        return parameterLength;
    }

    public void setParameterLength(String parameterLength) {
        this.parameterLength = parameterLength;
    }

    public String getParameterData() {
        return parameterData;
    }

    public void setParameterData(String parameterData) {
        this.parameterData = parameterData;
    }
}
