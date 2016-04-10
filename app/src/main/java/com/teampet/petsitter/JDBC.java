package com.teampet.petsitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Z on 4/10/2016.
 */
public class JDBC extends Activity{
    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        // list = new ArrayList<String>();
        thread = new Thread(background);
        thread.start();
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

                Statement stmt = null;
                Connection con=null;
                try { //create connection to database
                    con = DriverManager.getConnection(
                            URL,
                            username,
                            password);
                    stmt = con.createStatement();

                    ResultSet result = stmt.executeQuery(
                            "SELECT * FROM City ORDER BY Name LIMIT 30;");

//                    //for each record in City table add City to ArrayList and add city data to log
//                    while (result.next()) {
//                        name = result.getString("Name");
//                        code = result.getString("CountryCode");
//                        district = result.getString("District");
//                        population = result.getInt("Population");
//                        City city = new City(name, code, district, population);
//                        list.add(city.toString());
//                        Log.e("City", name + " " + code);
//                    }
//
//                    //Create intent, place ArrayList on intent object,
//                    //   request another Activity be started to use the data
//                    Intent intent = new Intent(MainActivity.this, UseData.class);
//                    intent.putStringArrayListExtra("list", list);
//                    startActivity(intent);

                    con.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            } //run
        }; //background
}
