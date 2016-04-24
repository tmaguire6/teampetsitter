package com.teampet.petsitter;

import java.io.Serializable;

/**
 * Created by Z on 4/10/2016.
 */
public class Pet implements Serializable{
    private String ID_pet;
    private String Name;
    private String Color;
    private String Sex;
    private Boolean NeuOrSpay;
    private String Breed;
    private String DOB;
    private double Weight;
    private String Comment;

    public String getID_pet() {
        return ID_pet;
    }

    public void setID_pet(String ID_pet) {
        this.ID_pet = ID_pet;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public Boolean getNeuOrSpay() {
        return NeuOrSpay;
    }

    public void setNeuOrSpay(Boolean neuOrSpay) {
        NeuOrSpay = neuOrSpay;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public double getWeight() {
        return Weight;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
    public Pet(String ID_pet, String Name){
        super();
        this.ID_pet = ID_pet;
        this.Name = Name;
    }

    public Pet(String ID_pet){
        super();
        this.ID_pet = ID_pet;
    }
}
