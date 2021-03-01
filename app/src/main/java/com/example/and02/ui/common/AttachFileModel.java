package com.example.and02.ui.common;

import java.io.Serializable;

public class AttachFileModel implements Serializable {

    private String saveFilePath;
    private String savePath;
    private String originalName;
    private String saveName;
    private double size;
    private boolean deleteYn;
    private String attachType;

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public boolean isDeleteYn() {
        return deleteYn;
    }

    public void setDeleteYn(boolean deleteYn) {
        this.deleteYn = deleteYn;
    }

    public String getAttachType() {
        return attachType;
    }

    public void setAttachType(String attachType) {
        this.attachType = attachType;
    }
}
