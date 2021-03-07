package com.example.and02.ui.common;

import java.io.Serializable;

public class BoardWriterModel implements Serializable {

    private String userNo;
    private String name;
    private String birthdate;
//    private String password;
    private String email;
    private boolean signupApprove;
    private String registeDate;
    private String modifyDate;
    private String deleteDate;
    private boolean deleteYn;
    private int userTypeCodeId;
    private CodeModel userTypeCode;
    private int classificationCodeId;
    private CodeModel classificationCode;
    private int genderCodeId;
    private CodeModel genderCode;
    private int regionCodeId;
    private CodeModel regionCode;
    private int sportCodeId;
    private CodeModel sportCode;
    private int belongedCodeId;
    private CodeModel belongCode;
    private String imageUrl;
    private String teamName;

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSignupApprove() {
        return signupApprove;
    }

    public void setSignupApprove(boolean signupApprove) {
        this.signupApprove = signupApprove;
    }

    public String getRegisteDate() {
        return registeDate;
    }

    public void setRegisteDate(String registeDate) {
        this.registeDate = registeDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(String deleteDate) {
        this.deleteDate = deleteDate;
    }

    public boolean isDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(boolean deleteYn) {
        this.deleteYn = deleteYn;
    }

    public int getUserTypeCodeId() {
        return userTypeCodeId;
    }

    public void setUserTypeCodeId(int userTypeCodeId) {
        this.userTypeCodeId = userTypeCodeId;
    }

    public CodeModel getUserTypeCode() {
        return userTypeCode;
    }

    public void setUserTypeCode(CodeModel userTypeCode) {
        this.userTypeCode = userTypeCode;
    }

    public int getClassificationCodeId() {
        return classificationCodeId;
    }

    public void setClassificationCodeId(int classificationCodeId) {
        this.classificationCodeId = classificationCodeId;
    }

    public CodeModel getClassificationCode() {
        return classificationCode;
    }

    public void setClassificationCode(CodeModel classificationCode) {
        this.classificationCode = classificationCode;
    }

    public int getGenderCodeId() {
        return genderCodeId;
    }

    public void setGenderCodeId(int genderCodeId) {
        this.genderCodeId = genderCodeId;
    }

    public CodeModel getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(CodeModel genderCode) {
        this.genderCode = genderCode;
    }

    public int getRegionCodeId() {
        return regionCodeId;
    }

    public void setRegionCodeId(int regionCodeId) {
        this.regionCodeId = regionCodeId;
    }

    public CodeModel getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(CodeModel regionCode) {
        this.regionCode = regionCode;
    }

    public int getSportCodeId() {
        return sportCodeId;
    }

    public void setSportCodeId(int sportCodeId) {
        this.sportCodeId = sportCodeId;
    }

    public CodeModel getSportCode() {
        return sportCode;
    }

    public void setSportCode(CodeModel sportCode) {
        this.sportCode = sportCode;
    }

    public int getBelongedCodeId() {
        return belongedCodeId;
    }

    public void setBelongedCodeId(int belongedCodeId) {
        this.belongedCodeId = belongedCodeId;
    }

    public CodeModel getBelongCode() {
        return belongCode;
    }

    public void setBelongCode(CodeModel belongCode) {
        this.belongCode = belongCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
