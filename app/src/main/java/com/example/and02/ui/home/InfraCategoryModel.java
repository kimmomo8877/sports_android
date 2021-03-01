package com.example.and02.ui.home;

import java.io.Serializable;

public class InfraCategoryModel implements Serializable {

    private int infraCategoryNo;
    private int parentInfraCategoryNo;
    private String name;
    private String iconClassName;

    public int getInfraCategoryNo() {
        return infraCategoryNo;
    }

    public void setInfraCategoryNo(int infraCategoryNo) {
        this.infraCategoryNo = infraCategoryNo;
    }

    public int getParentInfraCategoryNo() {
        return parentInfraCategoryNo;
    }

    public void setParentInfraCategoryNo(int parentInfraCategoryNo) {
        this.parentInfraCategoryNo = parentInfraCategoryNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconClassName() {
        return iconClassName;
    }

    public void setIconClassName(String iconClassName) {
        this.iconClassName = iconClassName;
    }
}
