package com.example.evotingapp.model;

public class SetData {
    String name;
    String url;

    public SetData() {
    }
    public SetData(String name, String url) {
        this.name = name;
        this.url = url;
    }
    public String getName(){
        return name;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
