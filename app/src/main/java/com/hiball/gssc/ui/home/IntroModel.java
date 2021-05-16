package com.hiball.gssc.ui.home;

import java.io.Serializable;

public class IntroModel implements Serializable {

    private String titleText;
    private String titleSubText;

    public IntroModel(String titleText, String titleSubText){
        this.titleText = titleText;
        this.titleSubText = titleSubText;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getTitleSubText() {
        return titleSubText;
    }

    public void setTitleSubText(String titleSubText) {
        this.titleSubText = titleSubText;
    }
}
