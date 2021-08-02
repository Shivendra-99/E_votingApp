package com.example.evotingapp.model;

import java.util.ArrayList;
import java.util.HashMap;

public class setVotingDate {
    private ArrayList<String> list;
    String startDate;
    String EndDate;
    String StartTime;
    String EndTime;
    String Pincode;
    String District;
    String State;
    public setVotingDate() {
    }
    public setVotingDate(ArrayList<String> list, String startDate, String endDate, String startTime, String endTime, String pincode, String district, String state) {
        this.list = list;
        this.startDate = startDate;
        EndDate = endDate;
        StartTime = startTime;
        EndTime = endTime;
        Pincode = pincode;
        District = district;
        State = state;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
