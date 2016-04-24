package com.teampet.petsitter;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Shiv on 4/24/2016.
 */
public class NearbySitters extends AppCompatActivity{
    private ListView listView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private Thread readList;
    private TextToSpeech speaker;
    private static final String TAG = "TextToSpeech";
    //private int pos; //store the list position in the arraylist listview when user clicks on the list

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_nearbytest);

        listView = (ListView) findViewById(R.id.listView);
        //listView.setOnItemClickListener(this); //set listener to widget
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,R.layout.activity_sitter_list_item, list);
        listView.setAdapter(adapter); // connect listView to ArrayAdapter
        readList = new Thread(background);
        readList.start();
    }

    Runnable background = new Runnable() {
        @Override
        public void run() {
            String URL = "jdbc:mysql://frodo.bentley.edu:3306/petfinderdb";
            String username = "ziyangli";
            String password = "cs680";

            try { //load driver into VM memory
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                Log.e("JDBC", "Did not load driver");

            }
            Statement stmt = null;
            Connection con = null;
            try { //create connection to database
                con = DriverManager.getConnection(
                        URL,
                        username,
                        password);
                stmt = con.createStatement();
                ResultSet result = stmt.executeQuery(
                        "SELECT Fname, Lname, Phone FROM petsitter ;");
                while (result.next()){
                    String FirstName = result.getString("Fname");
                    String LastName = result.getString("Lname");
                    String phone = result.getString ("Phone");
                    list.add(FirstName + " " + LastName + "\n" + phone);
                }
                //speaker
                if(speaker.isSpeaking()){
                    Log.i(TAG, "Speaker Speaking");
                    speaker.stop();
                    // else start speech
                } else {
                    Log.i(TAG, "Speaker Not Already Speaking");
                    speak(list.size()+" pet sitters are found near you");}
                //close connection
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };

    //--------------------------------------------------------------------------------------------//
    //speaker - txt to voice
    public void speak(String output){
        //speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null);  //for APIs before 21
        //speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null, "Id 0");
    }
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            // If a language is not be available, the result will indicate it.
            int result = speaker.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language data is missing or the language is not supported.
                Log.e(TAG, "Language is not available.");
            } else {
                // The TTS engine has been successfully initialized
                speak("Please enter your list item below");
                Log.i(TAG, "TTS Initialization successful.");
            }
        } else {
            // Initialization failed.
            Log.e(TAG,"Could not initialize TextToSpeech.");
        }
    }
    // on destroy
    @Override
    public void onDestroy(){

        // shut down TTS engine
        if(speaker != null){
            speaker.stop();
            speaker.shutdown();
        }
        super.onDestroy();
    }
}
