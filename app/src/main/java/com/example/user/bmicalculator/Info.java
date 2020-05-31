package com.example.user.bmicalculator;

public class Info {

    private String date,result;
    private float bmi;

    @Override
    public String toString() {
        return "date=" + date +
                ", result=" + result +
                ", bmi=" + bmi ;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public Info(String date, String result, float bmi) {
        this.date = date;
        this.result = result;
        this.bmi = bmi;
    }
}
