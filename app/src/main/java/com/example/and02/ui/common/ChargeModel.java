package com.example.and02.ui.common;

import java.io.Serializable;

public class ChargeModel implements Serializable {

    private String chargeNo;
    private int chargeTypeNo;
    private String infraNo;
    private int timeUnit;
    private int cost;

    public String getChargeNo() {
        return chargeNo;
    }

    public void setChargeNo(String chargeNo) {
        this.chargeNo = chargeNo;
    }

    public int getChargeTypeNo() {
        return chargeTypeNo;
    }

    public void setChargeTypeNo(int chargeTypeNo) {
        this.chargeTypeNo = chargeTypeNo;
    }

    public String getInfraNo() {
        return infraNo;
    }

    public void setInfraNo(String infraNo) {
        this.infraNo = infraNo;
    }

    public int getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
