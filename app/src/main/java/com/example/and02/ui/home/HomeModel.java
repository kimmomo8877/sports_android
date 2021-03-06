package com.example.and02.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class HomeModel implements Serializable {
  private String url;
  private String title;
  private int id;

  protected HomeModel(Parcel in) {
    url = in.readString();
    title = in.readString();
    id = in.readInt();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
