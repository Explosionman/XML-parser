package ru.rybinskov.entity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DeviceInfo {
    private String name;
    private Cipher cipher;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<WorkingMode> modeList;

    public DeviceInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cipher getCipher() {
        return cipher;
    }

    public void setCipher(Cipher cipher) {
        this.cipher = cipher;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<WorkingMode> getModeList() {
        return modeList;
    }

    public void addMode(WorkingMode mode) {
        if (modeList == null) {
            modeList = new ArrayList<>();
        }
        modeList.add(mode);
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "name='" + name + '\'' +
                ", cipher='" + cipher + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", modeList=" + modeList +
                '}';
    }
}
