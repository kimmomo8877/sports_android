package com.example.and02.ui.sport;

import java.util.List;

public class CenterDomain {
    private String centerNo;
    private String centerName;
    private List<String> reservationTime;

    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public List<String> getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(List<String> reservationTime) {
        this.reservationTime = reservationTime;
    }
}
