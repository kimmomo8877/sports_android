package com.example.and02.ui.team;

import java.io.Serializable;

public class SearchModel implements Serializable {

    private String searchItemNo;
    private String name;
    private String searchType;

    public String getSearchItemNo() {
        return searchItemNo;
    }

    public void setSearchItemNo(String searchItemNo) {
        this.searchItemNo = searchItemNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
