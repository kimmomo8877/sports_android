package com.hiball.gssc.ui.home;

import com.hiball.gssc.ui.common.ChargeModel;
import com.hiball.gssc.ui.common.CodeModel;

import org.json.JSONArray;

import java.io.Serializable;

public class InfraModel implements Serializable {
    private String infraNo;
    private int infraCategoryNo;
    private String parentInfraNo;
    private int sportCodeId;
    private InfraCategoryModel infraCategoryModel;
    private CodeModel sportCode;
    private InfraModel childInfras[];
    private transient JSONArray attachFiles;
    private String attachFile;
    private String name;
    private String phoneNumber;
    private String address;
    private String homepageUrl;
    private String facilityDescription;
    private String equipmentDescription;
    private String etcDescription;
    private String useRuleDescription;
    private String refundPolicyDescription;
    private double latitude;
    private double longitude;
    private transient JSONArray charges;
    private ChargeModel[] chargesModel;
    private int reservationCnt;
    private CodeModel regionCode;
    private boolean deleteYn;
    private int checkVisiable;
    private String firstPrVideoUrl;
    private String secondPrVideoUrl;
    private String thirdPrVideoUrl;

    public int getCheckVisiable() {
        return checkVisiable;
    }

    public void setCheckVisiable(int checkVisiable) {
        this.checkVisiable = checkVisiable;
    }

    public String getInfraNo() {
        return infraNo;
    }

    public void setInfraNo(String infraNo) {
        this.infraNo = infraNo;
    }

    public int getInfraCategoryNo() {
        return infraCategoryNo;
    }

    public void setInfraCategoryNo(int infraCategoryNo) {
        this.infraCategoryNo = infraCategoryNo;
    }

    public String getParentInfraNo() {
        return parentInfraNo;
    }

    public void setParentInfraNo(String parentInfraNo) {
        this.parentInfraNo = parentInfraNo;
    }

    public int getSportCodeId() {
        return sportCodeId;
    }

    public void setSportCodeId(int sportCodeId) {
        this.sportCodeId = sportCodeId;
    }

    public InfraCategoryModel getInfraCategoryModel() {
        return infraCategoryModel;
    }

    public void setInfraCategoryModel(InfraCategoryModel infraCategoryModel) {
        this.infraCategoryModel = infraCategoryModel;
    }

    public CodeModel getSportCode() {
        return sportCode;
    }

    public void setSportCode(CodeModel sportCode) {
        this.sportCode = sportCode;
    }

    public InfraModel[] getChildInfras() {
        return childInfras;
    }

    public void setChildInfras(InfraModel[] childInfras) {
        this.childInfras = childInfras;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getFacilityDescription() {
        return facilityDescription;
    }

    public void setFacilityDescription(String facilityDescription) {
        this.facilityDescription = facilityDescription;
    }

    public String getEquipmentDescription() {
        return equipmentDescription;
    }

    public void setEquipmentDescription(String equipmentDescription) {
        this.equipmentDescription = equipmentDescription;
    }

    public String getEtcDescription() {
        return etcDescription;
    }

    public void setEtcDescription(String etcDescription) {
        this.etcDescription = etcDescription;
    }

    public String getUseRuleDescription() {
        return useRuleDescription;
    }

    public void setUseRuleDescription(String useRuleDescription) {
        this.useRuleDescription = useRuleDescription;
    }

    public String getRefundPolicyDescription() {
        return refundPolicyDescription;
    }

    public void setRefundPolicyDescription(String refundPolicyDescription) {
        this.refundPolicyDescription = refundPolicyDescription;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public JSONArray getCharges() {
        return charges;
    }

    public void setCharges(JSONArray charges) {
        this.charges = charges;
    }

    public int getReservationCnt() {
        return reservationCnt;
    }

    public void setReservationCnt(int reservationCnt) {
        this.reservationCnt = reservationCnt;
    }

    public CodeModel getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(CodeModel regionCode) {
        this.regionCode = regionCode;
    }

    public boolean isDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(boolean deleteYn) {
        this.deleteYn = deleteYn;
    }

    public String getFirstPrVideoUrl() {
        return firstPrVideoUrl;
    }

    public void setFirstPrVideoUrl(String firstPrVideoUrl) {
        this.firstPrVideoUrl = firstPrVideoUrl;
    }

    public String getSecondPrVideoUrl() {
        return secondPrVideoUrl;
    }

    public void setSecondPrVideoUrl(String secondPrVideoUrl) {
        this.secondPrVideoUrl = secondPrVideoUrl;
    }

    public String getThirdPrVideoUrl() {
        return thirdPrVideoUrl;
    }

    public void setThirdPrVideoUrl(String thirdPrVideoUrl) {
        this.thirdPrVideoUrl = thirdPrVideoUrl;
    }

    public ChargeModel[] getChargesModel() {
        return chargesModel;
    }

    public void setChargesModel(ChargeModel[] chargesModel) {
        this.chargesModel = chargesModel;
    }
}
