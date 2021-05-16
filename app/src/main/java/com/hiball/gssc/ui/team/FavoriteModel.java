package com.hiball.gssc.ui.team;

import java.io.Serializable;

public class FavoriteModel implements Serializable {

    private String searcherNo;
    private String searchWord;

    public String getSearcherNo() {
        return searcherNo;
    }

    public void setSearcherNo(String searcherNo) {
        this.searcherNo = searcherNo;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
}
