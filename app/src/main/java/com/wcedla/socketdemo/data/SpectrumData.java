package com.wcedla.socketdemo.data;

public class SpectrumData {

    /**
     * 400Mhz-6000mhz
     */
    private long beginFrequency;

    /**
     * 400Mhz-6000mhz
     */
    private long endFrequency;

    /**
     * 作为初始化标记，为1就是进行初始化，为0就是切换扫描模式
     */
    private int frequencyCount;

    public SpectrumData(long beginFrequency, long endFrequency, int frequencyCount) {
        this.beginFrequency = beginFrequency;
        this.endFrequency = endFrequency;
        this.frequencyCount = frequencyCount;
    }

    public long getBeginFrequency() {
        return beginFrequency;
    }

    public void setBeginFrequency(long beginFrequency) {
        this.beginFrequency = beginFrequency;
    }

    public long getEndFrequency() {
        return endFrequency;
    }

    public void setEndFrequency(long endFrequency) {
        this.endFrequency = endFrequency;
    }

    public int getFrequencyCount() {
        return frequencyCount;
    }

    public void setFrequencyCount(int frequencyCount) {
        this.frequencyCount = frequencyCount;
    }
}
