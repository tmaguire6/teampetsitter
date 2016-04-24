package com.teampet.petsitter;


import android.content.Intent;
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
public class NearbySitters extends AppCompatActivity implements TextToSpeech.OnInitListener{
    private ListView listView;
    private ArrayList<String> stringList;
    private ArrayList<Petsitter> sitters;
    private ArrayAdapter<String> adapter;


    private TextToSpeech speaker;
    private static final String TAG = "TextToSpeech";
    //private int pos; //store the list position in the arraylist listview when user clicks on the list

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_nearbytest);

        listView = (ListView) findViewById(R.id.listView);


        Intent intent = getIntent();
        sitters = (ArrayList<Petsitter>)intent.getSerializableExtra("sitters");
        stringList = new ArrayList<>();
        for(Petsitter sitter : sitters ){
            stringList.add(sitter.getFname() + " " + sitter.getLname() + "\n" + sitter.getPhone());
        }

        adapter = new ArrayAdapter<>(this,R.layout.activity_sitter_list_item, stringList);
        listView.setAdapter(adapter); // connect listView to ArrayAdapter

        speaker = new TextToSpeech(this, this);



    }



    //--------------------------------------------------------------------------------------------//
    //speaker - txt to voice
    public void speak(String output){
        //speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null);  //for APIs before 21
        speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null, "Id 0");
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
                speak(stringList.size()+" pet sitters are found near you");
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
