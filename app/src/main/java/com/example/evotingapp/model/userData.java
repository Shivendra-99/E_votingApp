package com.example.evotingapp.model;

public class userData{
    String UserName;
    String FatherName;
    String Dob;
    String Gender;
    String Pincode;
    String District;
    String state;
    String Block;
    String Area;
    String Mobile_Number;
    String Aadhar_Card;
    String VoterId;
    String Password;
    String userid;
    public userData(String userName, String fatherName, String dob, String gender, String pincode, String district, String state, String block, String area, String mobile_Number, String aadhar_Card, String voterId, String password, String userid) {
        UserName = userName;
        FatherName = fatherName;
        Dob = dob;
        Gender = gender;
        Pincode = pincode;
        District = district;
        this.state = state;
        Block = block;
        Area = area;
        Mobile_Number = mobile_Number;
        Aadhar_Card = aadhar_Card;
        VoterId = voterId;
        Password = password;
        this.userid = userid;
    }
    public userData() {
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFatherName() {
        return FatherName;
    }

    public void setFatherName(String fatherName) {
        FatherName = fatherName;
    }

    public String getDob() {
        return Dob;
    }

    public void setDob(String dob) {
        Dob = dob;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
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
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public String getAadhar_Card() {
        return Aadhar_Card;
    }

    public void setAadhar_Card(String aadhar_Card) {
        Aadhar_Card = aadhar_Card;
    }

    public String getVoterId() {
        return VoterId;
    }

    public void setVoterId(String voterId) {
        VoterId = voterId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
