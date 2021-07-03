package com.example.evotingapp.model;

public class uploadImage {
  private  String name;
  private String downloadUrl;

    public uploadImage() {
    }
    public uploadImage(String name, String downloadUrl) {
        this.name = name;
        this.downloadUrl = downloadUrl;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
