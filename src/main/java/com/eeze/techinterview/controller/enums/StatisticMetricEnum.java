package com.eeze.techinterview.controller.enums;

public enum StatisticMetricEnum {

    IMPRESSIONS("impressions"),
    VIEWS("views");


    private final String dataColumn;

    StatisticMetricEnum(String dataColumn) {
        this.dataColumn = dataColumn;
    }

    public String getDataColumn() {
        return dataColumn;
    }
}
