package ru.rybinskov.entity;

public class Type {
    String reportType;

    public Type(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }

    @Override
    public String toString() {
        return "Type{" +
                "reportType='" + reportType + '\'' +
                '}';
    }
}
