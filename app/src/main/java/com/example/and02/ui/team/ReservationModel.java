package com.example.and02.ui.team;

import com.example.and02.ui.common.CodeModel;
import com.example.and02.ui.common.UserModel;
import com.example.and02.ui.home.InfraModel;

import org.json.JSONArray;

import java.io.Serializable;

public class ReservationModel implements Serializable {

    private String reservaterNo;
    private String registerNo;
    private String teamNo;
    private String infraNo;
    private String parentInfraNo;
    private int reservationStateCodeId;
    private UserModel reservater;
    private UserModel register;
    private TeamModel team;
    private InfraModel infra;
    private CodeModel reservationStateCode;
    private String reservationNo;
    private String startDate;
    private String endDate;
    private boolean approve;
    private String errMsg;
    private JSONArray attachFiles;
    private String attachFile;
    private String registeDate;

    public String getReservaterNo() {
        return reservaterNo;
    }

    public void setReservaterNo(String reservaterNo) {
        this.reservaterNo = reservaterNo;
    }

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }

    public String getInfraNo() {
        return infraNo;
    }

    public void setInfraNo(String infraNo) {
        this.infraNo = infraNo;
    }

    public String getParentInfraNo() {
        return parentInfraNo;
    }

    public void setParentInfraNo(String parentInfraNo) {
        this.parentInfraNo = parentInfraNo;
    }

    public int getReservationStateCodeId() {
        return reservationStateCodeId;
    }

    public void setReservationStateCodeId(int reservationStateCodeId) {
        this.reservationStateCodeId = reservationStateCodeId;
    }

    public UserModel getReservater() {
        return reservater;
    }

    public void setReservater(UserModel reservater) {
        this.reservater = reservater;
    }

    public UserModel getRegister() {
        return register;
    }

    public void setRegister(UserModel register) {
        this.register = register;
    }

    public TeamModel getTeam() {
        return team;
    }

    public void setTeam(TeamModel team) {
        this.team = team;
    }

    public InfraModel getInfra() {
        return infra;
    }

    public void setInfra(InfraModel infra) {
        this.infra = infra;
    }

    public CodeModel getReservationStateCode() {
        return reservationStateCode;
    }

    public void setReservationStateCode(CodeModel reservationStateCode) {
        this.reservationStateCode = reservationStateCode;
    }

    public String getReservationNo() {
        return reservationNo;
    }

    public void setReservationNo(String reservationNo) {
        this.reservationNo = reservationNo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public JSONArray getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(JSONArray attachFiles) {
        this.attachFiles = attachFiles;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getRegisteDate() {
        return registeDate;
    }

    public void setRegisteDate(String registeDate) {
        this.registeDate = registeDate;
    }
}
