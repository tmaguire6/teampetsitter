package com.teampet.petsitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.security.acl.Owner;
import java.util.ArrayList;

public class displayUsername extends AppCompatActivity{
    private EditText username;
    private ArrayList<Object> petowners ;
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
        petowners = new ArrayList<Object>();
        //downcast the object to petowner
        ArrayList<Object> petO= helper.getDatabaseValues("select * from Petowner;", SQLHelper.TableType.Owner);

        // add first names to list
        for (Object owner:petowners){
            ownerFirstName.add(((Petowner)owner).getFirstName());
        }
        // grab a random first name to display
        int randomNumber = (int)Math.random()* (ownerFirstName.size()-1);
        String tempUsername = ownerFirstName.get(randomNumber);
        username.setText("Welcome back, " + tempUsername);
    }




}
