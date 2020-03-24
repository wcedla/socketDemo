package com.wcedla.socketdemo.utils;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;
import com.wcedla.socketdemo.data.SocketData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.wcedla.socketdemo.data.SocketData.RECEIVE_MAX_LENGTH;
import static com.wcedla.socketdemo.data.SocketData.UDP_RESPONSE_TIMEOUT;

/**
 * @author wcedla
 * 2019-08-13
 */
public class UdpUtils {

    private volatile static UdpUtils udpUtils = null;

    private volatile DatagramSocket datagramSocket = null;

    private String ip = Hawk.get("ip", SocketData.IP);

    private int port = Hawk.get("port", SocketData.PORT);

    private volatile boolean stopReceive = false;

    private volatile boolean dataReceive = false;

    private volatile boolean dataSend = false;

    private byte[] receiveBytes = null;

    private volatile List<byte[]> sendList = new ArrayList<>();

    private volatile long currentTimeMillis = 0;

    private UdpStatusListener udpStatusListener;

    private final Object sendLock = new Object();

    private ThreadPoolExecutor udpThreadPoolExecutor = new ThreadPoolExecutor(2, 2, 2000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1024), r -> {
        Thread thread = new Thread(r);
        thread.setName("UdpSocketUtils");
        return thread;
    });

    @NonNull
    public static UdpUtils getInstance() {
        if (udpUtils == null) {
            synchronized (UdpUtils.class) {
                if (udpUtils == null) {
                    udpUtils = new UdpUtils();
                }
            }
        }
        return udpUtils;
    }

    @Nullable
    public synchronized DatagramSocket getUdpSocket() {
        return datagramSocket;
    }

    public synchronized void initUdpSocket(@NonNull String ip, int port, @NonNull UdpStatusListener udpStatusListener) {
        if (datagramSocket == null) {
            this.ip = ip;
            this.port = port;
            Hawk.put("ip", ip);
            Hawk.put("port", port);
            this.udpStatusListener = udpStatusListener;
            try {
                datagramSocket = new DatagramSocket(port);
                //设备（圆球）应答不得超过1秒
                datagramSocket.setSoTimeout(UDP_RESPONSE_TIMEOUT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //重置结束接收数据的标志位
        stopReceive = false;
        if (!dataReceive) {
            startReceiveData();
        }
    }

    public synchronized void initUdpSocket(@NonNull UdpStatusListener udpStatusListener) {
        if (datagramSocket == null) {
            this.udpStatusListener = udpStatusListener;
            try {
                datagramSocket = new DatagramSocket(port);
                //设备（圆球）应答不得超过1秒
                datagramSocket.setSoTimeout(UDP_RESPONSE_TIMEOUT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //重置结束接收数据的标志位
        stopReceive = false;
        if (!dataReceive) {
            startReceiveData();
        }
    }

    private synchronized void startReceiveData() {
        if (datagramSocket != null) {
            udpThreadPoolExecutor.execute(() -> {
                //接收数据，接收的数据长度不超过RECEIVE_MAX_LENGTH--->2000
                receiveBytes = new byte[RECEIVE_MAX_LENGTH];
                DatagramPacket receiverPacket = new DatagramPacket(receiveBytes, receiveBytes.length);
                while (!stopReceive) {
                    dataReceive = true;
                    try {
                        datagramSocket.receive(receiverPacket);
                        if (udpStatusListener != null && receiverPacket.getLength() > 0) {
                            udpStatusListener.receivedMessage(subByte(receiverPacket.getData(), receiverPacket.getLength()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (udpStatusListener != null) {
                            udpStatusListener.receivedMessageFailed();
                        }
                    }
                }
                Thread nowThread = Thread.currentThread();
                Logger.d("UDP接收停止，当前运行的线程的名字:" + nowThread.getName());
                if ("UdpSocketUtils".equals(nowThread.getName())) {
                    nowThread.interrupt();
                }
                dataReceive = false;
            });
        }
    }

    public void sendData(@NonNull byte[] sendDataBuf) {
        if (datagramSocket != null) {
            //这个锁是为了及时通知线程阻塞的wait有数据进来了，停止阻塞，继续while循环发送下一个数据
            synchronized (sendLock) {
                sendList.add(sendDataBuf);
                sendLock.notify();
            }
            if (!dataSend) {
                //这个锁是为了多个线程同时请求发送数据时不会造成发送错乱的问题
                synchronized (UdpUtils.class) {
                    udpThreadPoolExecutor.execute(() -> {
                        long nowTimeMillis;
                        InetAddress droneAddress;
                        DatagramPacket datagramPacket;
                        while (!sendList.isEmpty()) {
                            dataSend = true;
                            nowTimeMillis = System.currentTimeMillis();
                            if (currentTimeMillis == 0 || nowTimeMillis - currentTimeMillis > 1000) {
                                try {
                                    droneAddress = InetAddress.getByName(ip);
                                    datagramPacket = new DatagramPacket(sendList.get(0), sendList.get(0).length, droneAddress, port);
                                    datagramSocket.send(datagramPacket);
                                    Logger.d("数据已发送！");
                                    if (udpStatusListener != null) {
                                        udpStatusListener.sendSucceed();
                                    }
                                    sendList.remove(0);
                                    currentTimeMillis = System.currentTimeMillis();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    if (udpStatusListener != null) {
                                        udpStatusListener.sendFailed();
                                    }
                                    Logger.d("数据发送失败！");
                                }
                                //发送完第一个消息后，阻塞当前线程，如果还有任务进来的话，就使用notify通知结束阻塞,阻塞时间超过1s自动释放
                                synchronized (sendLock) {
                                    try {
                                        sendLock.wait(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        Thread nowThread = Thread.currentThread();
                        Logger.d("发送结束，当前线程名:" + nowThread.getName());
                        if ("UdpSocketUtils".equals(nowThread.getName())) {
                            nowThread.interrupt();
                        }
                        dataSend = false;
                    });
                }
            }
        }
    }

    public void destroyUdp() {
        if (datagramSocket != null) {
            stopReceive = true;
            sendList.clear();
            //udpThreadPoolExecutor.shutdownNow();
            udpStatusListener = null;
            datagramSocket.close();
            datagramSocket = null;
            dataReceive = false;
            dataSend = false;
        }
    }

    public interface UdpStatusListener {

        /**
         * 数据发送成功
         */
        void sendSucceed();

        /**
         * 数据发送失败
         */
        void sendFailed();

        /**
         * 数据接收成功
         *
         * @param receiveBytes 接收的数据
         */
        void receivedMessage(byte[] receiveBytes);

        /**
         * 数据接收失败
         */
        void receivedMessageFailed();
    }

    private byte[] subByte(byte[] src, int length) {
        byte[] des = new byte[length];
        int i = 0;
        for (int j = 0; i < length; j++) {
            des[i] = src[j];
            i += 1;
        }
        return des;
    }

    public String formatHexString(String originHexString, int destByte, boolean reverse) {
        if (TextUtils.isEmpty(originHexString)) {
            originHexString = "00";
        }
        if (originHexString.length() % 2 != 0) {
            originHexString = "0" + originHexString;
        }
        long byteCount = Math.round(originHexString.length() / 2);
        StringBuilder originBuilder = new StringBuilder();
        if (reverse) {
            for (int i = originHexString.length() - 1; i >= 0; i -= 2) {
                originBuilder.append(originHexString.charAt(i - 1));
                originBuilder.append(originHexString.charAt(i));
                if (i > 1) {
                    originBuilder.append(" ");
                }
            }
            for (int j = 0; j < destByte - byteCount; j++) {
                originBuilder.append(" 00");
            }

        } else {
            for (int j = 0; j < destByte - byteCount; j++) {
                originBuilder.append("00 ");
            }
            for (int i = 0; i < originHexString.length(); i += 2) {
                originBuilder.append(originHexString.charAt(i));
                originBuilder.append(originHexString.charAt(i + 1));
                if (i < originHexString.length() - 2) {
                    originBuilder.append(" ");
                }
            }
        }
        Logger.d("格式化16进制字符串:" + originBuilder.toString());
        return originBuilder.toString();
    }

    public String getParameterCrc(String frameString) {
        String[] contentArray = frameString.split(" ");
        byte[] bytes = new byte[contentArray.length];
        int index = 0;
        for (String content : contentArray) {
            bytes[index] = (byte) Integer.parseInt(content, 16);
            index += 1;
        }
        int crc = getCrc16(bytes, false);
        String crcString = formatHexString(Integer.toHexString(crc), 2, true);
        Logger.d("CRC校验值:" + crcString);
        return crcString;
    }

    private int getCrc16(byte[] bytes, boolean needChange) {
        int crc = 0xFFFF;
        int polynomial = 0xA001;
        int i, j;
        for (i = 0; i < bytes.length; i++) {
            crc ^= bytes[i] & 0xFF;
            for (j = 0; j < 8; j++) {
                if ((crc & 1) != 0) {
                    crc >>= 1;
                    crc ^= polynomial;
                } else {
                    crc >>= 1;
                }
            }
        }
        //高低位转换，看情况使用
        if (needChange) {
            crc = ((crc & 0x0000FF00) >> 8) | ((crc & 0x000000FF) << 8);
        }
        return crc;
    }

    public String reverseByteArray(byte[] originArray, int byteCount) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < originArray.length; i++) {
            String hex = Integer.toHexString(originArray[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            stringBuilder.append(hex.toUpperCase());
        }
        Logger.d("反转数组组装的值:" + stringBuilder.toString());
        return formatHexString(stringBuilder.toString(), byteCount, true);
    }
}