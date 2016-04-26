package com.teampet.petsitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import java.util.ArrayList;

public class displayUsername extends AppCompatActivity{
    private EditText username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);

        username = (EditText)findViewById(R.id.username);
        username.setText("Welcome back, ");
    }
    protected void displayUsername(){
        SQLHelper helper = new SQLHelper();
        ArrayList<Object> petowner = helper.getDatabaseValues("select * from Petowner;", SQLHelper.TableType.Owner);
        ArrayList<Petowner> pet_owners = new ArrayList<>();




    }
}
