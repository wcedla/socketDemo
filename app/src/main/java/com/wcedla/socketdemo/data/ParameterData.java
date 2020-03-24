package com.wcedla.socketdemo.data;

/**
 * @author wcedla, Email wcedla@live.com, Date on 2019/8/27.
 * PS: The code may be millions of lines,but remember comment first please
 *
 * 用于解析读命令数据分割参数时记录参数的编号，参数值以及参数长度
 *
 */
public class ParameterData {

    private String parameterName;

    private String parameterCode;

    private int getParameterLength;

    public ParameterData(String parameterName, String parameterCode, int getParameterLength) {
        this.parameterName = parameterName;
        this.parameterCode = parameterCode;
        this.getParameterLength = getParameterLength;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public int getGetParameterLength() {
        return getParameterLength;
    }

    public void setGetParameterLength(int getParameterLength) {
        this.getParameterLength = getParameterLength;
    }
}
