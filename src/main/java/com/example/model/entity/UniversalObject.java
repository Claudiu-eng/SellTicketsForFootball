package com.example.model.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UniversalObject {

    private String attribute1,attribute2,attribute3,attribute4,attribute6;
    private Date attribute5,attribute7;

    public String getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public UniversalObject() {
    }

    public UniversalObject(String attribute1, String attribute2, String attribute3, String attribute4, String attribute6, Date attribute5, Date attribute7) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.attribute6 = attribute6;
        this.attribute5 = attribute5;
        this.attribute7 = attribute7;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public Date getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(String attribute7) throws ParseException {
        this.attribute7 = new SimpleDateFormat("yyyy-MM-dd").parse(attribute7);;
    }

    public Date getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) throws ParseException {
        this.attribute5 = new SimpleDateFormat("yyyy-MM-dd").parse(attribute5);;
    }
}
