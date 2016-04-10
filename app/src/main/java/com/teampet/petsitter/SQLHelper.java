package com.teampet.petsitter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/** Helper to the database, manages versions and creation */
public class SQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "petfinderdb.db";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_NAME_1 = "petsitter";
    public static final String TABLE_NAME_2 = "petowner";
    public static final String TABLE_NAME_3 = "pet";
    public static final String KEY_NAME_1 = "ID_sitter";
    public static final String KEY_NAME_1_1 = "Lname";
    public static final String KEY_NAME_2 = "ID_owner";
    public static final String KEY_NAME_2_1 = "Lname";
    public static final String KEY_NAME_3 = "ID_pet";
    public static final String KEY_NAME_3_1 = "Name";

//    public static final String CREATE_TABLE = "CREATE TABLE animals ("
//            + KEY_ID + "," + KEY_NAME + " text,"
//            + KEY_Q + " integer);";

    private ContentValues values;
    private ArrayList<Petsitter> sitterList;
    private ArrayList<Petowner> ownerList;
    private ArrayList<Pet> petList;
    private Cursor cursor;

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //called to create table
    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = CREATE_TABLE;
//        Log.d("SQL", "onCreate: " + sql);
//        db.execSQL(sql);
    }

    //called when database version mismatch
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion) return;

        Log.d("Petfinder", "onUpgrade: Version = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_3);
        onCreate(db);
    }

   //add sitter to database
    public void addSitter(Petsitter sitter) {
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_NAME_1, sitter.getID_sitter());
        values.put("Fname",sitter.getFname());
        values.put("Lname",sitter.getLname());
        values.put("Email", sitter.getEmail());
        values.put("Phone", sitter.getPhone());
        db.insert(TABLE_NAME_1, null, values);
        Log.d("Petsitter", sitter.getID_sitter() + " added");
        db.close();
    }

    //update sitter Fname in database
    public void updateSitterFname(Petsitter sitter, Petsitter newSitter){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Fname", newSitter.getFname());
        db.update(TABLE_NAME_1, values, "Fname" + "=?", new String[]{sitter.getFname()});
        Log.d("Petsitter", sitter.getFname() + " updated");
        db.close();
    }

    //update sitter Lname in database
    public void updateSitterLname(Petsitter sitter, Petsitter newSitter){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Lname", newSitter.getLname());
        db.update(TABLE_NAME_1, values, "Lname" + "=?", new String[]{sitter.getLname()});
        Log.d("Petsitter", sitter.getLname() + " updated");
        db.close();
    }

    //update sitter Phone in database
    public void updateSitterPhone(Petsitter sitter, Petsitter newSitter){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Phone", newSitter.getPhone());
        db.update(TABLE_NAME_1, values, "Phone" + "=?", new String[] {sitter.getFname()});
        Log.d("Petsitter", sitter.getPhone() + " updated");
        db.close();
    }

    //update sitter Email in database
    public void updateSitterEmail(Petsitter sitter, Petsitter newSitter){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Email", newSitter.getFname());
        db.update(TABLE_NAME_1, values, "Email" + "=?", new String[] {sitter.getEmail()});
        Log.d("Petsitter", sitter.getEmail() + " updated");
        db.close();
    }

    //delete sitter from database
    public void deleteSitter(Petsitter sitter){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_1, KEY_NAME_1 + "=?", new String[]{sitter.getID_sitter()});
        Log.d("Petfinder", sitter.getID_sitter() + " deleted");
        db.close();
    }

    //query database and return ArrayList of all users
    public ArrayList<Petsitter> getSitterList () {

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.query(TABLE_NAME_1,
                new String[] {KEY_NAME_1, KEY_NAME_1_1},
                null, null, null, null,KEY_NAME_1);

        //write contents of Cursor to list
        sitterList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex(KEY_NAME_1));
            String Lname = cursor.getString(cursor.getColumnIndex(KEY_NAME_1_1));
            sitterList.add(new Petsitter(str, Lname));
        }
        db.close();
        return sitterList;
    }

    //-------------------------------------------------------------------------------------

    //add owner to database
    public void addOwner(Petowner owner) {
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_NAME_2, owner.getID_owner());
        values.put("Fname", owner.getFname());
        values.put("Lname", owner.getLname());
        values.put("Email", owner.getEmail());
        values.put("Phone", owner.getPhone());
        db.insert(TABLE_NAME_2, null, values);
        Log.d("Petowner", owner.getID_owner() + " added");
        db.close();
    }

    //update owner Fname in database
    public void updateOwnerFname(Petowner owner, Petowner newOwner){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Fname", newOwner.getFname());
        db.update(TABLE_NAME_2, values, "Fname" + "=?", new String[]{owner.getFname()});
        Log.d("Petowner", owner.getFname() + " updated");
        db.close();
    }

    //update owner Lname in database
    public void updateOwnerLname(Petowner owner, Petowner newOwner){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Lname", newOwner.getLname());
        db.update(TABLE_NAME_2, values, "Lname" + "=?", new String[]{owner.getLname()});
        Log.d("Petowner", owner.getLname() + " updated");
        db.close();
    }

    //update sitter Phone in database
    public void updateOwnerPhone(Petowner owner, Petowner newOwner){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Phone", newOwner.getPhone());
        db.update(TABLE_NAME_2, values, "Phone" + "=?", new String[] {owner.getFname()});
        Log.d("Petowner", owner.getPhone() + " updated");
        db.close();
    }

    //update sitter Email in database
    public void updateOwnerEmail(Petowner owner, Petowner newOwner){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Email", newOwner.getFname());
        db.update(TABLE_NAME_2, values, "Email" + "=?", new String[] {owner.getEmail()});
        Log.d("Petowner", owner.getEmail() + " updated");
        db.close();
    }

    //delete owner from database
    public void deleteOwner(Petowner owner){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_2, KEY_NAME_2 + "=?", new String[]{owner.getID_owner()});
        Log.d("Petfinder", owner.getID_owner() + " deleted");
        db.close();
    }

    //query database and return ArrayList of all users
    public ArrayList<Petowner> getOwnerList () {

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.query(TABLE_NAME_2,
                new String[] {KEY_NAME_2, KEY_NAME_2_1},
                null, null, null, null,KEY_NAME_2);

        //write contents of Cursor to list
        ownerList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex(KEY_NAME_2));
            String Lname = cursor.getString(cursor.getColumnIndex(KEY_NAME_2_1));
            ownerList.add(new Petowner(str, Lname));
        }
        db.close();
        return ownerList;
    }
