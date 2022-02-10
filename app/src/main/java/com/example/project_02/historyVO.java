package com.example.project_02;

public class historyVO {
    private String bauman;
    private int sk_res, sk_oil, sk_sen, sk_pig, sk_ela;
    private String user_id, anal_date, img_src;

    public historyVO(String bauman, int sk_res, int sk_oil, int sk_sen, int sk_pig, int sk_ela, String user_id, String anal_date, String img_src) {
        this.bauman = bauman;
        this.sk_res = sk_res;
        this.sk_oil = sk_oil;
        this.sk_sen = sk_sen;
        this.sk_pig = sk_pig;
        this.sk_ela = sk_ela;
        this.user_id = user_id;
        this.anal_date = anal_date;
        this.img_src = img_src;
    }

    public String getBauman() {
        return bauman;
    }

    public void setBauman(String bauman) {
        this.bauman = bauman;
    }

    public int getSk_res() {
        return sk_res;
    }

    public void setSk_res(int sk_res) {
        this.sk_res = sk_res;
    }

    public int getSk_oil() {
        return sk_oil;
    }

    public void setSk_oil(int sk_oil) {
        this.sk_oil = sk_oil;
    }

    public int getSk_sen() {
        return sk_sen;
    }

    public void setSk_sen(int sk_sen) {
        this.sk_sen = sk_sen;
    }

    public int getSk_pig() {
        return sk_pig;
    }

    public void setSk_pig(int sk_pig) {
        this.sk_pig = sk_pig;
    }

    public int getSk_ela() {
        return sk_ela;
    }

    public void setSk_ela(int sk_ela) {
        this.sk_ela = sk_ela;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAnal_date() {
        return anal_date;
    }

    public void setAnal_date(String anal_date) {
        this.anal_date = anal_date;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }
}

