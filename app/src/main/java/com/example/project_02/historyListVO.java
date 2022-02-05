package com.example.project_02;

public class historyListVO {
    private int imgID;
    private int date;
    private String time;
    private int res;

    public historyListVO(int imgID, int date, String time, int res) {
        this.imgID = imgID;
        this.date = date;
        this.time = time;
        this.res = res;
    }

    public int getImgID() {
        return imgID;
    }

    public void setImgID(int imgID) {
        this.imgID = imgID;
    }

    public int getdate() {
        return date;
    }

    public void setdate(int date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}