//-----------------------------------------------------------------------------------------------

    //add pet to database
    public void addPet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_NAME_3, pet.getID_pet());
        values.put("Name", pet.getName());
        values.put("Color", pet.getColor());
        values.put("Sex", pet.getSex());
        values.put("Breed", pet.getBreed());
        db.insert(TABLE_NAME_3, null, values);
        Log.d("Pet", pet.getID_pet() + " added");
        db.close();
    }

    //update pet name in database
    public void updatePetName(Pet pet, Pet newPet){
        SQLiteDatabase db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("Name", newPet.getName());
        db.update(TABLE_NAME_3, values, "name" + "=?", new String[]{pet.getName()});
        Log.d("Pet", pet.getName() + " updated");
        db.close();
    }

    //delete owner from database
    public void deletePet(Pet pet){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_3, KEY_NAME_3 + "=?", new String[]{pet.getID_pet()});
        Log.d("Petfinder", pet.getID_pet() + " deleted");
        db.close();
    }

    //query database and return ArrayList of all users
    public ArrayList<Pet> getPetList () {

        SQLiteDatabase db = this.getWritableDatabase();
        cursor = db.query(TABLE_NAME_3,
                new String[] {KEY_NAME_3, KEY_NAME_3_1},
                null, null, null, null,KEY_NAME_3);

        //write contents of Cursor to list
        petList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex(KEY_NAME_3));
            String name = cursor.getString(cursor.getColumnIndex(KEY_NAME_3_1));
            petList.add(new Pet(str, name));
        }
        db.close();
        return petList;
    }
}