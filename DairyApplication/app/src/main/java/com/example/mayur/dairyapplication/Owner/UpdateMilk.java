package com.example.mayur.dairyapplication.Owner;

/**
 * Created by Mayur on 03/02/2017.
 */
public class UpdateMilk {
    int oid,fid;
    String date;
    float quantity,fat,deg,snf,rate,total;

    public UpdateMilk(int fid, String date, float quantity, float fat, float deg, float snf, float rate, float total) {
        this.fid = fid;
        this.date = date;
        this.quantity = quantity;
        this.fat = fat;
        this.deg = deg;
        this.snf = snf;
        this.rate = rate;
        this.total = total;
    }

    public UpdateMilk(int oid, int fid, String date, float quantity, float fat, float deg, float snf, float rate, float total) {
        this.date = date;
        this.fid = fid;
        this.total=total;
        this.oid = oid;
        this.quantity = quantity;
        this.deg = deg;
        this.snf = snf;
        this.rate = rate;
        this.fat = fat;
    }
    public float getDeg() {

        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getSnf() {
        return snf;
    }

    public void setSnf(float snf) {
        this.snf = snf;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getFat() {
        return fat;
    }

    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }
}
