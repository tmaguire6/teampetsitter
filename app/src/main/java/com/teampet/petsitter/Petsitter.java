package com.teampet.petsitter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Z on 4/10/2016.
 */
public class Petsitter implements Serializable{
    private String ID_sitter;
    private String Fname;
    private String Lname;
    private String Phone;
    private String Email;
    private String Add_str;
    private String Add_city;
    private String Add_state;
    private String Add_zip;
    private int HourlyRate010;
    private int HourlyRate1020;
    private int HourlyRate2050;
    private int HourlyRate50100;
    private ArrayList<String> RegionAva;
    private ArrayList<String> TypeOfSpec;
    private String PaymentAcpt;

    public String getID_sitter() {
        return ID_sitter;
    }

    public void setID_sitter(String ID_sitter) {
        this.ID_sitter = ID_sitter;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAdd_str() {
        return Add_str;
    }

    public void setAdd_str(String add_str) {
        Add_str = add_str;
    }

    public String getAdd_city() {
        return Add_city;
    }

    public void setAdd_city(String add_city) {
        Add_city = add_city;
    }

    public String getAdd_state() {
        return Add_state;
    }

    public void setAdd_state(String add_state) {
        Add_state = add_state;
    }

    public String getAdd_zip() {
        return Add_zip;
    }

    public void setAdd_zip(String add_zip) {
        Add_zip = add_zip;
    }

    public int getHourlyRate010() {
        return HourlyRate010;
    }

    public void setHourlyRate010(int hourlyRate010) {
        HourlyRate010 = hourlyRate010;
    }

    public int getHourlyRate1020() {
        return HourlyRate1020;
    }

    public void setHourlyRate1020(int hourlyRate1020) {
        HourlyRate1020 = hourlyRate1020;
    }

    public int getHourlyRate2050() {
        return HourlyRate2050;
    }

    public void setHourlyRate2050(int hourlyRate2050) {
        HourlyRate2050 = hourlyRate2050;
    }

    public int getHourlyRate50100() {
        return HourlyRate50100;
    }

    public void setHourlyRate50100(int hourlyRate50100) {
        HourlyRate50100 = hourlyRate50100;
    }

    public ArrayList<String> getRegionAva() {
        return RegionAva;
    }

    public void setRegionAva(ArrayList<String> regionAva) {
        RegionAva = regionAva;
    }

    public ArrayList<String> getTypeOfSpec() {
        return TypeOfSpec;
    }

    public void setTypeOfSpec(ArrayList<String> typeOfSpec) {
        TypeOfSpec = typeOfSpec;
    }

    public String getPaymentAcpt() {
        return PaymentAcpt;
    }

    public void setPaymentAcpt(String paymentAcpt) {
        PaymentAcpt = paymentAcpt;
    }

    public Petsitter(String ID_sitter, String firstName, String lastName, String email, String phone, String background, String[] pictureUrls){
        super();
        this.ID_sitter = ID_sitter;
        this.Fname = firstName;
        this.Lname = lastName;
        this.Email = email;
        this.Phone = phone;

    }

    public Petsitter(String ID_sitter){
        super();
        this.ID_sitter = ID_sitter;
    }
}
