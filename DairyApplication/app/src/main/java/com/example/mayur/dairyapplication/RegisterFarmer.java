package com.example.mayur.dairyapplication;

/**
 * Created by Mayur on 24/01/2017.
 */
public class RegisterFarmer {
    int owner_id;
    String farmer_name, farmer_add ,farmer_contact, farmer_mail, farmer_username,farmer_pass;

    public String getFarmer_name() {
        return farmer_name;
    }

    public void setFarmer_name(String farmer_name) {
        this.farmer_name = farmer_name;
    }

    public String getFarmer_add() {
        return farmer_add;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setFarmer_add(String farmer_add) {
        this.farmer_add = farmer_add;
    }

    public String getFarmer_contact() {
        return farmer_contact;
    }

    public void setFarmer_contact(String farmer_contact) {
        this.farmer_contact = farmer_contact;
    }

    public String getFarmer_mail() {
        return farmer_mail;
    }

    public void setFarmer_mail(String farmer_mail) {
        this.farmer_mail = farmer_mail;
    }

    public String getFarmer_username() {
        return farmer_username;
    }

    public void setFarmer_username(String farmer_username) {
        this.farmer_username = farmer_username;
    }

    public String getFarmer_pass() {
        return farmer_pass;
    }

    public void setFarmer_pass(String farmer_pass) {
        this.farmer_pass = farmer_pass;
    }

    public RegisterFarmer(String farmer_name, String farmer_add , String farmer_contact, String farmer_mail, String farmer_username, String farmer_pass,int owner_id)
    {
        super();
        this.owner_id=owner_id;
        this.farmer_name=farmer_name;
        this.farmer_add=farmer_add;
        this.farmer_contact=farmer_contact;
        this.farmer_mail=farmer_mail;
        this.farmer_username=farmer_username;
        this.farmer_pass=farmer_pass;
    }

}
