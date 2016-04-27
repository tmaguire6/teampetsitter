package com.teampet.petsitter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;


public class Profile extends AppCompatActivity {

    private ImageView ImView;
    Button buttonSend;
    Petsitter sitter;
    Petowner owner;
    private TextView textInfo;
    private TabHost tabs;
    // if false, then this is a sitter profile
    boolean isOwner = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profiletest);
        buttonSend = (Button) findViewById(R.id.buttonSend);


        // initialize the tabs
        tabs=(TabHost)findViewById(R.id.tabHost);
        tabs.setup();

        TabHost.TabSpec spec;

        // Initialize a TabSpec for tab1 and add it to the TabHost
        spec=tabs.newTabSpec("tag1");	//create new tab specification
        spec.setContent(R.id.tab1);    //add tab view content
        spec.setIndicator("Info");    //put text on tab
        tabs.addTab(spec);             //put tab in TabHost container



        //-------------------------------------------------------------------------------------

        // Initialize a TabSpec for tab2 and add it to the TabHost
        spec=tabs.newTabSpec("tag2");		//create new tab specification
        spec.setContent(R.id.tab2);			//add view tab content
        spec.setIndicator("Reviews");
        tabs.addTab(spec);					//put tab in TabHost container



        //-------------------------------------------------------------------------------------

        // Initialize a TabSpec for tab3 and add it to the TabHost
        spec=tabs.newTabSpec("tag3");		//create new tab specification
        spec.setContent(R.id.tab3);			//add tab view content
        spec.setIndicator("Photos");			//put text on tab
        tabs.addTab(spec); 					//put tab in TabHost container

        // get intent to get data about a sitter or owner
        Intent intent = getIntent();
        // also get the text view
        textInfo = (TextView) findViewById(R.id.profileText);
        SQLHelper.TableType type = (SQLHelper.TableType)intent.getSerializableExtra("type");
        String profileString = "";
        switch (type){
            case Sitter:
                sitter = (Petsitter)intent.getSerializableExtra("sitter");
                // its a sitter, so build the profile string based on that
                profileString = sitter.getFname() + " " + sitter.getLname() +
                        "\n\n" + sitter.getBackground() + "\n\n" + "Email: " + sitter.getEmail() + "\n Phone: " + sitter.getPhone();
                break;
            case Owner:
                owner = (Petowner)intent.getSerializableExtra("owner");
                profileString = owner.getFirstName() + " " + owner.getLastName() +
                        "\n\n" + owner.getBackgroundInfo() + "\n\n" + "Email: " + owner.getEmail() + "\n Phone: " + owner.getPhone();
                isOwner = true;
                break;
        }
        // set the info text
        textInfo.setText(profileString);

        // if this is an owner, turn the send text off
        if(isOwner){
            buttonSend.setVisibility(View.GONE);
        }

        //Here we are defining the Imageadapter object
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_Pager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter); // Here we are passing and setting the adapter for the images


        // if

        //Profile Picture
        ImView = (ImageView) findViewById(R.id.image);
        ImView.setImageResource(R.drawable.fbphoto);

        buttonSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendSMS();

            }
        });
    }


    protected void sendSMS() {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        // if were here we know we're in sitter mode
        String phone = sitter.getPhone().replace("(","").replace(")","").replace("-","").replace(" ","");

        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("sms_body", "Hello there!");
        smsIntent.putExtra("address", phone);



        try {
            startActivity(smsIntent);
            finish();
            Toast.makeText(getApplicationContext(),
                    "Message Sent!",
                    Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS failed, please try again later!",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }








    }



    @Override
        public boolean onCreateOptionsMenu(Menu menu) { // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true; }
//
//    @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId(); //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item); }
}
