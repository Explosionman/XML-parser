package ru.rybinskov.entity;

public class Type {
    String reportType;

    public Type() {
    }

    public Type(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    @Override
    public String toString() {
        return "Type{" +
                "reportType='" + reportType + '\'' +
                '}';
    }
}
