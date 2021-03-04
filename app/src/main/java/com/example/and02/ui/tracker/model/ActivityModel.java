package com.example.and02.ui.tracker.model;

public class ActivityModel {
  private String Date;
  private String Time;
  private Integer Walk;
  private Integer WalkTime;
  private Integer WalkCalories;
  private Integer WalkDistance;
  private Integer Run;
  private Integer RunTime;
  private Integer RunCalories;
  private Integer RunDistance;
  private Integer IsComplete;

  public void add(ActivityModel activityModel) {
    this.Walk += activityModel.getWalk();
    this.WalkTime += activityModel.getWalkTime();
    this.WalkCalories += activityModel.getWalkCalories();
    this.WalkDistance += activityModel.getWalkDistance();

    this.Run += activityModel.getRun();
    this.RunTime += activityModel.getRunTime();
    this.RunCalories += activityModel.getRunCalories();
    this.RunDistance += activityModel.getRunDistance();
  }

  public void minus(ActivityModel activityModel) {
    this.Walk -= activityModel.getWalk();
    this.WalkTime -= activityModel.getWalkTime();
    this.WalkCalories -= activityModel.getWalkCalories();
    this.WalkDistance -= activityModel.getWalkDistance();

    this.Run -= activityModel.getRun();
    this.RunTime -= activityModel.getRunTime();
    this.RunCalories -= activityModel.getRunCalories();
    this.RunDistance -= activityModel.getRunDistance();
  }

  public Integer getIsComplete() {
    return IsComplete;
  }

  public void setIsComplete(Integer isComplete) {
    IsComplete = isComplete;
  }

  public Integer getWalkCalories() {
    return WalkCalories;
  }

  public void setWalkCalories(Integer walkCalories) {
    WalkCalories = walkCalories;
  }

  public Integer getWalkDistance() {
    return WalkDistance;
  }

  public void setWalkDistance(Integer walkDistance) {
    WalkDistance = walkDistance;
  }

  public Integer getRun() {
    return Run;
  }

  public void setRun(Integer run) {
    Run = run;
  }

  public Integer getRunTime() {
    return RunTime;
  }

  public void setRunTime(Integer runTime) {
    RunTime = runTime;
  }

  public Integer getRunCalories() {
    return RunCalories;
  }

  public void setRunCalories(Integer runCalories) {
    RunCalories = runCalories;
  }

  public Integer getRunDistance() {
    return RunDistance;
  }

  public void setRunDistance(Integer runDistance) {
    RunDistance = runDistance;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String date) {
    Date = date;
  }

  public String getTime() {
    return Time;
  }

  public void setTime(String time) {
    Time = time;
  }

  public Integer getWalk() {
    return Walk;
  }

  public void setWalk(Integer walk) {
    Walk = walk;
  }

  public Integer getWalkTime() {
    return WalkTime;
  }

  public void setWalkTime(Integer walkTime) {
    WalkTime = walkTime;
  }
}
