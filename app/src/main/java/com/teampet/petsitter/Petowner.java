package com.teampet.petsitter;

/**
 * Created by Z on 4/10/2016.
 */
public class Petowner {
    private String ID_owner;
    private String Fname;
    private String Lname;
    private String Phone;
    private String Email;
    private String Add_str;
    private String Add_city;
    private String Add_state;
    private String Add_zip;
    private String TypeOfPayment;

    public String getID_owner() {
        return ID_owner;
    }

    public void setID_owner(String ID_owner) {
        this.ID_owner = ID_owner;
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

    public String getTypeOfPayment() {
        return TypeOfPayment;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        TypeOfPayment = typeOfPayment;
    }
    public Petowner(String ID_owner, String Lname){
        super();
        this.ID_owner = ID_owner;
        this.Lname = Lname;
    }

    public Petowner(String ID_owner){
        super();
        this.ID_owner = ID_owner;
    }
}
