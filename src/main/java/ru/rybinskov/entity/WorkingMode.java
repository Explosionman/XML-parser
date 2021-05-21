package ru.rybinskov.entity;

public class WorkingMode {
    private Integer mode;

    public WorkingMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "WorkingMode{" +
                "mode=" + mode +
                '}';
    }
}
