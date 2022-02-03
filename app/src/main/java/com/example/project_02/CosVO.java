package com.example.project_02;

public class CosVO {
    private int cos_seq;
    private String cos_name;
    private String cos_price;
    private String cos_brand;
    private String cos_img;
    private String cos_bauman;

    public CosVO(int cos_seq, String cos_name, String cos_price, String cos_brand, String cos_img, String cos_bauman) {
        this.cos_seq = cos_seq;
        this.cos_name = cos_name;
        this.cos_price = cos_price;
        this.cos_brand = cos_brand;
        this.cos_img = cos_img;
        this.cos_bauman = cos_bauman;
    }

    public CosVO(String cos_name, String cos_price, String cos_brand, String cos_img) {
        this.cos_name = cos_name;
        this.cos_price = cos_price;
        this.cos_brand = cos_brand;
        this.cos_img = cos_img;
    }

    public int getCos_seq() {
        return cos_seq;
    }

    public void setCos_seq(int cos_seq) {
        this.cos_seq = cos_seq;
    }

    public String getCos_name() {
        return cos_name;
    }

    public void setCos_name(String cos_name) {
        this.cos_name = cos_name;
    }

    public String getCos_price() {
        return cos_price;
    }

    public void setCos_price(String cos_price) {
        this.cos_price = cos_price;
    }

    public String getCos_brand() {
        return cos_brand;
    }

    public void setCos_brand(String cos_brand) {
        this.cos_brand = cos_brand;
    }

    public String getCos_img() {
        return cos_img;
    }

    public void setCos_img(String cos_img) {
        this.cos_img = cos_img;
    }

    public String getCos_bauman() {
        return cos_bauman;
    }

    public void setCos_bauman(String cos_bauman) {
        this.cos_bauman = cos_bauman;
    }
}
