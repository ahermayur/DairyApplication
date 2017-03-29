package com.example.mayur.dairyapplication.Admin;

/**
 * Created by mayur on 20/3/17.
 */

public class Constructor_insert {

    String Dname,Descripion;



    public Constructor_insert( String dname, String descripion) {

        Dname = dname;
        Descripion = descripion;

    }

    public Constructor_insert(int die_id, String descripion) {


    }


    public String getDname() {
        return Dname;
    }

    public void setDname(String dname) {
        Dname = dname;
    }

    public String getDescripion() {
        return Descripion;
    }

    public void setDescripion(String descripion) {
        Descripion = descripion;
    }

}
