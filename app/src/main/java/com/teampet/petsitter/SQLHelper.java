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

    public enum TableType{
        Pet, Owner, Sitter
    }
    public static final String DATABASE_NAME = "petfinderdb.db";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME_SITTER = "petsitter";
    public static final String TABLE_NAME_OWNER = "petowner";
    public static final String TABLE_NAME_PET = "pet";

    public static final String KEY_NAME_SITTER_ID = "ID_sitter";
    public static final String KEY_NAME_SITTER_FIRST_NAME = "Fname";
    public static final String KEY_NAME_SITTER_PHONE = "Phone";
    public static final String KEY_NAME_SITTER_EMAIL = "Email";
    public static final String KEY_NAME_SITTER_LAST_NAME = "Lname";

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




    //query database and return ArrayList of all users
    public ArrayList<Object> getDatabaseValues (String queryString, TableType type) {

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
        while (result.next()) {
            switch(type){
                case Owner:
                    String ownerId = result.getString(KEY_NAME_OWNER_ID);
                    String ownerrLastName = result.getString(KEY_NAME_OWNER_LAST_NAME);
                    String ownerFirstName = result.getString(KEY_NAME_OWNER_FIRST_NAME);
                    String ownerPhone = result.getString(KEY_NAME_OWNER_PHONE);
                    String ownerEmail = result.getString(KEY_NAME_OWNER_EMAIL);

                    dataValues.add(new Petsitter(ownerId, ownerFirstName, ownerrLastName,ownerEmail,ownerPhone, "", new String[]{""}));
                    break;
                case Pet:
                    String petId = cursor.getString(cursor.getColumnIndex(KEY_NAME_PET_ID));
                    String name = cursor.getString(cursor.getColumnIndex(KEY_NAME_PET_NAME));
                    //dataValues.add(new Pet(petId, name));
                    break;
                case Sitter:
                    String sitterId = result.getString(KEY_NAME_SITTER_ID);
                    String sitterLastName = result.getString(KEY_NAME_SITTER_LAST_NAME);
                    String sitterFirstName = result.getString(KEY_NAME_SITTER_FIRST_NAME);
                    String sitterPhone = result.getString(KEY_NAME_SITTER_PHONE);
                    String sitterEmail = result.getString(KEY_NAME_SITTER_EMAIL);

                    dataValues.add(new Petsitter(sitterId, sitterFirstName, sitterLastName, sitterEmail, sitterPhone, "", new String[]{""}));

                    break;


            }
        }
    }
    //-------------------------------------------------------------------------------------


}