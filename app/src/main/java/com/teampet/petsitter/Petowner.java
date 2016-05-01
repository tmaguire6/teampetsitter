package com.teampet.petsitter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Z on 4/10/2016.
 */
public class Petowner implements Serializable{
    public String getID_owner() {
        return ID_owner;
    }

    public void setID_owner(String ID_owner) {
        this.ID_owner = ID_owner;
    }

    private String ID_owner;
    private String LastName;
    private String FirstName;
    private String Phone;
    private String Email;
    private String BackgroundInfo;
    private String[] PictureUrls;
    private String Add_str;
    private String Add_city;
    private String Add_state;
    private String Add_zip;



    private ArrayList<Pet> Pets;


    private String PictureUrl;



    // these are all just generated getters and setters. will remove unused ones, as they could be easily generated later if required
    public String getPictureUrl() {
        return PictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        PictureUrl = pictureUrl;
    }

    public ArrayList<Pet> getPets() {
        return Pets;
    }

    public void setPets(ArrayList<Object> pets) {
        // handle casting
        ArrayList<Pet> petList = new ArrayList<>();
        for(Object p : pets){
            petList.add((Pet)p);
        }
        Pets = petList;
    }
    // get the pet pic urls
    public ArrayList<String> getPetUrls() {
        ArrayList<String> urls = new ArrayList<>();
        for(Pet p : Pets){
            urls.add(p.getPictureUrl());
        }
        return urls;
    }

    // again, some of these aren't used yet
    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
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

    public String getBackgroundInfo() {
        return BackgroundInfo;
    }

    public void setBackgroundInfo(String backgroundInfo) {
        BackgroundInfo = backgroundInfo;
    }

    public String[] getPictureUrls() {
        return PictureUrls;
    }

    public void setPictureUrls(String[] pictureUrls) {
        PictureUrls = pictureUrls;
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

    // yes, pet owner and sitter are pretty much the same information. In future versions of the app this might be more differentiated.
    public Petowner(String ID_owner, String firstName, String lastName, String email, String phone, String background, String[] pictureUrls){
        super();
        this.ID_owner = ID_owner;
        this.LastName = lastName;
        this.FirstName = firstName;
        this.Email = email;
        this.Phone = phone;
        this.BackgroundInfo = background;
        this.PictureUrls = pictureUrls;
    }
    public Petowner(String ID_owner){
        super();
        this.ID_owner = ID_owner;

    }

}
