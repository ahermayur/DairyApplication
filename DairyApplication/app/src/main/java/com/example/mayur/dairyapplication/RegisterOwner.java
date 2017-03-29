package com.example.mayur.dairyapplication;

/**
 * Created by Mayur on 24/01/2017.
 */
public class RegisterOwner {
    String owner_name, owner_add ,owner_contact, owner_mail, owner_username,owner_pass;

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_add() {
        return owner_add;
    }

    public void setOwner_add(String owner_add) {
        this.owner_add = owner_add;
    }

    public String getOwner_contact() {
        return owner_contact;
    }

    public void setOwner_contact(String owner_contact) {
        this.owner_contact = owner_contact;
    }

    public String getOwner_mail() {
        return owner_mail;
    }

    public void setOwner_mail(String owner_mail) {
        this.owner_mail = owner_mail;
    }

    public String getOwner_username() {
        return owner_username;
    }

    public void setOwner_username(String owner_username) {
        this.owner_username = owner_username;
    }

    public String getOwner_pass() {
        return owner_pass;
    }

    public void setOwner_pass(String owner_pass) {
        this.owner_pass = owner_pass;
    }

    public RegisterOwner(String owner_name,String owner_add ,String owner_contact,String owner_mail,String owner_username,String owner_pass)
    {
        super();
        this.owner_name=owner_name;
        this.owner_add=owner_add;
        this.owner_contact=owner_contact;
        this.owner_mail=owner_mail;
        this.owner_username=owner_username;
        this.owner_pass=owner_pass;
    }
    public RegisterOwner(String owner_name, String owner_add, String owner_contact, String owner_mail, String owner_username, String owner_pass, int i)
    {
        super();
        this.owner_name=owner_name;
        this.owner_add=owner_add;
        this.owner_contact=owner_contact;
        this.owner_mail=owner_mail;
        this.owner_username=owner_username;
        this.owner_pass=owner_pass;
    }
}
