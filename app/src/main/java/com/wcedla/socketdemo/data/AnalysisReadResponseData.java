package com.wcedla.socketdemo.data;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wcedla.socketdemo.utils.UdpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wcedla, Email wcedla@live.com, Date on 2019/8/19.
 * PS: The code may be millions of lines,but remember comment first please
 */
public class AnalysisReadResponseData {

    private static AnalysisReadResponseData analysisReadResponseData;
    private byte[] responseByteArray;

    public static AnalysisReadResponseData getInstance(byte[] responseByteArray) {
        if (analysisReadResponseData == null) {
            synchronized (AnalysisReadResponseData.class) {
                if (analysisReadResponseData == null) {
                    analysisReadResponseData = new AnalysisReadResponseData();
                }
            }
        }
        analysisReadResponseData.responseByteArray = responseByteArray;
        return analysisReadResponseData;
    }

    public String analysis() {
        if (responseByteArray != null && responseByteArray.length > 0) {
            try {
                String readDataJson = "";
                String responseString = parseHexString(responseByteArray);
                String[] responseDataArray = responseString.split(" ");
                if (responseDataArray.length > 0) {
                    //帧体用于计算CRC值的部分
                    String crcOriginString = responseString.substring(45, responseString.length() - 7);
                    //直接读取返回字符串最后两个字节的值
                    String originCrc = responseString.substring(responseString.length() - 6, responseString.length() - 1).toUpperCase();
                    //使用方法计算crc的值
                    String nowCrc = UdpUtils.getInstance().getParameterCrc(crcOriginString).toUpperCase();
                    Logger.d("读响应体crc校验值（字符串|计算得到）:" + originCrc + "," + nowCrc);
                    ReadDataJsonBean readDataJsonBean = new ReadDataJsonBean();
                    String deviceNumber;
                    String fpgaVersion;
                    String softwareVersion;
                    String dataLength;
                    int frameLength;
                    boolean isLegal = originCrc.equals(nowCrc) && ("25".equals(responseDataArray[0]) && ("03".equals(responseDataArray[16]) || "10".equals(responseDataArray[16]) || "90".equals(responseDataArray[16])));
                    if (isLegal) {
                        deviceNumber = responseDataArray[8] + responseDataArray[7] + responseDataArray[6] + responseDataArray[5] + responseDataArray[4] + responseDataArray[3] + responseDataArray[2] + responseDataArray[1];
                        fpgaVersion = Integer.parseInt(responseDataArray[9], 16) + "." + Integer.parseInt(responseDataArray[10], 16);
                        softwareVersion = Integer.parseInt(responseDataArray[11], 16) + "." + Integer.parseInt(responseDataArray[12], 16);
                        //多个参数加起来的长度
                        int length = Integer.parseInt(responseDataArray[18] + responseDataArray[17], 16);
                        dataLength = String.valueOf(length);
                        //单帧总长度
                        frameLength = length + 21;
                        //当前索引指针位置
                        int nowIndex = 19;
                        List<ParameterData> parameterDataList=new ArrayList<>();
                        //如果当前索引位置小于crc前一位（也就是参数数据的最后一个）就执行方法体
                        while (nowIndex < frameLength - 2) {
                            //当前参数的值的长度
                            String parameterCode=responseDataArray[nowIndex + 1] + responseDataArray[nowIndex];
                            int paraLength = Integer.parseInt(responseDataArray[nowIndex + 2], 16);
                            StringBuilder parameterValueBuilder = new StringBuilder();
                            for (int j = 0; j <paraLength; j++) {
                                parameterValueBuilder.append(responseDataArray[nowIndex + 3 + j]);
                                if (j != paraLength-1) {
                                    parameterValueBuilder.append(" ");
                                }
                            }
                            parameterDataList.add(new ParameterData(parameterValueBuilder.toString(),parameterCode,paraLength));
                            //readDataParameterList.add(new ReadDataJsonBean.ReadDataParameterBean(responseDataArray[nowIndex + 1] + responseDataArray[nowIndex], paraLength + "", parameterValueBuilder.toString()));
                            nowIndex += paraLength + 3;
                        }
                        readDataJsonBean.setDeviceNumber(deviceNumber);
                        readDataJsonBean.setFPGAVersion(fpgaVersion);
                        readDataJsonBean.setSoftwareVersion(softwareVersion);
                        readDataJsonBean.setDataLength(dataLength);
                        readDataJsonBean.setParameterDataClass(SetJsonBeanData.getInstance().analysisParameter(parameterDataList));
                        readDataJson = new Gson().toJson(readDataJsonBean, ReadDataJsonBean.class);
                        Logger.d("读命令返回数据解析json：" + readDataJson);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public String parseHexString(byte[] b) {
        String hexString = "";
        for (byte value : b) {
            String hex = Integer.toHexString(value & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString += hex.toUpperCase() + " ";
        }

        return hexString;
    }
}
