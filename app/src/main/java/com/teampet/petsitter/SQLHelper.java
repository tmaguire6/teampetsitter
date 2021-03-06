package com.teampet.petsitter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;


/** Helper to the database, manages versions and creation */
public class SQLHelper {

    // used to differentiate between the three types all over the application
    public enum TableType{
        Pet, Owner, Sitter
    }


    public static final String KEY_NAME_SITTER_ID = "ID_sitter";
    public static final String KEY_NAME_SITTER_FIRST_NAME = "Fname";
    public static final String KEY_NAME_SITTER_PHONE = "Phone";
    public static final String KEY_NAME_SITTER_EMAIL = "Email";
    public static final String KEY_NAME_SITTER_LAST_NAME = "Lname";
    // used for both sitter and owner
    public static final String KEY_NAME_BACKGROUND = "Background";
    public static final String KEY_NAME_PICTUREURL = "Picture";

    public static final String KEY_NAME_OWNER_ID = "ID_owner";
    public static final String KEY_NAME_OWNER_FIRST_NAME = "Fname";
    public static final String KEY_NAME_OWNER_PHONE = "Phone";
    public static final String KEY_NAME_OWNER_EMAIL = "Email";
    public static final String KEY_NAME_OWNER_LAST_NAME = "Lname";

    public static final String KEY_NAME_PET_ID = "ID_pet";
    public static final String KEY_NAME_PET_NAME = "Name";
    public boolean done = false;
    private Thread thread;

//    public static final String CREATE_TABLE = "CREATE TABLE animals ("
//            + KEY_ID + "," + KEY_NAME + " text,"
//            + KEY_Q + " integer);";

    private ContentValues values;
    private ArrayList<Object> dataValues = null;
    private String query = null;
    private Cursor cursor;
    private TableType tableType;



    public Petowner getCurrentOwnerAndPets(){
        // could probably do this more effieciently in the future with one select, but this works too
        // eventually we will have a login step to actually get the correct owner, for now just grab one for display purposes
        query = "select * from petowner where ID_owner=28888994";
        tableType = TableType.Owner;
        // start thread to get data
        thread = new Thread(background);
        thread.start();

        // wait for thread to finish
        while(!done){}

        Petowner owner = (Petowner)dataValues.get(0);

        // now get pets
        owner.setPets(getDatabaseValues("select * from pet where petOwnerID=28888994", TableType.Pet));
        return owner;
    }

    //query database and return ArrayList of all users
    public ArrayList<Object> getDatabaseValues (String queryString, TableType type) {
        // reset just in case
        dataValues = new ArrayList<>();
        done = false;
        // give things a second to reset
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){}
        // set query string
        query = queryString;
        tableType = type;

        // start thread to get data
        thread = new Thread(background);
        thread.start();

        // wait for thread to finish
        while(!done){}

        return dataValues;
    }

    private Runnable background = new Runnable() {
        public void run(){
            String URL = "jdbc:mysql://frodo.bentley.edu:3306/petfinderdb";
            String username = "ziyangli";
            String password = "cs680";

            try { //load driver into VM memory
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                Log.e("JDBC", "Did not load driver");

            }
            dataValues = new ArrayList<>();
            Statement stmt = null;
            Connection con=null;
            try { //create connection to database
                con = DriverManager.getConnection(
                        URL,
                        username,
                        password);
                stmt = con.createStatement();

                ResultSet result = stmt.executeQuery(
                        query);


                FillOutArray(result, tableType);


                con.close();
                done = true;
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        } //run
    }; //background

    private void FillOutArray(ResultSet result, TableType type) throws SQLException {
        // reset data values
        dataValues = new ArrayList<>();
        // load all the values based on what type it is, and add it to the object array to be returned
        while (result.next()) {
            switch(type){
                case Owner:
                    String ownerId = result.getString(KEY_NAME_OWNER_ID);
                    String ownerrLastName = result.getString(KEY_NAME_OWNER_LAST_NAME);
                    String ownerFirstName = result.getString(KEY_NAME_OWNER_FIRST_NAME);
                    String ownerPhone = result.getString(KEY_NAME_OWNER_PHONE);
                    String ownerEmail = result.getString(KEY_NAME_OWNER_EMAIL);
                    String ownerBackground = result.getString(KEY_NAME_BACKGROUND);

                    dataValues.add(new Petowner(ownerId, ownerFirstName, ownerrLastName,ownerEmail,ownerPhone, ownerBackground, new String[]{""}));
                    break;
                case Pet:
                    String petId = result.getString(KEY_NAME_PET_ID);
                    String name = result.getString(KEY_NAME_PET_NAME);
                    String picture = result.getString(KEY_NAME_PICTUREURL);
                    dataValues.add(new Pet(petId, name, picture));
                    break;
                case Sitter:
                    String sitterId = result.getString(KEY_NAME_SITTER_ID);
                    String sitterLastName = result.getString(KEY_NAME_SITTER_LAST_NAME);
                    String sitterFirstName = result.getString(KEY_NAME_SITTER_FIRST_NAME);
                    String sitterPhone = result.getString(KEY_NAME_SITTER_PHONE);
                    String sitterEmail = result.getString(KEY_NAME_SITTER_EMAIL);
                    String sitterBackground = result.getString(KEY_NAME_BACKGROUND);
                    String sitterPicture = result.getString(KEY_NAME_PICTUREURL);
                    dataValues.add(new Petsitter(sitterId, sitterFirstName, sitterLastName, sitterEmail, sitterPhone, sitterBackground, sitterPicture));

                    break;


            }
        }
    }
    //-------------------------------------------------------------------------------------


}