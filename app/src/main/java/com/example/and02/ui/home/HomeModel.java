package com.example.and02.ui.home;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeModel implements Parcelable {
  private String url;
  private String title;
  private int id;

  protected HomeModel(Parcel in) {
    url = in.readString();
    title = in.readString();
    id = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(url);
    dest.writeString(title);
    dest.writeInt(id);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<HomeModel> CREATOR = new Creator<HomeModel>() {
    @Override
    public HomeModel createFromParcel(Parcel in) {
      return new HomeModel(in);
    }

    @Override
    public HomeModel[] newArray(int size) {
      return new HomeModel[size];
    }
  };

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
