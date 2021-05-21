package ru.rybinskov.entity;

import java.time.LocalDateTime;

public class Report {
    private Addressee sender;
    private Addressee receiver;
    private String name;
    private Type type;
    private LocalDateTime sendingDateTime;
    private DeviceInfo deviceInfo;

    public Report() {
    }

    public void setSender(Addressee sender) {
        this.sender = sender;
    }

    public void setReceiver(Addressee receiver) {
        this.receiver = receiver;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setSendingDateTime(LocalDateTime sendingDateTime) {
        this.sendingDateTime = sendingDateTime;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString() {
        return "Report{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", sendingDateTime=" + sendingDateTime +
                ", deviceInfo=" + deviceInfo +
                '}';
    }
}
