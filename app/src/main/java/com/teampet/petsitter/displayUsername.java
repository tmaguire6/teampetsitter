package com.teampet.petsitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.security.acl.Owner;
import java.util.ArrayList;

public class displayUsername extends AppCompatActivity{
    private EditText username;
    private ArrayList<Petowner> petowners ;
    private ArrayList<String> ownerFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);

        username = (EditText)findViewById(R.id.username);
        username.setText("Welcome back, ");
    }
    protected void displayUsername(){
        SQLHelper helper = new SQLHelper();
        petowners = new ArrayList<Petowner>();
        //downcast the object to petowner
        ArrayList<Object> petO= helper.getDatabaseValues("select * from Petowner;", SQLHelper.TableType.Owner);


        for (Petowner owner:petowners){
            ownerFirstName.add(owner.getFirstName());
        }
        int randomNumber = (int)Math.random()* (ownerFirstName.size()-1);
        String tempUsername = ownerFirstName.get(randomNumber);
        username.setText("Welcome back, " + tempUsername);
    }




}
