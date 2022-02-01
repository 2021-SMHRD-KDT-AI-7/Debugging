package com.example.project_02;

public class checkVO {
    private String type;
    private String Qs;
    private String checkBox;
    private String checkBox2;
    private String checkBox3;
    private String checkBox4;
    private String checkBox5;

    public checkVO(String type, String qs, String checkBox, String checkBox2, String checkBox3, String checkBox4) {
        this.type = type;
        Qs = qs;
        this.checkBox = checkBox;
        this.checkBox2 = checkBox2;
        this.checkBox3 = checkBox3;
        this.checkBox4 = checkBox4;
    }

    public checkVO(String type, String qs, String checkBox, String checkBox2, String checkBox3, String checkBox4, String checkBox5) {
        this.type = type;
        Qs = qs;
        this.checkBox = checkBox;
        this.checkBox2 = checkBox2;
        this.checkBox3 = checkBox3;
        this.checkBox4 = checkBox4;
        this.checkBox5 = checkBox5;
    }

    @Override
    public String toString() {
        return "checkVO{" +
                "type='" + type + '\'' +
                ", Qs='" + Qs + '\'' +
                ", checkBox='" + checkBox + '\'' +
                ", checkBox2='" + checkBox2 + '\'' +
                ", checkBox3='" + checkBox3 + '\'' +
                ", checkBox4='" + checkBox4 + '\'' +
                ", checkBox5='" + checkBox5 + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQs() {
        return Qs;
    }

    public void setQs(String qs) {
        Qs = qs;
    }

    public String getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(String checkBox) {
        this.checkBox = checkBox;
    }

    public String getCheckBox2() {
        return checkBox2;
    }

    public void setCheckBox2(String checkBox2) {
        this.checkBox2 = checkBox2;
    }

    public String getCheckBox3() {
        return checkBox3;
    }

    public void setCheckBox3(String checkBox3) {
        this.checkBox3 = checkBox3;
    }

    public String getCheckBox4() {
        return checkBox4;
    }

    public void setCheckBox4(String checkBox4) {
        this.checkBox4 = checkBox4;
    }

    public String getCheckBox5() {
        return checkBox5;
    }

    public void setCheckBox5(String checkBox5) {
        this.checkBox5 = checkBox5;
    }
}
