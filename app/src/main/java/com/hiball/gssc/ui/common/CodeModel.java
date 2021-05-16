package com.hiball.gssc.ui.common;

import java.io.Serializable;

public class CodeModel implements Serializable {

    private String ocdeTypeName;
    private int codeId;
    private int indexCodeId;
    private int parentCode;
    private String name;
    private boolean isCheck;

    public String getOcdeTypeName() {
        return ocdeTypeName;
    }

    public void setOcdeTypeName(String ocdeTypeName) {
        this.ocdeTypeName = ocdeTypeName;
    }

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public int getIndexCodeId() {
        return indexCodeId;
    }

    public void setIndexCodeId(int indexCodeId) {
        this.indexCodeId = indexCodeId;
    }

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
